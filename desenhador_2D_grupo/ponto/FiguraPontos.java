package ponto;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * Contém métodos para desenhar figuras com ponto
 * 
 * @author Julio Arakaki
 * @version 20220815
 */
public class FiguraPontos {
    public static ArrayList Pontos = new ArrayList();
    /**
     * Desenha um ponto na tela
     * @param g biblioteca grafica para desenhar elementos gráficos
     * @param x coordena x do ponto
     * @param y coordenada y do ponto
     * @param nome nome do ponto
     * @param diametro diametro do ponto
     * @param cor cor do ponto
     */
    public static void desenharPonto (Graphics g, int x, int y, String nome, int diametro, Color cor) {
        // Color cor = new Color((int) (Math.random() * 256),  
        // (int) (Math.random() * 256),  
        // (int) (Math.random() * 256));
        PontoGr p = new PontoGr(x, y, cor, nome, diametro);
        p.desenharPonto(g);
    }
    
    public static void redesenharPonto(Graphics g, PontoGr p){
        int x = (int)p.getX();
        int y = (int)p.getY();
        Color cor = p.getCorPto();
        String nome = p.getNomePto();
        int diametro = p.getDiametro();

        PontoGr pg = new PontoGr(x, y, cor, nome, diametro);
        pg.desenharPonto(g);
    }
    
    public static void guardarPonto(Graphics g, int x1, int y1, String nome, int esp, Color cor)
    {
        PontoGr r = new PontoGr(x1, y1, cor, nome, esp);
        Pontos.add(r);
        System.out.println(Pontos.isEmpty());
    }
}
