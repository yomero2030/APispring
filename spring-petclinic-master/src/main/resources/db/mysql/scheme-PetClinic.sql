-- phpMyAdmin SQL Dump
-- version 4.8.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 01-02-2019 a las 02:26:43
-- Versión del servidor: 10.1.34-MariaDB
-- Versión de PHP: 7.2.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `petclinic`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `medicamento`
--

CREATE TABLE `medicamento` (
  `id` int(11) UNSIGNED NOT NULL,
  `nombre` varchar(40) NOT NULL,
  `ingrediente_activo` varchar(40) NOT NULL,
  `presentacion` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `medicamento`
--

INSERT INTO `medicamento` (`id`, `nombre`, `ingrediente_activo`, `presentacion`) VALUES
(1, 'paracetamol', 'paracetamol :v', 'cajita'),
(2, 'ibuprofeno', 'ibuprofeno :v', 'cajita');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `owners`
--

CREATE TABLE `owners` (
  `id` int(4) UNSIGNED NOT NULL,
  `first_name` varchar(30) DEFAULT NULL,
  `last_name` varchar(30) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `city` varchar(80) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `latitud` varchar(50) DEFAULT NULL,
  `longitud` varchar(50) DEFAULT NULL,
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `owners`
--

INSERT INTO `owners` (`id`, `first_name`, `last_name`, `address`, `city`, `telephone`) VALUES
(1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023'),
(2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749'),
(3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763'),
(4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198'),
(5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765'),
(6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654'),
(7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387'),
(8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683'),
(9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435'),
(10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487'),
(11, 'Williams de Jesús', 'García Orozco', 'Calle 8a. Oriente Sur San Roque', 'Tuxtla Gutiérrez', '9616693142'),
(12, 'Rosalino', 'Orozco', 'Maldonado', 'Tuxtla Gutiérrez', '9612345678');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pets`
--

CREATE TABLE `pets` (
  `id` int(4) UNSIGNED NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  `type_id` int(4) UNSIGNED NOT NULL,
  `owner_id` int(4) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `pets`
--

INSERT INTO `pets` (`id`, `name`, `birth_date`, `type_id`, `owner_id`) VALUES
(1, 'Leo', '2000-09-07', 1, 1),
(2, 'Basil', '2002-08-06', 6, 2),
(3, 'Rosy', '2001-04-17', 2, 3),
(4, 'Jewel', '2000-03-07', 2, 3),
(5, 'Iggy', '2000-11-30', 3, 4),
(6, 'George', '2000-01-20', 4, 5),
(7, 'Samantha', '1995-09-04', 1, 6),
(8, 'Max', '1995-09-04', 1, 6),
(9, 'Lucky', '1999-08-06', 5, 7),
(10, 'Mulligan', '1997-02-24', 2, 8),
(11, 'Freddy', '2000-03-09', 5, 9),
(12, 'Lucky', '2000-06-24', 2, 10),
(13, 'Sly', '2002-06-08', 1, 10),
(14, 'Fido', '2003-06-30', 2, 11);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `products`
--

CREATE TABLE `products` (
  `id` int(11) NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 NOT NULL,
  `description` text CHARACTER SET utf8 NOT NULL,
  `price` float NOT NULL,
  `existence` int(11) NOT NULL,
  `photo` varchar(100) CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `products`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `records`
--

CREATE TABLE `records` (
  `id` int(4) UNSIGNED NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `record_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `type` varchar(255) DEFAULT NULL,
  `user_email` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `records`
--

INSERT INTO `records` (`id`, `description`, `record_date`, `type`, `user_email`) VALUES
(1, 'Contraseña o usuario incorrectos', NULL, 'failure', 'd'),
(2, 'Usuario ingreso al sistema sin problemas', NULL, 'success', 'chairo@chairo.com'),
(3, 'Usuario ingreso al sistema sin problemas', NULL, 'success', 'chairo@chairo.com'),
(4, 'Usuario ingreso al sistema sin problemas', NULL, 'success', 'chairo@chairo.com'),
(5, 'Usuario ingreso al sistema sin problemas', NULL, 'success', 'chairo@chairo.com');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `specialties`
--

CREATE TABLE `specialties` (
  `id` int(4) UNSIGNED NOT NULL,
  `name` varchar(80) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `specialties`
--

INSERT INTO `specialties` (`id`, `name`) VALUES
(3, 'dentistry'),
(1, 'radiology'),
(2, 'surgery');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `types`
--

CREATE TABLE `types` (
  `id` int(4) UNSIGNED NOT NULL,
  `name` varchar(80) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `types`
--

INSERT INTO `types` (`id`, `name`) VALUES
(5, 'bird'),
(1, 'cat'),
(2, 'dog'),
(6, 'hamster'),
(3, 'lizard'),
(4, 'snake');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE `users` (
  `id` int(4) UNSIGNED NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `first_name` varchar(30) DEFAULT NULL,
  `last_name` varchar(30) DEFAULT NULL,
  `active` int(11) NOT NULL,
  `password` varchar(255) NOT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `zipcode` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`id`, `email`, `first_name`, `last_name`, `active`, `password`, `telephone`, `zipcode`) VALUES
(1, 'chairo@chairo.com', 'Kevin<3Augusto', 'Lo-pez-que', 1, '{bcrypt}$2a$10$E1X2oYQFyDnUibAaRjZ2Ber3qevby6K4/4uzGQAnLxz7MjIMJnCnq', '9191172505', '29000');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vets`
--

CREATE TABLE `vets` (
  `id` int(4) UNSIGNED NOT NULL,
  `first_name` varchar(30) DEFAULT NULL,
  `last_name` varchar(30) DEFAULT NULL,
  `telephone` varchar(10) NOT NULL,
  `Schedule` varchar(100) NOT NULL,
  `specialty_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `vets`
--

INSERT INTO `vets` (`id`, `first_name`, `last_name`, `telephone`, `Schedule`, `specialty_id`) VALUES
(2, 'Williams de Jesús', 'García Orozco', '9616693142', '8:00am - 12:00pm', 'radiology'),
(3, 'Williams de Jesús', 'García Orozco', '9616693142', '8:00 am - 12:00 pm', 'dentistry'),
(4, 'Rosalino', 'Orozco', '9616693146', '8:00 am - 12:00 pm', 'radiology'),
(5, 'Mary', 'Pekerman', '9876543210', '12:00 pm - 4:00 pm', 'surgery'),
(6, 'Victor', 'Ramos Fonbon', '098765432', '12:00 pm - 4:00 pm', 'radiology');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vet_specialties`
--

CREATE TABLE `vet_specialties` (
  `vet_id` int(4) UNSIGNED NOT NULL,
  `specialty_id` int(4) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `visits`
--

CREATE TABLE `visits` (
  `id` int(4) UNSIGNED NOT NULL,
  `pet_id` int(4) UNSIGNED NOT NULL,
  `visit_date` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `visits`
--

INSERT INTO `visits` (`id`, `pet_id`, `visit_date`, `description`) VALUES
(1, 7, '2010-03-04', 'rabies shot'),
(2, 8, '2011-03-04', 'rabies shot'),
(3, 8, '2009-06-04', 'neutered'),
(4, 7, '2008-09-04', 'spayed');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `medicamento`
--
ALTER TABLE `medicamento`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `owners`
--
ALTER TABLE `owners`
  ADD PRIMARY KEY (`id`),
  ADD KEY `last_name` (`last_name`);

--
-- Indices de la tabla `pets`
--
ALTER TABLE `pets`
  ADD PRIMARY KEY (`id`),
  ADD KEY `name` (`name`),
  ADD KEY `owner_id` (`owner_id`),
  ADD KEY `type_id` (`type_id`);

--
-- Indices de la tabla `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `records`
--
ALTER TABLE `records`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `specialties`
--
ALTER TABLE `specialties`
  ADD PRIMARY KEY (`id`),
  ADD KEY `name` (`name`);

--
-- Indices de la tabla `types`
--
ALTER TABLE `types`
  ADD PRIMARY KEY (`id`),
  ADD KEY `name` (`name`);

--
-- Indices de la tabla `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indices de la tabla `vets`
--
ALTER TABLE `vets`
  ADD PRIMARY KEY (`id`),
  ADD KEY `last_name` (`last_name`),
  ADD KEY `specialty` (`specialty_id`);

--
-- Indices de la tabla `vet_specialties`
--
ALTER TABLE `vet_specialties`
  ADD UNIQUE KEY `vet_id` (`vet_id`,`specialty_id`),
  ADD KEY `specialty_id` (`specialty_id`);

--
-- Indices de la tabla `visits`
--
ALTER TABLE `visits`
  ADD PRIMARY KEY (`id`),
  ADD KEY `pet_id` (`pet_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `medicamento`
--
ALTER TABLE `medicamento`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `owners`
--
ALTER TABLE `owners`
  MODIFY `id` int(4) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `pets`
--
ALTER TABLE `pets`
  MODIFY `id` int(4) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `records`
--
ALTER TABLE `records`
  MODIFY `id` int(4) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `specialties`
--
ALTER TABLE `specialties`
  MODIFY `id` int(4) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `types`
--
ALTER TABLE `types`
  MODIFY `id` int(4) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `users`
--
ALTER TABLE `users`
  MODIFY `id` int(4) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `vets`
--
ALTER TABLE `vets`
  MODIFY `id` int(4) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `visits`
--
ALTER TABLE `visits`
  MODIFY `id` int(4) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `pets`
--
ALTER TABLE `pets`
  ADD CONSTRAINT `pets_ibfk_1` FOREIGN KEY (`owner_id`) REFERENCES `owners` (`id`),
  ADD CONSTRAINT `pets_ibfk_2` FOREIGN KEY (`type_id`) REFERENCES `types` (`id`);

--
-- Filtros para la tabla `vet_specialties`
--
ALTER TABLE `vet_specialties`
  ADD CONSTRAINT `vet_specialties_ibfk_1` FOREIGN KEY (`vet_id`) REFERENCES `vets` (`id`),
  ADD CONSTRAINT `vet_specialties_ibfk_2` FOREIGN KEY (`specialty_id`) REFERENCES `specialties` (`id`);

--
-- Filtros para la tabla `visits`
--
ALTER TABLE `visits`
  ADD CONSTRAINT `visits_ibfk_1` FOREIGN KEY (`pet_id`) REFERENCES `pets` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
