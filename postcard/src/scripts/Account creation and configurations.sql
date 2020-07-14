drop user if exists postcard;
CREATE USER 'postcard'@'%' IDENTIFIED BY 'Alten@123';
GRANT ALL PRIVILEGES ON *.* TO 'postcard'@'%' ;

CREATE TABLE `postcard`.`PROPERTIES` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100),
    `value` VARCHAR(300),
    PRIMARY KEY (`id`)
);

CREATE TABLE `postcard`.`image` (
  `imageId` int(11) NOT NULL AUTO_INCREMENT,
  `image` longblob DEFAULT NULL,
  `imageType` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`imageId`)
); 

CREATE TABLE `postcard`.`postcardorder` (
  `orderId` int(11) NOT NULL AUTO_INCREMENT,
   `imageId` int(11),
  `senderJson` text DEFAULT NULL,
  `orderStatus` varchar(45) DEFAULT NULL,
  `senderText` varchar(500) DEFAULT NULL,
  `brandingText` varchar(500) DEFAULT NULL,
  `brandingJson` text DEFAULT NULL,
  `createdDate` datetime(6) DEFAULT NULL,
  `createdBy` varchar(500) DEFAULT NULL,
  `updatedDate` datetime(6) DEFAULT NULL,
  `updatedBy` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`orderId`),
CONSTRAINT image_idfk_1 FOREIGN KEY (`imageId`) REFERENCES `postcard`.`image` (`imageId`));	


CREATE TABLE `postcard`.`postcard` (
  `orderId` int(11) DEFAULT NULL,
  `cardId` int(11) NOT NULL AUTO_INCREMENT,
  `cardKey` varchar(45) DEFAULT NULL,
  `attempts` int(11) DEFAULT NULL,
  `recipientJson` text DEFAULT NULL,
  `submissionStatus` varchar(45) DEFAULT NULL,
  `response` varchar(500) DEFAULT NULL,
  `cardStatus` varchar(45) DEFAULT NULL,
   `createdDate` datetime(6) DEFAULT NULL,
  `createdBy` varchar(500) DEFAULT NULL,
  `updatedDate` datetime(6) DEFAULT NULL,
  `updatedBy` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`cardId`),
CONSTRAINT order_idfk_1 FOREIGN KEY (`orderId`) REFERENCES `postcard`.`postcardorder` (`orderId`));


