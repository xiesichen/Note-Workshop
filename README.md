# 笔记工坊 Note Workshop

基于 Spring Boot + MyBatis + MySQL 的个人笔记管理系统。
前后端一体部署（前端页面放在 Spring Boot 的 static 目录中，无需单独启动前端服务）。

## 项目状态

| 模块 | 状态 |
|---|---|
| 笔记 CRUD（列表 / 详情 / 新增 / 修改 / 删除） | ✅ 已完成 |
| 用户登录 | ✅ 已完成 |
| 前端页面（大厅 / 登录 / 写笔记 / 详情） | ✅ 已完成 |
| 笔记编辑页 | 🚧 开发中 |
| 评论功能 | 🚧 开发中 |
| 笔记图片 | 🚧 开发中 |
| Token 登录鉴权 | 🚧 开发中 |

## 技术栈

| 技术 | 版本 | 用途 |
|---|---|---|
| Java | 17 | 开发语言 |
| Spring Boot | 4.0.8 | 后端框架 |
| MyBatis | 3.x | 持久层（注解开发） |
| MySQL | 8.0.30 | 数据库 |
| Lombok | - | 简化实体类（@Data / @Slf4j） |
| Maven | 3.9 | 构建工具 |

## 功能说明

### 已实现

- **用户登录**：校验账号密码，登录状态保存在浏览器 localStorage
- **笔记列表**：只显示公开笔记，按创建时间倒序
- **笔记详情**：查看单条笔记的完整内容
- **发布笔记**：填写标题、内容、可见性（公开 / 私有）
- **修改笔记**：修改标题、内容、可见性
- **删除笔记**：逻辑删除（数据保留，仅标记为已删除）
- **权限控制**：只有作者本人能看到「编辑 / 删除」按钮
- **操作日志**：使用 SLF4J + Logback 记录关键业务操作

### 开发中

- 笔记编辑页（后端 PUT 接口已完成，前端页面待补）
- 评论功能（后端表已设计，接口待开发）
- 笔记图片
- Token 登录鉴权（当前为简化版，后端未校验登录状态）

## 接口说明

### 笔记接口

| 方法 | 路径 | 说明 | 状态 |
|---|---|---|---|
| GET | /note/list | 查询公开笔记列表 | ✅ |
| GET | /note/detail/{id} | 查询笔记详情 | ✅ |
| POST | /note/add | 新增笔记 | ✅ |
| PUT | /note/update | 修改笔记 | ✅ |
| DELETE | /note/delete?id= | 逻辑删除笔记 | ✅ |

### 用户接口

| 方法 | 路径 | 说明 | 状态 |
|---|---|---|---|
| POST | /user/login | 用户登录 | ✅ |

### 请求示例

新增笔记：

```http
POST http://localhost:8080/note/add
Content-Type: application/json

{
  "userId": 2,
  "title": "我的第一篇笔记",
  "content": "今天开始学 Spring Boot",
  "isPublic": 1
}
```

登录：

```http
POST http://localhost:8080/user/login
Content-Type: application/json

{ "username": "admin", "password": "123456" }
```

更多接口测试见仓库根目录的 `test.http` 文件（IDEA HTTP Client 格式，可直接点击运行）。

## 数据库设计

| 表名 | 说明 | 关键字段 |
|---|---|---|
| `user` | 用户表 | `username` 唯一索引 |
| `note` | 笔记表 | `user_id` 关联作者，`is_public` 控制可见性 |
| `note_image` | 笔记图片表 | `note_id` 关联笔记，`sort` 排序 |
| `comment` | 评论表 | `parent_id` 自关联，支持多级回复 |

### 设计说明

- **逻辑删除**：`note`、`comment`、`user` 三张表都有 `deleted` 字段，
  删除时只把 `deleted` 置 1，数据保留；查询时统一加 `where deleted = 0` 过滤。
- **表关系**：`user 1:N note`、`note 1:N note_image`、`note 1:N comment`。
- **自关联**：`comment.parent_id` 指向本表的 `id`，`0` 表示顶级评论，实现评论回复。
- **索引**：`note` 表对 `user_id`、`create_time` 建立了普通索引，提升列表查询效率。
- **时间字段**：由数据库 `DEFAULT CURRENT_TIMESTAMP` 与 `ON UPDATE CURRENT_TIMESTAMP` 自动维护。

建表脚本见 `db/init.sql`（含建库、建表、测试数据）。

## 项目结构

```
Note-Workshop/
├─ db/
│   └─ init.sql                     建库建表 + 测试数据
├─ Note-Web/
│   ├─ pom.xml
│   └─ src/main/
│       ├─ java/com/noteworkshop/
│       │   ├─ controller/          控制层：接收请求、返回 JSON
│       │   ├─ service/             业务层：接口定义
│       │   │   └─ impl/            业务层：实现类
│       │   ├─ mapper/              数据访问层：MyBatis 注解
│       │   ├─ pojo/                实体类：对应数据库表
│       │   └─ NoteWebApplication   启动类
│       └─ resources/
│           ├─ static/              前端页面（HTML + 原生 JS）
│           └─ application.properties
├─ test.http                        接口测试文件
└─ README.md
```

## 前端页面

| 页面 | 访问路径 | 功能 |
|---|---|---|
| 大厅 | / | 笔记列表、登录状态显示、写笔记入口 |
| 登录 | /login.html | 账号密码登录 |
| 写笔记 | /add.html | 发布新笔记 |
| 详情 | /detail.html?id= | 查看笔记、删除笔记 |

前端未使用任何框架，采用原生 JavaScript + `fetch` 调用后端接口，
公共函数（登录状态管理、请求封装）统一放在 `static/common.js`。

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

运行 `Note-Web/src/main/java/com/noteworkshop/NoteWebApplication.java` 的 main 方法，
或使用命令：

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
| admin | 123456 | 管理员 |
| zhangsan | 123456 | 普通用户 |
| lisi | 123456 | 普通用户 |

## 说明

- 数据库密码等敏感配置直接写在 `application.properties` 中，便于演示；
  生产环境应改为环境变量或配置中心管理。
- 当前密码为明文存储与比对，生产环境应使用 BCrypt 等加密方式。
- 当前接口未做登录校验（任何人可直接调用写接口），后续将补充 Token 鉴权。
