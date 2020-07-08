drop user if exists postcard;
CREATE USER 'postcard'@'%' IDENTIFIED BY 'Alten@123';
GRANT ALL PRIVILEGES ON *.* TO 'postcard'@'%' ;

CREATE TABLE postcard.PROPERTIES (
    id INT(11) NOT NULL AUTO_INCREMENT,
    name VARCHAR(100),
    value VARCHAR(300),
    PRIMARY KEY (id)
);

CREATE TABLE `postcard.postcard` (
  `order_id` int(11) NOT NULL,
  `card_id` int(11) DEFAULT NULL,
  `card_key` varchar(45) DEFAULT NULL,
  `attempts` int(11) DEFAULT NULL,
  `recipient_json` varchar(45) DEFAULT NULL,
  `submission_status` varchar(45) DEFAULT NULL,
  `response` varchar(45) DEFAULT NULL,
  `card_status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
); 

CREATE TABLE `postcard.order` (
  `order_id` int(11) NOT NULL,
  `sender_json` longblob DEFAULT NULL,
  `branding_text` varchar(45) DEFAULT NULL,
  `branding_json` longblob DEFAULT NULL,
  `sender_text` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
);

CREATE TABLE `postcard.image` (
  `image_id` int(11) NOT NULL,
  `image` longblob DEFAULT NULL,
  `image_type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`image_id`)
); 

CREATE TABLE `postcard.user` (
  `user_name` int(11) NOT NULL,
  `password` varchar(45) DEFAULT NULL,
  `role` varchar(45) DEFAULT NULL,
  `gdpr_consent` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`user_name`)
); 

INSERT INTO `postcard.PROPERTIES` (`id`,`name`,`value`) VALUES (1,'enable-swagger','Y');
INSERT INTO `postcard.PROPERTIES` (`id`,`name`,`value`) VALUES (2,'authURL','https://apiint.post.ch/OAuth/authorization');
INSERT INTO `postcard.PROPERTIES` (`id`,`name`,`value`) VALUES (3,'accessTokenURL','https://apiint.post.ch/OAuth/token');
INSERT INTO `postcard.PROPERTIES` (`id`,`name`,`value`) VALUES (4,'clientID','3ff6558b0da85758e9d36fdd2092cc19');
INSERT INTO `postcard.PROPERTIES` (`id`,`name`,`value`) VALUES (5,'clientSecret','3552aa3d6dcb17e93353943758b6d63f');
INSERT INTO `postcard.PROPERTIES` (`id`,`name`,`value`) VALUES (6,'scope','PCCAPI');
INSERT INTO `postcard.PROPERTIES` (`id`,`name`,`value`) VALUES (7,'postcardAPI','api/v1/postcards/');
INSERT INTO `postcard.PROPERTIES` (`id`,`name`,`value`) VALUES (8,'campaignKey','0b6eee81-5ea3-475a-975b-0318335eb228');
INSERT INTO `postcard.PROPERTIES` (`id`,`name`,`value`) VALUES (9,'createPostcardEndPoint','api/v1/postcards?campaignKey=');
INSERT INTO `postcard.PROPERTIES` (`id`,`name`,`value`) VALUES (10,'campaignsEndPoint','api/v1/campaigns/');
INSERT INTO `postcard.PROPERTIES` (`id`,`name`,`value`) VALUES (11,'statisticEndpoint','/statistic');
INSERT INTO `postcard.PROPERTIES` (`id`,`name`,`value`) VALUES (12,'postcardBaseURL','https://apiint.post.ch/pcc/');
INSERT INTO `postcard.PROPERTIES` (`id`,`name`,`value`) VALUES (13,'stateEndPoint','/state');
INSERT INTO `postcard.PROPERTIES` (`id`,`name`,`value`) VALUES (14,'approvalEndPoint','/approval');
INSERT INTO `postcard.PROPERTIES` (`id`,`name`,`value`) VALUES (15,'senderAddressEndPoint','/addresses/sender');
INSERT INTO `postcard.PROPERTIES` (`id`,`name`,`value`) VALUES (16,'recipientAddressEndPoint','/addresses/recipient');
INSERT INTO `postcard.PROPERTIES` (`id`,`name`,`value`) VALUES (17,'frontImageEndPoint','/image');
INSERT INTO `postcard.PROPERTIES` (`id`,`name`,`value`) VALUES (18,'senderTextEndPoint','/sendertext?senderText=');
INSERT INTO `postcard.PROPERTIES` (`id`,`name`,`value`) VALUES (19,'brandingTextEndPoint','/branding/text');
INSERT INTO `postcard.PROPERTIES` (`id`,`name`,`value`) VALUES (20,'frontPreviewsEndPoint','/previews/front');
INSERT INTO `postcard.PROPERTIES` (`id`,`name`,`value`) VALUES (21,'backPreviewsEndPoint','/previews/back');
INSERT INTO `postcard.PROPERTIES` (`id`,`name`,`value`) VALUES (22,'brandingImageEndPoint','/branding/image');
INSERT INTO `postcard.PROPERTIES` (`id`,`name`,`value`) VALUES (23,'stampImageEndPoint','/stamp/image');
