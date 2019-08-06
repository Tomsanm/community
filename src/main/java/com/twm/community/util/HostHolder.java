package com.twm.community.util;

import com.twm.community.entity.User;
import org.springframework.stereotype.Component;

/**
 *  持有用户信息，代替 session 对象
 */

@Component
public class HostHolder {
    private ThreadLocal<User> users = new ThreadLocal<>();

    public void setUser(User user){
        users.set(user);
    }

    public User getUser(){
        return users.get();
    }

    public void clear(){
        users.remove();
    }

}
