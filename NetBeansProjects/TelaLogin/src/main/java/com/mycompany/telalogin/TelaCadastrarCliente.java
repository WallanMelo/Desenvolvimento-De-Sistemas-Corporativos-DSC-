package com.mycompany.telalogin;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TelaCadastrarCliente extends JPanel {
    private final JTextField txtNome = new JTextField();
    private final JTextField txtCpf = new JTextField();
    private final JTextField txtTelefone = new JTextField();
    private final JTextField txtEndereco = new JTextField();

    public TelaCadastrarCliente() {
        setLayout(new GridLayout(6, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(new JLabel("Nome *:"));      add(txtNome);
        add(new JLabel("CPF *:"));       add(txtCpf);
        add(new JLabel("Telefone:"));    add(txtTelefone);
        add(new JLabel("Endereço:"));    add(txtEndereco);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvar());
        add(btnSalvar);
        add(new JLabel()); // espaço
    }

    private void salvar() {
        String nome = txtNome.getText().trim();
        String cpf  = txtCpf.getText().trim();

        if (nome.isEmpty() || cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e CPF são obrigatórios.");
            return;
        }

        String sql = "INSERT INTO cliente(nome, cpf, telefone, endereco) VALUES (?,?,?,?)";
        try (Connection c = Conexao.getConnection();
             PreparedStatement st = c.prepareStatement(sql)) {
            st.setString(1, nome);
            st.setString(2, cpf);
            st.setString(3, txtTelefone.getText().trim());
            st.setString(4, txtEndereco.getText().trim());
            st.executeUpdate();
            JOptionPane.showMessageDialog(this, "Cliente cadastrado!");
            txtNome.setText(""); txtCpf.setText(""); txtTelefone.setText(""); txtEndereco.setText("");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
        }
    }
}
