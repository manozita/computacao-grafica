package armazenamento;

import java.util.ArrayList;

public class Array
{
    ArrayList<Primitivo> figuras = new ArrayList<Primitivo>();
    Primitivo fig;
    
    public void guardarPrimitivo()
    {
        fig = new Primitivo();
        figuras.add(fig);
    }
}
