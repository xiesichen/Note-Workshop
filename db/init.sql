-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: noteworkshop
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `noteworkshop`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `noteworkshop` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `noteworkshop`;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `note_id` int NOT NULL COMMENT '所属笔记id，关联 note.id',
  `user_id` int NOT NULL COMMENT '评论人id，关联 user.id',
  `parent_id` int NOT NULL DEFAULT '0' COMMENT '父评论id，0表示顶级评论',
  `content` varchar(500) NOT NULL COMMENT '评论内容',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除 0未删 1已删',
  PRIMARY KEY (`id`),
  KEY `idx_note_id` (`note_id`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='评论表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,1,3,0,'这篇写得不错','2026-10-23 09:48:27',0),(2,1,1,1,'我也觉得','2026-10-23 09:48:27',0),(3,1,2,2,'同意楼上','2026-10-23 09:48:27',0),(4,1,1,0,'有个问题想问','2026-10-23 09:48:27',0),(5,1,3,4,'你说','2026-10-23 09:48:27',0),(6,1,2,1,'我在回复第一条','2026-10-26 20:46:13',0),(7,1,1,0,'接口测试评论','2026-10-26 20:47:13',0),(8,1,2,1,'这是接口发的回复','2026-10-26 20:47:13',0);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `note`
--

DROP TABLE IF EXISTS `note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `note` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '笔记id',
  `user_id` int NOT NULL COMMENT '作者id，关联 user.id',
  `book_name` varchar(100) DEFAULT NULL COMMENT '图书名称',
  `title` varchar(100) NOT NULL COMMENT '标题',
  `content` text COMMENT '正文内容',
  `is_public` tinyint NOT NULL DEFAULT '0' COMMENT '0私有 1公开',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除 0未删 1已删',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='笔记表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `note`
--

LOCK TABLES `note` WRITE;
/*!40000 ALTER TABLE `note` DISABLE KEYS */;
INSERT INTO `note` VALUES (1,2,'Spring Boot 实战','我的第一篇笔记','今天开始学 Spring Boot，感觉还行。',1,'2026-10-23 09:48:11','2026-10-27 12:15:59',0),(2,2,'机械制图','一条大肥鱼','大肥鱼好玩',1,'2026-10-27 21:10:00','2026-10-27 20:50:45',0),(3,3,'机械制图','李四的私有笔记','这条只有我自己能看到。',0,'2026-10-23 09:48:11','2026-10-27 12:15:59',0),(4,4,'Spring Boot 实战','Spring Boot 第一个项目','从建项目到跑通第一个接口，花了三个小时，终于看到 JSON 了。',1,'2026-10-23 21:04:33','2026-10-27 12:15:59',0),(5,5,'数据库系统概念','数据库设计的一点体会','先想清楚表和表的关系，再动手建表，能少改很多次。',1,'2026-10-23 21:04:33','2026-10-27 12:15:59',0),(6,6,'原神攻略大全','如何成为原神大佬教程','略',1,'2026-10-27 21:09:00','2026-10-27 20:50:45',0),(7,2,'Spring Boot 实战','通过接口插入的笔记','这是测试内容',1,'2026-10-24 15:13:49','2026-10-27 12:15:59',0),(8,1,'未分类','越努力 越幸运','啊吧啊吧',1,'2026-10-25 16:15:36','2026-10-27 21:14:53',0),(9,2,'未分类','通过接口插入的笔记','这是测试内容',1,'2026-10-26 20:45:52','2026-10-27 21:14:53',0),(11,1,'TestBook2','ModifiedByAdmin','y',1,'2026-10-27 18:58:14','2026-10-27 18:58:26',1),(12,1,'深入理解 Java 虚拟机','JVM 内存区域划分','堆、虚拟机栈、本地方法栈、方法区、程序计数器。其中堆和方法区是线程共享的，其余是线程私有的。',1,'2026-10-27 20:47:52','2026-10-27 20:47:52',0),(13,1,'深入理解 Java 虚拟机','垃圾回收算法笔记','标记清除会产生碎片；复制算法适合新生代；标记整理适合老年代。CMS 关注停顿时间，G1 兼顾吞吐和停顿。',1,'2026-10-27 20:47:52','2026-10-27 20:47:52',0),(14,1,'MySQL 必知必会','索引为什么用 B+ 树','B+ 树非叶子节点只存键，能放更多索引项，树更矮；叶子节点用链表相连，范围查询快。',1,'2026-10-27 20:47:52','2026-10-27 20:47:52',0),(15,1,'MySQL 必知必会','事务隔离级别','读未提交、读已提交、可重复读、串行化。MySQL 默认是可重复读，靠 MVCC 实现，能避免脏读和不可重复读。',0,'2026-10-27 20:47:52','2026-10-27 20:47:52',0),(16,1,'代码整洁之道','命名是最难的事','变量名要能表达意图，不要用 a、b、temp。函数要短，只做一件事。注释不能弥补糟糕的代码。',1,'2026-10-27 20:47:52','2026-10-27 20:47:52',0),(17,1,'代码整洁之道','关于注释的取舍','好代码不需要太多注释。但「为什么这么写」比「这行在干什么」更值得写进注释。',0,'2026-10-27 20:47:52','2026-10-27 20:47:52',0);
/*!40000 ALTER TABLE `note` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `note_image`
--

DROP TABLE IF EXISTS `note_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `note_image` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '图片id',
  `note_id` int NOT NULL COMMENT '所属笔记id，关联 note.id',
  `url` varchar(255) NOT NULL COMMENT '图片地址',
  `sort` int NOT NULL DEFAULT '0' COMMENT '排序，越小越靠前',
  PRIMARY KEY (`id`),
  KEY `idx_note_id` (`note_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='笔记图片表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `note_image`
--

LOCK TABLES `note_image` WRITE;
/*!40000 ALTER TABLE `note_image` DISABLE KEYS */;
INSERT INTO `note_image` VALUES (1,1,'https://picsum.photos/400/300?random=1',0),(2,1,'https://picsum.photos/400/300?random=2',1),(3,2,'https://picsum.photos/400/300?random=3',0);
/*!40000 ALTER TABLE `note_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `note_like`
--

DROP TABLE IF EXISTS `note_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `note_like` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `note_id` int NOT NULL COMMENT '被点赞的笔记',
  `user_id` int NOT NULL COMMENT '点赞的用户',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '点赞时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_note_user` (`note_id`,`user_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='点赞表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `note_like`
--

LOCK TABLES `note_like` WRITE;
/*!40000 ALTER TABLE `note_like` DISABLE KEYS */;
INSERT INTO `note_like` VALUES (2,1,1,'2026-10-27 21:05:47'),(3,2,1,'2026-10-27 21:05:47'),(4,6,1,'2026-10-27 21:05:47'),(5,5,1,'2026-10-27 21:07:25'),(7,4,1,'2026-10-27 21:07:28');
/*!40000 ALTER TABLE `note_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(50) NOT NULL COMMENT '登录账号',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像url',
  `role` tinyint NOT NULL DEFAULT '0' COMMENT '0普通用户 1管理员',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除 0未删 1已删',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','$2a$10$flna5rup9lFx4dXPx9le9.x..96pfGm1hF4Csd57IG2/uSz1NcHDy','管理员',NULL,1,'2026-10-23 09:47:56','2026-10-27 12:18:39',0),(2,'zhangsan','$2a$10$SjI.3oD.dzNB6lCYFsDbWOA6snLv3I50GdW3Yeu.QzomFc/1VQ9Cy','张三',NULL,0,'2026-10-23 09:47:56','2026-10-27 12:18:40',0),(3,'lisi','$2a$10$O0ebzP04MXGQEgA45b4JUOMLCia8Ioq/yg0qT1wZks/m0P3zcXDZm','李四',NULL,0,'2026-10-23 09:47:56','2026-10-27 12:18:40',0),(4,'wangwu','$2a$10$nKKFUmV7SGmBayD1sp6tm.qubxuSg6uW1QGdi3T4LmZs/Dz.3dMau','王五',NULL,0,'2026-10-23 21:04:24','2026-10-27 12:18:40',0),(5,'zhaoliu','$2a$10$742Wz4p8vcyFn9CyKeTA/uHdVnAz98YQnv4MmijoEII3zuP7OGVGe','赵六',NULL,0,'2026-10-23 21:04:24','2026-10-27 12:18:40',0),(6,'sunqi','$2a$10$T40azBrzgQx9NINcWVzP0uLX2m..Mw6CIrKAgKzBrrUscGLrEmoXG','孙七',NULL,0,'2026-10-23 21:04:24','2026-10-27 12:18:40',0),(7,'testuser','$2a$10$QvqW8bVjfc4q6Ate5ZuRk.oEcMY4WcZ1syDXczNQSnF6Mp06Aan6.','????',NULL,0,'2026-10-27 12:18:47','2026-10-27 12:18:47',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-10-27 21:14:54
