package br.com.banco.app;

import br.com.banco.view.HomeBancoGUI;
import br.com.banco.conexao.InitDAO;
import br.com.banco.service.BancoService;
import br.com.banco.service.TarifaService;
import br.com.banco.service.ContaCorrenteService;
import br.com.banco.conexao.GerenciadorBancoDAO;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        InitDAO init = new InitDAO();
        //cria banco caso nao existe
        try {
            init.inicializarBanco();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Falha ao tentar criar o banco...");
        }
        //se o banco ja existir, mas estiver vazio, popula com o txt
        try {
            init.importarTxtSeTabelaVazia("contas.txt");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Falha ao tentar popular banco vazio com o conta.txt...");
        }

        SwingUtilities.invokeLater(() -> {
            var dao = new GerenciadorBancoDAO();
            var bancoService = new BancoService(dao);
            var contaService = new ContaCorrenteService(dao);
            var tarifaService = new TarifaService(bancoService, contaService);

            // exporta contas.txt ao fechar a GUI 
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    bancoService.exportarParaTxt("contas.txt");
                } catch (Exception ex) {
                    ex.printStackTrace(); 
                }
            }));

            var home = new HomeBancoGUI(null, true, bancoService, contaService, tarifaService);

            // garante que finaliza ao fechar a Home (dispara o hook acima)
            home.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override public void windowClosed(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });

            home.setVisible(true);
        });
    }
}
