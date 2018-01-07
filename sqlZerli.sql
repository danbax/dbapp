CREATE DATABASE  IF NOT EXISTS `zerli` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `zerli`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: zerli
-- ------------------------------------------------------
-- Server version	5.7.20-log

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
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `city` varchar(45) DEFAULT NULL,
  `street` varchar(45) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,'Nahariya','vaizman',82,16);
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cart` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL,
  `shop_id` varchar(45) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (1,16,38,14,'0'),(6,16,37,14,'0'),(10,16,37,14,'0'),(11,16,38,14,'0'),(13,16,37,14,'0'),(14,16,38,14,'0'),(17,16,37,15,'0'),(18,16,38,15,'0'),(19,16,39,15,'0'),(20,16,35,18,'0'),(21,16,35,18,'0'),(22,16,37,18,'0'),(23,16,41,18,'0'),(24,16,41,18,'0'),(25,16,35,18,'0'),(26,16,35,18,'0'),(27,16,35,18,'0'),(28,16,37,18,'0'),(29,16,36,18,'0'),(30,16,35,18,'0'),(31,16,37,18,'0'),(32,16,36,18,'0'),(33,16,35,16,'1'),(34,16,40,16,'1'),(35,16,35,16,'1'),(36,16,35,0,'2'),(37,16,40,0,'2'),(38,16,38,0,'2'),(39,16,36,17,'1'),(40,16,37,17,'1'),(41,16,36,17,'1'),(42,16,37,17,'1'),(43,16,36,17,'1'),(44,16,37,17,'1'),(45,16,39,17,'1'),(46,16,35,17,'1'),(47,16,38,0,'0'),(48,16,35,19,'1'),(49,16,40,19,'1'),(50,16,35,19,'1'),(51,16,35,0,'1'),(52,16,40,0,'1'),(53,16,35,0,'1'),(54,16,35,0,'1');
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `complains`
--

DROP TABLE IF EXISTS `complains`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `complains` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `desc` varchar(1445) NOT NULL,
  `userID` int(11) DEFAULT '0',
  `compensation` float DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `shop_id` int(11) DEFAULT NULL,
  `compesation_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `complains`
--

