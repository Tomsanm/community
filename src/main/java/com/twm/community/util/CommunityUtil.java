package com.twm.community.util;

import org.springframework.util.DigestUtils;
import org.thymeleaf.util.StringUtils;

import java.util.UUID;

// 做一些网站常用的工具类
public class CommunityUtil {

    /**
     *  生成随机的字符串
     */
    public static String gennerateUUID(){
        return UUID.randomUUID().toString().replace("_","");
    }

    /**
     *  MD5 加密 为了安全，我们需要给密码加盐
     *  这样不管你的密码是什么，黑客无法从已知加了密的密码得到你原来的密码。
     *  我们会把第一次加盐的随机数记录下来
     */
     public static String md5(String key){
         if (StringUtils.isEmptyOrWhitespace(key)){
             return null;
         }
         else{
             return DigestUtils.md5DigestAsHex(key.getBytes());
         }
     }




}
