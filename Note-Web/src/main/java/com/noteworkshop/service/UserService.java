package com.noteworkshop.service;

import com.noteworkshop.pojo.User;

public interface UserService {

    /**
     * 登录：成功返回用户（密码已清空），失败返回 null
     */
    User login(String username, String password);

    /**
     * 注册：成功返回新用户（密码已清空），失败抛异常
     */
    User register(String username, String password, String nickname);
}
