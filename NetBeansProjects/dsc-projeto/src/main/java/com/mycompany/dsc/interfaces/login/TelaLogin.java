package com.mycompany.dsc.interfaces.login;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class TelaLogin extends JFrame {
    private final JTextField campoUsuario = new JTextField();
    private final JPasswordField campoSenha = new JPasswordField();
    private final JComboBox<String> comboNivelAcesso = new JComboBox<>(new String[]{"Administrador", "Atendente", "Mecânico"});
    private final JButton botaoEntrar   = new JButton("ENTRAR") ;
    private final JLabel linkEsqueciSenha = new JLabel("Esqueci minha senha");
    public TelaLogin() {
        setTitle("Login - Sistema AVJ");
        setSize(400, 500); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        carregarLogo();
        inicializarComponentes();
        configurarEventos();
    }

    private void carregarLogo() {
        try {
            // Caminho para a imagem do logo
            ImageIcon originalIcon = new ImageIcon(
                getClass().getResource("/com/mycompany/dsc/images/AVJ-logo.png")
            );
            
            Image scaledImage = originalIcon.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
            JLabel labelLogo = new JLabel(new ImageIcon(scaledImage));
            labelLogo.setHorizontalAlignment(SwingConstants.CENTER);
            labelLogo.setBounds(100, 20, 200, 100);
            add(labelLogo);
        } catch (Exception e) {
            JLabel labelLogo = new JLabel("AVJ");
            labelLogo.setFont(new Font("Arial", Font.BOLD, 36));
            labelLogo.setForeground(new Color(0, 76, 153));
            labelLogo.setHorizontalAlignment(SwingConstants.CENTER);
            labelLogo.setBounds(0, 30, 400, 50);
            add(labelLogo);
        }
    }

    private void inicializarComponentes() {
        // Componentes da interface
        JLabel labelUsuario = new JLabel("Login (email ou CPF):");
        labelUsuario.setFont(new Font("Arial", Font.BOLD, 14));
        labelUsuario.setBounds(50, 150, 200, 20);
        add(labelUsuario);

        campoUsuario.setBounds(50, 170, 300, 30);
        add(campoUsuario);

        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setFont(new Font("Arial", Font.BOLD, 14));
        labelSenha.setBounds(50, 210, 100, 20);
        add(labelSenha);

        campoSenha.setBounds(50, 230, 300, 30);
        add(campoSenha);

        JLabel labelNivel = new JLabel("Nível de Acesso:");
        labelNivel.setFont(new Font("Arial", Font.BOLD, 14));
        labelNivel.setBounds(50, 270, 200, 20);
        add(labelNivel);

        comboNivelAcesso.setBounds(50, 290, 300, 30);
        add(comboNivelAcesso);

        botaoEntrar.setFont(new Font("Arial", Font.BOLD, 14));
        botaoEntrar.setBackground(new Color(0, 76, 153));
        botaoEntrar.setForeground(Color.WHITE);
        botaoEntrar.setBounds(150, 340, 100, 35);
        add(botaoEntrar);

        linkEsqueciSenha.setFont(new Font("Arial", Font.PLAIN, 12));
        linkEsqueciSenha.setForeground(Color.BLUE.darker());
        linkEsqueciSenha.setBounds(140, 390, 150, 20);
        linkEsqueciSenha.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(linkEsqueciSenha);
    }

    private void configurarEventos() {
        // Ação de login
        botaoEntrar.addActionListener(e -> fazerLogin());
        
        // Ação no link de recuperar senha
        linkEsqueciSenha.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, 
                    "Função de recuperação de senha ainda não implementada.");
            }
        });
    }

    private void fazerLogin() {
        String usuario = campoUsuario.getText();
        String senha = new String(campoSenha.getPassword());
        String nivel = (String) comboNivelAcesso.getSelectedItem();
        
        if (usuario.equals("admin") && senha.equals("123") && nivel.equals("Administrador")) {
            JOptionPane.showMessageDialog(this, "Login bem-sucedido como Administrador!");
            // Aqui você pode abrir a próxima tela
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
