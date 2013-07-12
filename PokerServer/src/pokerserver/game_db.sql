-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Хост: 127.0.0.1
-- Время создания: Июл 10 2013 г., 12:51
-- Версия сервера: 5.5.32
-- Версия PHP: 5.4.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- База данных: `game_db`
--
CREATE DATABASE IF NOT EXISTS `game_db` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `game_db`;

-- --------------------------------------------------------

--
-- Структура таблицы `bets`
--

CREATE TABLE IF NOT EXISTS `bets` (
  `hand_in_stage_id` int(11) NOT NULL COMMENT 'идентификатор руки на стадии',
  `player_id` int(11) NOT NULL COMMENT 'идентификатор игрока',
  `value` double NOT NULL COMMENT 'сумма ставки'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Ставка';

-- --------------------------------------------------------

--
-- Структура таблицы `cards`
--

CREATE TABLE IF NOT EXISTS `cards` (
  `id` int(11) NOT NULL COMMENT 'Идентификатор карты(Добавленно)',
  `suits_id` int(11) NOT NULL COMMENT 'Идентификатор масти',
  `dignitys_id` int(11) NOT NULL COMMENT 'Идентификатор достоинства',
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Карты';

-- --------------------------------------------------------

--
-- Структура таблицы `dignitys`
--

CREATE TABLE IF NOT EXISTS `dignitys` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор достоинства(Добавленно)',
  `power` int(11) NOT NULL COMMENT 'сила достоинства',
  `name` varchar(200) NOT NULL COMMENT 'имя достоинства',
  `short_name` varchar(200) NOT NULL COMMENT 'короткое имя',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 COMMENT='Достоинства карт' AUTO_INCREMENT=15 ;

--
-- Дамп данных таблицы `dignitys`
--

INSERT INTO `dignitys` (`id`, `power`, `name`, `short_name`) VALUES
(1, 2, '2', '2'),
(2, 2, '2', '2'),
(3, 3, '3', '3'),
(4, 4, '4', '4'),
(5, 5, '5', '5'),
(6, 6, '6', '6'),
(7, 7, '7', '7'),
(8, 8, '8', '8'),
(9, 9, '9', '9'),
(10, 10, 'Ten', 'T'),
(11, 11, 'Jack', 'J'),
(12, 12, 'Queen', 'Q'),
(13, 13, 'King', 'K'),
(14, 14, 'Ace', 'A');

-- --------------------------------------------------------

--
-- Структура таблицы `distribution`
--

CREATE TABLE IF NOT EXISTS `distribution` (
  `card_id` int(11) NOT NULL COMMENT 'идентификатор карты',
  `game_stage_id` int(11) NOT NULL COMMENT 'идентификатор стадии игры'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Раздача';

-- --------------------------------------------------------

--
-- Структура таблицы `game`
--

CREATE TABLE IF NOT EXISTS `game` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'идентификатор игры',
  `start_date` datetime NOT NULL COMMENT 'дата начала игры',
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Структура таблицы `game_stage`
--

CREATE TABLE IF NOT EXISTS `game_stage` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'идентификатор стадии игры',
  `stage_id` int(11) NOT NULL COMMENT 'идентификатор стадии',
  `game_id` int(11) NOT NULL COMMENT 'идентификатор игры',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Стадия игры' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Структура таблицы `hands`
--

CREATE TABLE IF NOT EXISTS `hands` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'идентификатор руки',
  `first_card_id` int(11) NOT NULL COMMENT 'идентификатор первой карты',
  `second_card_id` int(11) NOT NULL COMMENT 'идентификатор второй карты',
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Рука' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Структура таблицы `hands_in_stage`
--

CREATE TABLE IF NOT EXISTS `hands_in_stage` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор руки на стадии игры(Добавленно)',
  `game_stage_id` int(11) NOT NULL COMMENT 'идентификатор стадии игры',
  `hand_id` int(11) NOT NULL COMMENT 'идентификатор руки',
  `factor` float NOT NULL COMMENT 'коэффициент выигрыша',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Рука на стадии игры' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Структура таблицы `stage`
--

CREATE TABLE IF NOT EXISTS `stage` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'идентификатор стадии',
  `name` varchar(200) NOT NULL COMMENT 'имя стадии',
  `time` int(11) NOT NULL COMMENT 'время выделенное на стадию',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 COMMENT='Стадия игры' AUTO_INCREMENT=5 ;

--
-- Дамп данных таблицы `stage`
--

INSERT INTO `stage` (`id`, `name`, `time`) VALUES
(1, 'preflop', 42),
(2, 'flop', 42),
(3, 'turn', 42),
(4, 'river', 42);

-- --------------------------------------------------------

--
-- Структура таблицы `suits`
--

CREATE TABLE IF NOT EXISTS `suits` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'идентификатор масти',
  `name` varchar(200) NOT NULL COMMENT 'имя масти',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 COMMENT='Масть карты' AUTO_INCREMENT=5 ;

--
-- Дамп данных таблицы `suits`
--

INSERT INTO `suits` (`id`, `name`) VALUES
(1, 'diamonds'),
(2, 'spades'),
(3, 'clubs'),
(4, 'hearts');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
