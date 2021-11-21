/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `Coupon`
--

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `Coupon`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Coupon`
(
    `id`                int(11)      not null auto_increment,
    `hotel_id`          int(11)      not null default 0,
    `target_money`      int(11)      not null default 0,
    `discount_money`    int(11)      not null default 0,
    `multi_room_target` int(11)      not null default 0,
    `discount`          double       not null default 0,
    `status`            varchar(20)  not null default '',
    `type`              varchar(40)  not null default '',
    `name`              varchar(40)  not null default '',
    `description`       varchar(255) not null default '',
    `start_time`        datetime     not null default now(),
    `end_time`          datetime     not null default now(),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Coupon`
--
BEGIN;
/*!40000 ALTER TABLE `Coupon`
    DISABLE KEYS */;
INSERT INTO `Coupon`
(hotel_id, target_money, discount_money, multi_room_target, discount, status, type, name, description, start_time,
 end_time)
VALUES (1, 500, 100, -1, 1, 'Available', 'Birthday', '生日', '祝你快乐', now(), now()),
       (2, 500, 100, 100000000, 1, 'Available', 'Target', '双十一活动券', '双十一期间的满减券', now(), now());

/*!40000 ALTER TABLE `Coupon`
    ENABLE KEYS */;
COMMIT;

--
-- Table structure for table `Hotel`
--

DROP TABLE IF EXISTS `Hotel`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Hotel`
(
    `id`           int(11)       not null auto_increment,
    `manager_id`   int(11)       not null default 0,
    `credit_bound` int(11)       not null default 0,
    `rate`         double(10, 2) not null default 0,
    `name`         varchar(255)  not null default '',
    `description`  varchar(255)  not null default '',
    `address`      varchar(255)  not null default '',
    `biz_region`   varchar(255)  not null default '',
    `hotel_star`   varchar(255)  not null default '',
    `phone_number` varchar(255)  not null default '',
    `pic`          varchar(255)  not null default '',
    `announcement` varchar(2000) not null default '',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Hotel`
--

BEGIN;
/*!40000 ALTER TABLE `Hotel`
    DISABLE KEYS */;
INSERT INTO `Hotel`
(manager_id, credit_bound, rate, name, description, address, biz_region, hotel_star, phone_number, pic, announcement)
values (2, 100, 4.7, '汉庭酒店', '这是汉庭酒店的简介描述', '这是汉庭酒店的地址', 'XiDan', 'Five', '12345678910',
        'https://dimg04.c-ctrip.com/images/0202i120008ce99hwC65B_R_600_400_R5_D.jpg', '本酒店现在没有活动，爱住不住。'),
       (2, 100, 4.5, '儒家酒店', '这是如家酒店的简介描述', '这是如家酒店的地址', 'XiDan', 'Four', '78910123456',
        'https://dimg04.c-ctrip.com/images/0205n1200091bl12d03EB_R_600_400_R5_D.png', '双十一活动，满500减100。'),
       (3, 100, 5, '桂圆酒店', '这是桂圆酒店的描述简介', '这是桂圆酒店的地址', 'XiDan', 'Four', '45678912310',
        '	https://dimg04.c-ctrip.com/images/02005120008bxzcul2103_R_600_400_R5_D.jpg', '本酒店现在没有优惠，白嫖怪快滚');
/*!40000 ALTER TABLE `Hotel`
    ENABLE KEYS */;
COMMIT;

--
-- Table structure for table `OrderList`
--

DROP TABLE IF EXISTS `OrderList`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OrderList`
(
    `id`             int(11)      not null auto_increment,
    `user_id`        int(11)      not null default 0,
    `person_id`      int(11)      not null default 0,
    `hotel_id`       int(11)      not null default 0,
    `comment_id`     int(11)      not null default 0,
    `room_num`       int(11)      not null default 0,
    `people_num`     int(11)      not null default 0,
    `price`          double       not null default 0,
    `credit_delta`   double       not null default 0,
    `have_child`     bool         not null default false,
    `breakfast`      bool         not null default false,
    `room_type`      varchar(255) not null default '',
    `order_state`    varchar(255) not null default '',
    `hotel_name`     varchar(255) not null default '',
    `check_in_date`  date         not null default '1970-01-01',
    `check_out_date` date         not null default '1970-01-01',
    `create_date`    datetime     not null default '1970-01-01',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrderList`
--

BEGIN;
/*!40000 ALTER TABLE `OrderList`
    DISABLE KEYS */;

insert into OrderList (user_id, person_id, hotel_id, comment_id, room_num, people_num, price, credit_delta, have_child,
                       breakfast, room_type, order_state, hotel_name, check_in_date, check_out_date, create_date)
values (5, 1, 1, -1, 2, 2, 300, 300, false, false, 'BigBed', 'Finished', '汉庭酒店', date_sub(now(), interval 10 day),
        date_sub(now(), interval 5 day), date_sub(now(), interval 15 day)),
       (5, 1, 1, -1, 2, 2, 300, 300, false, false, 'BigBed', 'Finished', '汉庭酒店', date_sub(now(), interval 10 day),
        date_sub(now(), interval 5 day), date_sub(now(), interval 15 day)),
       (6, 1, 1, -1, 2, 2, 300, 300, false, false, 'BigBed', 'Finished', '汉庭酒店', date_sub(now(), interval 10 day),
        date_sub(now(), interval 5 day), date_sub(now(), interval 15 day)),
       (6, 1, 1, -1, 2, 2, 300, 300, false, false, 'BigBed', 'Finished', '汉庭酒店', date_sub(now(), interval 10 day),
        date_sub(now(), interval 5 day), date_sub(now(), interval 15 day));

