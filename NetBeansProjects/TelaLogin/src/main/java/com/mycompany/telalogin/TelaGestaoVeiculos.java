package com.mycompany.telalogin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

public class TelaGestaoVeiculos extends JPanel {
    
    private JTextField campoModelo, campoFabricante, campoTipo, campoAno, campoPlaca, campoEstado, campoCor, campoBusca;
    private JTextArea campoCaracteristicas;
    private JButton botaoCadastrar, botaoAtualizar, botaoExcluir, botaoLimpar, botaoBuscar;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private int veiculoSelecionadoId = -1;
    private String opcaoSelecionada;

    public TelaGestaoVeiculos() {
        this("Cadastrar Veículo");
    }

    public TelaGestaoVeiculos(String opcao) {
        this.opcaoSelecionada = opcao;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(140, 140, 140));
        
        if (opcaoSelecionada.equals("Cadastrar Veículo")) {
            setupTelaCadastro();
        }
    }

    private void setupTelaCadastro() {
    // Painel de busca
    JPanel painelBuscaSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
    painelBuscaSuperior.setBorder(BorderFactory.createTitledBorder("Buscar Veículo"));
    painelBuscaSuperior.setBackground(new Color(160, 160, 160));

    campoBusca = new JTextField(25);
    botaoBuscar = new JButton("Buscar");
    botaoBuscar.setPreferredSize(new Dimension(100, 30));

    painelBuscaSuperior.add(new JLabel("Modelo, Placa ou Fabricante:"));
    painelBuscaSuperior.add(campoBusca);
    painelBuscaSuperior.add(botaoBuscar);

    add(painelBuscaSuperior, BorderLayout.NORTH);

    JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
    painelPrincipal.setBackground(new Color(160, 160, 160));

    // Formulario
    JPanel painelForm = new JPanel(new GridBagLayout());
    painelForm.setBorder(BorderFactory.createTitledBorder("Dados do Veiculo"));
    painelForm.setBackground(new Color(160, 160, 160));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(8, 8, 8, 8);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.WEST;

    campoModelo = new JTextField(20);
    campoFabricante = new JTextField(20);
    campoTipo = new JTextField(20);
    campoAno = new JTextField(6);
    campoPlaca = new JTextField(10);
    campoEstado = new JTextField(20);
    campoCor = new JTextField(15);
    campoCaracteristicas = new JTextArea(4, 25);

    Font fontCampos = new Font("Arial", Font.PLAIN, 14);
    campoModelo.setFont(fontCampos);
    campoFabricante.setFont(fontCampos);
    campoTipo.setFont(fontCampos);
    campoAno.setFont(fontCampos);
    campoPlaca.setFont(fontCampos);
    campoEstado.setFont(fontCampos);
    campoCor.setFont(fontCampos);
    campoCaracteristicas.setFont(fontCampos);

    adicionarCampoForm(painelForm, gbc, "Modelo:*", campoModelo, 0);
    adicionarCampoForm(painelForm, gbc, "Fabricante:", campoFabricante, 1);
    adicionarCampoForm(painelForm, gbc, "Tipo:", campoTipo, 2);
    adicionarCampoForm(painelForm, gbc, "Ano:*", campoAno, 3);
    adicionarCampoForm(painelForm, gbc, "Placa:*", campoPlaca, 4);
    adicionarCampoForm(painelForm, gbc, "Estado Conservação:", campoEstado, 5);
    adicionarCampoForm(painelForm, gbc, "Cor:", campoCor, 6);

    gbc.gridx = 0;
    gbc.gridy = 7;
    gbc.gridwidth = 1;
    JLabel labelCaracteristicas = new JLabel("Características:");
    labelCaracteristicas.setFont(new Font("Arial", Font.BOLD, 14));
    painelForm.add(labelCaracteristicas, gbc);

    gbc.gridx = 1;
    gbc.gridwidth = 2;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    JScrollPane scrollCaracteristicas = new JScrollPane(campoCaracteristicas);
    scrollCaracteristicas.setPreferredSize(new Dimension(300, 80));
    painelForm.add(scrollCaracteristicas, gbc);

    painelPrincipal.add(painelForm, BorderLayout.NORTH);

    JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
    painelBotoes.setBackground(new Color(160, 160, 160));
    painelBotoes.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

    botaoCadastrar = criarBotaoGrande("Cadastrar", new Color(40, 167, 69));
    botaoAtualizar = criarBotaoGrande("Atualizar", new Color(255, 193, 7));
    botaoExcluir = criarBotaoGrande("Excluir", new Color(220, 53, 69));
    botaoLimpar = criarBotaoGrande("Limpar", new Color(108, 117, 125));

    painelBotoes.add(botaoCadastrar);
    painelBotoes.add(botaoAtualizar);
    painelBotoes.add(botaoExcluir);
    painelBotoes.add(botaoLimpar);

    painelPrincipal.add(painelBotoes, BorderLayout.CENTER);

    add(painelPrincipal, BorderLayout.CENTER);

    // Tabela
    JPanel painelTabela = new JPanel(new BorderLayout(10, 10));
    painelTabela.setBackground(new Color(160, 160, 160));
    painelTabela.setPreferredSize(new Dimension(1000, 300));

    JLabel tituloTabela = new JLabel("VEÍCULOS CADASTRADOS", SwingConstants.CENTER);
    tituloTabela.setFont(new Font("Arial", Font.BOLD, 16));
    tituloTabela.setForeground(Color.BLACK);
    tituloTabela.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
    painelTabela.add(tituloTabela, BorderLayout.NORTH);

    modeloTabela = new DefaultTableModel();
    tabela = new JTable(modeloTabela);
    tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    tabela.setFont(new Font("Arial", Font.PLAIN, 12));
    tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
    tabela.setRowHeight(25);

    JScrollPane scrollTabela = new JScrollPane(tabela);
    scrollTabela.setPreferredSize(new Dimension(1000, 200));

    modeloTabela.addColumn("ID");
    modeloTabela.addColumn("Modelo");
    modeloTabela.addColumn("Fabricante");
    modeloTabela.addColumn("Tipo");
    modeloTabela.addColumn("Ano");
    modeloTabela.addColumn("Placa");
    modeloTabela.addColumn("Estado");
    modeloTabela.addColumn("Cor");
    modeloTabela.addColumn("Status");

    painelTabela.add(scrollTabela, BorderLayout.CENTER);
    add(painelTabela, BorderLayout.SOUTH);

    // Listeners
    botaoCadastrar.addActionListener(e -> cadastrarVeiculo());
    botaoAtualizar.addActionListener(e -> atualizarVeiculo());
    botaoExcluir.addActionListener(e -> excluirVeiculo());
    botaoLimpar.addActionListener(e -> limparCampos());
    botaoBuscar.addActionListener(e -> buscarVeiculos());

    tabela.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            int linha = tabela.getSelectedRow();
            if (linha >= 0) {
                veiculoSelecionadoId = Integer.parseInt(modeloTabela.getValueAt(linha, 0).toString());
                campoModelo.setText(modeloTabela.getValueAt(linha, 1).toString());
                campoFabricante.setText(modeloTabela.getValueAt(linha, 2).toString());
                campoTipo.setText(modeloTabela.getValueAt(linha, 3).toString());
                campoAno.setText(modeloTabela.getValueAt(linha, 4).toString());
                campoPlaca.setText(modeloTabela.getValueAt(linha, 5).toString());
                campoEstado.setText(modeloTabela.getValueAt(linha, 6).toString());
                campoCor.setText(modeloTabela.getValueAt(linha, 7).toString());

                carregarCaracteristicasVeiculo(veiculoSelecionadoId);
            }
        }
    });

    carregarVeiculos();
}

    private JButton criarBotaoGrande(String texto, Color corFundo) {
        JButton botao = new JButton(texto);
        botao.setPreferredSize(new Dimension(140, 40));
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        botao.setBackground(corFundo);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return botao;
    }

    // meetodo auxiliar para add campos no form (APENAS NESTA VERSAO)
    private void adicionarCampoForm(JPanel painel, GridBagConstraints gbc, String label, JComponent campo, int linha) {
        gbc.gridx = 0;
        gbc.gridy = linha;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;

        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Arial", Font.BOLD, 14));
        jLabel.setPreferredSize(new Dimension(180, 25));
        painel.add(jLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        campo.setPreferredSize(new Dimension(300, 30));
        painel.add(campo, gbc);

        gbc.weightx = 0;
    }

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/aluguel_veiculos", 
            "root", 
            "12345"
        );
    }

    private void carregarVeiculos() {
        modeloTabela.setRowCount(0);
        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM veiculo")) {
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("id"));
                row.add(rs.getString("modelo"));
                row.add(rs.getString("fabricante"));
                row.add(rs.getString("tipo"));
                row.add(rs.getInt("ano"));
                row.add(rs.getString("placa"));
                row.add(rs.getString("estado_conservacao"));
                row.add(rs.getString("cor"));
                row.add(rs.getString("status"));
                modeloTabela.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar veículos: " + e.getMessage());
        }
    }

    private void buscarVeiculos() {
        String termo = campoBusca.getText().trim().toLowerCase();
        modeloTabela.setRowCount(0);
        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM veiculo")) {
            while (rs.next()) {
                String modelo = rs.getString("modelo").toLowerCase();
                String placa = rs.getString("placa").toLowerCase();
                String fabricante = rs.getString("fabricante").toLowerCase();
                
                if (modelo.contains(termo) || placa.contains(termo) || fabricante.contains(termo) || termo.isEmpty()) {
                    Vector<Object> row = new Vector<>();
                    row.add(rs.getInt("id"));
                    row.add(rs.getString("modelo"));
                    row.add(rs.getString("fabricante"));
                    row.add(rs.getString("tipo"));
                    row.add(rs.getInt("ano"));
                    row.add(rs.getString("placa"));
                    row.add(rs.getString("estado_conservacao"));
                    row.add(rs.getString("cor"));
                    row.add(rs.getString("status"));
                    modeloTabela.addRow(row);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar veículos: " + e.getMessage());
        }
    }
    
    //corrigir bug*************
    private void carregarCaracteristicasVeiculo(int id) {
        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement("SELECT caracteristicas FROM veiculo WHERE id = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                campoCaracteristicas.setText(rs.getString("caracteristicas"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar características: " + e.getMessage());
        }
    }

    private void cadastrarVeiculo() {
        if (campoModelo.getText().trim().isEmpty() || campoPlaca.getText().trim().isEmpty() || campoAno.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Modelo, Placa e Ano são campos obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int ano = Integer.parseInt(campoAno.getText().trim());
            if (ano < 1900 || ano > java.time.Year.now().getValue() + 1) {
                JOptionPane.showMessageDialog(this, "Ano deve estar entre 1900 e " + (java.time.Year.now().getValue() + 1), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ano deve ser um número válido!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "INSERT INTO veiculo (modelo, fabricante, tipo, ano, placa, estado_conservacao, cor, caracteristicas, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'Disponível')";
        
        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, campoModelo.getText().trim());
            ps.setString(2, campoFabricante.getText().trim());
            ps.setString(3, campoTipo.getText().trim());
            ps.setInt(4, Integer.parseInt(campoAno.getText().trim()));
            ps.setString(5, campoPlaca.getText().trim().toUpperCase());
            ps.setString(6, campoEstado.getText().trim());
            ps.setString(7, campoCor.getText().trim());
            ps.setString(8, campoCaracteristicas.getText().trim());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Veículo cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            carregarVeiculos();
            limparCampos();
            
        } catch (SQLException ex) {
            if (ex.getMessage().contains("Duplicate entry")) {
                JOptionPane.showMessageDialog(this, "Placa já cadastrada no sistema!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar veículo: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void atualizarVeiculo() {
        if (veiculoSelecionadoId == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo na tabela!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (campoModelo.getText().trim().isEmpty() || campoPlaca.getText().trim().isEmpty() || campoAno.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Modelo, Placa e Ano são campos obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int ano = Integer.parseInt(campoAno.getText().trim());
            if (ano < 1900 || ano > java.time.Year.now().getValue() + 1) {
                JOptionPane.showMessageDialog(this, "Ano deve estar entre 1900 e " + (java.time.Year.now().getValue() + 1), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ano deve ser um número válido!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "UPDATE veiculo SET modelo=?, fabricante=?, tipo=?, ano=?, placa=?, estado_conservacao=?, cor=?, caracteristicas=? WHERE id=?";
        
        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, campoModelo.getText().trim());
            ps.setString(2, campoFabricante.getText().trim());
            ps.setString(3, campoTipo.getText().trim());
            ps.setInt(4, Integer.parseInt(campoAno.getText().trim()));
            ps.setString(5, campoPlaca.getText().trim().toUpperCase());
            ps.setString(6, campoEstado.getText().trim());
            ps.setString(7, campoCor.getText().trim());
            ps.setString(8, campoCaracteristicas.getText().trim());
            ps.setInt(9, veiculoSelecionadoId);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Veículo atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarVeiculos();
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar veículo!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar veículo: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirVeiculo() {
        if (veiculoSelecionadoId == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo na tabela!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Tem certeza que deseja excluir este veículo?", 
            "Confirmação", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        String sql = "DELETE FROM veiculo WHERE id=?";
        
        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, veiculoSelecionadoId);
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Veículo excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarVeiculos();
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir veículo!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir veículo: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        campoModelo.setText("");
        campoFabricante.setText("");
        campoTipo.setText("");
        campoAno.setText("");
        campoPlaca.setText("");
        campoEstado.setText("");
        campoCor.setText("");
        campoCaracteristicas.setText("");
        campoBusca.setText("");
        veiculoSelecionadoId = -1;
        campoModelo.requestFocus();
    }
}
