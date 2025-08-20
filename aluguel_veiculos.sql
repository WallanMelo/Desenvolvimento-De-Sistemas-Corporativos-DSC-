DROP DATABASE aluguel_veiculos;
CREATE DATABASE aluguel_veiculos CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE aluguel_veiculos;

-- =========================
-- TABELA DE USUARIO E INSERT INTO
-- =========================
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

-- =========================
-- TABELA DE CLIENTE E INSERT INTO
-- =========================
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

-- =========================
-- TABELA DE VEICULO E INSERT INTO
-- =========================
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
-- TABELA DE ALUGUEIS E INSERT INTO
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

-- devolvido dentro do prazo e por isso sem multa
INSERT INTO aluguel (cliente_id, veiculo_id, data_inicio, data_fim, prazo_devolucao, detalhes, valor, multa, devolvido)
VALUES (1, 1, '2025-07-01', '2025-07-05', '2025-07-05', 'Aluguel para viagem de negócios', 250.00, 0.00, TRUE);

-- foi devolvido atrasado e por isso com multa
INSERT INTO aluguel (cliente_id, veiculo_id, data_inicio, data_fim, prazo_devolucao, detalhes, valor, multa, devolvido)
VALUES (2, 3, '2025-07-10', '2025-07-13', '2025-07-12', 'Aluguel para passeio familiar', 180.00, 50.00, TRUE);

-- veicuolo n foi devolvido e ainda está em andamento
INSERT INTO aluguel (cliente_id, veiculo_id, data_inicio, data_fim, prazo_devolucao, detalhes, valor, multa, devolvido)
VALUES (3, 2, '2025-07-15', NULL, '2025-07-20', 'Aluguel para uso pessoal', 300.00, 0.00, FALSE);

-- Aluguel 4: Devolvido sem multa
INSERT INTO aluguel (cliente_id, veiculo_id, data_inicio, data_fim, prazo_devolucao, detalhes, valor, multa, devolvido)
VALUES (1, 4, '2025-07-03', '2025-07-07', '2025-07-07', 'Aluguel para viagem rápida', 320.00, 0.00, TRUE);

-- Aluguel 5: Não devolvido (atrasado - deve multa)
INSERT INTO aluguel (cliente_id, veiculo_id, data_inicio, data_fim, prazo_devolucao, detalhes, valor, multa, devolvido)
VALUES (2, 1, '2025-07-08', NULL, '2025-07-10', 'Aluguel para trabalho', 200.00, 100.00, FALSE);

-- foi devolvido com multa por causa de danos ao veículo
INSERT INTO aluguel (cliente_id, veiculo_id, data_inicio, data_fim, prazo_devolucao, detalhes, valor, multa, devolvido)
VALUES (3, 3, '2025-07-05', '2025-07-08', '2025-07-08', 'Aluguel para festa', 150.00, 200.00, TRUE);

-- devolvido no prazo certo então sem multa
INSERT INTO aluguel (cliente_id, veiculo_id, data_inicio, data_fim, prazo_devolucao, detalhes, valor, multa, devolvido)
VALUES (1, 2, '2025-07-12', '2025-07-15', '2025-07-15', 'Aluguel para visita a parentes', 280.00, 0.00, TRUE);

-- N devolvido então está em andamento dentro do prazo
INSERT INTO aluguel (cliente_id, veiculo_id, data_inicio, data_fim, prazo_devolucao, detalhes, valor, multa, devolvido)
VALUES (2, 4, '2025-07-18', NULL, '2025-07-25', 'Aluguel para férias', 450.00, 0.00, FALSE);

-- devolvido no prazo certo então sem multa
INSERT INTO aluguel (cliente_id, veiculo_id, data_inicio, data_fim, prazo_devolucao, detalhes, valor, multa, devolvido)
VALUES (3, 1, '2025-07-20', '2025-07-22', '2025-07-22', 'Aluguel para compromisso rápido', 120.00, 0.00, TRUE);

-- N devolvido está atrasado e deve multa)
INSERT INTO aluguel (cliente_id, veiculo_id, data_inicio, data_fim, prazo_devolucao, detalhes, valor, multa, devolvido)
VALUES (1, 3, '2025-07-14', NULL, '2025-07-16', 'Aluguel para emergência', 90.00, 75.00, FALSE);


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

-- ========================================
-- TABELA DE RELATÓRIOS E INSERT INTO
-- =======================================
CREATE TABLE IF NOT EXISTS relatorios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    tipo ENUM('Financeiro', 'Operacional', 'Salarios') NOT NULL,
    periodoInicio DATE NOT NULL,
    periodoFim DATE NOT NULL,
    formato ENUM('PDF', 'XLSX') not null,
    data_geracao timestamp default CURRENT_TIMESTAMP,
    foreign key (usuario_id) references usuario(id)
);

INSERT INTO relatorios (usuario_id, tipo, periodoInicio, periodoFim, formato) VALUES
(1, 'Financeiro', '2025-07-01', '2025-07-31', 'PDF'),
(1, 'Operacional', '2025-06-01', '2025-06-30', 'XLSX'),
(1, 'Salarios', '2025-07-01', '2025-07-31', 'PDF');