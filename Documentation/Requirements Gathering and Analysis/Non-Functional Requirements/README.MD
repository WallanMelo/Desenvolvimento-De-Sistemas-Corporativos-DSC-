# Especificação de Requisitos Não-Funcionais - Sistema de Gestão de Aluguel de Veículos AVJ

## Requisitos de Segurança

#### RNF01 - O sistema deve armazenar senhas de forma criptografada.

#### RNF02 - O sistema só deve permitir que o usuário acesse dados internos quando seu login for validado.

#### RNF03 - O sistema deve permitir que apenas usuários autenticados acessem as funcionalidades internas.

#### RNF04 - Cada usuário deve acessar apenas as interfaces permitidos com base em seu nível de acesso (Administrador, Atendente ou Mecânico).

## Requisitos de Desempenho

#### RNF05 - O carregamento de relatórios não deve exceder 10 segundos para até 1.000 registros.

#### RNF06 - Cada operação realizada por um usuário deve ter um tempo de resposta inferior a 3 segundos.

#### RNF07 - O sistema deve ser compatível com as configurações de hardware da empresa AVJ.

## Plataforma

#### RNF08 - O sistema deve ser desenvolvido em Java e funcionar em qualquer sistema operacional que suporte a JVM.

## Evolução

#### RNF09 - O sistema deve permitir adição de novas funcionalidades com impacto mínimo sobre os módulos existentes.

#### RNF10 - O código deve estar documentado com comentários nas partes mais complexas, visando facilitar futuras manutenções.

## Disponibilidade

#### RNF11 - O sistema deve estar disponível para uso durante o horário comercial padrão (8h às 18h), com 99% de tempo operacional.

#### RNF12 - Em caso de falhas, o sistema deve informar o usuário com uma mensagem amigável e registrar a falha em log para análise futura.

