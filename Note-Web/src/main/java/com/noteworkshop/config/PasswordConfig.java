package com.noteworkshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 密码加密配置
 *
 * BCrypt 的特点：
 *   1. 单向哈希，无法反推明文
 *   2. 每次加密结果不同（自带随机盐），但 matches() 能验证
 *   3. 计算慢，抗暴力破解
 */
@Configuration
public class PasswordConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
