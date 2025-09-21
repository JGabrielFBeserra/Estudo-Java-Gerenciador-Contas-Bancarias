/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.banco.core;
import br.com.banco.model.ContaCorrente;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class Banco {
    private final List<ContaCorrente> contas = new ArrayList<>();

    public void adicionarConta(ContaCorrente c) { contas.add(c); }

    public List<ContaCorrente> listarContas() {
        return Collections.unmodifiableList(contas);
    }

    public ContaCorrente buscarPorId(int id) {
        return contas.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    public boolean existeId(int id) { return buscarPorId(id) != null; }
}
