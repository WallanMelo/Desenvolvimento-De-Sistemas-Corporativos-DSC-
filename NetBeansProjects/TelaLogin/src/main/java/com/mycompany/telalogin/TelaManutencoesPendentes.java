package com.mycompany.telalogin;

import com.mycompany.telalogin.dao.MecanicoDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.DefaultCellEditor;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class TelaManutencoesPendentes extends JPanel {
    private JTable tabela;
    private String nomeMecanico;

    public TelaManutencoesPendentes(String nomeMecanico) {
        this.nomeMecanico = nomeMecanico;
        setLayout(new BorderLayout());
        setBackground(new Color(180, 180, 180));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titulo = new JLabel("Manutenções Ativas(Pendente ou Em_Andamneto)", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setOpaque(true);
        titulo.setBackground(new Color(120, 120, 120));
        titulo.setForeground(Color.BLACK);
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(titulo, BorderLayout.NORTH);

        // ===== TABELA =========================================================
        String[] colunas = {"VEÍCULO", "PLACA", "Data de Solicitação", "Tipo de Manutenção", "Status", "Ação"};

        DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
        };

        tabela = new JTable(modelo);
        tabela.setFont(new Font("Arial", Font.PLAIN, 14));
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        

        // ===== CÉLULAS DO BOTÃO DE AÇÃO =================================================
        tabela.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        tabela.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        carregarDados(null);
    }

    //Função para Carregar os Daodos Buscados do BD na tela
    private void carregarDados(String termo) {
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setRowCount(0);

        List<Object[]> lista = MecanicoDAO.listarManutencoesAtivas();

        for (Object[] linha : lista) {
            String status = (String) linha[4];
            String textoBotao = "";
            //Condicional p tratar o caso de iniciar e conluir uma manu
            if (status.equals("Pendente")) {
                textoBotao = "Iniciar";
            } else if (status.equals("Em_Andamento")) {
                textoBotao = "Concluir";
            }
            
            modelo.addRow(new Object[]{
                linha[0], // vaiuclo    
                linha[1], // plalca
                linha[2], // data de solicitação da manutenção
                linha[3], // tipo d emanutenção , ou seja o problema q deve ser reoslvido 
                status,   // stattus atual da manu
                textoBotao // Botão dinamico(qunado está pendente aparece INICIAR qunado está em Andamento  parace CONCLUIR)
            });
        }
    }

    // ===== Classes para botões do JTable =================================================
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setBackground(new Color(200, 200, 200));
            setFont(new Font("Arial", Font.BOLD, 12));
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                      boolean isSelected, boolean hasFocus,
                                                      int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private final JButton button;
        private String label;
        private boolean clicked;
        private int row;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            this.row = row;
            clicked = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                String placa = tabela.getValueAt(row, 1).toString();
                String acao = button.getText();
                boolean sucesso = false;
                
                if ("Iniciar".equals(acao)) {
                    sucesso = MecanicoDAO.assumirManutencao(placa, nomeMecanico);
                    if (sucesso) {
                        JOptionPane.showMessageDialog(button,
                            "Manutenção assumida com sucesso!\nO veículo com a placa " + placa + " foi atribuído a você.");
                    } else {
                        JOptionPane.showMessageDialog(button, "Erro ao assumir manutenção!");
                    }
                } else if ("Concluir".equals(acao)) {
                    sucesso = MecanicoDAO.concluirManutencao(placa);
                    if (sucesso) {
                        JOptionPane.showMessageDialog(button,
                            "Manutenção concluída com sucesso!\nO veículo com a placa " + placa + " agora está disponível.");
                    } else {
                        JOptionPane.showMessageDialog(button, "Erro ao concluir manutenção!");
                    }
                }
                
                if (sucesso) {
                    DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
                    carregarDados(null);
                }
            }
            clicked = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }
    }
}