package com.mycompany.dsc.interfaces.login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaLogin extends JFrame {
    private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private JComboBox<String> comboNivelAcesso;
    private JButton botaoEntrar;
    private JLabel linkEsqueciSenha;

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
        botaoEntrar.setBackground(Color.LIGHT_GRAY);
        botaoEntrar.setBounds(150, 310, 100, 35);
        add(botaoEntrar);

        linkEsqueciSenha = new JLabel("Esqueci minha senha");
        linkEsqueciSenha.setFont(new Font("Arial", Font.PLAIN, 12));
        linkEsqueciSenha.setForeground(Color.BLUE.darker());
        linkEsqueciSenha.setBounds(140, 355, 150, 20);
        linkEsqueciSenha.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(linkEsqueciSenha);

        //ação de login
        botaoEntrar.addActionListener(e -> fazerLogin());

        //ação no link de recuperar senha
        linkEsqueciSenha.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Função de recuperação de senha ainda não implementada.");
            }
        });
    }

    private void fazerLogin() {
        String usuario = campoUsuario.getText();
        String senha = new String(campoSenha.getPassword());
        String nivel = (String) comboNivelAcesso.getSelectedItem();
        
        //login temporário para testesá
        if (usuario.equals("admin") && senha.equals("123") && nivel.equals("Administrador")) {
            JOptionPane.showMessageDialog(this, "Login bem-sucedido como Administrador!");
        } else {
            JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaLogin login = new TelaLogin();
            login.setVisible(true);
        });
    }
}
