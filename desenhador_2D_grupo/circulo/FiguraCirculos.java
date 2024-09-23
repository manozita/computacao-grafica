package circulo;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * Write a description of class FiguraCirculo here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class FiguraCirculos
{
    public static ArrayList Circulos = new ArrayList();
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

    public static void guardarCirculo(Graphics g, int x1, int y1, int x2, int y2, String nome, int esp, Color cor)
    {
        CirculoGr r = new CirculoGr(x1, y1, x2, y2, cor, nome, esp);
        Circulos.add(r);
    }
    
    public static void redefinirCirculos(Graphics g)
    {
        CirculoGr r;
        for (int i = 0; i < Circulos.size(); i++) {
            r = (CirculoGr)Circulos.get(i);
            desenharCirculo(g, (int)r.getP1().getX(), (int)r.getP1().getY(), (int)r.getP2().getX(), (int)r.getP2().getY(), r.getNomeCirculo(), r.getEspCirculo(), r.getCorCirculo());
        }
    }
}
