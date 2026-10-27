# 笔记工坊 Note Workshop

基于 Spring Boot + MyBatis + MySQL 的个人笔记管理系统。
前后端一体部署（前端页面放在 Spring Boot 的 static 目录中，无需单独启动前端服务）。

## 项目状态

| 模块 | 状态 |
|---|---|
| 用户注册 / 登录 | ✅ 已完成 |
| **JWT 鉴权 + 接口拦截** | ✅ 已完成 |
| **密码 BCrypt 加密** | ✅ 已完成 |
| 笔记 CRUD（列表 / 详情 / 新增 / 修改 / 删除） | ✅ 已完成 |
| 笔记必须指定图书名 | ✅ 已完成 |
| 私有 / 公开可见性 | ✅ 已完成 |
| 只有作者本人可改 / 删 | ✅ 已完成 |
| **我的笔记（分页）** | ✅ 已完成 |
| 关键词搜索 | ✅ 已完成 |
| 评论（含多级回复） | ✅ 已完成 |
| **点赞 / 取消点赞** | ✅ 已完成 |
| **热门榜（按点赞数排名，分页）** | ✅ 已完成 |
| **我赞过的笔记（分页）** | ✅ 已完成 |
| 前端页面（9 个） | ✅ 已完成 |
| 笔记配图（图片上传） | 🚧 开发中 |
| 管理员后台 | 🚧 开发中 |

## 技术栈

| 技术 | 版本 | 用途 |
|---|---|---|
| Java | 17 | 开发语言 |
| Spring Boot | 4.0.8 | 后端框架 |
| MyBatis | 3.x | 持久层（注解开发） |
| MySQL | 8.0.30 | 数据库 |
| **JJWT** | 0.12.6 | JWT 生成与校验 |
| **Spring Security Crypto** | - | BCrypt 密码加密 |
| Lombok | - | 简化实体类（@Data / @Slf4j） |
| Maven | 3.9 | 构建工具 |

## 功能说明

### 用户模块

- **注册**：用户名查重，密码至少 6 位，注册成功直接返回 token
- **登录**：BCrypt 校验密码，成功后返回 JWT token
- **JWT 鉴权**：登录后写接口需在请求头带 `Authorization: Bearer <token>`
- **密码加密**：BCrypt 哈希存储（自带随机盐），数据库不存明文

### 笔记模块

- **大厅列表**：只显示公开且未删除的笔记，按创建时间倒序
- **关键词搜索**：按标题或内容模糊匹配（`like '%kw%'`）
- **笔记详情**：返回正文、图书名、作者、时间、点赞数
- **发布笔记**：必须填写图书名；可设公开 / 私有
- **修改 / 删除**：逻辑删除（`deleted = 1`），仅作者本人可操作
- **我的笔记**：含私有笔记，分页展示

### 评论模块

- **评论列表**：LEFT JOIN `user` 表取显示名
- **发表评论**：内容非空、不超过 500 字
- **多级回复**：`parent_id` 自关联，`0` 表示顶级评论

### 点赞模块

- **点赞 / 取消**：同一接口切换（`/like/toggle`），返回当前状态
- **防重复点赞**：数据库 `UNIQUE KEY (note_id, user_id)` 约束
- **点赞数**：笔记查询通过子查询统计
- **热门榜**：按点赞数排名，点赞数相同按时间兜底，分页
- **我赞过的**：按点赞时间倒序，分页

## 接口说明

### 用户接口

| 方法 | 路径 | 说明 | 需登录 |
|---|---|---|---|
| POST | /user/register | 注册 | ❌ |
| POST | /user/login | 登录（返回 token） | ❌ |

### 笔记接口

| 方法 | 路径 | 说明 | 需登录 |
|---|---|---|---|
| GET | /note/list | 公开笔记列表 | ❌ |
| GET | /note/detail/{id} | 笔记详情 | ❌ |
| GET | /note/search?keyword= | 关键词搜索 | ❌ |
| GET | /note/hot?page=&size= | 热门榜（按点赞数排名） | ❌ |
| POST | /note/add | 新增笔记 | ✅ |
| PUT | /note/update | 修改笔记（仅作者） | ✅ |
| DELETE | /note/delete?id= | 逻辑删除（仅作者） | ✅ |
| GET | /note/my?page=&size= | 我的笔记（含私有，分页） | ✅ |

### 评论接口

| 方法 | 路径 | 说明 | 需登录 |
|---|---|---|---|
| GET | /comment/list?noteId= | 某篇笔记的评论 | ❌ |
| POST | /comment/add | 发表评论 | ✅ |

### 点赞接口

| 方法 | 路径 | 说明 | 需登录 |
|---|---|---|---|
| POST | /like/toggle?noteId= | 点赞 / 取消（切换） | ✅ |
| GET | /like/my?page=&size= | 我点赞过的笔记（分页） | ✅ |

### 请求示例

**登录（返回 token）：**

```http
POST http://localhost:8080/user/login
Content-Type: application/json

{ "username": "admin", "password": "123456" }
```

**带 token 新增笔记：**

```http
POST http://localhost:8080/note/add
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...

{
  "bookName": "深入理解 Java 虚拟机",
  "title": "JVM 内存区域划分",
  "content": "堆、虚拟机栈、本地方法栈...",
  "isPublic": 1
}
```

> 注意：请求体里的 `userId` 会被后端忽略，实际作者从 token 解析，防止冒充。

**点赞 / 取消：**

