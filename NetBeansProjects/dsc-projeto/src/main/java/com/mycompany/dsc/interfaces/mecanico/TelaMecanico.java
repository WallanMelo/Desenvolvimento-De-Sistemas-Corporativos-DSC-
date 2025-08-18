package com.mycompany.dsc.interfaces.mecanico;

import javax.swing.*;
import java.awt.*;
import com.mycompany.dsc.interfaces.login.TelaLogin;
import javax.swing.*;
import java.awt.*;

public class TelaMecanico extends JFrame {
    public TelaMecanico(String nomeUsuario) {
        setTitle("Painel do Mecânico - AVJ");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new JLabel("Tela do Mecânico (em construção)", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}
