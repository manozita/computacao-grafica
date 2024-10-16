package aplicacao;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.lang.Math;

import ponto.FiguraPontos;
import reta.FiguraRetas;
import circulo.FiguraCirculos;
import armazenamento.Array;

/**
 * Cria desenhos de acordo com o tipo e eventos do mouse
 * 
 * @author Julio Arakaki 
 * @version 20220815
 */
public class PainelDesenho extends JPanel implements MouseListener, MouseMotionListener {

    JLabel msg;                 // Label para mensagens
    TipoPrimitivo tipo;         // Tipo do primitivo
    Color corAtual;             // Cor atual do primitivo
    int esp;                    // Espessura, diâmetro do ponto

    Integer x1, y1, x2, y2, x3, y3, xant, yant; // Coordenadas para RETA, TRIÂNGULO e CÍRCULO
    int tolerancia;  // Erro do clique para deletar uma figura
    int clickCount = 0;         // Contador de cliques para o triângulo
    boolean primeiraVez = true; // Verifica se foi o primeiro click do mouse, para construção das figuras
    Array formas = new Array();

    /**
     * Constroi o painel de desenho
     *
     * @param msg mensagem a ser escrita no rodape do painel
     * @param tipo tipo atual do primitivo
     * @param corAtual cor atual do primitivo
     * @param esp espessura atual do primitivo
     */
    public PainelDesenho (JLabel msg, TipoPrimitivo tipo, Color corAtual, int esp) {
        setTipo(tipo);
        setMsg(msg);
        setCorAtual(corAtual);
        setEsp(esp);
        tolerancia = getEsp()/2 + 4;

        // Adiciona "ouvidor" de eventos de mouse
        this.addMouseListener(this); 
        this.addMouseMotionListener(this);
    }

    /**
     * SETTERS E GETTERS
     *
     * Para o tipo primitivo, espessura, cor atual e mensagem exibida no rodapé
     */
    public void setTipo (TipoPrimitivo tipo) {      // Altera o valor do tipo primitivo
        this.tipo = tipo;
    }

    public TipoPrimitivo getTipo () {               // Retorna o valor do tipo primitivo
        return this.tipo;
    }

    public void setEsp (int esp) {                  // Altera o valor da espessura
        this.esp = esp;
    }

    public int getEsp () {                          // Retorna o valor da espessura
        return this.esp;
    }

    public void setCorAtual (Color corAtual) {      // Altera o valor da cor atual
        this.corAtual = corAtual;
    }

    public Color getCorAtual () {                   // Retorna o valor da cor atual
        return this.corAtual;
    }

    public void setMsg (JLabel msg) {               // Altera o valor da mensagem exibida no rodapé
        this.msg = msg;
    }

    public JLabel getMsg () {                       // Retorna o valor da mensagem exibida no rodapé
        return this.msg;
    }

    /**
     * Metodo chamado quando o paint é acionado
     *
     * @param g biblioteca para desenhar em modo gráfico
     */
    public void paintComponent(Graphics g) {   
        apagarPrimitivos(g);
        desenharPrimitivos(g);
    }

    /**
     * Evento: pressionar do mouse
     *
     * @param e dados do evento
     */
    public void mousePressed(MouseEvent e) { 
        Graphics g = getGraphics();
        if (tipo == TipoPrimitivo.PONTO) { // O ponto e o delete requerem UM único click do mouse
            x1 = e.getX();
            y1 = e.getY();
            paint(g);       // Pega as coordenadas e pinta um ponto
        } else if(tipo == TipoPrimitivo.DELETAR) {
            x1 = e.getX();
            y1 = e.getY();
            deletarPrimitivo(x1, y1);
        } else if (tipo == TipoPrimitivo.RETA || tipo == TipoPrimitivo.CIRCULO || tipo == TipoPrimitivo.RETANGULO){ // DOIS clicks do mouse
            if (primeiraVez == true) { // Se for o primeiro click do mouse
                x1 = e.getX();
                y1 = e.getY();
                primeiraVez = false; // Não é mais a primeira vez
            } else {
                x2 = e.getX();
                y2 = e.getY();
                primeiraVez = true;  // Reinicia a variável para o próximo ponto
                paint(g);   // Pega as coordenadas e faz a figura
            }
        } else if(tipo == TipoPrimitivo.TRIANGULO) { // TRÊS clicks do mouse
            // Nova lógica para o triângulo
            if (clickCount == 0) {
                x1 = e.getX();
                y1 = e.getY();
                clickCount++;
            } else if (clickCount == 1) {
                x2 = e.getX();
                y2 = e.getY();
                clickCount++;
            } else if (clickCount == 2) { // Último click para o triângulo
                x3 = e.getX();
                y3 = e.getY();
                clickCount = 0; // Reinicia o contador para o próximo triângulo
                paint(g); // Pega as coordenadas e faz a figura
                formas.adicionarFigura(tipo, x1, y1, x2, y2, x3, y3, getEsp(), getCorAtual());
                x1 = y1 = x2 = y2 = x3 = y3 = null;
            }
        }
    }     

