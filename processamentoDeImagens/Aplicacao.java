package processamentoDeImagens;
import javax.swing.SwingUtilities;

public class Aplicacao {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProcessadorDeImagens());
    }
}
