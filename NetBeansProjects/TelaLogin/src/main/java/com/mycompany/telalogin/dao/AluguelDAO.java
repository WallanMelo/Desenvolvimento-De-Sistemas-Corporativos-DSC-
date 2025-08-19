package com.mycompany.telalogin.dao;

import com.mycompany.telalogin.Conexao;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AluguelDAO {

    public void registrarAluguel(int clienteId, int veiculoId, LocalDate dataInicio,
                                 LocalDate prazoDevolucao, String detalhes,
                                 BigDecimal valor) throws Exception {
        try (Connection c = Conexao.getConnection()) {
            c.setAutoCommit(false);
            try {
                String ins = "INSERT INTO aluguel (cliente_id, veiculo_id, data_inicio, prazo_devolucao, detalhes, valor, devolvido) "
                           + "VALUES (?,?,?,?,?,?,FALSE)";
                try (PreparedStatement st = c.prepareStatement(ins)) {
                    st.setInt(1, clienteId);
                    st.setInt(2, veiculoId);
                    st.setDate(3, Date.valueOf(dataInicio));
                    st.setDate(4, Date.valueOf(prazoDevolucao));
                    st.setString(5, detalhes);
                    st.setBigDecimal(6, valor == null ? BigDecimal.ZERO : valor);
                    st.executeUpdate();
                }
                String upd = "UPDATE veiculo SET status='Alugado' WHERE id=?";
                try (PreparedStatement st = c.prepareStatement(upd)) {
                    st.setInt(1, veiculoId);
                    st.executeUpdate();
                }
                c.commit();
            } catch (Exception ex) {
                c.rollback();
                throw ex;
            } finally {
                c.setAutoCommit(true);
            }
        }
    }

    public void registrarDevolucao(int aluguelId, int veiculoId,
                                   LocalDate dataFim, BigDecimal multa) throws Exception {
        try (Connection c = Conexao.getConnection()) {
            c.setAutoCommit(false);
            try {
                String upAlug = "UPDATE aluguel SET devolvido=TRUE, data_fim=?, multa=? WHERE id=?";
                try (PreparedStatement st = c.prepareStatement(upAlug)) {
                    st.setDate(1, Date.valueOf(dataFim));
                    st.setBigDecimal(2, multa == null ? BigDecimal.ZERO : multa);
                    st.setInt(3, aluguelId);
                    st.executeUpdate();
                }
                String upVei = "UPDATE veiculo SET status='Disponível' WHERE id=?";
                try (PreparedStatement st = c.prepareStatement(upVei)) {
                    st.setInt(1, veiculoId);
                    st.executeUpdate();
                }
                c.commit();
            } catch (Exception ex) {
                c.rollback();
                throw ex;
            } finally {
                c.setAutoCommit(true);
            }
        }
    }

    public List<Object[]> listarAbertos() throws SQLException {
        String sql = "SELECT a.id AS aluguel_id, a.veiculo_id, c.nome, c.cpf, v.modelo, v.placa "
                   + "FROM aluguel a "
                   + "JOIN cliente c ON c.id=a.cliente_id "
                   + "JOIN veiculo v ON v.id=a.veiculo_id "
                   + "WHERE a.devolvido=FALSE "
                   + "ORDER BY a.id";
        List<Object[]> lista = new ArrayList<>();
        try (Connection c = Conexao.getConnection();
             PreparedStatement st = c.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                lista.add(new Object[]{
                        rs.getInt("aluguel_id"),
                        rs.getInt("veiculo_id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("modelo"),
                        rs.getString("placa")
                });
            }
        }
        return lista;
    }
}
