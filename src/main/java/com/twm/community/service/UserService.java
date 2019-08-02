package com.twm.community.service;

import com.twm.community.dao.UserMapper;
import com.twm.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    // 根据userId 这个外键关联一些必要的帖子所属用户信息
    public User findUserById(int userId){
        return userMapper.selectUserById(userId);
    }

}
