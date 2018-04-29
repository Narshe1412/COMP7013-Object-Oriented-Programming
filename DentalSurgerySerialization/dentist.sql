-- phpMyAdmin SQL Dump
-- version 4.1.12
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 07, 2015 at 02:16 AM
-- Server version: 5.6.16
-- PHP Version: 5.5.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `dentist`
--

-- --------------------------------------------------------

--
-- Table structure for table `patient`
--

CREATE TABLE IF NOT EXISTS `patient` (
  `patientID` int(11) NOT NULL AUTO_INCREMENT,
  `patientName` varchar(100) NOT NULL,
  `patientAddress` varchar(250) NOT NULL,
  `patientPhone` varchar(10) NOT NULL,
  PRIMARY KEY (`patientID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `patient`
--

INSERT INTO `patient` (`patientID`, `patientName`, `patientAddress`, `patientPhone`) VALUES
(1, 'TestPatient', 'TestPatient Address Cork', '101010101'),
(6, 'test', '1234', '987654321'),
(7, 'sdysehrher', 'egrhreherherh', '876543213');

-- --------------------------------------------------------

--
-- Table structure for table `patientprocedure`
--

CREATE TABLE IF NOT EXISTS `patientprocedure` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `patientID` int(11) NOT NULL,
  `procedureID` int(11) NOT NULL,
  `paymentID` varchar(50) NOT NULL,
  `date` varchar(100) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `patientprocedure`
--

INSERT INTO `patientprocedure` (`ID`, `patientID`, `procedureID`, `paymentID`, `date`) VALUES
(8, 6, 12, 'b96e0eee-2e90-478e-831f-eac36e191f40', '04/28/2015'),
(9, 6, 1, '42c4381d-badd-4674-b329-c5bbccc0d05e', '04/28/2015'),
(10, 6, 16, '69485690-bb0f-443a-ba4b-0cc3ef763caa', '04/28/2015'),
(11, 6, 12, 'fd6a67c3-1462-418a-ac85-c803f09ee3bf', '04/01/2015');

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE IF NOT EXISTS `payment` (
  `paymentID` varchar(50) NOT NULL,
  `paymentDate` varchar(50) DEFAULT NULL,
  `paymentAmount` float NOT NULL,
  `isPaid` tinyint(4) NOT NULL,
  PRIMARY KEY (`paymentID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`paymentID`, `paymentDate`, `paymentAmount`, `isPaid`) VALUES
('42c4381d-badd-4674-b329-c5bbccc0d05e', NULL, 55, 0),
('69485690-bb0f-443a-ba4b-0cc3ef763caa', '2015/04/28 22:40:36', 300, 1),
('b96e0eee-2e90-478e-831f-eac36e191f40', '2015/04/28 22:39:48', 134, 1),
('fd6a67c3-1462-418a-ac85-c803f09ee3bf', NULL, 134, 0);

-- --------------------------------------------------------

--
-- Table structure for table `procedures`
--

CREATE TABLE IF NOT EXISTS `procedures` (
  `procedureID` int(11) NOT NULL AUTO_INCREMENT,
  `procedureName` varchar(400) NOT NULL,
  `procedureCost` float NOT NULL,
  PRIMARY KEY (`procedureID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=18 ;

--
-- Dumping data for table `procedures`
--

INSERT INTO `procedures` (`procedureID`, `procedureName`, `procedureCost`) VALUES
(1, 'Cleaning & Polishing', 55),
(2, 'Comprehensive Dental Exams', 205.5),
(3, 'Fillings', 35),
(4, 'Sealants', 25),
(5, 'Crowns', 80),
(6, 'Cosmetic Dentistry', 110),
(7, 'Porcelain Veneers', 45),
(8, 'Athletic Mouth Guards', 20),
(9, 'Periodontal Treatment', 125),
(10, 'Dental Implants', 224),
(11, 'Partial dentures and dentures', 105.5),
(12, 'Occlusal Splints or Night Guards', 134),
(13, 'Bridges', 95),
(14, 'Extractions', 34),
(15, 'Root Canals', 97),
(16, 'Emergency Services', 300),
(17, 'testset', 123);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
