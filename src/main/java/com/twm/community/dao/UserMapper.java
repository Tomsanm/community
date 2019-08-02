package com.twm.community.dao;

import com.twm.community.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("SELECT id, username, password, salt,email,type, status,activation_code, header_url,create_time FROM user WHERE id = #{id}")
    User selectUserById(int id);
    @Select("SELECT id, username, password, salt,email,type, status,activation_code, header_url,create_time FROM user WHERE username = #{username}")
    User selectUserByUserName(String username);
    @Select("SELECT id, username, password, salt,email,type, status,activation_code, header_url,create_time FROM user WHERE email = #{email}")
    User selectUserByUserEmail(String email);
    @Insert("INSERT INTO user (username, password, salt,email,type, status,activation_code, header_url,create_time) values(#{username},#{password},#{salt},#{email},#{type},#{status},#{activationCode},#{headerUrl},#{createTime})")
    int addUser(User user);
    @Update("UPDATE user set status = #{status} WHERE id=#{id}")
    int updateStatusById(int id, int status);
    @Update("UPDATE user set header_url = #{headerUrl} WHERE id=#{id}")
    int updateHeader(int id, String headerUrl);

    @Update("UPDATE user set password = #{password} WHERE id=#{id}")
    int updatePassword(int id, String password);

}
