package circulo;
import java.awt.Color;
import java.awt.Graphics;

import ponto.PontoGr;

/**
 * Implementacao da classe circulo grafico.
 *
 * @author João, Kauã, Luan e Manoela
 * @version 1.0 - 27/08/2024
 */
public class CirculoGr extends Circulo {
    // Atributos da Circulo grafica
    Color corCirculo = Color.BLACK;   // cor da Circulo
    String nomeCirculo = ""; // nome da Circulo
    Color corNomeCirculo  = Color.BLACK;
    int espCirculo = 1; // espessura da Circulo

    // Construtores
    /**
     * CirculoGr - Constroi um Circulo grafico
     *
     * @param x1 int. Coordenada x1
     * @param y1 int. Coordenada y1
     * @param x2 int. Coordenada x2
     * @param y2 int. Coordenada y2
     * @param cor Color. Cor da Circulo
     * @param nome String. Nome da Circulo
     * @param esp int. Espessura da Circulo
     */
    public CirculoGr(int x1, int y1, int x2, int y2, Color cor, String nome, int esp){
        super (x1, y1, x2, y2);
        setCorCirculo(cor);
        setNomeCirculo(nome);
        setEspCirculo(esp);
    }    

    /**
     * CirculoGr - Constroi um Circulo grafico
     *
     * @param x1 int. Coordenada x1
     * @param y1 int. Coordenada y1
     * @param x2 int. Coordenada x2
     * @param y2 int. Coordenada y2
     * @param cor Color. Cor da Circulo
     */
    public CirculoGr(int x1, int y1, int x2, int y2, Color cor){
        super (x1, y1, x2, y2);
        setCorCirculo(cor);
        setNomeCirculo("");
    }   

    /**
     * CirculoGr - Constroi um Circulo grafico
     *
     * @param x1 int. Coordenada x1
     * @param y1 int. Coordenada y1
     * @param x2 int. Coordenada x2
     * @param y2 int. Coordenada y2
     * @param cor Color. Cor do Circulo
     * @param esp int. Espessura do Circulo
     */
    public CirculoGr(int x1, int y1, int x2, int y2, Color cor, int esp){
        super (x1, y1, x2, y2);
        setCorCirculo(cor);
        setNomeCirculo("");
        setEspCirculo(esp);
    }   

    /**
     * CirculoGr - Constroi um Circulo grafico
     *
     * @param x1 int. Coordenada x1
     * @param y1 int. Coordenada y1
     * @param x2 int. Coordenada x2
     * @param y2 int. Coordenada y2
     */
    public CirculoGr(int x1, int y1, int x2, int y2){
        super (x1, y1, x2, y2);
        setCorCirculo(Color.black);
        setNomeCirculo("");
    }   

    /**
     * CirculoGr - Constroi um Circulo grafico
     *
     * @param p1 PontoGr. Ponto grafico p1 (x1, y1)
     * @param p2 PontoGr. Ponto grafico p2 (x2, y2)
     */
    public CirculoGr(PontoGr p1, PontoGr p2){
        super(p1, p2);
        setCorCirculo(Color.black);
        setNomeCirculo("");
    }    

    /**
     * CirculoGr - Constroi um Circulo grafico
     *
     * @param p1 PontoGr. Ponto grafico p1 (x1, y1)
     * @param p2 PontoGr. Ponto grafico p2 (x2, y2)
     * @param cor Color. Cor da Circulo
     */
    public CirculoGr(PontoGr p1, PontoGr p2, Color cor){
        super(p1, p2);
        setCorCirculo(cor);
        setNomeCirculo("");
    }    

    /**
     * CirculoGr - Constroi um Circulo grafico
     *
     * @param p1 PontoGr. Ponto grafico p1 (x1, y1)
     * @param p2 PontoGr. Ponto grafico p2 (x2, y2)
     * @param cor Color. Cor da Circulo
     * @param nome String. Nome da Circulo
     */
    public CirculoGr(PontoGr p1, PontoGr p2, Color cor, String str){
        super(p1, p2);
        setCorCirculo(cor);
        setNomeCirculo(str);
    }    

