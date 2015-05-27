-- phpMyAdmin SQL Dump
-- version 4.1.12
-- http://www.phpmyadmin.net
--
-- Machine: localhost:3306
-- Gegenereerd op: 27 mei 2015 om 22:55
-- Serverversie: 5.5.34
-- PHP-versie: 5.5.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Databank: `asrs`
--

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `products`
--

CREATE TABLE `products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `x` int(7) NOT NULL,
  `y` int(7) NOT NULL,
  `size` int(7) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=26 ;

--
-- Gegevens worden geëxporteerd voor tabel `products`
--

INSERT INTO `products` (`id`, `name`, `x`, `y`, `size`) VALUES
(1, 'iPhone 6', 0, 0, 1),
(2, 'iPhone 6 plus', 0, 1, 1),
(3, 'MacBook Pro', 0, 2, 2),
(4, 'iMac', 0, 3, 2),
(5, 'Mac Pro', 0, 4, 2),
(6, 'iPad', 1, 0, 1),
(7, 'iPod nano', 1, 1, 1),
(8, 'Cola', 1, 2, 1),
(9, 'Sinas', 1, 3, 1),
(10, 'Brood', 1, 4, 2),
(11, 'Jam', 2, 0, 1),
(12, 'Kaas', 2, 1, 1),
(13, 'Hagelslag', 2, 2, 1),
(14, 'Broodtrommel', 2, 3, 1),
(15, 'Stekkerdoos', 2, 4, 1),
(16, 'Bril', 3, 0, 1),
(17, 'Stofzuiger', 3, 1, 1),
(18, 'TV', 3, 2, 2),
(19, 'Magnetron', 3, 3, 2),
(20, 'Gitaar', 3, 4, 2),
(21, 'Drumstel', 4, 0, 2),
(22, 'Keyboard', 4, 1, 2),
(23, 'Oplader', 4, 2, 1),
(24, 'je moeder', 4, 3, 2),
(25, 'Muis', 4, 4, 1);
