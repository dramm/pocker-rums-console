-- Adminer 3.7.1 MySQL dump

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


DROP TABLE IF EXISTS `dignitys`;
CREATE TABLE `dignitys` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор достоинства(Добавленно)',
  `power` int(11) NOT NULL COMMENT 'сила достоинства',
  `name` varchar(200) CHARACTER SET latin1 NOT NULL COMMENT 'имя достоинства',
  `short_name` varchar(200) CHARACTER SET latin1 NOT NULL COMMENT 'короткое имя',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='Достоинства карт';


DROP TABLE IF EXISTS `distribution`;
CREATE TABLE `distribution` (
  `card_id` int(11) NOT NULL COMMENT 'идентификатор карты',
  `game_stage_id` int(11) NOT NULL COMMENT 'идентификатор стадии игры'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='Раздача';

INSERT INTO `distribution` (`card_id`, `game_stage_id`) VALUES
(1,	22),
(3,	22),
(50,	22),
(30,	23),
(37,	23),
(49,	23),
(13,	24),
(5,	24),
(14,	24),
(3,	37),
(18,	37),
(21,	37),
(34,	38),
(29,	38),
(4,	38),
(5,	39),
(6,	39),
(8,	39),
(27,	40),
(37,	41),
(42,	42),
(45,	43),
(6,	44),
(28,	45),
(39,	49),
(26,	49),
(6,	49),
(18,	50),
(29,	50),
(49,	50),
(33,	51),
(7,	51),
(3,	51);

DROP TABLE IF EXISTS `distribution_n`;
CREATE TABLE `distribution_n` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `card_id` int(11) NOT NULL,
  `game_stage_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `game`;
CREATE TABLE `game` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'идентификатор игры',
  `start_date` datetime NOT NULL COMMENT 'дата начала игры',
  UNIQUE KEY `id` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `game_n`;
CREATE TABLE `game_n` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start_date` datetime NOT NULL,
  `table_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`)
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


DROP TABLE IF EXISTS `suits`;
CREATE TABLE `suits` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'идентификатор масти',
  `name` varchar(200) CHARACTER SET latin1 NOT NULL COMMENT 'имя масти',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='Масть карты';


-- 2013-07-23 16:21:55
