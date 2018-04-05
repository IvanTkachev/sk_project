-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: sk_database
-- ------------------------------------------------------
-- Server version	5.7.21-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `linker`
--

DROP TABLE IF EXISTS `linker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `linker` (
  `id_link` int(11) NOT NULL AUTO_INCREMENT,
  `id_product` int(11) NOT NULL,
  `id_store` int(11) NOT NULL,
  `count_product` int(11) NOT NULL,
  PRIMARY KEY (`id_link`),
  UNIQUE KEY `idlink_UNIQUE` (`id_link`),
  KEY `fk_product_id_idx` (`id_product`),
  KEY `fk_store_id_idx` (`id_store`),
  CONSTRAINT `fk_product_id` FOREIGN KEY (`id_product`) REFERENCES `products` (`id_product`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_store_id` FOREIGN KEY (`id_store`) REFERENCES `store` (`id_store`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `linker`
--

LOCK TABLES `linker` WRITE;
/*!40000 ALTER TABLE `linker` DISABLE KEYS */;
INSERT INTO `linker` VALUES (2,2,1,122),(3,3,1,122),(4,3,2,12),(5,4,1,122),(8,8,1,122),(9,5,3,123),(10,2,3,123),(11,5,1,122),(15,8,3,124),(16,11,1,122),(18,13,1,123453),(19,14,1,122),(20,15,1,122),(27,22,1,122),(33,28,1,122),(34,29,1,122),(44,39,1,2),(47,42,1,3),(48,43,3,3),(49,44,2,23),(50,45,16,123);
/*!40000 ALTER TABLE `linker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `products` (
  `id_product` int(11) NOT NULL AUTO_INCREMENT,
  `name_product` varchar(45) NOT NULL,
  PRIMARY KEY (`id_product`),
  UNIQUE KEY `id_product_UNIQUE` (`id_product`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Лепс1'),(2,'Банан'),(3,'Киви'),(4,'Селедка'),(5,'Григорий'),(6,'Гриша1'),(8,'Гриша'),(11,'asdsad'),(13,'asd123'),(14,'Ivan'),(15,'Вася2'),(22,'Виталя'),(28,'Никита'),(29,'Орешник'),(39,'DS'),(42,'pomidor'),(43,'Огурец'),(44,'dfsad'),(45,'Вася');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `store`
--

DROP TABLE IF EXISTS `store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `store` (
  `id_store` int(11) NOT NULL AUTO_INCREMENT,
  `name_store` varchar(45) NOT NULL,
  PRIMARY KEY (`id_store`),
  UNIQUE KEY `id_store_UNIQUE` (`id_store`),
  UNIQUE KEY `name_store_UNIQUE` (`name_store`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store`
--

LOCK TABLES `store` WRITE;
/*!40000 ALTER TABLE `store` DISABLE KEYS */;
INSERT INTO `store` VALUES (18,'BSU'),(1,'RootStore'),(16,'Store1'),(2,'Store22'),(3,'Store3');
/*!40000 ALTER TABLE `store` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-02  9:58:27
