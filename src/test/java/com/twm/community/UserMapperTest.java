package com.twm.community;


import com.twm.community.dao.UserMapper;
import com.twm.community.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void selectUserMapperTest(){
        User user= userMapper.selectUserById(101);
        System.out.println(user);

    }

    @Test
    public void addUserMapperTest(){
        User user = new User();
        user.setUsername("test_add");
        user.setEmail("18125178@bjtu.edu.cn");
        user.setHeaderUrl("http://www.nowcoder.com.101.png");
        user.setCreateTime(new Date());
        int ok = userMapper.addUser(user);
        System.out.println(ok);
    }

    @Test
    public void setUserPasswordTest(){
        int ok= userMapper.updatePassword(101,"123456");
        System.out.println(ok);

    }

}
