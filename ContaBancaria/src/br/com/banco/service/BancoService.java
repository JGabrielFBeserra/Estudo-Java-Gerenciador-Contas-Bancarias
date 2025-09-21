package br.com.banco.service;

import br.com.banco.core.Banco;
import br.com.banco.model.ContaCorrente;
import java.io.*;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BancoService {

    private final Banco banco;

    public BancoService(Banco banco) {
        this.banco = banco;
    }

    // Estado
    public List<ContaCorrente> listar() {
        return banco.listarContas();
    }
    
    

    public boolean existeId(int numero) {
        return banco.existeId(numero);
    }

    // Criar conta com unicidade
    public ContaCorrente criarConta(int id, String titular, double saldo) {
    if (id <= 0) throw new IllegalArgumentException("Número deve ser > 0.");
    if (titular == null || titular.isBlank()) throw new IllegalArgumentException("Titular obrigatório.");
    if (saldo < 0) throw new IllegalArgumentException("Saldo inicial não pode ser negativo.");
    if (banco.existeId(id)) throw new IllegalArgumentException("Já existe conta com esse número.");
    ContaCorrente c = new ContaCorrente(id, titular, saldo);
    banco.adicionarConta(c);
    return c;
}

    // Carrega as contas do contas.txt
    public void carregarDeArquivo(String path) throws IOException {
        try (BufferedReader stream = new BufferedReader(new FileReader(path))) {
            stream.lines()
                    .map(String::trim)
                    .filter(l -> !l.isBlank()) // ignora linha vazia
                    .forEach(linha -> {
                        String[] p = linha.split(",");
                        if (p.length != 3) {
                            return;            // validação
                        }
                        int id = Integer.parseInt(p[0].trim());
                        String titular = p[1].trim();
                        double saldo = Double.parseDouble(p[2].trim());

                        if (!banco.existeId(id)) {
                            banco.adicionarConta(new ContaCorrente(id, titular, saldo));
                        }
                    });
        }
    }

    // Salva as contas (sobrescreve atulizando)
    public void salvarCompleto(String path) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (ContaCorrente c : banco.listarContas()) {
                bw.write(c.getId() + ", " + c.getTitular() + ", " + String.format(Locale.US, "%.2f", c.getSaldo()));
                bw.newLine();
            }
        }
    }

    // Dá append de UMA conta no contas.txt
    public void appendConta(String path, ContaCorrente c) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            bw.write(c.getId() + ", " + c.getTitular() + ", " + String.format(Locale.US, "%.2f", c.getSaldo()));
            bw.newLine();
        }
    }

    // filtrar contas com saldo > min
    public List<ContaCorrente> filtrarSaldoMaiorQue(double min) {
        return listar().stream()
                .filter(c -> c.getSaldo() > min)
                .toList();
    }

    // somar todos os saldos (reduce)
    public double saldoTotal() {
        return listar().stream()
                .map(ContaCorrente::getSaldo)
                .reduce(0.0, Double::sum);
    }

    //  faixa de saldo
    public Map<String, List<ContaCorrente>> agruparPorFaixa() {
        Function<ContaCorrente, String> faixa = c -> {
            double s = c.getSaldo();
            if (s <= 5000) {
                return "Até R$ 5.000";
            } else if (s <= 10000) {
                return "R$ 5.001 a R$ 10.000";
            } else {
                return "Acima de R$ 10.000";
            }
        };
        return listar().stream()
                .collect(Collectors.groupingBy(
                        faixa,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));
    }

}
