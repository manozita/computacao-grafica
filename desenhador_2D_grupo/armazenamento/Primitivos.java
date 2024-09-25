package armazenamento;

import ponto.*;
import aplicacao.TipoPrimitivo;
import java.util.ArrayList;
import java.awt.Color;

public class Primitivos
{
    private TipoPrimitivo tipo;
    private ArrayList<Ponto> pontos = new ArrayList<>();
    private int espessura;
    private Color cor;
    
    public Primitivos(TipoPrimitivo t, Integer x1, Integer y1, Integer x2, Integer y2, Integer x3, Integer y3, Integer esp, Color c)
    {
        tipo = t;
        
        Ponto p1 = new Ponto();
        if(x1 == null)
        {
            x1 = 0;
        }
        if(y1 == null)
        {
            y1 = 0;
        }
        if(x2 == null)
        {
            x2 = 0;
        }
        if(y2 == null)
        {
            y2 = 0;
        }
        if(x3 == null)
        {
            x3 = 0;
        }
        if(y3 == null)
        {
            y3 = 0;
        }
        p1.setX(x1);
        p1.setY(y1);
        pontos.add(p1);
        
        if(tipo != TipoPrimitivo.PONTO)
        {
            Ponto p2 = new Ponto();
            p2.setX(x2);
            p2.setY(y2);
            pontos.add(p2);
            
            if(tipo == TipoPrimitivo.TRIANGULO)
            {
                Ponto p3 = new Ponto();
                p3.setX(x3);
                p3.setY(y3);
                pontos.add(p3);       
            }
        }
        
        espessura = esp;
        cor = c;
    }
    
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
    
    public int getEsp()
    {
        return espessura;
    }
    
    public Color getCor()
    {
        return cor;
    }
}
