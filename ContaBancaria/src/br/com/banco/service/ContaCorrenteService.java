package br.com.banco.service;

import br.com.banco.conexao.GerenciadorBancoDAO;
import br.com.banco.exceptions.SaldoInsuficienteException;
import br.com.banco.model.ContaCorrente;
import java.sql.SQLException;

public class ContaCorrenteService {

    private final GerenciadorBancoDAO dao;

    public ContaCorrenteService(GerenciadorBancoDAO dao) {
        this.dao = dao;
    }

    public void depositar(int numero, double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor deve ser > 0.");
        }
        try {
            boolean ok = dao.depositar(numero, valor);
            if (!ok) {
                throw new IllegalArgumentException("Conta inexistente.");
            }
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Falha ao depositar.", e);
        }
    }

    public void sacar(int numero, double valor) throws SaldoInsuficienteException {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor deve ser > 0.");
        }
        try {
            boolean ok = dao.sacar(numero, valor);
            if (!ok) {
                // false pode ser: conta não existe OU saldo insuficiente; tratamos como saldo p/ UX
                throw new SaldoInsuficienteException("Saldo insuficiente para saque.");
            }
        } catch (SaldoInsuficienteException e) {
            throw e;
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Falha ao sacar.", e);
        }
    }

    public void transferir(int numeroOrigem, int numeroDestino, double valor)
            throws SaldoInsuficienteException {
        if (numeroOrigem == numeroDestino) {
            throw new IllegalArgumentException("Contas de origem e destino não podem ser iguais.");
        }
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor da transferência deve ser > 0.");
        }

        try {
            dao.transferir(numeroOrigem, numeroDestino, valor); // transação no DAO
        } catch (SaldoInsuficienteException e) {
            throw e; // propaga para a GUI mostrar mensagem amigável
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao transferir no banco de dados.", e);
        }
    }

}