    // EVENTOS DO MOUSE          
    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    /**
     * Evento mouseReleased: elástico
     *
     * @param e dados do evento do mouse
     */
    public void mouseReleased(MouseEvent e) { 
        if (tipo != TipoPrimitivo.TRIANGULO) {
            Graphics g = getGraphics();
            x2 = (int)e.getX();
            y2 = (int)e.getY();
            primeiraVez = true;
            this.msg.setText("("+e.getX() + ", " + e.getY() + ") - " + getTipo());
            paint(g);
        }

        Graphics g = getGraphics();
        if(tipo == TipoPrimitivo.TRIANGULO)
        {
            if(x1 != null && x2 != null && x3 != null)
            {
                formas.adicionarFigura(tipo, x1, y1, x2, y2, x3, y3, getEsp(), getCorAtual());
            }
        }
        else
        {
            formas.adicionarFigura(tipo, x1, y1, x2, y2, x3, y3, getEsp(), getCorAtual());
        }
        redesenharPainel(g);
    } 

    /**
     * Evento mouseDragged: elástico
     *
     * @param e dados do evento do mouse
     */
    public void mouseDragged(MouseEvent e) {
        Graphics g = getGraphics();
        redesenharPainel(g);
        if (tipo != TipoPrimitivo.TRIANGULO) {
            //Graphics g = getGraphics();
            xant = x2;
            yant = y2;
            x2 = (int)e.getX();
            y2 = (int)e.getY();
            this.msg.setText("("+e.getX() + ", " + e.getY() + ") - " + getTipo());
            paint(g);
        }
    }

    /**
     * Evento mouseMoved: escreve mensagem no rodape (x, y) do mouse
     *
     * @param e dados do evento do mouse
     */
    public void mouseMoved(MouseEvent e) {
        this.msg.setText("("+e.getX() + ", " + e.getY() + ") - " + getTipo());
    }

    /**
     * Desenha os primitivos
     *
     * @param g biblioteca para desenhar em modo grafico
     */
    public void desenharPrimitivos(Graphics g){
        if (tipo == TipoPrimitivo.PONTO && x1 != null) {          // Desenha o ponto
            FiguraPontos.desenharPonto(g, x1, y1, "", getEsp(), getCorAtual());
            //FiguraPontos.desenharPontos(g, 50, 20);
        }
        else if (tipo == TipoPrimitivo.RETA && x1 != null && x2 != null) {      // Desenha a reta
            FiguraRetas.desenharReta(g, x1, y1, x2, y2, "", getEsp(), getCorAtual());
            //FiguraRetas.desenharRetas(g, 10, 3);
        }
        else if (tipo==TipoPrimitivo.CIRCULO && x1 != null && x2 != null) {     // Desenha o círculo
            FiguraCirculos.desenharCirculo(g, x1, y1, x2, y2, "", getEsp(), getCorAtual());
        }
        else if (tipo==TipoPrimitivo.RETANGULO && x1 != null && x2 != null) {   // Desenha o retângulo
            FiguraRetas.desenharRetangulo(g, x1, y1, x2, y2, "", getEsp(), getCorAtual());
        }
        else if (tipo==TipoPrimitivo.TRIANGULO && x1 != null && x2 != null && x3 != null) {   // Desenha o triângulo
            FiguraRetas.desenharTriangulo(g, x1, y1, x2, y2, x3, y3, "", getEsp(), getCorAtual());
        }
    }

    /**
     * Apaga os primitivos anteriores
     *
     * @param g biblioteca para desenhar em modo grafico
     */
    public void apagarPrimitivos(Graphics g){
        if (tipo == TipoPrimitivo.RETA && x1 != null && xant != null) {           // Apaga a reta
            FiguraRetas.desenharReta(g, x1, y1, xant, yant, "", getEsp(), getBackground());
        }
        else if (tipo==TipoPrimitivo.CIRCULO && x1 != null && xant != null) {     // Desenha o círculo
            FiguraCirculos.desenharCirculo(g, x1, y1, xant, yant, "", getEsp(), getBackground());
        }
        else if (tipo==TipoPrimitivo.RETANGULO && x1 != null && xant != null) {   // Desenha o retângulo
            FiguraRetas.desenharRetangulo(g, x1, y1, xant, yant, "", getEsp(), getBackground());
        }
    }

