package com.twm.community.service;

import com.twm.community.dao.LoginTicketMapper;
import com.twm.community.dao.UserMapper;
import com.twm.community.entity.LoginTicket;
import com.twm.community.entity.User;
import com.twm.community.util.CommunityUtil;
import com.twm.community.util.MailClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
@Slf4j
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${community.path.domain}")
    private String domain;

    // 根据userId 这个外键关联一些必要的帖子所属用户信息
    public User findUserById(int userId){
        return userMapper.selectUserById(userId);
    }
    // 根据名字查 user
    public User findUserByName(String username){
        User user = userMapper.selectUserByUserName(username);
        return user;
    }

    // login 业务，负责返回登录的一系列值
    public Map<String,Object> login(String username, String password, int expireSeconds, HttpSession session){
        Map<String,Object> map = new HashMap<>();
        if (username==null){
            map.put("usernameMsg","账号不能为空");
            return map;
        }
        if(password==null){
            map.put("passwordMsg","密码不能为空");
            return map;
        }

        User user = findUserByName(username);

        if (user==null) {
            map.put("usernameMsg","用户不存在");
            return map;
        }
        // 判断密码是否正确
        if (!user.getPassword().equals(CommunityUtil.md5(password+user.getSalt()))){
            map.put("passwordMsg","密码错误");
            return map;
        }
        // 一切正常 ，生成 登入凭证
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(CommunityUtil.gennerateUUID());
        loginTicket.setStatus(0);
        loginTicket.setExpired( new Date(System.currentTimeMillis()+expireSeconds * 7));
        log.info(loginTicket.getExpired().toString());
        int flag = loginTicketMapper.insertLoginTicket(loginTicket);
        map.put("ticket",loginTicket.getTicket());
        // 把 user 信息放入session 中
        session.setAttribute("user",user);
        return map;
    }





    /**
     *  邮件注册功能
     *  返回 注册后服务后的状态
     */

    public Map<String, Object> register(User user) {
        Map<String, Object> map = new HashMap<>();

        // 邮件内容是否空
        if (user == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        if (StringUtils.isEmptyOrWhitespace(user.getUsername())) {
            map.put("usernameMs", "账号不能为空");
            return map;
        }
        if (StringUtils.isEmptyOrWhitespace(user.getPassword())) {
            map.put("passwordMsg", "密码不能为空");
            return map;
        }
        if (StringUtils.isEmptyOrWhitespace(user.getEmail())) {
            map.put("emailMsg", "邮件不能为空");
            return map;
        }
        // 先验证账号是否存在
        User user1 = userMapper.selectUserByUserName(user.getUsername());
        if (user1 != null) {
            map.put("usernameMsg", "用户已存在");
            return map;
        }
        // 先验邮箱是否存在
        user1 = userMapper.selectUserByUserEmail(user.getEmail());
        if (user1 != null) {
            map.put("usernameMsg", "邮箱已被注册");
            return map;
        }

        // 现在开始注册，把用户信息存放数据库
        user.setSalt(CommunityUtil.gennerateUUID().substring(0, 5));

        user.setPassword(CommunityUtil.md5(user.getPassword() + user.getSalt()));
        user.setCreateTime(new Date());
        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(CommunityUtil.gennerateUUID());
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        // 将用户信息存入数据库
        userMapper.addUser(user);

        // 发送激活码
        Context context = new Context();
        context.setVariable("email", user.getEmail());
        context.setVariable("url", "https://blog.csdn.net/Tommy1295");
        String content = templateEngine.process("/mail/activemail", context);
//        System.out.println(content);
        mailClient.sendMail(user.getEmail(), "激活邮箱", content);

        return map;

    }
    // 插入 loginTicket 对象
    public int inputLoginTicket(LoginTicket loginTicket){
        return loginTicketMapper.insertLoginTicket(loginTicket);
    }

    // 根据ticket 查出 loginticket 对象
    public LoginTicket getLoginTicketByTicket(String ticket){
        LoginTicket loginTicket = loginTicketMapper.getLoginTicketBy(ticket);
        return  loginTicket;
    }
    // 根据ticket 设置status 状态，使得 ticket 失效
    public int setStatus(String ticket, int status){
        int flag = loginTicketMapper.setStatus(ticket,status);
        return flag;
    }

    // 更新用户的头像 url
    public int updateHeaderUrl(int userId,String url){
        return userMapper.updateHeader(userId,url);
    }







}
