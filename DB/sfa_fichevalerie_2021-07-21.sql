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
) ENGINE=MyISAM AUTO_INCREMENT=119 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `activite`
--

INSERT INTO `activite` (`id`, `idBulletinSalaire`, `activitee`, `debut`, `fin`, `date`, `tarifHoraire`) VALUES
(117, 93, 'Aide à la personne', '2021-01-02 01:00:00', '2021-01-02 01:05:00', '2021-07-19 23:18:21', -1),
(112, 93, 'Aide a la personne', '2021-01-01 02:00:00', '2021-01-01 03:00:00', '2021-07-19 19:45:20', -1),
(111, 94, 'Cuisine', '2021-11-01 01:00:00', '2021-11-01 02:00:00', '2021-07-18 19:23:16', -1),
(118, 93, 'Cuisine', '2021-01-03 14:01:00', '2021-01-03 15:00:00', '2021-07-19 23:18:21', -1),
(115, 93, 'Aide a la personne', '2021-01-02 01:00:00', '2021-01-02 02:00:00', '2021-07-19 21:34:23', -1);

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
) ENGINE=MyISAM AUTO_INCREMENT=95 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `bulletinsalaire`
--

INSERT INTO `bulletinsalaire` (`id`, `idPersonne`, `mois`, `annee`, `tarifHoraire`, `date`) VALUES
(91, 1, 8, 2021, 15, '2021-07-18 18:52:08'),
(90, 1, 7, 2021, 15, '2021-07-18 18:51:19'),
(89, 1, 6, 2021, 15, '2021-07-18 18:49:46'),
(92, 1, 9, 2021, 15, '2021-07-18 18:54:28'),
(93, 1, 0, 2021, 15, '2021-07-18 19:12:39'),
(94, 1, 10, 2021, 15, '2021-07-18 19:22:46');

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
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

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
) ENGINE=MyISAM AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `pdf`
--

INSERT INTO `pdf` (`id`, `idBulletinSalaire`, `file`, `version`, `date`) VALUES
(57, 93, '1c\\1ce8dbf5-60ed-499f-bc58-9e30bf83da90.pdf', 0, '2021-07-19 22:48:35'),
(58, 93, '02\\0204ab71-262b-49ce-931f-ca14f29d36ab.pdf', 0, '2021-07-19 23:18:38'),
(59, 93, 'ea\\ea6dbd8b-3f93-4de5-9b45-466c2f27f7c7.pdf', 0, '2021-07-19 23:20:57');

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
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `personne`
--

INSERT INTO `personne` (`id`, `genre`, `nom`, `date`) VALUES
(1, 'Madame', 'azerty', '2021-07-05 11:40:00'),
(2, 'Madame', 'qwerty', '2021-07-05 15:16:04'),
(3, 'Madame', 'toto', '2021-07-05 15:19:32');

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
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