/*!40000 ALTER TABLE `OrderList`
    ENABLE KEYS */;
COMMIT;

--
-- Table structure for table `Room`
--

DROP TABLE IF EXISTS `Room`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Room`
(
    `id`         int(11)     not null auto_increment,
    `people_max` int(11)     not null default 0,
    `total`      int(11)     not null default 0,
    `hotel_id`   int(11)     not null default 0,
    `price`      double      not null default 0,
    `breakfast`  bool        not null default false,
    `room_type`  varchar(50) not null default '',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Room`
--

BEGIN;
/*!40000 ALTER TABLE `Room`
    DISABLE KEYS */;

/*!40000 ALTER TABLE `Room`
    ENABLE KEYS */;
COMMIT;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User`
(
    `id`        int(11)      not null auto_increment,
    `credit`    double       not null default 0,
    `user_type` varchar(255) not null default '',
    `avatar`    varchar(255) not null default '',
    `email`     varchar(255) not null default '',
    `password`  varchar(255) not null default '',
    `username`  varchar(255) not null default '',
    `vip_type`  varchar(255) not null default '',
    `vip_end`   datetime     not null default now(),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

BEGIN;
/*!40000 ALTER TABLE `User`
    DISABLE KEYS */;
INSERT INTO `User`
(credit, user_type, avatar, email, password, username, vip_type, vip_end)
VALUES (100, 'Admin', 'https://dimg04.c-ctrip.com/images/fd/headphoto/g6/M04/58/1E/CggYtFbqT4CAGa2kAAAtt8o2rRM946_R_100_100_R5_Q70_D.jpg',
        'a@a.com', 'x', 'A1', 'Nil', now()),
       (100, 'HotelManager', 'https://dimg04.c-ctrip.com/images/fd/headphoto/g6/M04/58/1E/CggYtFbqT4CAGa2kAAAtt8o2rRM946_R_100_100_R5_Q70_D.jpg',
        'b@b.com', 'x', 'HM1', 'Nil', now()),
       (100, 'HotelManager', 'https://dimg04.c-ctrip.com/images/fd/headphoto/g6/M04/58/1E/CggYtFbqT4CAGa2kAAAtt8o2rRM946_R_100_100_R5_Q70_D.jpg',
        'c@c.com', 'x', 'HM2', 'Nil', now()),
       (100, 'Marketer', 'https://dimg04.c-ctrip.com/images/fd/headphoto/g6/M04/58/1E/CggYtFbqT4CAGa2kAAAtt8o2rRM946_R_100_100_R5_Q70_D.jpg',
        'd@d.com', 'x', 'M1', 'Nil', now()),
       (100, 'Client', 'https://dimg04.c-ctrip.com/images/fd/headphoto/g6/M04/58/1E/CggYtFbqT4CAGa2kAAAtt8o2rRM946_R_100_100_R5_Q70_D.jpg',
        'e@e.com', 'x', 'C1', 'Nil', now()),
       (100, 'Client', 'https://dimg04.c-ctrip.com/images/fd/headphoto/g6/M04/58/1E/CggYtFbqT4CAGa2kAAAtt8o2rRM946_R_100_100_R5_Q70_D.jpg',
        'f@f.com', 'x', 'C2', 'Nil', now()),
       (100, 'Client', 'https://dimg04.c-ctrip.com/images/fd/headphoto/g6/M04/58/1E/CggYtFbqT4CAGa2kAAAtt8o2rRM946_R_100_100_R5_Q70_D.jpg',
        'g@g.com', 'x', 'C3', 'Nil', now());
/*!40000 ALTER TABLE `User`
    ENABLE KEYS */;
COMMIT;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;


DROP TABLE IF EXISTS `Person`;
CREATE TABLE `Person`
(
    `id`           int(11)      not null auto_increment,
    `user_id`      int(11)      not null default 0,
    `id_no`        varchar(20)  not null default '',
    `real_name`    varchar(255) not null default '',
    `phone_number` varchar(255) not null default '',
    `birthday`     date         not null default '1970-01-01',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
insert into `Person`
    (user_id, id_no, real_name, phone_number, birthday)
values (5, '123451234512345000', 'hyc', '11111111111', '2020-07-01'),
       (5, '123451234512345001', 'djh', '11111111111', '2000-07-15'),
       (6, '123451234512345002', 'dmz', '11111111111', '1999-07-03'),
       (6, '123451234512345003', 'lbd', '11111111111', '2000-07-04');

drop table if exists `Comment`;
create table `Comment`
(
    `id`       int(11)      not null auto_increment,
    `user_id`  int(11)      not null default 0,
    `hotel_id` int(11)      not null default 0,
    `score`    int(11)      not null default 0,
    `title`    varchar(255) not null default 0,
    `content`  varchar(255) not null default 0,
    primary key (`id`)
) engine = InnoDB
  default charset = utf8;
insert into `Comment` (user_id, hotel_id, score, title, content)
values (5, 1, 5, 'Good', 'very good'),
       (6, 1, 2, 'Hell', 'very bad');

drop table if exists `RoomNumber`;
create table `RoomNumber`
(
    `id`      int(11) not null auto_increment,
    `room_id` int(11) not null,
    `number`  int(11) not null default 0,
    `date`    date    not null default '1970-01-01',
    primary key (`id`)
) engine = InnoDB
  default charset = utf8;

drop table if exists `CreditUp`;
create table `CreditUp`
(
    `id`           int(11)       not null auto_increment primary key,
    `user_id`      int(11)       not null default 0,
    `credit_delta` double(64, 2) not null default 0
) default charset = utf8;

SET FOREIGN_KEY_CHECKS = 1;

