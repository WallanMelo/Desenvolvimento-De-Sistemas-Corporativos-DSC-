package com.mycompany.telalogin;

import javax.swing.*;
import java.awt.*;

public class TelaAtendente extends JFrame {
    private final JPanel painelConteudo;

    public TelaAtendente(String nomeUsuario) {
        setTitle("Painel do Atendente - AVJ");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== TOPO =====
        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBackground(Color.BLACK);
        painelTopo.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));

        JLabel labelLogin = new JLabel("<html><b>Login:</b> Atendente &nbsp;&nbsp; <b>Nome:</b> " + nomeUsuario + "</html>");
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

        // ===== MENU LATERAL =====
        JPanel painelMenu = new JPanel();
        painelMenu.setLayout(new BoxLayout(painelMenu, BoxLayout.Y_AXIS));
        painelMenu.setBackground(Color.BLACK);
        painelMenu.setPreferredSize(new Dimension(250, getHeight()));

        JLabel labelMenuTitulo = new JLabel("MENU", SwingConstants.CENTER);
        labelMenuTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        labelMenuTitulo.setForeground(Color.WHITE);
        labelMenuTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelMenuTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        painelMenu.add(labelMenuTitulo);

        //botões de menu
        String[] opcoes = {
          "Cadastrar Cliente", "Gerenciar Clientes", "Registrar Aluguel", "Registrar Devolução", "Status Da Frota"
        };
        //painelConteudo para trocar telas
        painelConteudo = new JPanel(new BorderLayout());
        painelConteudo.setBackground(new Color(90, 90, 90)); // cinza escuro

        // Tela inicial de boas-vindas
        painelConteudo.add(criarTelaBemVindo(), BorderLayout.CENTER);

        //adiciona botões ao menu e configura ação para trocar conteudo
        for (String opcao : opcoes) {
            JButton botao = new JButton(opcao);
            botao.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
            botao.setFont(new Font("Arial", Font.PLAIN, 14));
            botao.setForeground(Color.WHITE);
            botao.setBackground(new Color(60, 60, 60));
            botao.setFocusPainted(false);
            botao.setHorizontalAlignment(SwingConstants.LEFT);
            botao.setIconTextGap(10);
            botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            botao.addActionListener(e -> {
                switch (opcao) {
                    case "Cadastrar Cliente":
                        trocarConteudo(new TelaCadastrarCliente());
                        break;
                    case "Registrar Aluguel":
                        trocarConteudo(new TelaRegistrarAluguel());
                        break;
                    case "Registrar Devolução":
                        trocarConteudo(new TelaRegistrarDevolucao());
                        break;
                    case "Status Da Frota":
                        trocarConteudo(new TelaStatusFrota("Atendente", nomeUsuario));//Passando o TIpo(Adm - atend - Meca) e o Nome do User 
                        break;
                    case "Gerenciar Clientes":
                        trocarConteudo(new TelaGerenciarClientes());
                        break;
                }
            });
            painelMenu.add(botao);
        }
        add(painelMenu, BorderLayout.WEST);
        add(painelConteudo, BorderLayout.CENTER);
    }

    //método para trocar o conteúdo central
    private void trocarConteudo(JPanel novoPainel){
        painelConteudo.removeAll();
        painelConteudo.add(novoPainel, BorderLayout.CENTER);
        painelConteudo.revalidate();
        painelConteudo.repaint();
    }

    //tela inicial de boas-vindas
    private JPanel criarTelaBemVindo() {
        JPanel painel = new JPanel();
        painel.setBackground(new Color(140, 140, 140));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel("<html><div style='text-align: center; font-size: 10px'>"
                + "Bem-vindo, Atendente!<br>"
                + "Pronto para mais um dia normal na vida de CLT?<br><br>"
                + "Use o menu ao lado para registros e consultas. "
                + "</div></html>", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setForeground(Color.BLACK);
        painel.add(label);

        return painel;
    }

    // Método para criar telas
    private JPanel criarTela(String titulo) {
        JPanel painel = new JPanel();
        painel.setBackground(new Color(140, 140, 140));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel(titulo, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(Color.BLACK);
        painel.setLayout(new BorderLayout());
        painel.add(label, BorderLayout.CENTER);

        return painel;
    }
}
