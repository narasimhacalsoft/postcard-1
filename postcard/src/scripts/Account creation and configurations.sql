drop user if exists postcard;
CREATE USER 'postcard'@'%' IDENTIFIED BY 'Alten@123';
GRANT ALL PRIVILEGES ON *.* TO 'postcard'@'%' ;

CREATE TABLE PROPERTIES (
    id INT(11) NOT NULL AUTO_INCREMENT,
    name VARCHAR(100),
    value VARCHAR(300),
    PRIMARY KEY (id)
);


INSERT INTO `` (`id`,`name`,`value`) VALUES (1,'enable-swagger','Y');
INSERT INTO `` (`id`,`name`,`value`) VALUES (2,'authURL','https://apiint.post.ch/OAuth/authorization');
INSERT INTO `` (`id`,`name`,`value`) VALUES (3,'accessTokenURL','https://apiint.post.ch/OAuth/token');
INSERT INTO `` (`id`,`name`,`value`) VALUES (4,'clientID','3ff6558b0da85758e9d36fdd2092cc19');
INSERT INTO `` (`id`,`name`,`value`) VALUES (5,'clientSecret','3552aa3d6dcb17e93353943758b6d63f');
INSERT INTO `` (`id`,`name`,`value`) VALUES (6,'scope','PCCAPI');
INSERT INTO `` (`id`,`name`,`value`) VALUES (7,'postcardAPI','api/v1/postcards/');
INSERT INTO `` (`id`,`name`,`value`) VALUES (8,'campaignKey','0b6eee81-5ea3-475a-975b-0318335eb228');
INSERT INTO `` (`id`,`name`,`value`) VALUES (9,'createPostcardEndPoint','api/v1/postcards?campaignKey=');
INSERT INTO `` (`id`,`name`,`value`) VALUES (10,'campaignsEndPoint','api/v1/campaigns/');
INSERT INTO `` (`id`,`name`,`value`) VALUES (11,'statisticEndpoint','/statistic');
INSERT INTO `` (`id`,`name`,`value`) VALUES (12,'postcardBaseURL','https://apiint.post.ch/pcc/');
INSERT INTO `` (`id`,`name`,`value`) VALUES (13,'stateEndPoint','/state');
INSERT INTO `` (`id`,`name`,`value`) VALUES (14,'approvalEndPoint','/approval');
INSERT INTO `` (`id`,`name`,`value`) VALUES (15,'senderAddressEndPoint','/addresses/sender');
INSERT INTO `` (`id`,`name`,`value`) VALUES (16,'recipientAddressEndPoint','/addresses/recipient');
INSERT INTO `` (`id`,`name`,`value`) VALUES (17,'frontImageEndPoint','/image');
INSERT INTO `` (`id`,`name`,`value`) VALUES (18,'senderTextEndPoint','/sendertext?senderText=');
INSERT INTO `` (`id`,`name`,`value`) VALUES (19,'brandingTextEndPoint','/branding/text');
INSERT INTO `` (`id`,`name`,`value`) VALUES (20,'frontPreviewsEndPoint','/previews/front');
INSERT INTO `` (`id`,`name`,`value`) VALUES (21,'backPreviewsEndPoint','/previews/back');
INSERT INTO `` (`id`,`name`,`value`) VALUES (22,'brandingImageEndPoint','/branding/image');
INSERT INTO `` (`id`,`name`,`value`) VALUES (23,'stampImageEndPoint','/stamp/image');
