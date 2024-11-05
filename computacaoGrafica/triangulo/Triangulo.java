package triangulo;
import ponto.Ponto;
import reta.*;

/**
 * Triangulo matematica.
 *
 * @author Julio
 * @version 12/08/2020
 */
public class Triangulo {
 
    // Atributos da Triangulo
    public Ponto p1, p2, p3;

    /**
     * Constroi uma Triangulo com valores (int) de x1, y1 e x2, y2
     *
     * @param x1 coordenada x de p1
     * @param y1 coordenada y de p1
     * @param x2 coordenada x de p2
     * @param y2 coordenada y de p2
     */
    public Triangulo(int x1, int y1, int x2, int y2, int x3, int y3) {
        setP1(new Ponto(x1, y1));
        setP2(new Ponto(x2, y2));
        setP3(new Ponto(x3, y3));
    }
    
    /**
     * Constroi uma Triangulo com valores (double) de x1, y1 e x2, y2
     *
     * @param x1 coordenada x de p1
     * @param y1 coordenada y de p1
     * @param x2 coordenada x de p2
     * @param y2 coordenada y de p2
     */
    public Triangulo(double x1, double y1, double x2, double y2, double x3, double y3) {
        setP1(new Ponto(x1, y1));
        setP2(new Ponto(x2, y2));
        setP3(new Ponto(x3, y3));
    }
    
    /**
     * Controi uma Triangulo com valores de p1 e p2 (externos)
     *
     * @param p1 Um par�metro
     * @param p2 Um par�metro
     */
    public Triangulo(Ponto p1, Ponto p2, Ponto p3) {
        setP1(p1);
        setP2(p2);
        setP3(p3);
    }
    
    /**
     * Constroi uma Triangulo com dados de outra (externa)
     *
     * @param r Triangulo externa
     */
    public Triangulo (Triangulo r){
        setP1(r.getP1());
        setP2(r.getP2());
        setP2(r.getP3());
    }

    public void setP1(Ponto p){
        this.p1 = p;
    }

    public void setP2(Ponto p){
        this.p2 = p;
    }

    public void setP3(Ponto p){
        this.p3 = p;
    }

    public Ponto getP1(){
        return this.p1;
    }

    public Ponto getP2(){
        return this.p2;
    }

    public Ponto getP3(){
        return this.p3;
    }
    
    
}
