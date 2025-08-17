package com.mycompany.telalogin;

import javax.swing.*;
import java.awt.*;

public class TelaAdministrador extends JFrame {
    private final JPanel painelConteudo;

    public TelaAdministrador(String nomeUsuario) {
        setTitle("Painel do Administrador - AVJ");
        setSize(1000, 640);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== TOPO =====
        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBackground(Color.BLACK); // fundo preto
        painelTopo.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));

        // Login/Usuário
        JLabel labelLogin = new JLabel("<html><b>Login:</b> Administrador &nbsp;&nbsp; <b>Nome:</b> " + nomeUsuario + "</html>");
        labelLogin.setFont(new Font("Arial", Font.PLAIN, 14));
        labelLogin.setForeground(Color.WHITE);  // texto branco
        painelTopo.add(labelLogin, BorderLayout.WEST);

        // Botão Logout
        JButton botaoLogout = new JButton("Logout >");
        botaoLogout.setFont(new Font("Arial", Font.BOLD, 12));
        botaoLogout.setFocusPainted(false);
        botaoLogout.setBackground(new Color(220, 53, 69)); // vermelho
        botaoLogout.setForeground(Color.WHITE);
        botaoLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoLogout.addActionListener(e -> {
            dispose();
            new TelaLogin().setVisible(true);
        });
        painelTopo.add(botaoLogout, BorderLayout.EAST);

        add(painelTopo, BorderLayout.NORTH);

        // ===== MENU LATERAL =====
        JPanel painelMenu = new JPanel();
        painelMenu.setLayout(new BoxLayout(painelMenu, BoxLayout.Y_AXIS));
        painelMenu.setBackground(Color.BLACK); // fundo preto escuro
        painelMenu.setPreferredSize(new Dimension(200, getHeight()));

        JLabel labelMenuTitulo = new JLabel("MENU", SwingConstants.CENTER);
        labelMenuTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        labelMenuTitulo.setForeground(Color.WHITE); // texto branco
        labelMenuTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelMenuTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        painelMenu.add(labelMenuTitulo);

        String[] opcoes = {
            "Gestão", "Relatórios"
        };

        for (String opcao : opcoes) {
            JButton botao = new JButton(opcao);
            botao.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            botao.setFont(new Font("Arial", Font.PLAIN, 14));
            botao.setAlignmentX(Component.CENTER_ALIGNMENT);
            botao.setFocusPainted(false);
            botao.setBackground(new Color(60, 60, 60)); // botão cinza escuro
            botao.setForeground(Color.WHITE); // texto branco
            botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
            // Aqui adiciona o listener para abrir a janela correta
            botao.addActionListener(e -> {
                if (opcao.equals("Gestão")) {
                    new TelaGestao().setVisible(true);
                } else if (opcao.equals("Relatórios")) {
                    new TelaRelatorios().setVisible(true);
                }
            });

            painelMenu.add(botao);
        }

        add(painelMenu, BorderLayout.WEST);

        // ===== CONTEÚDO CENTRAL =====
        painelConteudo = new JPanel(new BorderLayout());
        painelConteudo.setBackground(Color.BLACK); // fundo preto

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(500, 200));
        card.setBackground(new Color(60, 63, 65));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel texto = new JLabel("<html><div style='text-align: center;'>"
                + "<h3>Bem-vindo, Administrador!</h3>"
                + "<p>Gerencie sua empresa com eficiência e segurança.</p>"
                + "<p>Use o menu ao lado para acessar todas as funções.</p>"
                + "</div></html>", SwingConstants.CENTER);
        texto.setFont(new Font("Arial", Font.PLAIN, 16));
        texto.setForeground(Color.WHITE);
        texto.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(texto);
        painelConteudo.add(card, BorderLayout.CENTER);

        add(painelConteudo, BorderLayout.CENTER);
    }
}

// TelaGestao.java
class TelaGestao extends JFrame {
    public TelaGestao() {
        setTitle("Tela de Gestão");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel painel = new JPanel();
        painel.setBackground(Color.DARK_GRAY);
        JLabel label = new JLabel("Aqui é a Tela de Gestão!");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        painel.add(label);
        add(painel);
    }
}

// TelaRelatorios.java
class TelaRelatorios extends JFrame {
    public TelaRelatorios() {
        setTitle("Tela de Relatórios");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel painel = new JPanel();
        painel.setBackground(Color.DARK_GRAY);
        JLabel label = new JLabel("Aqui é a Tela de Relatórios!");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        painel.add(label);
        add(painel);
    }
}
