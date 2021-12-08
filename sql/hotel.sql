SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

drop table if exists `RoomNumber`;
drop table if exists `UsableRoom`;
drop table if exists `RoomUnit`;
drop table if exists `RoomConfig`;
drop table if exists `Room`;
drop table if exists `Hotel`;

DROP TABLE IF EXISTS `Coupon`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Coupon` (
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

BEGIN;
/*!40000 ALTER TABLE `Coupon`
    DISABLE KEYS */;
INSERT INTO
    `Coupon`
(hotel_id, target_money, discount_money, multi_room_target, discount, status, type, name, description, start_time,
 end_time)
    VALUES
        (1, 500, 100, -1, 1, 'Available', 'Birthday', '生日', '祝你快乐', now(), now()),
        (2, 500, 100, 100000000, 1, 'Available', 'Target', '双十一活动券', '双十一期间的满减券', now(), now());

/*!40000 ALTER TABLE `Coupon`
    ENABLE KEYS */;
COMMIT;




--
-- Table structure for table `OrderList`
--

DROP TABLE IF EXISTS `OrderList`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OrderList` (
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

insert into
    OrderList (user_id, person_id, hotel_id, comment_id, room_num, people_num, price, credit_delta, have_child,
               breakfast, room_type, order_state, hotel_name, check_in_date, check_out_date, create_date)
    values
        (5, 1, 1, -1, 2, 2, 300, 300, false, false, 'BigBed', 'Finished', '汉庭酒店', date_sub(now(), interval 10 day),
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



DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
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
INSERT INTO
    `User`
(credit, user_type, avatar, email, password, username, vip_type, vip_end)
    VALUES
        (100, 'Admin',
         'https://dimg04.c-ctrip.com/images/fd/headphoto/g6/M04/58/1E/CggYtFbqT4CAGa2kAAAtt8o2rRM946_R_100_100_R5_Q70_D.jpg',
         'a@a.com', 'x', 'A1', 'Nil', now()),
        (100, 'HotelManager',
         'https://dimg04.c-ctrip.com/images/fd/headphoto/g6/M04/58/1E/CggYtFbqT4CAGa2kAAAtt8o2rRM946_R_100_100_R5_Q70_D.jpg',
         'b@b.com', 'x', 'HM1', 'Nil', now()),
        (100, 'HotelManager',
         'https://dimg04.c-ctrip.com/images/fd/headphoto/g6/M04/58/1E/CggYtFbqT4CAGa2kAAAtt8o2rRM946_R_100_100_R5_Q70_D.jpg',
         'c@c.com', 'x', 'HM2', 'Nil', now()),
        (100, 'Marketer',
         'https://dimg04.c-ctrip.com/images/fd/headphoto/g6/M04/58/1E/CggYtFbqT4CAGa2kAAAtt8o2rRM946_R_100_100_R5_Q70_D.jpg',
         'd@d.com', 'x', 'M1', 'Nil', now()),
        (100, 'Client',
         'https://dimg04.c-ctrip.com/images/fd/headphoto/g6/M04/58/1E/CggYtFbqT4CAGa2kAAAtt8o2rRM946_R_100_100_R5_Q70_D.jpg',
         'e@e.com', 'x', 'C1', 'Nil', now()),
        (100, 'Client',
         'https://dimg04.c-ctrip.com/images/fd/headphoto/g6/M04/58/1E/CggYtFbqT4CAGa2kAAAtt8o2rRM946_R_100_100_R5_Q70_D.jpg',
         'f@f.com', 'x', 'C2', 'Nil', now()),
        (100, 'Client',
         'https://dimg04.c-ctrip.com/images/fd/headphoto/g6/M04/58/1E/CggYtFbqT4CAGa2kAAAtt8o2rRM946_R_100_100_R5_Q70_D.jpg',
         'g@g.com', 'x', 'C3', 'Nil', now());
/*!40000 ALTER TABLE `User`
    ENABLE KEYS */;
COMMIT;



DROP TABLE IF EXISTS `Person`;
CREATE TABLE `Person` (
    `id`           int(11)      not null auto_increment,
    `user_id`      int(11)      not null default 0,
    `id_no`        varchar(20)  not null default '',
    `real_name`    varchar(255) not null default '',
    `phone_number` varchar(255) not null default '',
    `birthday`     date         not null default '1970-01-01',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
insert into
    `Person`
    (user_id, id_no, real_name, phone_number, birthday)
    values
        (5, '123451234512345000', 'hyc', '11111111111', '2020-07-01'),
        (5, '123451234512345001', 'djh', '11111111111', '2000-07-15'),
        (6, '123451234512345002', 'dmz', '11111111111', '1999-07-03'),
        (6, '123451234512345003', 'lbd', '11111111111', '2000-07-04');

drop table if exists `Comment`;
create table `Comment` (
    `id`       int(11)      not null auto_increment,
    `user_id`  int(11)      not null default 0,
    `hotel_id` int(11)      not null default 0,
    `score`    int(11)      not null default 0,
    `title`    varchar(255) not null default 0,
    `content`  varchar(255) not null default 0,
    primary key (`id`)
) engine = InnoDB
  default charset = utf8;
insert into
    `Comment` (user_id, hotel_id, score, title, content)
    values
        (5, 1, 5, 'Good', 'very good'),
        (6, 1, 2, 'Hell', 'very bad');

drop table if exists `CreditUp`;
create table `CreditUp` (
    `id`           int(11)       not null auto_increment primary key,
    `user_id`      int(11)       not null default 0,
    `credit_delta` double(64, 2) not null default 0
) default charset = utf8;

drop table if exists `OrderRoom`;
create table `OrderRoom` (
    `order_id`    int(11) not null,
    `room_id`     int(11) not null,
    `config_id`   int(11) not null, # REDUNDANT
    `resident_id` int(11) not null,
    primary key (order_id, config_id)
);

SET FOREIGN_KEY_CHECKS = 1;
