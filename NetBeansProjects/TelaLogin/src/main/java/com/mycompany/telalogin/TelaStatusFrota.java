package com.mycompany.telalogin;
import com.mycompany.telalogin.dao.MecanicoDAO;
import com.mycompany.telalogin.dao.VeiculoDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
public class TelaStatusFrota extends JPanel {
    private final DefaultTableModel model = new DefaultTableModel(
        new String[]{"ID","Modelo","Placa","Status","Tipo"}, 0) {
        public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable tabela = new JTable(model);
    private final JTextField txtBusca = new JTextField(15);
    private final VeiculoDAO veiculoDAO = new VeiculoDAO();
    private final String tipoUsuario;
    private final String nomeUsuario;
    public TelaStatusFrota(String tipoUsuario, String nomeUsuario) {
        this.tipoUsuario = tipoUsuario; 
        this.nomeUsuario = nomeUsuario;
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(12,12,12,12));

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topo.add(new JLabel("Status da Frota | Buscar (placa/modelo/tipo):"));
        topo.add(txtBusca);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> carregar(txtBusca.getText()));
        topo.add(btnBuscar);

        JButton btnDisponivel = new JButton("Marcar Disponível");
        btnDisponivel.addActionListener(e -> mudarStatus("Disponível"));
        topo.add(btnDisponivel);

        JButton btnManut = new JButton("Marcar Manutenção");
        btnManut.addActionListener(e -> {
            if (verificarAcesso()) {
                mudarStatus("Manutenção");
            } else {
                JOptionPane.showMessageDialog(this, "Acesso NEGADO. Apenas o ADMINISTRADOR e ATENDENTE podem marcar manutenção.");
            }
        });
        topo.add(btnManut);

        add(topo, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        carregar(null);
    }   
    //===== Funct p verificar o TIPO DE USER =================================================
    private boolean verificarAcesso() {
        return tipoUsuario.equalsIgnoreCase("Administrador") || tipoUsuario.equals("Atendente");
    }
    private void carregar(String termo) {
        model.setRowCount(0);
        try {
            List<Object[]> lista = veiculoDAO.listar(termo);
            for (Object[] v : lista) model.addRow(v);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar: " + ex.getMessage());
        }
    }
    private void mudarStatus(String status) {
        int row = tabela.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo na tabela.");
            return;
        }
        String placa = (String) model.getValueAt(row, 2);
        
        boolean sucesso = false;
        try {
            if ("Manutenção".equals(status)) {
                String problema = JOptionPane.showInputDialog(this, "Descreva o problema para a manutenção:");
                if (problema != null && !problema.trim().isEmpty()) {
                    //chama a funct solicitarManutencao q add um regist ao BD
                    sucesso = MecanicoDAO.solicitarManutencao(placa, problema, nomeUsuario);
                } else {
                    JOptionPane.showMessageDialog(this, "A descrição do problema é obrigatória para solicitar a manutenção.");
                    return;
                }
            } else if ("Disponível".equals(status)) {
                sucesso = MecanicoDAO.concluirManutencao(placa);
            }

            if (sucesso) {
                carregar(txtBusca.getText());
                JOptionPane.showMessageDialog(this, "Status atualizado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Não foi possível atualizar o status.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar status: " + ex.getMessage());
        }
    }
}