/*
Navicat MySQL Data Transfer

Source Server         : connection
Source Server Version : 80019
Source Host           : localhost:3306
Source Database       : cardgametestgen

Target Server Type    : MYSQL
Target Server Version : 80019
File Encoding         : 65001

Date: 2020-07-29 20:24:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity` (
  `activity_id` int NOT NULL,
  `activity_name` varchar(255) NOT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `start` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for black_list
-- ----------------------------
DROP TABLE IF EXISTS `black_list`;
CREATE TABLE `black_list` (
  `black_list_id` int NOT NULL,
  PRIMARY KEY (`black_list_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for black_list_block_ids
-- ----------------------------
DROP TABLE IF EXISTS `black_list_block_ids`;
CREATE TABLE `black_list_block_ids` (
  `black_list_black_list_id` int NOT NULL,
  `block_ids` int DEFAULT NULL,
  KEY `FKfxffx1d4ybny8uqmq2amdq9f9` (`black_list_black_list_id`),
  CONSTRAINT `FKfxffx1d4ybny8uqmq2amdq9f9` FOREIGN KEY (`black_list_black_list_id`) REFERENCES `black_list` (`black_list_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for card
-- ----------------------------
DROP TABLE IF EXISTS `card`;
CREATE TABLE `card` (
  `card_id` int NOT NULL,
  `attack` int DEFAULT NULL,
  `attack_range` int DEFAULT NULL,
  `card_name` varchar(255) DEFAULT NULL,
  `cd` double DEFAULT NULL,
  `defense` int DEFAULT NULL,
  `health_point` int DEFAULT NULL,
  `rarity` varchar(255) DEFAULT NULL,
  `speed` int DEFAULT NULL,
  `type` int DEFAULT NULL,
  PRIMARY KEY (`card_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for chapter
-- ----------------------------
DROP TABLE IF EXISTS `chapter`;
CREATE TABLE `chapter` (
  `chapter_id` int NOT NULL,
  `phase_no` int NOT NULL,
  `phase_type` int DEFAULT NULL,
  PRIMARY KEY (`chapter_id`,`phase_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for chapter_award_cards
-- ----------------------------
DROP TABLE IF EXISTS `chapter_award_cards`;
CREATE TABLE `chapter_award_cards` (
  `chapter_chapter_id` int NOT NULL,
  `award_cards` int DEFAULT NULL,
  KEY `FK9xby1v786ixnriq1qqagog3b4` (`chapter_chapter_id`),
  CONSTRAINT `FK9xby1v786ixnriq1qqagog3b4` FOREIGN KEY (`chapter_chapter_id`) REFERENCES `chapter` (`chapter_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for chapter_award_items
-- ----------------------------
DROP TABLE IF EXISTS `chapter_award_items`;
CREATE TABLE `chapter_award_items` (
  `chapter_chapter_id` int NOT NULL,
  `award_items` int DEFAULT NULL,
  `award_items_key` int NOT NULL,
  PRIMARY KEY (`chapter_chapter_id`,`award_items_key`),
  CONSTRAINT `FKniujckthypr32qs7omuhifu7v` FOREIGN KEY (`chapter_chapter_id`) REFERENCES `chapter` (`chapter_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for chapter_details
-- ----------------------------
DROP TABLE IF EXISTS `chapter_details`;
CREATE TABLE `chapter_details` (
  `card_id` int NOT NULL,
  `chapter_id` int NOT NULL,
  `phase_id` int NOT NULL,
  `position_id` int NOT NULL,
  PRIMARY KEY (`card_id`,`chapter_id`,`phase_id`,`position_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for chapter_phase
-- ----------------------------
DROP TABLE IF EXISTS `chapter_phase`;
CREATE TABLE `chapter_phase` (
  `chapter_id` int NOT NULL,
  `phase_id` int NOT NULL,
  PRIMARY KEY (`chapter_id`,`phase_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for chapter_phase_award_cards
-- ----------------------------
DROP TABLE IF EXISTS `chapter_phase_award_cards`;
CREATE TABLE `chapter_phase_award_cards` (
  `chapter_phase_chapter_id` int NOT NULL,
  `chapter_phase_phase_id` int NOT NULL,
  `award_cards` int DEFAULT NULL,
  KEY `FKmwbewc9xbnv17n4u7jb270qsj` (`chapter_phase_chapter_id`,`chapter_phase_phase_id`),
  CONSTRAINT `FKmwbewc9xbnv17n4u7jb270qsj` FOREIGN KEY (`chapter_phase_chapter_id`, `chapter_phase_phase_id`) REFERENCES `chapter_phase` (`chapter_id`, `phase_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for chapter_phase_award_items
-- ----------------------------
DROP TABLE IF EXISTS `chapter_phase_award_items`;
CREATE TABLE `chapter_phase_award_items` (
  `chapter_phase_chapter_id` int NOT NULL,
  `chapter_phase_phase_id` int NOT NULL,
  `award_items` int DEFAULT NULL,
  `award_items_key` int NOT NULL,
  PRIMARY KEY (`chapter_phase_chapter_id`,`chapter_phase_phase_id`,`award_items_key`),
  CONSTRAINT `FKtrbuaqug7nia3ecdhxnuaa47d` FOREIGN KEY (`chapter_phase_chapter_id`, `chapter_phase_phase_id`) REFERENCES `chapter_phase` (`chapter_id`, `phase_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for crash_reports
-- ----------------------------
DROP TABLE IF EXISTS `crash_reports`;
CREATE TABLE `crash_reports` (
  `report_id` int NOT NULL,
  `checked` bit(1) DEFAULT NULL,
  PRIMARY KEY (`report_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for enemy
-- ----------------------------
DROP TABLE IF EXISTS `enemy`;
CREATE TABLE `enemy` (
  `enemy_id` int NOT NULL,
  `attack` int DEFAULT NULL,
  `attack_range` int DEFAULT NULL,
  `cd` double DEFAULT NULL,
  `defense` int DEFAULT NULL,
  `enemy_name` varchar(255) DEFAULT NULL,
  `health_point` int DEFAULT NULL,
  `speed` int DEFAULT NULL,
  PRIMARY KEY (`enemy_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for friend_list
-- ----------------------------
DROP TABLE IF EXISTS `friend_list`;
CREATE TABLE `friend_list` (
  `friend_list_id` int NOT NULL,
  PRIMARY KEY (`friend_list_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for friend_list_friend_ids
-- ----------------------------
DROP TABLE IF EXISTS `friend_list_friend_ids`;
CREATE TABLE `friend_list_friend_ids` (
  `friend_list_friend_list_id` int NOT NULL,
  `friend_ids` int DEFAULT NULL,
  KEY `FKnbv80ae0l107fjkjm3rwcxlk4` (`friend_list_friend_list_id`),
  CONSTRAINT `FKnbv80ae0l107fjkjm3rwcxlk4` FOREIGN KEY (`friend_list_friend_list_id`) REFERENCES `friend_list` (`friend_list_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item` (
  `item_id` int NOT NULL,
  `item_name` varchar(255) DEFAULT NULL,
  `price` int DEFAULT NULL,
  PRIMARY KEY (`item_id`),
  UNIQUE KEY `UK_ku7fv295hhuscbb04easg4184` (`item_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mail
-- ----------------------------
DROP TABLE IF EXISTS `mail`;
CREATE TABLE `mail` (
  `mail_id` int NOT NULL,
  `mail_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`mail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mail_box
-- ----------------------------
DROP TABLE IF EXISTS `mail_box`;
CREATE TABLE `mail_box` (
  `mail_box_id` int NOT NULL,
  PRIMARY KEY (`mail_box_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mail_box_mail_ids
-- ----------------------------
DROP TABLE IF EXISTS `mail_box_mail_ids`;
CREATE TABLE `mail_box_mail_ids` (
  `mail_box_mail_box_id` int NOT NULL,
  `mail_ids` int DEFAULT NULL,
  KEY `FK7rd6fd8dfvstlb6gq6he2f8i3` (`mail_box_mail_box_id`),
  CONSTRAINT `FK7rd6fd8dfvstlb6gq6he2f8i3` FOREIGN KEY (`mail_box_mail_box_id`) REFERENCES `mail_box` (`mail_box_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mission
-- ----------------------------
DROP TABLE IF EXISTS `mission`;
CREATE TABLE `mission` (
  `mission_id` int NOT NULL,
  `mission_name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`mission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mission_award_items
-- ----------------------------
DROP TABLE IF EXISTS `mission_award_items`;
CREATE TABLE `mission_award_items` (
  `mission_mission_id` int NOT NULL,
  `award_items` int DEFAULT NULL,
  KEY `FKnf512iinmgwhnmhv8w2kndjei` (`mission_mission_id`),
  CONSTRAINT `FKnf512iinmgwhnmhv8w2kndjei` FOREIGN KEY (`mission_mission_id`) REFERENCES `mission` (`mission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mission_list
-- ----------------------------
DROP TABLE IF EXISTS `mission_list`;
CREATE TABLE `mission_list` (
  `mission_list_id` int NOT NULL,
  PRIMARY KEY (`mission_list_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mission_list_missions
-- ----------------------------
DROP TABLE IF EXISTS `mission_list_missions`;
CREATE TABLE `mission_list_missions` (
  `mission_list_mission_list_id` int NOT NULL,
  `missions_mission_id` int NOT NULL,
  KEY `FKbwxsir0run8okpyml0p6mqaqu` (`missions_mission_id`),
  KEY `FKr4vw7v5a3nxt9eh6kb25v4k7e` (`mission_list_mission_list_id`),
  CONSTRAINT `FKbwxsir0run8okpyml0p6mqaqu` FOREIGN KEY (`missions_mission_id`) REFERENCES `mission` (`mission_id`),
  CONSTRAINT `FKr4vw7v5a3nxt9eh6kb25v4k7e` FOREIGN KEY (`mission_list_mission_list_id`) REFERENCES `mission_list` (`mission_list_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for online_count_record
-- ----------------------------
DROP TABLE IF EXISTS `online_count_record`;
CREATE TABLE `online_count_record` (
  `record_time` datetime(6) NOT NULL,
  `in_play_count` int DEFAULT NULL,
  `online_count` int DEFAULT NULL,
  PRIMARY KEY (`record_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for own_card
-- ----------------------------
DROP TABLE IF EXISTS `own_card`;
CREATE TABLE `own_card` (
  `own_card_id` int NOT NULL,
  `accquire_date` datetime(6) DEFAULT NULL,
  `card_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `card_cur_exp` int DEFAULT NULL,
  `card_level` int DEFAULT NULL,
  `card_level_limit` int DEFAULT NULL,
  `repetitive_owns` int DEFAULT NULL,
  `enhance_attack` int DEFAULT NULL,
  `enhance_attack_range` int DEFAULT NULL,
  `enhance_defense` int DEFAULT NULL,
  `enhance_point` int DEFAULT NULL,
  `enhance_speed` int DEFAULT NULL,
  `left_points` int DEFAULT NULL,
  `enhancecd` int DEFAULT NULL,
  `enhancehp` int DEFAULT NULL,
  PRIMARY KEY (`own_card_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for own_item
-- ----------------------------
DROP TABLE IF EXISTS `own_item`;
CREATE TABLE `own_item` (
  `own_item_id` int NOT NULL,
  `item_count` int DEFAULT NULL,
  `accquire_date` datetime(6) DEFAULT NULL,
  `item_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`own_item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pve_record
-- ----------------------------
DROP TABLE IF EXISTS `pve_record`;
CREATE TABLE `pve_record` (
  `pve_record_id` int NOT NULL,
  `chapter_id` int DEFAULT NULL,
  `phase_id` int DEFAULT NULL,
  `record_time` datetime(6) DEFAULT NULL,
  `result` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`pve_record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pve_record_pos_record
-- ----------------------------
DROP TABLE IF EXISTS `pve_record_pos_record`;
CREATE TABLE `pve_record_pos_record` (
  `pve_record_pve_record_id` int NOT NULL,
  `pos_record` int DEFAULT NULL,
  `pos_record_key` int NOT NULL,
  PRIMARY KEY (`pve_record_pve_record_id`,`pos_record_key`),
  CONSTRAINT `FK8k9hjgmqn8wve8k6dsf76bjj8` FOREIGN KEY (`pve_record_pve_record_id`) REFERENCES `pve_record` (`pve_record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int NOT NULL,
  `access` bit(1) DEFAULT NULL,
  `credits` int DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `level` int DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `exp` int DEFAULT NULL,
  `grade` double DEFAULT NULL,
  `money` int DEFAULT NULL,
  `stamina` int DEFAULT NULL,
  `chi_knowledge` int DEFAULT NULL,
  `eng_knowledge` int DEFAULT NULL,
  `math_knowledge` int DEFAULT NULL,
  `cur_exp_point` int DEFAULT NULL,
  `identity` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_lqjrcobrh9jc8wpcar64q1bfh` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_login_record
-- ----------------------------
DROP TABLE IF EXISTS `user_login_record`;
CREATE TABLE `user_login_record` (
  `user_login_record_id` int NOT NULL,
  `login_time` datetime(6) DEFAULT NULL,
  `logout_time` datetime(6) DEFAULT NULL,
  `type` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`user_login_record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Procedure structure for generateActivity
-- ----------------------------
DROP PROCEDURE IF EXISTS `generateActivity`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `generateActivity`()
BEGIN
	#Routine body goes here...
	declare i int default 3;
	declare basic int default 10000;

	insert into activity(activity_id, activity_name, type, start)
			select 1, "SuperActivity True", true, current_timestamp();
	insert into activity(activity_id, activity_name, type, start)
			select 2, "SuperActivity False", true, current_timestamp();
#10000 
	while i <= basic do
		if(i % 2 = 0) then
			insert into activity(activity_id, activity_name, type, start)
			select i, "Test Activity True", true, current_timestamp();
		else	
			insert into activity(activity_id, activity_name, type, start)
			select i, "Test Activity False", false, current_timestamp();
		end if;
		set i = i + 1;
	end while;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for generateCard
-- ----------------------------
DROP PROCEDURE IF EXISTS `generateCard`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `generateCard`()
BEGIN
	#Routine body goes here...
	declare i int default 3;
	declare name varchar(15) default '';
	declare rarity varchar(10) default 'SSS';
	declare basic int default 10000;
	
	insert into card(card_id, attack, attack_range, card_name, cd, defense,
										health_point, rarity, speed, type)
	select 1, 100, 100, "SuperCard 1", 100, 100, 100, rarity, 60, 1;

	insert into card(card_id, attack, attack_range, card_name, cd, defense,
										health_point, rarity, speed, type)
	select 2, 100, 100, "SuperCard 2", 100, 100, 100, rarity, 60, 2;
#10000 
	while i <= basic do

		set name = LEFT(UUID(), 5);
		set name = CONCAT("card",name);

		if(i % 3 = 0) then 
			set rarity = 'SSS';
		elseif(i % 2 = 0)  then
			set rarity = 'SR';
		else 
			set rarity = 'R';
		end if;

		insert into card(card_id, attack, attack_range, card_name, cd, defense,
										health_point, rarity, speed, type)
		select i, 100 % i, 60 % i, name, 10 % i, 100 % i, 100 % i, rarity, 60 % i, i % 2;

		set i = i + 1;

	end while;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for generateEnemy
-- ----------------------------
DROP PROCEDURE IF EXISTS `generateEnemy`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `generateEnemy`()
BEGIN
	#Routine body goes here...
	declare i int default 1;
	declare name varchar(15) default '';
	declare basic int default 500000;
	
	insert into enemy(enemy_id, attack, attack_range, cd, defense,
										enemy_name, health_point, speed)
	select 1, 55, 60, 40, 40, "SuperEnemy 1", 60, 50;

	insert into enemy(enemy_id, attack, attack_range, cd, defense,
										enemy_name, health_point, speed)
	select 2, 55, 60, 40, 40, "SuperEnemy 1", 60, 50;

#500000 
	while i <= basic do

		set name = LEFT(UUID(), 5);
		set name = CONCAT("enemy ",name);

		insert into enemy(enemy_id, attack, attack_range, cd, defense,
											enemy_name, health_point, speed)
		select i, 55 % i, 60 % i, 40 % i, 40 % i, name, 60 % i, 50;

		set i = i + 1;

	end while;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for generateItem
-- ----------------------------
DROP PROCEDURE IF EXISTS `generateItem`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `generateItem`()
BEGIN
	#Routine body goes here...
	declare i int default 3;
	declare name varchar(15) default '';
	declare basic int default 10000;

	insert into item(item_id, item_name, price)
	select 1, "SuperItem 1", 60;

	insert into item(item_id, item_name, price)
	select 2, "SuperItem 2", 60;
#10000
	while i <= basic do

		set name = LEFT(UUID(), 5);
		set name = CONCAT("item", name);

		insert into item(item_id, item_name, price)
		select i, name, 60 % i;

		set i = i + 1;

	end while;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for generateMail
-- ----------------------------
DROP PROCEDURE IF EXISTS `generateMail`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `generateMail`()
BEGIN
	#Routine body goes here...
	declare i int default 1;
	declare j int default 1;
	declare basic int default 10;
	declare userBasic int default 10000;

#1000
	while i <= basic DO
		insert into mail(mail_id, mail_name)
		select i, CONCAT("Test Mail ", LEFT(UUID(), 5));
		set i = i + 1;
	end while;

	set i = 1;


	while i <= userBasic do
		insert into mail_box(mail_box_id) select i;
		set i = i + 1;
	end while;

	set i = 1;

	while i <= userBasic do
		#1000
		while j <= basic DO

			if((select count(*) from mail_box_mail_ids where mail_box_mail_box_id = i and mail_ids = j) = 0) then
				insert into mail_box_mail_ids(mail_box_mail_box_id, mail_ids)
				select i, j;
			end if;

			set j = j + 1;

		end while;
		
		set j = 1;

		set i = i + 1;

	end while;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for generateOwnCard
-- ----------------------------
DROP PROCEDURE IF EXISTS `generateOwnCard`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `generateOwnCard`()
BEGIN
	#Routine body goes here...
	declare i int default 1;
	declare j int default 1;
	declare k int default 1;
	declare name varchar(10) default '';
	declare basic int default 1000;

	#1000
	while i <= basic do
	#1000	
		while j <= basic do
			insert into own_card(own_card_id, accquire_date, card_id, user_id, card_cur_exp,
													card_level, card_level_limit, repetitive_owns,enhance_attack,
													enhance_attack_range, enhance_defense, enhance_point, enhance_speed,
													left_points, enhancecd, enhancehp)
			select k, CURRENT_TIMESTAMP(), i, j, 50, 25, 100, k % 10, 20, 20, 20, 20, 20, 80, 5, 100;
			set k = k + 1;
			set j = j + 1;
		end while;

		set j = 1;
		set i = i + 1;

	end while;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for generateOwnItem
-- ----------------------------
DROP PROCEDURE IF EXISTS `generateOwnItem`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `generateOwnItem`()
BEGIN
	#Routine body goes here...
	declare i int default 1;
	declare j int default 1;
	declare k int default 1;
	declare name varchar(10) default '';
	declare basic int default 1000;
#1000
	while i <= basic do
#1000
		while j <= basic do
			insert into own_item(own_item_id, item_count, accquire_date, item_id, user_id)
			select k, 20, CURRENT_TIMESTAMP(), i, j;
			set k = k + 1;
			set j = j + 1;
		end while;

		set j = 1;
		set i = i + 1;

	end while;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for generateUser
-- ----------------------------
DROP PROCEDURE IF EXISTS `generateUser`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `generateUser`()
BEGIN
	#Routine body goes here...
	declare i int default 3;
	declare pwd varchar(10) default '';
	declare name varchar(15) default '';
	declare email varchar(20) default '';
	declare role varchar(12) default 'ROLE_USER';
	declare basic int default 10000;

	insert into user(user_id, access, credits, email, level, password, phone_number, user_name,
									exp, grade, money, stamina, chi_knowledge, eng_knowledge, math_knowledge, cur_exp_point, identity)
	select 1, 1, 50, email, 50, 111111, 18321798666, "SuperAdmin",
					100 % i, 4.0, 100 % i, 50 % i, 100 % i, 100 % i, 100 % i, 100 % i, "ROLE_ADMIN";

	insert into user(user_id, access, credits, email, level, password, phone_number, user_name,
									exp, grade, money, stamina, chi_knowledge, eng_knowledge, math_knowledge, cur_exp_point, identity)
	select 2, 1, 50, email, 50, 111111, 18321798666, "SuperUser",
					100 % i, 4.0, 100 % i, 50 % i, 100 % i, 100 % i, 100 % i, 100 % i, "ROLE_USER";

	#while i <= 10000 do
	while i <= basic do
		set name = LEFT(UUID(), 5);
		set name = CONCAT("user", name);
		set email = CONCAT(name, "@email.com");

		IF(i % 2 = 0) THEN
			set role = "ROLE_USER";
		ELSE
			set role = "ROLE_ADMIN";
		end if;

		#if(i <= 900000) then
		if(i <= basic * 0.9) then
			insert into user(user_id, access, credits, email, level, password, phone_number, user_name,
											exp, grade, money, stamina, chi_knowledge, eng_knowledge, math_knowledge, cur_exp_point, identity)
			select i, 1, 50, email, 50, 111111, 18321798666, name,
							100 % i, 4.0, 100 % i, 50 % i, 100 % i, 100 % i, 100 % i, 100 % i, role;
		else
			insert into user(user_id, access, credits, email, level, password, phone_number, user_name,
											exp, grade, money, stamina, chi_knowledge, eng_knowledge, math_knowledge, cur_exp_point, identity)
			select i, 0, 50, email, 50, 111111, 18321798666, name,
							100 % i, 4.0, 100 % i, 50 % i, 100 % i, 100 % i, 100 % i, 100 % i, role;
		end if;


		set i = i + 1;

	end while;

END
;;
DELIMITER ;
