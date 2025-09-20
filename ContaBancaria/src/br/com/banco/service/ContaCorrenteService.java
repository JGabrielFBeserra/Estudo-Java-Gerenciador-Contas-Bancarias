package br.com.banco.service;

import br.com.banco.core.Banco;
import br.com.banco.exceptions.SaldoInsuficienteException;
import br.com.banco.model.ContaCorrente;

public class ContaCorrenteService {
    private final Banco banco;

    public ContaCorrenteService(Banco banco) { this.banco = banco; }

    public ContaCorrente buscarPorNumero(int numero) {
        return banco.buscarPorNumero(numero); // pode retornar null
    }

    public void depositar(ContaCorrente conta, double valor) {
        if (valor <= 0) throw new IllegalArgumentException("Valor deve ser > 0.");
        conta.depositar(valor);
    }

    public void sacar(ContaCorrente conta, double valor) throws SaldoInsuficienteException {
        if (valor <= 0) throw new IllegalArgumentException("Valor deve ser > 0.");
        conta.sacar(valor);
    }
}
