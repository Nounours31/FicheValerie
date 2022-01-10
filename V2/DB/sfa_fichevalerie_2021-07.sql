-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3307
-- Généré le :  ven. 16 juil. 2021 à 22:18
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
CREATE DATABASE IF NOT EXISTS `sfa_fichevalerie` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `sfa_fichevalerie`;

-- --------------------------------------------------------

--
-- Structure de la table `activite`
--

DROP TABLE IF EXISTS `depassementforfaitaire`;
CREATE TABLE IF NOT EXISTS `depassementforfaitaire` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idBulletinSalaire` int(11) NOT NULL,
  `dureeenheure` float NOT NULL,
  `date` datetime NOT NULL,
  `tarifHoraire` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

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
) ENGINE=MyISAM AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `activite`
--

INSERT INTO `activite` (`id`, `idBulletinSalaire`, `activitee`, `debut`, `fin`, `date`, `tarifHoraire`) VALUES
(48, 44, 'Cuisine', '2021-07-01 08:00:00', '2021-07-01 09:00:00', '2021-07-05 19:48:22', -1),
(47, 43, 'Cuisine', '2021-07-01 08:00:00', '2021-07-01 09:00:00', '2021-07-05 19:42:19', -1),
(46, 42, 'Cuisine', '2021-07-01 08:00:00', '2021-07-01 09:00:00', '2021-07-05 19:29:56', -1);

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
  `tarifHoraire` float NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `bulletinsalaire`
--

INSERT INTO `bulletinsalaire` (`id`, `idPersonne`, `mois`, `annee`, `tarifHoraire`, `date`) VALUES
(42, 1, 6, 2021, 12.34, '2021-07-05 19:29:48'),
(43, 1, 6, 2021, 12.34, '2021-07-05 19:42:19'),
(44, 1, 6, 2021, 12.34, '2021-07-05 19:48:21');

-- --------------------------------------------------------

--
-- Structure de la table `pdf`
--

DROP TABLE IF EXISTS `pdf`;
CREATE TABLE IF NOT EXISTS `pdf` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idBulletinSalaire` int(11) NOT NULL,
  `file` text NOT NULL,
  `version` int(11) NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `pdf`
--

INSERT INTO `pdf` (`id`, `idBulletinSalaire`, `file`, `version`, `date`) VALUES
(37, 44, '14\\1479e189-2804-4639-a8a9-db5ba1f1d681.pdf', 0, '2021-07-05 19:48:22'),
(36, 43, '2f\\2fa2e451-6a03-4527-841a-40ed0c5cf002.pdf', 0, '2021-07-05 19:42:20'),
(35, 42, 'f9\\f91aeb98-55e6-4730-82dc-27b24437bd0a.pdf', 0, '2021-07-05 19:29:57');

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
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `personne`
--

INSERT INTO `personne` (`id`, `genre`, `nom`, `date`) VALUES
(1, 'Madame', 'azerty', '2021-07-05 11:40:00'),
(2, 'Madame', 'qwerty', '2021-07-05 15:16:04'),
(3, 'Madame', 'toto', '2021-07-05 15:19:32');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
