package aplicacao;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ponto.FiguraPontos;
import reta.FiguraRetas;
import circulo.FiguraCirculos;

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

    int x, y;                   // Coordenadas para o PONTO
    int x1, y1, x2, y2, x3, y3, xant=0, yant=0; // Coordenadas para RETA, TRIÂNGULO e CÍRCULO
    int clickCount = 0;         // Contador de cliques para o triângulo
    boolean primeiraVez = true; // Verifica se foi o primeiro click do mouse, para construção das figuras

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
        if (tipo == TipoPrimitivo.PONTO) { // O ponto requer UM único click do mouse
            x = e.getX();
            y = e.getY();
            paint(g);       // Pega as coordenadas e pinta um ponto
        } else if (tipo == TipoPrimitivo.RETA || tipo == TipoPrimitivo.CIRCULO || tipo == TipoPrimitivo.RETANGULO){ // DOIS clicks do mouse
            if (primeiraVez == true) { // Se for o primeiro click do mouse
                x1 = (int)e.getX();
                y1 = (int)e.getY();
                primeiraVez = false; // Não é mais a primeira vez
            } else {
                x2 = (int)e.getX();
                y2 = (int)e.getY();
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
        if (tipo == TipoPrimitivo.PONTO) { // O ponto requer UM único click do mouse
            FiguraPontos.guardarPonto(g, x, y, "", getEsp(), getCorAtual());
        } else if (tipo == TipoPrimitivo.RETA){ // DOIS clicks do mouse
            FiguraRetas.guardarReta(g, x1, y1, x2, y2, "", getEsp(), getCorAtual());
        } else if(tipo == TipoPrimitivo.RETANGULO) { // TRÊS clicks do mouse
            FiguraRetas.guardarRetangulo(g, x1, y1, x2, y2, "", getEsp(), getCorAtual());
        }
        else if(tipo == TipoPrimitivo.CIRCULO) { // TRÊS clicks do mouse
            FiguraCirculos.guardarCirculo(g, x1, y1, x2, y2, "", getEsp(), getCorAtual());
        }
    } 

    /**
     * Evento mouseDragged: elástico
     *
     * @param e dados do evento do mouse
     */
    public void mouseDragged(MouseEvent e) {
        Graphics g = getGraphics();
        if (tipo != TipoPrimitivo.TRIANGULO) {
            //Graphics g = getGraphics();
            xant = x2;
            yant = y2;
            x2 = (int)e.getX();
            y2 = (int)e.getY();
            this.msg.setText("("+e.getX() + ", " + e.getY() + ") - " + getTipo());
            paint(g);
        }
        redesenharPainel(g);
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
        if (tipo == TipoPrimitivo.PONTO) {          // Desenha o ponto
            FiguraPontos.desenharPonto(g, x, y, "", getEsp(), getCorAtual());
            //FiguraPontos.desenharPontos(g, 50, 20);
        }
        else if (tipo == TipoPrimitivo.RETA) {      // Desenha a reta
            FiguraRetas.desenharReta(g, x1, y1, x2, y2, "", getEsp(), getCorAtual());
            //FiguraRetas.desenharRetas(g, 10, 3);
        }
        else if (tipo==TipoPrimitivo.CIRCULO) {     // Desenha o círculo
            FiguraCirculos.desenharCirculo(g, x1, y1, x2, y2, "", getEsp(), getCorAtual());
        }
        else if (tipo==TipoPrimitivo.RETANGULO) {   // Desenha o retângulo
            FiguraRetas.desenharRetangulo(g, x1, y1, x2, y2, "", getEsp(), getCorAtual());
        }
        else if (tipo==TipoPrimitivo.TRIANGULO) {   // Desenha o triângulo
            FiguraRetas.desenharTriangulo(g, x1, y1, x2, y2, x3, y3, "", getEsp(), getCorAtual());
        }
    }

    /**
     * Apaga os primitivos anteriores
     *
     * @param g biblioteca para desenhar em modo grafico
     */
    public void apagarPrimitivos(Graphics g){
        if (tipo == TipoPrimitivo.RETA) {           // Apaga a reta
            FiguraRetas.desenharReta(g, x1, y1, xant, yant, "", getEsp(), getBackground());
        }
        else if (tipo==TipoPrimitivo.CIRCULO) {     // Desenha o círculo
            FiguraCirculos.desenharCirculo(g, x1, y1, xant, yant, "", getEsp(), getBackground());
        }
        else if (tipo==TipoPrimitivo.RETANGULO) {   // Desenha o retângulo
            FiguraRetas.desenharRetangulo(g, x1, y1, xant, yant, "", getEsp(), getBackground());
        }
        else if (tipo==TipoPrimitivo.TRIANGULO) {   // Desenha o triângulo
            //FiguraRetas.desenharTriangulo(g, x1, y1, x2, y2, x3, y3, "", getEsp(), getBackground());
        }
    }
    
    public void redesenharPainel(Graphics g)
    {
        FiguraRetas.redefinirRetas(g);
        FiguraRetas.redefinirRetangulos(g);
        FiguraCirculos.redefinirCirculos(g);
    }
}
