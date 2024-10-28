package armazenamento;

import ponto.*;
import controller.TipoPrimitivo;
import java.util.ArrayList;
import java.awt.Color;

public class Primitivos // define os dados da figura
{
    private TipoPrimitivo tipo; // tipo da figura
    private ArrayList<Ponto> pontos = new ArrayList<>(); // array list com os pontos da figura
    private int espessura;
    private Color cor;
    
    /**
     * Primitivos Construtor
     *
     * Parametros: tipo de primitivo, pontos, espessura e cor
     */
    public Primitivos(TipoPrimitivo t, Integer x1, Integer y1, Integer x2, Integer y2, Integer x3, Integer y3, Integer x4, Integer y4, Integer esp, Color c)
    {
        tipo = t;
        
        // para que nenhuma coordenada seja NULL
        if(x1 == null) { x1 = 0; }
        if(y1 == null) { y1 = 0; }
        if(x2 == null) { x2 = 0; }
        if(y2 == null) { y2 = 0; }
        if(x3 == null) { x3 = 0; }
        if(y3 == null) { y3 = 0; }
        if(x3 == null) { x4 = 0; }
        if(y3 == null) { y4 = 0; }
        
        // cria um novo ponto p1 com as coordenadas
        Ponto p1 = new Ponto();
        p1.setX(x1); p1.setY(y1);
        pontos.add(p1);
        
        if(tipo != TipoPrimitivo.PONTO) // se for necessária a criação de um segundo ponto
        {
            Ponto p2 = new Ponto();
            p2.setX(x2);
            p2.setY(y2);
            pontos.add(p2);
            
            if(tipo == TipoPrimitivo.TRIANGULO || tipo == TipoPrimitivo.RETANGULO) // se for necesária a criação de um terceiro ponto
            {
                Ponto p3 = new Ponto();
                p3.setX(x3);
                p3.setY(y3);
                pontos.add(p3);       
            }
            if(tipo == TipoPrimitivo.RETANGULO)
            {
                Ponto p4 = new Ponto();
                p4.setX(x4);
                p4.setY(y4);
                pontos.add(p4); 
            }
        }
        
        // define a espessura e a cor
        espessura = esp;
        cor = c;
    }
    
    /**
     * Métodos getters
     *
     */
    public TipoPrimitivo getTipo()
    {
        return tipo;
    }
    public int getX1()
    {
        return (int)pontos.get(0).getX();
    }
    public int getY1()
    {
        return (int)pontos.get(0).getY();
    }
    public int getX2()
    {
        if(pontos.size() > 1)
            return (int)pontos.get(1).getX();
        return 0; // Valor padrão caso não haja ponto 2
    }
    public int getY2()
    {
        if(pontos.size() > 1)
            return (int)pontos.get(1).getY();
        return 0; // Valor padrão caso não haja ponto 2
    }
    public int getX3()
    {
        if(pontos.size() > 2)
            return (int)pontos.get(2).getX();
        return 0; // Valor padrão caso não haja ponto 3
    }
    public int getY3()
    {
        if(pontos.size() > 2)
            return (int)pontos.get(2).getY();
        return 0; // Valor padrão caso não haja ponto 3
    }
    public int getX4()
    {
        if(pontos.size() > 3)
            return (int)pontos.get(3).getX();
        return 0; // Valor padrão caso não haja ponto 3
    }
    public int getY4()
    {
        if(pontos.size() > 3)
            return (int)pontos.get(3).getY();
        return 0; // Valor padrão caso não haja ponto 3
    }
    public int getEsp()
    {
        return espessura;
    }    
    public Color getCor()
    {
        return cor;
    }
    public void setX(int i, Integer x)
    {
        pontos.get(i).setX(x);
    }
    public void setY(int i, Integer y)
    {
        pontos.get(i).setY(y);
    }

}