```http
POST http://localhost:8080/like/toggle?noteId=1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

返回 `{ "liked": true }` 表示刚点赞，`{ "liked": false }` 表示刚取消。

更多接口测试见仓库根目录的 `test.http` 文件（IDEA HTTP Client 格式，可直接点击运行）。

## 数据库设计

| 表名 | 说明 | 关键字段 |
|---|---|---|
| `user` | 用户表 | `username` 唯一索引；密码存 BCrypt 哈希 |
| `note` | 笔记表 | `user_id` 关联作者、`book_name` 图书名、`is_public` 可见性 |
| `note_image` | 笔记图片表 | `note_id` 关联笔记、`url` 图片地址、`sort` 排序 |
| `comment` | 评论表 | `parent_id` 自关联，支持多级回复 |
| `note_like` | 点赞表 | `UNIQUE KEY (note_id, user_id)` 防重复点赞 |

### 设计说明

- **逻辑删除**：`note`、`comment`、`user` 三张表有 `deleted` 字段，
  删除只置 1，查询统一加 `where deleted = 0` 过滤。
- **点赞用物理删除**：取消点赞直接 `DELETE`，因为点赞可随时重做，
  没有「恢复」需求，也不必长期保留历史记录。这是和笔记（逻辑删除）不同的取舍。
- **防重复点赞**：`note_like` 建 `(note_id, user_id)` 联合唯一索引，
  从数据库层面保证一个用户对一篇笔记只能点一次赞，不依赖代码判断。
- **自关联**：`comment.parent_id` 指向本表 `id`，`0` 表示顶级评论。
- **点赞数统计**：笔记查询带子查询 `(select count(*) from note_like where note_id = n.id) as likeCount`，
  映射到 `Note.likeCount`（非数据库列）。
- **索引**：`note` 对 `user_id`、`create_time` 建索引；
  `note_like` 对 `user_id` 建索引（`where user_id = ?` 查我赞过的）。
- **时间字段**：由数据库 `DEFAULT CURRENT_TIMESTAMP` 与 `ON UPDATE CURRENT_TIMESTAMP` 自动维护。

建表脚本见 `db/init.sql`（含建库、5 张表结构、测试数据）。

## 项目结构

```
Note-Workshop/
├─ db/
│   └─ init.sql                       建库建表 + 测试数据
├─ Note-Web/
│   ├─ pom.xml
│   └─ src/main/
│       ├─ java/com/noteworkshop/
│       │   ├─ controller/            控制层：接收请求、返回 JSON
│       │   ├─ service/               业务层：接口
│       │   │   └─ impl/              业务层：实现（校验、分页）
│       │   ├─ mapper/                数据访问层：MyBatis 注解 SQL
│       │   ├─ pojo/                  实体类：对应数据库表
│       │   ├─ interceptor/           登录拦截器（校验 JWT）
│       │   ├─ config/                配置（BCrypt Bean、拦截器注册、密码迁移）
│       │   ├─ utils/                 JWT 工具类
│       │   └─ NoteWebApplication     启动类
│       └─ resources/
│           ├─ static/                前端页面（HTML + 原生 JS）
│           └─ application.properties
├─ test.http                          接口测试文件
└─ README.md
```

## 分层架构

```
前端（浏览器 / static 下的 HTML+JS）
    ↓ HTTP（JWT 放在 Authorization 头）
LoginInterceptor                    校验 token，解析出 userId
    ↓
Controller                          接收请求、取 token 里的 userId、返回 JSON
    ↓
Service（接口 + impl）                业务校验、分页计算、权限判断
    ↓
Mapper（MyBatis 注解）                只写 SQL，不含业务逻辑
    ↓
MySQL
```

## 前端页面

| 页面 | 访问路径 | 功能 |
|---|---|---|
| 大厅 | / | 笔记列表、搜索、点赞、登录状态 |
| 登录 | /login.html | 账号密码登录 |
| 注册 | /register.html | 注册新账号 |
| 写笔记 | /add.html | 发布新笔记 |
| 详情 | /detail.html?id= | 查看笔记、点赞、评论 |
| 编辑 | /edit.html?id= | 修改自己的笔记 |
| 我的笔记 | /my.html | 我的全部笔记（含私有，分页） |
| 热门榜 | /hot.html | 按点赞数排名（分页） |
| 我赞过的 | /liked.html | 我点赞过的笔记（分页） |

前端未使用任何框架，采用原生 JavaScript + `fetch` 调用接口。
公共逻辑（登录状态、token 管理、请求封装、401 自动跳登录）统一放在 `static/common.js`。

## 启动步骤

> 以下命令均在**仓库根目录**执行。

### 1. 建库建表

```bash
mysql -u root -p < db/init.sql
```

### 2. 修改数据库密码

打开 `Note-Web/src/main/resources/application.properties`，把密码改成你自己的：

```properties
spring.datasource.password=你的数据库密码
```

### 3. 启动项目

运行 `Note-Web/src/main/java/com/noteworkshop/NoteWebApplication.java` 的 main 方法，或：

```bash
cd Note-Web
mvn spring-boot:run
```

### 4. 访问

```
http://localhost:8080/
```

## 测试账号

| 账号 | 密码 | 角色 |
|---|---|---|
| admin | 123456 | 管理员（role=1） |
| zhangsan | 123456 | 普通用户 |
| lisi | 123456 | 普通用户 |

> 数据库中密码以 BCrypt 哈希存储。首次启动时 `PasswordMigrator` 会把历史明文密码自动迁移成哈希。

## 说明

- 数据库密码直接写在 `application.properties` 便于演示；生产环境应改为环境变量或配置中心。
- JWT 签名密钥当前写在 `JwtUtil` 中（代码有注释说明）；生产环境应从环境变量注入。
- 笔记配图设计为 `note_image` 表存 URL，图片本体不入库；
  若需支持本地照片上传，新增一个 `MultipartFile` 上传接口即可，表结构无需改动。
- 管理员后台（审核、下架、AI 统计）为进阶功能，暂未实现。