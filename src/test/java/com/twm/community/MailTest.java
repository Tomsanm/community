package com.twm.community;

import com.twm.community.util.MailClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MailTest {

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testMail(){
        mailClient.sendMail("twm951226@126.com","测试","欢迎，你已经会用springboot 发送邮件了~");
    }



    @Test // 使用模板引擎的邮件模式 html 邮件模板
    public void testhtmlMail(){
        Context context = new Context() ;
        context.setVariable("username","sunday");
        String content = templateEngine.process("/mail/activemail",context);
        System.out.println(content);
        mailClient.sendMail("twm951226@126.com","激活邮箱",content);

    }


}
