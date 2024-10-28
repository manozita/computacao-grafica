package retangulo;
import java.awt.Color;
import java.awt.Graphics;

import ponto.PontoGr;
import reta.*;

/**
 * Implementacao da classe reta grafica.
 *
 * @author Julio Arakaki
 * @version 1.0 - 24/08/2020
 */
public class RetanguloGr extends Retangulo {
    // Atributos da reta grafica
    Color corRetangulo = Color.BLACK;   // cor da Retangulo
    String nomeRetangulo = ""; // nome da Retangulo
    Color corNomeRetangulo  = Color.BLACK;
    int espRetangulo = 1; // espessura da Retangulo

    // Construtores
    /**
     * RetanguloGr - Constroi uma Retangulo grafica
     *
     * @param x1 int. Coordenada x1
     * @param y1 int. Coordenada y1
     * @param x2 int. Coordenada x2
     * @param y2 int. Coordenada y2
     * @param cor Color. Cor da Retangulo
     * @param nome String. Nome da Retangulo
     * @param esp int. Espessura da Retangulo
     */
    public RetanguloGr(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, Color cor, String nome, int esp){
        super (x1, y1, x2, y2, x3, y3, x4, y4);
        setCorRetangulo(cor);
        setNomeRetangulo(nome);
        setEspRetangulo(esp);
    }        

    /**
     * Altera a cor da Retangulo.
     *
     * @param cor Color. Cor da Retangulo.
     */
    public void setCorRetangulo(Color cor) {
        this.corRetangulo = cor;
    }

    /**
     * Altera o nome da Retangulo.
     *
     * @param str String. Nome da Retangulo.
     */
    public void setNomeRetangulo(String str) {
        this.nomeRetangulo = str;
    }

    /**
     * Altera a espessura da Retangulo.
     *
     * @param esp int. Espessura da Retangulo.
     */
    public void setEspRetangulo(int esp) {
        this.espRetangulo = esp;
    }

    /**
     * Retorna a espessura da Retangulo.
     *
     * @return int. Espessura da Retangulo.
     */
    public int getEspRetangulo() {
        return(this.espRetangulo);
    }

    /**
     * Retorna a cor da Retangulo.
     *
     * @return Color. Cor da Retangulo.
     */
    public Color getCorRetangulo() {
        return corRetangulo;
    }

    /**
     * Retorna o nome da Retangulo.
     *
     * @return String. Nome da Retangulo.
     */
    public String getNomeRetangulo() {
        return nomeRetangulo;
    }

    /**
     * @return the corNomeRetangulo
     */
    public Color getCorNomeRetangulo() {
        return corNomeRetangulo;
    }

    /**
     * @param corNomeRetangulo the corNomeRetangulo to set
     */
    public void setCorNomeRetangulo(Color corNomeRetangulo) {
        this.corNomeRetangulo = corNomeRetangulo;
    }

    /**
     * Desenha Retangulo grafica utilizando a equacao da Retangulo: y = mx + b
     *
     * @param g Graphics. Classe com os metodos graficos do Java
     */
    public void desenharRetangulo(Graphics g){
        RetaGr r = new RetaGr((int)p1.getX(), (int)p1.getY(), (int)p3.getX(), (int)p3.getY(), getCorRetangulo(), getNomeRetangulo(), getEspRetangulo());
        r.desenharReta(g);
        RetaGr s = new RetaGr((int)p3.getX(), (int)p3.getY(), (int)p2.getX(), (int)p2.getY(), getCorRetangulo(), getNomeRetangulo(), getEspRetangulo());
        s.desenharReta(g);
        RetaGr t = new RetaGr((int)p2.getX(), (int)p2.getY(), (int)p4.getX(), (int)p4.getY(), getCorRetangulo(), getNomeRetangulo(), getEspRetangulo());
        t.desenharReta(g);
        RetaGr h = new RetaGr((int)p4.getX(), (int)p4.getY(), (int)p1.getX(), (int)p1.getY(), getCorRetangulo(), getNomeRetangulo(), getEspRetangulo());
        h.desenharReta(g);
    }
}

