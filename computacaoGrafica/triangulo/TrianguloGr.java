package triangulo;
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
public class TrianguloGr extends Triangulo {
    // Atributos da reta grafica
    Color corTriangulo = Color.BLACK;   // cor da Triangulo
    String nomeTriangulo = ""; // nome da Triangulo
    Color corNomeTriangulo  = Color.BLACK;
    int espTriangulo = 1; // espessura da Triangulo

    // Construtores
    /**
     * TrianguloGr - Constroi uma Triangulo grafica
     *
     * @param x1 int. Coordenada x1
     * @param y1 int. Coordenada y1
     * @param x2 int. Coordenada x2
     * @param y2 int. Coordenada y2
     * @param cor Color. Cor da Triangulo
     * @param nome String. Nome da Triangulo
     * @param esp int. Espessura da Triangulo
     */
    public TrianguloGr(int x1, int y1, int x2, int y2, int x3, int y3, Color cor, String nome, int esp){
        super (x1, y1, x2, y2, x3, y3);
        setCorTriangulo(cor);
        setNomeTriangulo(nome);
        setEspTriangulo(esp);
    }    

    /**
     * TrianguloGr - Constroi uma Triangulo grafica
     *
     * @param x1 int. Coordenada x1
     * @param y1 int. Coordenada y1
     * @param x2 int. Coordenada x2
     * @param y2 int. Coordenada y2
     * @param cor Color. Cor da Triangulo
     */
    public TrianguloGr(int x1, int y1, int x2, int y2, int x3, int y3, Color cor){
        super (x1, y1, x2, y2, x3, y3);
        setCorTriangulo(cor);
        setNomeTriangulo("");
    }   

    /**
     * TrianguloGr - Constroi uma Triangulo grafica
     *
     * @param x1 int. Coordenada x1
     * @param y1 int. Coordenada y1
     * @param x2 int. Coordenada x2
     * @param y2 int. Coordenada y2
     * @param cor Color. Cor da Triangulo
     * @param esp int. Espessura da Triangulo
     */
    public TrianguloGr(int x1, int y1, int x2, int y2, int x3, int y3, Color cor, int esp){
        super (x1, y1, x2, y2, x3, y3);
        setCorTriangulo(cor);
        setNomeTriangulo("");
        setEspTriangulo(esp);
    }   

    /**
     * TrianguloGr - Constroi uma Triangulo grafica
     *
     * @param x1 int. Coordenada x1
     * @param y1 int. Coordenada y1
     * @param x2 int. Coordenada x2
     * @param y2 int. Coordenada y2
     */
    public TrianguloGr(int x1, int y1, int x2, int y2, int x3, int y3){
        super (x1, y1, x2, y2, x3, y3);
        setCorTriangulo(Color.black);
        setNomeTriangulo("");
    }   

    /**
     * TrianguloGr - Constroi uma Triangulo grafica
     *
     * @param p1 PontoGr. Ponto grafico p1 (x1, y1)
     * @param p2 PontoGr. Ponto grafico p2 (x2, y2)
     */
    public TrianguloGr(PontoGr p1, PontoGr p2, PontoGr p3){
        super(p1, p2, p3);
        setCorTriangulo(Color.black);
        setNomeTriangulo("");
    }    

    /**
     * TrianguloGr - Constroi uma Triangulo grafica
     *
     * @param p1 PontoGr. Ponto grafico p1 (x1, y1)
     * @param p2 PontoGr. Ponto grafico p2 (x2, y2)
     * @param cor Color. Cor da Triangulo
     */
    public TrianguloGr(PontoGr p1, PontoGr p2, PontoGr p3, Color cor){
        super(p1, p2, p3);
        setCorTriangulo(cor);
        setNomeTriangulo("");
    }    

    /**
     * TrianguloGr - Constroi uma Triangulo grafica
     *
     * @param p1 PontoGr. Ponto grafico p1 (x1, y1)
     * @param p2 PontoGr. Ponto grafico p2 (x2, y2)
     * @param cor Color. Cor da Triangulo
     * @param nome String. Nome da Triangulo
     */
    public TrianguloGr(PontoGr p1, PontoGr p2, PontoGr p3, Color cor, String str){
        super(p1, p2, p3);
        setCorTriangulo(cor);
        setNomeTriangulo(str);
    }    

    /**
     * Altera a cor da Triangulo.
     *
     * @param cor Color. Cor da Triangulo.
     */
    public void setCorTriangulo(Color cor) {
        this.corTriangulo = cor;
    }

    /**
     * Altera o nome da Triangulo.
     *
     * @param str String. Nome da Triangulo.
     */
    public void setNomeTriangulo(String str) {
        this.nomeTriangulo = str;
    }

    /**
     * Altera a espessura da Triangulo.
     *
     * @param esp int. Espessura da Triangulo.
     */
    public void setEspTriangulo(int esp) {
        this.espTriangulo = esp;
    }

    /**
     * Retorna a espessura da Triangulo.
     *
     * @return int. Espessura da Triangulo.
     */
    public int getEspTriangulo() {
        return(this.espTriangulo);
    }

    /**
     * Retorna a cor da Triangulo.
     *
     * @return Color. Cor da Triangulo.
     */
    public Color getCorTriangulo() {
        return corTriangulo;
    }

    /**
     * Retorna o nome da Triangulo.
     *
     * @return String. Nome da Triangulo.
     */
    public String getNomeTriangulo() {
        return nomeTriangulo;
    }

    /**
     * @return the corNomeTriangulo
     */
    public Color getCorNomeTriangulo() {
        return corNomeTriangulo;
    }

    /**
     * @param corNomeTriangulo the corNomeTriangulo to set
     */
    public void setCorNomeTriangulo(Color corNomeTriangulo) {
        this.corNomeTriangulo = corNomeTriangulo;
    }

    /**
     * Desenha Triangulo grafica utilizando a equacao da Triangulo: y = mx + b
     *
     * @param g Graphics. Classe com os metodos graficos do Java
     */
    public void desenharTriangulo(Graphics g){
        RetaGr r = new RetaGr((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY(), getCorTriangulo(), getNomeTriangulo(), getEspTriangulo());
        r.desenharReta(g);
        RetaGr s = new RetaGr((int)p2.getX(), (int)p2.getY(), (int)p3.getX(), (int)p3.getY(), getCorTriangulo(), getNomeTriangulo(), getEspTriangulo());
        s.desenharReta(g);
        RetaGr t = new RetaGr((int)p3.getX(), (int)p3.getY(), (int)p1.getX(), (int)p1.getY(), getCorTriangulo(), getNomeTriangulo(), getEspTriangulo());
        t.desenharReta(g);
    }
}

