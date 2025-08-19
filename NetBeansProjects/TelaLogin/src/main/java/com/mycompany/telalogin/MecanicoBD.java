package com.mycompany.telalogin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MecanicoBD {

    //Busca no BD a qtd de manutenções pendentes na table    
    public static int getQuantidadeManutencoesPendentes() {
        String sql = "SELECT COUNT(*) FROM manutencoes WHERE status = 'PENDENTE'";//FINALIZADA - PENDENTE - EM ANDAMENTO
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
        String sql = "SELECT COUNT(*) FROM solicitacoes_pecas WHERE status = 'EM_ANALISE'";// CONCLUIDA - EM AMDAMENTO - EM ANALISE
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
}
