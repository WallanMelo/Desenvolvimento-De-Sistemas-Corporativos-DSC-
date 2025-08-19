package com.mycompany.telalogin;

import com.mycompany.telalogin.dao.MecanicoDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class TelaManutencoesPendentes extends JPanel {
    private JTable tabela;

    public TelaManutencoesPendentes() {
        setLayout(new BorderLayout());
        setBackground(new Color(180, 180, 180));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titulo = new JLabel("Manutenções Pendentes", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setOpaque(true);
        titulo.setBackground(new Color(120, 120, 120));
        titulo.setForeground(Color.BLACK);
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(titulo, BorderLayout.NORTH);

        // ===== TABELA =====
        String[] colunas = {"VEÍCULO", "PLACA", "Data de Solicitação", "Tipo de Manutenção", "Manutenção"};

        DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // AGORA A COLUNA DO BOTÃO É A 4 (última)
            }
        };

        tabela = new JTable(modelo);
        tabela.setRowHeight(35);
        tabela.setFont(new Font("Arial", Font.PLAIN, 14));
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabela.getTableHeader().setBackground(Color.BLACK);
        tabela.getTableHeader().setForeground(Color.WHITE);

        // ===== BOTÃO Iniciar a manutenção =====
        tabela.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        tabela.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        carregarDados(modelo);
    }

    private void carregarDados(DefaultTableModel modelo) {
        modelo.setRowCount(0);

        List<Object[]> lista = MecanicoDAO.listarManutencoesPendentes();

        for (Object[] linha : lista) {
            modelo.addRow(new Object[]{
                linha[0], // veículo
                linha[1], // placa
                linha[2], // data DE SOLICITA
                linha[3], // tipo DE MANUTENÇÕA É A DESCRIÇÃO DO PROBLEMA A SER RESOLVIDO
                "Iniciar"  // buttao
            });
        }
    }

    // ===== Classes  para botões do JTable =====
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
        private JButton button;
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
                String veiculo = tabela.getValueAt(row, 0).toString();
                JOptionPane.showMessageDialog(button, "Iniciando manutenção do veículo: " + veiculo);
                // tenho q atualizar o  status e chamar o dao
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