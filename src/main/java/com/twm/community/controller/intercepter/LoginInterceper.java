package com.twm.community.controller.intercepter;

import com.twm.community.entity.LoginTicket;
import com.twm.community.entity.User;
import com.twm.community.service.UserService;
import com.twm.community.util.CookieUtil;
import com.twm.community.util.HostHolder;
import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceper implements HandlerInterceptor {
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private UserService userService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 验证 hosthander 中是否含有 ticket 对象
        String  ticket= CookieUtil.getValue(request,"ticket");
        if(ticket == null){
            request.getRequestDispatcher("/getlogin").forward(request,response);
            request.setAttribute("usernameMsg","您还未登录");
            return false;
        }else{
            // 根据 ticket 找到
            LoginTicket loginTicket = userService.getLoginTicketByTicket(ticket);
            // 根据登录信息，获取用户名
            User user = userService.findUserById(loginTicket.getUserId());
            hostHolder.setUser((User) user);
            return true;
        }
    }

    @Override// 把user 放入 model
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        User user = hostHolder.getUser();
            if (user != null ){
                modelAndView.addObject("loginUser",user);
            }
    }


    @Override // 请求结束后清除 Threadlocal 内的变量
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}
