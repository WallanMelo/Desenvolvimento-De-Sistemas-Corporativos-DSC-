# Especificação de Requisitos Funcionais - Sistema de Gestão de Aluguel de Veículos AVJ

## Requisito Inicial

#### RF01 - O sistema deve solicitar os dados de login de cada funcionário, quando o mesmo for entrar no sistema.

## Requisitos para Tela de Login

#### RF02 - Autenticação com Email ou CPF e Senha
O usuário deve poder se autenticar no sistema utilizando seu CPF com a sua senha, ou seu email também com a sua senha.

#### RF03 - Identificação de Nível de Acesso do Usuário
O sistema deve identificar qual é o nível de acesso do usuário, com base na validação dos dados fornecidos previamente pelo mesmo na tela de login.

#### RF04 - Redirecionamento Automático para a sua Tela de Usuário
Após o login, o sistema deve redirecionar o usuário para sua respectiva tela (Administrador ou Atendente ou Mecânico), na qual o usuário terá permissões com base no seu nível de acesso.

## Requisitos para a interface do Administrador

#### RF05 - Ter todas as permissões que os demais funcionários têm
O administrador deve ter os mesmos privilégios que os outros níveis de acesso (Atendente, Mecânico) têm, para garantir total controle e supervisão sobre o sistema. Se algum funcionário fizer algo errado no sistema, o administrador pode corrigir.

#### RF06 - O sistema deve permitir que o administrador cadastre novos funcionários.

#### RF07 - O sistema deve permitir que o administrador edite os dados dos funcionários.

#### RF08 - O sistema deve permitir que o administrador exclua funcionários.

#### RF09 - O sistema deve permitir que o administrador visualize os salários de todos os funcionários.

#### RF10 - O sistema deve permitir que o administrador edite a função do funcionário (Administrador, Atendente, Mecânico).

#### RF11 - O sistema deve permitir que o administrador edite os valores e taxas para empréstimo de cada veículo.

#### RF12 - O sistema deve permitir que o administrador visualize o relatório geral mensal/anual da empresa.

## Requisitos para a interface do Atendente

#### RF13 - O sistema deve permitir que o atendente cadastre novos clientes.

#### RF14 - O sistema deve permitir que o atendente exclua clientes.

#### RF15 - O sistema deve permitir que o atendente registre aluguéis de veículos.

#### RF16 - O sistema deve permitir que o atendente registre devolução de veículos.

#### RF17 - O sistema deve permitir que o atendente edite o status dos veículos.
O atendente deve poder editar o status atual de um determinado veículo, definindo se o mesmo está atualmente alugado, em manutenção ou disponível para alugar.

#### RF18 - O sistema deve permitir que o atendente visualize o status dos veículos.
O atendente deve poder visualizar o status de determinado veículo, verificando se o mesmo está atualmente alugado, em manutenção ou disponível para alugar.

## Requisitos para a interface do Mecânico 

#### RF19 - A interface do mecânico deve ter interface simplificada e de fácil uso, visando usabilidade por profissionais não familiarizados com tecnologia.
Pelo fato do mecânico ser um profissional com expertise em mecânica automotiva, o mesmo talvez possa sofrer dificuldades ao lidar com um sistema computacional complexo, por isso, a sua interface deve ser o mais intuitiva possível, se comparada as demais.

#### RF20 - O sistema deve permitir que o mecânico adicione uma nota ao veículo em manutenção.
O mecânico deve poder adicionar uma nota ao veículo que está em manutenção, nota que contém informações como tipo de defeito no veículo, prazo de entrega e valor da manutenção.

#### RF21 - O sistema deve permitir que o mecânico remova um veículo da lista de veículos em manutenção.

## Requisitos para o Cadastro de Funcionários

#### RF22 - Ao cadastrar um novo funcionário no sistema, deve ser solicitado:
* Nome completo; 
* CPF;
* Email;
* Número de telefone;
* Data de nascimento;
* Nível de acesso;
* Salário.

#### RF23 - O sistema deve gerar um ID único, exclusivo para cada funcionário cadastrado na empresa.

#### RF24 - O sistema deve permitir que o administrador edite quaisquer dados de qualquer funcionário, com a exceção do ID.

#### RF25 - O sistema deve permitir que o administrador visualize quaisquer dados de qualquer funcionário.

## Requisitos para o Cadastro de Veículos

#### RF26 - Ao cadastrar um novo veículo no sistema, deve ser solicitado:
* Modelo;
* Fabricante;
* Tipo;
* Ano; 
* Placa; 
* Estado de conservação;
* Cor; 
* Características adicionais.

#### RF27 - O sistema deve gerar um ID único para cada veículo cadastrado no sistema.

#### RF28 - O sistema deve permitir que o administrador edite quaisquer dados de qualquer veículo, com a exceção do ID.

#### RF29 - O sistema deve permitir que o administrador visualize quaisquer dados de qualquer veículo.

## Requisitos para o Cadastro de Clientes

#### RF30 - Ao cadastrar um novo cliente no sistema, deve ser solicitado:
* Nome completo; 
* CPF;
* CNH;
* Email;
* Número de telefone;
* Data de nascimento;
* Endereço.

#### RF31 - O sistema deve gerar um ID único para cada cliente cadastrado no sistema.

#### RF32 - O sistema deve permitir que o atendente edite quaisquer dados de qualquer cliente, com a exceção do ID.

#### RF33 - O sistema deve permitir que o atendente visualize quaisquer dados de qualquer cliente.

## Requisitos para o Aluguel de Veículos

#### RF34 - O sistema só deverá permitir ao atendente registrar o aluguel de veículos que estejam com status de disponível para aluguel.

#### RF35 - Ao registrar o aluguel de um veículo, o sistema deve solicitar:
* Nome completo do cliente; 
* CNH;
* CPF;
* Placa do veículo a ser alugado;
* Detalhes sobre o aluguel;
* Prazo estipulado para devolução do veículo.

#### RF36 - O sistema deve gerar um código único que identifique aquele aluguel.

#### RF37 - O sistema deve calcular o valor total do aluguel, com base no valor do veículo escolhido e tempo estipulado de aluguel.

#### RF38 - O sistema deve calcular uma multa, caso o cliente devolva o veículo com atraso no tempo estipulado para entrega, e adicioná-la ao valor total final do aluguel.

#### RF39 - O sistema deverá permitir ao atendente registrar o veículo como devolvido após o cliente devolver o veículo.

## Requisitos para o Manutenção de Veículos

#### RF40 - O sistema deve permitir ao atendente marcar veículos como “em manutenção”.

#### RF41 - O sistema deve permitir ao mecânico adicionar qual é o problema de determinado veículo.

#### RF42 - O sistema deve permitir ao mecânico adicionar o prazo para conserto do veículo, junto ao valor total de conserto.

## Requisitos para Relatórios e Buscas

#### RF43 - O sistema deve gerar um relatório mensal e outro anual de faturamento da empresa
Nesse relatório deve conter informações como, quantidade de veículos alugados, lucro obtido com alugueis, despesas de manutenção de veículos, salários de funcionários e demais informações que podem ser adicionadas durante o desenvolvimento do sistema.

#### RF44 - O sistema deve gerar um relatório atual de veículos alugados, disponíveis para aluguel e em manutenção.

#### RF45 - O sistema deve permitir que o atendente busque veículos por placa, modelo ou tipo.

#### RF46 - O sistema deve permitir que o atendente busque clientes por CPF, CNH ou nome.

#### RF47 - O sistema deve permitir que o administrador busque funcionários por CPF, função ou nome.