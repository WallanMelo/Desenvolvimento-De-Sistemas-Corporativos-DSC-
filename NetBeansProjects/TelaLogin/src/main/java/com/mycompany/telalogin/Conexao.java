package com.mycompany.telalogin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/aluguel_veiculos?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "12345";

    static {
        try { Class.forName("com.mysql.cj.jdbc.Driver"); }
        catch (ClassNotFoundException e) { throw new RuntimeException("Driver MySQL não encontrado", e); }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
