/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.dsc;

/**
 *
 * @author clebson
 */
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.mycompany.dsc.interfaces.login.TelaLogin;

public class Main extends JFrame {

    // Componentes da interface
    private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private JComboBox<String> comboNivelAcesso;
    private JButton botaoEntrar;

    public Main() {
        setTitle("Login - Sistema AVJ");
        setSize(400, 300);
        setLocationRelativeTo(null); // Centraliza a janela
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null); // Layout absoluto

        // Rótulos
        JLabel labelTitulo = new JLabel("AVJ - Sistema de Aluguel de Veículos");
        labelTitulo.setBounds(60, 20, 300, 20);
        add(labelTitulo);

        JLabel labelUsuario = new JLabel("Usuário (CPF ou Email):");
        labelUsuario.setBounds(50, 60, 200, 20);
        add(labelUsuario);

        campoUsuario = new JTextField();
        campoUsuario.setBounds(50, 80, 300, 25);
        add(campoUsuario);

        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setBounds(50, 110, 200, 20);
        add(labelSenha);

        campoSenha = new JPasswordField();
        campoSenha.setBounds(50, 130, 300, 25);
        add(campoSenha);

        JLabel labelNivel = new JLabel("Nível de Acesso:");
        labelNivel.setBounds(50, 160, 200, 20);
        add(labelNivel);

        comboNivelAcesso = new JComboBox<>(new String[] {
            "Administrador Geral", "Atendente", "Mecânico"
        });
        comboNivelAcesso.setBounds(50, 180, 300, 25);
        add(comboNivelAcesso);

        botaoEntrar = new JButton("Entrar");
        botaoEntrar.setBounds(150, 220, 100, 30);
        add(botaoEntrar);

        // Ação ao clicar no botão Entrar
        botaoEntrar.addActionListener(e -> fazerLogin());
    }

    private void fazerLogin() {
        String usuario = campoUsuario.getText();
        String senha = new String(campoSenha.getPassword());
        String nivel = (String) comboNivelAcesso.getSelectedItem();

        // Simulação de autenticação (substituir por validação real com banco de dados)
        if (usuario.equals("admin") && senha.equals("123") && nivel.equals("Administrador Geral")) {
            JOptionPane.showMessageDialog(this, "Login bem-sucedido como Administrador!");
            // Aqui você pode abrir o menu principal do sistema
        } else {
            JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos.");
        }
    }

    // Método main para testar a tela
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaLogin login = new TelaLogin();
            login.setVisible(true);
        });
    }
}

