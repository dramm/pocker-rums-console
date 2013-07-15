-- Adminer 3.7.1 MySQL dump1

SET NAMES utf8;
SET foreign_key_checks = 0;
SET time_zone = '+04:00';
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

DROP TABLE IF EXISTS `bets`;
CREATE TABLE `bets` (
  `hand_in_stage_id` int(11) NOT NULL COMMENT 'идентификатор руки на стадии',
  `player_id` int(11) NOT NULL COMMENT 'идентификатор игрока',
  `value` double NOT NULL COMMENT 'сумма ставки'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='Ставка';


DROP TABLE IF EXISTS `cards`;
CREATE TABLE `cards` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор карты(Добавленно)',
  `suits_id` int(11) NOT NULL COMMENT 'Идентификатор масти',
  `dignitys_id` int(11) NOT NULL COMMENT 'Идентификатор достоинства',
  UNIQUE KEY `id` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='Карты';

INSERT INTO `cards` (`id`, `suits_id`, `dignitys_id`) VALUES
(1,	1,	2),
(2,	2,	2),
(3,	3,	2),
(4,	4,	2),
(5,	1,	3),
(6,	2,	3),
(7,	3,	3),
(8,	4,	3),
(9,	1,	4),
(10,	2,	4),
(11,	3,	4),
(12,	4,	4),
(13,	1,	5),
(14,	2,	5),
(15,	3,	5),
(16,	4,	5),
(17,	1,	6),
(18,	2,	6),
(19,	3,	6),
(20,	4,	6),
(21,	1,	7),
(22,	2,	7),
(23,	3,	7),
(24,	4,	7),
(25,	1,	8),
(26,	2,	8),
(27,	3,	8),
(28,	4,	8),
(29,	1,	9),
(30,	2,	9),
(31,	3,	9),
(32,	4,	9),
(33,	1,	10),
(34,	2,	10),
(35,	3,	10),
(36,	4,	10),
(37,	1,	11),
(38,	2,	11),
(39,	3,	11),
(40,	4,	11),
(41,	1,	12),
(42,	2,	12),
(43,	3,	12),
(44,	4,	12),
(45,	1,	13),
(46,	2,	13),
(47,	3,	13),
(48,	4,	13),
(49,	1,	14),
(50,	2,	14),
(51,	3,	14),
(52,	4,	14);

DROP TABLE IF EXISTS `dignitys`;
CREATE TABLE `dignitys` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор достоинства(Добавленно)',
  `power` int(11) NOT NULL COMMENT 'сила достоинства',
  `name` varchar(200) CHARACTER SET latin1 NOT NULL COMMENT 'имя достоинства',
  `short_name` varchar(200) CHARACTER SET latin1 NOT NULL COMMENT 'короткое имя',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='Достоинства карт';

INSERT INTO `dignitys` (`id`, `power`, `name`, `short_name`) VALUES
(2,	2,	'2',	'2'),
(3,	3,	'3',	'3'),
(4,	4,	'4',	'4'),
(5,	5,	'5',	'5'),
(6,	6,	'6',	'6'),
(7,	7,	'7',	'7'),
(8,	8,	'8',	'8'),
(9,	9,	'9',	'9'),
(10,	10,	'Ten',	'T'),
(11,	11,	'Jack',	'J'),
(12,	12,	'Queen',	'Q'),
(13,	13,	'King',	'K'),
(14,	14,	'Ace',	'A');

DROP TABLE IF EXISTS `distribution`;
CREATE TABLE `distribution` (
  `card_id` int(11) NOT NULL COMMENT 'идентификатор карты',
  `game_stage_id` int(11) NOT NULL COMMENT 'идентификатор стадии игры'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='Раздача';


DROP TABLE IF EXISTS `game`;
CREATE TABLE `game` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'идентификатор игры',
  `start_date` datetime NOT NULL COMMENT 'дата начала игры',
  UNIQUE KEY `id` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `game_stage`;
CREATE TABLE `game_stage` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'идентификатор стадии игры',
  `stage_id` int(11) NOT NULL COMMENT 'идентификатор стадии',
  `game_id` int(11) NOT NULL COMMENT 'идентификатор игры',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='Стадия игры';


DROP TABLE IF EXISTS `hands`;
CREATE TABLE `hands` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'идентификатор руки',
  `first_card_id` int(11) NOT NULL COMMENT 'идентификатор первой карты',
  `second_card_id` int(11) NOT NULL COMMENT 'идентификатор второй карты',
  UNIQUE KEY `id` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='Рука';


DROP TABLE IF EXISTS `hands_in_stage`;
CREATE TABLE `hands_in_stage` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор руки на стадии игры(Добавленно)',
  `game_stage_id` int(11) NOT NULL COMMENT 'идентификатор стадии игры',
  `hand_id` int(11) NOT NULL COMMENT 'идентификатор руки',
  `factor` float NOT NULL COMMENT 'коэффициент выигрыша',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='Рука на стадии игры';


DROP TABLE IF EXISTS `stage`;
CREATE TABLE `stage` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'идентификатор стадии',
  `name` varchar(200) CHARACTER SET latin1 NOT NULL COMMENT 'имя стадии',
  `time` int(11) NOT NULL COMMENT 'время выделенное на стадию',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='Стадия игры';

INSERT INTO `stage` (`id`, `name`, `time`) VALUES
(1,	'preflop',	42),
(2,	'flop',	42),
(3,	'turn',	42),
(4,	'river',	42);

DROP TABLE IF EXISTS `suits`;
CREATE TABLE `suits` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'идентификатор масти',
  `name` varchar(200) CHARACTER SET latin1 NOT NULL COMMENT 'имя масти',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='Масть карты';

INSERT INTO `suits` (`id`, `name`) VALUES
(1,	'spades'),
(2,	'diamonds'),
(3,	'clubs'),
(4,	'hearts');

-- 2013-07-14 23:37:36
