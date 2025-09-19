package br.com.banco.view;

import br.com.banco.model.ContaCorrente;
import br.com.banco.core.Banco;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ContaTableModel extends AbstractTableModel {
    private final List<ContaCorrente> dados;
    private final String[] colunas = { "Número", "Titular", "Saldo" };

    public ContaTableModel(List<ContaCorrente> dados) {
        this.dados = dados;
    }

    @Override public int getRowCount() { return dados.size(); }
    @Override public int getColumnCount() { return colunas.length; }
    @Override public String getColumnName(int col) { return colunas[col]; }

    @Override
    public Object getValueAt(int row, int col) {
        ContaCorrente c = dados.get(row);
        return switch (col) {
            case 0 -> c.getId();   // ou getId(), conforme sua classe
            case 1 -> c.getTitular();
            case 2 -> c.getSaldo();
            default -> null;
        };
    }

    @Override
    public Class<?> getColumnClass(int col) {
        return switch (col) {
            case 0 -> Integer.class;
            case 1 -> String.class;
            case 2 -> Double.class;
            default -> Object.class;
        };
    }

    public ContaCorrente getContaAt(int rowIndex) {
        return dados.get(rowIndex);
    }
}