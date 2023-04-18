-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 03, 2023 at 09:13 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.0.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `drive_safe`
--
CREATE DATABASE IF NOT EXISTS `drive_safe` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `drive_safe`;

-- --------------------------------------------------------

--
-- Table structure for table `driver`
--

CREATE TABLE `driver` (
  `DriverID` int(11) DEFAULT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `Password` varchar(255) DEFAULT NULL,
  `FirstName` varchar(255) DEFAULT NULL,
  `LastName` varchar(255) DEFAULT NULL,
  `Age` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `driver`
--

INSERT INTO `driver` (`DriverID`, `Email`, `Password`, `FirstName`, `LastName`, `Age`) VALUES
(675654, NULL, NULL, 'Dhruv', 'Parikh', 23),
(675654, NULL, NULL, 'Sheldon', 'Deans', 28),
(23518, NULL, NULL, 'Jasmine', 'Carey', 28),
(675654, NULL, NULL, 'David', 'Sweeney', 28),
(123987, 'nikki@mcrue.com', 'DrFeelgood', 'Nikki', 'Sixx', 64),
(123987, 'axl@gnr.com', 'BrwnSton3', 'Axl', 'Rose', 61);

-- --------------------------------------------------------

--
-- Table structure for table `incident`
--

CREATE TABLE `incident` (
  `Driver_ID` int(11) NOT NULL,
  `Incident_Type` varchar(255) DEFAULT NULL,
  `Speed` int(11) DEFAULT NULL,
  `Date` date DEFAULT NULL,
  `Time` time DEFAULT NULL,
  `Location` varchar(255) DEFAULT NULL,
  `Trip_ID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `incident`
--

INSERT INTO `incident` (`Driver_ID`, `Incident_Type`, `Speed`, `Date`, `Time`, `Location`, `Trip_ID`) VALUES
(12131432, 'HardBrake', NULL, '2020-05-05', '12:12:00', '4310 Elkhorn Ave Norfolk VA 23529', NULL);

INSERT INTO `incident` (`Driver_ID`, `Incident_Type`, `Speed`, `Date`, `Time`, `Location`, `Trip_ID`) VALUES
(234567, 'HardBrake', NULL, '2023-04-11', '10:15:00', '101 E Main St Norfolk VA 23510', 123);

INSERT INTO `incident` (`Driver_ID`, `Incident_Type`, `Speed`, `Date`, `Time`, `Location`, `Trip_ID`) VALUES
(345678, 'Collision', NULL, '2023-04-10', '14:20:00', '520 Boush St Norfolk VA 23510', 234);

INSERT INTO `incident` (`Driver_ID`, `Incident_Type`, `Speed`, `Date`, `Time`, `Location`, `Trip_ID`) VALUES
(456789, 'Speeding', 70, '2023-04-09', '08:00:00', '101 Granby St Norfolk VA 23510', 345);

INSERT INTO `incident` (`Driver_ID`, `Incident_Type`, `Speed`, `Date`, `Time`, `Location`, `Trip_ID`) VALUES
(567890, 'HardBrake', NULL, '2023-04-08', '12:30:00', '150 Boush St Norfolk VA 23510', 456);

-- --------------------------------------------------------

--
-- Table structure for table `road_hazard`
--

CREATE TABLE `road_hazard` (
  `Driver_ID` int(11) NOT NULL,
  `Hazard_Type` varchar(255) DEFAULT NULL,
  `Photo_Filename` varchar(255) DEFAULT NULL,
  `Date` date DEFAULT NULL,
  `Time` time DEFAULT NULL,
  `Location` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `road_hazard`
--

INSERT INTO `road_hazard` (`Driver_ID`, `Hazard_Type`, `Photo_Filename`, `Date`, `Time`, `Location`) VALUES
(23518, 'pothole', 'ph123.jpg', '2012-12-12', '12:12:00', '5115 Hampton Blvd Norfolk VA 23529');

INSERT INTO `road_hazard` (`Driver_ID`, `Hazard_Type`, `Photo_Filename`, `Date`, `Time`, `Location`) VALUES
(12345, 'construction zone', 'cz789.jpg', '2023-04-11', '10:15:00', '235 E Main St Norfolk VA 23510');

INSERT INTO `road_hazard` (`Driver_ID`, `Hazard_Type`, `Photo_Filename`, `Date`, `Time`, `Location`) VALUES
(67890, 'fallen tree', 'ft012.jpg', '2023-04-10', '14:20:00', '401 Boush St Norfolk VA 23510');

INSERT INTO `road_hazard` (`Driver_ID`, `Hazard_Type`, `Photo_Filename`, `Date`, `Time`, `Location`) VALUES
(24680, 'road flooding', 'rf345.jpg', '2023-04-10', '16:45:00', '700 Monticello Ave Norfolk VA 23510');

INSERT INTO `road_hazard` (`Driver_ID`, `Hazard_Type`, `Photo_Filename`, `Date`, `Time`, `Location`) VALUES
(13579, 'icy road', 'ir678.jpg', '2023-04-09', '08:00:00', '800 Granby St Norfolk VA 23510');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `incident`
--
ALTER TABLE `incident`
  ADD PRIMARY KEY (`Driver_ID`);

--
-- Indexes for table `road_hazard`
--
ALTER TABLE `road_hazard`
  ADD PRIMARY KEY (`Driver_ID`);