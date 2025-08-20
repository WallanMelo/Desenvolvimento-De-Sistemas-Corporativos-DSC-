package com.mycompany.telalogin;
import com.mycompany.telalogin.dao.RelatorioDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Vector;

public class TelaRelatorios extends JPanel {

    private JTable tabelaRelatorio;
    private DefaultTableModel tableModel;
    private String tipoRelatorio;
    private String filtroAtual;
    private JComboBox<String> comboFiltro;
    private String[] colunasAtuais;

    public TelaRelatorios() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel labelBoasVindas = new JLabel("Selecione um tipo de relatório no menu ao lado.", SwingConstants.CENTER);
        labelBoasVindas.setFont(new Font("Arial", Font.PLAIN, 16));
        add(labelBoasVindas, BorderLayout.CENTER);
    }

    public TelaRelatorios(String tipoRelatorio) {
        this.tipoRelatorio = tipoRelatorio;
        this.filtroAtual = "Todos"; // Filtro padrão
        
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel tituloRelatorio = new JLabel("Relatório " + tipoRelatorio, SwingConstants.CENTER);
        tituloRelatorio.setFont(new Font("Arial", Font.BOLD, 20));
        add(tituloRelatorio, BorderLayout.NORTH);

        //==== PANEL c FILTROS
        JPanel painelFiltros = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelFiltros.setBackground(new Color(240, 240, 240));
        
        JLabel labelFiltro = new JLabel("Filtrar por:");
        painelFiltros.add(labelFiltro);
        
        //mostra OPÇÕES de FILTRO
        comboFiltro = new JComboBox<>(RelatorioDAO.getOpcoesFiltro(tipoRelatorio));
        comboFiltro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtroAtual = (String) comboFiltro.getSelectedItem();
                carregarDadosTabela();
            }
        });
        painelFiltros.add(comboFiltro);
        
        add(painelFiltros, BorderLayout.NORTH);

        //painel c CONTEUDO CENTRAL
        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        criarTabela();
        JScrollPane scrollPane = new JScrollPane(tabelaRelatorio);
        painelTabela.add(scrollPane, BorderLayout.CENTER);

        add(painelTabela, BorderLayout.CENTER);

        //BOTÔES p EXPORTAR p EXCEL ou PDF
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelBotoes.setBackground(new Color(240, 240, 240));

        JButton botaoExportarPDF = new JButton("Exportar para PDF");
        JButton botaoExportarExcel = new JButton("Exportar para Excel");

        botaoExportarPDF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportarParaPDF();
            }
        });

        botaoExportarExcel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportarParaExcel();
            }
        });

        painelBotoes.add(botaoExportarPDF);
        painelBotoes.add(botaoExportarExcel);
        add(painelBotoes, BorderLayout.SOUTH);

        carregarDadosTabela();
    }

    private void criarTabela() {
        colunasAtuais = RelatorioDAO.getColunasParaTabela(tipoRelatorio, filtroAtual);
        tableModel = new DefaultTableModel(colunasAtuais, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaRelatorio = new JTable(tableModel);
        tabelaRelatorio.setFont(new Font("Arial", Font.PLAIN, 12));
        tabelaRelatorio.setRowHeight(25);
        tabelaRelatorio.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
    }

    private void carregarDadosTabela() {
        tableModel.setRowCount(0);

        //RECEB os DADOS do DATA access c FILTRO
        List<Object[]> dados = RelatorioDAO.obterDadosRelatorio(tipoRelatorio, filtroAtual);

        String[] novasColunas = RelatorioDAO.getColunasParaTabela(tipoRelatorio, filtroAtual);
        
        boolean colunasMudaram = !java.util.Arrays.equals(novasColunas, colunasAtuais);
        
        if (colunasMudaram) {
            colunasAtuais = novasColunas;
            tableModel.setColumnIdentifiers(novasColunas);
        }

        for (Object[] linha : dados) {
            tableModel.addRow(linha);
        }
    }
    
    //funct p EXPORTAR p PDF
    private void exportarParaPDF() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar Relatório PDF");
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getName().toLowerCase().endsWith(".pdf")) {
                fileToSave = new File(fileToSave.getParentFile(), fileToSave.getName() + ".pdf");
            }
            RelatorioDAO.exportarParaPDF(tipoRelatorio, fileToSave.getAbsolutePath(), filtroAtual);
            JOptionPane.showMessageDialog(this, "Relatório PDF exportado com sucesso!");
        }
    }
    //funct p EXPORT p EXCEL
    private void exportarParaExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar Relatório Excel");
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getName().toLowerCase().endsWith(".xlsx")) {
                fileToSave = new File(fileToSave.getParentFile(), fileToSave.getName() + ".xlsx");
            }
            RelatorioDAO.exportarParaExcel(tipoRelatorio, fileToSave.getAbsolutePath(), filtroAtual);
            JOptionPane.showMessageDialog(this, "Relatório Excel exportado com sucesso!");
        }
    }
}