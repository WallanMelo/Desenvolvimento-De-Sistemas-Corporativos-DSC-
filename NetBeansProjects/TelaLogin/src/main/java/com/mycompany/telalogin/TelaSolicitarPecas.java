package com.mycompany.telalogin;

import com.mycompany.telalogin.dao.MecanicoDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class TelaSolicitarPecas extends JPanel {

    private JTextField campoNomePeca;
    private JSpinner campoQuantidade;
    private JTextField campoDataDesejada;
    private JTextArea campoJustificativa;
    private JTextField campoPlaca;
    private JButton botaoEnviar;
    private int usuarioId; 
    
    public TelaSolicitarPecas(int usuarioId) {
        this.usuarioId = usuarioId;
        
        setLayout(new GridBagLayout());
        setBackground(new Color(230, 230, 230));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel titulo = new JLabel("Solicitar Peças");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titulo, gbc);

        //==== Nome da Peça =======================================================
        JLabel labelNomePeca = new JLabel("<html><font color='red'><b>*</b></font> Nome da Peça:</html>");
        labelNomePeca.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(labelNomePeca, gbc);

        campoNomePeca = new JTextField(20);
        campoNomePeca.setPreferredSize(new Dimension(250, 30));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(campoNomePeca, gbc);

        //==== QTD ==============================================================
        JLabel labelQuantidade = new JLabel("<html><font color='red'><b>*</b></font> Quantidade:</html>");
        labelQuantidade.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(labelQuantidade, gbc);

        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 100, 1);
        campoQuantidade = new JSpinner(model);
        campoQuantidade.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(campoQuantidade, gbc);
        
        //==== Data Desejada do Pedido =======================================================
        JLabel labelDataDesejada = new JLabel("<html><font color='red'><b>*</b></font> Data Desejada:</html>");
        labelDataDesejada.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(labelDataDesejada, gbc);
        
        campoDataDesejada = new JTextField(10);
        campoDataDesejada.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(campoDataDesejada, gbc);

        //==== justificativa ============================================================
        JLabel labelJustificativa = new JLabel("<html><font color='red'><b>*</b></font> Justificativa:</html>");
        labelJustificativa.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        add(labelJustificativa, gbc);

        campoJustificativa = new JTextArea(5, 20);
        campoJustificativa.setLineWrap(true);
        campoJustificativa.setWrapStyleWord(true);
        JScrollPane scrollJustificativa = new JScrollPane(campoJustificativa);
        scrollJustificativa.setPreferredSize(new Dimension(250, 100));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(scrollJustificativa, gbc);
        
        //==== Placa associada ao Pedido ===========================================
        JLabel labelPlaca = new JLabel("<html><font color='red'><b>*</b></font> Placa associada a Peça:</html>");
        labelPlaca.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(labelPlaca, gbc);

        campoPlaca = new JTextField(20);
        campoPlaca.setPreferredSize(new Dimension(250, 30));
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(campoPlaca, gbc);

        //==== BUTTON de enviar o pedido =========================================
        botaoEnviar = new JButton("Enviar Solicitação");
        botaoEnviar.setFont(new Font("Arial", Font.BOLD, 14));
        botaoEnviar.setBackground(new Color(50, 50, 50));
        botaoEnviar.setForeground(Color.BLACK);
        botaoEnviar.setFocusPainted(false);
        botaoEnviar.setPreferredSize(new Dimension(180, 40));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(botaoEnviar, gbc);
        
        botaoEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarSolicitacao();
            }
        });
    }
    
    //funct q envia para  o DataAcces do oBJ a solicitação feita pelo USER
    private void enviarSolicitacao() {
        String nomePeca = campoNomePeca.getText();
        int quantidade = (Integer) campoQuantidade.getValue();
        String dataDesejadaStr = campoDataDesejada.getText();
        String justificativa = campoJustificativa.getText();
        String placa = campoPlaca.getText();

        if (nomePeca.isEmpty() || justificativa.isEmpty() || placa.isEmpty() || dataDesejadaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos marcados com * são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (MecanicoDAO.solicitarPecas(usuarioId, nomePeca, quantidade, dataDesejadaStr, justificativa, placa)) {
            JOptionPane.showMessageDialog(this, "Solicitação de peças enviada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            campoNomePeca.setText("");
            campoQuantidade.setValue(1);
            campoDataDesejada.setText("");
            campoJustificativa.setText("");
            campoPlaca.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao enviar solicitação. Verifique se a placa do veículo está correta.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
