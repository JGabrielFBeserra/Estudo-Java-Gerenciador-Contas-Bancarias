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
    private final java.util.List<ContaCorrente> contas = new java.util.ArrayList<>();

    public void adicionarConta(ContaCorrente c) { contas.add(c); }

    public java.util.List<ContaCorrente> listarContas() {
        return java.util.Collections.unmodifiableList(contas);
    }

    public ContaCorrente buscarPorId(int id) {
        return contas.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    public boolean existeId(int id) { return buscarPorId(id) != null; }
}
