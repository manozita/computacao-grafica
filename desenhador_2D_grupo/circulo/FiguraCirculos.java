package circulo;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Write a description of class FiguraCirculo here.
 *
 * @author (your name)
 * @version (a version number or a date)
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
       //r.desenharCirculoEq(g);
       //r.desenharCirculoTheta(g);
       r.desenharCirculoSim(g);
    }

}
