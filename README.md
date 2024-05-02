# clase_espejo
CREATE TABLE `product` (
  `idproduct` int NOT NULL AUTO_INCREMENT,
  `name_product` varchar(100) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `stocks` int DEFAULT NULL,
  `status` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`idproduct`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Granola',15.5,36,'1');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

CREATE TABLE `sale` (
  `idsale` int NOT NULL AUTO_INCREMENT,
  `idCustomer` int NOT NULL,
  `serial_number` varchar(45) DEFAULT NULL,
  `sale_date` date DEFAULT NULL,
  `cost` double DEFAULT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`idsale`)
  ) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `detailsale` (
  `idDetailSale` int NOT NULL AUTO_INCREMENT,
  `idsale` int NOT NULL,
  `idproduct` int NOT NULL,
  `amount` int DEFAULT NULL,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`idDetailSale`),
  KEY `fk_detailSale_sale1_idx` (`idsale`),
  KEY `fk_detailSale_product1_idx` (`idproduct`),
  CONSTRAINT `fk_detailSale_product1` FOREIGN KEY (`idproduct`) REFERENCES `product` (`idproduct`),
  CONSTRAINT `fk_detailSale_sale1` FOREIGN KEY (`idsale`) REFERENCES `sale` (`idsale`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3;

