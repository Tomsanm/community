package com.twm.community.controller;

import com.twm.community.entity.User;
import com.twm.community.service.UserService;
import com.twm.community.util.CommunityUtil;
import com.twm.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.http.HttpResponse;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/setting")
    public String getSetting(){
        return "/site/setting";
    }
    // 配置上传图片存储路径
    @Value("${community.file.path}")
    private String filepath;
    // 配置图片访问域名
    @Value("${community.path.domain}")
    private String domian;
    // 注入 UserService
    @Autowired
    private UserService userService;
    //
    @Autowired
    private HostHolder hostHolder;


    @PostMapping("/upload")
    public String imageUpload(MultipartFile multipartFile, Model model){
        if (multipartFile==null){
            model.addAttribute("error","您还未选择图片！");
            return "/site/setting";
        }
        // 获取图片原始名称
        String fileName = multipartFile.getOriginalFilename();
        // 获取文件尾缀
        String suffix = StringUtils.substring(fileName,fileName.lastIndexOf('.'));
        if (StringUtils.isEmptyOrWhitespace(suffix)){
            model.addAttribute("error","文件格式错误！");
            return "site/setting";
        }
        // 一切都没有问题的话，保存图片到服务器磁盘
        fileName = CommunityUtil.gennerateUUID()+suffix;
        String dest = filepath+"/"+ fileName;
        // 确定文件存储路径
        File file = new File(dest);
        // 开始存入二进制图像文件
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 更新当前用户的头像路径
        String url = domian+"/user/header/"+fileName;
        // 获取当前用户
        User user = hostHolder.getUser();
        userService.updateHeaderUrl(user.getId(),url);

        return "redirect:/";
    }

    @GetMapping("/header/{filename}")
    public void header(HttpServletResponse response , @PathVariable("filename") String filename){
        filename = filepath +"/"+filename;
        // 获取文件后缀
        String suffix = filename.substring(filename.lastIndexOf('.'));
        // 设定响应图片类型
        response.setContentType("image/"+suffix);

        try {
            FileInputStream fis = new FileInputStream(filename);
            OutputStream os = response.getOutputStream();

            byte[] buffer = new byte[1024];
            int b=0;
            while ((b = fis.read(buffer)) != -1){
                os.write(buffer,0,b);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }





}
