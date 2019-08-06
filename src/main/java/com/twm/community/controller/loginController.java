package com.twm.community.controller;

import com.google.code.kaptcha.Producer;
import com.twm.community.entity.User;
import com.twm.community.service.UserService;
import com.twm.community.util.CommunityConstant;
import com.twm.community.util.HostHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Map;
@Slf4j
@Controller
public class loginController {

    @Autowired
    UserService userService;

    @Autowired
    Producer kaptchaProducer;
    @Autowired
    private HostHolder hostHolder;

    @GetMapping("/getlogin")
    public String getLoginPage(){
        return "site/login";
    }

    @PostMapping("/login")
    public String login(@Param("username") String username, @Param("password") String password,@Param("code") String code,
                        @Param("rememberme") boolean rememberme, ModelMap modelMap, HttpSession session,HttpServletResponse response){// 提取前端传入的   账号\密码\记住我 的值

        // 验证码是否正确
        String sessionKaptcha = (String) session.getAttribute("kaptcha");
        if (!code.equals(sessionKaptcha)){
            modelMap.addAttribute("codeMsg","验证码错误");
            return "/site/login";
        }




        int expireTime = rememberme?CommunityConstant.REMEMBER_EXPIRED_TIME:CommunityConstant.EXPIRED_TIME;

        Map<String,Object> map = userService.login(username,password, expireTime,session);


        if (map.containsKey("ticket")){  // map 中返回了 ticket 说明登录成功
            Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
            response.addCookie(cookie);
            log.info("现在系统的时间是"+ new Date());
            return "redirect:/index";
        }else{
            modelMap.addAttribute("usernameMsg",map.get("usernameMsg"));
            modelMap.addAttribute("passwordMsg",map.get("passwordMsg"));
            return "site/login";
        }

    }
    @GetMapping("/kaptcha")
    public void getKaptcha(HttpServletResponse response, HttpSession session){
        // 生成验证码，
        String text = kaptchaProducer.createText();
        BufferedImage image = kaptchaProducer.createImage(text);
        // 将验证码放入session,求情就可以根据这次会话得到它的专属验证码
        session.setAttribute("kaptcha",text);
        // 把同样内容的图片输出给浏览器
        response.setContentType("image/png");
        try {
            OutputStream os = response.getOutputStream();
            ImageIO.write(image,"png",os);
        }catch(IOException e){
            log.error("验证码响应失败"+e.getMessage());
        }

    }


    @GetMapping("/register")
    public String getRegisterPage(){
        // 直接返回注册页面
        return "/site/register";
    }

    @PostMapping("/register")
    public String Register(ModelMap modelMap, User user){
        // 直接返回注册页面
        Map<String,Object> map  = userService.register(user);

        if(map == null || map.isEmpty()){
            // 表示注册成功，跳到操作成功的页面
            modelMap.addAttribute("msg", "注册成功，我们已向您的邮箱发送了激活邮件，请尽快激活");
            modelMap.addAttribute("target","/index");
            return "/site/operate-result";

        }else{
            modelMap.addAttribute("usernameMsg",map.get("usernameMsg"));
            modelMap.addAttribute("passwordMsg",map.get("passwordMsg"));
            modelMap.addAttribute("emailMsg",map.get("emailMsg"));

            return "/site/register";
        }


    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        hostHolder.clear();
        return "/site/login";

    }


}