    public void redesenharPainel(Graphics g)
    {
        for (int i = 0; i < formas.getTamanho(); i++) {
            if(formas.getFigura(i).getTipo() == TipoPrimitivo.PONTO)
            {
                FiguraPontos.desenharPonto(g, formas.getFigura(i).getX1(), formas.getFigura(i).getY1(), "", formas.getFigura(i).getEsp(), formas.getFigura(i).getCor());
            }
            else if(formas.getFigura(i).getTipo() == TipoPrimitivo.RETA)
            {
                FiguraRetas.desenharReta(g, formas.getFigura(i).getX1(), formas.getFigura(i).getY1(), formas.getFigura(i).getX2(), formas.getFigura(i).getY2(), "", formas.getFigura(i).getEsp(), formas.getFigura(i).getCor());
            }
            else if(formas.getFigura(i).getTipo() == TipoPrimitivo.RETANGULO)
            {
                FiguraRetas.desenharRetangulo(g, formas.getFigura(i).getX1(), formas.getFigura(i).getY1(), formas.getFigura(i).getX2(), formas.getFigura(i).getY2(), "", formas.getFigura(i).getEsp(), formas.getFigura(i).getCor());
            }
            else if(formas.getFigura(i).getTipo() == TipoPrimitivo.CIRCULO)
            {
                FiguraCirculos.desenharCirculo(g, formas.getFigura(i).getX1(), formas.getFigura(i).getY1(), formas.getFigura(i).getX2(), formas.getFigura(i).getY2(), "", formas.getFigura(i).getEsp(), formas.getFigura(i).getCor());
            }
            else if(formas.getFigura(i).getTipo() == TipoPrimitivo.TRIANGULO)
            {
                FiguraRetas.desenharTriangulo(g, formas.getFigura(i).getX1(), formas.getFigura(i).getY1(), formas.getFigura(i).getX2(), formas.getFigura(i).getY2(), formas.getFigura(i).getX3(), formas.getFigura(i).getY3(), "", formas.getFigura(i).getEsp(), formas.getFigura(i).getCor());
            }
        }
    }

    public void limparPainel()
    {
        formas.limparArray();
    }

    public void deletarPrimitivo(Integer x, Integer y)  // Deleta uma figura selecionada pelo usuario
    {
        boolean apagou = false; // Verificador de remocao
        int i = formas.getTamanho()-1;  // Indice do ultimo elemento
        TipoPrimitivo t;

        while(i >= 0 && apagou == false)    // Na duvida remove o mais recente
        {
            t = formas.getFigura(i).getTipo();  // Faremos a comparação até achar um elemento mais próximo
            if(t == TipoPrimitivo.PONTO)
            {
                if(deletarPonto(x, y, formas.getFigura(i).getX1(), formas.getFigura(i).getY1(), i))    // Verifica se da pra apagar
                {
                    apagou = true;
                }
            }
            else if(t == TipoPrimitivo.RETA)
            {
                if(deletarReta(x, y, formas.getFigura(i).getX1(), formas.getFigura(i).getY1(), formas.getFigura(i).getX2(), formas.getFigura(i).getY2(), i))
                {
                    apagou = true;
                }
            }
            else if(t == TipoPrimitivo.RETANGULO)
            {
                if(deletarRetangulo(x, y, formas.getFigura(i).getX1(), formas.getFigura(i).getY1(), formas.getFigura(i).getX2(), formas.getFigura(i).getY2(), i))
                {
                    apagou = true;
                }
            }
            else if(t == TipoPrimitivo.CIRCULO)
            {
                if(deletarCirculo(x, y, formas.getFigura(i).getX1(), formas.getFigura(i).getY1(), formas.getFigura(i).getX2(), formas.getFigura(i).getY2(), i))
                {
                    apagou = true;
                }
            }
            else if(t == TipoPrimitivo.TRIANGULO)
            {
                if(deletarTriangulo(x, y, formas.getFigura(i).getX1(), formas.getFigura(i).getY1(), formas.getFigura(i).getX2(), formas.getFigura(i).getY2(), formas.getFigura(i).getX3(), formas.getFigura(i).getY3(), i))
                {
                    apagou = true;
                }
            }
            i--;
        }        
    }

    public boolean deletarPonto(Integer x, Integer y, Integer xP, Integer yP, int i)
    {
        double d;
        d = Math.sqrt((xP-x)*(xP-x)+(yP-y)*(yP-y));
        if(d <= tolerancia)
        {
            Graphics g = getGraphics();
            FiguraPontos.desenharPonto(g, xP, yP, "", formas.getFigura(i).getEsp(), getBackground());
            formas.apagarElemento(i);
            return true;
        }
        return false;
    } 

