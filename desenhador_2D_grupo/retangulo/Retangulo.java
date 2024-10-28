package retangulo;
import ponto.Ponto;

/**
 * Retangulo matematica.
 *
 * @author Julio
 * @version 12/08/2020
 */
public class Retangulo {
 
    // Atributos da Retangulo
    public Ponto p1, p2, p3, p4;

    /**
     * Constroi uma Retangulo com valores (int) de x1, y1, x2, y2, x3, y3 e x4, y4
     *
     * @param x1 coordenada x de p1
     * @param y1 coordenada y de p1
     * @param x2 coordenada x de p2
     * @param y2 coordenada y de p2
     * @param x3 coordenada x de p3
     * @param y3 coordenada y de p3
     * @param x4 coordenada x de p4
     * @param y4 coordenada y de p4
     */
    public Retangulo(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        setP1(new Ponto(x1, y1));
        setP2(new Ponto(x2, y2));
        setP3(new Ponto(x3, y3));
        setP4(new Ponto(x4, y4));
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
     * Altera valor de p3 de acordo com o parametro
     *
     * @param p valor de p3 (externo)
     */
    public void setP3(Ponto p){
        this.p3 = p;
    }
    
    /**
     * Altera valor de p4 de acordo com o parametro
     *
     * @param p valor de p4 (externo)
     */
    public void setP4(Ponto p){
        this.p4 = p;
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
     * Retorna valor de p2
     *
     * @return valor de p2
     */
    public Ponto getP2(){
        return this.p2;
    }
    
    /**
     * Retorna valor de p3
     *
     * @return valor de p3
     */
    public Ponto getP3(){
        return this.p3;
    }
    
    /**
     * Retorna valor de p4
     *
     * @return valor de p4
     */
    public Ponto getP4(){
        return this.p4;
    }
}
