-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jul 22, 2021 at 06:50 PM
-- Server version: 10.3.14-MariaDB
-- PHP Version: 7.3.5

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
  `activitee` text COLLATE utf8_bin NOT NULL,
  `gmtepoch_debut` bigint(20) NOT NULL,
  `gmtepoch_fin` bigint(20) NOT NULL,
  `date` datetime NOT NULL,
  `tarifHoraire` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=47 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `activite`
--

INSERT INTO `activite` (`id`, `idBulletinSalaire`, `activitee`, `gmtepoch_debut`, `gmtepoch_fin`, `date`, `tarifHoraire`) VALUES
(46, 9, 'pêche', 1625126400000, 1625130000000, '2021-07-22 20:47:49', -1),
(44, 9, 'Jeux', 1625126400000, 1625137200000, '2021-07-22 10:21:46', -1);

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
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `bulletinsalaire`
--

INSERT INTO `bulletinsalaire` (`id`, `idPersonne`, `mois`, `annee`, `tarifHoraire`, `date`) VALUES
(9, 1, 6, 2021, 15, '2021-07-22 10:04:50');

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
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `depassementforfaitaire`
--

INSERT INTO `depassementforfaitaire` (`id`, `idBulletinSalaire`, `dureeenheure`, `date`, `tarifHoraire`) VALUES
(3, 8, 0.5, '2021-07-22 10:00:36', -1),
(4, 9, 0.5, '2021-07-22 10:05:29', -1);

-- --------------------------------------------------------

--
-- Table structure for table `env`
--

DROP TABLE IF EXISTS `env`;
CREATE TABLE IF NOT EXISTS `env` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `CSG` float NOT NULL,
  `TauxImposition` float NOT NULL,
  `storePath` text COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `env`
--

INSERT INTO `env` (`id`, `CSG`, `TauxImposition`, `storePath`) VALUES
(1, 0.092, 0.11, 'E:/WS/GitHubPerso/FicheValerie/store');

-- --------------------------------------------------------

--
-- Table structure for table `listactivitee`
--

DROP TABLE IF EXISTS `listactivitee`;
CREATE TABLE IF NOT EXISTS `listactivitee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` text COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `listactivitee`
--

INSERT INTO `listactivitee` (`id`, `nom`) VALUES
(7, 'pÃªche'),
(5, 'PÃªche'),
(4, 'Jeux'),
(8, 'p%C3%AAche'),
(9, 'pÃªche'),
(10, 'pêche');

-- --------------------------------------------------------

--
-- Table structure for table `pdf`
--

DROP TABLE IF EXISTS `pdf`;
CREATE TABLE IF NOT EXISTS `pdf` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idBulletinSalaire` int(11) NOT NULL,
  `file` text COLLATE utf8_bin NOT NULL,
  `version` int(11) NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `pdf`
--

INSERT INTO `pdf` (`id`, `idBulletinSalaire`, `file`, `version`, `date`) VALUES
(18, 9, '9\\02\\0220210e-f796-470d-9257-caa9920a8fe5.pdf', 7, '2021-07-22 20:48:15'),
(17, 9, '9\\03\\031875ce-ca10-4553-aba0-f834820f849a.pdf', 6, '2021-07-22 20:42:11'),
(16, 9, '9\\0b\\0b548436-37bc-473b-a451-de5c4861eedb.pdf', 5, '2021-07-22 12:53:33'),
(15, 9, '9\\d4\\d4a72c38-8fa0-492f-8e4a-fd86989067da.pdf', 4, '2021-07-22 12:49:11'),
(14, 9, '9\\bb\\bbc7b5a6-66d8-4506-8cb7-bae561d02127.pdf', 3, '2021-07-22 12:45:26'),
(13, 9, '9\\06\\0621d91f-114f-46a0-8c02-71efdc346ab6.pdf', 2, '2021-07-22 12:44:29'),
(12, 9, '9\\b9\\b9140d8a-45d5-461e-b8bb-1213fadf4b52.pdf', 1, '2021-07-22 10:21:57');

-- --------------------------------------------------------

--
-- Table structure for table `personne`
--

DROP TABLE IF EXISTS `personne`;
CREATE TABLE IF NOT EXISTS `personne` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `genre` set('Madame','Monsieur') COLLATE utf8_bin NOT NULL,
  `nom` text COLLATE utf8_bin NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `personne`
--

INSERT INTO `personne` (`id`, `genre`, `nom`, `date`) VALUES
(1, 'Madame', 'Beautée', '2021-07-21 19:08:55'),
(2, 'Monsieur', 'ùéèàê', '2021-07-22 11:19:01');

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
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `rappel`
--

INSERT INTO `rappel` (`id`, `idBulletinSalaire`, `idBulletinSalaireOrigine`, `dureeenheure`, `tarifHoraire`, `date`, `status`) VALUES
(3, 8, -1, 14.75, -1, '2021-07-22 10:00:36', 1),
(4, 9, -1, 14.75, -1, '2021-07-22 10:05:29', 1);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
