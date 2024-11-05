package ponto;

import java.awt.Color;
import java.awt.Graphics;

public class PontoGr extends Ponto {
    Color corPto = Color.BLACK;         // Cor do ponto
    String nomePto = "";                // Nome do ponto
    Color corNomePto  = Color.BLACK;    // Cor do nome (string) do ponto  
    int diametro = 1;                   // Diâmetro do ponto, default = 1

    // CONSTRUTORES
    public PontoGr (int x, int y) { // Entrada com duas coordenadas x e y para o ponto
        super((double)x, (double)y);
    }
    public PontoGr (int x, int y, Color corPto) { // Entrada com duas coordenadas e cor do ponto
        super((double)x, (double)y);
        this.corPto = corPto;
    }
    public PontoGr (int x, int y, Color corPto, int diametro) { // Entrada com duas coordenadas, cor e diametro do ponto
        this(x, y, corPto);
        this.diametro = diametro;
    }
    public PontoGr (int x, int y, Color corPto, String nomePto, int diametro) { // Entrada com duas coordenadas, cor, nome e diametro do ponto
        this(x, y, corPto, diametro);
        this.nomePto = nomePto;
    }
    public PontoGr (int x, int y, Color corPto, String nomePto) { // Entrada com duas coordenadas, cor e nome do ponto
        super((double)x, (double)y);
        this.corPto = corPto;
        this.nomePto = nomePto; 
    }
    public PontoGr (PontoGr p2d, Color corPto) { // Entrada com um ponto e cor do ponto
        super(p2d);    
        this.corPto = corPto;   
    }
    public PontoGr() { // Entrada sem parâmetros
        super();       
    }

    /**
     * GETTERS E SETTERS
     * 
     * Para cor, nome, cor do nome e diâmetro
     */
    public Color getCorPto() {                    // Retorna a cor do ponto
        return corPto;
    }
    public void setCorPto (Color corPto) {        // Altera a cor do ponto
        this.corPto = corPto;
    }
    public String getNomePto() {                  // Retorna o nome do ponto
        return nomePto;
    }
    public void setNomePto(String nomePto) {      // Altera o nome do ponto
        this.nomePto = nomePto;
    }
    public Color getCorNomePto() {                // Retorna a cor do nome do ponto
        return corNomePto;
    }
    public void setCorNomePto(Color corNomePto) { // Altera a cor do nome do ponto
        this.corNomePto = corNomePto;
    }
    public int getDiametro() {                    // Retorna o diâmetro do ponto
        return diametro;
    }
    public void setDiametro(int diametro) {       // Altera o diâmetro do ponto
        this.diametro = diametro;
    }
    
    /**
     * MÉTODO desenharPonto
     *
     * Desenha um novo ponto e seu nome
     * @param g Um parâmetro
     */
    public void desenharPonto(Graphics g) {
        // Desenha ponto como um oval
        g.setColor(getCorPto());
        g.fillOval((int)getX() -(getDiametro()/2), (int)getY() - (getDiametro()/2), getDiametro(), getDiametro());

        // Desenha nome do ponto
        g.setColor(getCorNomePto());
        g.drawString(getNomePto(), (int)getX() + getDiametro(), (int)getY());
    }
}