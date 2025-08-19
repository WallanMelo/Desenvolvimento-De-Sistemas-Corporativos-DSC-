package com.mycompany.telalogin;

import com.mycompany.telalogin.dao.VeiculoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class TelaStatusFrota extends JPanel {
    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID","Modelo","Placa","Status","Tipo"}, 0) {
        public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable tabela = new JTable(model);
    private final JTextField txtBusca = new JTextField(15);

    public TelaStatusFrota() {
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(12,12,12,12));

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topo.add(new JLabel("Status da Frota | Buscar (placa/modelo/tipo):"));
        topo.add(txtBusca);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> carregar(txtBusca.getText()));
        topo.add(btnBuscar);

        JButton btnDisponivel = new JButton("Marcar Disponível");
        btnDisponivel.addActionListener(e -> mudarStatus("Disponível"));
        topo.add(btnDisponivel);

        JButton btnManut = new JButton("Marcar Manutenção");
        btnManut.addActionListener(e -> mudarStatus("Manutenção"));
        topo.add(btnManut);

        add(topo, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        carregar(null);
    }

    private void carregar(String termo) {
        model.setRowCount(0);
        try {
            List<Object[]> lista = new VeiculoDAO().listar(termo);
            for (Object[] v : lista) model.addRow(v);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar: " + ex.getMessage());
        }
    }

    private void mudarStatus(String status) {
        int row = tabela.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo na tabela.");
            return;
        }
        int id = (Integer) model.getValueAt(row, 0);
        try {
            new com.mycompany.telalogin.dao.VeiculoDAO().atualizarStatus(id, status);
            carregar(txtBusca.getText());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar status: " + ex.getMessage());
        }
    }
}
