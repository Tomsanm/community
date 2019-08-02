package com.twm.community.dao;

import com.twm.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DiscussPostMapper {

    // 因为展现在首页的帖子相当于一个帖子列表
    @Select("SELECT * FROM discuss_post WHERE status!=2 order by type desc, create_time desc LIMIT #{offset}, #{limit}")
    List<DiscussPost> selectDiscussPosts(int offset, int limit);
    @Select("SELECT COUNT(*) FROM discuss_post WHERE status!=2")
    // 查询共有多少个帖子 @Param 这个注解给参数取别名 动态拼sql时候用到参数仅有一个参数必须写这个注解
    int selectDiscussPostRows(@Param("userId") int userId);
}
