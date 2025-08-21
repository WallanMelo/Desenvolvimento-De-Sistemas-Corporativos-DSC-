package com.mycompany.telalogin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

public class TelaGestao extends JPanel {

    private JTextField campoNome, campoCPF, campoEmail, campoTelefone, campoNascimento, campoLogin, campoSalario, campoBusca;
    private JPasswordField campoSenha;
    private JComboBox<String> comboNivel;
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    private int funcionarioSelecionadoId = -1;

    public TelaGestao() {
        setLayout(new BorderLayout(10,10));
        setBackground(new Color(200,200,200));

        // ====== PAINEL de BUSCA ======
        JPanel painelBuscaSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        painelBuscaSuperior.setBorder(BorderFactory.createTitledBorder("Buscar Funcionário"));
        
        campoBusca = new JTextField(25);
        JButton botaoBuscar = new JButton("Buscar");
        
        painelBuscaSuperior.add(new JLabel("Nome ou CPF (com pontos e traço):"));
        painelBuscaSuperior.add(campoBusca);
        painelBuscaSuperior.add(botaoBuscar);
        
        add(painelBuscaSuperior, BorderLayout.NORTH);

        // ==== PAINEL CENTRAL=======
        JPanel painelCentral = new JPanel(new BorderLayout(10,10));

        // ======== FORMULÁRIO DE FUNCIONARIO ======
        JPanel painelForm = new JPanel(new GridLayout(0,4,10,10)); 
        painelForm.setBorder(BorderFactory.createTitledBorder("Dados do Funcionário"));
        painelForm.setPreferredSize(new Dimension(900, 250)); 

        campoNome = new JTextField(20);
        campoCPF = new JTextField(15);
        campoEmail = new JTextField(20);
        campoTelefone = new JTextField(15);
        campoNascimento = new JTextField(10);
        campoLogin = new JTextField(15);
        campoSenha = new JPasswordField(15);
        comboNivel = new JComboBox<>(new String[]{"Administrador","Atendente","Mecânico"});
        campoSalario = new JTextField(10);

        // 4 colunas
        painelForm.add(new JLabel("Nome:*")); 
        painelForm.add(campoNome);
        painelForm.add(new JLabel("CPF:*")); 
        painelForm.add(campoCPF);
        
        painelForm.add(new JLabel("Email:")); 
        painelForm.add(campoEmail);
        painelForm.add(new JLabel("Telefone:")); 
        painelForm.add(campoTelefone);
        
        painelForm.add(new JLabel("Nascimento (AAAA-MM-DD):")); 
        painelForm.add(campoNascimento);
        painelForm.add(new JLabel("Login:*")); 
        painelForm.add(campoLogin);
        
        painelForm.add(new JLabel("Senha:*")); 
        painelForm.add(campoSenha);
        painelForm.add(new JLabel("Nível:*")); 
        painelForm.add(comboNivel);
        
        painelForm.add(new JLabel("Salário:*")); 
        painelForm.add(campoSalario);
        painelForm.add(new JLabel(""));
        painelForm.add(new JLabel(""));

        painelCentral.add(painelForm, BorderLayout.NORTH);

        // ====== BOTÕES ======
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton botaoCadastrar = new JButton("Cadastrar");
        JButton botaoAtualizar = new JButton("Atualizar");
        JButton botaoExcluir = new JButton("Excluir");
        JButton botaoLimpar = new JButton("Limpar");

        botaoCadastrar.setPreferredSize(new Dimension(120, 30));
        botaoAtualizar.setPreferredSize(new Dimension(120, 30));
        botaoExcluir.setPreferredSize(new Dimension(120, 30));
        botaoLimpar.setPreferredSize(new Dimension(120, 30));

        painelBotoes.add(botaoCadastrar);
        painelBotoes.add(botaoAtualizar);
        painelBotoes.add(botaoExcluir);
        painelBotoes.add(botaoLimpar);

        painelCentral.add(painelBotoes, BorderLayout.CENTER);
        add(painelCentral, BorderLayout.CENTER);

        // ====== PAINEL DA TABELA COM TITULO ======
        JPanel painelTabela = new JPanel(new BorderLayout(10,10));
        
        // Título
        JLabel tituloTabela = new JLabel("FUNCIONÁRIOS", SwingConstants.CENTER);
        tituloTabela.setFont(new Font("Arial", Font.BOLD, 16));
        tituloTabela.setForeground(Color.WHITE);
        tituloTabela.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        painelTabela.add(tituloTabela, BorderLayout.NORTH);

        // ====== TABELA ======
        modeloTabela = new DefaultTableModel();
        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setFont(new Font("Arial", Font.PLAIN, 12));
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setPreferredSize(new Dimension(1000, 300));

        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Nome");
        modeloTabela.addColumn("CPF");
        modeloTabela.addColumn("Email");
        modeloTabela.addColumn("Telefone");
        modeloTabela.addColumn("Nascimento");
        modeloTabela.addColumn("Login");
        modeloTabela.addColumn("Nível");
        modeloTabela.addColumn("Salário");

        painelTabela.add(scroll, BorderLayout.CENTER);
        add(painelTabela, BorderLayout.SOUTH);

        // ===== AÇ
        botaoCadastrar.addActionListener(e -> cadastrarFuncionario());
        botaoAtualizar.addActionListener(e -> atualizarFuncionario());
        botaoExcluir.addActionListener(e -> excluirFuncionario());
        botaoLimpar.addActionListener(e -> limparCampos());
        botaoBuscar.addActionListener(e -> buscarFuncionarios());

        tabela.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int linha = tabela.getSelectedRow();
                if(linha >= 0) {
                    funcionarioSelecionadoId = Integer.parseInt(modeloTabela.getValueAt(linha,0).toString());
                    campoNome.setText(modeloTabela.getValueAt(linha,1).toString());
                    campoCPF.setText(modeloTabela.getValueAt(linha,2).toString());
                    campoEmail.setText(modeloTabela.getValueAt(linha,3).toString());
                    campoTelefone.setText(modeloTabela.getValueAt(linha,4).toString());
                    campoNascimento.setText(modeloTabela.getValueAt(linha,5).toString());
                    campoLogin.setText(modeloTabela.getValueAt(linha,6).toString());
                    comboNivel.setSelectedItem(modeloTabela.getValueAt(linha,7).toString());
                    campoSalario.setText(modeloTabela.getValueAt(linha,8).toString());
                    campoSenha.setText("");
                }
            }
        });

        carregarFuncionarios();
    }

    // ========BUSCA ======
    private void buscarFuncionarios() {
        String termo = campoBusca.getText().trim().toLowerCase();
        modeloTabela.setRowCount(0);
        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM usuario")) {
            while (rs.next()) {
                String nome = rs.getString("nome").toLowerCase();
                String cpf = rs.getString("cpf").toLowerCase();
                if (nome.contains(termo) || cpf.contains(termo) || termo.isEmpty()) {
                    Vector<Object> row = new Vector<>();
                    row.add(rs.getInt("id"));
                    row.add(rs.getString("nome"));
                    row.add(rs.getString("cpf"));
                    row.add(rs.getString("email"));
                    row.add(rs.getString("telefone"));
                    row.add(rs.getDate("data_nascimento"));
                    row.add(rs.getString("login"));
                    row.add(rs.getString("nivel"));
                    row.add(rs.getDouble("salario"));
                    modeloTabela.addRow(row);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,"Erro ao buscar funcionarios: " + ex.getMessage());
        }
    }

    // ====== CONEXÃO DB ==========
    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(
            "jdbc:mysql://localhost/aluguel_veiculos?useSSL=false&useUnicode=true&characterEncoding=UTF-8",
            "root",
            "12345"
        );
    }

    // ====== CARREGA FUNCIONARIOS ========
    private void carregarFuncionarios() {
        modeloTabela.setRowCount(0);
        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM usuario")) {
            while(rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("id"));
                row.add(rs.getString("nome"));
                row.add(rs.getString("cpf"));
                row.add(rs.getString("email"));
                row.add(rs.getString("telefone"));
                row.add(rs.getDate("data_nascimento"));
                row.add(rs.getString("login"));
                row.add(rs.getString("nivel"));
                row.add(rs.getDouble("salario"));
                modeloTabela.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,"Erro ao carregar funcionários: " + e.getMessage());
        }
    }

    // ====== CADASTRA FUNCIONARIO =========
    private void cadastrarFuncionario() {
        // Validação dos campos obrigatórios
        if (campoNome.getText().isEmpty() || campoCPF.getText().isEmpty() || 
            campoLogin.getText().isEmpty() || campoSenha.getPassword().length == 0 ||
            campoSalario.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios (*)!");
            return;
        }

        String sql = "INSERT INTO usuario (nome,cpf,email,telefone,data_nascimento,login,senha,nivel,salario) VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, campoNome.getText());
            ps.setString(2, campoCPF.getText());
            ps.setString(3, campoEmail.getText());
            ps.setString(4, campoTelefone.getText());
            ps.setString(5, campoNascimento.getText());
            ps.setString(6, campoLogin.getText());
            ps.setString(7, new String(campoSenha.getPassword()));
            ps.setString(8, comboNivel.getSelectedItem().toString());
            ps.setDouble(9, Double.parseDouble(campoSalario.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this,"Funcionário cadastrado com sucesso!");
            carregarFuncionarios();
            limparCampos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,"Erro: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,"Erro: Salário deve ser um número válido!");
        }
    }

    // ====== ATUALIZAR FUNCIONAIO ==========
    private void atualizarFuncionario() {
        if(funcionarioSelecionadoId == -1) {
            JOptionPane.showMessageDialog(this,"Selecione um funcionário na tabela!");
            return;
        }
        
        // Validação dos campos obrigatórios
        if (campoNome.getText().isEmpty() || campoCPF.getText().isEmpty() || 
            campoLogin.getText().isEmpty() || campoSalario.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios (*)!");
            return;
        }

        String senha = new String(campoSenha.getPassword());
        String sql;
        boolean atualizaSenha = !senha.isEmpty();
        if(atualizaSenha) {
            sql = "UPDATE usuario SET nome=?, cpf=?, email=?, telefone=?, data_nascimento=?, login=?, senha=?, nivel=?, salario=? WHERE id=?";
        } else {
            sql = "UPDATE usuario SET nome=?, cpf=?, email=?, telefone=?, data_nascimento=?, login=?, nivel=?, salario=? WHERE id=?";
        }

        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, campoNome.getText());
            ps.setString(2, campoCPF.getText());
            ps.setString(3, campoEmail.getText());
            ps.setString(4, campoTelefone.getText());
            ps.setString(5, campoNascimento.getText());
            ps.setString(6, campoLogin.getText());

            if(atualizaSenha) {
                ps.setString(7, senha);
                ps.setString(8, comboNivel.getSelectedItem().toString());
                ps.setDouble(9, Double.parseDouble(campoSalario.getText()));
                ps.setInt(10, funcionarioSelecionadoId);
            } else {
                ps.setString(7, comboNivel.getSelectedItem().toString());
                ps.setDouble(8, Double.parseDouble(campoSalario.getText()));
                ps.setInt(9, funcionarioSelecionadoId);
            }

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this,"Funcionario atualizado com sucesso!");
            carregarFuncionarios();
            limparCampos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,"Erro: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,"Erro: Salário deve ser um número válido!");
        }
    }

    // ====== EXCLUI FUNCIONARIO =========
    private void excluirFuncionario() {
        if(funcionarioSelecionadoId == -1) {
            JOptionPane.showMessageDialog(this,"Selecione um funcionario na tabela!!!!");
            return;
        }
        
        int confirmacao = JOptionPane.showConfirmDialog(this, 
            "Tem certeza que deseja excluir este funcionário?", 
            "Confirmação de Exclusão", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirmacao != JOptionPane.YES_OPTION) {
            return;
        }

        String sql = "DELETE FROM usuario WHERE id=?";
        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, funcionarioSelecionadoId);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this,"Funcionário excluído com sucesso!");
            carregarFuncionarios();
            limparCampos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,"Erro: " + e.getMessage());
        }
    }

    // ====== LIMPA OS CAMPOS ======
    private void limparCampos() {
        campoNome.setText("");
        campoCPF.setText("");
        campoEmail.setText("");
        campoTelefone.setText("");
        campoNascimento.setText("2000-01-01");
        campoLogin.setText("");
        campoSenha.setText("");
        comboNivel.setSelectedIndex(0);
        campoSalario.setText("");
        funcionarioSelecionadoId = -1;
        campoBusca.setText("");
    }
}