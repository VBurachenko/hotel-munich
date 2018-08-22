INSERT INTO hotel.room(room_number, berth_count, comfort_level, price_per_night, picture_link,  available_status)
VALUES (1001, 3, 4, 56.4, 'few2rf', 1);

INSERT INTO hotel.room(room_number, berth_count, comfort_level, price_per_night, picture_link,  available_status)
VALUES (1002, 2, 3, 52.5, 'fej7dwed', 1);

INSERT INTO hotel.room(room_number, berth_count, comfort_level, price_per_night, picture_link,  available_status)
VALUES (1003, 1, 5, 70.4, 'btg0jgr', 1);

INSERT INTO hotel.room(room_number, berth_count, comfort_level, price_per_night, picture_link,  available_status)
VALUES (1004, 4, 5, 80.2, 'xwe3edcdd', 1);

INSERT INTO hotel.room(room_number, berth_count, comfort_level, price_per_night, picture_link,  available_status)
VALUES (1005, 5, 4, 55.9, 'vtg5fwertg', 1);

INSERT INTO hotel.room(room_number, berth_count, comfort_level, price_per_night, picture_link,  available_status)
VALUES (1006, 3, 5, 95.8, 'c44rfwee', 1);

INSERT INTO hotel.room(room_number, berth_count, comfort_level, price_per_night, picture_link,  available_status)
VALUES (1007, 2, 3, 50.5, 'crff10dew', 0);

INSERT INTO hotel.room(room_number, berth_count, comfort_level, price_per_night, picture_link,  available_status)
VALUES (2001, 3, 4, 56.4, 'fer4rdwfcr', 1);

INSERT INTO hotel.room(room_number, berth_count, comfort_level, price_per_night, picture_link,  available_status)
VALUES (2002, 2, 3, 52.5, 'fed55swewed', 1);

INSERT INTO hotel.room(room_number, berth_count, comfort_level, price_per_night, picture_link,  available_status)
VALUES (2003, 1, 5, 70.4, 'm6ygteht', 1);

INSERT INTO hotel.room(room_number, berth_count, comfort_level, price_per_night, picture_link,  available_status)
VALUES (2004, 4, 5, 80.2, 'x7hereder', 1);

INSERT INTO hotel.room(room_number, berth_count, comfort_level, price_per_night, picture_link,  available_status)
VALUES (2005, 5, 4, 55.9, 'vf34rerf', 1);

INSERT INTO hotel.room(room_number, berth_count, comfort_level, price_per_night, picture_link,  available_status)
VALUES (2006, 3, 5, 95.8, 'cr77gerefwe', 1);

INSERT INTO hotel.room(room_number, berth_count, comfort_level, price_per_night, picture_link,  available_status)
VALUES (2007, 2, 3, 50.5, 've9jhdfrerf', 1);

INSERT INTO `hotel`.`user`
          (`email`, `password`, `name`, `surname`, `tel_number`, `birthday`, `discount`, `gender_male`, `blocking`, `role`)
VALUES ('qwert@gmail.com', 'f97e2f1f37d10977c25a118f21c302f8', 'Игорь', 'Петров', '+375447754924', '2018-04-15', 0, 1, 0, 'customer');

INSERT INTO `hotel`.`user`
          (`email`, `password`, `name`, `surname`, `tel_number`, `birthday`, `discount`, `gender_male`, `blocking`, `role`)
VALUES ('ytrew@gmail.com', 'f97e2f1f37d10977c25a118f21c302f8', 'Cdewdw', 'Pfrew', '+375447754921', '2018-09-23', 0, 1, 0, 'customer');

INSERT INTO `hotel`.`user`
          (`email`, `password`, `name`, `surname`, `tel_number`, `birthday`, `discount`, `gender_male`, `blocking`, `role`)
VALUES ('asdfg@gmail.com', 'f97e2f1f37d10977c25a118f21c302f8', 'Johnatan', 'Davis','+375447754922', '2018-09-23', 10, 1, 1, 'customer');

