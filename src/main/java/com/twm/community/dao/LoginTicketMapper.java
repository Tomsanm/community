package com.twm.community.dao;

import com.twm.community.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LoginTicketMapper {
    // 每次用户登录时 插入 一条数据 LoginTicket
    @Insert({"insert into login_ticket (user_id,ticket,status,expired) ",
            "values(#{userId},#{ticket},#{status},#{expired})"})
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int insertLoginTicket(LoginTicket loginTicket);
    // 根据 用户带来的 ticket 属性 提取 LoginTicket 对象
    @Select({"select id, user_id, ticket,status,expired from login_ticket ",
            "where ticket = #{ticket}"})
    LoginTicket getLoginTicketBy(String ticket);
    // 设置ticket 是否过期 退出账号使用
    @Update("update login_ticket set status=#{status} where ticket=#{ticket}")
    int setStatus(String ticket,int status);
}
