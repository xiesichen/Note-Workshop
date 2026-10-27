package com.noteworkshop.controller;

import com.noteworkshop.pojo.User;
import com.noteworkshop.service.UserService;
import com.noteworkshop.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 登录
     * POST /user/login   {"username":"admin","password":"123456"}
     *
     * 返回：{"token":"eyJhbG...", "user":{"id":1,"username":"admin",...}}
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> body) {
        User user = userService.login(body.get("username"), body.get("password"));

        if (user == null) {
            log.info("登录失败，username={}", body.get("username"));
            return ResponseEntity.status(401).build();
        }

        // 生成 token（payload 里带 userId、username）
        String token = jwtUtil.generate(user.getId(), user.getUsername());

        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", user);

        log.info("登录成功，username={}", user.getUsername());
        return ResponseEntity.ok(result);
    }

    /**
     * 注册
     * POST /user/register   {"username":"newuser","password":"123456","nickname":"新人"}
     *
     * 注册成功直接发 token，前端可以自动登录
     */
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> body) {
        log.info("注册请求，username={}", body.get("username"));

        User user = userService.register(
                body.get("username"),
                body.get("password"),
                body.get("nickname"));

        String token = jwtUtil.generate(user.getId(), user.getUsername());

        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", user);
        return result;
    }
}
