package com.mycompany.telalogin;

import com.mycompany.telalogin.dao.MecanicoDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaListarSolicitacoesPecas extends JPanel {

    private JComboBox<String> statusFiltro;
    private JTable tabelaSolicitacoes;
    private DefaultTableModel tableModel;
    private String tipoUsuario;

    public TelaListarSolicitacoesPecas(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
        // Configurações do painel principal
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(new Color(175, 175, 175)); 

        //===== FILTRO ================================================================
        JPanel painelFiltro = new JPanel();
        painelFiltro.setBackground(new Color(175, 175, 175));
        painelFiltro.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        
        JLabel labelFiltro = new JLabel("Filtrar por Status:");
        labelFiltro.setFont(new Font("Arial", Font.BOLD, 14));
        labelFiltro.setForeground(Color.BLACK);
        painelFiltro.add(labelFiltro);

        String[] statusOpcoes = {"Todos", "Em_Analise", "Aprovada", "Recusada"};
        statusFiltro = new JComboBox<>(statusOpcoes);
        statusFiltro.setPreferredSize(new Dimension(150, 30));
        statusFiltro.setBackground(Color.WHITE);
        painelFiltro.add(statusFiltro);

        add(painelFiltro, BorderLayout.NORTH);

        //====== TABLE De SDOLCIITAÇÕES ===========================================
        String[] colunas = {"ID", "Peça", "Quantidade", "Veículo (Placa)", "Data", "Status", "Usuário", "Justificativa"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaSolicitacoes = new JTable(tableModel);
        tabelaSolicitacoes.setFillsViewportHeight(true);
        tabelaSolicitacoes.setRowHeight(25);
        tabelaSolicitacoes.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 12));
        tabelaSolicitacoes.getTableHeader().setBackground(new Color(60, 60, 60));
        tabelaSolicitacoes.getTableHeader().setForeground(Color.BLACK);
        tabelaSolicitacoes.setFont(new Font("Arial", Font.PLAIN, 12));

        tabelaSolicitacoes.getColumnModel().getColumn(7).setPreferredWidth(250);

        JScrollPane scrollPane = new JScrollPane(tabelaSolicitacoes);
        add(scrollPane, BorderLayout.CENTER);

        // se o usuer logado for adm add buttons p alterar o staus da solicitação da peça 
        if ("Administrador".equalsIgnoreCase(this.tipoUsuario)) {
            JPanel painelAcoes = new JPanel();
            painelAcoes.setBackground(new Color(175, 175, 175));
            painelAcoes.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));

            JButton botaoAprovar = new JButton("Aprovar");
            JButton botaoRecusar = new JButton("Recusar");

            configurarBotao(botaoAprovar, new Color(40, 167, 69)); 
            configurarBotao(botaoRecusar, new Color(220, 53, 69));

            painelAcoes.add(botaoAprovar);
            painelAcoes.add(botaoRecusar);
            
            add(painelAcoes, BorderLayout.SOUTH);

            //event listenber button APROVAR
            botaoAprovar.addActionListener(e -> {
                int selectedRow = tabelaSolicitacoes.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(this, "Selecione uma solicitação na tabela.", "Atenção", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                String statusAtual = (String) tabelaSolicitacoes.getValueAt(selectedRow, 5);
                if (!statusAtual.equals("Em_Analise") && !statusAtual.equals("Recusada")) {
                    JOptionPane.showMessageDialog(this, "Apenas solicitações 'Em_Analise' e 'Recusada' podem ser aprovadas.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                int solicitacaoId = (int) tabelaSolicitacoes.getValueAt(selectedRow, 0);
                if (MecanicoDAO.atualizarStatusSolicitacaoPeca(solicitacaoId, "Aprovada")) {
                    JOptionPane.showMessageDialog(this, "Solicitação aprovada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    carregarDadosTabela((String) statusFiltro.getSelectedItem());
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao aprovar solicitação. Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            });
            
            //event listenber button RECUSAR
            botaoRecusar.addActionListener(e -> {
                int selectedRow = tabelaSolicitacoes.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(this, "Selecione uma solicitação na tabela.", "Atenção", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                String statusAtual = (String) tabelaSolicitacoes.getValueAt(selectedRow, 5);
                if (!statusAtual.equals("Em_Analise")) {
                    JOptionPane.showMessageDialog(this, "Apenas solicitações 'Em_Analise' podem ser recusadas.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int solicitacaoId = (int) tabelaSolicitacoes.getValueAt(selectedRow, 0);
                if (MecanicoDAO.atualizarStatusSolicitacaoPeca(solicitacaoId, "Recusada")) {
                    JOptionPane.showMessageDialog(this, "Solicitação recusada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    carregarDadosTabela((String) statusFiltro.getSelectedItem());
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao recusar solicitação. Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        statusFiltro.addActionListener(e -> {
            String statusSelecionado = (String) statusFiltro.getSelectedItem();
            carregarDadosTabela(statusSelecionado);
        });
        
        //faz o caregamentop de TODOS os DADOS
        carregarDadosTabela("Todos");
    }

    //Estilizando BUTTOm
    private void configurarBotao(JButton button, Color corFundo) {
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setForeground(Color.BLACK);
        button.setBackground(corFundo);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(120, 35));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    //CAREGA os DADOS de ACORDO C/ O STATUS SELECTED
    private void carregarDadosTabela(String status) {
        tableModel.setRowCount(0);

        //recebe os dados do DATA acces Obj
        List<Object[]> solicitacoes = MecanicoDAO.listarSolicitacoesPecasPorStatus(status);

        //alimenta a table com os nvs dados
        if (solicitacoes.isEmpty()) {//se n tiver nada
            tableModel.addRow(new Object[]{"Nenhum resultado encontrado.", "", "", "", "", "", "", ""});
        } else {
            for (Object[] solicitacao : solicitacoes) {
                tableModel.addRow(solicitacao);
            }
        }
    }
}

