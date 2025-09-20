/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.banco.core;
import br.com.banco.model.ContaCorrente;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.*;


public class Banco {
    // vive em tempo de execução
    private final List<ContaCorrente> contas = new ArrayList<>();

    // adiciona uma conta (número único)
    public void adicionarConta(ContaCorrente conta) {
        if (existeId(conta.getId())) {
            throw new IllegalArgumentException("Já existe conta com número " + conta.getId());
        }
        contas.add(conta);
    }

    // list contas
    public List<ContaCorrente> listarContas() {
        return contas;
    }

    // search por id
    public ContaCorrente buscarPorNumero(int id) {
        for (ContaCorrente conta : contas) {
            if (conta.getId() == id) {
                return conta;
            }
        }
        return null; // se não encontrar
    }

    // verificar se existe
    public boolean existeId(int id) {
        return buscarPorNumero(id) != null;
    }
    
    public void carregarDeArquivo(String caminhoArquivo) {
        //
    try (BufferedReader buffer = new BufferedReader(new FileReader(caminhoArquivo))) {
        // declarando para eu salvar o conteúdo de cada linha
        String linha;
        // ler linha a linha, enquanto a próxima linha for diferente de null eu to lendo o arquivo
        while ((linha = buffer.readLine()) != null) {
            if (linha.trim().isEmpty()) continue; // ignora linha vazia e tenta continuar
            
            String[] partes = linha.split(",");
            //só aceito split que retorna [0,1,2] 
            if (partes.length != 3) {
                System.out.println("Linha inválida: " + linha);
                continue;
            }
            
            //conversão de tipos após o split
            int numero = Integer.parseInt(partes[0].trim());
            String titular = partes[1].trim();
            double saldo = Double.parseDouble(partes[2].trim());

            // criar um objeto conta e itera na lista
            ContaCorrente conta = new ContaCorrente(numero, titular, saldo);
            adicionarConta(conta);
        }
    } catch (IOException e) {
        System.out.println("Erro ao ler arquivo: " + e.getMessage());
    }
    
}
    
}
