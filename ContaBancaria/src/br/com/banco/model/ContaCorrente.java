/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.banco.model;
import br.com.banco.exceptions.SaldoInsuficienteException;
/**
 *
 * @author jgabr
 */
public class ContaCorrente extends Conta {

    public ContaCorrente(int id, String titular, double saldo) {
        super(id, titular, saldo);
    }

    @Override
    public void sacar(double valor) throws SaldoInsuficienteException {
        if (valor > getSaldo()) {
            throw new SaldoInsuficienteException("Saldo insuficiente para saque.");
        }
        setSaldo(getSaldo() - valor);
    }
}

