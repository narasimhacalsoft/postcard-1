drop user if exists postcard;
CREATE USER 'postcard'@'%' IDENTIFIED BY 'Alten@123';
GRANT ALL PRIVILEGES ON *.* TO 'postcard'@'%' ;

CREATE TABLE PROPERTIES (
    id INT(11) NOT NULL AUTO_INCREMENT,
    name VARCHAR(100),
    value VARCHAR(300),
    PRIMARY KEY (id)
);

insert into PROPERTIES(name,value)values('enable-swagger','Y');
insert into PROPERTIES(name,value)values('authURL','https://apiint.post.ch/OAuth/authorization');
insert into PROPERTIES(name,value)values('accessTokenURL','https://apiint.post.ch/OAuth/token');
insert into PROPERTIES(name,value)values('clientID','');
insert into PROPERTIES(name,value)values('clientSecret','');
insert into PROPERTIES(name,value)values('scope','PCCAPI');
insert into PROPERTIES(name,value)values('postcardAPI','https://apiint.post.ch/pcc/');
insert into PROPERTIES(name,value)values('campaignKey','');