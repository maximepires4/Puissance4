-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Sep 29, 2022 at 12:37 PM
-- Server version: 10.1.38-MariaDB
-- PHP Version: 5.6.40

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `Puissance4`
--

-- --------------------------------------------------------

--
-- Table structure for table `Cpu_Level`
--

CREATE TABLE `Cpu_Level` (
  `id` int(50) NOT NULL,
  `description` varchar(50) NOT NULL,
  `depth` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Game`
--

CREATE TABLE `Game` (
  `id` int(50) NOT NULL,
  `player_1_id` int(50) DEFAULT NULL,
  `player_2_id` int(50) DEFAULT NULL,
  `cpu_level_id` int(50) DEFAULT NULL,
  `grid_size_x` int(50) NOT NULL,
  `grid_size_y` int(50) NOT NULL,
  `result` int(50) NOT NULL,
  `datetime_start` datetime(6) NOT NULL,
  `duration` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Player`
--

CREATE TABLE `Player` (
  `id` int(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `permission` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Turn`
--

CREATE TABLE `Turn` (
  `game_id` int(11) NOT NULL,
  `turn` int(11) NOT NULL,
  `player_id` int(11) DEFAULT NULL,
  `cpu_id` int(50) DEFAULT NULL,
  `turn_X` int(11) NOT NULL,
  `turn_Y` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Cpu_Level`
--
ALTER TABLE `Cpu_Level`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `Game`
--
ALTER TABLE `Game`
  ADD PRIMARY KEY (`id`),
  ADD KEY `Foreign Key3` (`player_1_id`),
  ADD KEY `Foreign Key4` (`player_2_id`),
  ADD KEY `Foreign Key5` (`cpu_level_id`);

--
-- Indexes for table `Player`
--
ALTER TABLE `Player`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `Turn`
--
ALTER TABLE `Turn`
  ADD PRIMARY KEY (`game_id`,`turn`),
  ADD KEY `Foreign Key2` (`player_id`),
  ADD KEY `Foreign Key6` (`cpu_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Cpu_Level`
--
ALTER TABLE `Cpu_Level`
  MODIFY `id` int(50) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `Game`
--
ALTER TABLE `Game`
  MODIFY `id` int(50) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `Player`
--
ALTER TABLE `Player`
  MODIFY `id` int(50) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Game`
--
ALTER TABLE `Game`
  ADD CONSTRAINT `Foreign Key3` FOREIGN KEY (`player_1_id`) REFERENCES `Player` (`id`),
  ADD CONSTRAINT `Foreign Key4` FOREIGN KEY (`player_2_id`) REFERENCES `Player` (`id`),
  ADD CONSTRAINT `Foreign Key5` FOREIGN KEY (`cpu_level_id`) REFERENCES `Cpu_Level` (`id`);

--
-- Constraints for table `Turn`
--
ALTER TABLE `Turn`
  ADD CONSTRAINT `Foreign Key1` FOREIGN KEY (`game_id`) REFERENCES `Game` (`id`),
  ADD CONSTRAINT `Foreign Key2` FOREIGN KEY (`player_id`) REFERENCES `Player` (`id`),
  ADD CONSTRAINT `Foreign Key6` FOREIGN KEY (`cpu_id`) REFERENCES `Cpu_Level` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
