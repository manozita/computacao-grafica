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
    public static ArrayList Retas = new ArrayList();
    public static ArrayList Retangulos = new ArrayList();
    public static ArrayList Triangulos = new ArrayList();
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

    /**
     * Desenha varias retas na area de desenho
     *
     * @param g biblioteca grafica para desenhar os primitivos
     * @param qtde quantidade de retas
     * @param esp espessura das retas
     */
    public static void desenharRetas (Graphics g, int qtde, int esp) {
        for(int i=0; i < qtde; i++) {
            int x1 = (int) (Math.random() * 801);
            int y1 = (int) (Math.random() * 801);
            int x2 = (int) (Math.random() * 801);
            int y2 = (int) (Math.random() * 801);

            // Cor (R, G e B) aleatorio
            Color cor = new Color((int) (Math.random() * 256),  
                    (int) (Math.random() * 256),  
                    (int) (Math.random() * 256));
            RetaGr r = new RetaGr(x1, y1, x2, y2, cor, "", esp);
            r.desenharReta(g);
        }
    }

    public static void desenharRetangulo(Graphics g, int x1, int y1, int x2, int y2, String nome, int esp, Color cor){
        RetanguloGr r = new RetanguloGr(x1, y1, x2, y2, cor, nome, esp);
        r.desenharRetangulo(g);
    }

    public static void desenharTriangulo(Graphics g, int x1, int y1, int x2, int y2, int x3, int y3, String nome, int esp, Color cor){
        TrianguloGr t = new TrianguloGr(x1, y1, x2, y2, x3, y3, cor, nome, esp);
        t.desenharTriangulo(g);
    }
    
    public static void guardarReta(Graphics g, int x1, int y1, int x2, int y2, String nome, int esp, Color cor)
    {
        RetaGr r = new RetaGr(x1, y1, x2, y2, cor, nome, esp);
        Retas.add(r);
    }
    
    public static void guardarRetangulo(Graphics g, int x1, int y1, int x2, int y2, String nome, int esp, Color cor)
    {
        RetanguloGr r = new RetanguloGr(x1, y1, x2, y2, cor, nome, esp);
        Retangulos.add(r);
    }
    
    public static void guardarTriangulo(Graphics g, int x1, int y1, int x2, int y2, int x3, int y3, String nome, int esp, Color cor)
    {
        TrianguloGr r = new TrianguloGr(x1, y1, x2, y2, x3, y3, cor, nome, esp);
        Triangulos.add(r);
    }
    
    public static void redefinirRetas(Graphics g)
    {
        RetaGr r;
        for (int i = 0; i < Retas.size(); i++) {
            r = (RetaGr)Retas.get(i);
            desenharReta(g, (int)r.getP1().getX(), (int)r.getP1().getY(), (int)r.getP2().getX(), (int)r.getP2().getY(), r.getNomeReta(), r.getEspReta(), r.getCorReta());
        }
    }
    
    public static void redefinirRetangulos(Graphics g)
    {
        RetanguloGr r;
        for (int i = 0; i < Retangulos.size(); i++) {
            r = (RetanguloGr)Retangulos.get(i);
            desenharRetangulo(g, (int)r.getP1().getX(), (int)r.getP1().getY(), (int)r.getP2().getX(), (int)r.getP2().getY(), r.getNomeRetangulo(), r.getEspRetangulo(), r.getCorRetangulo());
        }
    }
    
    public static void redefinirTriangulos(Graphics g)
    {
        TrianguloGr r;
        for (int i = 0; i < Triangulos.size(); i++) {
            r = (TrianguloGr)Triangulos.get(i);
            desenharTriangulo(g, (int)r.getP1().getX(), (int)r.getP1().getY(), (int)r.getP2().getX(), (int)r.getP2().getY(), 
            (int)r.getP3().getX(), (int)r.getP3().getY(), r.getNomeTriangulo(), r.getEspTriangulo(), r.getCorTriangulo());
        }
    }
    
    public static void reinicializarRetas() {
        Retas.clear();
        Retangulos.clear();
        Triangulos.clear();
    }
}
