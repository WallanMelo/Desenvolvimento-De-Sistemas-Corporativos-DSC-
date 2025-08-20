package com.mycompany.telalogin;

import com.mycompany.telalogin.dao.MecanicoDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaHistoricoManutencoes extends JPanel {

    private JTextField campoBusca;
    private JTable tabelaHistorico;
    private DefaultTableModel tabelaModelo;
    private JButton botaoBuscar;

    public TelaHistoricoManutencoes() {
        setLayout(new GridBagLayout());
        setBackground(new Color(230, 230, 230));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        //===== TITLE DA PAGE ==================================================
        JLabel titulo = new JLabel("Histórico De Manutenções");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titulo, gbc);

        //===== campo de pesquisa/busca parametros esperados => placa ou data de manutenção
        JLabel labelBusca = new JLabel("<html><font color='red'><b>*</b></font> Placa/Data De Manutenção:</html>");
        labelBusca.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(labelBusca, gbc);

        campoBusca = new JTextField(20);
        campoBusca.setPreferredSize(new Dimension(250, 30)); 
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(campoBusca, gbc);

        //===== Button de busca do Historico de manutenção =========
        botaoBuscar = new JButton("Buscar");
        botaoBuscar.setFont(new Font("Arial", Font.PLAIN, 14));
        botaoBuscar.setBackground(new Color(50, 50, 50));
        botaoBuscar.setForeground(Color.BLACK);
        botaoBuscar.setFocusPainted(false);
        botaoBuscar.setPreferredSize(new Dimension(100, 30)); 
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE; 
        gbc.anchor = GridBagConstraints.LINE_START;
        add(botaoBuscar, gbc);

        //====== Tabela q p mostrar o HISTORICO na tela
        String[] colunas = {"VEÍCULO", "PLACA", "Data De Solicitação", "Data De Conclusão", "Descrição da Manutenção", "Status"};
        tabelaModelo = new DefaultTableModel(colunas, 0);
        tabelaHistorico = new JTable(tabelaModelo);
        tabelaHistorico.getTableHeader().setReorderingAllowed(false);
        tabelaHistorico.setEnabled(false);
        
        //estilização(back-fore e font) do cabecalho da tabela 
        tabelaHistorico.getTableHeader().setBackground(Color.BLACK);
        tabelaHistorico.getTableHeader().setForeground(Color.BLACK);
        tabelaHistorico.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 12));        
        
        JScrollPane scrollPane = new JScrollPane(tabelaHistorico);
        scrollPane.setPreferredSize(new Dimension(800, 300));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(scrollPane, gbc);

        carregarHistoricoManutencoes("");

        botaoBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filtro = campoBusca.getText();
                carregarHistoricoManutencoes(filtro);
            }
        });
    }

    private void carregarHistoricoManutencoes(String filtro) {
        tabelaModelo.setRowCount(0);

        //busca a lista manutenções finalizadas no data acces obj
        List<Object[]> historico = MecanicoDAO.listarManutencoesFinalizadas(filtro);

        //for p add os dados a table linha por linha
        for (Object[] manutencao : historico) {
            tabelaModelo.addRow(manutencao);
        }
    }
}