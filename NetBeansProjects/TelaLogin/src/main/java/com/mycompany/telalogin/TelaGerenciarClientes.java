package com.mycompany.telalogin;

import com.mycompany.telalogin.dao.ClienteDAO;
import com.mycompany.telalogin.model.Cliente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class TelaGerenciarClientes extends JPanel {
    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID","Nome","CPF","CNH","Email","Telefone","Nascimento","Endereço"}, 0) {
        public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable tabela = new JTable(model);
    private final JTextField txtBusca = new JTextField(15);

    public TelaGerenciarClientes() {
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(12,12,12,12));

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topo.add(new JLabel("Buscar (nome/CPF/CNH):"));
        topo.add(txtBusca);
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> carregar(txtBusca.getText()));
        topo.add(btnBuscar);

        JButton btnEditar = new JButton("Editar");
        btnEditar.addActionListener(e -> editarSelecionado());
        topo.add(btnEditar);

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(e -> excluirSelecionado());
        topo.add(btnExcluir);

        add(topo, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);
        carregar(null);
    }

    private void carregar(String termo) {
        model.setRowCount(0);
        try {
            List<Cliente> lista = new ClienteDAO().listar(termo);
            for (Cliente c : lista) {
                model.addRow(new Object[]{
                        c.getId(), c.getNome(), c.getCpf(), c.getCnh(),
                        c.getEmail(), c.getTelefone(),
                        c.getDataNascimento(), c.getEndereco()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar: " + ex.getMessage());
        }
    }

    private void editarSelecionado() {
        int row = tabela.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente.");
            return;
        }
        
        Integer id = (Integer) model.getValueAt(row, 0);
        try {
            Cliente c = new ClienteDAO().buscarPorId(id);
            if (c == null) return;
            
            // Atulizando para poder editar quaisquer campos do cliente
            JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
            
            panel.add(new JLabel("Nome:"));
            JTextField txtNome = new JTextField(c.getNome(), 20);
            panel.add(txtNome);
            
            panel.add(new JLabel("CPF:"));
            JTextField txtCpf = new JTextField(c.getCpf(), 20);
            panel.add(txtCpf);
            
            panel.add(new JLabel("CNH:"));
            JTextField txtCnh = new JTextField(c.getCnh(), 20);
            panel.add(txtCnh);
            
            panel.add(new JLabel("Email:"));
            JTextField txtEmail = new JTextField(c.getEmail(), 20);
            panel.add(txtEmail);
            
            panel.add(new JLabel("Telefone:"));
            JTextField txtTelefone = new JTextField(c.getTelefone(), 20);
            panel.add(txtTelefone);
            
            panel.add(new JLabel("Data de Nascimento (AAAA-MM-DD):"));
            JTextField txtNascimento = new JTextField(c.getDataNascimento().toString(), 20);
            panel.add(txtNascimento);
            
            panel.add(new JLabel("Endereço:"));
            JTextField txtEndereco = new JTextField(c.getEndereco(), 20);
            panel.add(txtEndereco);
            
            int result = JOptionPane.showConfirmDialog(this, panel, "Editar Cliente", 
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            
            if (result != JOptionPane.OK_OPTION) return;
            
            // Validar e atualizar os dados
            if (txtNome.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome é obrigatório!");
                return;
            }
            
            // Verificaçao de data de nascimento
            LocalDate dataNascimento;
            try {
                dataNascimento = LocalDate.parse(txtNascimento.getText().trim());
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this, "Formato de data inválido! Use AAAA-MM-DD.");
                return;
            }
            
            // Atualizar o objeto cliente
            c.setNome(txtNome.getText().trim());
            c.setCpf(txtCpf.getText().trim());
            c.setCnh(txtCnh.getText().trim());
            c.setEmail(txtEmail.getText().trim());
            c.setTelefone(txtTelefone.getText().trim());
            c.setDataNascimento(dataNascimento);
            c.setEndereco(txtEndereco.getText().trim());
            
            new ClienteDAO().atualizar(c);
            carregar(txtBusca.getText());
            JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso!");
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao editar: " + ex.getMessage());
        }
    }

    private void excluirSelecionado() {
        int row = tabela.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente.");
            return;
        }
        Integer id = (Integer) model.getValueAt(row, 0);
        int conf = JOptionPane.showConfirmDialog(this, "Excluir cliente ID " + id + "?", "Confirmação",
                JOptionPane.YES_NO_OPTION);
        if (conf != JOptionPane.YES_OPTION) return;

        try {
            new ClienteDAO().excluir(id);
            carregar(txtBusca.getText());
            JOptionPane.showMessageDialog(this, "Cliente excluído!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage());
        }
    }
}