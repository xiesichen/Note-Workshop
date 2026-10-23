package com.noteworkshop.service.impl;

import com.noteworkshop.mapper.UserMapper;
import com.noteworkshop.pojo.User;
import com.noteworkshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        // ① 参数校验
        if (username == null || username.trim().isEmpty()
                || password == null || password.isEmpty()) {
            return null;
        }

        // ② 查用户
        User user = userMapper.findByUsername(username);
        if (user == null) {
            return null;
        }

        // ③ 比对密码
        if (!user.getPassword().equals(password)) {
            return null;
        }

        // ④ 不把密码返回给前端
        user.setPassword(null);

        return user;
    }
}