    /**
     * Altera a cor do Circulo.
     *
     * @param cor Color. Cor da Circulo.
     */
    public void setCorCirculo(Color cor) {
        this.corCirculo = cor;
    }

    /**
     * Altera o nome do Circulo.
     *
     * @param str String. Nome da Circulo.
     */
    public void setNomeCirculo(String str) {
        this.nomeCirculo = str;
    }

    /**
     * Altera a espessura do Circulo.
     *
     * @param esp int. Espessura da Circulo.
     */
    public void setEspCirculo(int esp) {
        this.espCirculo = esp;
    }

    /**
     * Retorna a espessura do Circulo.
     *
     * @return int. Espessura do Circulo.
     */
    public int getEspCirculo() {
        return(this.espCirculo);
    }

    /**
     * Retorna a cor do Circulo.
     *
     * @return Color. Cor do Circulo.
     */
    public Color getCorCirculo() {
        return corCirculo;
    }

    /**
     * Retorna o nome do Circulo.
     *
     * @return String. Nome do Circulo.
     */
    public String getNomeCirculo() {
        return nomeCirculo;
    }

    /**
     * @return the corNomeCirculo
     */
    public Color getCorNomeCirculo() {
        return corNomeCirculo;
    }

    /**
     * @param corNomeCirculo the corNomeCirculo to set
     */
    public void setCorNomeCirculo(Color corNomeCirculo) {
        this.corNomeCirculo = corNomeCirculo;
    }

    /**
     * Desenha Circulo grafico utilizando a equacao do Circulo
     *
     * @param g Graphics. Classe com os metodos graficos do Java
     */
    public void desenharCirculoEq(Graphics g){
        PontoGr ponto; 
        double x, y, raio;
        
        // desenha nome do ponto
        g.setColor(getCorNomeCirculo());
        g.drawString(getNomeCirculo(), (int)getP1().getX() + getEspCirculo(), (int)getP1().getY());
        raio = calculaRaio(p1, p2);
        
        for(y = p1.getY(); y <= p1.getY()+raio; y++) //Percorre o eixo y do centro ao raio "para cima"
        {
            x = calculaXPositivo(y, raio); //Calcula os pontos de x correspondentes
        // Define ponto grafico
            ponto = new PontoGr((int)x, (int)y, getCorCirculo(), getEspCirculo());
            ponto.desenharPonto(g);
            
            x = calculaXNegativo(y, raio); //Calcula os pontos de x correspondentes
        // Define ponto grafico
            ponto = new PontoGr((int)x, (int)y, getCorCirculo(), getEspCirculo());
            ponto.desenharPonto(g);
        }
        
        for(y = p1.getY(); y >= p1.getY() - raio; y--) //Percorre o eixo y do centro ao raio "para baixo"
        {
            x = calculaXPositivo(y, raio);
            // Define ponto gráfico para o lado direito do círculo
            ponto = new PontoGr((int)x, (int)y, getCorCirculo(), getEspCirculo()); //Calcula os pontos de x correspondentes
            ponto.desenharPonto(g);
            
            x = calculaXNegativo(y, raio);
            // Define ponto gráfico para o lado esquerdo do círculo
            ponto = new PontoGr((int)x, (int)y, getCorCirculo(), getEspCirculo()); //Calcula os pontos de x correspondentes
            ponto.desenharPonto(g);
        }
        
        for(x = p1.getX(); x <= p1.getX()+raio; x++) //Percorre o eixo x do centro ao raio "para a direita"
        {
            y = calculaYPositivo(x, raio);
        // Define ponto grafico
            ponto = new PontoGr((int)x, (int)y, getCorCirculo(), getEspCirculo()); //Calcula os pontos de y correspondentes
            ponto.desenharPonto(g);
            
            y = calculaYNegativo(x, raio);
        // Define ponto grafico
            ponto = new PontoGr((int)x, (int)y, getCorCirculo(), getEspCirculo()); //Calcula os pontos de y correspondentes
            ponto.desenharPonto(g);
        }
        
        for(x = p1.getX(); x >= p1.getX()-raio; x--) //Percorre o eixo x do centro ao raio "para a esquerda"
        {
            y = calculaYPositivo(x, raio);
        // Define ponto grafico
            ponto = new PontoGr((int)x, (int)y, getCorCirculo(), getEspCirculo()); //Calcula os pontos de y correspondentes
            ponto.desenharPonto(g);
            
            y = calculaYNegativo(x, raio);
        // Define ponto grafico
            ponto = new PontoGr((int)x, (int)y, getCorCirculo(), getEspCirculo()); //Calcula os pontos de y correspondentes
            ponto.desenharPonto(g);
        }
    }
    
