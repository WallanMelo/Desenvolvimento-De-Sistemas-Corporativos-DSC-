/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.dsc; 
/**
 *
 * @author clebson
 */
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.mycompany.dsc.interfaces.login.TelaLogin;

public class Main extends JFrame {
    // Método main para testar a tela
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaLogin login = new TelaLogin();
            login.setVisible(true);
        });
    }
}

