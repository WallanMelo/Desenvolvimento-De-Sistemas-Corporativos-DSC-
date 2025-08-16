/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.dsc;
 
/**
 *
 * @author clebson
 */
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.mycompany.dsc.interfaces.login.TelaLogin;

public class Main extends JFrame {

    // Componentes da interface
    private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private JComboBox<String> comboNivelAcesso;
    private JButton botaoEntrar;

    // Método main para testar a tela
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaLogin login = new TelaLogin();
            login.setVisible(true);
        });
    }
}

