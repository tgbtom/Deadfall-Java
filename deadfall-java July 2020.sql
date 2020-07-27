-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 27, 2020 at 09:06 PM
-- Server version: 10.4.13-MariaDB
-- PHP Version: 7.4.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `deadfall-java`
--

-- --------------------------------------------------------

--
-- Table structure for table `df_characters`
--

CREATE TABLE `df_characters` (
  `char_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `town_id` int(11) DEFAULT 0,
  `current_ap` int(11) DEFAULT 16,
  `max_ap` int(11) DEFAULT 16,
  `zone_id` int(11) DEFAULT NULL,
  `name` varchar(40) NOT NULL,
  `classification` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `df_characters`
--

INSERT INTO `df_characters` (`char_id`, `user_id`, `town_id`, `current_ap`, `max_ap`, `zone_id`, `name`, `classification`) VALUES
(1, 1, 1, 16, 16, 61, 'Testy', 'Survivor');

-- --------------------------------------------------------

--
-- Table structure for table `df_characters_items`
--

CREATE TABLE `df_characters_items` (
  `stack_id` int(11) NOT NULL,
  `char_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `quantity` int(11) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `df_characters_items`
--

INSERT INTO `df_characters_items` (`stack_id`, `char_id`, `item_id`, `quantity`) VALUES
(22, 1, 2, 6),
(23, 1, 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `df_characters_legacy`
--

CREATE TABLE `df_characters_legacy` (
  `char_id` int(11) NOT NULL,
  `construction_contributions` int(11) DEFAULT 0,
  `zeds_killed` int(11) DEFAULT 0,
  `distance_travelled` int(11) DEFAULT 0,
  `times_looted` int(11) DEFAULT 0,
  `successful_camps` int(11) DEFAULT 0,
  `lvl` int(11) DEFAULT 1,
  `current_xp` int(11) DEFAULT 0,
  `deaths` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `df_characters_legacy`
--

INSERT INTO `df_characters_legacy` (`char_id`, `construction_contributions`, `zeds_killed`, `distance_travelled`, `times_looted`, `successful_camps`, `lvl`, `current_xp`, `deaths`) VALUES
(1, 0, 0, 0, 0, 0, 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `df_characters_skills`
--

CREATE TABLE `df_characters_skills` (
  `char_id` int(11) NOT NULL,
  `skill_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `df_characters_stats`
--

CREATE TABLE `df_characters_stats` (
  `char_id` int(11) NOT NULL,
  `construction_contributions` int(11) DEFAULT 0,
  `zeds_killed` int(11) DEFAULT 0,
  `distance_travelled` int(11) DEFAULT 0,
  `times_looted` int(11) DEFAULT 0,
  `successful_camps` int(11) DEFAULT 0,
  `day_of_death` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `df_characters_stats`
--

INSERT INTO `df_characters_stats` (`char_id`, `construction_contributions`, `zeds_killed`, `distance_travelled`, `times_looted`, `successful_camps`, `day_of_death`) VALUES
(1, 0, 0, 0, 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `df_consumables`
--

CREATE TABLE `df_consumables` (
  `item_id` int(11) NOT NULL,
  `ap_gain` int(11) DEFAULT 100,
  `consume_type` varchar(30) DEFAULT NULL,
  `health_gain` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `df_consumables`
--

INSERT INTO `df_consumables` (`item_id`, `ap_gain`, `consume_type`, `health_gain`) VALUES
(1, 100, 'Drink', 0),
(2, 80, 'Eat', 0),
(24, 100, 'Eat', 0);

-- --------------------------------------------------------

--
-- Table structure for table `df_items`
--

CREATE TABLE `df_items` (
  `item_id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `mass` int(3) DEFAULT 2,
  `rarity` varchar(30) DEFAULT NULL,
  `category` varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `df_items`
--

INSERT INTO `df_items` (`item_id`, `name`, `description`, `mass`, `rarity`, `category`) VALUES
(1, 'Water Ration', 'Water is essential to keeping your survivors hydrated and alive. Be sure to ration if you want to survive.', 2, 'Rare', 'Consumable'),
(2, 'Bits of Food', 'Scraps of food are better than nothing. They won\'t stop your stomach from rumbling, but they will keep you alive.', 2, 'Rare', 'Consumable'),
(3, 'Arrow', 'Could be used with a bow.', 1, 'Epic', 'Ammo'),
(4, 'Wood Board', 'Sturdy wooden board. Could be useful for building.', 2, 'Common', 'Resource'),
(5, 'Sheet Metal', 'Sheet of metal. Could be useful for building.', 3, 'Common', 'Resource'),
(6, 'Makeshift Spear', 'A wooden stick with a sharp stone affixed to the end. Good for poking zeds.', 2, 'Epic', 'Weapon'),
(7, 'Stone', 'Just a solid stone.', 2, 'Scrap', 'Resource'),
(8, 'Bow', 'A basic longbow. Can use arrows to kill zombies from a safe distance.', 2, 'Epic', 'Weapon'),
(9, 'Rope', 'A length of rope. Could be used to tie things together.', 2, 'UnCommon', 'Resource'),
(10, 'Battery', 'A basic battery.', 2, 'UnCommon', 'Resource'),
(11, 'Brick', 'A basic building material.', 2, 'Common', 'Resource'),
(12, 'Cloth', 'Piece of cloth material.', 2, 'UnCommon', 'Resource'),
(13, 'Grenade', 'A questionably old explosive.', 2, 'Legendary', 'Weapon'),
(14, 'Pistol', 'A basic handgun. Uses small bullets.', 2, 'Legendary', 'Weapon'),
(15, 'Slingshot', 'Used to propel rocks at unsuspecting targets.', 2, 'Rare', 'Weapon'),
(16, 'Rock', 'A rock. Can be launched with a slingshot.', 1, 'UnCommon', 'Ammo'),
(17, 'Small Bullet', 'Ammunition for small arms.', 1, 'Rare', 'Ammo'),
(18, 'Sharp Stick', 'Just a stick that has been sharpened at one end.', 2, 'Rare', 'Weapon'),
(19, 'Assault Rifle', 'Machine gun. Uses medium ammo that has been packed into an ammo magazine.', 2, 'Legendary', 'Weapon'),
(20, 'Bag of Sand', 'Justa bag of dirt.', 2, 'Scrap', 'Resource'),
(21, 'Empty Mag', 'Empty ammo magazine, can be filled with medium bullets.', 2, 'Rare', 'Ammo'),
(22, 'Full Mag', 'Full ammo magazine. Empties after each use.', 3, 'Legendary', 'Ammo'),
(23, 'Medium Bullet', 'Ammunition for medium arms.', 1, 'Rare', 'Ammo'),
(24, 'Carrot', 'A fresh picked carrot. Restores full AP if you have not eaten yet today.', 2, 'Rare', 'Consumable');

-- --------------------------------------------------------

--
-- Table structure for table `df_skills`
--

CREATE TABLE `df_skills` (
  `skill_id` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `df_structures`
--

CREATE TABLE `df_structures` (
  `structure_id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `category` varchar(40) DEFAULT NULL,
  `description` varchar(4000) DEFAULT NULL,
  `defence` int(11) DEFAULT 0,
  `ap_cost` int(11) DEFAULT 10,
  `levels` int(11) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `df_structures`
--

INSERT INTO `df_structures` (`structure_id`, `name`, `category`, `description`, `defence`, `ap_cost`, `levels`) VALUES
(1, 'Perimeter Fence', 'Defence', 'Build a fence establishing a perimeter for the settlement. Will Keep some zeds out.', 50, 45, 5),
(2, 'Wooden Wall', 'Defence', 'Improve the perimeter around the settlement with a large wooden wall.', 100, 80, 10),
(3, 'Inner Wall', 'Defence', 'Create a secondary wall, just in case there is a breach in the main wall.', 75, 90, 10),
(4, 'Trenches', 'Defence', 'Dig out trenches between the outer and inner wall. Great place to set traps.', 40, 100, 15),
(5, 'Spike Pits', 'Defence', 'Set up sharp spikes in some areas of the trenches to impale falling zeds.', 65, 35, 5),
(6, 'Wooden Support', 'Defence', 'Add structural support the walls.', 30, 40, 5),
(7, 'Metal Patching', 'Defence', 'Reinforce the outer wall with metal.', 115, 85, 5),
(8, 'Sentry Tower', 'Defence', 'Each level will build a sentry tower on a different section of the wall. Used as a lookout.', 85, 120, 4),
(9, 'MG Nest', 'Defence', 'Set up machine gun nests near the perimeter.', 250, 50, 2),
(10, 'Water Reserve', 'Supply', 'Establish a holding area for all clean water reserves in the settlement. Once built; provides 2-4 water rations per day.', 0, 50, 1),
(11, 'Vegetable Garden', 'Supply', 'A small plot to grow vegetables. Generates food overnight. Grows 1-4 food per night.', 0, 85, 1),
(12, 'Fabrikator Workshop', 'Production', 'Allows the conversion of basic resources at a 3:1 Ratio.', 0, 45, 1);

-- --------------------------------------------------------

--
-- Table structure for table `df_structure_costs`
--

CREATE TABLE `df_structure_costs` (
  `structure_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `item_quantity` int(11) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `df_structure_costs`
--

INSERT INTO `df_structure_costs` (`structure_id`, `item_id`, `item_quantity`) VALUES
(1, 3, 10),
(1, 9, 2),
(2, 3, 20),
(2, 9, 5),
(3, 3, 5),
(3, 11, 15),
(4, 3, 2),
(4, 4, 2),
(4, 11, 2),
(5, 4, 5),
(5, 9, 6),
(5, 18, 3),
(6, 3, 15),
(7, 4, 15),
(8, 3, 5),
(8, 4, 25),
(8, 11, 5),
(9, 19, 1),
(9, 20, 10),
(9, 22, 3),
(10, 1, 5),
(10, 4, 2),
(10, 6, 10),
(11, 1, 10),
(12, 3, 10),
(12, 4, 10),
(12, 9, 10);

-- --------------------------------------------------------

--
-- Table structure for table `df_structure_requirements`
--

CREATE TABLE `df_structure_requirements` (
  `structure_id` int(11) NOT NULL,
  `required_id` int(11) NOT NULL,
  `required_level` int(11) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `df_structure_requirements`
--

INSERT INTO `df_structure_requirements` (`structure_id`, `required_id`, `required_level`) VALUES
(2, 1, 1),
(3, 2, 1),
(4, 3, 1),
(5, 3, 1),
(6, 2, 1),
(7, 2, 1),
(8, 2, 1),
(9, 2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `df_towns`
--

CREATE TABLE `df_towns` (
  `town_id` int(11) NOT NULL,
  `name` varchar(300) DEFAULT NULL,
  `max_chars` int(11) DEFAULT 10,
  `horde_size` int(11) DEFAULT NULL,
  `defence` int(11) DEFAULT NULL,
  `day_number` int(11) DEFAULT 0,
  `map_size` int(11) DEFAULT 11,
  `game_mode` varchar(20) DEFAULT 'Regular',
  `status` varchar(20) DEFAULT 'New'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `df_towns`
--

INSERT INTO `df_towns` (`town_id`, `name`, `max_chars`, `horde_size`, `defence`, `day_number`, `map_size`, `game_mode`, `status`) VALUES
(1, 'Brampton', 10, 300, 350, 0, 11, 'Regular', 'New');

-- --------------------------------------------------------

--
-- Table structure for table `df_towns_structures`
--

CREATE TABLE `df_towns_structures` (
  `town_id` int(11) NOT NULL,
  `structure_id` int(11) NOT NULL,
  `current_level` int(11) DEFAULT 1,
  `current_ap` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `df_town_bulletins`
--

CREATE TABLE `df_town_bulletins` (
  `bulletin_id` int(11) NOT NULL,
  `town_id` int(11) NOT NULL,
  `content` varchar(4000) DEFAULT NULL,
  `posted_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `df_town_bulletins`
--

INSERT INTO `df_town_bulletins` (`bulletin_id`, `town_id`, `content`, `posted_time`) VALUES
(1, 1, '<span class=\'bulletinChar\'>Testy</span> [<img src=\"/Deadfall/resources/img/icons/Survivor.png\">Survivor] has joined the town! 9 spots remaining before the town can begin!', '2020-07-24 14:18:27');

-- --------------------------------------------------------

--
-- Table structure for table `df_town_zones`
--

CREATE TABLE `df_town_zones` (
  `zone_id` int(11) NOT NULL,
  `town_id` int(11) NOT NULL,
  `x` int(11) NOT NULL,
  `y` int(11) NOT NULL,
  `lootability` int(11) DEFAULT 10,
  `zeds` int(11) DEFAULT 0,
  `special_zone` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `df_town_zones`
--

INSERT INTO `df_town_zones` (`zone_id`, `town_id`, `x`, `y`, `lootability`, `zeds`, `special_zone`) VALUES
(1, 1, 5, 5, 10, 0, ''),
(2, 1, 4, 5, 10, 0, ''),
(3, 1, 3, 5, 10, 0, ''),
(4, 1, 2, 5, 10, 0, ''),
(5, 1, 1, 5, 10, 0, ''),
(6, 1, 0, 5, 10, 0, ''),
(7, 1, -1, 5, 10, 0, ''),
(8, 1, -2, 5, 10, 0, ''),
(9, 1, -3, 5, 10, 0, ''),
(10, 1, -4, 5, 10, 0, ''),
(11, 1, -5, 5, 10, 0, ''),
(12, 1, 5, 4, 10, 0, ''),
(13, 1, 4, 4, 10, 0, ''),
(14, 1, 3, 4, 10, 0, ''),
(15, 1, 2, 4, 10, 0, ''),
(16, 1, 1, 4, 10, 0, ''),
(17, 1, 0, 4, 10, 0, ''),
(18, 1, -1, 4, 10, 0, ''),
(19, 1, -2, 4, 10, 0, ''),
(20, 1, -3, 4, 10, 0, ''),
(21, 1, -4, 4, 10, 0, ''),
(22, 1, -5, 4, 10, 0, ''),
(23, 1, 5, 3, 10, 0, ''),
(24, 1, 4, 3, 10, 0, ''),
(25, 1, 3, 3, 10, 0, ''),
(26, 1, 2, 3, 10, 0, ''),
(27, 1, 1, 3, 10, 0, ''),
(28, 1, 0, 3, 10, 0, ''),
(29, 1, -1, 3, 10, 0, ''),
(30, 1, -2, 3, 10, 0, ''),
(31, 1, -3, 3, 10, 0, ''),
(32, 1, -4, 3, 10, 0, ''),
(33, 1, -5, 3, 10, 0, ''),
(34, 1, 5, 2, 10, 0, ''),
(35, 1, 4, 2, 10, 0, ''),
(36, 1, 3, 2, 10, 0, ''),
(37, 1, 2, 2, 10, 0, ''),
(38, 1, 1, 2, 10, 0, ''),
(39, 1, 0, 2, 10, 0, ''),
(40, 1, -1, 2, 10, 0, ''),
(41, 1, -2, 2, 10, 0, ''),
(42, 1, -3, 2, 10, 0, ''),
(43, 1, -4, 2, 10, 0, ''),
(44, 1, -5, 2, 10, 0, ''),
(45, 1, 5, 1, 10, 0, ''),
(46, 1, 4, 1, 10, 0, ''),
(47, 1, 3, 1, 10, 0, ''),
(48, 1, 2, 1, 10, 0, ''),
(49, 1, 1, 1, 10, 0, ''),
(50, 1, 0, 1, 10, 0, ''),
(51, 1, -1, 1, 10, 0, ''),
(52, 1, -2, 1, 10, 0, ''),
(53, 1, -3, 1, 10, 0, ''),
(54, 1, -4, 1, 10, 0, ''),
(55, 1, -5, 1, 10, 0, ''),
(56, 1, 5, 0, 10, 0, ''),
(57, 1, 4, 0, 10, 0, ''),
(58, 1, 3, 0, 10, 0, ''),
(59, 1, 2, 0, 10, 0, ''),
(60, 1, 1, 0, 10, 0, ''),
(61, 1, 0, 0, 10, 0, ''),
(62, 1, -1, 0, 10, 0, ''),
(63, 1, -2, 0, 10, 0, ''),
(64, 1, -3, 0, 10, 0, ''),
(65, 1, -4, 0, 10, 0, ''),
(66, 1, -5, 0, 10, 0, ''),
(67, 1, 5, -1, 10, 0, ''),
(68, 1, 4, -1, 10, 0, ''),
(69, 1, 3, -1, 10, 0, ''),
(70, 1, 2, -1, 10, 0, ''),
(71, 1, 1, -1, 10, 0, ''),
(72, 1, 0, -1, 10, 0, ''),
(73, 1, -1, -1, 10, 0, ''),
(74, 1, -2, -1, 10, 0, ''),
(75, 1, -3, -1, 10, 0, ''),
(76, 1, -4, -1, 10, 0, ''),
(77, 1, -5, -1, 10, 0, ''),
(78, 1, 5, -2, 10, 0, ''),
(79, 1, 4, -2, 10, 0, ''),
(80, 1, 3, -2, 10, 0, ''),
(81, 1, 2, -2, 10, 0, ''),
(82, 1, 1, -2, 10, 0, ''),
(83, 1, 0, -2, 10, 0, ''),
(84, 1, -1, -2, 10, 0, ''),
(85, 1, -2, -2, 10, 0, ''),
(86, 1, -3, -2, 10, 0, ''),
(87, 1, -4, -2, 10, 0, ''),
(88, 1, -5, -2, 10, 0, ''),
(89, 1, 5, -3, 10, 0, ''),
(90, 1, 4, -3, 10, 0, ''),
(91, 1, 3, -3, 10, 0, ''),
(92, 1, 2, -3, 10, 0, ''),
(93, 1, 1, -3, 10, 0, ''),
(94, 1, 0, -3, 10, 0, ''),
(95, 1, -1, -3, 10, 0, ''),
(96, 1, -2, -3, 10, 0, ''),
(97, 1, -3, -3, 10, 0, ''),
(98, 1, -4, -3, 10, 0, ''),
(99, 1, -5, -3, 10, 0, ''),
(100, 1, 5, -4, 10, 0, ''),
(101, 1, 4, -4, 10, 0, ''),
(102, 1, 3, -4, 10, 0, ''),
(103, 1, 2, -4, 10, 0, ''),
(104, 1, 1, -4, 10, 0, ''),
(105, 1, 0, -4, 10, 0, ''),
(106, 1, -1, -4, 10, 0, ''),
(107, 1, -2, -4, 10, 0, ''),
(108, 1, -3, -4, 10, 0, ''),
(109, 1, -4, -4, 10, 0, ''),
(110, 1, -5, -4, 10, 0, ''),
(111, 1, 5, -5, 10, 0, ''),
(112, 1, 4, -5, 10, 0, ''),
(113, 1, 3, -5, 10, 0, ''),
(114, 1, 2, -5, 10, 0, ''),
(115, 1, 1, -5, 10, 0, ''),
(116, 1, 0, -5, 10, 0, ''),
(117, 1, -1, -5, 10, 0, ''),
(118, 1, -2, -5, 10, 0, ''),
(119, 1, -3, -5, 10, 0, ''),
(120, 1, -4, -5, 10, 0, ''),
(121, 1, -5, -5, 10, 0, '');

-- --------------------------------------------------------

--
-- Table structure for table `df_town_zone_items`
--

CREATE TABLE `df_town_zone_items` (
  `stack_id` int(11) NOT NULL,
  `zone_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `quantity` int(11) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `df_town_zone_items`
--

INSERT INTO `df_town_zone_items` (`stack_id`, `zone_id`, `item_id`, `quantity`) VALUES
(1, 61, 1, 30),
(2, 61, 2, 9);

-- --------------------------------------------------------

--
-- Table structure for table `df_users`
--

CREATE TABLE `df_users` (
  `user_id` int(11) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(70) DEFAULT NULL,
  `salt` varchar(150) DEFAULT NULL,
  `email` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `df_users`
--

INSERT INTO `df_users` (`user_id`, `username`, `password`, `salt`, `email`) VALUES
(1, 'thomas', 'dcabdb56a67a258f8f9b8b84c8c04558bb9039687634c6581013661c556f34e1', '[120, -72, 8, 28, 60, -80, 88, -20, -36, 104, 37, 60, 75, -32, -99, -99]', 'thomas@gmail');

-- --------------------------------------------------------

--
-- Table structure for table `df_users_skills`
--

CREATE TABLE `df_users_skills` (
  `user_id` int(11) NOT NULL,
  `skill_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `df_user_bulletins`
--

CREATE TABLE `df_user_bulletins` (
  `bulletin_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `content` varchar(750) DEFAULT NULL,
  `posted_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `df_user_bulletins`
--

INSERT INTO `df_user_bulletins` (`bulletin_id`, `user_id`, `content`, `posted_time`) VALUES
(1, 1, 'Account has been created. Welcome to Deadfall', '2020-07-24 14:16:09'),
(2, 1, 'Testy [Survivor] has been created.', '2020-07-24 14:16:51');

-- --------------------------------------------------------

--
-- Table structure for table `df_weapons`
--

CREATE TABLE `df_weapons` (
  `item_id` int(11) NOT NULL,
  `ammo_id` int(11) DEFAULT -1,
  `min_kills` int(11) DEFAULT 0,
  `max_kills` int(11) DEFAULT 1,
  `chance_of_injury` int(11) DEFAULT 50,
  `chance_of_break` int(11) DEFAULT 10,
  `item_on_break` int(11) DEFAULT 0,
  `ap_cost` int(11) DEFAULT 0,
  `chance_output` int(11) DEFAULT 0,
  `ammo_output` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `df_weapons`
--

INSERT INTO `df_weapons` (`item_id`, `ammo_id`, `min_kills`, `max_kills`, `chance_of_injury`, `chance_of_break`, `item_on_break`, `ap_cost`, `chance_output`, `ammo_output`) VALUES
(6, 0, 1, 1, 15, 15, 0, 1, 0, 0),
(8, 3, 0, 1, 1, 5, 0, 1, 80, 3),
(13, 0, 5, 20, 25, 100, 0, 0, 0, 0),
(14, 17, 1, 3, 1, 1, 0, 0, 0, 0),
(15, 16, -2, 1, 10, 10, 0, 1, 100, 16),
(18, 0, 0, 1, 20, 25, 0, 1, 0, 0),
(19, 22, 12, 30, 1, 1, 0, 0, 100, 21);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `df_characters`
--
ALTER TABLE `df_characters`
  ADD PRIMARY KEY (`char_id`);

--
-- Indexes for table `df_characters_items`
--
ALTER TABLE `df_characters_items`
  ADD PRIMARY KEY (`stack_id`);

--
-- Indexes for table `df_characters_skills`
--
ALTER TABLE `df_characters_skills`
  ADD PRIMARY KEY (`char_id`);

--
-- Indexes for table `df_characters_stats`
--
ALTER TABLE `df_characters_stats`
  ADD PRIMARY KEY (`char_id`);

--
-- Indexes for table `df_consumables`
--
ALTER TABLE `df_consumables`
  ADD PRIMARY KEY (`item_id`);

--
-- Indexes for table `df_items`
--
ALTER TABLE `df_items`
  ADD PRIMARY KEY (`item_id`);

--
-- Indexes for table `df_skills`
--
ALTER TABLE `df_skills`
  ADD PRIMARY KEY (`skill_id`);

--
-- Indexes for table `df_structures`
--
ALTER TABLE `df_structures`
  ADD PRIMARY KEY (`structure_id`);

--
-- Indexes for table `df_structure_costs`
--
ALTER TABLE `df_structure_costs`
  ADD PRIMARY KEY (`structure_id`,`item_id`);

--
-- Indexes for table `df_structure_requirements`
--
ALTER TABLE `df_structure_requirements`
  ADD PRIMARY KEY (`structure_id`);

--
-- Indexes for table `df_towns`
--
ALTER TABLE `df_towns`
  ADD PRIMARY KEY (`town_id`);

--
-- Indexes for table `df_towns_structures`
--
ALTER TABLE `df_towns_structures`
  ADD PRIMARY KEY (`town_id`);

--
-- Indexes for table `df_town_bulletins`
--
ALTER TABLE `df_town_bulletins`
  ADD PRIMARY KEY (`bulletin_id`);

--
-- Indexes for table `df_town_zones`
--
ALTER TABLE `df_town_zones`
  ADD PRIMARY KEY (`zone_id`);

--
-- Indexes for table `df_town_zone_items`
--
ALTER TABLE `df_town_zone_items`
  ADD PRIMARY KEY (`stack_id`);

--
-- Indexes for table `df_users`
--
ALTER TABLE `df_users`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `df_users_skills`
--
ALTER TABLE `df_users_skills`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `df_user_bulletins`
--
ALTER TABLE `df_user_bulletins`
  ADD PRIMARY KEY (`bulletin_id`);

--
-- Indexes for table `df_weapons`
--
ALTER TABLE `df_weapons`
  ADD PRIMARY KEY (`item_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `df_characters`
--
ALTER TABLE `df_characters`
  MODIFY `char_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `df_characters_items`
--
ALTER TABLE `df_characters_items`
  MODIFY `stack_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `df_items`
--
ALTER TABLE `df_items`
  MODIFY `item_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `df_skills`
--
ALTER TABLE `df_skills`
  MODIFY `skill_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `df_structures`
--
ALTER TABLE `df_structures`
  MODIFY `structure_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `df_towns`
--
ALTER TABLE `df_towns`
  MODIFY `town_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `df_town_bulletins`
--
ALTER TABLE `df_town_bulletins`
  MODIFY `bulletin_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `df_town_zones`
--
ALTER TABLE `df_town_zones`
  MODIFY `zone_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=122;

--
-- AUTO_INCREMENT for table `df_town_zone_items`
--
ALTER TABLE `df_town_zone_items`
  MODIFY `stack_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `df_users`
--
ALTER TABLE `df_users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `df_user_bulletins`
--
ALTER TABLE `df_user_bulletins`
  MODIFY `bulletin_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
