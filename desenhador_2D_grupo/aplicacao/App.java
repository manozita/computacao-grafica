package aplicacao;
import GUI.Gui;
import GUI.MainGUI;
import controller.PainelDesenho;
import javax.swing.JOptionPane;

/**
 * Aplicacao para testar primitivos graficos.
 * 
 * @author Julio Arakaki 
 * @version 20220815
 */
public class App {
    public static void main(String args[]) {
        // Cria e define dimensao da janela (em pixels)
        int resposta = JOptionPane.showConfirmDialog(null, "MainGUI?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (resposta == JOptionPane.YES_OPTION) {
            new MainGUI().setVisible(true);
        } else {
            new Gui(700, 600);
        }
    }
}
