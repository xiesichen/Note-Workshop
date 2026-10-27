package com.noteworkshop.mapper;

import com.noteworkshop.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    /** 按用户名查（登录 / 注册查重） */
    @Select("select * from user where username = #{username} and deleted = 0")
    User findByUsername(String username);

    /** 新增用户（密码由 Service 加密后传入） */
    @Insert("insert into user (username, password, nickname, role) " +
            "values (#{username}, #{password}, #{nickname}, #{role})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    /** 查询全部用户（只用于启动时迁移明文密码） */
    @Select("select * from user where deleted = 0")
    List<User> findAll();

    /** 更新密码（只用于启动时迁移明文密码） */
    @Update("update user set password = #{password} where id = #{id}")
    int updatePassword(@Param("id") Integer id, @Param("password") String password);
}
