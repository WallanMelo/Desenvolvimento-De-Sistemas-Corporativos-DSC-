package com.mycompany.telalogin;
import com.mycompany.telalogin.dao.MecanicoDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TelaMecanico extends JFrame {
    private final JPanel painelConteudo;
    public TelaMecanico(String nomeUsuario) {
        setTitle("Painel do Mecânico - AVJ");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //add(new JLabel("Tela do Mecânico (em construção)", SwingConstants.CENTER), BorderLayout.CENTER);
        setLayout(new BorderLayout());

        // ===== TOPO =====
        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBackground(Color.BLACK);
        painelTopo.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));

        JLabel labelLogin = new JLabel("<html><b>Login:</b> Mecânico &nbsp;&nbsp; <b>Nome:</b> " + nomeUsuario + "</html>");
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

        // Botões do menu
        String[] opcoes = {
            "Manutenções Pendentes", "Histórico De Manutenções", "Solicitar Peças", "Status Da Frota"
        };

        // Criando painelConteudo para trocar telas
        painelConteudo = new JPanel(new BorderLayout());
        painelConteudo.setBackground(new Color(90, 90, 90)); // cinza escuro

        // Tela inicial de boas-vindas
        painelConteudo.add(criarTelaBemVindo(), BorderLayout.CENTER);

        // Adiciona botões ao menu e configura ação para trocar conteúdo
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
                    case "Manutenções Pendentes":
                        trocarConteudo(new TelaManutencoesPendentes());
                        break;
                    case "Histórico De Manutenções":
                        trocarConteudo(new TelaHistoricoManutencoes());
                        break;
                    case "Solicitar Peças":
                        trocarConteudo(new TelaSolicitarPecas());
                        break;
                    case "Status Da Frota":
                        trocarConteudo(new TelaStatusFrota());
                        break;
                }
            });

            painelMenu.add(botao);
        }

        add(painelMenu, BorderLayout.WEST);
        add(painelConteudo, BorderLayout.CENTER);
    }

    // Método para trocar o conteúdo central
    private void trocarConteudo(JPanel novoPainel) {
        painelConteudo.removeAll();
        painelConteudo.add(novoPainel, BorderLayout.CENTER);
        painelConteudo.revalidate();
        painelConteudo.repaint();
    }

    // Tela inicial de boas-vindas
    private JPanel criarTelaBemVindo() {
        JPanel painel = new JPanel();
        painel.setBackground(new Color(140, 140, 140));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        int quantidadeManutencoesPendentes = MecanicoDAO.getQuantidadeManutencoesPendentes();
        int solicitacoesEmAnalise = MecanicoDAO.getSolicitacoesEmAnalise();
        JLabel label = new JLabel("<html><div style='text-align: center; font-size: 10px'>"
                + "Bem-vindo, Mecânico!<br>"
                + "Sua dedicação mantém nossa frota em perfeito funcionamento.<br><br>"
                + "Hoje, voçê possui " + quantidadeManutencoesPendentes + " manutenções pendendtes e " + solicitacoesEmAnalise +  " solicitações em análise. "
                + "</div></html>", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setForeground(Color.BLACK);
        painel.add(label);

        return painel;
    }
}
