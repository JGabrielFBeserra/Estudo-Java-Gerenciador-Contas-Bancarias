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
public abstract class Conta {
    private int id;
    private String titular;
    private double saldo;

    public int getId() {
        return id;
    }

    public String getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    
    

    public Conta(int id, String titular, double saldo) {
        this.id = id;
        this.titular = titular;
        this.saldo = saldo;
    }

    public abstract void sacar(double valor) throws SaldoInsuficienteException;

    public void depositar(double valor) {
        this.saldo += valor;
    }

    public void imprimirDados() {
        System.out.println("Número: " + id);
        System.out.println("Titular: " + titular);
        System.out.println("Saldo: " + saldo);
    }
}
