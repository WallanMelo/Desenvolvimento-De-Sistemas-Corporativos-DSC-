package com.mycompany.telalogin.dao;

import com.mycompany.telalogin.Conexao;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDAO {
    //funct p EXPORTAR os DAODS de um RELATÓRIO no FORMAT PDF 
    public static void exportarParaPDF(String tipoRelatorio, String caminhoArquivo, String filtro) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(caminhoArquivo));
            document.open();

            String titulo = "Relatório " + tipoRelatorio;
            if (filtro != null && !filtro.isEmpty()) {
                titulo += " - Filtro: " + filtro;
            }
            document.add(new Paragraph(titulo));
            document.add(new Paragraph("\n"));

            //recebe os DADOS do RELATÓRIO com base no TIPO de RELATÓRIOe  FILTRO
            List<Object[]> dados = obterDadosRelatorio(tipoRelatorio, filtro);

            //ADD ao DOCUMENT.PDF os DADOS do RELATORIO
            for (Object[] linha : dados) {
                StringBuilder linhaStr = new StringBuilder();
                for (Object celula : linha) {
                    if (celula != null) {
                        linhaStr.append(celula.toString()).append(" | ");
                    }
                }
                document.add(new Paragraph(linhaStr.toString()));
            }
               //MSG de EXPORT SUCESS
            System.out.println("Relatório PDF de " + tipoRelatorio + " exportado com sucesso para " + caminhoArquivo);

        } catch (Exception e) {
            //MSG de EXPORT FAILED
            e.printStackTrace();
            System.err.println("Erro ao exportar relatório para PDF: " + e.getMessage());
        } finally {
            if (document.isOpen()) {
                document.close();
            }
        }
    }

    //funct p EXPORTAR o RELATORIO no FORMAT de EXCEL o .xlxl
    public static void exportarParaExcel(String tipoRelatorio, String caminhoArquivo, String filtro) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Relatório " + tipoRelatorio);

            //recebe os DADOS do RELATÓRIO com base no TIPO de RELATÓRIOe  FILTRO
        List<Object[]> dados = obterDadosRelatorio(tipoRelatorio, filtro);

        if (!dados.isEmpty()) {
            XSSFRow headerRow = sheet.createRow(0);//CABEçALHO
            String[] colunas = getColunasRelatorio(tipoRelatorio, filtro);
            for (int i = 0; i < colunas.length; i++) {
                XSSFCell cell = headerRow.createCell(i);
                cell.setCellValue(colunas[i]);
            }

            //PREENCHE o DOCUMENT.xlxl 0/ os  DADOS
            for (int i = 0; i < dados.size(); i++) {
                XSSFRow row = sheet.createRow(i + 1);
                Object[] linha = dados.get(i);
                for (int j = 0; j < linha.length; j++) {
                    XSSFCell cell = row.createCell(j);
                    if (linha[j] != null) {
                        cell.setCellValue(linha[j].toString());
                    }
                }
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream(caminhoArquivo)) {
            workbook.write(outputStream);
            System.out.println("Relatório Excel de " + tipoRelatorio + " exportado com sucesso para " + caminhoArquivo);//MSG de SUCESS na EXPORTAÇÂO
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao exportar relatório para Excel: " + e.getMessage());//MSG de FAILED na EXPORTAÇÂO
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //funct RECEBE os DADOS do BD dos REALTORIOS c BASE no TIPO E FILTRO 
    public static List<Object[]> obterDadosRelatorio(String tipoRelatorio, String filtro) {
        List<Object[]> resultados = new ArrayList<>();
        String sql = "";
        String whereClause = "";

        switch (tipoRelatorio) {
            case "Financeiro":
                //FILTRO de STATUS por tipo DEVOLVIDO e NÂO DEVOLVIDO
                if (filtro != null && !filtro.isEmpty() && !filtro.equals("Todos")) {
                    if (filtro.equals("Devolvidos")) {
                        whereClause = " WHERE a.devolvido = TRUE";
                    } else if (filtro.equals("Não Devolvidos")) {
                        whereClause = " WHERE a.devolvido = FALSE";
                    }
                }
                
                sql = "SELECT a.id, c.nome as cliente, v.modelo as veiculo, " +
                      "a.data_inicio, a.prazo_devolucao, a.valor, a.multa, " +
                      "CASE WHEN a.devolvido THEN 'Devolvido' ELSE 'Não Devolvido' END as status " +
                      "FROM aluguel a " +
                      "JOIN cliente c ON a.cliente_id = c.id " +
                      "JOIN veiculo v ON a.veiculo_id = v.id" +
                      whereClause + " " +
                      "ORDER BY a.data_inicio DESC";
                break;
                
            case "Operacional":
                //FILTRO por   tipo MANUTENçÃO ou SOLICITACOES DE PECAS
                if (filtro != null && !filtro.isEmpty()) {
                    if (filtro.startsWith("Manutenções")) {
                        String statusFiltro = filtro.replace("Manutenções - ", "");
                        if (!statusFiltro.equals("Todos")) {
                            whereClause = " WHERE m.status = '" + statusFiltro.replace("_", " ") + "'";
                        }
                        sql = "SELECT v.placa, m.data_solicitacao, m.data_conclusao, m.descricao, " +
                              "u.nome as mecanico, m.status " +
                              "FROM manutencoes m " +
                              "JOIN veiculo v ON v.id = m.veiculo_id " +
                              "JOIN usuario u ON u.id = m.usuario_id" +
                              whereClause + " " +
                              "ORDER BY m.data_solicitacao DESC";
                    } else if (filtro.startsWith("Solicitações")) {
                        String statusFiltro = filtro.replace("Solicitações - ", "");
                        if (!statusFiltro.equals("Todos")) {
                            whereClause = " WHERE sp.status = '" + statusFiltro.replace("_", " ") + "'";
                        }
                        sql = "SELECT v.placa, sp.nome_peca, sp.quantidade, sp.data_solicitacao, " +
                              "u.nome as solicitante, sp.status, sp.justificativa " +
                              "FROM solicitacoes_pecas sp " +
                              "JOIN veiculo v ON v.id = sp.veiculo_id " +
                              "JOIN usuario u ON u.id = sp.usuario_id" +
                              whereClause + " " +
                              "ORDER BY sp.data_solicitacao DESC";
                    } else {
                        sql = "SELECT v.placa, m.data_solicitacao, m.data_conclusao, m.descricao, " +
                              "u.nome as mecanico, m.status " +
                              "FROM manutencoes m " +
                              "JOIN veiculo v ON v.id = m.veiculo_id " +
                              "JOIN usuario u ON u.id = m.usuario_id " +
                              "ORDER BY m.data_solicitacao DESC";
                    }
                } else {
                    sql = "SELECT v.placa, m.data_solicitacao, m.data_conclusao, m.descricao, " +
                          "u.nome as mecanico, m.status " +
                          "FROM manutencoes m " +
                          "JOIN veiculo v ON v.id = m.veiculo_id " +
                          "JOIN usuario u ON u.id = m.usuario_id " +
                          "ORDER BY m.data_solicitacao DESC";
                }
                break;
                
            case "Salários":
                //FILTRO po rNIVEL 
                if (filtro != null && !filtro.isEmpty() && !filtro.equals("Todos")) {
                    whereClause = " WHERE u.nivel = '" + filtro + "'";
                }
                
                sql = "SELECT u.nome, u.login, u.nivel, u.salario " +
                      "FROM usuario u" + whereClause + " " +
                      "ORDER BY u.nivel, u.nome";
                break;
                
            default:
                System.err.println("Tipo de relatório não suportado: " + tipoRelatorio);
                return resultados;
        }

        //primeiro checa se o SQL n está vazio
        if (sql == null || sql.trim().isEmpty()) {
            System.err.println("SQL está vazio para o relatório: " + tipoRelatorio + " com filtro: " + filtro);
            return resultados;
        }

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                Object[] linha = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    linha[i - 1] = rs.getObject(i);
                }
                resultados.add(linha);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao obter dados para o relatório: " + e.getMessage());
            e.printStackTrace();
        }
        return resultados;
    }

    //funct recebe o NOME das COLUNAS e passa p o CABEÇALHO
    private static String[] getColunasRelatorio(String tipoRelatorio, String filtro) {
        switch (tipoRelatorio) {
            case "Financeiro":
                return new String[]{"ID", "Cliente", "Veículo", "Data Início", "Prazo Devolução", "Valor", "Multa", "Status"};
            case "Operacional":
                if (filtro != null && filtro.startsWith("Solicitações")) {
                    return new String[]{"Placa", "Peça", "Quantidade", "Data Solicitação", "Solicitante", "Status", "Justificativa"};
                } else {
                    return new String[]{"Placa", "Data Solicitação", "Data Conclusão", "Descrição", "Mecânico", "Status"};
                }
            case "Salários":
                return new String[]{"Nome", "Login", "Nível", "Salário"};
            default:
                return new String[]{};
        }
    }
    
    //funct para receber as COLUNAS p retornar p TELA
    public static String[] getColunasParaTabela(String tipoRelatorio, String filtro) {
        return getColunasRelatorio(tipoRelatorio, filtro);
    }
    
    //funct c as OPÇÕES de FILTRO para cada tipo de RELATORIO financeiro - operacional e salaarios
    public static String[] getOpcoesFiltro(String tipoRelatorio) {
        switch (tipoRelatorio) {
            case "Financeiro":
                return new String[]{"Todos", "Devolvidos", "Não Devolvidos"};
            case "Operacional":
                return new String[]{
                    "Manutenções - Todos", 
                    "Manutenções - Pendente", 
                    "Manutenções - Em Andamento", 
                    "Manutenções - Finalizada",
                    "Solicitações - Todos",
                    "Solicitações - Em Analise",
                    "Solicitações - Aprovada",
                    "Solicitações - Recusada"
                };
            case "Salários":
                return new String[]{"Todos", "Administrador", "Atendente", "Mecânico"};
            default:
                return new String[]{"Todos"};
        }
    }
}