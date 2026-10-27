package com.noteworkshop.service.impl;

import com.noteworkshop.mapper.UserMapper;
import com.noteworkshop.pojo.User;
import com.noteworkshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;      // BCrypt

    @Override
    public User login(String username, String password) {
        if (username == null || username.trim().isEmpty()
                || password == null || password.isEmpty()) {
            return null;
        }

        User user = userMapper.findByUsername(username);
        if (user == null) {
            return null;
        }

        // ★ BCrypt 校验：matches(明文, 数据库里的哈希)
        //   不能用 equals 比较，因为哈希每次都不一样
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return null;
        }

        user.setPassword(null);      // 不把密码返回给前端
        return user;
    }

    @Override
    public User register(String username, String password, String nickname) {
        // ① 参数校验
        if (username == null || username.trim().isEmpty()) {
            throw new RuntimeException("用户名不能为空");
        }
        if (password == null || password.length() < 6) {
            throw new RuntimeException("密码至少 6 位");
        }
        if (username.length() < 3 || username.length() > 50) {
            throw new RuntimeException("用户名长度需在 3~50 位之间");
        }

        // ② 查重
        if (userMapper.findByUsername(username) != null) {
            throw new RuntimeException("用户名已存在");
        }

        // ③ 组装
        User user = new User();
        user.setUsername(username.trim());
        user.setPassword(passwordEncoder.encode(password));   // ★ BCrypt 加密
        user.setNickname((nickname == null || nickname.trim().isEmpty())
                ? username.trim() : nickname.trim());
        user.setRole(0);       // 0 = 普通用户

        userMapper.insert(user);

        user.setPassword(null);
        return user;
    }
}
