package br.com.banco.view;

import br.com.banco.service.BancoService;
import br.com.banco.service.ContaCorrenteService;
import br.com.banco.service.TarifaService;
import br.com.banco.model.ContaCorrente;
import br.com.banco.strategy.TarifaStrategy;
/**
 *
 * @author jgabr
 */

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;
import java.text.NumberFormat;
import java.util.Locale;
import java.io.*;


public class ContaAvancadaGUI extends javax.swing.JDialog {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ContaAvancadaGUI.class.getName());
    
    private final BancoService bancoService;
    private final ContaCorrenteService contaService;
    private final TarifaService tarifaService;
    private final ContaCorrente conta;

    /**
     * Creates new form ContaAvancadaGUI
     */
    public ContaAvancadaGUI(java.awt.Frame parent, boolean modal,
            BancoService bancoService,
            ContaCorrenteService contaService,
            TarifaService tarifaService,
            ContaCorrente conta) {

        super(parent, modal);
        this.bancoService = bancoService;
        this.contaService = contaService;
        this.tarifaService = tarifaService;
        this.conta = conta;
        initComponents();             // gerado pelo NetBeans
        setLocationRelativeTo(parent);

    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnVoltar = new javax.swing.JButton();
        lblId = new javax.swing.JLabel();
        lblSaldo = new javax.swing.JLabel();
        txtTitularaaa = new javax.swing.JTextField();
        btnSalvarTitularaaa = new javax.swing.JButton();
        cmbTarifa = new javax.swing.JComboBox<>();
        lblTarifaPrevista = new javax.swing.JLabel();
        btnAplicarTarifa = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        btnVoltar.setText("< - Voltar");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        lblId.setText("ID: -");

        lblSaldo.setText("Saldo: -");

        txtTitularaaa.setText("Titular");
        txtTitularaaa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTitularaaaActionPerformed(evt);
            }
        });

        btnSalvarTitularaaa.setText("Editar Titular");
        btnSalvarTitularaaa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarTitularaaaActionPerformed(evt);
            }
        });

        private javax.swing.JComboBox<br.com.banco.strategy.TarifaStrategy> cmbTarifa;

        lblTarifaPrevista.setText("Tarifa Prevista ");

        btnAplicarTarifa.setText("Aplicar Tarifa");
        btnAplicarTarifa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAplicarTarifaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblId, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVoltar)
                    .addComponent(lblSaldo)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtTitularaaa, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnSalvarTitularaaa, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 134, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTarifaPrevista)
                    .addComponent(cmbTarifa, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAplicarTarifa, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnVoltar)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblId)
                    .addComponent(lblTarifaPrevista))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTitularaaa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTarifa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblSaldo)
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvarTitularaaa)
                    .addComponent(btnAplicarTarifa))
                .addContainerGap(102, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtTitularaaaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTitularaaaActionPerformed

    }//GEN-LAST:event_txtTitularaaaActionPerformed

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
 ;
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void btnSalvarTitularaaaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarTitularaaaActionPerformed

    }//GEN-LAST:event_btnSalvarTitularaaaActionPerformed

    private void btnAplicarTarifaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAplicarTarifaActionPerformed

    }//GEN-LAST:event_btnAplicarTarifaActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAplicarTarifa;
    private javax.swing.JButton btnSalvarTitularaaa;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JComboBox<String> cmbTarifa;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblSaldo;
    private javax.swing.JLabel lblTarifaPrevista;
    private javax.swing.JTextField txtTitularaaa;
    // End of variables declaration//GEN-END:variables
