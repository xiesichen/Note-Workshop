# 笔记工坊 Note Workshop

基于 Spring Boot + MyBatis + MySQL 的个人笔记管理系统。

## 技术栈

- Java 17
- Spring Boot 4.0.8
- MyBatis
- MySQL 8.0

## 功能

- 笔记列表（只显示公开笔记）
- 用户登录

## 数据库

4 张表：`user`、`note`、`note_image`、`comment`

建表脚本见 `db/init.sql`。

## 启动步骤

1. 执行 `db/init.sql` 建库建表
2. 修改 `src/main/resources/application.properties` 里的数据库密码
3. 运行 `NoteWebApplication`
4. 访问 http://localhost:8080/

## 测试账号

- 账号：`admin`
- 密码：`123456`