CREATE TABLE `postcard`.`user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(250) NOT NULL,
  `role` varchar(45) NOT NULL,
  `userName` varchar(45) NOT NULL,
  PRIMARY KEY (`userId`)
);

CREATE TABLE `postcard`.`VALIDATION_CONFIGURATION` (
    `id` int(11) not null  AUTO_INCREMENT ,
    `key` varchar(50) not null,
    `configuration` text not null,
    primary key (`id`)
);

INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('enable-swagger','Y');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('authURL','https://apiint.post.ch/OAuth/authorization');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('accessTokenURL','https://apiint.post.ch/OAuth/token');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('clientID','3ff6558b0da85758e9d36fdd2092cc19');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('clientSecret','3552aa3d6dcb17e93353943758b6d63f');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('scope','PCCAPI');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('postcardAPI','api/v1/postcards/');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('campaignKey','0b6eee81-5ea3-475a-975b-0318335eb228');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('createPostcardEndPoint','api/v1/postcards?campaignKey=');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('campaignsEndPoint','api/v1/campaigns/');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('statisticEndpoint','/statistic');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('postcardBaseURL','https://apiint.post.ch/pcc/');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('stateEndPoint','/state');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('approvalEndPoint','/approval');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('senderAddressEndPoint','/addresses/sender');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('recipientAddressEndPoint','/addresses/recipient');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('frontImageEndPoint','/image');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('senderTextEndPoint','/sendertext?senderText=');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('brandingTextEndPoint','/branding/text');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('frontPreviewsEndPoint','/previews/front');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('backPreviewsEndPoint','/previews/back');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('brandingImageEndPoint','/branding/image');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('stampImageEndPoint','/stamp/image');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('getUserQuery','select * from user where userName=?');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('jwt.secret','AltenCalsoftLabs');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('updateProperty','update PROPERTIES set value=? where name =?');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('getValidationConfiguration','select `configuration` from VALIDATION_CONFIGURATION where `key`=?');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('findOnePostcardOrderQuery','SELECT * FROM postcardorder WHERE orderId = ?');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('findallPostcardOrderQuery','select * from postcardorder');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('deletePostcardOrderQuery','delete from postcardorder where orderId = ?');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('findallPostcardQuery','select * from postcard');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('deletePostcardQuery','delete from Postcard where cardId = ?');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('findOnePostcardQuery','SELECT * FROM Postcard WHERE cardKey = ?');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('createImageQuery','insert into image(imageType) values (?)');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('findOneImageQuery','SELECT * FROM image WHERE imageId = ?');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('findallImageQuery','select * from image');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('deletePostcardQuery','delete from image where imageId = ?');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('management.endpoints.web.exposure.include','*');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('management.endpoint.health','true');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('management.endpoint.info','true');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('management.endpoint.restart.enabled','true');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('findallPostcardByOrderidQuery','SELECT * FROM Postcard WHERE orderId = ?');

INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('createPostcardOrderQuery','insert into postcardorder(orderStatus,createdDate,createdBy) values (?,?,?)');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('updatePostcardOrderQuery','update postcardorder set senderText =? ,senderJson=? ,brandingText=?,brandingJson=?, imageId=?,updatedDate=?,updatedBy=? where orderId = ?');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('updateSenderAddressInfo','update postcardorder set senderJson=?,senderText=?,updatedDate=?,updatedBy=? where orderId=?');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('updateBrandInfo','update postcardorder set brandingJson=?,brandingText=?,updatedDate=?,updatedBy=? where orderId=?');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('createPostcardForRecipientAddressQuery','insert into postcard(orderId,recipientJson,submissionStatus,createdDate,createdBy) values (?,?,?,?,?)');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('createPostcardQuery','insert into postcard(orderId,cardKey,recipientJson,submissionStatus,response,attempts,cardStatus,,createdDate,createdBy) values (?,?,?,?,?,?,?,?,?)');
INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('updatePostcardQuery','update postcard set recipientJson =? ,submissionStatus=? ,response=?,attempts=?,cardStatus=?,updatedDate=?,updatedBy=? where cardKey = ?');

INSERT INTO `postcard`.`PROPERTIES` (`name`,`value`) VALUES ('selectGetallPostcards','SELECT * FROM postcard.postcardorder where CAST(createdDate as DATE) >= ?  AND CAST(createdDate as DATE) <= ? and orderStatus=?');

insert into `postcard`.`user`(`userName`,`password`,`role`)values('alten','$2a$10$ixlPY3AAd4ty1l6E2IsQ9OFZi2ba9ZQE0bP7RFcGIWNhyFrrT3YUi','ROLE_USER');
insert into `postcard`.`user`(`userName`,`password`,`role`)values('admin','$2a$10$ixlPY3AAd4ty1l6E2IsQ9OFZi2ba9ZQE0bP7RFcGIWNhyFrrT3YUi','ROLE_ADMIN');

insert into `postcard`.`VALIDATION_CONFIGURATION`(`key`,`configuration`)values('recipientAddress','{"title":{"validations":[{"type":"NOT MANDATORY","message":{"code":"1009","description":"The title is not required"}},{"type":"MAX_LENGTH","value":30,"message":{"code":"1019","description":"Title maximum length allowed is 30"}}]},"lastname":{"validations":[{"type":"MANDATORY","message":{"code":"1001","description":"The last name is required"}},{"type":"MAX_LENGTH","value":75,"message":{"code":"1011","description":"Last name maximum length allowed is 75"}}]},"firstname":{"validations":[{"type":"MANDATORY","message":{"code":"1000","description":"The first name is required"}},{"type":"MAX_LENGTH","value":75,"message":{"code":"1010","description":"First name maximum length allowed is 75"}}]},"company":{"validations":[{"type":"NOT MANDATORY","message":{"code":"1008","description":"The company is not required"}},{"type":"MAX_LENGTH","value":100,"message":{"code":"1018","description":"Company name maximum length allowed is 100"}}]},"street":{"validations":[{"type":"MANDATORY","message":{"code":"1002","description":"The street is required"}},{"type":"MAX_LENGTH","value":60,"message":{"code":"1012","description":"Street maximum length allowed is 60"}}]},"houseNr":{"validations":[{"type":"NOT MANDATORY","message":{"code":"1005","description":"The houseNr is not required"}},{"type":"MAX_LENGTH","value":10,"message":{"code":"1015","description":"houseNr maximum length allowed is 10"}}]},"zip":{"validations":[{"type":"MANDATORY","message":{"code":"1003","description":"The zip is required"}},{"type":"MAX_LENGTH","value":6,"message":{"code":"1013","description":"zip maximum length allowed is 6"}}]},"city":{"validations":[{"type":"MANDATORY","message":{"code":"1004","description":"The city is required"}},{"type":"MAX_LENGTH","value":39,"message":{"code":"1014","description":"city maximum length allowed is 39"}}]},"country":{"validations":[{"type":"NOT MANDATORY","message":{"code":"1006","description":"The country is not required"}},{"type":"MAX_LENGTH","value":60,"message":{"code":"1016","description":"country maximum length allowed is 60"}}]},"poBox":{"validations":[{"type":"NOT MANDATORY","message":{"code":"1007","description":"The poBox is not required"}},{"type":"MAX_LENGTH","value":26,"message":{"code":"1017","description":"poBox maximum length allowed is 25"}}]}}');





