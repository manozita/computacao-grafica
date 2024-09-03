package circulo;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Implementacao da classe figura circulos.
 *
 * @author João, Kauã, Luan e Manoela
 * @version 1.0 - 27/08/2024
 */
public class FiguraCirculos
{
    /**
     * Desenha uma Circulo de acordo com os pontos p1 e p2
     *
     * @param g biblioteca para desenhar o primitivo grafico
     * @param x1 coordenada x de p1
     * @param y1 coordenada y de p1
     * @param x2 coordenada x de p2
     * @param y2 coordenada y de p2
     * @param nome nome da Circulo
     * @param esp espessura da Circulo
     * @param cor cor da Circulo
     */
    public static void desenharCirculo(Graphics g, int x1, int y1, int x2, int y2, String nome, int esp, Color cor){
       CirculoGr r = new CirculoGr(x1, y1, x2, y2, cor, nome, esp);
       //r.desenharCirculoEq(g );
       //r.desenharCirculoTheta(g);
       r.desenharCirculoSim(g);
    }

    /**
     * Desenha varias Circulos na area de desenho
     *
     * @param g biblioteca grafica para desenhar os primitivos
     * @param qtde quantidade de Circulos
     * @param esp espessura das Circulos
     */
    public static void desenharCirculos(Graphics g, int qtde, int esp){

        for(int i=0; i < qtde; i++) {
            int x1 = (int) (Math.random() * 801);
            int y1 = (int) (Math.random() * 801);
            int x2 = (int) (Math.random() * 801);
            int y2 = (int) (Math.random() * 801);

            // Cor (R, G e B) aleatorio
            Color cor = new Color((int) (Math.random() * 256),  
                    (int) (Math.random() * 256),  
                    (int) (Math.random() * 256));
            CirculoGr r = new CirculoGr(x1, y1, x2, y2, cor, "", esp);
            //r.desenharCirculoEq(g);
            //r.desenharCirculoTheta(g);
            r.desenharCirculoSim(g);
        }
    }
}
