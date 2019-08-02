package com.twm.community.service;

import com.twm.community.dao.DiscussPostMapper;
import com.twm.community.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussPostService {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    // 查首页的帖子
    public List<DiscussPost> findDiscussPosts(int offset,int limit) {
        return discussPostMapper.selectDiscussPosts(offset,limit);
    }
    // 总帖子数量
    public int getCount(int userId){
        return discussPostMapper.selectDiscussPostRows(userId);
    }


}
