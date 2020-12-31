CREATE DATABASE  IF NOT EXISTS `vacation_directory`;
USE `vacation_directory`;

CREATE TABLE `account` (
  `account_id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(45) DEFAULT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1;

INSERT INTO `account`

VALUES
(1,'admin@admin.com','Admin','Admin'),
(2,'user@user.com','User','user');



CREATE TABLE `authority` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(45) DEFAULT NULL,
  `admin` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

INSERT INTO `authority`

VALUES
(1,'admin@admin.com', 1),
(2,'user@user.com', 0);


CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `token` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

INSERT INTO `user`

VALUES
(1,'admin@admin.com', admin1, null),
(2,'user@user.com', user12, null);


CREATE TABLE `vacation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `note` varchar(300) DEFAULT NULL,
  `is_approved` tinyint(1) NOT NULL,
  `account_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `account_id` (`account_id`),
  CONSTRAINT `vacation_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

INSERT INTO `vacation`

VALUES
(1,'2021-06-09', '2021-06-15', '', 0, 1),
(2,'2021-01-11','2021-02-11', 'bla bla', 1, 1),
(3,'2021-05-11','2021-05-15', 'bla bla bla', 1, 2),
(4,'2021-04-11','2021-04-15', 'bla bla bla', 0, 2);

