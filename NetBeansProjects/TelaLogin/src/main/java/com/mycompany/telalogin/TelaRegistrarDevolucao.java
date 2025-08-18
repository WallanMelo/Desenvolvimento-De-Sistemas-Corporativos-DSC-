package com.mycompany.telalogin;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;

public class TelaRegistrarDevolucao extends JPanel {
    private final JComboBox<Item> cbAluguel = new JComboBox<>();
    private final JTextField txtDataFim = new JTextField(LocalDate.now().toString());

    public TelaRegistrarDevolucao() {
        setLayout(new GridLayout(4, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        add(new JLabel("Aluguel em aberto *:")); add(cbAluguel);
        add(new JLabel("Data devolução (YYYY-MM-DD) *:")); add(txtDataFim);

        JButton btn = new JButton("Registrar Devolução");
        btn.addActionListener(e -> devolver());
        add(btn);
        add(new JLabel());

        carregarAlugueisAbertos();
    }
//aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
    private void carregarAlugueisAbertos() {
        cbAluguel.removeAllItems();
        String sql = """
            SELECT a.id AS aluguel_id, c.nome, c.cpf, v.modelo, v.placa, a.veiculo_id
            FROM aluguel a
            JOIN cliente c ON c.id=a.cliente_id
            JOIN veiculo v ON v.id=a.veiculo_id
            WHERE a.devolvido=FALSE
            ORDER BY a.id
            """;
        try (Connection c = Conexao.getConnection();
             PreparedStatement st = c.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("aluguel_id");
                int veiculoId = rs.getInt("veiculo_id");
                String label = String.format("#%d - %s (%s) - %s [%s]",
                        id, rs.getString("nome"), rs.getString("cpf"),
                        rs.getString("modelo"), rs.getString("placa"));
                cbAluguel.addItem(new Item(id, veiculoId, label));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar aluguéis: " + ex.getMessage());
        }
    }

    private void devolver() {
        Item item = (Item) cbAluguel.getSelectedItem();
        if (item == null) {
            JOptionPane.showMessageDialog(this, "Selecione um aluguel.");
            return;
        }
        String fim = txtDataFim.getText().trim();
        if (fim.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe a data de devolução.");
            return;
        }

        try (Connection c = Conexao.getConnection()) {
            c.setAutoCommit(false);
            try {
                // marca aluguel como devolvido
                String upAlug = "UPDATE aluguel SET devolvido=TRUE, data_fim=? WHERE id=?";
                try (PreparedStatement st = c.prepareStatement(upAlug)) {
                    st.setDate(1, java.sql.Date.valueOf(fim));
                    st.setInt(2, item.aluguelId);
                    st.executeUpdate();
                }

                // volta status do veículo
                String upVei = "UPDATE veiculo SET status='Disponível' WHERE id=?";
                try (PreparedStatement st = c.prepareStatement(upVei)) {
                    st.setInt(1, item.veiculoId);
                    st.executeUpdate();
                }

                c.commit();
                JOptionPane.showMessageDialog(this, "Devolução registrada!");
                carregarAlugueisAbertos(); // atualisza lista
            } catch (Exception ex) {
                c.rollback();
                throw ex;
            } finally {
                c.setAutoCommit(true);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro na devolução: " + ex.getMessage());
        }
    }

    private static class Item {
        final int aluguelId;
        final int veiculoId;
        final String label;
        Item(int aluguelId, int veiculoId, String label) {
            this.aluguelId = aluguelId; this.veiculoId = veiculoId; this.label = label;
        }
        public String toString() { return label; }
    }
}
