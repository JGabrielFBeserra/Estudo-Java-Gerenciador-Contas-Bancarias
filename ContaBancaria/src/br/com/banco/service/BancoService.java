package br.com.banco.service;

import br.com.banco.core.Banco;
import br.com.banco.model.ContaCorrente;
import br.com.banco.conexao.GerenciadorBancoDAO;
import br.com.banco.conexao.InitDAO;

import java.io.*;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.sql.SQLException;

public class BancoService {

    private final GerenciadorBancoDAO dao;

    // NOVO construtor: injeta o DAO (e aposenta a dependência em Banco/HashMap)
    public BancoService(GerenciadorBancoDAO dao) {
        this.dao = dao;
    }

    // antitgo
    /* 
    public List<ContaCorrente> listar() {
        return banco.listarContas();
    } 
     */
    // novo
    public List<ContaCorrente> listar() {
        try {
            return dao.listarContas(); // SELECT numero,titular,saldo FROM contas ORDER BY numero
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar contas no banco.", e);
        }
    }

    public boolean existeId(int numero) {
        try {
            return dao.existeId(numero);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar existência da conta.", e);
        }
    }

    // Criar conta com validações + persistência no MySQL
    public ContaCorrente criarConta(int numero, String titular, double saldo) {
        if (numero <= 0) {
            throw new IllegalArgumentException("Número deve ser > 0.");
        }
        if (titular == null || titular.isBlank()) {
            throw new IllegalArgumentException("Titular obrigatório.");
        }
        if (saldo < 0) {
            throw new IllegalArgumentException("Saldo inicial não pode ser negativo.");
        }

        try {
            if (dao.existeId(numero)) {
                throw new IllegalArgumentException("Já existe conta com esse número.");
            }
            ContaCorrente c = new ContaCorrente(numero, titular, saldo);
            dao.inserirConta(c);                    // INSERT no banco
            return c;
        } catch (SQLException e) {
            // Em caso de corrida, PK pode estourar mesmo após checar; trate amigável:
            throw new RuntimeException("Erro ao criar a conta no banco.", e);
        }
    }

    public ContaCorrente buscar(int numero) {
        try {
            return dao.buscarPorNumero(numero);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar conta.", e);
        }
    }

    public void atualizarTitular(int numero, String novoTitular) {
        if (novoTitular == null || novoTitular.isBlank()) {
            throw new IllegalArgumentException("Titular obrigatório.");
        }
        try {
            dao.atualizarTitular(numero, novoTitular);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar titular.", e);
        }
    }

    public List<ContaCorrente> filtrarSaldoMaiorQue(double min) {
        return listar().stream()
                .filter(c -> c.getSaldo() > min)
                .toList();
    }

// somatório de todos os saldos
    public double saldoTotal() {
        return listar().stream()
                .map(ContaCorrente::getSaldo)
                .reduce(0.0, Double::sum);
    }

// agrupamento por faixas
    public Map<String, List<ContaCorrente>> agruparPorFaixa() {
        java.util.function.Function<ContaCorrente, String> faixa = c -> {
            double s = c.getSaldo();
            if (s <= 5_000) {
                return "Até R$ 5.000";
            }
            if (s <= 10_000) {
                return "R$ 5.001 a R$ 10.000";
            }
            return "Acima de R$ 10.000";
        };
        return listar().stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        faixa,
                        java.util.LinkedHashMap::new,
                        java.util.stream.Collectors.toList()
                ));
    }
}
