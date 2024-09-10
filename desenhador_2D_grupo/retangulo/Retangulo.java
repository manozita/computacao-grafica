package retangulo;
import ponto.Ponto;
import reta.*;

/**
 * Retangulo matematica.
 *
 * @author Julio
 * @version 12/08/2020
 */
public class Retangulo {
 
    // Atributos da Retangulo
    public Ponto p1, p2;

    /**
     * Constroi uma Retangulo com valores (int) de x1, y1 e x2, y2
     *
     * @param x1 coordenada x de p1
     * @param y1 coordenada y de p1
     * @param x2 coordenada x de p2
     * @param y2 coordenada y de p2
     */
    public Retangulo(int x1, int y1, int x2, int y2) {
        setP1(new Ponto(x1, y1));
        setP2(new Ponto(x2, y2));
    }
    
    /**
     * Constroi uma Retangulo com valores (double) de x1, y1 e x2, y2
     *
     * @param x1 coordenada x de p1
     * @param y1 coordenada y de p1
     * @param x2 coordenada x de p2
     * @param y2 coordenada y de p2
     */
    public Retangulo(double x1, double y1, double x2, double y2) {
        setP1(new Ponto(x1, y1));
        setP2(new Ponto(x2, y2));
    }
    
    /**
     * Controi uma Retangulo com valores de p1 e p2 (externos)
     *
     * @param p1 Um parâmetro
     * @param p2 Um parâmetro
     */
    public Retangulo(Ponto p1, Ponto p2) {
        setP1(p1);
        setP2(p2);
    }
    
    /**
     * Constroi uma Retangulo com dados de outra (externa)
     *
     * @param r Retangulo externa
     */
    public Retangulo (Retangulo r){
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
    
}
