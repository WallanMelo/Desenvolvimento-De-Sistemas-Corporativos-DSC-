package com.mycompany.telalogin.dao;

import com.mycompany.telalogin.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO {

    public List<Object[]> listar(String termo) throws SQLException {
        String sql = "SELECT id, modelo, placa, status, tipo FROM veiculo "
                   + "WHERE (? IS NULL OR placa LIKE ? OR modelo LIKE ? OR tipo LIKE ?) "
                   + "ORDER BY modelo";
        List<Object[]> lista = new ArrayList<>();
        try (Connection c = Conexao.getConnection();
             PreparedStatement st = c.prepareStatement(sql)) {
            String like = (termo == null || termo.trim().isEmpty()) ? null : "%" + termo.trim() + "%";
            if (like == null) {
                st.setNull(1, Types.VARCHAR);
                st.setNull(2, Types.VARCHAR);
                st.setNull(3, Types.VARCHAR);
                st.setNull(4, Types.VARCHAR);
            } else {
                st.setString(1, like);
                st.setString(2, like);
                st.setString(3, like);
                st.setString(4, like);
            }
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Object[]{
                        rs.getInt("id"),
                        rs.getString("modelo"),
                        rs.getString("placa"),
                        rs.getString("status"),
                        rs.getString("tipo")
                    });
                }
            }
        }
        return lista;
    }

    public List<Object[]> listarDisponiveis() throws SQLException {
        String sql = "SELECT id, modelo, placa FROM veiculo WHERE status='Disponível' ORDER BY modelo";
        List<Object[]> lista = new ArrayList<>();
        try (Connection c = Conexao.getConnection();
             PreparedStatement st = c.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                lista.add(new Object[]{ rs.getInt("id"), rs.getString("modelo"), rs.getString("placa") });
            }
        }
        return lista;
    }

    public void atualizarStatus(int veiculoId, String novoStatus) throws SQLException {
        if (!"Disponível".equals(novoStatus) && !"Alugado".equals(novoStatus) && !"Manutenção".equals(novoStatus)) {
            throw new IllegalArgumentException("Status inválido: " + novoStatus);
        }
        String sql = "UPDATE veiculo SET status=? WHERE id=?";
        try (Connection c = Conexao.getConnection();
             PreparedStatement st = c.prepareStatement(sql)) {
            st.setString(1, novoStatus);
            st.setInt(2, veiculoId);
            st.executeUpdate();
        }
    }
}
