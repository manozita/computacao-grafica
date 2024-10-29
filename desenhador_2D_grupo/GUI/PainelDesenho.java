package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.*;
import controller.*;
import ponto.FiguraPontos;
import reta.FiguraRetas;
import circulo.FiguraCirculos;
import armazenamento.Array;

/**
 * Cria desenhos de acordo com o tipo e eventos do mouse
 * 
 */
public class PainelDesenho extends JPanel implements MouseListener, MouseMotionListener {

    JLabel coord, acaoAtual;                 // Labels para mensagens de coordenadas e a��o atual
    TipoPrimitivo tipo;                      // Tipo de primitivo selecionado (PONTO, RETA, C�RCULO, etc.)
    Color corAtual;                          // Cor atual do primitivo a ser desenhado
    int esp;                                 // Espessura ou di�metro do ponto
    Integer coordenadas[] = null;            // Coordenadas selecionadas

    // Coordenadas utilizadas para desenhar diferentes figuras
    Integer x1, y1, x2, y2, x3, y3, x4, y4, xant, yant;
    int clickCount = 0;                      // Contador de cliques para constru��o do tri�ngulo
    boolean primeiraVez = true;              // Verifica se foi o primeiro click do mouse
    public Array formas = new Array();       // Armazena todas as formas desenhadas

    /**
     * Constroi o painel de desenho
     *
     * @param coord mensagem a ser escrita no rodape do painel
     * @param tipo tipo atual do primitivo
     * @param corAtual cor atual do primitivo
     * @param esp espessura atual do primitivo
     */
    public PainelDesenho(JLabel coord, JLabel acaoAtual, TipoPrimitivo tipo, Color corAtual, int esp) {
        setTipo(tipo);
        setCoord(coord);
        this.acaoAtual = acaoAtual;
        setCorAtual(corAtual);
        setEsp(esp);
        this.setBackground(UIManager.getColor("Panel.background")); // Define a cor de fundo do painel de acordo com o padr�o Swing

        // Adiciona "ouvidor" de eventos de mouse para intera��o do usu�rio
        this.addMouseListener(this); 
        this.addMouseMotionListener(this);
    }

    /**
     * SETTERS E GETTERS
     * Para o tipo primitivo, espessura, cor atual e mensagem exibida no rodap�
     */
    public void setTipo(TipoPrimitivo tipo) { // Altera o valor do tipo primitivo
        this.tipo = tipo;
    }

    public TipoPrimitivo getTipo() { // Retorna o valor do tipo primitivo
        return this.tipo;
    }

    public void setEsp(int esp) { // Altera o valor da espessura
        this.esp = esp;
    }

    public int getEsp() { // Retorna o valor da espessura
        return this.esp;
    }

    public void setCorAtual(Color corAtual) { // Altera o valor da cor atual
        this.corAtual = corAtual;
    }

    public Color getCorAtual() { // Retorna o valor da cor atual
        return this.corAtual;
    }

    public void setCoord(JLabel coord) { // Altera o valor da mensagem exibida no rodap�
        this.coord = coord;
    }

    public void setAcaoAtual(String acao) { // Altera o valor da mensagem da a��o atual exibida no rodap�
        acaoAtual.setText(acao);
    }

    public JLabel getMsg() { // Retorna o valor da mensagem exibida no rodap�
        return this.coord;
    }

    public Integer[] getCoordenadas() { // Retorna as coordenadas selecionadas
        return coordenadas;
    }

    /**
     * Metodo chamado quando o paint � acionado
     *
     * @param g biblioteca para desenhar em modo gr�fico
     */
    public void paintComponent(Graphics g) {   
        apagarPrimitivos(g); // Apaga primitivos antigos para evitar sobreposi��o ao redesenhar
        desenharPrimitivos(g); // Desenha os primitivos atuais
    }

