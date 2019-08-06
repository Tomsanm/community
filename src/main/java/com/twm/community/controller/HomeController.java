package com.twm.community.controller;


import com.twm.community.entity.DiscussPost;
import com.twm.community.entity.Page;
import com.twm.community.entity.User;
import com.twm.community.service.DiscussPostService;
import com.twm.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@Controller
public class HomeController {
    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;

    @GetMapping({"/index","/"})
    public String getIndexPage(ModelMap modelMap, Page page){ // spring会自动把page注入给 modelMap
        // 得到页面的总记录数
        page.setRows(discussPostService.getCount(0));
        page.setPath("/index");

        List<DiscussPost> discussPosts = discussPostService.findDiscussPosts(page.getOffset(),page.getLimit());
        List<Map<String, Object>> indexList = new ArrayList<>();
        for (int i=0; i<discussPosts.size();i++){
            Map map = new HashMap();
            DiscussPost discussPost = discussPosts.get(i);
            map.put("post",discussPost);
            int userId = discussPost.getUserId();
            User user = userService.findUserById(userId);
            map.put("user",user);
            indexList.add(map);
        }
        modelMap.addAttribute("discussPosts",indexList);
        log.error("我是slf4j的日志噢噢噢噢");
        log.warn("我是warn");
        return "index";

    }



}
