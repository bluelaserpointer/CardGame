-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: cardgame
-- ------------------------------------------------------
-- Server version	8.0.19
set @userAmount = 10000;
set @cardAmount = 200;
set @itemAmount = 200;
set @userAmountWhoOwnSomething = @userAmount; #50: for function test | @userAmount: for pressure test
set @activityAmount = 10000;
set @enemyAmount = 200;
set @mailAmountPerUser = 10;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping routines for database 'cardgame'
--
/*!50003 DROP PROCEDURE IF EXISTS `deleteAll` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteAll`()
BEGIN

	#Routine body goes here...

	delete from user;

	delete from own_item;

	delete from own_card;

	delete from mail_box_mail_ids;

	delete from mail;

	delete from mail_box;

	delete from item;

	delete from enemy;

	delete from card;

	delete from activity;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `generateActivity` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `generateActivity`()
BEGIN

	#Routine body goes here...

	declare i int default 3;
    
	insert into activity(activity_id, activity_name, type, start)

			select 1, "SuperActivity True", true, current_timestamp();

	insert into activity(activity_id, activity_name, type, start)

			select 2, "SuperActivity False", false, current_timestamp();

	while i <= @activityAmount do

		if(i % 2 = 0) then

			insert into activity(activity_id, activity_name, type, start)

			select i, "Test Activity True", true, current_timestamp();

		else	

			insert into activity(activity_id, activity_name, type, start)

			select i, "Test Activity False", false, current_timestamp();

		end if;

		set i = i + 1;

	end while;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `generateAll` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `generateAll`()
BEGIN

	#Routine body goes here...

	call deleteAll();

	call generateUser();

	call generateCard();

	call generateItem();

	call generateOwnCard();

	call generateOwnItem();

	call generateMail();

	call generateActivity();

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `generateCard` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `generateCard`()
BEGIN

	#Routine body goes here...

	declare i int default 3;

	declare name varchar(15) default '';

	declare rarity varchar(10) default 'SSR';

	insert into card(card_id, attack, attack_range, card_name, cd, defense,

										health_point, rarity, speed, type)

	select 1, 100, 100, "SuperCard 1", 100, 100, 100, rarity, 60, 1;

	insert into card(card_id, attack, attack_range, card_name, cd, defense,

										health_point, rarity, speed, type)

	select 2, 100, 100, "SuperCard 2", 100, 100, 100, rarity, 60, 2;

	while i <= @cardAmount do

		set name = LEFT(UUID(), 5);

		set name = CONCAT("card",name);

		if(i % 3 = 0) then 

			set rarity = 'SSR';

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

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `generateEnemy` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `generateEnemy`()
BEGIN

	#Routine body goes here...

	declare i int default 3;

	declare name varchar(15) default '';

	insert into enemy(enemy_id, attack, attack_range, cd, defense,

										enemy_name, health_point, speed)

	select 1, 55, 60, 40, 40, "SuperEnemy 1", 60, 50;

	insert into enemy(enemy_id, attack, attack_range, cd, defense,

										enemy_name, health_point, speed)

	select 2, 55, 60, 40, 40, "SuperEnemy 1", 60, 50;

	while i <= @enemyAmount do

		set name = LEFT(UUID(), 5);

		set name = CONCAT("enemy ",name);

		insert into enemy(enemy_id, attack, attack_range, cd, defense,

											enemy_name, health_point, speed)

		select i, 55 % i, 60 % i, 40 % i, 40 % i, name, 60 % i, 50;

		set i = i + 1;

	end while;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `generateItem` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `generateItem`()
BEGIN

	#Routine body goes here...

	declare i int default 3;

	declare name varchar(15) default '';
    
	insert into item(item_id, item_name, price)

	select 1, "SuperItem 1", 60;
    
	insert into item(item_id, item_name, price)

	select 2, "SuperItem 2", 60;

	while i <= @itemAmount do

		set name = LEFT(UUID(), 5);

		set name = CONCAT("item", name);

		insert into item(item_id, item_name, price)

		select i, name, 60 % i;

		set i = i + 1;

	end while;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `generateMail` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `generateMail`()
BEGIN

	#Routine body goes here...

	declare i int default 1;

	declare j int default 1;

	while i <= @MailAmountPerUser DO

		insert into mail(mail_id, mail_name)

		select i, CONCAT("Test Mail ", LEFT(UUID(), 5));

		set i = i + 1;

	end while;

	set i = 1;

	while i <= @userAmountWhoOwnSomething do

		insert into mail_box(mail_box_id) select i;

		set i = i + 1;

	end while;

	set i = 1;

	while i <= @userAmountWhoOwnSomething do

		while j <= @mailAmountPerUser DO

			if((select count(*) from mail_box_mail_ids where mail_box_mail_box_id = i and mail_ids = j) = 0) then

				insert into mail_box_mail_ids(mail_box_mail_box_id, mail_ids)

				select i, j;

			end if;

			set j = j + 1;

		end while;

		set j = 1;

		set i = i + 1;

	end while;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `generateOwnCard` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `generateOwnCard`()
BEGIN

	#Routine body goes here...

	declare i int default 1;

	declare j int default 1;

	declare k int default 1;

	declare name varchar(10) default '';
    #cards
	while i <= @cardAmount do
		#users
		while j <= @userAmountWhoOwnSomething do
        
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

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `generateOwnItem` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `generateOwnItem`()
BEGIN

	#Routine body goes here...

	declare i int default 1;

	declare j int default 1;

	declare k int default 1;

	declare name varchar(10) default '';

#items
	while i <= @itemAmount do
#users
		while j <= @userAmountWhoOwnSomething do
        
			insert into own_item(own_item_id, item_count, accquire_date, item_id, user_id)

			select k, 20, CURRENT_TIMESTAMP(), i, j;

			set k = k + 1;

			set j = j + 1;

		end while;

		set j = 1;

		set i = i + 1;

	end while;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `generateUser` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `generateUser`()
BEGIN

	#Routine body goes here...

	declare i int default 3;

	declare pwd varchar(10) default '';

	declare name varchar(15) default '';

	declare email varchar(20) default '';

	declare role varchar(12) default 'ROLE_USER';

	insert into user(user_id, access, credits, email, level, password, phone_number, user_name,

									grade, money, stamina, chi_knowledge, eng_knowledge, math_knowledge, cur_exp_point, identity)

	select 1, 1, 50, email, 50, 111111, 18321798666, "SuperAdmin", 4.0, 100 % i, 50 % i, 100 % i, 100 % i, 100 % i, 100 % i, "ROLE_ADMIN";

	insert into user(user_id, access, credits, email, level, password, phone_number, user_name,

									grade, money, stamina, chi_knowledge, eng_knowledge, math_knowledge, cur_exp_point, identity)

	select 2, 1, 50, email, 50, 111111, 18321798666, "SuperUser", 4.0, 100 % i, 50 % i, 100 % i, 100 % i, 100 % i, 100 % i, "ROLE_USER";

	while i <= @userAmount do

		set name = LEFT(UUID(), 5);

		set name = CONCAT("user", name);

		set email = CONCAT(name, "@email.com");

		IF(i % 2 = 0) THEN

			set role = "ROLE_USER";

		ELSE

			set role = "ROLE_ADMIN";

		end if;

		if(i <= @userAmount * 0.9) then

			insert into user(user_id, access, credits, email, level, password, phone_number, user_name,

											grade, money, stamina, chi_knowledge, eng_knowledge, math_knowledge, cur_exp_point, identity)

			select i, 1, 50, email, 50, 111111, 18321798666, name, 4.0, 100 % i, 50 % i, 100 % i, 100 % i, 100 % i, 100 % i, role;

		else

			insert into user(user_id, access, credits, email, level, password, phone_number, user_name,

											grade, money, stamina, chi_knowledge, eng_knowledge, math_knowledge, cur_exp_point, identity)

			select i, 0, 50, email, 50, 111111, 18321798666, name, 4.0, 100 % i, 50 % i, 100 % i, 100 % i, 100 % i, 100 % i, role;

		end if;

		set i = i + 1;

	end while;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-07-31 12:11:43
