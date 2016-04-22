CREATE DATABASE `biglib` /*!40100 DEFAULT CHARACTER SET latin1 */;
CREATE TABLE `authorities` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Email` varchar(80) NOT NULL,
  `Authority` varchar(60) NOT NULL DEFAULT 'ROLE_USER',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
CREATE TABLE `book` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Title` varchar(120) NOT NULL,
  `Athor` varchar(80) NOT NULL,
  `Genre` varchar(45) NOT NULL,
  `CountPage` int(11) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
CREATE TABLE `customer` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `FullName` varchar(80) NOT NULL,
  `Email` varchar(60) NOT NULL,
  `Password` varchar(45) NOT NULL,
  `Enabled` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
CREATE TABLE `cart` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `IdCustomer` int(11) NOT NULL,
  `IdBook` int(11) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `CartIdCustomer_idx` (`IdCustomer`),
  KEY `CartIdBook_idx` (`IdBook`),
  CONSTRAINT `CartIdBook` FOREIGN KEY (`IdBook`) REFERENCES `book` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `CartIdCustomer` FOREIGN KEY (`IdCustomer`) REFERENCES `customer` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
