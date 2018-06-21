/**
 * Version: 0.0.1
 * Basic table creation
 */

CREATE TABLE `LanguageMaster` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Code` varchar(10) NOT NULL,
  `Name` varchar(100) NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `LanguageMaster_UN` (`Code`),
  KEY `LanguageMaster_Code_IDX` (`Code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `TranslationCategoryMaster` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) NOT NULL,
  `Description` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `TranslationCategoryMaster_UN` (`Name`),
  KEY `TranslationCategoryMaster_Name_IDX` (`Name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `Translation` (
  `Key` varchar(100) NOT NULL,
  `IdLanguageMaster` int(11) NOT NULL,
  `IdTranslationCategoryMaster` int(11) NOT NULL,
  `Value` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`Key`,`IdLanguageMaster`,`IdTranslationCategoryMaster`),
  KEY `Translation_LanguageMaster_FK` (`IdLanguageMaster`),
  KEY `Translation_TranslationCategoryMaster_FK` (`IdTranslationCategoryMaster`),
  CONSTRAINT `Translation_LanguageMaster_FK` FOREIGN KEY (`IdLanguageMaster`) REFERENCES `LanguageMaster` (`Id`),
  CONSTRAINT `Translation_TranslationCategoryMaster_FK` FOREIGN KEY (`IdTranslationCategoryMaster`) REFERENCES `TranslationCategoryMaster` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `GenderMaster` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) NOT NULL,
  `Description` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `GenderMaster_UN` (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `User` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(100) NOT NULL,
  `LastName` varchar(100) DEFAULT NULL,
  `DoB` datetime DEFAULT NULL,
  `Mobile` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `IdGenderMaster` int(11) DEFAULT NULL,
  `CreatedBy` int(11) DEFAULT NULL,
  `CreatedDate` datetime DEFAULT NULL,
  `LastModifiedBy` int(11) DEFAULT NULL,
  `LastModifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `User_GenderMaster_FK` (`IdGenderMaster`),
  CONSTRAINT `User_GenderMaster_FK` FOREIGN KEY (`IdGenderMaster`) REFERENCES `GenderMaster` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `Avatar` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `IdUser` int(11) NOT NULL,
  `Photo` blob,
  `CreatedBy` int(11) DEFAULT NULL,
  `CreatedDate` datetime DEFAULT NULL,
  `LastModifiedBy` int(11) DEFAULT NULL,
  `LastModifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `Avatar_User_FK` (`IdUser`),
  CONSTRAINT `Avatar_User_FK` FOREIGN KEY (`IdUser`) REFERENCES `User` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;