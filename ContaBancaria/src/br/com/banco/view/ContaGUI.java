/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package br.com.banco.view;

import br.com.banco.service.BancoService;
import br.com.banco.service.ContaCorrenteService;
import br.com.banco.service.TarifaService;
import br.com.banco.model.ContaCorrente;
import br.com.banco.strategy.TarifaStrategy;
import br.com.banco.exceptions.SaldoInsuficienteException;

import javax.swing.JOptionPane;
import br.com.banco.strategy.TarifaStrategy;
import java.text.NumberFormat;
import java.util.Locale;

public class ContaGUI extends javax.swing.JDialog {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ContaGUI.class.getName());

    private final BancoService bancoService;
    private final ContaCorrenteService contaService;
    private final TarifaService tarifaService;
    private final ContaCorrente conta;

    /**
     * Creates new form ContaGUI
     */
    public ContaGUI(
            java.awt.Frame parent,
            boolean modal,
            BancoService bancoService,
            ContaCorrenteService contaService,
            TarifaService tarifaService,
            ContaCorrente conta
    ) {
        super(parent, modal);
        initComponents();
        this.bancoService = bancoService;
        this.contaService = contaService;
        this.tarifaService = tarifaService;
        this.conta = conta;
        setLocationRelativeTo(parent);

        carregarDadosConta();
        configurarComboTarifa();
        cmbTarifa.addActionListener(e -> atualizarTarifaPrevista());
        atualizarTarifaPrevista();
        montarListaTodas();
    }

    private void carregarDadosConta() {
        // sempre pega do banco pra evitar ficar com dado velho
        ContaCorrente c = bancoService.buscar(conta.getId());
        if (c != null) {
            lblId.setText(String.valueOf(c.getId()));
            txtTitular.setText(c.getTitular());
            lblSaldo.setText(BRL.format(c.getSaldo()));
        }
    }

    private final NumberFormat BRL = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    private void configurarComboTarifa() {
        JOptionPane.showMessageDialog(null, "isso roda");
        // Preenche com os NAMES do enum
        String[] itens = java.util.Arrays.stream(br.com.banco.strategy.TarifaStrategy.values())
                .map(Enum::name)
                .toArray(String[]::new);

        cmbTarifa.setModel(new javax.swing.DefaultComboBoxModel<>(itens));

        cmbTarifa.setSelectedItem("FIXA");
    }

    private void atualizarTarifaPrevista() {
        String sel = (String) cmbTarifa.getSelectedItem();
        if (sel == null) {
            lblTarifarEm.setText("Tarifa: —");
            return;
        }

        TarifaStrategy strategy = TarifaStrategy.valueOf(sel);

        // calcula com saldo ATUAL do BD
        double valor = tarifaService.calcular(conta.getId(), strategy);

        lblTarifarEm.setText("Tarifa: " + BRL.format(valor));
    }

    private void montarListaTodas() {
        var contas = bancoService.listar();               // vem do BD
        var model = new ContaTableModel(contas);         // REUSO do model da lista
        tabelaTodas.setModel(model);
        tabelaTodas.setRowSorter(new javax.swing.table.TableRowSorter<>(model));
        tabelaTodas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        // deixar read-only (bloqueia edição na célula)
        tabelaTodas.setDefaultEditor(Object.class, null);

        // centralizar colunas
        var centralizar = new javax.swing.table.DefaultTableCellRenderer();
        centralizar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        for (int i = 0; i < tabelaTodas.getColumnCount(); i++) {
            tabelaTodas.getColumnModel().getColumn(i).setCellRenderer(centralizar);
        }

        // formatar moeda na coluna 2 (Saldo)
        var moedaRenderer = new javax.swing.table.DefaultTableCellRenderer() {
            private final java.text.NumberFormat formato
                    = java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("pt", "BR"));

            @Override
            protected void setValue(Object value) {
                if (value instanceof Number) {
                    value = formato.format(value);
                }
                super.setValue(value);
            }
        };
        moedaRenderer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        if (tabelaTodas.getColumnCount() > 2) {
            tabelaTodas.getColumnModel().getColumn(2).setCellRenderer(moedaRenderer);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnSalvarTitular = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();
        lblI = new javax.swing.JLabel();
        txtTitular = new javax.swing.JTextField();
        lblS = new javax.swing.JLabel();
        lblTitular = new javax.swing.JLabel();
        btnSalvarTitular1 = new javax.swing.JButton();
        lblTarifaPrevista = new javax.swing.JLabel();
        btnAplicarTarifa = new javax.swing.JButton();
        cmbTarifa = new javax.swing.JComboBox<>();
        lblSaldo = new javax.swing.JLabel();
        lblId = new javax.swing.JLabel();
        lblTarifarEm = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtContaDestinoId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtValorTransferencia = new javax.swing.JTextField();
        btnRealizarTransferencia = new javax.swing.JToggleButton();
        jLabel3 = new javax.swing.JLabel();
        scrollTodas = new javax.swing.JScrollPane();
        tabelaTodas = new javax.swing.JTable();
        btnRecarregarLista = new javax.swing.JButton();

        btnSalvarTitular.setText("Editar Titular");
        btnSalvarTitular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarTitularActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        btnVoltar.setText("<- Voltar");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        lblI.setText("ID: ");

        txtTitular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTitularActionPerformed(evt);
            }
        });

        lblS.setText("Saldo: ");

        lblTitular.setText("Titular: ");

        btnSalvarTitular1.setText("Editar Titular");
        btnSalvarTitular1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarTitular1ActionPerformed(evt);
            }
        });

        lblTarifaPrevista.setText("Tarifa Prevista ");

        btnAplicarTarifa.setText("Aplicar Tarifa");
        btnAplicarTarifa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAplicarTarifaActionPerformed(evt);
            }
        });

        cmbTarifa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblSaldo.setText("-");

        lblId.setText("-");

        lblTarifarEm.setText("-");

        jLabel1.setText("ID da Conta Destino:");

        jLabel2.setText("Valor da Trasnferência:");

        btnRealizarTransferencia.setText("Transferir");
        btnRealizarTransferencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRealizarTransferenciaActionPerformed(evt);
            }
        });

        jLabel3.setText("=================================================");

        tabelaTodas.setModel(new javax.swing.table.DefaultTableModel(
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
        scrollTodas.setViewportView(tabelaTodas);

        btnRecarregarLista.setText("Atualizar Tabela");
        btnRecarregarLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecarregarListaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollTodas, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lblTitular, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(lblI))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblId, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTitular, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                            .addComponent(lblSaldo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(88, 88, 88)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbTarifa, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblTarifaPrevista)
                                .addGap(0, 52, Short.MAX_VALUE))
                            .addComponent(lblTarifarEm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSalvarTitular1)
                        .addGap(170, 170, 170)
                        .addComponent(btnAplicarTarifa, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnRecarregarLista, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRealizarTransferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtContaDestinoId))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtValorTransferencia))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnVoltar)
                            .addComponent(jLabel3))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnVoltar)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblI)
                    .addComponent(lblTarifaPrevista)
                    .addComponent(lblId))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTitular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTitular)
                    .addComponent(cmbTarifa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblS)
                    .addComponent(lblSaldo)
                    .addComponent(lblTarifarEm))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvarTitular1)
                    .addComponent(btnAplicarTarifa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtContaDestinoId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtValorTransferencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRealizarTransferencia)
                    .addComponent(btnRecarregarLista))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollTodas, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        dispose();
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void txtTitularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTitularActionPerformed

    }//GEN-LAST:event_txtTitularActionPerformed

    private void btnSalvarTitularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarTitularActionPerformed

    }//GEN-LAST:event_btnSalvarTitularActionPerformed

    private void btnSalvarTitular1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarTitular1ActionPerformed
        String novo = txtTitular.getText().trim();
        if (novo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o titular.");
            return;
        }
        try {
            bancoService.atualizarTitular(Integer.parseInt(lblId.getText()), novo);
            carregarDadosConta(); // recarrega da base
            JOptionPane.showMessageDialog(this, "Titular atualizado.");
        } catch (IllegalArgumentException iae) {
            JOptionPane.showMessageDialog(this, iae.getMessage());
        } catch (RuntimeException re) {
            JOptionPane.showMessageDialog(this, "Falha ao atualizar: " + re.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSalvarTitular1ActionPerformed

    private void btnAplicarTarifaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAplicarTarifaActionPerformed
        String sel = (String) cmbTarifa.getSelectedItem();
        if (sel == null) {
            JOptionPane.showMessageDialog(this, "Escolha uma tarifa.");
            return;
        }

        TarifaStrategy strategy;
        try {
            strategy = TarifaStrategy.valueOf(sel);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Tarifa inválida.");
            return;
        }

        int numero = conta.getId(); // ou getNumero()

        // aplica no BD (não cobra se faltar saldo)
        double cobrada = tarifaService.aplicar(numero, strategy);

        if (cobrada > 0) {
            JOptionPane.showMessageDialog(this, "Tarifa aplicada: " + BRL.format(cobrada));
        } else {
            JOptionPane.showMessageDialog(this, "Tarifa não aplicada (isenta ou saldo insuficiente).");
        }

        // Recarrega dados da conta a partir do BD e atualiza labels
        carregarDadosConta();
        atualizarTarifaPrevista();
    }//GEN-LAST:event_btnAplicarTarifaActionPerformed

    private void btnRealizarTransferenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRealizarTransferenciaActionPerformed
        btnRealizarTransferencia.setEnabled(false);
        try {
            // 1) ler e validar entradas
            String sDestino = txtContaDestinoId.getText().trim();
            String sValor = txtValorTransferencia.getText().trim().replace(",", "."); // aceita vírgula

            if (sDestino.isEmpty() || sValor.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe conta destino e valor.");
                return;
            }

            int numeroOrigem = conta.getId();      // conta aberta
            int numeroDestino = Integer.parseInt(sDestino);
            if (numeroDestino == numeroOrigem) {
                JOptionPane.showMessageDialog(this, "Origem e destino não podem ser iguais.");
                return;
            }

            double valor = Double.parseDouble(sValor);
            if (valor <= 0) {
                JOptionPane.showMessageDialog(this, "Valor deve ser > 0.");
                return;
            }

            // 2) valida existência da conta destino (feedback mais cedo)
            var cDestino = bancoService.buscar(numeroDestino);
            if (cDestino == null) {
                JOptionPane.showMessageDialog(this, "Conta destino não existe.");
                return;
            }

            // 3) confirmação
            int ok = JOptionPane.showConfirmDialog(
                    this,
                    "Confirmar transferência de " + BRL.format(valor)
                    + " da conta " + numeroOrigem + " para " + numeroDestino + "?",
                    "Confirmar transferência",
                    JOptionPane.YES_NO_OPTION
            );
            if (ok != JOptionPane.YES_OPTION) {
                return;
            }

            // 4) chama o serviço (transação no DAO)
            try {
                contaService.transferir(numeroOrigem, numeroDestino, valor);
                JOptionPane.showMessageDialog(this, "Transferência concluída.");

                // 5) atualiza a UI a partir do BD
                carregarDadosConta();       // recarrega saldo da origem
                atualizarTarifaPrevista();  // recalcula label da tarifa prevista
                txtValorTransferencia.setText("");    // limpa valor
            } catch (SaldoInsuficienteException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(),
                        "Saldo insuficiente", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Destino deve ser inteiro e valor numérico (ex.: 123.45).");
        } catch (RuntimeException re) {
            JOptionPane.showMessageDialog(this, "Falha ao transferir: " + re.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            btnRealizarTransferencia.setEnabled(true);
        }
    }//GEN-LAST:event_btnRealizarTransferenciaActionPerformed

    private void btnRecarregarListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecarregarListaActionPerformed
        montarListaTodas();
    }//GEN-LAST:event_btnRecarregarListaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAplicarTarifa;
    private javax.swing.JToggleButton btnRealizarTransferencia;
    private javax.swing.JButton btnRecarregarLista;
    private javax.swing.JButton btnSalvarTitular;
    private javax.swing.JButton btnSalvarTitular1;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JComboBox<String> cmbTarifa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblI;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblS;
    private javax.swing.JLabel lblSaldo;
    private javax.swing.JLabel lblTarifaPrevista;
    private javax.swing.JLabel lblTarifarEm;
    private javax.swing.JLabel lblTitular;
    private javax.swing.JScrollPane scrollTodas;
    private javax.swing.JTable tabelaTodas;
    private javax.swing.JTextField txtContaDestinoId;
    private javax.swing.JTextField txtTitular;
    private javax.swing.JTextField txtValorTransferencia;
    // End of variables declaration//GEN-END:variables
}
