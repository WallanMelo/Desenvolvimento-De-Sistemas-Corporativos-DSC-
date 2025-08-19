package com.mycompany.telalogin;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class TelaLogin extends JFrame {
    private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private JComboBox<String> comboNivelAcesso;
    private JButton botaoEntrar;
    private JLabel linkEsqueciSenha;

    // Deixei denovo para root, talvez haja mudanças depois....*********************************************************
    private static final String DB_URL = "jdbc:mysql://localhost:3306/aluguel_veiculos?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "12345";

    public TelaLogin() {
        setTitle("Login - Sistema AVJ");
        setSize(400, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel labelAVJ = new JLabel("AVJ");
        labelAVJ.setFont(new Font("Arial", Font.BOLD, 20));
        labelAVJ.setHorizontalAlignment(SwingConstants.CENTER);
        labelAVJ.setBounds(175, 80, 50, 25);
        add(labelAVJ);

        JLabel labelUsuario = new JLabel("Login (email ou CPF):");
        labelUsuario.setFont(new Font("Arial", Font.BOLD, 14));
        labelUsuario.setBounds(50, 120, 200, 20);
        add(labelUsuario);

        campoUsuario = new JTextField();
        campoUsuario.setBounds(50, 140, 300, 30);
        add(campoUsuario);

        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setFont(new Font("Arial", Font.BOLD, 14));
        labelSenha.setBounds(50, 180, 100, 20);
        add(labelSenha);

        campoSenha = new JPasswordField();
        campoSenha.setBounds(50, 200, 300, 30);
        add(campoSenha);

        JLabel labelNivel = new JLabel("Nível de Acesso:");
        labelNivel.setFont(new Font("Arial", Font.BOLD, 14));
        labelNivel.setBounds(50, 240, 200, 20);
        add(labelNivel);

        comboNivelAcesso = new JComboBox<>(new String[]{
            "Administrador", "Atendente", "Mecânico"
        });
        comboNivelAcesso.setBounds(50, 260, 300, 30);
        add(comboNivelAcesso);

        botaoEntrar = new JButton("ENTRAR");
        botaoEntrar.setFont(new Font("Arial", Font.BOLD, 14));
        botaoEntrar.setBackground(new Color(70, 130, 180));
        botaoEntrar.setForeground(Color.WHITE);
        botaoEntrar.setBounds(150, 310, 100, 35);
        botaoEntrar.addActionListener(e -> fazerLogin());
        add(botaoEntrar);

        linkEsqueciSenha = new JLabel("Esqueci minha senha");
        linkEsqueciSenha.setFont(new Font("Arial", Font.PLAIN, 12));
        linkEsqueciSenha.setForeground(Color.WHITE);
        linkEsqueciSenha.setBounds(140, 355, 150, 20);
        linkEsqueciSenha.setCursor(new Cursor(Cursor.HAND_CURSOR));
        linkEsqueciSenha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JOptionPane.showMessageDialog(null,
                        "Entre em contato com o administrador do sistema.",
                        "Recuperação de Senha",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        add(linkEsqueciSenha);
    }

    private void fazerLogin() {
        String usuario = campoUsuario.getText().trim();
        String senha = new String(campoSenha.getPassword());
        String nivel = (String) comboNivelAcesso.getSelectedItem();

        if (usuario.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Preencha todos os campos!",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection conexao = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM usuario WHERE login = ? AND senha = ? AND nivel = ?";

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setString(1, usuario);
                stmt.setString(2, senha);
                stmt.setString(3, nivel);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String nivelBanco = rs.getString("nivel");
                        loginBemSucedido(usuario, nivelBanco);
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Usuário, senha ou nível de acesso incorretos!",
                                "Erro de Login", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao conectar ao banco de dados! (Certifique-se de estar com usuario 'root' e senha 12345).\n" + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void loginBemSucedido(String usuario, String nivel) {
        JOptionPane.showMessageDialog(this,
                "Bem-vindo, " + nivel + "!\nLogin realizado com sucesso.",
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        
        //Telas de cada tipo de usuário
        SwingUtilities.invokeLater(() -> {
            switch (nivel) {
                case "Administrador":
                    new TelaAdministrador(usuario).setVisible(true);
                    break;
                case "Atendente":
                    new TelaAtendente(usuario).setVisible(true);
                    break;
                case "Mecânico":
                    new TelaMecanico(usuario).setVisible(true);
                    break;
                default:
                    JOptionPane.showMessageDialog(this,
                            "Nível de acesso desconhecido: " + nivel,
                            "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
            }
            dispose(); // fecha a tela de login
        });
    }

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver JDBC carregado com sucesso!");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "Driver JDBC não encontrado!\n" + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Look and Feel deve vir antes da criação de janelas
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // iniciandoá a aplicação
        SwingUtilities.invokeLater(() -> {
            TelaLogin login = new TelaLogin();
            login.setVisible(true);
        });
    }
}
