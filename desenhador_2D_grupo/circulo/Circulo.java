package circulo;
import ponto.Ponto;
import java.lang.Math;

/**
 * Reta matematica.
 *
 * @author Julio
 * @version 12/08/2020
 */
public class Circulo {
 
    // Atributos da reta
    public Ponto p1, p2;

    /**
     * Constroi uma reta com valores (int) de x1, y1 e x2, y2
     *
     * @param x1 coordenada x de p1
     * @param y1 coordenada y de p1
     * @param x2 coordenada x de p2
     * @param y2 coordenada y de p2
     */
    public Circulo(int x1, int y1, int x2, int y2) {
        setP1(new Ponto(x1, y1));
        setP2(new Ponto(x2, y2));
    }
    
    /**
     * Constroi uma Circulo com valores (double) de x1, y1 e x2, y2
     *
     * @param x1 coordenada x de p1
     * @param y1 coordenada y de p1
     * @param x2 coordenada x de p2
     * @param y2 coordenada y de p2
     */
    public Circulo(double x1, double y1, double x2, double y2) {
        setP1(new Ponto(x1, y1));
        setP2(new Ponto(x2, y2));
    }
    
    /**
     * Controi uma Circulo com valores de p1 e p2 (externos)
     *
     * @param p1 Um parâmetro
     * @param p2 Um parâmetro
     */
    public Circulo(Ponto p1, Ponto p2) {
        setP1(p1);
        setP2(p2);
    }
    
    /**
     * Constroi uma Circulo com dados de outra (externa)
     *
     * @param r Circulo externa
     */
    public Circulo (Circulo r){
        setP1(r.getP1());
        setP2(r.getP2());
    }
    
    /**
     * Altera valor de p1 de acordo com o parametro
     *
     * @param p valor de p1 (externo)
     */
    public void setP1(Ponto p){
        this.p1 = p;
    }
    
    /**
     * Altera valor de p2 de acordo com o parametro
     *
     * @param p valor de p2 (externo)
     */
    public void setP2(Ponto p){
        this.p2 = p;
    }
    
    /**
     * Retorna valor de p1
     *
     * @return valor de p1
     */
    public Ponto getP1(){
        return this.p1;
    }
    
    /**
     * Retorna p2
     *
     * @return valor de p2
     */
    public Ponto getP2(){
        return this.p2;
    }
    
    /**
     * Calcula o raio para utilizar na equação da circunferencia
     *
     * @return 
     */
    public double calculaRaio(Ponto p1, Ponto p2)
    {
        double raio;
        raio = Math.sqrt(Math.pow((p2.getX()-p1.getX()), 2) + Math.pow((p2.getY()-p1.getY()), 2));
        return raio;
    }
    
    /**
     * 
     *
     * @return 
    */
    public double calculaXPositivo(double y, double r)
    {
        double x;
        x = p1.getX() + Math.sqrt(r*r-Math.pow((y-p1.getY()), 2));
        return x;
    }
    public double calculaXNegativo(double y, double r)
    {
        double x;
        x = p1.getX() - Math.sqrt(r*r-Math.pow((y-p1.getY()), 2));
        return x;
    }
    
    public double calculaYPositivo(double x, double r)
    {
        double y;
        y = p1.getY() + Math.sqrt(r*r-Math.pow((x-p1.getX()), 2));
        return y;
    }
    public double calculaYNegativo(double x, double r)
    {
        double y;
        y = p1.getY() - Math.sqrt(r*r-Math.pow((x-p1.getX()), 2));
        return y;
    }
    
    // /**
     // * Imprime a equacao de Circulo no formato (y = mx + b)
     // *
     // * @return string que representa a Circulo
     // */
    // public String toString(){
        // String s = "P1: " + getP1().toString() + " P2: " + getP2().toString();
        // s = s + "\nEq. da Circulo: y = " + calcularM() + "*x + " + calcularB();
        // return s;
    // }
   
}
