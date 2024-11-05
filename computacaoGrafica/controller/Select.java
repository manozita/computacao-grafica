package controller;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.*;
import GUI.*;

import java.lang.Math;

import ponto.FiguraPontos;
import reta.FiguraRetas;
import controller.TipoPrimitivo;
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
    public PainelDesenho painel;
    int tolerancia;
    Integer[] coordenadas, fator = null;
    public int i;

    public Select (PainelDesenho painel) {
        this.painel = painel;
        tolerancia = painel.getEsp()/2 + 4;  // Erro do clique para deletar uma figura
    }

    public void selecionar(Integer x, Integer y) {
        boolean selecionado = false; // Verificador de seleção
        TipoPrimitivo t;
        i = painel.formas.getTamanho()-1;  // Indice do ultimo elemento
        // encontrar qual o item do array que está sendo selecionado
        while(i >= 0 && !selecionado)    // na duvida é o mais recente
        {
            t = painel.formas.getFigura(i).getTipo();  // Faremos a comparação até achar um elemento mais próximo
            switch(t) {
                case PONTO:
                    if(selecionarPonto(x, y, painel.formas.getFigura(i).getX1(), painel.formas.getFigura(i).getY1(), i))
                        selecionado = true;
                    break;
                case RETA:
                    if(selecionarReta(x, y, painel.formas.getFigura(i).getX1(), painel.formas.getFigura(i).getY1(), painel.formas.getFigura(i).getX2(), painel.formas.getFigura(i).getY2(), i))
                        selecionado = true;
                    break;
                case RETANGULO:
                    if(selecionarRetangulo(x, y, painel.formas.getFigura(i).getX1(), painel.formas.getFigura(i).getY1(), painel.formas.getFigura(i).getX2(), painel.formas.getFigura(i).getY2(), painel.formas.getFigura(i).getX3(), painel.formas.getFigura(i).getY3(),painel.formas.getFigura(i).getX4(), painel.formas.getFigura(i).getY4(),i))
                        selecionado = true;
                    break;
                case CIRCULO:
                    if(selecionarCirculo(x, y, painel.formas.getFigura(i).getX1(), painel.formas.getFigura(i).getY1(), painel.formas.getFigura(i).getX2(), painel.formas.getFigura(i).getY2(), i))
                        selecionado = true;
                    break;
                case TRIANGULO:
                    if(selecionarTriangulo(x, y, painel.formas.getFigura(i).getX1(), painel.formas.getFigura(i).getY1(), painel.formas.getFigura(i).getX2(), painel.formas.getFigura(i).getY2(), painel.formas.getFigura(i).getX3(), painel.formas.getFigura(i).getY3(), i))
                        selecionado = true;
                    break;
            }
            if (selecionado == true) break;
            i--;
        }
        painel.setAcaoAtual("NENHUM");
    }

    public void setCoordenadas(int x, int y) {
        coordenadas = new Integer[2];
        coordenadas[0] = x; coordenadas[1] = y;
    }

    public boolean selecionarPonto(Integer x, Integer y, Integer xP, Integer yP, int i)
    {
        double distancia = Math.sqrt((xP-x)*(xP-x)+(yP-y)*(yP-y));
        if(distancia <= tolerancia)
        {
            if (painel.getTipo() == TipoPrimitivo.DELETAR) {
                Graphics g = painel.getGraphics();
                FiguraPontos.desenharPonto(g, xP, yP, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                painel.formas.apagarElemento(i);
            } else new PainelTransfs(painel, this, painel.formas.getFigura(i).getTipo());
            return true;
        }
        return false;
    }

    public boolean selecionarReta(Integer x, Integer y, Integer x1R, Integer y1R, Integer x2R, Integer y2R, int i)
    {
        // Fórmula da distância ponto para reta
        double distancia = distanciaParaAresta(x, y, x1R, y1R, x2R, y2R);
        if (distancia <= tolerancia) { // Tolerância de 4 pixels
            if (painel.getTipo() == TipoPrimitivo.DELETAR) {
                Graphics g = painel.getGraphics();
                FiguraRetas.desenharReta(g, x1R, y1R, x2R, y2R, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                painel.formas.apagarElemento(i);
            } else new PainelTransfs(painel, this, painel.formas.getFigura(i).getTipo());
            return true;
        }
        return false;
    }

    public boolean selecionarRetangulo(Integer x, Integer y, Integer x1R, Integer y1R, Integer x2R, Integer y2R, Integer x3R, Integer y3R, Integer x4R, Integer y4R, int i) {
        boolean proximoAresta1 = distanciaParaAresta(x, y, x1R, y1R, x3R, y3R) <= tolerancia; // Aresta superior
        boolean proximoAresta2 = distanciaParaAresta(x, y, x3R, y3R, x2R, y2R) <= tolerancia; // Aresta direita
        boolean proximoAresta3 = distanciaParaAresta(x, y, x2R, y2R, x4R, y4R) <= tolerancia; // Aresta inferior
        boolean proximoAresta4 = distanciaParaAresta(x, y, x4R, y4R, x1R, y1R) <= tolerancia; // Aresta esquerda

        if (proximoAresta1 || proximoAresta2 || proximoAresta3 || proximoAresta4) {
            if (painel.getTipo() == TipoPrimitivo.DELETAR) {
                Graphics g = painel.getGraphics();
                FiguraRetas.desenharRetangulo(g, x1R, y1R, x2R, y2R, x3R, y3R, x4R, y4R, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                painel.formas.apagarElemento(i);
            } else new PainelTransfs(painel, this, painel.formas.getFigura(i).getTipo());
            return true;
        }
        return false;
    }

    public boolean selecionarCirculo(Integer x, Integer y, Integer xC, Integer yC, Integer xB, Integer yB, int i) {
        double raio = Math.sqrt((xB - xC) * (xB - xC) + (yB - yC) * (yB - yC));
        double distancia = Math.sqrt((xC - x) * (xC - x) + (yC - y) * (yC - y));

        if (Math.abs(distancia - raio) <= tolerancia) {
            if (painel.getTipo() == TipoPrimitivo.DELETAR) {
                Graphics g = painel.getGraphics();
                FiguraCirculos.desenharCirculo(g, xC, yC, xB, yB, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                painel.formas.apagarElemento(i);
            } else {
                new PainelTransfs(painel, this, painel.formas.getFigura(i).getTipo());
            }
            return true;
        }
        return false;
    }

    public boolean selecionarTriangulo(Integer x, Integer y, Integer x1T, Integer y1T, Integer x2T, Integer y2T, Integer x3T, Integer y3T, int i) {
        boolean proximoAresta1 = distanciaParaAresta(x, y, x1T, y1T, x2T, y2T) <= tolerancia;
        boolean proximoAresta2 = distanciaParaAresta(x, y, x2T, y2T, x3T, y3T) <= tolerancia;
        boolean proximoAresta3 = distanciaParaAresta(x, y, x3T, y3T, x1T, y1T) <= tolerancia;

        if (proximoAresta1 || proximoAresta2 || proximoAresta3) {
            if (painel.getTipo() == TipoPrimitivo.DELETAR) {
                Graphics g = painel.getGraphics();
                FiguraRetas.desenharTriangulo(g, x1T, y1T, x2T, y2T, x3T, y3T, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                painel.formas.apagarElemento(i);
            } else new PainelTransfs(painel, this, painel.formas.getFigura(i).getTipo());
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

    public int[] rotacionarPonto(double x, double y, double refX, double refY, double theta) {
        // Desloca o ponto para a origem em relação ao ponto de referência
        double xDeslocado = x - refX;
        double yDeslocado = y - refY;

        // Aplica a rotação
        double novoX = xDeslocado * Math.cos(theta) - yDeslocado * Math.sin(theta);
        double novoY = xDeslocado * Math.sin(theta) + yDeslocado * Math.cos(theta);

        // Desloca de volta para o ponto de referência
        novoX += refX;
        novoY += refY;

        return new int[] {(int) novoX, (int) novoY}; // Retorna as novas coordenadas
    }

    public void transformacoesPonto(TipoPrimitivo tipoTransformacao, double valores[]) {
        Graphics g = painel.getGraphics();
        int xP = painel.formas.getFigura(i).getX1(); int yP = painel.formas.getFigura(i).getY1();
        switch (tipoTransformacao) {
            case MOVER: // mover
                FiguraPontos.desenharPonto(g, xP, yP, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                painel.formas.setCoordenadas(i, 0, xP+(int)valores[0], yP+(int)valores[1]);
                break;

            case ROTACAO: // rotacao
                double theta = Math.toRadians(valores[0]);

                FiguraPontos.desenharPonto(g, xP, yP, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());

                xP = (int)((xP - valores[0])*Math.cos(theta) - (yP - valores[1])*Math.sin(theta) + valores[0]); 
                yP = (int)((xP - valores[0])*Math.sin(theta) + (yP - valores[1])*Math.cos(theta) + valores[1]);
                break;
        }
        painel.redesenharPainel(painel.getGraphics());
    }

    public void transformacoesReta(TipoPrimitivo tipoTransformacao, double valores[]) {
        Graphics g = painel.getGraphics();
        int x1R = painel.formas.getFigura(i).getX1(); int y1R = painel.formas.getFigura(i).getY1();
        int x2R = painel.formas.getFigura(i).getX2(); int y2R = painel.formas.getFigura(i).getY2();
        switch (tipoTransformacao) {
            case MOVER:
                FiguraRetas.desenharReta(g, x1R, y1R, x2R, y2R, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                painel.formas.setCoordenadas(i, 0, x1R+(int)valores[0], y1R+(int)valores[1]);
                painel.formas.setCoordenadas(i, 1, x2R+(int)valores[0], y2R+(int)valores[1]);
                break;

            case ESCALA:
                double fatorDeEscalaX = valores[0];
                double fatorDeEscalaY = valores[1];
                // Coordenadas do ponto clicado pelo usuário (em relação a esse ponto que a escala será aplicada)
                double px = valores[2]; // Coordenada x do ponto clicado
                double py = valores[3]; // Coordenada y do ponto clicado
                FiguraRetas.desenharReta(g, x1R, y1R, x2R, y2R, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                // Supondo que fatorDeEscalaX e fatorDeEscalaY sejam os fatores de escala em x e y respectivamente

                // Passo 1: Deslocar p1 e p2 para a origem em relação ao ponto (px, py)
                double x1Deslocado = x1R - px;
                double y1Deslocado = y1R - py;
                double x2Deslocado = x2R - px;
                double y2Deslocado = y2R - py;

                // Passo 2: Aplicar a escala separadamente para x e y
                x1Deslocado *= fatorDeEscalaX;
                y1Deslocado *= fatorDeEscalaY;
                x2Deslocado *= fatorDeEscalaX;
                y2Deslocado *= fatorDeEscalaY;

                // Passo 3: Deslocar de volta para o ponto (px, py)
                x1R = (int)(px + x1Deslocado);
                y1R = (int)(py + y1Deslocado);
                x2R = (int)(px + x2Deslocado);
                y2R = (int)(py + y2Deslocado);

                // Atualizar as coordenadas dos pontos
                painel.formas.setCoordenadas(i, 0, x1R, y1R); // Atualiza p1
                painel.formas.setCoordenadas(i, 1, x2R, y2R); // Atualiza p2
                break;

            case ROTACAO:
                double theta = Math.toRadians(valores[0]);
                FiguraRetas.desenharReta(g, x1R, y1R, x2R, y2R, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());

                // Coordenadas do ponto de rotação
                double pxR = valores[1];
                double pyR = valores[2];

                // Passo 1: Deslocar p1 e p2 para a origem em relação ao ponto (300, 300)
                double x1DeslocadoR = x1R - pxR;
                double y1DeslocadoR = y1R - pyR;
                double x2DeslocadoR = x2R - pxR;
                double y2DeslocadoR = y2R - pyR;

                // Passo 2: Aplicar a rotação a cada ponto em relação ao ponto de rotação (300, 300)
                double rotacaoX1 = x1DeslocadoR * Math.cos(theta) - y1DeslocadoR * Math.sin(theta);
                double rotacaoY1 = x1DeslocadoR * Math.sin(theta) + y1DeslocadoR * Math.cos(theta);

                double rotacaoX2 = x2DeslocadoR * Math.cos(theta) - y2DeslocadoR * Math.sin(theta);
                double rotacaoY2 = x2DeslocadoR * Math.sin(theta) + y2DeslocadoR * Math.cos(theta);

                // Passo 3: Deslocar de volta para o ponto (300, 300)
                x1R = (int)(pxR + rotacaoX1);
                y1R = (int)(pyR + rotacaoY1);
                x2R = (int)(pxR + rotacaoX2);
                y2R = (int)(pyR + rotacaoY2);

                // Atualizar as coordenadas dos pontos
                painel.formas.setCoordenadas(i, 0, x1R, y1R); // Atualiza p1
                painel.formas.setCoordenadas(i, 1, x2R, y2R); // Atualiza p2
                break;
        }
        painel.redesenharPainel(painel.getGraphics());
    }

    public void transformacoesCirculo(TipoPrimitivo tipoTransformacao, double valores[]) {
        Graphics g = painel.getGraphics();
        int xC = painel.formas.getFigura(i).getX1(); int yC = painel.formas.getFigura(i).getY1();
        int xB = painel.formas.getFigura(i).getX2(); int yB = painel.formas.getFigura(i).getY2();
        switch (tipoTransformacao) {
            case DELETAR:
                FiguraCirculos.desenharCirculo(g, xC, yC, xB, yB, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                painel.formas.apagarElemento(i);
                break;
            case MOVER:
                FiguraCirculos.desenharCirculo(g, xC, yC, xB, yB, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                painel.formas.setCoordenadas(i, 0, xC + (int) valores[0], yC + (int) valores[1]);
                painel.formas.setCoordenadas(i, 1, xB + (int) valores[0], yB + (int) valores[1]);
                break;
            case ESCALACIRC:
                double fatorDeEscala = valores[0];
                double refX = valores[1];
                double refY = valores[2];
                FiguraCirculos.desenharCirculo(g, xC, yC, xB, yB, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());

                double xCDeslocado = xC - refX;
                double yCDeslocado = yC - refY;
                double xBDeslocado = xB - refX;
                double yBDeslocado = yB - refY;

                xCDeslocado *= fatorDeEscala;
                yCDeslocado *= fatorDeEscala;
                xBDeslocado *= fatorDeEscala;
                yBDeslocado *= fatorDeEscala;

                xC = (int) (refX + xCDeslocado);
                yC = (int) (refY + yCDeslocado);
                xB = (int) (refX + xBDeslocado);
                yB = (int) (refY + yBDeslocado);

                painel.formas.setCoordenadas(i, 0, xC, yC);
                painel.formas.setCoordenadas(i, 1, xB, yB);
                break;
            case ROTACAO:
                double theta = Math.toRadians(valores[0]);
                FiguraCirculos.desenharCirculo(g, xC, yC, xB, yB, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                double refXRot = valores[1];
                double refYRot = valores[2];

                double xCDeslocadoR = xC - refXRot;
                double yCDeslocadoR = yC - refYRot;
                double xBDeslocadoR = xB - refXRot;
                double yBDeslocadoR = yB - refYRot;

                double rotacaoXC = xCDeslocadoR * Math.cos(theta) - yCDeslocadoR * Math.sin(theta);
                double rotacaoYC = xCDeslocadoR * Math.sin(theta) + yCDeslocadoR * Math.cos(theta);
                double rotacaoXB = xBDeslocadoR * Math.cos(theta) - yBDeslocadoR * Math.sin(theta);
                double rotacaoYB = xBDeslocadoR * Math.sin(theta) + yBDeslocadoR * Math.cos(theta);

                xC = (int) (refXRot + rotacaoXC);
                yC = (int) (refYRot + rotacaoYC);
                xB = (int) (refXRot + rotacaoXB);
                yB = (int) (refYRot + rotacaoYB);

                painel.formas.setCoordenadas(i, 0, xC, yC);
                painel.formas.setCoordenadas(i, 1, xB, yB);
                break;
        }
        painel.redesenharPainel(painel.getGraphics());
    }

    public void transformacoesRetangulo(TipoPrimitivo tipoTransformacao, double valores[]) {
        Graphics g = painel.getGraphics();
        int x1R = painel.formas.getFigura(i).getX1(); int y1R = painel.formas.getFigura(i).getY1();
        int x2R = painel.formas.getFigura(i).getX2(); int y2R = painel.formas.getFigura(i).getY2();
        int x3R = painel.formas.getFigura(i).getX3(); int y3R = painel.formas.getFigura(i).getY3();
        int x4R = painel.formas.getFigura(i).getX4(); int y4R = painel.formas.getFigura(i).getY4();
        switch (tipoTransformacao) {
            case MOVER:
                g = painel.getGraphics();
                FiguraRetas.desenharRetangulo(g, x1R, y1R, x2R, y2R, x3R, y3R, x4R, y4R, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                painel.formas.setCoordenadas(i, 0, x1R+(int)valores[0], y1R+(int)valores[1]);
                painel.formas.setCoordenadas(i, 1, x2R+(int)valores[0], y2R+(int)valores[1]);
                painel.formas.setCoordenadas(i, 2, x3R+(int)valores[0], y3R+(int)valores[1]);
                painel.formas.setCoordenadas(i, 3, x4R+(int)valores[0], y4R+(int)valores[1]);
                break;

            case ESCALA:
                double fatorDeEscalaX = valores[0];
                double fatorDeEscalaY = valores[1];
                double refX = valores[2];
                double refY = valores[3];
                g = painel.getGraphics();
                FiguraRetas.desenharRetangulo(g, x1R, y1R, x2R, y2R, x3R, y3R, x4R, y4R, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());

                double x1Deslocado = x1R - refX;
                double y1Deslocado = y1R - refY;
                double x2Deslocado = x2R - refX;
                double y2Deslocado = y2R - refY;
                double x3Deslocado = x3R - refX;
                double y3Deslocado = y3R - refY;
                double x4Deslocado = x4R - refX;
                double y4Deslocado = y4R - refY;

                x1Deslocado *= fatorDeEscalaX;
                y1Deslocado *= fatorDeEscalaY;
                x2Deslocado *= fatorDeEscalaX;
                y2Deslocado *= fatorDeEscalaY;
                x3Deslocado *= fatorDeEscalaX;
                y3Deslocado *= fatorDeEscalaY;
                x4Deslocado *= fatorDeEscalaX;
                y4Deslocado *= fatorDeEscalaY;

                x1R = (int)(refX + x1Deslocado);
                y1R = (int)(refY + y1Deslocado);
                x2R = (int)(refX + x2Deslocado);
                y2R = (int)(refY + y2Deslocado);
                x3R = (int)(refX + x3Deslocado);
                y3R = (int)(refY + y3Deslocado);
                x4R = (int)(refX + x4Deslocado);
                y4R = (int)(refY + y4Deslocado);

                painel.formas.setCoordenadas(i, 0, x1R, y1R);
                painel.formas.setCoordenadas(i, 1, x2R, y2R);
                painel.formas.setCoordenadas(i, 2, x3R, y3R);
                painel.formas.setCoordenadas(i, 3, x4R, y4R);
                break;

            case ROTACAO:
                double theta = Math.toRadians(valores[0]);
                g = painel.getGraphics();
                FiguraRetas.desenharRetangulo(g, x1R, y1R, x2R, y2R, x3R, y3R, x4R, y4R, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                refX = valores[1];
                refY = valores[2];

                x1Deslocado = x1R - refX;
                y1Deslocado = y1R - refY;
                x2Deslocado = x2R - refX;
                y2Deslocado = y2R - refY;
                x3Deslocado = x3R - refX;
                y3Deslocado = y3R - refY;
                x4Deslocado = x4R - refX;
                y4Deslocado = y4R - refY;

                double rotacaoX1 = x1Deslocado * Math.cos(theta) - y1Deslocado * Math.sin(theta);
                double rotacaoY1 = x1Deslocado * Math.sin(theta) + y1Deslocado * Math.cos(theta);
                double rotacaoX2 = x2Deslocado * Math.cos(theta) - y2Deslocado * Math.sin(theta);
                double rotacaoY2 = x2Deslocado * Math.sin(theta) + y2Deslocado * Math.cos(theta);
                double rotacaoX3 = x3Deslocado * Math.cos(theta) - y3Deslocado * Math.sin(theta);
                double rotacaoY3 = x3Deslocado * Math.sin(theta) + y3Deslocado * Math.cos(theta);
                double rotacaoX4 = x4Deslocado * Math.cos(theta) - y4Deslocado * Math.sin(theta);
                double rotacaoY4 = x4Deslocado * Math.sin(theta) + y4Deslocado * Math.cos(theta);

                x1R = (int)(refX + rotacaoX1);
                y1R = (int)(refY + rotacaoY1);
                x2R = (int)(refX + rotacaoX2);
                y2R = (int)(refY + rotacaoY2);
                x3R = (int)(refX + rotacaoX3);
                y3R = (int)(refY + rotacaoY3);
                x4R = (int)(refX + rotacaoX4);
                y4R = (int)(refY + rotacaoY4);

                painel.formas.setCoordenadas(i, 0, x1R, y1R);
                painel.formas.setCoordenadas(i, 1, x2R, y2R);
                painel.formas.setCoordenadas(i, 2, x3R, y3R);
                painel.formas.setCoordenadas(i, 3, x4R, y4R);
                break;
        }
        painel.redesenharPainel(painel.getGraphics());
    }

    public void transformacoesTriangulo(TipoPrimitivo tipoTransformacao, double valores[]) {
        Graphics g = painel.getGraphics();
        int x1T = painel.formas.getFigura(i).getX1(); int y1T = painel.formas.getFigura(i).getY1();
        int x2T = painel.formas.getFigura(i).getX2(); int y2T = painel.formas.getFigura(i).getY2();
        int x3T = painel.formas.getFigura(i).getX3(); int y3T = painel.formas.getFigura(i).getY3();
        switch (tipoTransformacao) {
            case MOVER:
                FiguraRetas.desenharTriangulo(g, x1T, y1T, x2T, y2T, x3T, y3T, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                painel.formas.setCoordenadas(i, 0, x1T + (int) valores[0], y1T + (int) valores[1]);
                painel.formas.setCoordenadas(i, 1, x2T + (int) valores[0], y2T + (int) valores[1]);
                painel.formas.setCoordenadas(i, 2, x3T + (int) valores[0], y3T + (int) valores[1]);
                break;
            case ESCALA:
                double fatorDeEscalaX = valores[0];
                double fatorDeEscalaY = valores[1];
                FiguraRetas.desenharTriangulo(g, x1T, y1T, x2T, y2T, x3T, y3T, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                double refX = valores[2];
                double refY = valores[3];

                double x1Deslocado = x1T - refX;
                double y1Deslocado = y1T - refY;
                double x2Deslocado = x2T - refX;
                double y2Deslocado = y2T - refY;
                double x3Deslocado = x3T - refX;
                double y3Deslocado = y3T - refY;

                x1Deslocado *= fatorDeEscalaX;
                y1Deslocado *= fatorDeEscalaY;
                x2Deslocado *= fatorDeEscalaX;
                y2Deslocado *= fatorDeEscalaY;
                x3Deslocado *= fatorDeEscalaX;
                y3Deslocado *= fatorDeEscalaY;

                x1T = (int) (refX + x1Deslocado);
                y1T = (int) (refY + y1Deslocado);
                x2T = (int) (refX + x2Deslocado);
                y2T = (int) (refY + y2Deslocado);
                x3T = (int) (refX + x3Deslocado);
                y3T = (int) (refY + y3Deslocado);

                painel.formas.setCoordenadas(i, 0, x1T, y1T);
                painel.formas.setCoordenadas(i, 1, x2T, y2T);
                painel.formas.setCoordenadas(i, 2, x3T, y3T);
                break;
            case ROTACAO:
                double theta = Math.toRadians(valores[0]);
                FiguraRetas.desenharTriangulo(g, x1T, y1T, x2T, y2T, x3T, y3T, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                double refXRot = valores[1];
                double refYRot = valores[2];

                int[] novoPonto1 = rotacionarPonto(x1T, y1T, refXRot, refYRot, theta);
                int[] novoPonto2 = rotacionarPonto(x2T, y2T, refXRot, refYRot, theta);
                int[] novoPonto3 = rotacionarPonto(x3T, y3T, refXRot, refYRot, theta);

                painel.formas.setCoordenadas(i, 0, novoPonto1[0], novoPonto1[1]);
                painel.formas.setCoordenadas(i, 1, novoPonto2[0], novoPonto2[1]);
                painel.formas.setCoordenadas(i, 2, novoPonto3[0], novoPonto3[1]);
                break;
        }
        painel.redesenharPainel(painel.getGraphics());
    }
}
