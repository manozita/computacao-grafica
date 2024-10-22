package controller;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.lang.Math;

import ponto.FiguraPontos;
import reta.FiguraRetas;
import circulo.FiguraCirculos;
import armazenamento.Array;

/**
 * Escreva uma descrição da classe Selecionar aqui.
 * 
 * @author (seu nome) 
 * @version (um número da versão ou uma data)
 */
public class Select
{
    PainelDesenho painel;
    int tolerancia;
    public Select (PainelDesenho painel) {
        this.painel = painel;
        tolerancia = painel.getEsp()/2 + 4;  // Erro do clique para deletar uma figura
    }
    
    public void selecionar(Integer x, Integer y) {
        boolean selecionado = false; // Verificador de seleção
        int i = painel.formas.getTamanho()-1;  // Indice do ultimo elemento
        TipoPrimitivo t;

        // encontrar qual o item do array que está sendo selecionado
        while(i >= 0 && !selecionado)    // na duvida é o mais recente
        {
            t = painel.formas.getFigura(i).getTipo();  // Faremos a comparação até achar um elemento mais próximo
            if(t == TipoPrimitivo.PONTO)
            {
                if(selecionarPonto(x, y, painel.formas.getFigura(i).getX1(), painel.formas.getFigura(i).getY1(), i))    // Verifica se da pra apagar
                {
                    selecionado = true;
                }
            }
            else if(t == TipoPrimitivo.RETA)
            {
                if(selecionarReta(x, y, painel.formas.getFigura(i).getX1(), painel.formas.getFigura(i).getY1(), painel.formas.getFigura(i).getX2(), painel.formas.getFigura(i).getY2(), i))
                {
                    selecionado = true;
                }
            }
            else if(t == TipoPrimitivo.RETANGULO)
            {
                if(selecionarRetangulo(x, y, painel.formas.getFigura(i).getX1(), painel.formas.getFigura(i).getY1(), painel.formas.getFigura(i).getX2(), painel.formas.getFigura(i).getY2(), i))
                {
                    selecionado = true;                
                }
            }
            else if(t == TipoPrimitivo.CIRCULO)
            {
                if(selecionarCirculo(x, y, painel.formas.getFigura(i).getX1(), painel.formas.getFigura(i).getY1(), painel.formas.getFigura(i).getX2(), painel.formas.getFigura(i).getY2(), i))
                {
                    selecionado = true;
                }
            }
            else if(t == TipoPrimitivo.TRIANGULO)
            {
                if(selecionarTriangulo(x, y, painel.formas.getFigura(i).getX1(), painel.formas.getFigura(i).getY1(), painel.formas.getFigura(i).getX2(), painel.formas.getFigura(i).getY2(), painel.formas.getFigura(i).getX3(), painel.formas.getFigura(i).getY3(), i))
                {
                    selecionado = true;
                }
            }
            i--;
        }        
    }
    public boolean selecionarPonto(Integer x, Integer y, Integer xP, Integer yP, int i)
    {
        double d;
        d = Math.sqrt((xP-x)*(xP-x)+(yP-y)*(yP-y));
        if(d <= tolerancia)
        {
            
            return true;
        }
        return false;
    } 

    public boolean selecionarReta(Integer x, Integer y, Integer x1R, Integer y1R, Integer x2R, Integer y2R, int i)
    {
        // Fórmula da distância ponto para reta
        double distancia = distanciaParaAresta(x, y, x1R, y1R, x2R, y2R);
        if (distancia <= tolerancia) { // Tolerância de 4 pixels
            return true;
        }
        return false;
    }

    public boolean selecionarRetangulo(Integer x, Integer y, Integer x1R, Integer y1R, Integer x2R, Integer y2R, int i) {
        // Definindo as quatro arestas do retângulo
        boolean proximoAresta1 = distanciaParaAresta(x, y, x1R, y1R, x2R, y1R) <= tolerancia; // Aresta superior
        boolean proximoAresta2 = distanciaParaAresta(x, y, x2R, y1R, x2R, y2R) <= tolerancia; // Aresta direita
        boolean proximoAresta3 = distanciaParaAresta(x, y, x2R, y2R, x1R, y2R) <= tolerancia; // Aresta inferior
        boolean proximoAresta4 = distanciaParaAresta(x, y, x1R, y2R, x1R, y1R) <= tolerancia; // Aresta esquerda

        // Se o ponto estiver próximo de alguma das arestas, apagamos o retângulo
        if (proximoAresta1 || proximoAresta2 || proximoAresta3 || proximoAresta4) {
            return true;
        }

        return false;
    }

    public boolean selecionarCirculo(Integer x, Integer y, Integer xC, Integer yC, Integer xB, Integer yB, int i) {
        // Cálculo do raio com base na distância entre o centro (xC, yC) e o ponto na borda (xB, yB)
        double raio = Math.sqrt((xB - xC) * (xB - xC) + (yB - yC) * (yB - yC));

        // Cálculo da distância do ponto clicado (x, y) ao centro do círculo (xC, yC)
        double distancia = Math.sqrt((xC - x) * (xC - x) + (yC - y) * (yC - y));

        // Verifica se a distância do clique é aproximadamente igual ao raio
        if (Math.abs(distancia - raio) <= 4) { // Tolerância de 4 pixels
            return true;
        }
        return false;
    }

    public boolean selecionarTriangulo(Integer x, Integer y, Integer x1T, Integer y1T, Integer x2T, Integer y2T, Integer x3T, Integer y3T, int i) {
        boolean proximoAresta1 = distanciaParaAresta(x, y, x1T, y1T, x2T, y2T) <= tolerancia;
        boolean proximoAresta2 = distanciaParaAresta(x, y, x2T, y2T, x3T, y3T) <= tolerancia;
        boolean proximoAresta3 = distanciaParaAresta(x, y, x3T, y3T, x1T, y1T) <= tolerancia;

        // Se o ponto estiver próximo a uma das arestas, apagamos o triângulo
        if (proximoAresta1 || proximoAresta2 || proximoAresta3)
        {
            return true;
        }
        return false;
    }
    
    private double distanciaParaAresta(Integer x, Integer y, Integer xA, Integer yA, Integer xB, Integer yB) {
        // Vetores (ponto A para o ponto B) e (ponto A para o ponto P)
        double ABx = xB - xA;
        double ABy = yB - yA;
        double APx = x - xA;
        double APy = y - yA;

        // Produto escalar de AB e AP
        double produtoEscalar = APx * ABx + APy * ABy;

        // Comprimento ao quadrado de AB
        double comprimentoAB2 = ABx * ABx + ABy * ABy;

        // Se o segmento é na verdade um ponto
        if (comprimentoAB2 == 0) {
            return Math.sqrt(Math.pow(x - xA, 2) + Math.pow(y - yA, 2));
        }

        // Projeção escalar do ponto no segmento normalizado entre 0 e 1
        double t = produtoEscalar / comprimentoAB2;

        // Verifica se a projeção está dentro do segmento
        if (t < 0) {
            // Mais perto do ponto A
            return Math.sqrt(Math.pow(x - xA, 2) + Math.pow(y - yA, 2));
        } else if (t > 1) {
            // Mais perto do ponto B
            return Math.sqrt(Math.pow(x - xB, 2) + Math.pow(y - yB, 2));
        }

        // Projeção está dentro do segmento, calcular ponto projetado
        double Px = xA + t * ABx;
        double Py = yA + t * ABy;

        // Distância do ponto (x, y) para o ponto projetado (Px, Py)
        return Math.sqrt(Math.pow(x - Px, 2) + Math.pow(y - Py, 2));
    }
}
