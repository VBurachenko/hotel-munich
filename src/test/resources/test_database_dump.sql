DROP SCHEMA IF EXISTS `test_hotel`;
CREATE SCHEMA IF NOT EXISTS `test_hotel` DEFAULT CHARACTER SET utf8 COLLATE utf8_swedish_ci;
USE `test_hotel`;
CREATE TABLE IF NOT EXISTS `test_hotel`.`user` (
  `user_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(60) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `surname` VARCHAR(100) NOT NULL,
  `tel_number` VARCHAR(20) NOT NULL,
  `birthday` DATE NOT NULL,
  `discount` INT(11) UNSIGNED NOT NULL DEFAULT 0,
  `gender_male` TINYINT(1) UNSIGNED NOT NULL,
  `blocking` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0,
  `role` ENUM('customer', 'admin', 'moder') NOT NULL DEFAULT 'customer',
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  UNIQUE INDEX `tel_number_UNIQUE` (`tel_number` ASC))
  ENGINE = InnoDB AUTO_INCREMENT = 10 DEFAULT CHARACTER SET utf8 COLLATE utf8_swedish_ci;

CREATE TABLE IF NOT EXISTS `test_hotel`.`invoice` (
  `invoice_id` BIGINT(255) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` INT UNSIGNED NOT NULL,
  `invoice_date` DATE NOT NULL,
  `nights_count` INT UNSIGNED NOT NULL,
  `total_payment` DECIMAL(10,2) UNSIGNED NOT NULL,
  `invoice_status` ENUM('invoiced', 'pay_in_hotel', 'instant_pay') NOT NULL DEFAULT 'invoiced',
  `payed` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`invoice_id`),
  CONSTRAINT `invoice_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `test_hotel`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
    ENGINE = InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_swedish_ci;

CREATE TABLE IF NOT EXISTS `test_hotel`.`room` (
  `room_number` INT UNSIGNED NOT NULL,
  `berth_count` TINYINT(5) UNSIGNED NOT NULL,
  `comfort_level` TINYINT(5) UNSIGNED NOT NULL,
  `price_per_night` DECIMAL(20,2) UNSIGNED NOT NULL,
  `picture_link` VARCHAR(255) NOT NULL,
  `available_status` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1,
  PRIMARY KEY (`room_number`),
  UNIQUE INDEX `room_number_UNIQUE` (`room_number` ASC),
  UNIQUE INDEX `picture_link_UNIQUE` (`picture_link` ASC))
  ENGINE = InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_swedish_ci;

CREATE TABLE IF NOT EXISTS `test_hotel`.`booking` (
  `booking_id` BIGINT(255) UNSIGNED NOT NULL,
  `check_in` DATE NOT NULL,
  `check_out` DATE NOT NULL,
  `adult_count` TINYINT(9) NOT NULL,
  `child_count` TINYINT(9) NOT NULL,
  `user_id` INT UNSIGNED NOT NULL,
  `invoice_id` BIGINT(255) UNSIGNED NULL,
  `booking_status` ENUM ('registered', 'confirmed', 'performing', 'completed', 'rejected') NOT NULL DEFAULT 'registered',
  PRIMARY KEY (`booking_id`, `check_in`, `check_out`),
  UNIQUE INDEX `booking_id_UNIQUE` (`booking_id` ASC),
  INDEX `booking_user_id_idx` (`user_id` ASC),
  INDEX `booking_invoice_id_idx` (`invoice_id` ASC),
  CONSTRAINT `booking_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `test_hotel`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `booking_invoice_id`
    FOREIGN KEY (`invoice_id`)
    REFERENCES `test_hotel`.`invoice` (`invoice_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
    ENGINE = InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_swedish_ci;

CREATE TABLE IF NOT EXISTS `test_hotel`.`room_in_booking` (
  `booking_id` BIGINT(255) UNSIGNED NOT NULL,
  `room_number` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`booking_id`, `room_number`),
  INDEX `room_in_booking_room_number_idx` (`room_number` ASC),
  INDEX `room_in_booking_booking_id_idx` (`booking_id` ASC),
  CONSTRAINT `room_in_booking_booking_id`
    FOREIGN KEY (`booking_id`)
    REFERENCES `test_hotel`.`booking` (`booking_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `room_in_booking_room_number`
    FOREIGN KEY (`room_number`)
    REFERENCES `test_hotel`.`room` (`room_number`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
    ENGINE = InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_swedish_ci;

USE `test_hotel`;

INSERT INTO room(room_number, berth_count, comfort_level, price_per_night, picture_link,  available_status)
VALUES (1001, 3, 4, 56.4, 'few2rf', 1);

INSERT INTO room(room_number, berth_count, comfort_level, price_per_night, picture_link,  available_status)
VALUES (1002, 2, 3, 52.5, 'fej7dwed', 1);

INSERT INTO room(room_number, berth_count, comfort_level, price_per_night, picture_link,  available_status)
VALUES (1003, 1, 5, 70.4, 'btg0jgr', 1);

INSERT INTO room(room_number, berth_count, comfort_level, price_per_night, picture_link,  available_status)
VALUES (1004, 4, 5, 80.2, 'xwe3edcdd', 1);

INSERT INTO room(room_number, berth_count, comfort_level, price_per_night, picture_link,  available_status)
VALUES (1005, 5, 4, 55.9, 'vtg5fwertg', 1);

INSERT INTO room(room_number, berth_count, comfort_level, price_per_night, picture_link,  available_status)
VALUES (1006, 3, 5, 95.8, 'c44rfwee', 1);

INSERT INTO room(room_number, berth_count, comfort_level, price_per_night, picture_link,  available_status)
VALUES (1007, 2, 3, 50.5, 'crff10dew', 0);

INSERT INTO room(room_number, berth_count, comfort_level, price_per_night, picture_link,  available_status)
VALUES (2001, 3, 4, 56.4, 'fer4rdwfcr', 1);

INSERT INTO room(room_number, berth_count, comfort_level, price_per_night, picture_link,  available_status)
VALUES (2002, 2, 3, 52.5, 'fed55swewed', 1);

INSERT INTO room(room_number, berth_count, comfort_level, price_per_night, picture_link,  available_status)
VALUES (2003, 1, 5, 70.4, 'm6ygteht', 1);

INSERT INTO room(room_number, berth_count, comfort_level, price_per_night, picture_link,  available_status)
VALUES (2004, 4, 5, 80.2, 'x7hereder', 1);

INSERT INTO room(room_number, berth_count, comfort_level, price_per_night, picture_link,  available_status)
VALUES (2005, 5, 4, 55.9, 'vf34rerf', 1);

INSERT INTO room(room_number, berth_count, comfort_level, price_per_night, picture_link,  available_status)
VALUES (2006, 3, 5, 95.8, 'cr77gerefwe', 1);

INSERT INTO room(room_number, berth_count, comfort_level, price_per_night, picture_link,  available_status)
VALUES (2007, 2, 3, 50.5, 've9jhdfrerf', 1);

INSERT INTO `user`
(`email`, `password`, `name`, `surname`, `tel_number`, `birthday`, `discount`, `gender_male`, `blocking`, `role`)
VALUES ('qwert@gmail.com', '3d5326c8717e6b6426b31ec9819a0baf', 'Игорь', 'Петров', '+375447754924', '2018-04-15', 0, 1, 0, 'customer');

INSERT INTO `user`
(`email`, `password`, `name`, `surname`, `tel_number`, `birthday`, `discount`, `gender_male`, `blocking`, `role`)
VALUES ('ytrew@gmail.com', '3d5326c8717e6b6426b31ec9819a0baf', 'Cdewdw', 'Pfrew', '+375447754921', '2018-09-23', 0, 1, 0, 'customer');

INSERT INTO `user`
(`email`, `password`, `name`, `surname`, `tel_number`, `birthday`, `discount`, `gender_male`, `blocking`, `role`)
VALUES ('asdfg@gmail.com', '3d5326c8717e6b6426b31ec9819a0baf', 'Johnatan', 'Davis','+375447754922', '2018-09-23', 10, 1, 1, 'customer');

INSERT INTO `user`
(`user_id`, `email`, `password`, `name`, `surname`, `tel_number`, `birthday`, `discount`, `gender_male`, `blocking`, `role`)
VALUES (2, 'xswedc@gmail.com', '3d5326c8717e6b6426b31ec9819a0baf', 'Евгений', 'Крюк','+375447754988', '2018-09-01', 0, 1, 0, 'admin');

INSERT INTO `user`
(`user_id`, `email`, `password`, `name`, `surname`, `tel_number`, `birthday`, `discount`, `gender_male`, `blocking`, `role`)
VALUES (1, 'zxcvb@gmail.com', '3d5326c8717e6b6426b31ec9819a0baf', 'Ann', 'Volkova','+375447754956', '2018-09-15', 0, 0, 0, 'moder');

INSERT INTO `user`
(`email`, `password`, `name`, `surname`, `tel_number`, `birthday`, `discount`, `gender_male`, `blocking`, `role`)
VALUES ('poiut@gmail.com', '3d5326c8717e6b6426b31ec9819a0baf', 'Natali', 'Kir','+375447754945', '2018-09-24', 15, 0, 0, 'customer');

INSERT INTO `user`
(`email`, `password`, `name`, `surname`, `tel_number`, `birthday`, `discount`, `gender_male`, `blocking`, `role`)
VALUES ('stas@gmail.com', '3d5326c8717e6b6426b31ec9819a0baf', 'Stas', 'Ilyasov','+375447754432', '2018-01-06', 5, 1, 0, 'customer');

INSERT INTO `booking`
(booking_id, check_in, check_out, adult_count, child_count, user_id)
VALUES (127062018, '2018-07-03', '2018-07-09', 2, 1, 10);

INSERT INTO `room_in_booking`
(booking_id, room_number)
VALUES (127062018, 1006);

INSERT INTO `booking`
(booking_id, check_in, check_out, adult_count, child_count, user_id)
VALUES (227062018, '2018-07-06', '2018-07-15', 2, 0, 12);

INSERT INTO `room_in_booking`
(booking_id, room_number)
VALUES (227062018, 1003);

INSERT INTO `room_in_booking`
(booking_id, room_number)
VALUES (227062018, 2003);

INSERT INTO `booking`
(booking_id, check_in, check_out, adult_count, child_count, user_id)
VALUES (327062018, '2018-07-05', '2018-07-26', 6, 2, 14);

INSERT INTO `room_in_booking`
(booking_id, room_number)
VALUES (327062018, 1002);

INSERT INTO `room_in_booking`
(booking_id, room_number)
VALUES (327062018, 2001);

INSERT INTO `room_in_booking`
(booking_id, room_number)
VALUES (327062018, 2006);

INSERT INTO `invoice` (invoice_id, user_id, invoice_date, nights_count, total_payment, invoice_status, payed)
VALUES (20, 14, '2018-06-28', 21, 4298.7, 'instant_pay', 1);
UPDATE `booking` SET `booking`.`invoice_id` = 20, `booking`.`booking_status` = 'confirmed' WHERE `booking`.`booking_id` = 327062018;

INSERT INTO `invoice` (invoice_id, user_id, invoice_date, nights_count, total_payment, invoice_status, payed)
VALUES (21, 10, '2018-06-29', 6, 574.8, 'pay_in_hotel', 0);
UPDATE `booking` SET `booking`.`invoice_id` = 21, `booking`.`booking_status` = 'confirmed' WHERE `booking`.`booking_id` = 127062018;

INSERT INTO `invoice` (invoice_id, user_id, invoice_date, nights_count, total_payment, invoice_status, payed)
VALUES (22, 12, '2018-06-28', 9, 1267.2, 'instant_pay', 1);
UPDATE `booking` SET `booking`.`invoice_id` = 22 WHERE `booking`.`booking_id` = 227062018;


CREATE PROCEDURE findFreeRooms
  (IN from_date    DATE,
   IN to_date      DATE,
   IN adults       INT,
   IN children     INT,
   IN comfort      INT,
   IN start_index  INT,
   IN offset_value INT)
  BEGIN
    SELECT `room`.`room_number`,
           `room`.`berth_count`,
           `room`.`comfort_level`,
           `room`.`price_per_night`,
           `room`.`picture_link`,
           `room`.`available_status`
    FROM `room`
           JOIN (SELECT `room`.`room_number` FROM `room` ORDER BY `room_number` LIMIT start_index, offset_value)
        AS `number` ON `number`.`room_number` = `room`.`room_number`
    WHERE `room`.`room_number` NOT IN
          (SELECT `room_in_booking`.`room_number`
           FROM `room_in_booking`
                  JOIN `booking` ON `room_in_booking`.`booking_id` = `booking`.`booking_id`
           WHERE (
                     (
                         (from_date BETWEEN `booking`.`check_in` AND `booking`.`check_out`)
                           OR
                         (to_date BETWEEN `booking`.`check_in` AND `booking`.`check_out`)
                         )
                       OR (
                         (`booking`.`check_in` BETWEEN from_date AND to_date)
                           OR
                         (`booking`.`check_out` BETWEEN from_date AND to_date)
                         )
                     )
          )
      AND `room`.`comfort_level` >= comfort
      AND `room`.`berth_count` >= adults + children
      AND `room`.`available_status` > 0;
  END;



CREATE PROCEDURE defineNumberOfOccupiedRoom (from_date DATE, to_date DATE, room_num INT)
  BEGIN
    SELECT
      `room`.`room_number`
    FROM `room`
      JOIN `room_in_booking` ON `room`.`room_number` = `room_in_booking`.`room_number`
      JOIN `booking` ON `booking`.`booking_id` = `room_in_booking`.`booking_id`
    WHERE `room`.`room_number` = room_num
          AND (
              (
                  (from_date NOT BETWEEN `booking`.`check_in` AND `booking`.`check_out`)
                    OR
                  (to_date NOT BETWEEN `booking`.`check_in` AND `booking`.`check_out`)
                  )
                OR (
                  (`booking`.`check_in` NOT BETWEEN from_date AND to_date)
                    OR
                  (`booking`.`check_out` NOT BETWEEN from_date AND to_date)
                  )
              );
  END ;

CREATE PROCEDURE getCountOfFreeRooms
  (IN from_date DATE,
   IN to_date DATE,
   IN adults INT,
   IN children INT,
   IN comfort INT)
  BEGIN
    SELECT
           COUNT(*)
    FROM `room`
    WHERE `room`.`room_number` NOT IN
          (SELECT `room_in_booking`.`room_number`
           FROM `room_in_booking`
                  JOIN `booking` ON `room_in_booking`.`booking_id` = `booking`.`booking_id`
           WHERE (
                     (
                         (from_date BETWEEN `booking`.`check_in` AND `booking`.`check_out`)
                           OR
                         (to_date BETWEEN `booking`.`check_in` AND `booking`.`check_out`)
                         )
                       OR (
                         (`booking`.`check_in` BETWEEN from_date AND to_date)
                           OR
                         (`booking`.`check_out` BETWEEN from_date AND to_date)
                         )
                     )
          )
      AND `room`.`comfort_level` >= comfort
      AND `room`.`berth_count` >= adults + children
      AND `room`.`available_status` > 0;
  END ;