package com.mycompany.telalogin;

import javax.swing.*;
import java.awt.*;
import java.awt.Cursor;

public class TelaAdministrador extends JFrame {

    private final JPanel painelConteudo;
    private final JPanel painelMenuContainer;

    public TelaAdministrador(String nomeUsuario) {
        setTitle("Painel do Administrador - AVJ");
        setSize(1000, 640);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== TOPO =====
        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBackground(Color.BLACK);
        painelTopo.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));

        JLabel labelLogin = new JLabel("<html><b>Login:</b> Administrador &nbsp;&nbsp; <b>Nome:</b> " + nomeUsuario + "</html>");
        labelLogin.setFont(new Font("Arial", Font.PLAIN, 14));
        labelLogin.setForeground(Color.WHITE);
        painelTopo.add(labelLogin, BorderLayout.WEST);

        JButton botaoLogout = new JButton("Logout >");
        botaoLogout.setFont(new Font("Arial", Font.BOLD, 12));
        botaoLogout.setFocusPainted(false);
        botaoLogout.setBackground(new Color(220, 53, 69));
        botaoLogout.setForeground(Color.WHITE);
        botaoLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoLogout.addActionListener(e -> {
            dispose();
            new TelaLogin().setVisible(true);
        });
        painelTopo.add(botaoLogout, BorderLayout.EAST);

        add(painelTopo, BorderLayout.NORTH);

        // ===== CONTAINER DO MENU LATERAL =====
        painelMenuContainer = new JPanel(new BorderLayout());
        painelMenuContainer.setBackground(Color.BLACK);
        painelMenuContainer.setPreferredSize(new Dimension(200, getHeight()));
        add(painelMenuContainer, BorderLayout.WEST);

        // ===== CONTEÚDO CENTRAL =====
        painelConteudo = new JPanel(new BorderLayout());
        painelConteudo.setBackground(new Color(90, 90, 90));
        add(painelConteudo, BorderLayout.CENTER);

        trocarMenu(criarPainelMenuPrincipal());
        trocarConteudo(criarTelaBemVindo());
    }

    private void trocarMenu(JPanel novoPainel) {
        painelMenuContainer.removeAll();
        painelMenuContainer.add(novoPainel, BorderLayout.CENTER);
        painelMenuContainer.revalidate();
        painelMenuContainer.repaint();
    }

    private void trocarConteudo(JPanel novoPainel) {
        painelConteudo.removeAll();
        painelConteudo.add(novoPainel, BorderLayout.CENTER);
        painelConteudo.revalidate();
        painelConteudo.repaint();
    }

    ////MENU PRINCIPAL onde FICA as OPÇÕES de GESTÃO e REALTORIOS
    private JPanel criarPainelMenuPrincipal() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(Color.BLACK);
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel labelMenuTitulo = new JLabel("MENU", SwingConstants.CENTER);
        labelMenuTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        labelMenuTitulo.setForeground(Color.WHITE);
        labelMenuTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelMenuTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        painel.add(labelMenuTitulo);

        String[] opcoes = {"Gestão", "Relatórios"};
        for (String opcao : opcoes) {
            JButton botao = new JButton(opcao);
            botao.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            botao.setFont(new Font("Arial", Font.PLAIN, 14));
            botao.setAlignmentX(Component.CENTER_ALIGNMENT);
            botao.setFocusPainted(false);
            botao.setBackground(new Color(60, 60, 60));
            botao.setForeground(Color.WHITE);
            botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            botao.addActionListener(e -> {
                if (opcao.equals("Gestão")) {
                    trocarMenu(criarPainelMenuPrincipal()); 
                    trocarConteudo(new TelaGestao());
                } else if (opcao.equals("Relatórios")) {
                    trocarMenu(criarPainelMenuRelatorios());
                    trocarConteudo(new TelaRelatorios());
                }
            });
            painel.add(botao);
        }
        return painel;
    }

    //funct p CRIAR um PANEL da funcionalidad de RELATORIOS
    private JPanel criarPainelMenuRelatorios() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(new Color(60, 60, 60)); // Cinza escuro
        
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));// borda p buttons n colar nos cantos da tela

        JLabel labelMenuTitulo = new JLabel("RELATÓRIOS", SwingConstants.CENTER);
        labelMenuTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        labelMenuTitulo.setForeground(Color.WHITE);
        labelMenuTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelMenuTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        painel.add(labelMenuTitulo);

        String[] opcoes = {"Financeiro", "Operacional", "Salários"};
        for (String opcao : opcoes) {
            JButton botao = new JButton("<html><div style='text-align: left;'>" + opcao + " ></div></html>");
            botao.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            botao.setFont(new Font("Arial", Font.PLAIN, 12));
            botao.setAlignmentX(Component.CENTER_ALIGNMENT);
            botao.setFocusPainted(false);
            botao.setBackground(new Color(90, 90, 90));
            botao.setForeground(Color.WHITE);
            botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            botao.setHorizontalAlignment(SwingConstants.LEFT);
            botao.addActionListener(e -> {
                trocarConteudo(new TelaRelatorios(opcao));
            });
            painel.add(botao);
        }
        
        painel.add(Box.createVerticalGlue());

        JButton botaoVoltar = new JButton("Voltar");
        
        botaoVoltar.setMaximumSize(new Dimension(100, 30));
        botaoVoltar.setPreferredSize(new Dimension(100, 30));
        botaoVoltar.setMinimumSize(new Dimension(100, 30));
        botaoVoltar.setFont(new Font("Arial", Font.BOLD, 12));
        botaoVoltar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoVoltar.setFocusPainted(false);
        botaoVoltar.setBackground(new Color(150, 150, 150));
        botaoVoltar.setForeground(Color.WHITE);
        botaoVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoVoltar.addActionListener(e -> {
            trocarMenu(criarPainelMenuPrincipal());
            trocarConteudo(criarTelaBemVindo());
        });
        painel.add(Box.createVerticalStrut(10)); 
        painel.add(botaoVoltar);
        painel.add(Box.createVerticalStrut(10)); 

        return painel;
    }
    
    //===== TELA de BOAS VINDAS p ADM
    private JPanel criarTelaBemVindo() {
        JPanel painel = new JPanel();
        painel.setBackground(new Color(140, 140, 140));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel texto = new JLabel("<html><div style='text-align: center;'>"
                + "<h3>Bem-vindo, Administrador!</h3>"
                + "<p>Gerencie sua empresa com eficiência e segurança.</p>"
                + "<p>Use o menu ao lado para acessar todas as funções.</p>"
                + "</div></html>", SwingConstants.CENTER);
        texto.setFont(new Font("Arial", Font.PLAIN, 16));
        texto.setForeground(Color.WHITE);
        texto.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        painel.add(texto);
        return painel;
    }
}