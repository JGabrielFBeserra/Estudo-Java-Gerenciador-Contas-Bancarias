package br.com.banco.app;

import br.com.banco.core.Banco;
import br.com.banco.model.ContaCorrente;
import br.com.banco.core.Banco;
import br.com.banco.view.HomeBancoGUI;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        Banco banco = new Banco();

        // carregar todas as contas do arquivo
        banco.carregarDeArquivo("contas.txt");

        //  todas no console (só para testar antes 
        System.out.println("=== Contas carregadas ===");
        for (ContaCorrente conta : banco.listarContas()) {
            conta.imprimirDados();
            System.out.println("-------------------------");
        }


   
        SwingUtilities.invokeLater(() -> {
        HomeBancoGUI home = new HomeBancoGUI(null, true);
        home.setVisible(true);
    });
    }
}

