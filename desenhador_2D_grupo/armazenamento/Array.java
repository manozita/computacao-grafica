package armazenamento;

import java.util.ArrayList;
import aplicacao.TipoPrimitivo;
import java.awt.Color;

public class Array
{
    ArrayList<Primitivos> lista = new ArrayList<>();
    Primitivos figura;
    
    public Array()
    {
        
    }
    
    public void adicionarFigura(TipoPrimitivo t, Integer x1, Integer y1, Integer x2, Integer y2, Integer x3, Integer y3, Integer esp, Color c)
    {
        figura = new Primitivos(t, x1, y1, x2, y2, x3, y3, esp, c);
        lista.add(figura);
    }
    
    public void limparArray()
    {
        lista.clear();
    }
    
    public int getTamanho()
    {
        return lista.size();
    }
    
    public Primitivos getFigura(int i)
    {
        return lista.get(i);
    }
}
