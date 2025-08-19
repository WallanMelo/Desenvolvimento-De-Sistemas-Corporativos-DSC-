package com.mycompany.telalogin;

import com.mycompany.telalogin.dao.ClienteDAO;
import com.mycompany.telalogin.model.Cliente;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class TelaCadastrarCliente extends JPanel {
    private final JTextField txtNome = new JTextField();
    private final JTextField txtCpf = new JTextField();
    private final JTextField txtCnh = new JTextField();
    private final JTextField txtEmail = new JTextField();
    private final JTextField txtTelefone = new JTextField();
    private final JTextField txtDataNasc = new JTextField(); // YYYY-MM-DD
    private final JTextField txtEndereco = new JTextField();

    public TelaCadastrarCliente() {
        setLayout(new GridLayout(9, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(new JLabel("Nome *:")); add(txtNome);
        add(new JLabel("CPF *:")); add(txtCpf);
        add(new JLabel("CNH *:")); add(txtCnh);
        add(new JLabel("Email *:")); add(txtEmail);
        add(new JLabel("Telefone:")); add(txtTelefone);
        add(new JLabel("Data Nasc. (YYYY-MM-DD):")); add(txtDataNasc);
        add(new JLabel("Endereço:")); add(txtEndereco);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvar());
        add(btnSalvar);

        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.addActionListener(e -> limpar());
        add(btnLimpar);
    }

    private void salvar() {
        try {
            String nome = txtNome.getText().trim();
            String cpf  = txtCpf.getText().trim();
            String cnh  = txtCnh.getText().trim();
            String email = txtEmail.getText().trim();

            if (nome.isEmpty() || cpf.isEmpty() || cnh.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome, CPF, CNH e Email são obrigatórios.");
                return;
            }

            Cliente c = new Cliente();
            c.setNome(nome);
            c.setCpf(cpf);
            c.setCnh(cnh);
            c.setEmail(email);
            c.setTelefone(txtTelefone.getText().trim());
            String dn = txtDataNasc.getText().trim();
            c.setDataNascimento(dn.isEmpty() ? null : LocalDate.parse(dn));
            c.setEndereco(txtEndereco.getText().trim());

            new ClienteDAO().inserir(c);
            JOptionPane.showMessageDialog(this, "Cliente cadastrado!");
            limpar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
        }
    }

    private void limpar() {
        txtNome.setText(""); txtCpf.setText(""); txtCnh.setText("");
        txtEmail.setText(""); txtTelefone.setText(""); txtDataNasc.setText(""); txtEndereco.setText("");
    }
}
