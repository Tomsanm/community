package com.twm.community.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class CookieUtil {

    public static String getValue(HttpServletRequest request,String name){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())){
                    return cookie.getValue();
                }
            }

        }
        return null;
    }
}
