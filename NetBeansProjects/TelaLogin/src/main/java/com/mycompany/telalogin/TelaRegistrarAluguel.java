package com.mycompany.telalogin;

import com.mycompany.telalogin.dao.AluguelDAO;
import com.mycompany.telalogin.dao.VeiculoDAO;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TelaRegistrarAluguel extends JPanel {
    private final JComboBox<Item> cbCliente = new JComboBox<>();
    private final JComboBox<Item> cbVeiculo = new JComboBox<>();
    private final JTextField txtInicio = new JTextField(LocalDate.now().toString());
    private final JTextField txtPrazo = new JTextField(); // prazo de devolução
    private final JTextField txtDetalhes = new JTextField();
    private final JTextField txtValor = new JTextField("0.00");

    public TelaRegistrarAluguel() {
        setLayout(new GridLayout(8, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        add(new JLabel("Cliente *:")); add(cbCliente);
        add(new JLabel("Veículo (Disponível) *:")); add(cbVeiculo);
        add(new JLabel("Data início (YYYY-MM-DD) *:")); add(txtInicio);
        add(new JLabel("Prazo devolução (YYYY-MM-DD) *:")); add(txtPrazo);
        add(new JLabel("Detalhes:")); add(txtDetalhes);
        add(new JLabel("Valor (R$):")); add(txtValor);

        JButton btn = new JButton("Registrar Aluguel");
        btn.addActionListener(e -> salvar());
        add(btn);
        add(new JLabel());

        carregarClientes();
        carregarVeiculosDisponiveis();
    }

    private void carregarClientes() {
        cbCliente.removeAllItems();
        String sql = "SELECT id, nome, cpf FROM cliente ORDER BY nome";
        try (java.sql.Connection c = Conexao.getConnection();
             java.sql.PreparedStatement st = c.prepareStatement(sql);
             java.sql.ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                cbCliente.addItem(new Item(rs.getInt("id"),
                        rs.getString("nome") + " - " + rs.getString("cpf")));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro clientes: " + ex.getMessage());
        }
    }

    private void carregarVeiculosDisponiveis() {
        cbVeiculo.removeAllItems();
        try {
            List<Object[]> lista = new VeiculoDAO().listarDisponiveis();
            for (Object[] v : lista) {
                int id = (Integer) v[0];
                String label = v[1] + " - " + v[2];
                cbVeiculo.addItem(new Item(id, label));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro veículos: " + ex.getMessage());
        }
    }

    private void salvar() {
        Item cli = (Item) cbCliente.getSelectedItem();
        Item vei = (Item) cbVeiculo.getSelectedItem();
        String ini = txtInicio.getText().trim();
        String prazo = txtPrazo.getText().trim();

        if (cli == null || vei == null || ini.isEmpty() || prazo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha Cliente, Veículo, Data início e Prazo.");
            return;
        }

        try {
            new AluguelDAO().registrarAluguel(
                    cli.id, vei.id,
                    LocalDate.parse(ini),
                    LocalDate.parse(prazo),
                    txtDetalhes.getText().trim(),
                    new BigDecimal(txtValor.getText().trim())
            );
            JOptionPane.showMessageDialog(this, "Aluguel registrado!");
            carregarVeiculosDisponiveis();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao registrar: " + ex.getMessage());
        }
    }

    private static class Item {
        final int id; final String label;
        Item(int id, String label) { this.id = id; this.label = label; }
        public String toString() { return label; }
    }
}
