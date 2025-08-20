package com.mycompany.telalogin.dao;

import com.mycompany.telalogin.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat; 
import java.util.Date; 


public class MecanicoDAO {
    // funct q RETORNA a qtd de MANUTENÇÔES q estão com STSTUS PENDENTES
    public static int getQuantidadeManutencoesPendentes() {
        String sql = "SELECT COUNT(*) FROM manutencoes WHERE status = 'Pendente'";
        try (Connection conexao = Conexao.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter quantidade de manutenções pendentes: " + e.getMessage());
        }
        return 0;
    }
    // funct q RETORNA a qtd de solicitaçẽos de peças q estão em analise
    public static int getSolicitacoesEmAnalise() {
        String sql = "SELECT COUNT(*) FROM solicitacoes_pecas WHERE status = 'Em_Analise'";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery()) {
                 
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter quantidade de solicitações em análise: " + e.getMessage());
        }
        return 0;
    }
    
    //function p listar manutenções apenas as pendentes
    public static List<Object[]> listarManutencoesPendentes() {
        List<Object[]> resultados = new ArrayList<>();
        String sql = """
            SELECT v.modelo, v.placa, m.data_solicitacao, m.descricao, m.status
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
                    rs.getString("status")
                };
                resultados.add(linha);
            }
        } catch (SQLException e) {//MSGs de ERRO
            System.err.println("Erro ao listar manutenções pendentes: " + e.getMessage());
        }
        return resultados;
    }

    //funcn p poder listar todas as manu ativas(pendentes e em andamento)
    public static List<Object[]> listarManutencoesAtivas() {
        List<Object[]> resultados = new ArrayList<>();
        String sql = """
            SELECT v.modelo, v.placa, m.data_solicitacao, m.descricao, m.status
            FROM manutencoes m
            JOIN veiculo v ON v.id = m.veiculo_id
            WHERE m.status IN ('Pendente', 'Em_Andamento')
            ORDER BY m.status, m.data_solicitacao
        """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] linha = {
                    rs.getString("modelo"),
                    rs.getString("placa"),
                    rs.getString("data_solicitacao"),
                    rs.getString("descricao"), //BO do Veiculo
                    rs.getString("status") // Em andamento ou Pendente
                };
                resultados.add(linha);
            }
        } catch (SQLException e) {//MSGs de ERRO
            System.err.println("Erro ao listar manutenções ativas: " + e.getMessage());
        }
        return resultados;
    }

    // Funcioton p solicitar uma manutenção p algum veicu, então se cria um reg no BD e altera o status do veic
    public static boolean solicitarManutencao(String placa, String problema, String nomeUsuario) {
        // Encontra o ID do veículo pela placa
        String sqlVeiculoId = "SELECT id FROM veiculo WHERE placa = ?";
        String veiculoId = null;
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlVeiculoId)) {
            stmt.setString(1, placa);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    veiculoId = rs.getString("id");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter o ID do veículo: " + e.getMessage());
            return false;
        }

        if (veiculoId == null) {
            System.err.println("Veículo não encontrado com a placa: " + placa);
            return false;
        }
        
        //encontro o ID do USER atraves do seu LOGIN
        String sqlUsuarioId = "SELECT id FROM usuario WHERE login = ?";
        String usuarioId = null;
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlUsuarioId)) {
            stmt.setString(1, nomeUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuarioId = rs.getString("id");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter o ID do usuário: " + e.getMessage());
            return false;
        }

        if (usuarioId == null) {
            System.err.println("Usuário não encontrado com o nome: " + nomeUsuario);
            return false;
        }

        //add o USER como compo no BD
        String sqlInsertManutencao = """
            INSERT INTO manutencoes (veiculo_id, data_solicitacao, descricao, status, usuario_id)
            VALUES (?, CURDATE(), ?, 'Pendente', ?)
        """;
        //att o STATUS
        String sqlUpdateVeiculo = "UPDATE veiculo SET status = 'Manutenção' WHERE placa = ?";

        try (Connection conn = Conexao.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsertManutencao);
                 PreparedStatement stmtUpdateVeiculo = conn.prepareStatement(sqlUpdateVeiculo)) {

                // inserindo a manu no BD
                stmtInsert.setString(1, veiculoId);
                stmtInsert.setString(2, problema);
                stmtInsert.setString(3, usuarioId); // NOVO: Define o usuario_id
                int linhasManutencao = stmtInsert.executeUpdate();

                // Atualiza o status do veículo p -> manutenção
                stmtUpdateVeiculo.setString(1, placa);
                int linhasVeiculo = stmtUpdateVeiculo.executeUpdate();

                if (linhasManutencao > 0 && linhasVeiculo > 0) {
                    conn.commit();
                    return true;
                } else {
                    conn.rollback();
                    return false;
                }
            }
        } catch (SQLException e) {//MSGs de ERRO
            System.err.println("Erro ao solicitar manutenção: " + e.getMessage());
            return false;
        }
    }
    
    
    //Functionn p iniciar uma manuentção, após clicar no botão de INICIAR ela atualiza o status do veiculo lá no BD informa qm foi o MECANICO A ASSUMIR A MANUTENC
    public static boolean assumirManutencao(String placa, String nomeMecanico) {
        String sql = """
            UPDATE manutencoes m
            JOIN veiculo v ON v.id = m.veiculo_id
            SET m.status = 'Em_Andamento'
            WHERE v.placa = ? AND m.status = 'Pendente'
        """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, placa);
            
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
            
        } catch (SQLException e) {//MSGs de ERRO
            System.err.println("Erro ao assumir manutenção: " + e.getMessage());
            return false;
        }
    }

    //Functionn p clonluir uma manuentção, após clicar no botão de concluir ela atualiza o status do veiculo e atualiza a data de conclusão lá no BD para a data atual e return TRUE se a OP for sucess
    public static boolean concluirManutencao(String placa) {
        String sqlManutencao = """
            UPDATE manutencoes m
            JOIN veiculo v ON v.id = m.veiculo_id
            SET m.status = 'Finalizada', m.data_conclusao = CURDATE()
            WHERE v.placa = ? AND m.status = 'Em_Andamento'
        """;

        String sqlVeiculo = "UPDATE veiculo SET status = 'Disponível' WHERE placa = ?";

        try (Connection conn = Conexao.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtManutencao = conn.prepareStatement(sqlManutencao);
                 PreparedStatement stmtVeiculo = conn.prepareStatement(sqlVeiculo)) {
                
                stmtManutencao.setString(1, placa);
                int linhasManutencao = stmtManutencao.executeUpdate();

                stmtVeiculo.setString(1, placa);
                int linhasVeiculo = stmtVeiculo.executeUpdate();

                if (linhasManutencao > 0 && linhasVeiculo > 0) {
                    conn.commit();
                    return true;
                } else {
                    conn.rollback();
                    return false;
                }
            }
        } catch (SQLException e) {//MSGs de ERRO
            System.err.println("Erro ao concluir manutenção: " + e.getMessage());
            return false;
        }
    }
    
    //funct p LISTAR todas as MANU c STATUS de FINALIZADAS/CONCLUIDAS
    public static List<Object[]> listarManutencoesFinalizadas(String filtro) {
        List<Object[]> resultados = new ArrayList<>();
        String sql = """
            SELECT v.modelo, v.placa, m.data_solicitacao, m.data_conclusao, m.descricao, m.status
            FROM manutencoes m
            JOIN veiculo v ON v.id = m.veiculo_id
            WHERE m.status = 'Finalizada'
            AND (v.placa LIKE ? OR m.data_solicitacao LIKE ?)
            ORDER BY m.data_conclusao DESC
        """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + filtro + "%");
            stmt.setString(2, "%" + filtro + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Object[] linha = {
                        rs.getString("modelo"),
                        rs.getString("placa"),
                        rs.getString("data_solicitacao"),
                        rs.getString("data_conclusao"),
                        rs.getString("descricao"),
                        rs.getString("status")
                    };
                    resultados.add(linha);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar histórico de manutenções: " + e.getMessage());
        }
        return resultados;
    }
    
    //funct p SOLICICTAR PEÇAS e INSERIR a SOLICITATION no BD
    public static boolean solicitarPecas(int usuarioId, String nomePeca, int quantidade, String dataDesejadaStr, String justificativa, String placa) {
        Connection conn = null;
        PreparedStatement stmtInsert = null;
        PreparedStatement stmtVeiculoId = null;
        ResultSet rs = null;
        java.sql.Date dataSQL = null;

        try {
            //A data vem no format(dd/mm/yyyy) convertemos p  (yyyy/mm/dd) q é o melhor p o BD
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy");
            Date data = formatoEntrada.parse(dataDesejadaStr);
            dataSQL = new java.sql.Date(data.getTime());
        } catch (ParseException e) {
            System.err.println("Erro ao converter a data: " + e.getMessage());
            return false; // return FALSE se ñ conseguir CONVERTER a DATA
        }

        try {
            conn = Conexao.getConnection();
            
            //Busca o ID do carro pela chave UNIQUE -> PLACA
            String sqlVeiculoId = "SELECT id FROM veiculo WHERE placa = ?";
            int veiculoId = -1;
            stmtVeiculoId = conn.prepareStatement(sqlVeiculoId);
            stmtVeiculoId.setString(1, placa);
            
            rs = stmtVeiculoId.executeQuery();
            
            if (rs.next()) {
                veiculoId = rs.getInt("id");
            } else {
                System.err.println("Veículo não encontrado com a placa: " + placa);
                return false;
            }

            //armazena a SOLICITAÇÃO da PECA no BD
            String sqlInsert = "INSERT INTO solicitacoes_pecas (usuario_id, nome_peca, quantidade, data_solicitacao, justificativa, veiculo_id, status) VALUES (?, ?, ?, ?, ?, ?, 'Em_Analise')";
            stmtInsert = conn.prepareStatement(sqlInsert);
            stmtInsert.setInt(1, usuarioId);
            stmtInsert.setString(2, nomePeca);
            stmtInsert.setInt(3, quantidade);
            stmtInsert.setDate(4, dataSQL);
            stmtInsert.setString(5, justificativa);
            stmtInsert.setInt(6, veiculoId);
            
            int linhasAfetadas = stmtInsert.executeUpdate();
            
            return linhasAfetadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao enviar solicitação de peças: " + e.getMessage());
            return false;
        } finally {
            // Fechando os recursos
            try {
                if (rs != null) rs.close();
                if (stmtInsert != null) stmtInsert.close();
                if (stmtVeiculoId != null) stmtVeiculoId.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }
 
    //funct p OBTER o ID do USER atraves do seu LOGIN
    public static int getUsuarioIdPorNome(String nomeUsuario) {
        String sql = "SELECT id FROM usuario WHERE login = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter o ID do usuário: " + e.getMessage());
        }
        return -1;
    }
    
    //funct p LISTAR tds SOLICITAÇOES POR STATUS(recebe o status como parametro)
    public static List<Object[]> listarSolicitacoesPecasPorStatus(String status) {
        List<Object[]> resultados = new ArrayList<>();
        String sql = """
            SELECT
                sp.id,
                sp.nome_peca,
                sp.quantidade,
                sp.data_solicitacao,
                v.placa,
                sp.justificativa,
                sp.status,
                u.login AS nome_usuario
            FROM solicitacoes_pecas sp
            JOIN veiculo v ON sp.veiculo_id = v.id
            JOIN usuario u ON sp.usuario_id = u.id
            WHERE sp.status = ?
            ORDER BY sp.data_solicitacao DESC
        """;
        
        //caso o STATUS seja TODOS temos q mudar a QUERRY p q SELECIONE TUDO e ñ FILTRE ND
        if (status.equals("Todos")) {
            sql = """
                SELECT
                    sp.id,
                    sp.nome_peca,
                    sp.quantidade,
                    sp.data_solicitacao,
                    v.placa,
                    sp.justificativa,
                    sp.status,
                    u.login AS nome_usuario
                FROM solicitacoes_pecas sp
                JOIN veiculo v ON sp.veiculo_id = v.id
                JOIN usuario u ON sp.usuario_id = u.id
                ORDER BY sp.data_solicitacao DESC
            """;
        }

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Apenas define o parâmetro se o status não for "Todos"
            if (!status.equals("Todos")) {
                stmt.setString(1, status);
            }
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Object[] linha = {
                        rs.getInt("id"),
                        rs.getString("nome_peca"),
                        rs.getInt("quantidade"),
                        rs.getString("placa"),
                        rs.getString("data_solicitacao"),
                        rs.getString("status"),
                        rs.getString("nome_usuario"),
                        rs.getString("justificativa")
                    };
                    resultados.add(linha);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar solicitações de peças: " + e.getMessage());
        }
        return resultados;
    }
    
    //funct p ATUALIZAR o STATUS de UMA SOLICITAÇÂO DE PEÇA por ex de Em - Analise p -> RECUSED
    public static boolean atualizarStatusSolicitacaoPeca(int solicitacaoId, String novoStatus) {
        String sql = "UPDATE solicitacoes_pecas SET status = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novoStatus);
            stmt.setInt(2, solicitacaoId);
            
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar o status da solicitação de peça: " + e.getMessage());
            return false;
        }
    }
    
    //funct q RETURN o TIPO do USER atraves do seu NAME
    public static String getTipoUsuarioPorNome(String nomeUsuario) {
        String sql = "SELECT nivel FROM usuario WHERE login = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nivel");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter o tipo de usuário: " + e.getMessage());
        }
        return null;
    }
}
