package br.com.banco.service;

import br.com.banco.exceptions.SaldoInsuficienteException;
import br.com.banco.model.ContaCorrente;
import br.com.banco.strategy.TarifaStrategy;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TarifaService {

    /**
     * Só calcula o valor (não mexe no saldo).
     */
    public double calcular(ContaCorrente conta, TarifaStrategy strategy) {
        double t = strategy.calcular(conta.getSaldo());
        return Math.max(0.0, t);
    }

    /**
     * Debita a tarifa da conta. Respeita a mesma regra do saque (não deixa
     * negativo).
     */
    public double aplicar(ContaCorrente conta, TarifaStrategy strategy) {
        double tarifa = calcular(conta, strategy);
        if (tarifa <= 0) {
            return 0.0;
        }

        try {
            conta.sacar(tarifa); // se não tiver saldo, lança
            return tarifa;
        } catch (br.com.banco.exceptions.SaldoInsuficienteException e) {
            return 0.0; // política A: não cobra se faltar saldo
        }
    }

    /**
     * Aplica em lote e retorna quanto foi cobrado de cada conta. (0.0 se não
     * cobrou).
     */
    public Map<ContaCorrente, Double> aplicarEmTodas(List<ContaCorrente> contas, TarifaStrategy strategy) {
        Map<ContaCorrente, Double> cobradas = new LinkedHashMap<>();
        for (ContaCorrente c : contas) {
            try {
                cobradas.put(c, aplicar(c, strategy));
            } catch (SaldoInsuficienteException e) {
                cobradas.put(c, 0.0); // sem saldo: não cobra
            }
        }
        return cobradas;
    }
}
