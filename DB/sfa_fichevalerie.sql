-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3307
-- Généré le :  ven. 02 juil. 2021 à 22:14
-- Version du serveur :  10.3.14-MariaDB
-- Version de PHP :  7.3.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `sfa_fichevalerie`
--
CREATE DATABASE IF NOT EXISTS `sfa_fichevalerie` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `sfa_fichevalerie`;

-- --------------------------------------------------------

--
-- Structure de la table `bulletinsalaire`
--

DROP TABLE IF EXISTS `bulletinsalaire`;
CREATE TABLE IF NOT EXISTS `bulletinsalaire` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idPersonne` int(11) NOT NULL,
  `mois` int(11) NOT NULL,
  `annee` int(11) NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `bulletinsalaire`
--

INSERT INTO `bulletinsalaire` (`id`, `idPersonne`, `mois`, `annee`, `date`) VALUES
(1, 1, 1, 2021, '2021-07-01 00:00:00');

-- --------------------------------------------------------

--
-- Structure de la table `infohoraire`
--

DROP TABLE IF EXISTS `infohoraire`;
CREATE TABLE IF NOT EXISTS `infohoraire` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idBulletin` int(11) NOT NULL,
  `activitee` int(11) NOT NULL,
  `horairedebut` datetime NOT NULL,
  `horairefin` datetime NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `listeactivitee`
--

DROP TABLE IF EXISTS `listeactivitee`;
CREATE TABLE IF NOT EXISTS `listeactivitee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activitee` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `listeactivitee`
--

INSERT INTO `listeactivitee` (`id`, `activitee`) VALUES
(1, 'aide à la personne'),
(2, 'menage');

-- --------------------------------------------------------

--
-- Structure de la table `pdf`
--

DROP TABLE IF EXISTS `pdf`;
CREATE TABLE IF NOT EXISTS `pdf` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idBulletinSalaire` int(11) NOT NULL,
  `file` text NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `personne`
--

DROP TABLE IF EXISTS `personne`;
CREATE TABLE IF NOT EXISTS `personne` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `genre` set('Madame','Monsieur') NOT NULL,
  `nom` text NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=41 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `personne`
--

INSERT INTO `personne` (`id`, `genre`, `nom`, `date`) VALUES
(12, 'Madame', 'TarteMuche', '2021-06-30 00:00:00'),
(34, 'Madame', 'ffdd', '2021-07-01 20:07:55'),
(15, 'Madame', 'De machin de corvee de chiotte', '2021-06-30 00:00:00'),
(11, 'Monsieur', 'Test', '2021-06-30 00:00:00'),
(39, 'Madame', 'zqazq', '2021-07-01 20:22:55'),
(38, 'Madame', 'kiki', '2021-07-01 20:21:17'),
(37, 'Madame', 'qwx', '2021-07-01 20:19:31'),
(36, 'Monsieur', 'qw', '2021-07-01 20:18:11'),
(35, 'Monsieur', 'az', '2021-07-01 20:09:33'),
(33, 'Madame', 'ff', '2021-07-01 20:07:20'),
(32, 'Monsieur', 'dede', '2021-07-01 20:05:10'),
(40, 'Madame', 'qwaaaq', '2021-07-01 20:24:43');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
