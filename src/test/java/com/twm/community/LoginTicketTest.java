package com.twm.community;


import com.twm.community.dao.LoginTicketMapper;
import com.twm.community.entity.LoginTicket;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LoginTicketTest {

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    // 测试插入数据是否正常
    @Test
    public void testInsert(){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(101);
        loginTicket.setStatus(0);
        loginTicket.setTicket("ffffff");
        loginTicket.setExpired(new Date(System.currentTimeMillis()+1000*60*30));
        int flag = loginTicketMapper.insertLoginTicket(loginTicket);
        log.info("如果为1就是正常"+flag);
    }

    // 测试根据 ticket 提取 整个 login_ticket
    @Test
    public void testGetLoginTicket(){
        LoginTicket loginTicket = loginTicketMapper.getLoginTicketBy("ffffff");
        log.info(loginTicket.toString());
    }

    // 测试设置 login_ticket 的状态是否成功
    @Test
    public void setLoginTicketStatus(){
        int flag = loginTicketMapper.setStatus("ffffff",1);
        log.info("状态是否为1"+flag);
        log.info(new Date().toString());
    }


}