INSERT INTO `hotel`.`user`
          (`user_id`, `email`, `password`, `name`, `surname`, `tel_number`, `birthday`, `discount`, `gender_male`, `blocking`, `role`)
VALUES (2, 'xswedc@gmail.com', 'f97e2f1f37d10977c25a118f21c302f8', 'Евгений', 'Крюк','+375447754988', '2018-09-01', 0, 1, 0, 'admin');

INSERT INTO `hotel`.`user`
          (`user_id`, `email`, `password`, `name`, `surname`, `tel_number`, `birthday`, `discount`, `gender_male`, `blocking`, `role`)
VALUES (1, 'zxcvb@gmail.com', 'f97e2f1f37d10977c25a118f21c302f8', 'Ann', 'Volkova','+375447754956', '2018-09-15', 0, 0, 0, 'moder');

INSERT INTO `hotel`.`user`
          (`email`, `password`, `name`, `surname`, `tel_number`, `birthday`, `discount`, `gender_male`, `blocking`, `role`)
VALUES ('poiut@gmail.com', 'f97e2f1f37d10977c25a118f21c302f8', 'Natali', 'Kir','+375447754945', '2018-09-24', 15, 0, 2, 'customer');

INSERT INTO `hotel`.`user`
          (`email`, `password`, `name`, `surname`, `tel_number`, `birthday`, `discount`, `gender_male`, `blocking`, `role`)
VALUES ('stas@gmail.com', 'f97e2f1f37d10977c25a118f21c302f8', 'Stas', 'Ilyasov','+375447754432', '2018-01-06', 5, 1, 0, 'customer');

INSERT INTO `hotel`.`booking`
        (booking_id, check_in, check_out, adult_count, child_count, user_id)
VALUES (127062018, '2018-07-03', '2018-07-09', 2, 1, 10);

INSERT INTO `hotel`.`room_in_booking`
        (booking_id, room_number)
VALUES (127062018, 1006);

INSERT INTO `hotel`.`booking`
        (booking_id, check_in, check_out, adult_count, child_count, user_id)
VALUES (227062018, '2018-07-06', '2018-07-15', 2, 0, 12);

INSERT INTO `hotel`.`room_in_booking`
        (booking_id, room_number)
VALUES (227062018, 1003);

INSERT INTO `hotel`.`room_in_booking`
        (booking_id, room_number)
VALUES (227062018, 2003);

INSERT INTO `hotel`.`booking`
        (booking_id, check_in, check_out, adult_count, child_count, user_id)
VALUES (327062018, '2018-07-05', '2018-07-26', 6, 2, 14);

INSERT INTO `hotel`.`room_in_booking`
        (booking_id, room_number)
VALUES (327062018, 1002);

INSERT INTO `hotel`.`room_in_booking`
        (booking_id, room_number)
VALUES (327062018, 2001);

INSERT INTO `hotel`.`room_in_booking`
        (booking_id, room_number)
VALUES (327062018, 2006);

INSERT INTO `hotel`.`invoice` (invoice_id, user_id, invoice_date, nights_count, total_payment, invoice_status, payed)
VALUES (20, 14, '2018-06-28', 21, 4298.7, 'instant_pay', 1);
UPDATE `hotel`.`booking` SET `booking`.`invoice_id` = 20, `booking`.`booking_status` = 'confirmed' WHERE `booking`.`booking_id` = 327062018;

INSERT INTO `hotel`.`invoice` (invoice_id, user_id, invoice_date, nights_count, total_payment, invoice_status, payed)
VALUES (21, 10, '2018-06-29', 6, 574.8, 'pay_in_hotel', 0);
UPDATE `hotel`.`booking` SET `booking`.`invoice_id` = 21, `booking`.`booking_status` = 'registered' WHERE `booking`.`booking_id` = 127062018;

INSERT INTO `hotel`.`invoice` (invoice_id, user_id, invoice_date, nights_count, total_payment, invoice_status, payed)
VALUES (22, 12, '2018-06-28', 9, 1267.2, 'instant_pay', 1);
UPDATE `hotel`.`booking` SET `booking`.`invoice_id` = 22 WHERE `booking`.`booking_id` = 227062018;