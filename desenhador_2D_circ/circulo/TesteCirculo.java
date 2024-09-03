package circulo;
import ponto.Ponto;

/**
 * Write a description of class TesteCirculo here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TesteCirculo
{
    public static void main(String args[])
    {
        Ponto p1 = new Ponto(1, 1);
        Ponto p2 = new Ponto(5, 5);
        Circulo c = new Circulo(p1, p2);
        double r;
        r = c.calculaRaio(p1, p2);
        System.out.println(r);
    }
}