LOCK TABLES `complains` WRITE;
/*!40000 ALTER TABLE `complains` DISABLE KEYS */;
INSERT INTO `complains` VALUES (2,'text text text text text',16,100,1,1,'2018-01-04'),(3,'bla bla bla bla bla bla bla bla',16,0,0,NULL,'2018-01-04'),(4,'asfasf',2,0,0,1,'2018-01-04'),(5,'go ',1,100,0,1,'2018-01-04'),(7,'aasfasfa',2,50,0,1,'2018-01-04');
/*!40000 ALTER TABLE `complains` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `credit_card`
--

DROP TABLE IF EXISTS `credit_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `credit_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `card_number` varchar(10) DEFAULT NULL,
  `expMonth` int(11) DEFAULT NULL,
  `expYear` int(11) DEFAULT NULL,
  `cvv` varchar(3) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credit_card`
--

LOCK TABLES `credit_card` WRITE;
/*!40000 ALTER TABLE `credit_card` DISABLE KEYS */;
INSERT INTO `credit_card` VALUES (1,'543543453',12,2017,'277',1),(2,'3134435748',7,2016,'222',16),(4,'12412412',13,2016,'555',13);
/*!40000 ALTER TABLE `credit_card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deals`
--

DROP TABLE IF EXISTS `deals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deals` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) DEFAULT NULL,
  `percent` int(11) DEFAULT NULL,
  `shop_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deals`
--

LOCK TABLES `deals` WRITE;
/*!40000 ALTER TABLE `deals` DISABLE KEYS */;
INSERT INTO `deals` VALUES (1,38,24,0),(3,38,29,0),(4,39,50,0),(5,36,40,0),(7,34,24,0),(8,35,45,0);
/*!40000 ALTER TABLE `deals` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expert_conclusion`
--

DROP TABLE IF EXISTS `expert_conclusion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `expert_conclusion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `survey_id` varchar(45) DEFAULT NULL,
  `conclusion` varchar(1500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expert_conclusion`
--

LOCK TABLES `expert_conclusion` WRITE;
/*!40000 ALTER TABLE `expert_conclusion` DISABLE KEYS */;
INSERT INTO `expert_conclusion` VALUES (1,'1','conclusion text conclusion text conclusion text conclusion text'),(2,'9','FA');
/*!40000 ALTER TABLE `expert_conclusion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` varchar(45) DEFAULT NULL,
  `user_id` varchar(45) DEFAULT NULL,
  `order_date` date DEFAULT NULL,
  `greeting_text` varchar(1500) DEFAULT NULL,
  `hours` int(11) DEFAULT '0',
  `minutes` int(11) DEFAULT '0',
  `price` float DEFAULT '0',
  `payment_method` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `shop_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (2,'37','16','2018-01-10','222222',3,1,100,1,0,0),(3,'37','16','2018-01-18','Yes',2,1,100,2,0,0),(4,'39','16','2018-01-02','6666',11,2,100,1,0,0),(5,'35','16','2018-01-17','',1,2,50,1,0,0),(6,'38','16','2018-01-11','',2,2,100,2,0,0),(7,'35','16','2018-01-02','safasf',14,22,50,2,0,0),(8,'37','16','2018-01-02','sfsaf',15,22,100,2,1,0),(9,NULL,'16','2017-01-10','asfasf',1,2,600,1,0,1),(10,NULL,'16','2018-01-10','asfasf',1,2,600,1,1,0),(11,NULL,'16','2018-01-17','fsaf',1,2,600,2,0,0),(12,NULL,'16','2018-01-09','',1,2,600,1,0,0),(13,NULL,'16','2018-01-03','asf',1,1,600,1,0,0),(14,NULL,'16','2018-01-10','asf',1,2,600,2,1,0),(15,NULL,'16','2018-01-10','asff',2,2,300,2,0,0),(16,NULL,'16','2018-01-04','asfasf',1,1,145.5,2,0,1),(17,NULL,'16','2018-01-03','5354',1,2,50,2,0,1),(18,NULL,'16','2018-01-10','dsg',1,1,680,2,0,0),(19,NULL,'16','2018-01-25','asf',1,1,146.5,1,1,1);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pname` varchar(45) DEFAULT NULL,
  `ptype` varchar(45) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `img` varchar(145) DEFAULT NULL,
  `product_ID` varchar(150) DEFAULT NULL,
  `color` varchar(45) DEFAULT NULL,
  `stock` int(11) DEFAULT '0',
  `shop_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=hebrew;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (34,'super flower','Super Flowers',500,'14122017151229.jpg','E5G4','blue',100,0),(35,'dequila','boquet',50,'26132017151229.jpg','E3G43','blue',90,1),(36,'rose','boquet',80,'52122017151229.jpg','E3G43','blue',80,0),(37,'ren','Flower collection',100,'58112017151229.jpg','E3G43','black',70,0),(38,'Petunia','Flower collection',100,'06132017151229.jpg','E3G433','black',25,2),(39,'Antonio','Flower collection',100,'xxx.jpg','E3G433','black',4,0),(40,'Just flower','Flower collection',46.5,'21112017161229.jpg','123123','red',60,1),(41,'intersting2','intersting2',110,'00122017161229.jpg','12312322','red',6,0);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refunds`
--

DROP TABLE IF EXISTS `refunds`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `refunds` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) DEFAULT '0',
  `refund` float DEFAULT '0',
  `shop_id` int(11) DEFAULT '0',
  `refund_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refunds`
--

LOCK TABLES `refunds` WRITE;
/*!40000 ALTER TABLE `refunds` DISABLE KEYS */;
INSERT INTO `refunds` VALUES (1,8,50,1,'2018-01-04'),(2,10,600,2,'2018-01-04'),(3,14,600,2,'2018-01-04'),(4,19,146.5,1,'2018-01-04');
/*!40000 ALTER TABLE `refunds` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `survey_results`
--

DROP TABLE IF EXISTS `survey_results`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `survey_results` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `q1` int(2) DEFAULT '0',
  `q2` int(2) DEFAULT '0',
  `q3` int(2) DEFAULT '0',
  `q4` int(2) DEFAULT '0',
  `q5` int(2) DEFAULT '0',
  `q6` int(2) DEFAULT '0',
  `survey_id` int(11) DEFAULT '0',
  `shop_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `survey_results`
--

LOCK TABLES `survey_results` WRITE;
/*!40000 ALTER TABLE `survey_results` DISABLE KEYS */;
INSERT INTO `survey_results` VALUES (1,2,2,3,2,2,9,6,2),(2,2,4,2,9,8,10,6,2),(3,2,4,2,9,8,9,1,1),(4,2,2,2,2,2,9,1,1),(5,2,3,1,2,2,9,1,1),(6,2,1,2,2,2,8,1,1),(9,1,1,2,3,3,8,2,2),(10,1,1,2,3,3,8,9,2),(11,4,4,4,3,4,8,10,1),(12,2,3,3,2,3,9,2,2);
/*!40000 ALTER TABLE `survey_results` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `surveys`
--

DROP TABLE IF EXISTS `surveys`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `surveys` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `q1` varchar(1000) DEFAULT NULL,
  `q2` varchar(1000) DEFAULT NULL,
  `q3` varchar(1000) DEFAULT NULL,
  `q4` varchar(1000) DEFAULT NULL,
  `q5` varchar(1000) DEFAULT NULL,
  `q6` varchar(1000) DEFAULT NULL,
  `survey_name` varchar(150) DEFAULT NULL,
  `shop_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `surveys`
--

LOCK TABLES `surveys` WRITE;
/*!40000 ALTER TABLE `surveys` DISABLE KEYS */;
INSERT INTO `surveys` VALUES (1,'1','1','1','1','1','1','survey1',1),(2,'asf','asf','','','asf','asf','survey2',2),(6,'What is you favorite product?','how much money do you spend?','how much you love this shop?','are you satisfaid from the service?','how much money do you spend?','how much you love this shop?','survey3',1),(9,'124','1244','124','124','1424','124','survey4',2),(10,' how would you describe the overall\natmosphere?',' Have you ever witnessed disruptive behavior?','Are there any particular settings where disruptive behavior is most prevalent?','Cultural/Ethnic differences','Generational differences ','Gender differences','disruptive behavior',1);
/*!40000 ALTER TABLE `surveys` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `fname` varchar(20) DEFAULT NULL,
  `lname` varchar(20) DEFAULT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `permissions` int(11) DEFAULT '0',
  `username` varchar(25) DEFAULT NULL,
  `password` varchar(25) DEFAULT NULL,
  `logged` int(11) DEFAULT '0',
  `authorized` int(2) DEFAULT '0',
  `shop_id` int(11) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=hebrew COMMENT='users table';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'shon','ron',NULL,0,'admin','root',0,1,1),(2,'ron','bool',NULL,NULL,'daniel2','123',0,0,1),(3,'lior','abu',NULL,2,'lior','456',0,0,1),(5,'george','toor',NULL,NULL,'or','123',0,0,1),(6,'shimhon','koor',NULL,1,'employee','employee',0,0,1),(7,'dor','poor',NULL,1,'employee1','employee1',0,0,2),(8,'shim','soor',NULL,1,'employee2','employee2',0,0,2),(9,'jonny','noor',NULL,1,'employee3','employee3',0,0,3),(10,'koral','loor',NULL,4,'shop manager','shop manager',0,0,1),(11,'yossi','moor',NULL,2,'Sexpert','Sexpert',0,0,1),(12,'moti','mor',NULL,2,'Sexpert2','Sexpert2',0,0,2),(13,'dani','sjpr',NULL,2,'Sexpert3','Sexpert3',0,0,2),(14,'dorit','shor',NULL,2,'Sexpert4','Sexpert4',0,0,3),(15,'shimrit','kasan',NULL,3,'service','service',0,0,1),(16,'daniel','bachnov','0502208768',5,'customer','customer',0,1,0),(17,'michael','dasan',NULL,5,'customer2','customer2',0,1,0),(18,'john','hasan',NULL,5,'customer3','customer3',0,1,0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-04 17:46:15
