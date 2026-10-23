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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='评论表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,1,3,0,'这篇写得不错','2026-10-23 09:48:27',0),(2,1,1,1,'我也觉得','2026-10-23 09:48:27',0),(3,1,2,2,'同意楼上','2026-10-23 09:48:27',0),(4,1,1,0,'有个问题想问','2026-10-23 09:48:27',0),(5,1,3,4,'你说','2026-10-23 09:48:27',0);
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
  `title` varchar(100) NOT NULL COMMENT '标题',
  `content` text COMMENT '正文内容',
  `is_public` tinyint NOT NULL DEFAULT '0' COMMENT '0私有 1公开',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除 0未删 1已删',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='笔记表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `note`
--

LOCK TABLES `note` WRITE;
/*!40000 ALTER TABLE `note` DISABLE KEYS */;
INSERT INTO `note` VALUES (1,2,'我的第一篇笔记','今天开始学 Spring Boot，感觉还行。',1,'2026-10-23 09:48:11','2026-10-23 09:48:11',0),(2,2,'一条大肥鱼','大肥鱼好玩',1,'2026-10-23 09:48:11','2026-10-23 21:13:47',0),(3,3,'李四的私有笔记','这条只有我自己能看到。',0,'2026-10-23 09:48:11','2026-10-23 09:48:11',0),(4,4,'Spring Boot 第一个项目','从建项目到跑通第一个接口，花了三个小时，终于看到 JSON 了。',1,'2026-10-23 21:04:33','2026-10-23 21:04:33',0),(5,5,'数据库设计的一点体会','先想清楚表和表的关系，再动手建表，能少改很多次。',1,'2026-10-23 21:04:33','2026-10-23 21:04:33',0),(6,6,'如何成为原神大佬教程','略',1,'2026-10-23 21:04:33','2026-10-23 21:13:47',0);
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','123456','管理员',NULL,1,'2026-10-23 09:47:56','2026-10-23 21:04:24',0),(2,'zhangsan','123456','张三',NULL,0,'2026-10-23 09:47:56','2026-10-23 09:47:56',0),(3,'lisi','123456','李四',NULL,0,'2026-10-23 09:47:56','2026-10-23 09:47:56',0),(4,'wangwu','123456','王五',NULL,0,'2026-10-23 21:04:24','2026-10-23 21:04:33',0),(5,'zhaoliu','123456','赵六',NULL,0,'2026-10-23 21:04:24','2026-10-23 21:04:33',0),(6,'sunqi','123456','孙七',NULL,0,'2026-10-23 21:04:24','2026-10-23 21:04:33',0);
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

-- Dump completed on 2026-10-23 22:07:38
