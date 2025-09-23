package br.com.banco.service;

import br.com.banco.exceptions.SaldoInsuficienteException;
import br.com.banco.model.ContaCorrente;
import br.com.banco.strategy.TarifaStrategy;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TarifaService {

    private final BancoService bancoService;
    private final ContaCorrenteService contaService;

    public TarifaService(BancoService bancoService, ContaCorrenteService contaService) {
        this.bancoService = bancoService;
        this.contaService = contaService;
    }

    /**
     * Calcula a tarifa pelo saldo ATUAL do BD.
     */
    public double calcular(int numero, TarifaStrategy strategy) {
        ContaCorrente conta = bancoService.buscar(numero);
        if (conta == null) {
            throw new IllegalArgumentException("Conta não encontrada.");
        }
        double t = strategy.calcular(conta.getSaldo());
        return Math.max(0.0, t);
    }

    /**
     * Aplica (debita) a tarifa no BD. Política A: se não houver saldo
     * suficiente, NÃO cobra e retorna 0.0.
     */
    public double aplicar(int numero, TarifaStrategy strategy) {
        double valor = calcular(numero, strategy);
        if (valor <= 0) {
            return 0.0;
        }
        try {
            contaService.sacar(numero, valor);   
            return valor;
        } catch (SaldoInsuficienteException e) {
            return 0.0; // sem saldo não cobra
        }
    }

}
