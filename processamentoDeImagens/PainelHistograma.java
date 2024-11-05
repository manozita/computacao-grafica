package processamentoDeImagens;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * Classe para exibir o histograma de uma imagem.
 */
class PainelHistograma extends JPanel {

    private int[] histograma;

    /**
     * Construtor. Recebe o histograma a ser exibido.
     *
     * @param histograma Array contendo os valores do histograma.
     */
    public PainelHistograma(int[] histograma) {
        this.histograma = Arrays.copyOf(histograma, histograma.length);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int largura = getWidth();
        int altura = getHeight();

        int maximo = Arrays.stream(histograma).max().orElse(1);

        // Desenha o histograma
        for (int i = 0; i < histograma.length; i++) {
            int valor = histograma[i];
            int alturaBarra = (int) (((double) valor / maximo) * (altura - 20));
            g.setColor(Color.GRAY);
            g.fillRect(i * (largura / 256), altura - alturaBarra, (largura / 256), alturaBarra);
        }
    }
}