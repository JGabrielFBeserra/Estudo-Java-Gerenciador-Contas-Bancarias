package br.com.banco.service;

import br.com.banco.core.Banco;
import br.com.banco.exceptions.SaldoInsuficienteException;
import br.com.banco.model.ContaCorrente;

import java.io.*;
import java.util.List;

public class ContaService {
    private final Banco banco;

    public ContaService(Banco banco) {
        this.banco = banco;
    }

    // carregar do arquivo
    public void carregarDeArquivo(String caminhoArquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                String[] partes = linha.split(",");
                if (partes.length != 3) continue;

                int numero = Integer.parseInt(partes[0].trim());
                String titular = partes[1].trim();
                double saldo = Double.parseDouble(partes[2].trim());

                ContaCorrente conta = new ContaCorrente(numero, titular, saldo);
                banco.adicionarConta(conta);
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar arquivo: " + e.getMessage());
        }
    }

    // salvar no arquivo
    public void salvarEmArquivo(String caminhoArquivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (ContaCorrente conta : banco.listarContas()) {
                bw.write(conta.getId() + "," + conta.getTitular() + "," + conta.getSaldo());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        }
    }

    // operações de negócio
    public void sacar(ContaCorrente conta, double valor) throws SaldoInsuficienteException {
        conta.sacar(valor);
    }

    public void depositar(ContaCorrente conta, double valor) {
        conta.depositar(valor);
    }

    // utilitário
    public List<ContaCorrente> listarContas() {
        return banco.listarContas();
    }
}