    /**
     * Evento: pressionar do mouse
     *
     * @param e dados do evento
     */
    public void mousePressed(MouseEvent e) { 
        Graphics g = getGraphics();
        if (tipo == TipoPrimitivo.PONTO) { // Caso seja para desenhar um ponto, requer apenas um clique
            x1 = e.getX();
            y1 = e.getY();
            paint(g); // Desenha o ponto nas coordenadas clicadas
        } else if (tipoDeSelecao()) { // Se o tipo for uma a��o de sele��o ou transforma��o
            x1 = e.getX();
            y1 = e.getY();
            if (tipo != TipoPrimitivo.MOVER) {
                setAcaoAtual("SELECIONE UMA COORDENADA"); // Atualiza mensagem para orientar o usu�rio
            }
            Select selecao = new Select(this);
            selecao.selecionar(x1, y1); // Realiza a sele��o da figura no ponto clicado
            tipo = TipoPrimitivo.NENHUM;
        } else if (tipo == TipoPrimitivo.RETA || tipo == TipoPrimitivo.CIRCULO || tipo == TipoPrimitivo.RETANGULO) { // Para desenhar uma reta, c�rculo ou ret�ngulo, requer dois cliques
            if (primeiraVez) { // Primeiro clique define o ponto inicial
                x1 = e.getX();
                y1 = e.getY();
                primeiraVez = false; // Marca que o pr�ximo clique ser� o segundo
            } else { // Segundo clique define o ponto final
                x2 = e.getX();
                y2 = e.getY();
                primeiraVez = true;  // Reseta para permitir outro desenho
                paint(g); // Desenha a figura definida pelos dois pontos
            }
        } else if (tipo == TipoPrimitivo.TRIANGULO) { // Para desenhar um tri�ngulo, requer tr�s cliques
            if (clickCount == 0) { // Primeiro clique
                x1 = e.getX();
                y1 = e.getY();
                clickCount++;
            } else if (clickCount == 1) { // Segundo clique
                x2 = e.getX();
                y2 = e.getY();
                clickCount++;
            } else if (clickCount == 2) { // Terceiro clique, finaliza o tri�ngulo
                x3 = e.getX();
                y3 = e.getY();
                clickCount = 0; // Reseta o contador para o pr�ximo tri�ngulo
                paint(g); // Desenha o tri�ngulo
                formas.adicionarFigura(tipo, x1, y1, x2, y2, x3, y3, x4, y4, getEsp(), getCorAtual()); // Adiciona o tri�ngulo ao array de formas
                x1 = y1 = x2 = y2 = x3 = y3 = x4 = y4 = null; // Reseta as coordenadas
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
     * Evento mouseReleased: finaliza o desenho de retas e c�rculos
     *
     * @param e dados do evento do mouse
     */
    public void mouseReleased(MouseEvent e) {
        Graphics g = getGraphics();
        if (tipo == TipoPrimitivo.RETA || tipo == TipoPrimitivo.CIRCULO) { // Finaliza o desenho de uma reta ou c�rculo
            x2 = e.getX();
            y2 = e.getY();
            primeiraVez = true;
            this.coord.setText("(" + e.getX() + ", " + e.getY() + ")"); // Atualiza as coordenadas exibidas no rodap�
            paint(g);
        } else if (tipo == TipoPrimitivo.RETANGULO) { // Finaliza o desenho de um ret�ngulo
            x2 = e.getX();
            y2 = e.getY();
            x3 = x2;
            y3 = y1;
            x4 = x1;
            y4 = y2;
            primeiraVez = true;
            this.coord.setText("(" + e.getX() + ", " + e.getY() + ")"); // Atualiza as coordenadas exibidas no rodap�
            paint(g);
        }

        if (tipo == TipoPrimitivo.TRIANGULO) { // Adiciona o tri�ngulo ao array de formas se estiver completo
            if (x1 != null && x2 != null && x3 != null) {
                formas.adicionarFigura(tipo, x1, y1, x2, y2, x3, y3, x4, y4, getEsp(), getCorAtual());
            }
        } else if (!tipoDeSelecao()) { // Adiciona outras figuras ao array de formas
            formas.adicionarFigura(tipo, x1, y1, x2, y2, x3, y3, x4, y4, getEsp(), getCorAtual());
            x1 = y1 = x2 = y2 = x3 = y3 = x4 = y4 = null; // Reseta as coordenadas para o pr�ximo desenho
        }
        redesenharPainel(g); // Redesenha o painel para refletir todas as altera��es
    } 

    /**
     * Evento mouseDragged: desenha uma pr�via el�stica de retas, c�rculos e ret�ngulos
     *
     * @param e dados do evento do mouse
     */
    public void mouseDragged(MouseEvent e) {
        Graphics g = getGraphics();
        redesenharPainel(g); // Redesenha todas as formas para manter o painel atualizado
        if (tipo == TipoPrimitivo.RETA || tipo == TipoPrimitivo.CIRCULO) { // Desenha uma pr�via el�stica para reta ou c�rculo
            xant = x2;
            yant = y2;
            x2 = e.getX();
            y2 = e.getY();
            primeiraVez = true;
            this.coord.setText("(" + e.getX() + ", " + e.getY() + ")"); // Atualiza as coordenadas exibidas no rodap�
            paint(g);
        } else if (tipo == TipoPrimitivo.RETANGULO) { // Desenha uma pr�via el�stica para ret�ngulo
            xant = x2;
            yant = y2;
            x2 = e.getX();
            y2 = e.getY();
            x3 = x2;
            y3 = y1;
            x4 = x1;
            y4 = y2;
            primeiraVez = true;
            this.coord.setText("(" + e.getX() + ", " + e.getY() + ")"); // Atualiza as coordenadas exibidas no rodap�
            paint(g);
        }
    }

    /**
     * Evento mouseMoved: escreve mensagem no rodape (x, y) do mouse
     *
     * @param e dados do evento do mouse
     */
    public void mouseMoved(MouseEvent e) {
        this.coord.setText("(" + e.getX() + ", " + e.getY() + ")"); // Exibe as coordenadas do mouse no rodap� enquanto se move
    }

    /**
     * Desenha os primitivos
     *
     * @param g biblioteca para desenhar em modo grafico
     */
    public void desenharPrimitivos(Graphics g) {
        if (tipo == TipoPrimitivo.PONTO && x1 != null) { // Desenha um ponto
            FiguraPontos.desenharPonto(g, x1, y1, "", getEsp(), getCorAtual());
        } else if (tipo == TipoPrimitivo.RETA && x1 != null && x2 != null) { // Desenha uma reta
            FiguraRetas.desenharReta(g, x1, y1, x2, y2, "", getEsp(), getCorAtual());
        } else if (tipo == TipoPrimitivo.CIRCULO && x1 != null && x2 != null) { // Desenha um c�rculo
            FiguraCirculos.desenharCirculo(g, x1, y1, x2, y2, "", getEsp(), getCorAtual());
        } else if (tipo == TipoPrimitivo.RETANGULO && x1 != null && x2 != null) { // Desenha um ret�ngulo
            FiguraRetas.desenharRetangulo(g, x1, y1, x2, y2, x3, y3, x4, y4, "", getEsp(), getCorAtual());
        } else if (tipo == TipoPrimitivo.TRIANGULO && x1 != null && x2 != null && x3 != null) { // Desenha um tri�ngulo
            FiguraRetas.desenharTriangulo(g, x1, y1, x2, y2, x3, y3, "", getEsp(), getCorAtual());
        }
    }

    /**
     * Apaga os primitivos anteriores durante o desenho de Retas, Circulos e Retangulos
     *
     * @param g biblioteca para desenhar em modo grafico
     */
    public void apagarPrimitivos(Graphics g) {
        if (tipo == TipoPrimitivo.RETA && x1 != null && xant != null) { // Apaga a reta anterior
            FiguraRetas.desenharReta(g, x1, y1, xant, yant, "", getEsp(), getBackground());
        } else if (tipo == TipoPrimitivo.CIRCULO && x1 != null && xant != null) { // Apaga o c�rculo anterior
            FiguraCirculos.desenharCirculo(g, x1, y1, xant, yant, "", getEsp(), getBackground());
        } else if (tipo == TipoPrimitivo.RETANGULO && x1 != null && xant != null) { // Apaga o ret�ngulo anterior
            FiguraRetas.desenharRetangulo(g, x1, y1, xant, yant, xant, y1, x1, yant, "", getEsp(), getBackground());
        }
    }

    /**
     * Redesenha todas as figuras j� presentes no painel
     *
     * @param g biblioteca para desenhar em modo grafico
     */
    public void redesenharPainel(Graphics g) {
        for (int i = 0; i < formas.getTamanho(); i++) {
            switch (formas.getFigura(i).getTipo()) {
                case PONTO:
                    FiguraPontos.desenharPonto(g, formas.getFigura(i).getX1(), formas.getFigura(i).getY1(), "", formas.getFigura(i).getEsp(), formas.getFigura(i).getCor());
                    break;
                case RETA:
                    FiguraRetas.desenharReta(g, formas.getFigura(i).getX1(), formas.getFigura(i).getY1(), formas.getFigura(i).getX2(), formas.getFigura(i).getY2(), "", formas.getFigura(i).getEsp(), formas.getFigura(i).getCor());
                    break;
                case RETANGULO:
                    FiguraRetas.desenharRetangulo(g, formas.getFigura(i).getX1(), formas.getFigura(i).getY1(), formas.getFigura(i).getX2(), formas.getFigura(i).getY2(), formas.getFigura(i).getX3(), formas.getFigura(i).getY3(), formas.getFigura(i).getX4(), formas.getFigura(i).getY4(), "", formas.getFigura(i).getEsp(), formas.getFigura(i).getCor());
                    break;
                case CIRCULO:
                    FiguraCirculos.desenharCirculo(g, formas.getFigura(i).getX1(), formas.getFigura(i).getY1(), formas.getFigura(i).getX2(), formas.getFigura(i).getY2(), "", formas.getFigura(i).getEsp(), formas.getFigura(i).getCor());
                    break;
                case TRIANGULO:
                    FiguraRetas.desenharTriangulo(g, formas.getFigura(i).getX1(), formas.getFigura(i).getY1(), formas.getFigura(i).getX2(), formas.getFigura(i).getY2(), formas.getFigura(i).getX3(), formas.getFigura(i).getY3(), "", formas.getFigura(i).getEsp(), formas.getFigura(i).getCor());
                    break;
            }
        }
    }

    /**
     * Limpa o painel, redesenhando todas as figuras com a cor do fundo, apagando-as
     *
     * @param g biblioteca para desenhar em modo grafico
     */
    public void limparPainel(Graphics g) {
        for (int i = 0; i < formas.getTamanho(); i++) {
            switch (formas.getFigura(i).getTipo()) {
                case PONTO:
                    FiguraPontos.desenharPonto(g, formas.getFigura(i).getX1(), formas.getFigura(i).getY1(), "", formas.getFigura(i).getEsp(), getBackground());
                    break;
                case RETA:
                    FiguraRetas.desenharReta(g, formas.getFigura(i).getX1(), formas.getFigura(i).getY1(), formas.getFigura(i).getX2(), formas.getFigura(i).getY2(), "", formas.getFigura(i).getEsp(), getBackground());
                    break;
                case RETANGULO:
                    FiguraRetas.desenharRetangulo(g, formas.getFigura(i).getX1(), formas.getFigura(i).getY1(), formas.getFigura(i).getX2(), formas.getFigura(i).getY2(), formas.getFigura(i).getX3(), formas.getFigura(i).getY3(), formas.getFigura(i).getX4(), formas.getFigura(i).getY4(), "", formas.getFigura(i).getEsp(), getBackground());
                    break;
                case CIRCULO:
                    FiguraCirculos.desenharCirculo(g, formas.getFigura(i).getX1(), formas.getFigura(i).getY1(), formas.getFigura(i).getX2(), formas.getFigura(i).getY2(), "", formas.getFigura(i).getEsp(), getBackground());
                    break;
                case TRIANGULO:
                    FiguraRetas.desenharTriangulo(g, formas.getFigura(i).getX1(), formas.getFigura(i).getY1(), formas.getFigura(i).getX2(), formas.getFigura(i).getY2(), formas.getFigura(i).getX3(), formas.getFigura(i).getY3(), "", formas.getFigura(i).getEsp(), getBackground());
                    break;
            }
        }
    }

    /**
     * Limpa a �rea de desenho, mas mant�m a espessura e cores atuais
     */
    public void restaurarPainel() {
        Graphics g = getGraphics();
        limparPainel(g); // Apaga todas as figuras do painel
        formas.limparArray(); // Limpa o array que armazena as figuras
    }

    /**
     * Verifica se o tipo atual � uma a��o de sele��o
     * 
     * @return true se for uma a��o de sele��o, false caso contr�rio
     */
    private boolean tipoDeSelecao() {
        return (tipo == TipoPrimitivo.DELETAR || tipo == TipoPrimitivo.ROTACAO || tipo == TipoPrimitivo.ESCALA || tipo == TipoPrimitivo.MOVER);
    }
}
