/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.banco.strategy;

public enum TarifaStrategy {
    FIXA {
        @Override public double calcular(double saldo) { return 10.00; }
    },
    PERCENTUAL_1 {
        @Override public double calcular(double saldo) { return saldo * 0.01; } // 1%
    },
    PERCENTUAL_20 {
        @Override public double calcular(double saldo) { return saldo * 0.20; } // 1%
    },
    ISENTA {
        @Override public double calcular(double saldo) { return 0.0; }
    };

    public abstract double calcular(double saldo);
    
}
