CREATE DATABASE aluguel_veiculos CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE aluguel_veiculos;

CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    telefone VARCHAR(20),
    data_nascimento DATE,
    login VARCHAR(100) NOT NULL UNIQUE, -- pode ser email ou CPF
    senha VARCHAR(100) NOT NULL,
    nivel ENUM('Administrador', 'Atendente', 'Mecânico') NOT NULL,
    salario DECIMAL(10,2) DEFAULT 0.00
);

-- USUARIOS (FUNCIONARIOS) PADRÕES
INSERT INTO usuario (nome, cpf, email, telefone, data_nascimento, login, senha, nivel, salario) VALUES
('Administrador Geral', '000.000.000-00', 'admin@avj.com', '(38) 99999-0000', '1980-01-01', 'admin', '123', 'Administrador', 5000.00),
('Clebson Atendente', '111.111.111-11', 'cleb@avj.com', '(38) 98888-1111', '1995-05-10', 'cleb', '1234', 'Atendente', 2500.00),
('Wallan Mecânico', '222.222.222-22', 'wallan@avj.com', '(38) 97777-2222', '2005-09-20', 'wallan', '4321', 'Mecânico', 3000.00);

CREATE TABLE cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    cnh VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL,
    telefone VARCHAR(20),
    data_nascimento DATE,
    endereco VARCHAR(200)
);


INSERT INTO cliente (nome, cpf, cnh, email, telefone, data_nascimento, endereco) VALUES
('Adriano Antunes','321.111.987-00','12345678900','adriano@ifnmg.com','(38) 95555-3333','1992-07-15','Rua das Redes, 120'),
('Paulo Veloso','147.222.369-11','98765432100','paulo@ifnmg.com','(38) 94444-2222','1988-02-28','Av. Orientada a Objetos, 800'),
('Cleiane Oliveira','147.333.369-11','55555555555','cleiane@ifnmg.com','(38) 93333-1111','1995-11-10','Av. Banco de Dados, 777');

CREATE TABLE veiculo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    modelo VARCHAR(100) NOT NULL,
    fabricante VARCHAR(100),
    tipo VARCHAR(50),
    ano YEAR,
    placa VARCHAR(10) NOT NULL UNIQUE,
    estado_conservacao VARCHAR(100),
    cor VARCHAR(50),
    caracteristicas TEXT,
    status ENUM('Disponível','Alugado','Manutenção') DEFAULT 'Disponível'
);

INSERT INTO veiculo (modelo, fabricante, tipo, ano, placa, estado_conservacao, cor, caracteristicas, status) VALUES
('Gol 1.0', 'Volkswagen', 'Hatch', 2015, 'ABC-1234', 'Bom', 'Prata', 'Econômico', 'Disponível'),
('Uno Endemoniado', 'Fiat', 'Hatch', 2010, 'SLK-1234', 'Regular', 'Branco', 'Som potente', 'Disponível'),
('Celta 1.0', 'Chevrolet', 'Hatch', 2012, 'ZZZ-1234', 'Bom', 'Preto', 'Completo', 'Disponível'),
('Onix 1.4', 'Chevrolet', 'Hatch', 2018, 'DEF-5678', 'Ótimo', 'Vermelho', 'Ar e direção', 'Disponível'),
('Strada', 'Fiat', 'Hatch', 2020, 'MAL-2538', 'Ótimo', 'Verde', 'Cabine Estendida', 'Manutenção'),
('Hillux', 'Toyota', 'Hatch', 2022, 'HAK-1586', 'Regular', 'Rosa Pink', '4x4', 'Manutenção');

-- =========================
-- TABELA DE ALUGUEIS
-- =========================
CREATE TABLE aluguel (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    veiculo_id INT NOT NULL,
    data_inicio DATE NOT NULL,
    data_fim DATE,
    prazo_devolucao DATE NOT NULL,
    detalhes TEXT,
    valor DECIMAL(10,2) DEFAULT 0.00,
    multa DECIMAL(10,2) DEFAULT 0.00,
    devolvido BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id),
    FOREIGN KEY (veiculo_id) REFERENCES veiculo(id)
);


-- =========================
-- TABELA DE MANUTENÇÕES E INSERT INTO
-- =========================
CREATE TABLE IF NOT EXISTS manutencoes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    veiculo_id INT NOT NULL,
    usuario_id INT NOT Null,
    descricao VARCHAR(255) NOT NULL,
    data_solicitacao DATE NOT NULL,
    data_conclusao DATE,
    status ENUM('Pendente', 'Em_Andamento', 'Finalizada') DEFAULT 'Pendente',
    FOREIGN KEY (veiculo_id) REFERENCES veiculo(id),
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

INSERT INTO manutencoes (veiculo_id, usuario_id, descricao, data_solicitacao, data_conclusao, status) VALUES
(5, 3, 'Problema na caixa de marcha, e farol de neblina sem funcionar o lado esquerdo', '2025-07-10', NULL, 'Pendente'),
(6, 3, 'Cinto de Segurança do passageiro quebrado', '2025-07-30', NULL, 'Pendente');

-- =========================
-- TABELA DE SOLICITAÇÕES DE PEÇAS E INSERT INTO
-- =========================
CREATE TABLE IF NOT EXISTS solicitacoes_pecas(
    id INT auto_increment primary key,
    usuario_id INT NOT NULL,
    nome_peca VARCHAR(100) NOT NULL,
    quantidade INT NOT NULL,
    data_solicitacao DATE NOT NULL,
    justificativa TEXT NOT NULL,
    veiculo_id INT NOT NULL,
    status ENUM('Em_Analise', 'Aprovada', 'Recusada') DEFAULT 'Em_Analise',
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    FOREIGN KEY (veiculo_id) REFERENCES veiculo(id)
);

INSERT INTO solicitacoes_pecas (usuario_id, nome_peca, quantidade, data_solicitacao, justificativa, veiculo_id, status) VALUES
(3, 'Farol de neblina da luz amarela', 1, '2025-07-11', 'Manutenção corretiva', 1, 'Aprovada');
