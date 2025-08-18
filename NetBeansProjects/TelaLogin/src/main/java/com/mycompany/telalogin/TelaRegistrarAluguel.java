package com.mycompany.telalogin;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;

public class TelaRegistrarAluguel extends JPanel {
    private final JComboBox<Item> cbCliente = new JComboBox<>();
    private final JComboBox<Item> cbVeiculo = new JComboBox<>();
    private final JTextField txtInicio = new JTextField(LocalDate.now().toString());
    private final JTextField txtFim = new JTextField(); // opcional

    public TelaRegistrarAluguel() {
        setLayout(new GridLayout(6, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        add(new JLabel("Cliente *:")); add(cbCliente);
        add(new JLabel("Veículo (Disponível) *:")); add(cbVeiculo);
        add(new JLabel("Data início (YYYY-MM-DD) *:")); add(txtInicio);
        add(new JLabel("Data fim (YYYY-MM-DD):")); add(txtFim);

        JButton btnSalvar = new JButton("Registrar Aluguel");
        btnSalvar.addActionListener(e -> salvar());
        add(btnSalvar);
        add(new JLabel());

        carregarClientes();
        carregarVeiculosDisponiveis();
    }

    private void carregarClientes() {
        cbCliente.removeAllItems();
        String sql = "SELECT id, nome, cpf FROM cliente ORDER BY nome";
        try (Connection c = Conexao.getConnection();
             PreparedStatement st = c.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                cbCliente.addItem(new Item(
                        rs.getInt("id"),
                        rs.getString("nome") + " - " + rs.getString("cpf")));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro clientes: " + ex.getMessage());
        }
    }

    private void carregarVeiculosDisponiveis() {
        cbVeiculo.removeAllItems();
        String sql = "SELECT id, modelo, placa FROM veiculo WHERE status='Disponível' ORDER BY modelo";
        try (Connection c = Conexao.getConnection();
             PreparedStatement st = c.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                cbVeiculo.addItem(new Item(
                        rs.getInt("id"),
                        rs.getString("modelo") + " - " + rs.getString("placa")));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro veículos: " + ex.getMessage());
        }
    }

    private void salvar() {
        Item cli = (Item) cbCliente.getSelectedItem();
        Item vei = (Item) cbVeiculo.getSelectedItem();
        String ini = txtInicio.getText().trim();
        String fim = txtFim.getText().trim().isEmpty() ? null : txtFim.getText().trim();

        if (cli == null || vei == null || ini.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha Cliente, Veículo e Data início.");
            return;
        }

        try (Connection c = Conexao.getConnection()) {
            c.setAutoCommit(false);
            try {
                // cria aluguel
                String ins = "INSERT INTO aluguel (cliente_id, veiculo_id, data_inicio, data_fim, devolvido) VALUES (?,?,?,?,FALSE)";
                try (PreparedStatement st = c.prepareStatement(ins)) {
                    st.setInt(1, cli.id);
                    st.setInt(2, vei.id);
                    st.setDate(3, java.sql.Date.valueOf(ini));
                    if (fim == null) st.setNull(4, Types.DATE);
                    else st.setDate(4, java.sql.Date.valueOf(fim));
                    st.executeUpdate();
                }

                // muda status veículo
                String upd = "UPDATE veiculo SET status='Alugado' WHERE id=?";
                try (PreparedStatement st = c.prepareStatement(upd)) {
                    st.setInt(1, vei.id);
                    st.executeUpdate();
                }

                c.commit();
                JOptionPane.showMessageDialog(this, "Aluguel registrado!");
                carregarVeiculosDisponiveis(); // atualiza lista
            } catch (Exception ex) {
                c.rollback();
                throw ex;
            } finally {
                c.setAutoCommit(true);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao registrar: " + ex.getMessage());
        }
    }

    // aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
    private static class Item {
        final int id; final String label;
        Item(int id, String label) { this.id = id; this.label = label; }
        public String toString() { return label; }
    }
}
