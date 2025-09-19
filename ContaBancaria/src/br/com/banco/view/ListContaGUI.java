package br.com.banco.view;

import br.com.banco.core.Banco;
import br.com.banco.model.ContaCorrente;
import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;
import java.text.NumberFormat;
import java.util.Locale;

public class ListContaGUI extends javax.swing.JDialog {

    private final Banco banco;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ListContaGUI.class.getName());

    public ListContaGUI(java.awt.Frame parent, boolean modal, Banco banco) {
        super(parent, modal);
        this.banco = banco;
        try {
            initComponents();              // do NetBeans
            setLocationRelativeTo(parent);
            carregarTabela();              // monta modelo + renderers + listener
        } catch (Throwable t) {
            t.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro abrindo lista: " + t.getClass().getSimpleName()
                    + " - " + String.valueOf(t.getMessage()));
        }
    }

    private void carregarTabela() {
        List<ContaCorrente> contas = banco.listarContas();
        ContaTableModel model = new ContaTableModel(contas);
        tabelaContas.setModel(model);
        tabelaContas.setRowSorter(new TableRowSorter<>(model));
        tabelaContas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // centralizador padrão
        DefaultTableCellRenderer centralizar = new DefaultTableCellRenderer();
        centralizar.setHorizontalAlignment(SwingConstants.CENTER);

        // aplica centralização em todas as colunas
        for (int i = 0; i < tabelaContas.getColumnCount(); i++) {
            tabelaContas.getColumnModel().getColumn(i).setCellRenderer(centralizar);
        }

        // renderer especial para a coluna saldo (índice 2)
        DefaultTableCellRenderer moedaRenderer = new DefaultTableCellRenderer() {
            private final NumberFormat formato = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

            @Override
            protected void setValue(Object value) {
                if (value instanceof Number) {
                    value = formato.format(value);
                }
                super.setValue(value);
            }
        };
        moedaRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // aplica só na coluna Saldo
        tabelaContas.getColumnModel().getColumn(2).setCellRenderer(moedaRenderer);

        // ===== listener de seleção =====
        tabelaContas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabelaContas.getSelectedRow() != -1) {
                int row = tabelaContas.getSelectedRow();
                int modelRow = tabelaContas.convertRowIndexToModel(row);

                ContaTableModel m = (ContaTableModel) tabelaContas.getModel();
                ContaCorrente conta = m.getContaAt(modelRow);

                // atualiza labels (crie lblNumero, lblTitular, lblSaldo no design)
                lblId.setText("Número: " + conta.getId());
                lblTitular.setText("Titular: " + conta.getTitular());
                lblSaldo.setText("Saldo: R$ " + conta.getSaldo());
            }
        });
    }

    private void atualizarLabels(ContaCorrente conta) {
        
        lblId.setText("Número: " + conta.getId());
        lblTitular.setText("Titular: " + conta.getTitular());
        lblSaldo.setText("Saldo: R$ " + conta.getSaldo());
    }

    private void salvarCompleto(String caminho) {
        try (java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter(caminho))) {
            for (ContaCorrente c : banco.listarContas()) {
                // use getNumero() se esse for o seu getter; troque getId() se necessário
                bw.write(c.getId() + ", " + c.getTitular() + ", " + String.format(java.util.Locale.US, "%.2f", c.getSaldo()));
                bw.newLine();
            }
        } catch (java.io.IOException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Jscrollai = new javax.swing.JScrollPane();
        tabelaContas = new javax.swing.JTable();
        lblId = new javax.swing.JLabel();
        lblTitular = new javax.swing.JLabel();
        lblSaldo = new javax.swing.JLabel();
        btnVoltar = new javax.swing.JButton();
        txtValor = new javax.swing.JTextField();
        btnDepositar = new javax.swing.JButton();
        btnSacar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tabelaContas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        Jscrollai.setViewportView(tabelaContas);

        lblId.setText("ID: —");

        lblTitular.setText("Titular: —");

        lblSaldo.setText("Saldo: —");

        btnVoltar.setText("<- voltar");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        txtValor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorActionPerformed(evt);
            }
        });

        btnDepositar.setText("Depositar");
        btnDepositar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDepositarActionPerformed(evt);
            }
        });

        btnSacar.setText("Sacar");
        btnSacar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSacarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Jscrollai, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnVoltar)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTitular, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                            .addComponent(lblSaldo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtValor)
                            .addComponent(btnDepositar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSacar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnVoltar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Jscrollai, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblId, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitular)
                    .addComponent(btnDepositar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSaldo)
                    .addComponent(btnSacar))
                .addGap(13, 13, 13))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        dispose(); // fecha só este diálogo
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void txtValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorActionPerformed

    private void salvarContasAtualizadas(String caminho) {
        try (java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter(caminho))) {
            for (ContaCorrente c : banco.listarContas()) {
                bw.write(c.getId() + "," + c.getTitular() + "," + c.getSaldo()); // use getNumero() se for o seu caso
                bw.newLine();
            }
        } catch (java.io.IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage());
        }
    }
    private void btnDepositarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDepositarActionPerformed
        int row = tabelaContas.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma conta primeiro!");
            return;
        }

        try {
            double valor = Double.parseDouble(txtValor.getText().trim());
            int modelRow = tabelaContas.convertRowIndexToModel(row);
            ContaTableModel model = (ContaTableModel) tabelaContas.getModel();
            ContaCorrente conta = model.getContaAt(modelRow);

            conta.depositar(valor);
            carregarTabela(); // atualiza tabela
            atualizarLabels(conta); // método para atualizar os labels abaixo
            salvarCompleto("contas.txt");
            salvarContasAtualizadas("extrato_logs.txt");
            JOptionPane.showMessageDialog(this, "Depósito realizado com sucesso!");
            lblId.setText("Número: -");
            lblTitular.setText("Titular: -");
            lblSaldo.setText("Saldo: R$ -");
            txtValor.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite um valor válido!");
        }


    }//GEN-LAST:event_btnDepositarActionPerformed

    private void btnSacarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSacarActionPerformed

        int row = tabelaContas.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma conta primeiro!");
            return;
        }
        try {
            double valor = Double.parseDouble(txtValor.getText().trim());
            int modelRow = tabelaContas.convertRowIndexToModel(row);
            ContaTableModel model = (ContaTableModel) tabelaContas.getModel();
            ContaCorrente conta = model.getContaAt(modelRow);
            try {
                conta.sacar(valor);
                carregarTabela();
                atualizarLabels(conta);
                salvarCompleto("contas.txt");
                JOptionPane.showMessageDialog(this, "Saque realizado com sucesso!");
                salvarContasAtualizadas("extrato_logs.txt");
                txtValor.setText("");
                lblId.setText("Número: -");
                lblTitular.setText("Titular: -");
                lblSaldo.setText("Saldo: R$ -");
            } catch (br.com.banco.exceptions.SaldoInsuficienteException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite um valor válido!");
        }


    }//GEN-LAST:event_btnSacarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            // cria o banco e carrega as contas do arquivo
            br.com.banco.core.Banco banco = new br.com.banco.core.Banco();
            banco.carregarDeArquivo("contas.txt");

            // abre a ListContaGUI passando o banco
            ListContaGUI dialog = new ListContaGUI(new javax.swing.JFrame(), true, banco);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane Jscrollai;
    private javax.swing.JButton btnDepositar;
    private javax.swing.JButton btnSacar;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblSaldo;
    private javax.swing.JLabel lblTitular;
    private javax.swing.JTable tabelaContas;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