    public boolean deletarReta(Integer x, Integer y, Integer x1R, Integer y1R, Integer x2R, Integer y2R, int i)
    {
        // Fórmula da distância ponto para reta
        double distancia = distanciaParaAresta(x, y, x1R, y1R, x2R, y2R);

        if (distancia <= tolerancia) { // Tolerância de 4 pixels
            Graphics g = getGraphics();
            FiguraRetas.desenharReta(g, x1R, y1R, x2R, y2R, "", formas.getFigura(i).getEsp(), getBackground());
            formas.apagarElemento(i);
            return true;
        }
        return false;
    }

    public boolean deletarRetangulo(Integer x, Integer y, Integer x1R, Integer y1R, Integer x2R, Integer y2R, int i) {
        // Definindo as quatro arestas do retângulo
        boolean proximoAresta1 = distanciaParaAresta(x, y, x1R, y1R, x2R, y1R) <= tolerancia; // Aresta superior
        boolean proximoAresta2 = distanciaParaAresta(x, y, x2R, y1R, x2R, y2R) <= tolerancia; // Aresta direita
        boolean proximoAresta3 = distanciaParaAresta(x, y, x2R, y2R, x1R, y2R) <= tolerancia; // Aresta inferior
        boolean proximoAresta4 = distanciaParaAresta(x, y, x1R, y2R, x1R, y1R) <= tolerancia; // Aresta esquerda
        System.out.println("");

        // Se o ponto estiver próximo de alguma das arestas, apagamos o retângulo
        if (proximoAresta1 || proximoAresta2 || proximoAresta3 || proximoAresta4) {
            Graphics g = getGraphics();
            System.out.println(i);
            FiguraRetas.desenharRetangulo(g, x1R, y1R, x2R, y2R, "", formas.getFigura(i).getEsp(), getBackground());
            formas.apagarElemento(i);
            return true;
        }

        return false;
    }

    public boolean deletarCirculo(Integer x, Integer y, Integer xC, Integer yC, Integer xB, Integer yB, int i) {
        // Cálculo do raio com base na distância entre o centro (xC, yC) e o ponto na borda (xB, yB)
        double raio = Math.sqrt((xB - xC) * (xB - xC) + (yB - yC) * (yB - yC));

        // Cálculo da distância do ponto clicado (x, y) ao centro do círculo (xC, yC)
        double distancia = Math.sqrt((xC - x) * (xC - x) + (yC - y) * (yC - y));

        // Verifica se a distância do clique é aproximadamente igual ao raio
        if (Math.abs(distancia - raio) <= 4) { // Tolerância de 4 pixels
            Graphics g = getGraphics();
            FiguraCirculos.desenharCirculo(g, xC, yC, xB, yB, "", formas.getFigura(i).getEsp(), getBackground());
            formas.apagarElemento(i);
            return true;
        }
        return false;
    }

    public boolean deletarTriangulo(Integer x, Integer y, Integer x1T, Integer y1T, Integer x2T, Integer y2T, Integer x3T, Integer y3T, int i) {
        boolean proximoAresta1 = distanciaParaAresta(x, y, x1T, y1T, x2T, y2T) <= tolerancia;
        boolean proximoAresta2 = distanciaParaAresta(x, y, x2T, y2T, x3T, y3T) <= tolerancia;
        boolean proximoAresta3 = distanciaParaAresta(x, y, x3T, y3T, x1T, y1T) <= tolerancia;

        // Se o ponto estiver próximo a uma das arestas, apagamos o triângulo
        if (proximoAresta1 || proximoAresta2 || proximoAresta3)
        {
            Graphics g = getGraphics();
            FiguraRetas.desenharTriangulo(g, x1T, y1T, x2T, y2T, x3T, y3T, "", formas.getFigura(i).getEsp(), getBackground());
            formas.apagarElemento(i);
            return true;
        }
        return false;
    }

    private double distanciaParaAresta(Integer x, Integer y, Integer xA, Integer yA, Integer xB, Integer yB)
    {
        double d;
        if(xA.equals(xB) && yA.equals(yB))
        {
            d = Math.sqrt(Math.pow((xA-x), 2)+ Math.pow((yA-y), 2));
            System.out.println(d);
            return d;
        }
        d = Math.abs((yB - yA) * x - (xB - xA) * y + xB * yA - yB * xA) / Math.sqrt(Math.pow(yB - yA, 2) + Math.pow(xB - xA, 2));
        //System.out.println(d);
        return d;
    }
}
