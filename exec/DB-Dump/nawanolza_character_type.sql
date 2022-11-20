CREATE DATABASE  IF NOT EXISTS `nawanolza` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `nawanolza`;
-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: k7d103.p.ssafy.io    Database: nawanolza
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `character_type`
--

DROP TABLE IF EXISTS `character_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `character_type` (
  `character_type_id` bigint NOT NULL AUTO_INCREMENT,
  `character_id` bigint NOT NULL,
  `type_id` bigint NOT NULL,
  PRIMARY KEY (`character_type_id`),
  KEY `histories_fk_characters` (`character_id`),
  KEY `histories_fk_types` (`type_id`),
  CONSTRAINT `histories_fk_characters` FOREIGN KEY (`character_id`) REFERENCES `characters` (`character_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `histories_fk_types` FOREIGN KEY (`type_id`) REFERENCES `types` (`type_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `character_type`
--

LOCK TABLES `character_type` WRITE;
/*!40000 ALTER TABLE `character_type` DISABLE KEYS */;
INSERT INTO `character_type` VALUES (48,1,2),(49,2,2),(50,2,3),(51,3,3),(52,4,3),(53,5,1),(54,5,3),(55,6,3),(56,7,4),(57,8,1),(58,8,2),(59,8,4),(60,9,3),(61,10,4),(62,11,2),(63,12,1),(64,12,2),(65,13,2),(66,13,3),(67,14,3),(68,15,1),(69,15,3),(70,16,1),(71,16,4),(72,17,2),(73,17,3),(74,18,2),(75,19,1),(76,19,2),(77,20,1),(78,20,2),(79,21,1),(80,21,3),(81,22,1),(82,22,3),(83,23,2),(84,24,3),(85,25,1),(86,25,4),(87,26,2),(88,27,1),(89,27,4),(90,28,2),(91,28,3),(92,29,3),(93,30,1),(94,30,4);
/*!40000 ALTER TABLE `character_type` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-20 22:49:53
