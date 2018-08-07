DROP SCHEMA IF EXISTS `hotel`;
CREATE SCHEMA IF NOT EXISTS `hotel` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `hotel`;
CREATE TABLE IF NOT EXISTS `hotel`.`user` (
  `user_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(60) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `surname` VARCHAR(100) NOT NULL,
  `tel_number` VARCHAR(20) NOT NULL,
  `birthday` DATE NOT NULL,
  `discount` INT(11) UNSIGNED NOT NULL DEFAULT 0,
  `gender_male` TINYINT(1) UNSIGNED NOT NULL,
  `blocked` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0,
  `role` ENUM('customer', 'admin', 'moder') NOT NULL DEFAULT 'customer',
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  UNIQUE INDEX `tel_number_UNIQUE` (`tel_number` ASC))
  ENGINE = InnoDB AUTO_INCREMENT = 10;

CREATE TABLE IF NOT EXISTS `hotel`.`invoice` (
  `invoice_id` BIGINT(255) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` INT UNSIGNED NOT NULL,
  `invoice_date` DATE NOT NULL,
  `nights_count` INT UNSIGNED NOT NULL,
  `total_payment` DECIMAL(10,2) UNSIGNED NOT NULL,
  `invoice_status` ENUM('invoiced', 'pay_in_hotel', 'instant_pay') NOT NULL DEFAULT 'invoiced',
  `payed` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`invoice_id`),
  CONSTRAINT `invoice_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `hotel`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `hotel`.`room` (
  `room_number` INT UNSIGNED NOT NULL,
  `berth_count` TINYINT(5) UNSIGNED NOT NULL,
  `comfort_level` TINYINT(5) UNSIGNED NOT NULL,
  `price_per_night` DECIMAL(20,2) UNSIGNED NOT NULL,
  `picture_link` VARCHAR(255) NOT NULL,
  `available_status` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1,
  PRIMARY KEY (`room_number`),
  UNIQUE INDEX `room_number_UNIQUE` (`room_number` ASC),
  UNIQUE INDEX `picture_link_UNIQUE` (`picture_link` ASC))
  ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `hotel`.`booking` (
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
    REFERENCES `hotel`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `booking_invoice_id`
    FOREIGN KEY (`invoice_id`)
    REFERENCES `hotel`.`invoice` (`invoice_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `hotel`.`room_in_booking` (
  `booking_id` BIGINT(255) UNSIGNED NOT NULL,
  `room_number` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`booking_id`, `room_number`),
  INDEX `room_in_booking_room_number_idx` (`room_number` ASC),
  INDEX `room_in_booking_booking_id_idx` (`booking_id` ASC),
  CONSTRAINT `room_in_booking_booking_id`
    FOREIGN KEY (`booking_id`)
    REFERENCES `hotel`.`booking` (`booking_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `room_in_booking_room_number`
    FOREIGN KEY (`room_number`)
    REFERENCES `hotel`.`room` (`room_number`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
    ENGINE = InnoDB;

DELIMITER $$
CREATE PROCEDURE findFreeRooms
  (IN from_date DATE,
  IN to_date DATE,
  IN adults INT,
  IN children INT,
  IN comfort INT,
  IN start_index INT,
  IN offset_value INT)
  BEGIN
    SELECT
      `room`.`room_number`,
      `room`.`berth_count`,
      `room`.`comfort_level`,
      `room`.`price_per_night`,
      `room`.`picture_link`,
      `room`.`available_status`
    FROM `room`
      JOIN (SELECT `room`.`room_number` FROM `room`
      ORDER BY `room`.`room_number` LIMIT start_index, offset_value)
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
  END $$
DELIMITER ;

DELIMITER $$
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
  END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE defineNumberOfOccupiedRoom(from_date DATE, to_date DATE, room_num INT)
  BEGIN
    SELECT `room`.`room_number`
    FROM `room`
           JOIN `room_in_booking` ON `room`.`room_number` = `room_in_booking`.`room_number`
           JOIN `booking` ON `booking`.`booking_id` = `room_in_booking`.`booking_id`
    WHERE `room`.`room_number` = room_num
            AND (
              (from_date NOT BETWEEN `booking`.`check_in` AND `booking`.`check_out`)
                OR
              (to_date NOT BETWEEN `booking`.`check_in` AND `booking`.`check_out`)
              )
       OR (
              (`booking`.`check_in` NOT BETWEEN from_date AND to_date)
                OR
              (`booking`.`check_out` NOT BETWEEN from_date AND to_date)
              );
  END $$
DELIMITER ;