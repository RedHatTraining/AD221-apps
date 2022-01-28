DROP TABLE IF EXISTS coupons;
create table coupons(
   id INT NOT NULL AUTO_INCREMENT,
   code VARCHAR(50) NOT NULL,
   usage_count INT,
   usage_limit INT,
   PRIMARY KEY ( id )
);
