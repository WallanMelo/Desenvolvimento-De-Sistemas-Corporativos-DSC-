package com.mycompany.telalogin.dao;

import com.mycompany.telalogin.Conexao;
import com.mycompany.telalogin.model.Cliente;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public void inserir(Cliente c) throws SQLException {
        String sql = "INSERT INTO cliente (nome, cpf, cnh, email, telefone, data_nascimento, endereco) "
                   + "VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, c.getNome());
            st.setString(2, c.getCpf());
            st.setString(3, c.getCnh());
            st.setString(4, c.getEmail());
            st.setString(5, c.getTelefone());
            if (c.getDataNascimento() == null) st.setNull(6, Types.DATE);
            else st.setDate(6, Date.valueOf(c.getDataNascimento()));
            st.setString(7, c.getEndereco());
            st.executeUpdate();
        }
    }

    public List<Cliente> listar(String termo) throws SQLException {
        String sql = "SELECT * FROM cliente "
                   + "WHERE (? IS NULL OR nome LIKE ? OR cpf LIKE ? OR cnh LIKE ?) "
                   + "ORDER BY nome";
        List<Cliente> lista = new ArrayList<>();
        try (Connection conn = Conexao.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
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
                    lista.add(map(rs));
                }
            }
        }
        return lista;
    }

    public Cliente buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE id=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) return map(rs);
                return null;
            }
        }
    }

    public void atualizar(Cliente c) throws SQLException {
        String sql = "UPDATE cliente SET nome=?, cpf=?, cnh=?, email=?, telefone=?, data_nascimento=?, endereco=? "
                   + "WHERE id=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, c.getNome());
            st.setString(2, c.getCpf());
            st.setString(3, c.getCnh());
            st.setString(4, c.getEmail());
            st.setString(5, c.getTelefone());
            if (c.getDataNascimento() == null) st.setNull(6, Types.DATE);
            else st.setDate(6, Date.valueOf(c.getDataNascimento()));
            st.setString(7, c.getEndereco());
            st.setInt(8, c.getId());
            st.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM cliente WHERE id=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
        }
    }

    private Cliente map(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setId(rs.getInt("id"));
        c.setNome(rs.getString("nome"));
        c.setCpf(rs.getString("cpf"));
        c.setCnh(rs.getString("cnh"));
        c.setEmail(rs.getString("email"));
        c.setTelefone(rs.getString("telefone"));
        Date dn = rs.getDate("data_nascimento");
        c.setDataNascimento(dn == null ? null : dn.toLocalDate());
        c.setEndereco(rs.getString("endereco"));
        return c;
    }
}
