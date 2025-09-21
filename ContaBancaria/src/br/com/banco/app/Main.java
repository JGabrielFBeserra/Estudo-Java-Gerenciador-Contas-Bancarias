package br.com.banco.app;

import br.com.banco.view.HomeBancoGUI;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HomeBancoGUI home = new HomeBancoGUI(null, true);
            home.setVisible(true);
        });
    }
}
