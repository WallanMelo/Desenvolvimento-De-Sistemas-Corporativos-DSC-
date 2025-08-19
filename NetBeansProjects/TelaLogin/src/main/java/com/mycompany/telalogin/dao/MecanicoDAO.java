package com.mycompany.telalogin.dao;
import com.mycompany.telalogin.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public class MecanicoDAO {

    //Busca no BD a qtd de manutenções pendentes na table    
    public static int getQuantidadeManutencoesPendentes() {
        String sql = "SELECT COUNT(*) FROM manutencoes WHERE status = 'Pendente'";//FINALIZADA - PENDENTE - EM ANDAMENTO
         try (Connection conexao = Conexao.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            //e.printStackTrace();Log para debug
        }
        return 0;
    }

    public static int getSolicitacoesEmAnalise() {
        String sql = "SELECT COUNT(*) FROM solicitacoes_pecas WHERE status = 'Em_Analise'";// CONCLUIDA - EM AMDAMENTO - EM ANALISE
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery()) {
            
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            //e.printStackTrace();Log para debug
        }
        return 0;
    }
    
    public static List<Object[]> listarManutencoesPendentes() {
        List<Object[]> resultados = new ArrayList<>();
        String sql = """
            SELECT v.modelo, v.placa, m.data_solicitacao, m.descricao
            FROM manutencoes m
            JOIN veiculo v ON v.id = m.veiculo_id
            WHERE m.status = 'Pendente'
        """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] linha = {
                    rs.getString("modelo"),
                    rs.getString("placa"),
                    rs.getString("data_solicitacao"),
                    rs.getString("descricao"),
                };
                resultados.add(linha);
            }

        } catch (Exception e) {
            //e.printStackTrace();
        }
        return resultados;
    }
}