package com.mycompany.telalogin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class TelaStatusFrota extends JPanel {
    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID","Modelo","Placa","Status"}, 0) {
        public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable tabela = new JTable(model);

    public TelaStatusFrota() {
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(12,12,12,12));

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(e -> carregar());

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topo.add(new JLabel("Status da Frota"));
        topo.add(btnAtualizar);

        add(topo, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        carregar();
    }

    private void carregar() {
        model.setRowCount(0);
        String sql = "SELECT id, modelo, placa, status FROM veiculo ORDER BY modelo";
        try (Connection c = Conexao.getConnection();
             PreparedStatement st = c.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("modelo"),
                        rs.getString("placa"),
                        rs.getString("status")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar: " + ex.getMessage());
        }
    }
}
