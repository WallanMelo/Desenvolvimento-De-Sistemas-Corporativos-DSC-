package com.mycompany.telalogin;

import com.mycompany.telalogin.dao.AluguelDAO;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class TelaRegistrarDevolucao extends JPanel {
    private final JComboBox<Item> cbAluguel = new JComboBox<>();
    private final JTextField txtDataFim = new JTextField(LocalDate.now().toString());
    private final JTextField txtMulta = new JTextField("0.00");

    public TelaRegistrarDevolucao() {
        setLayout(new GridLayout(5, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        add(new JLabel("Aluguel em aberto *:")); add(cbAluguel);
        add(new JLabel("Data devolução (YYYY-MM-DD) *:")); add(txtDataFim);
        add(new JLabel("Multa (R$):")); add(txtMulta);

        JButton btn = new JButton("Registrar Devolução");
        btn.addActionListener(e -> devolver());
        add(btn);
        add(new JLabel());

        carregarAlugueisAbertos();
    }

    private void carregarAlugueisAbertos() {
        cbAluguel.removeAllItems();
        try {
            List<Object[]> lista = new AluguelDAO().listarAbertos();
            for (Object[] row : lista) {
                int aluguelId = (Integer) row[0];
                int veiculoId = (Integer) row[1];
                String label = "#" + aluguelId + " - " + row[2] + " (" + row[3] + ") - " + row[4] + " [" + row[5] + "]";
                cbAluguel.addItem(new Item(aluguelId, veiculoId, label));
            }
        } catch (Exception ex) {
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

        try {
            new AluguelDAO().registrarDevolucao(
                    item.aluguelId,
                    item.veiculoId,
                    LocalDate.parse(fim),
                    new BigDecimal(txtMulta.getText().trim())
            );
            JOptionPane.showMessageDialog(this, "Devolução registrada!");
            carregarAlugueisAbertos();
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
