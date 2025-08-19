CREATE DATABASE aluguel_veiculos CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE aluguel_veiculos;

CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL,
    nivel ENUM('Administrador', 'Atendente', 'Mecânico') NOT NULL
);

INSERT INTO usuario (login, senha, nivel) VALUES 
('admin', '123', 'Administrador'),
('cleb', '1234', 'Atendente'),
('wallan', '4321', 'Mecânico');

CREATE TABLE IF NOT EXISTS cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    telefone VARCHAR(20),
    endereco VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS veiculo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    modelo VARCHAR(100) NOT NULL,
    placa VARCHAR(10) NOT NULL UNIQUE,
    status ENUM('Disponível','Alugado','Manutenção') DEFAULT 'Disponível'
);

CREATE TABLE IF NOT EXISTS aluguel (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    veiculo_id INT NOT NULL,
    data_inicio DATE NOT NULL,
    data_fim DATE,
    devolvido BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id),
    FOREIGN KEY (veiculo_id) REFERENCES veiculo(id)
);

INSERT INTO cliente (nome, cpf, telefone, endereco) VALUES
('Adriano Antunes','321.111.987-00','(38) 95555-3333','Rua das Redes, 120'),
('Paulo Veloso','147.222.369-11','(38) 94444-2222','Av. Orientada a Objetos, 800'),
('Cleiane Oliveira','147.333.369-11','(38) 94444-2222','Av. Banco de Dados, 800');

INSERT INTO veiculo (modelo, placa, status) VALUES
('Gol 1.0','ABC-1234','Disponível'),
('Uno Endemoniado','SLK-1234','Disponível'),
('Celta 1.0','ZZZ-1234','Disponível'),
('Onix 1.4','DEF-5678','Disponível');



CREATE TABLE IF NOT EXISTS manutencoes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    veiculo_id INT NOT NULL,
    usuario_id INT NOT Null,
    descricao VARCHAR(255) NOT NULL,
    data_solicitacao DATE NOT NULL,
    data_conclusao DATE,
    status ENUM('PENDENTE', 'EM_ANDAMENTO', 'FINALIZADA') DEFAULT 'PENDENTE'
);

insert into manutencoes () VALUES
('Problema na caixa de marcha, e farol de neblina sem funcionar o lado esquerdo', 2025/07/10, 2025/08/12, 'FINALIZADA')


CREATE TABLE IF NOT EXISTS solicitacoes_pecas(
    id INT auto_increment primary key,
    usuario_id INT NOT NULL,
    nome_peca VARCHAR(100) NOT NULL,
    quantidade INT NOT NULL,
    data_solicitacao DATE NOT NULL,
    status ENUM('EM_ANALISE', 'APROVADA', 'EM_ANDAMENTO') DEFAULT 'EM_ANALISE'
);

insert into solicitacoes_pecas() VALUES
('farol de neblina da luz amarela', '1', 2025/07/11,'APROVADA');
