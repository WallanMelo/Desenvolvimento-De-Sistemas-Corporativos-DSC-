# Especificação de Requisitos Adicionais - Sistema de Gestão de Aluguel de Veículos AVJ

## Requisitos de Implantação

#### RA01 - Instalação Local
O sistema deve ser instalado localmente nos computadores da empresa AVJ, com suporte a ambientes Windows e Linux.

#### RA02 - Dependências de Software
O sistema requer a instalação da Máquina Virtual Java (JVM) versão 11 ou superior e um sistema de banco de dados relacional, como MySQL.

#### RA03 - Base de Dados Local ou em Servidor Interno
O banco de dados deverá estar hospedado em um servidor local da empresa, com acesso controlado exclusivamente pela rede interna.

## Requisitos de Desenvolvimento

#### RA04 - Linguagem de Programação
O sistema será desenvolvido na linguagem Java, utilizando a IDE NetBeans.

#### RA05 - Interface Gráfica
A interface do sistema deve ser construída com bibliotecas visuais compatíveis com Java, como Java Swing.

## Requisitos de Testes

#### RA06 – Testes de Validação de Níveis de Acesso
O sistema deve ser testado para garantir que cada tipo de usuário (Administrador, Atendente, Mecânico) consiga acessar apenas as funcionalidades correspondentes ao seu nível de permissão.

#### RA07 – Testes de Consistência de Dados
O sistema deve ser testado para evitar inconsistências entre status de veículos e operações (ex: não permitir alugar um veículo em manutenção).