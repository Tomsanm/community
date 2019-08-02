package com.twm.community;


import com.twm.community.dao.DiscussPostMapper;
import com.twm.community.entity.DiscussPost;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DiscussPostTest {

    @Autowired
    DiscussPostMapper discussPostMapper;

    @Test
    public void testDiscuss(){
        List<DiscussPost> discussPostList = discussPostMapper.selectDiscussPosts(0,10);
        int countAll = discussPostMapper.selectDiscussPostRows(0);
        System.out.println("选取了"+discussPostList.size()+"总共数据是"+countAll);
    }



}
