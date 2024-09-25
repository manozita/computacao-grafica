package reta;
import java.awt.Color;
import java.awt.Graphics;
import retangulo.RetanguloGr;
import triangulo.TrianguloGr;
import java.util.ArrayList;

/**
 * Desenha figuras com retas.
 * 
 * @author Julio Arakaki 
 * @version 20220815
 */
public class FiguraRetas {
    /**
     * Desenha uma reta de acordo com os pontos p1 e p2
     *
     * @param g biblioteca para desenhar o primitivo grafico
     * @param x1 coordenada x de p1
     * @param y1 coordenada y de p1
     * @param x2 coordenada x de p2
     * @param y2 coordenada y de p2
     * @param nome nome da reta
     * @param esp espessura da reta
     * @param cor cor da reta
     */
    public static void desenharReta (Graphics g, int x1, int y1, int x2, int y2, String nome, int esp, Color cor) {
        RetaGr r = new RetaGr(x1, y1, x2, y2, cor, nome, esp);
        r.desenharReta(g );
    }

    public static void desenharRetangulo(Graphics g, int x1, int y1, int x2, int y2, String nome, int esp, Color cor){
        RetanguloGr r = new RetanguloGr(x1, y1, x2, y2, cor, nome, esp);
        r.desenharRetangulo(g);
    }

    public static void desenharTriangulo(Graphics g, int x1, int y1, int x2, int y2, int x3, int y3, String nome, int esp, Color cor){
        TrianguloGr t = new TrianguloGr(x1, y1, x2, y2, x3, y3, cor, nome, esp);
        t.desenharTriangulo(g);
    }

}
