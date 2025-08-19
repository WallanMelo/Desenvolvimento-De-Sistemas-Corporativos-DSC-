package com.mycompany.telalogin;

import com.mycompany.telalogin.dao.ClienteDAO;
import com.mycompany.telalogin.model.Cliente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
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
            String nome = JOptionPane.showInputDialog(this, "Nome:", c.getNome());
            if (nome == null) return;
            String telefone = JOptionPane.showInputDialog(this, "Telefone:", c.getTelefone());
            if (telefone == null) return;
            String email = JOptionPane.showInputDialog(this, "Email:", c.getEmail());
            if (email == null) return;
            String endereco = JOptionPane.showInputDialog(this, "Endereço:", c.getEndereco());
            if (endereco == null) return;

            c.setNome(nome.trim());
            c.setTelefone(telefone.trim());
            c.setEmail(email.trim());
            c.setEndereco(endereco.trim());

            new ClienteDAO().atualizar(c);
            carregar(txtBusca.getText());
            JOptionPane.showMessageDialog(this, "Cliente atualizado!");
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