    /**
     * Desenha Circulo grafico a partir dos ângulos
     *
     * @param g Graphics. Classe com os metodos graficos do Java
     */
    public void desenharCirculoTheta(Graphics g)
    {
        PontoGr ponto; 
        double x, y, raio;
        
        // desenha nome do ponto
        g.setColor(getCorNomeCirculo());
        g.drawString(getNomeCirculo(), (int)getP1().getX() + getEspCirculo(), (int)getP1().getY());
        raio = calculaRaio(p1, p2);
        
        for (int theta = 0; theta < 360; theta++) { //Percorre os 360 angulos
            double radianos = Math.toRadians(theta);  // Converte o ângulo para radianos
            x = (int) (p1.getX() + raio * Math.cos(radianos));  //Calcula x e y pela relação de simetria
            y = (int) (p1.getY() + raio * Math.sin(radianos));
            
            // Desenha o ponto na circunferência
            ponto = new PontoGr((int)x, (int)y, getCorCirculo(), getEspCirculo());
            ponto.desenharPonto(g);
        }
    }
    
    /**
     * Desenha Circulo grafico a partir do algoritmo de círculo com a correspondencia simetrica
     *
     * @param g Graphics. Classe com os metodos graficos do Java
     */
    public void desenharCirculoSim(Graphics g)
    {
        PontoGr ponto; 
        double x, y, raio;
        
        g.setColor(getCorNomeCirculo());
        g.drawString(getNomeCirculo(), (int)getP1().getX() + getEspCirculo(), (int)getP1().getY());
        
        raio = calculaRaio(p1, p2);
        x = 0;
        y = raio;
        double d = 3 - 2 * raio;
        
        while (x <= y) {
            // Desenhar os pontos calculando os outros 7 octantes por simetria
            plotarPontosSimetricos(g, x, y);
    
            // Atualiza o valor de x e y
            if (d < 0) {
                d = d + 4 * x + 6;
            } else {
                d = d + 4 * (x - y) + 10;
                y--;
            }
            x++;
        }
    }
    
    /**
     * Plota os pontos por simetria
     *
     * @param g Graphics. Classe com os metodos graficos do Java
     */
    private void plotarPontosSimetricos(Graphics g, double x, double y) {
        PontoGr ponto;
    
        // Octantes
        ponto = new PontoGr((int)(p1.getX()+x), (int)(p1.getY()+y), getCorCirculo(), getEspCirculo());
        ponto.desenharPonto(g);
        ponto = new PontoGr((int)(p1.getX()-x), (int)(p1.getY()+y), getCorCirculo(), getEspCirculo());
        ponto.desenharPonto(g);
        ponto = new PontoGr((int)(p1.getX()+x), (int)(p1.getY()-y), getCorCirculo(), getEspCirculo());
        ponto.desenharPonto(g);
        ponto = new PontoGr((int)(p1.getX()-x), (int)(p1.getY()-y), getCorCirculo(), getEspCirculo());
        ponto.desenharPonto(g);
        ponto = new PontoGr((int)(p1.getX()+y), (int)(p1.getY()+x), getCorCirculo(), getEspCirculo());
        ponto.desenharPonto(g);
        ponto = new PontoGr((int)(p1.getX()-y), (int)(p1.getY()+x), getCorCirculo(), getEspCirculo());
        ponto.desenharPonto(g);
        ponto = new PontoGr((int)(p1.getX()+y), (int)(p1.getY()-x), getCorCirculo(), getEspCirculo());
        ponto.desenharPonto(g);
        ponto = new PontoGr((int)(p1.getX()-y), (int)(p1.getY()-x), getCorCirculo(), getEspCirculo());
        ponto.desenharPonto(g);
    }
}

