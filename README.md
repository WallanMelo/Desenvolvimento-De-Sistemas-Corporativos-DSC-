# Corporate Systems Development (CSD)
Work(s) from the CSD Discipline offered by the Bachelor's Degree in Information Systems.

### Objectives of the Subject
A disciplina de Desenvolvimento de Sistemas Corporativos (DSC) visa capacitar os alunos na análise, planejamento, desenvolvimento e manutenção de sistemas que atendam às necessidades de empresas e organizações. Durante a disciplina, os alunos são organizados em duplas para desenvolver um sistema corporativo completo. Este projeto envolve a criação do backend, frontend, documentação técnica e outros requisitos funcionais. O sistema é avaliado como o único trabalho prático do curso e é essencial para a nota final.
#### Taught by The Teacher: Paulo Veloso Santos Júnior 

## Description of the Developed System

## Features

## Tools Used
 ![Linux](https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=linux&logoColor=black)
 ![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)
 ![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white) 
 ![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)
 ![NetBeans IDE](https://img.shields.io/badge/NetBeansIDE-1B6AC6.svg?style=for-the-badge&logo=apache-netbeans-ide&logoColor=white)
 ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)

## Developers

| Developer                | GitHub | 
|--------------------------|--------|
| 1 - Clebson Santos       | [![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/ClebTech)|
| 2 - Wallan De Melo Lima  | [![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/WallanMelo)|


# Como usar esse sistema?
## Recomenda-se que o use em tela cheia, para melhor experiência

## 1. Certifique-se de ter o MySQL e MySQL Workbench instalados na sua máquina, além destes, instale também o Apache NetBeans, assim como o JDK (Java Development Kit).
<img width="628" height="405" alt="Image" src="https://github.com/user-attachments/assets/4ad2386e-ebd3-4fb9-b4ba-d68712c9d0d9" />

## 2. Clone esse repositório na sua máquina local:
git clone https://github.com/WallanMelo/Desenvolvimento-De-Sistemas-Corporativos-DSC-

## 3. Abra seu MySQL Workbench e crie uma nova conexão:
### 3.1 Coloque o nome dessa conexão de "aluguel_veiculos".
### 3.2 Preencha o seguintes dados: 
- hostname: 127.0.0.1
- port: 3306
- username: root
- password: 12345
<img width="1445" height="884" alt="Image" src="https://github.com/user-attachments/assets/1af9cbbb-16d9-4f23-a856-296f1838b8a1" />

## 4. Dentro da pasta do repositório que você acabou de clonar, abra a base de dados "aluguel_veiculos.sql" na conexão que você acabou de criar.
<img width="977" height="544" alt="Image" src="https://github.com/user-attachments/assets/e002dbae-88d2-4fa0-b094-ac85aa36a0f9" />

## 5. Execute essa base de dados (Ctrl + Shift + Enter), pois é ela contém dados base para você navegar no sistema.
<img width="1528" height="880" alt="Image" src="https://github.com/user-attachments/assets/087af9c0-c482-430e-89a2-3eba3d2deab6" />

## 6. Volte para a pasta que você clonou, abra a pasta "NetBeansProjects".
Você verá uma tela parecida com essa, perceba que há uma pasta chamada "TelaLogin"
<img width="1123" height="452" alt="Image" src="https://github.com/user-attachments/assets/f0387e0d-6002-41e2-9df5-e440f9c2bb7d" />

## 7. Abra essa pasta "TelaLogin" no Apache NetBeans.
Isto é, clique nela com o botão direito do mouse e escolha a opção de abrir com NetBeans.
Você verá algo parecido com isso (ou seja, você acabou de abrir a pasta TelaLogin no NetBeans):
<img width="1086" height="599" alt="Image" src="https://github.com/user-attachments/assets/c3cc0144-52c8-428b-bfb9-d62ddee33efe" />

## 8. Clique com o botão direito do mouse sobre a pasta "TelaLogin" e clique na opção "Clean and Build".
Ou o equivalente, se o seu NetBeans estiver em português.
<img width="1269" height="983" alt="Image" src="https://github.com/user-attachments/assets/7178b75d-473e-494d-82bc-0d8f182ccf7c" />

## 9. Logo em seguinda, clique denovo na pasta "TelaLogin", mas desta vez você vai clicar em "Open in Terminal"
Ou o equivalente, se o seu NetBeans estiver em português.
<img width="1269" height="983" alt="Image" src="https://github.com/user-attachments/assets/33c0200c-75e4-4b93-8095-70ae134e7254" />

## 10. No terminal que você acabou de abrir, digite o seguinte comando e depois dê Enter:
mvn clean compile exec:java
<img width="1345" height="502" alt="Image" src="https://github.com/user-attachments/assets/2d606f9d-6ecd-4e9f-bd0e-69ea8963f0c0" />

## 11. PRONTO! Agora você está na tela de login do sistema.
Lembra daquela base de dados que você executou no MySQL Workbench? pois é, nela contém alguns usuários pré-definidos que você pode usar para se conectar ao sistema.
- Administrador (login: admin / senha: 123)
- Atendente (login: cleb / senha: 1234)
- Mecânico (login: wallan / senha: 4321)

<img width="1470" height="898" alt="Image" src="https://github.com/user-attachments/assets/7778847e-35bb-46ea-908b-c422f80dd142" />

