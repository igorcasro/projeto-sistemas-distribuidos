-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 01/06/2023 às 02:51
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
  `data` timestamp(6) NOT NULL DEFAULT current_timestamp(6) ON UPDATE current_timestamp(6)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Despejando dados para a tabela `incidente`
--

INSERT INTO `incidente` (`id_incidente`, `id_usuario`, `token`, `tipo_incidente`, `km`, `rodovia`, `data`) VALUES
(3, 23, '3W7jRpJqPVzTJrCXZi5ml8q2', 13, 392, 'BR-200', '2023-05-25 14:50:00.000000'),
(4, 23, '3W7jRpJqPVzTJrCXZi5ml8q2', 12, 24, 'PR-190', '1999-01-03 02:33:33.000000'),
(5, 24, 'Ovip9hoegsbxcxZ59fRXvwLn', 4, 342, 'PR-157', '2023-05-05 15:00:00.000000'),
(6, 24, 'DvpuxpzA9u5h7Fqu2dY2GhLD', 1, 230, 'GO-070', '2023-02-03 01:22:22.000000'),
(7, 23, '3q7PBsQEh7OsLKod9ymuVyjs', 8, 111, 'GO-111', '2023-05-25 15:00:00.000000'),
(8, 25, 'n79FTh0GGYMtWj1NtcKXnuBS', 12, 0, 'BR-555', '2023-05-31 22:55:38.000000'),
(9, 25, 'aFC5Mh9R8hppU2nEFCv5tGNk', 6, 111, 'BR-555', '2023-05-31 23:00:58.000000'),
(10, 25, 'xIbJ7FBqojxqanWQGOK4EAPg', 6, 555, 'BR-555', '2023-05-31 23:28:22.000000');

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
('Igor', 'igorpfcastro@gmail.com', '|m{|m9:;', NULL, 25);

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
  MODIFY `id_incidente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de tabela `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
