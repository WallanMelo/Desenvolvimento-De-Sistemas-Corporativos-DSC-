CREATE DATABASE aluguel_veiculos CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE aluguel_veiculos;

CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(100) NOT NULL UNIQUE,  -- pode ser email ou CPF
    senha VARCHAR(100) NOT NULL,
    nivel ENUM('Administrador', 'Atendente', 'Mecânico') NOT NULL
);

-- Inserindo usuários para teste:
INSERT INTO usuario (login, senha, nivel) VALUES 
('admin', '123', 'Administrador'),
('joao', '1234', 'Atendente'),
('maria', '4321', 'Mecânico');
