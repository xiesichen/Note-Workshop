package com.noteworkshop.config;

import com.noteworkshop.mapper.UserMapper;
import com.noteworkshop.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 启动时把数据库里的【明文密码】迁移成 BCrypt 哈希。
 *
 * 为什么需要：
 *   历史数据里的密码是明文（123456），改成 BCrypt 校验后就登不进去了。
 *   这个类在应用启动时检查一遍：凡是密码不以 "$2" 开头的（BCrypt 哈希都以 $2 开头），
 *   就加密后更新回数据库。
 *
 * 迁移完成后，所有密码都以 $2 开头，这段逻辑变成空操作，可以一直留着。
 */
@Slf4j
@Component
public class PasswordMigrator implements CommandLineRunner {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        List<User> users = userMapper.findAll();
        int migrated = 0;

        for (User u : users) {
            String pwd = u.getPassword();
            // BCrypt 哈希都以 $2a$ / $2b$ / $2y$ 开头
            if (pwd != null && !pwd.startsWith("$2")) {
                userMapper.updatePassword(u.getId(), passwordEncoder.encode(pwd));
                migrated++;
                log.warn("用户 {} 的明文密码已迁移为 BCrypt 哈希", u.getUsername());
            }
        }

        if (migrated > 0) {
            log.info("密码迁移完成，共处理 {} 个用户", migrated);
        }
    }
}
