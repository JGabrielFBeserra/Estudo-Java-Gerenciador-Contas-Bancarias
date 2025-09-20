package br.com.banco.view;

import br.com.banco.core.Banco;
import br.com.banco.model.ContaCorrente;
import br.com.banco.exceptions.SaldoInsuficienteException;
import br.com.banco.service.BancoService;
import br.com.banco.service.ContaCorrenteService;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;
import java.text.NumberFormat;
import java.util.Locale;
import java.io.*;

public class ListContaGUI extends javax.swing.JDialog {

    private final BancoService bancoService;
    private final ContaCorrenteService contaService;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ListContaGUI.class.getName());

    public ListContaGUI(java.awt.Frame parent, boolean modal,
            BancoService bancoService,
            ContaCorrenteService contaService) {
        super(parent, modal);
        this.bancoService = bancoService;
        this.contaService = contaService;
        initComponents();
        setLocationRelativeTo(parent);
        carregarTabela();

    }

    private void carregarTabela() {
        setTableData(bancoService.listar());
        List<ContaCorrente> contas = bancoService.listar();
        ContaTableModel model = new ContaTableModel(contas);
        tabelaContas.setModel(model);
        tabelaContas.setRowSorter(new TableRowSorter<>(model));
        tabelaContas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        aplicarRenderersPadrao(); // bloco de centralização + saldo com R$

        // listener de seleção
        tabelaContas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabelaContas.getSelectedRow() != -1) {
                int row = tabelaContas.getSelectedRow();
                int modelRow = tabelaContas.convertRowIndexToModel(row);
                ContaTableModel m = (ContaTableModel) tabelaContas.getModel();
                ContaCorrente conta = m.getContaAt(modelRow);
                atualizarLabels(conta);
            }
        });
    }

    private void aplicarRenderersPadrao() {
        // centralizar todas
        DefaultTableCellRenderer centralizar = new DefaultTableCellRenderer();
        centralizar.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tabelaContas.getColumnCount(); i++) {
            tabelaContas.getColumnModel().getColumn(i).setCellRenderer(centralizar);
        }

        // saldo na coluna 2
        DefaultTableCellRenderer moedaRenderer = new DefaultTableCellRenderer() {
            private final NumberFormat formato
                    = NumberFormat.getCurrencyInstance(new java.util.Locale("pt", "BR"));

            @Override
            protected void setValue(Object value) {
                if (value instanceof Number) {
                    value = formato.format(value);
                }
                super.setValue(value);
            }
        };
        moedaRenderer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tabelaContas.getColumnModel().getColumn(2).setCellRenderer(moedaRenderer);
    }

    private int faixaRank(double s) {
        if (s <= 5000.0) {
            return 0;
        }
        if (s <= 10000.0) {
            return 1;
        }
        return 2;
    }

    private void setTableData(java.util.List<br.com.banco.model.ContaCorrente> dados) {
        ContaTableModel model = new ContaTableModel(dados);
        tabelaContas.setModel(model);
        tabelaContas.setRowSorter(new javax.swing.table.TableRowSorter<>(model));
        tabelaContas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        aplicarRenderersPadrao(); // seu método que centraliza e formata moeda
    }

    private void atualizarLabels(ContaCorrente conta) {

        lblId.setText("Número: " + conta.getId());
        lblTitular.setText("Titular: " + conta.getTitular());
        lblSaldo.setText("Saldo: R$ " + conta.getSaldo());
    }

    private void salvarCompleto(String caminho) {
        try (java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter(caminho))) {
            for (ContaCorrente c : bancoService.listar()) {
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

        jCheckBox1 = new javax.swing.JCheckBox();
        Jscrollai = new javax.swing.JScrollPane();
        tabelaContas = new javax.swing.JTable();
        lblId = new javax.swing.JLabel();
        lblTitular = new javax.swing.JLabel();
        lblSaldo = new javax.swing.JLabel();
        btnVoltar = new javax.swing.JButton();
        txtValor = new javax.swing.JTextField();
        btnDepositar = new javax.swing.JButton();
        btnSacar = new javax.swing.JButton();
        chkAcima10k = new javax.swing.JCheckBox();
        btnSaldoTotal = new javax.swing.JButton();
        btnAgruparFaixa = new javax.swing.JButton();

        jCheckBox1.setText("jCheckBox1");

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

        chkAcima10k.setText("Saldo > 10000");
        chkAcima10k.setToolTipText("Filtrar contas com saldo superior a 10 mil");
        chkAcima10k.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkAcima10kStateChanged(evt);
            }
        });
        chkAcima10k.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkAcima10kActionPerformed(evt);
            }
        });

        btnSaldoTotal.setText("Soma Saldos");
        btnSaldoTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaldoTotalActionPerformed(evt);
            }
        });

        btnAgruparFaixa.setText("Agrupar");
        btnAgruparFaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgruparFaixaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnVoltar)
                        .addGap(18, 18, 18)
                        .addComponent(chkAcima10k)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAgruparFaixa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSaldoTotal))
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
            .addComponent(Jscrollai, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVoltar)
                    .addComponent(chkAcima10k)
                    .addComponent(btnSaldoTotal)
                    .addComponent(btnAgruparFaixa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Jscrollai, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
        dispose();
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void txtValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorActionPerformed

    private void salvarContasAtualizadas(String caminho) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
            for (ContaCorrente c : bancoService.listar()) {
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

            contaService.depositar(conta, valor);
            carregarTabela(); // atualiza tabela
            atualizarLabels(conta); // método para atualizar os labels abaixo
            bancoService.salvarCompleto("contas.txt");
            salvarContasAtualizadas("extrato_logs.txt");
            JOptionPane.showMessageDialog(this, "Depósito realizado com sucesso!");
            lblId.setText("Número: -");
            lblTitular.setText("Titular: -");
            lblSaldo.setText("Saldo: R$ -");
            txtValor.setText("");
        } catch (java.io.IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage(),
                    "I/O", JOptionPane.ERROR_MESSAGE);
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
                bancoService.salvarCompleto("contas.txt");

                JOptionPane.showMessageDialog(this, "Saque realizado com sucesso!");
                salvarContasAtualizadas("extrato_logs.txt");
                txtValor.setText("");
                lblId.setText("Número: -");
                lblTitular.setText("Titular: -");
                lblSaldo.setText("Saldo: R$ -");
            } catch (SaldoInsuficienteException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (java.io.IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage(),
                    "I/O", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_btnSacarActionPerformed

    private void chkAcima10kActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkAcima10kActionPerformed
    }//GEN-LAST:event_chkAcima10kActionPerformed

    private void chkAcima10kStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkAcima10kStateChanged
        var dados = chkAcima10k.isSelected()
                ? bancoService.filtrarSaldoMaiorQue(10_000)
                : bancoService.listar();

        setTableData(dados);

        // opcional: limpar detalhes
        lblId.setText("Número: —");
        lblTitular.setText("Titular: —");
        lblSaldo.setText("Saldo: —");
    }//GEN-LAST:event_chkAcima10kStateChanged

    private void btnSaldoTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaldoTotalActionPerformed
        double total = bancoService.saldoTotal();
        var brl = java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("pt", "BR"));
        JOptionPane.showMessageDialog(this, "Saldo total: " + brl.format(total));
    }//GEN-LAST:event_btnSaldoTotalActionPerformed

    private void btnAgruparFaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgruparFaixaActionPerformed
        var grupos = bancoService.agruparPorFaixa(); // Map<String, List<ContaCorrente>>
        var brl = java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("pt", "BR"));

        // ordem exigida: a), b), c)
        List<java.util.Map.Entry<String, String>> ordem = java.util.List.of(
                new java.util.AbstractMap.SimpleEntry<>("a) Até R$ 5.000", "Até R$ 5.000"),
                new java.util.AbstractMap.SimpleEntry<>("b) R$ 5.001 a R$ 10.000", "R$ 5.001 a R$ 10.000"),
                new java.util.AbstractMap.SimpleEntry<>("c) Acima de R$ 10.000", "Acima de R$ 10.000")
        );

        String corpo = ordem.stream()
                .map(pair -> {
                    String rotulo = pair.getKey();   // "a) Até R$ 5.000"
                    String chave = pair.getValue(); // "Até R$ 5.000"
                    var stats = grupos.getOrDefault(chave, java.util.Collections.emptyList())
                            .stream()
                            .collect(java.util.stream.Collectors.summarizingDouble(ContaCorrente::getSaldo));
                    return String.format("%-22s  Qtde: %-3d  Total: %s",
                            rotulo, stats.getCount(), brl.format(stats.getSum()));
                })
                .collect(java.util.stream.Collectors.joining("\n"));

        javax.swing.JOptionPane.showMessageDialog(
                this, "Resumo por faixa:\n\n" + corpo, "Agrupamento por Faixa",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );

        // 2) ordenar a TABELA por faixa (a, b, c) e, dentro da faixa, por saldo desc
        java.util.Comparator<ContaCorrente> byFaixaThenSaldoAsc =
    java.util.Comparator.comparingInt((ContaCorrente c) -> faixaRank(c.getSaldo()))
                        .thenComparing(ContaCorrente::getSaldo);

        var base = chkAcima10k != null && chkAcima10k.isSelected()
                ? bancoService.filtrarSaldoMaiorQue(10_000)
                : bancoService.listar();

        var ordenada = base.stream().sorted(byFaixaThenSaldoAsc).toList();
        setTableData(ordenada); 


    }//GEN-LAST:event_btnAgruparFaixaActionPerformed

    public static void main(String args[]) {
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

        java.awt.EventQueue.invokeLater(() -> {
            br.com.banco.core.Banco banco = new br.com.banco.core.Banco();
            br.com.banco.service.BancoService bancoService = new br.com.banco.service.BancoService(banco);
            br.com.banco.service.ContaCorrenteService contaService = new br.com.banco.service.ContaCorrenteService(banco);
            try {
                bancoService.carregarDeArquivo("contas.txt");
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }

            ListContaGUI dialog = new ListContaGUI(new javax.swing.JFrame(), true, bancoService, contaService);
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
    private javax.swing.JButton btnAgruparFaixa;
    private javax.swing.JButton btnDepositar;
    private javax.swing.JButton btnSacar;
    private javax.swing.JButton btnSaldoTotal;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JCheckBox chkAcima10k;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblSaldo;
    private javax.swing.JLabel lblTitular;
    private javax.swing.JTable tabelaContas;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
