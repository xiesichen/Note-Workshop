package com.noteworkshop.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户实体类，对应数据库表 user
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer id;

    private String username;

    private String password;

    private String nickname;
    /** 头像url */
    private String avatar;

    /** 0普通用户 1管理员 */
    private Integer role;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /** 逻辑删除 0未删 1已删 */
    private Integer deleted;
}
