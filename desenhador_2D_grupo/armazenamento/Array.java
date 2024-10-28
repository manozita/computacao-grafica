package armazenamento;

import java.util.ArrayList;
import controller.TipoPrimitivo;
import java.awt.Color;

public class Array
{
    ArrayList<Primitivos> lista = new ArrayList<>(); // array list que armazena todas as figuras
    Primitivos figura; // figura de um tipo primitivo

    /**
     * Método adicionarFigura
     * Entra com parametros para a figura e adiciona no array list
     */
    public void adicionarFigura(TipoPrimitivo t, Integer x1, Integer y1, Integer x2, Integer y2, Integer x3, Integer y3, Integer x4, Integer y4, Integer esp, Color c)
    {
        figura = new Primitivos(t, x1, y1, x2, y2, x3, y3, x4, y4, esp, c); // novo primitivo com os dados da figura
        lista.add(figura); // adiciona na lista
    }
    
    /**
     * Método limparArray
     * Limpa o array totalmente
     */
    public void limparArray()
    {
        lista.clear();
    }
    
    /**
     * Método getTamanho
     *
     * retorna o tamanho da lista
     */
    public int getTamanho()
    {
        return lista.size();
    }
    
    /**
     * Método getFigura
     *
     * Retorna a i-esima figura
     */
    public Primitivos getFigura(int i)
    {
        return lista.get(i);
    }
    
    /**
     * Método apagarElemento
     *
     * Apaga o i-esimo elemento da lista
     */
    public void apagarElemento(int i)
    {
        lista.remove(i);
    }
    
    public void setCoordenadas(int i, int j, int x, int y)
    {
        lista.get(i).setX(j, x);
        lista.get(i).setY(j, y);
    }
}
