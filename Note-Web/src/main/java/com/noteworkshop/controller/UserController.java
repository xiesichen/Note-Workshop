package com.noteworkshop.controller;

import com.noteworkshop.pojo.User;
import com.noteworkshop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     * POST /user/login   {"username":"admin","password":"123456"}
     */
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody Map<String, String> body) {
        User user = userService.login(body.get("username"), body.get("password"));
        if (user == null) {
            log.info("登录失败，username={}", body.get("username"));
            return ResponseEntity.status(401).build();
        }
        log.info("登录成功，username={}", user.getUsername());
        return ResponseEntity.ok(user);
    }

    /**
     * 注册
     * POST /user/register   {"username":"newuser","password":"123456","nickname":"新人"}
     */
    @PostMapping("/register")
    public User register(@RequestBody Map<String, String> body) {
        log.info("注册请求，username={}", body.get("username"));
        return userService.register(
                body.get("username"),
                body.get("password"),
                body.get("nickname"));
    }
}
