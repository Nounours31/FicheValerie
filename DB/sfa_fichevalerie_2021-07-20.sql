-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3307
-- Generation Time: Jul 20, 2021 at 10:32 AM
-- Server version: 10.2.14-MariaDB
-- PHP Version: 7.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sfa_fichevalerie`
--
CREATE DATABASE IF NOT EXISTS `sfa_fichevalerie` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `sfa_fichevalerie`;

-- --------------------------------------------------------

--
-- Table structure for table `activite`
--

DROP TABLE IF EXISTS `activite`;
CREATE TABLE IF NOT EXISTS `activite` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idBulletinSalaire` int(11) NOT NULL,
  `activitee` text NOT NULL,
  `debut` datetime NOT NULL,
  `fin` datetime NOT NULL,
  `date` datetime NOT NULL,
  `tarifHoraire` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COLLATE=utf8_bin;


-- --------------------------------------------------------

--
-- Table structure for table `bulletinsalaire`
--

DROP TABLE IF EXISTS `bulletinsalaire`;
CREATE TABLE IF NOT EXISTS `bulletinsalaire` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idPersonne` int(11) NOT NULL,
  `mois` int(11) NOT NULL,
  `annee` int(11) NOT NULL,
  `tarifHoraire` float NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COLLATE=utf8_bin;


-- --------------------------------------------------------

--
-- Table structure for table `depassementforfaitaire`
--

DROP TABLE IF EXISTS `depassementforfaitaire`;
CREATE TABLE IF NOT EXISTS `depassementforfaitaire` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idBulletinSalaire` int(11) NOT NULL,
  `dureeenheure` float NOT NULL,
  `date` datetime NOT NULL,
  `tarifHoraire` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `env`
--

DROP TABLE IF EXISTS `env`;
CREATE TABLE IF NOT EXISTS `env` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `CSG` float NOT NULL,
  `TauxImposition` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `env`
--

INSERT INTO `env` (`id`, `CSG`, `TauxImposition`) VALUES
(1, 0.099, 0.089);

-- --------------------------------------------------------

--
-- Table structure for table `listactivitee`
--

DROP TABLE IF EXISTS `listactivitee`;
CREATE TABLE IF NOT EXISTS `listactivitee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` text COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `listactivitee`
--

INSERT INTO `listactivitee` (`id`, `nom`) VALUES
(1, 'Aide à la personne'),
(2, 'Cuisine'),
(3, 'Courses'),
(4, 'Sortie'),
(5, 'Jeux');

-- --------------------------------------------------------

--
-- Table structure for table `pdf`
--

DROP TABLE IF EXISTS `pdf`;
CREATE TABLE IF NOT EXISTS `pdf` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idBulletinSalaire` int(11) NOT NULL,
  `file` text NOT NULL,
  `version` int(11) NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `personne`
--

DROP TABLE IF EXISTS `personne`;
CREATE TABLE IF NOT EXISTS `personne` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `genre` set('Madame','Monsieur') NOT NULL,
  `nom` text NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `rappel`
--

DROP TABLE IF EXISTS `rappel`;
CREATE TABLE IF NOT EXISTS `rappel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idBulletinSalaire` int(11) NOT NULL,
  `idBulletinSalaireOrigine` int(11) NOT NULL,
  `dureeenheure` float NOT NULL,
  `tarifHoraire` float NOT NULL,
  `date` datetime NOT NULL,
  `status` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;