package controller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.*;

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

    JLabel coord;                 // Label para mensagens
    TipoPrimitivo tipo;         // Tipo do primitivo
    Color corAtual;             // Cor atual do primitivo
    int esp;                    // Espessura, diâmetro do ponto
    Integer coordenadas[] = null; // Coordenadas para select

    Integer x1, y1, x2, y2, x3, y3, x4, y4, xant, yant; // Coordenadas para RETA, TRIÂNGULO e CÍRCULO
    int clickCount = 0;         // Contador de cliques para o triângulo
    boolean primeiraVez = true; // Verifica se foi o primeiro click do mouse, para construção das figuras
    public Array formas = new Array();

    /**
     * Constroi o painel de desenho
     *
     * @param coord mensagem a ser escrita no rodape do painel
     * @param tipo tipo atual do primitivo
     * @param corAtual cor atual do primitivo
     * @param esp espessura atual do primitivo
     */
    public PainelDesenho (JLabel coord, TipoPrimitivo tipo, Color corAtual, int esp) {
        setTipo(tipo);
        setCoord(coord);
        setCorAtual(corAtual);
        setEsp(esp);
        this.setBackground(UIManager.getColor("Panel.background")); // acessa a cor padrão que o Swing utiliza para painéis

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

    public void setCoord (JLabel coord) {               // Altera o valor da mensagem exibida no rodapé
        this.coord = coord;
    }

    public JLabel getMsg () {                       // Retorna o valor da mensagem exibida no rodapé
        return this.coord;
    }
    public Integer[] getCoordenadas() {
        return coordenadas;
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
        } else if(tipoDeSelecao()) {
            x1 = e.getX();
            y1 = e.getY();
            // IDEIA: na hora de selecionar, destacar a figura de alguma forma
            Select selecao = new Select(this);
            selecao.selecionar(x1, y1);
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
                formas.adicionarFigura(tipo, x1, y1, x2, y2, x3, y3, x4, y4, getEsp(), getCorAtual());
                x1 = y1 = x2 = y2 = x3 = y3 = x4 = y4 = null;
            }
        } else if(tipo == TipoPrimitivo.COORDENADA) {
            JOptionPane.showMessageDialog(null, "Clicou.");
            coordenadas = new Integer[2];
            coordenadas[0] = x1; coordenadas[1] = y1;
            x1 = e.getX();
            y1 = e.getY();
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
        Graphics g = getGraphics();
        if (tipo == TipoPrimitivo.RETA || tipo == TipoPrimitivo.CIRCULO) {
            x2 = (int)e.getX();
            y2 = (int)e.getY();
            primeiraVez = true;
            this.coord.setText("("+e.getX() + ", " + e.getY() + ")");
            paint(g);
        }
        else if(tipo == TipoPrimitivo.RETANGULO)
        {
            x2 = (int)e.getX();
            y2 = (int)e.getY();
            x3 = x2;
            y3 = y1;
            x4 = x1;
            y4 = y2;
            primeiraVez = true;
            this.coord.setText("("+e.getX() + ", " + e.getY() + ")");
            paint(g);
        }

        if(tipo == TipoPrimitivo.TRIANGULO)
        {
            if(x1 != null && x2 != null && x3 != null)
            {
                formas.adicionarFigura(tipo, x1, y1, x2, y2, x3, y3, x4, y4, getEsp(), getCorAtual());
            }
        }
        else if(!tipoDeSelecao())
        {
            formas.adicionarFigura(tipo, x1, y1, x2, y2, x3, y3, x4, y4, getEsp(), getCorAtual());
            x1 = y1 = x2 = y2 = x3 = y3 = x4 = y4 = null;
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
        if (tipo == TipoPrimitivo.RETA || tipo == TipoPrimitivo.CIRCULO) {
            xant = x2;
            yant = y2;
            x2 = (int)e.getX();
            y2 = (int)e.getY();
            primeiraVez = true;
            this.coord.setText("("+e.getX() + ", " + e.getY() + ")");
            paint(g);
        }
        else if(tipo == TipoPrimitivo.RETANGULO)
        {
            xant = x2;
            yant = y2;
            x2 = (int)e.getX();
            y2 = (int)e.getY();
            x3 = x2;
            y3 = y1;
            x4 = x1;
            y4 = y2;
            primeiraVez = true;
            this.coord.setText("("+e.getX() + ", " + e.getY() + ")");
            paint(g);
        }
    }

    /**
     * Evento mouseMoved: escreve mensagem no rodape (x, y) do mouse
     *
     * @param e dados do evento do mouse
     */
    public void mouseMoved(MouseEvent e) {
        this.coord.setText("("+e.getX() + ", " + e.getY() + ")");
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
            FiguraRetas.desenharRetangulo(g, x1, y1, x2, y2, x3, y3, x4, y4, "", getEsp(), getCorAtual());
        }
        else if (tipo==TipoPrimitivo.TRIANGULO && x1 != null && x2 != null && x3 != null) {   // Desenha o triângulo
            FiguraRetas.desenharTriangulo(g, x1, y1, x2, y2, x3, y3, "", getEsp(), getCorAtual());
        }
    }

    /**
     * Apaga os primitivos anteriores durante o desenho de Retas, Circulos e Retangulos
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
            FiguraRetas.desenharRetangulo(g, x1, y1, xant, yant, xant, y1, x1, yant, "", getEsp(), getBackground());
        }
    }

    public void redesenharPainel(Graphics g)    // Redesenha as figuras já presentes
    {
        for (int i = 0; i < formas.getTamanho(); i++) {
            if(formas.getFigura(i).getTipo() == TipoPrimitivo.PONTO)
                FiguraPontos.desenharPonto(g, formas.getFigura(i).getX1(), formas.getFigura(i).getY1(), "", formas.getFigura(i).getEsp(), formas.getFigura(i).getCor());
            else if(formas.getFigura(i).getTipo() == TipoPrimitivo.RETA)
                FiguraRetas.desenharReta(g, formas.getFigura(i).getX1(), formas.getFigura(i).getY1(), formas.getFigura(i).getX2(), formas.getFigura(i).getY2(), "", formas.getFigura(i).getEsp(), formas.getFigura(i).getCor());
            else if(formas.getFigura(i).getTipo() == TipoPrimitivo.RETANGULO)
                FiguraRetas.desenharRetangulo(g, formas.getFigura(i).getX1(), formas.getFigura(i).getY1(), formas.getFigura(i).getX2(), formas.getFigura(i).getY2(), formas.getFigura(i).getX3(), formas.getFigura(i).getY3(), formas.getFigura(i).getX4(), formas.getFigura(i).getY4(), "", formas.getFigura(i).getEsp(), formas.getFigura(i).getCor());
            else if(formas.getFigura(i).getTipo() == TipoPrimitivo.CIRCULO)
                FiguraCirculos.desenharCirculo(g, formas.getFigura(i).getX1(), formas.getFigura(i).getY1(), formas.getFigura(i).getX2(), formas.getFigura(i).getY2(), "", formas.getFigura(i).getEsp(), formas.getFigura(i).getCor());
            else if(formas.getFigura(i).getTipo() == TipoPrimitivo.TRIANGULO)
                FiguraRetas.desenharTriangulo(g, formas.getFigura(i).getX1(), formas.getFigura(i).getY1(), formas.getFigura(i).getX2(), formas.getFigura(i).getY2(), formas.getFigura(i).getX3(), formas.getFigura(i).getY3(), "", formas.getFigura(i).getEsp(), formas.getFigura(i).getCor());
        }
    }

    public void limparPainel(Graphics g)    // Redesenha todas as figuras com a cor do fundo "apagando-as" da tela
    {
        for (int i = 0; i < formas.getTamanho(); i++)
        {
            if(formas.getFigura(i).getTipo() == TipoPrimitivo.PONTO)
            {
                FiguraPontos.desenharPonto(g, formas.getFigura(i).getX1(), formas.getFigura(i).getY1(), "", formas.getFigura(i).getEsp(), getBackground());
            }
            else if(formas.getFigura(i).getTipo() == TipoPrimitivo.RETA)
            {
                FiguraRetas.desenharReta(g, formas.getFigura(i).getX1(), formas.getFigura(i).getY1(), formas.getFigura(i).getX2(), formas.getFigura(i).getY2(), "", formas.getFigura(i).getEsp(), getBackground());
            }
            else if(formas.getFigura(i).getTipo() == TipoPrimitivo.RETANGULO)
            {
                FiguraRetas.desenharRetangulo(g, formas.getFigura(i).getX1(), formas.getFigura(i).getY1(), formas.getFigura(i).getX2(), formas.getFigura(i).getY2(),  formas.getFigura(i).getX3(), formas.getFigura(i).getY3(), formas.getFigura(i).getX4(), formas.getFigura(i).getY4(), "", formas.getFigura(i).getEsp(), getBackground());
            }
            else if(formas.getFigura(i).getTipo() == TipoPrimitivo.CIRCULO)
            {
                FiguraCirculos.desenharCirculo(g, formas.getFigura(i).getX1(), formas.getFigura(i).getY1(), formas.getFigura(i).getX2(), formas.getFigura(i).getY2(), "", formas.getFigura(i).getEsp(), getBackground());
            }
            else if(formas.getFigura(i).getTipo() == TipoPrimitivo.TRIANGULO)
            {
                FiguraRetas.desenharTriangulo(g, formas.getFigura(i).getX1(), formas.getFigura(i).getY1(), formas.getFigura(i).getX2(), formas.getFigura(i).getY2(), formas.getFigura(i).getX3(), formas.getFigura(i).getY3(), "", formas.getFigura(i).getEsp(), getBackground());
            }
        }
    }

    public void restaurarPainel()   // Limpa a area de desenho mas mantem a espessura e cores atuais
    {
        Graphics g = getGraphics();
        limparPainel(g);
        formas.limparArray();
    }

    private boolean tipoDeSelecao() {
        return (tipo == TipoPrimitivo.DELETAR || tipo == TipoPrimitivo.ROTACAO || tipo == TipoPrimitivo.ESCALA || tipo == TipoPrimitivo.MOVER);
    }

    public double[] infosSelect(String s) {
        String pergunta, resposta;

        if (s.matches("Angulo")) {
            pergunta = "Insira o Ângulo de Rotação"; resposta = "Entre -360 e 360 graus: ";
        } else if (s.matches("Fatores")) {
            pergunta = "Insira Fatores de Escala"; resposta = "(x,y): ";
        } else if (s.matches("Fator")) {
            pergunta = "Insira um Fator de Escala"; resposta = "";
        } else if (s.isEmpty()) {
            pergunta = "Insira Direções para Mover"; resposta = "(x,y): ";
        } else {
            pergunta = "ERRO"; resposta = "ERRO";
        }

        String resp = JOptionPane.showInputDialog(null, pergunta, resposta, JOptionPane.QUESTION_MESSAGE);

        // Regex para verificar o formato (x, y) onde x e y são números decimais positivos
        String regexEscala = "\\(\\d+(\\.\\d+)?,\\d+(\\.\\d+)?\\)";
        // Regex para verificar o formato (x, y) onde x e y são números inteiros positivos ou negativos
        String regexMover = "\\((-?\\d+),(-?\\d+)\\)";

        try {
            if (s.matches("Fatores") && resp != null && resp.matches(regexEscala)) { // FATORES DE ESCALA
                resp = resp.replaceAll("[()\\s]", ""); // Remove parênteses e espaços
                String[] partes = resp.split(",");

                double[] escala = new double[2];
                escala[0] = Double.parseDouble(partes[0]); escala[1] = Double.parseDouble(partes[1]);

                // Verificar se o valor da escala é um inteiro positivo e menor ou igual a 10
                if (escala[0] > 0 && escala[0] <= 10 && escala[1] > 0 && escala[1] <= 10) {
                    return escala;  // Retorna um array de inteiros com o valor da escala
                }
            } else if (s.matches("Fator") && resp != null && resp.matches("\\d+(\\.\\d+)?")) {
                double[] escala = new double[1];
                escala[0] = Double.parseDouble(resp);

                // Verificar se o valor da escala é um inteiro positivo e menor ou igual a 10
                if (escala[0] > 0 && escala[0] <= 10) {
                    return escala;  // Retorna um array de inteiros com o valor da escala
                }
            }else if (s.matches("Angulo") && resp != null && resp.matches("-?\\d+")) { // ROTACAO
                int rotacao = Integer.parseInt(resp);

                // Verificar se o valor da rotação está dentro do intervalo -360 a 360
                if (rotacao >= -360 && rotacao <= 360) {
                    return new double[]{rotacao};  // Retorna um array de inteiros com o valor da rotação
                }
            } else if (s.isEmpty() && resp != null && resp.matches("\\d+(\\.\\d+)?")) { // MOVER
                resp = resp.replaceAll("[()\\s]", ""); // Remove parênteses e espaços
                String[] partes = resp.split(",");

                int x = Integer.parseInt(partes[0]);
                int y = Integer.parseInt(partes[1]);

                // Verificar se x <= 1000 e y <= 600
                if (x <= 1000 && y <= 600) {
                    return new double[]{x, y};  // Retorna um array de inteiros com as resps
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erro: Entrada inválida, tente novamente.");
        }

        // Caso a entrada seja inválida, chamar recursivamente para nova tentativa
        return infosSelect(s);
    }

}
