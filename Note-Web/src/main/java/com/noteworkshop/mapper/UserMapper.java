package com.noteworkshop.mapper;

import com.noteworkshop.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from user where username = #{username} and deleted = 0")
    User findByUsername(String username);
}