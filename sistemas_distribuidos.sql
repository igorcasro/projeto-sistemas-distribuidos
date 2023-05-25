-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 25/05/2023 às 19:23
-- Versão do servidor: 10.4.28-MariaDB
-- Versão do PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `sistemas_distribuidos`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `incidente`
--

CREATE TABLE `incidente` (
  `id_incidente` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `token` varchar(36) NOT NULL,
  `tipo_incidente` int(11) NOT NULL,
  `km` int(11) NOT NULL,
  `rodovia` varchar(6) NOT NULL,
  `data` varchar(19) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Despejando dados para a tabela `incidente`
--

INSERT INTO `incidente` (`id_incidente`, `id_usuario`, `token`, `tipo_incidente`, `km`, `rodovia`, `data`) VALUES
(1, 23, 'AAAAAAAAAAAAAAAAAAAAA', 1, 123, 'BR-153', '2023-05-25 11:13:00'),
(2, 23, 'AAAAAAAAAAAAAAAAAAAAA', 1, 123, 'BR-153', '2023-05-25 11:13:00'),
(3, 23, '3W7jRpJqPVzTJrCXZi5ml8q2', 13, 392, 'BR-200', '2023-05-25 11:50:00'),
(4, 23, '3W7jRpJqPVzTJrCXZi5ml8q2', 12, 24, 'PR-190', '1999-01-02 23:33:33'),
(5, 24, 'Ovip9hoegsbxcxZ59fRXvwLn', 4, 342, 'PR-157', '2023-05-05 12:00:00'),
(6, 24, 'DvpuxpzA9u5h7Fqu2dY2GhLD', 1, 230, 'GO-070', '2023-02-02 22:22:22');

-- --------------------------------------------------------

--
-- Estrutura para tabela `usuario`
--

CREATE TABLE `usuario` (
  `nome` varchar(32) NOT NULL,
  `email` varchar(50) NOT NULL,
  `senha` varchar(32) NOT NULL,
  `token` varchar(36) DEFAULT NULL,
  `id_usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Despejando dados para a tabela `usuario`
--

INSERT INTO `usuario` (`nome`, `email`, `senha`, `token`, `id_usuario`) VALUES
('igor', 'igorpfcastro@gmail.com', '|m{|m9:;', 'ghWIEJZq9lhSor3ll2Qz3aXz', 23),
('Eduardo', 'eduardo@gmail.com', 'nz|p:>', 'DvpuxpzA9u5h7Fqu2dY2GhLD', 24);

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `incidente`
--
ALTER TABLE `incidente`
  ADD PRIMARY KEY (`id_incidente`);

--
-- Índices de tabela `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id_usuario`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `incidente`
--
ALTER TABLE `incidente`
  MODIFY `id_incidente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de tabela `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
