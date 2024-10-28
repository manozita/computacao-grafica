package controller;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Graphics;

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
                    selecionado = true;
            }
            else if(t == TipoPrimitivo.RETA)
            {
                if(selecionarReta(x, y, painel.formas.getFigura(i).getX1(), painel.formas.getFigura(i).getY1(), painel.formas.getFigura(i).getX2(), painel.formas.getFigura(i).getY2(), i))
                    selecionado = true;
            }
            else if(t == TipoPrimitivo.RETANGULO)
            {
                if(selecionarRetangulo(x, y, painel.formas.getFigura(i).getX1(), painel.formas.getFigura(i).getY1(), painel.formas.getFigura(i).getX2(), painel.formas.getFigura(i).getY2(), painel.formas.getFigura(i).getX3(), painel.formas.getFigura(i).getY3(),painel.formas.getFigura(i).getX4(), painel.formas.getFigura(i).getY4(),i))
                    selecionado = true;                
            }
            else if(t == TipoPrimitivo.CIRCULO)
            {
                if(selecionarCirculo(x, y, painel.formas.getFigura(i).getX1(), painel.formas.getFigura(i).getY1(), painel.formas.getFigura(i).getX2(), painel.formas.getFigura(i).getY2(), i))
                    selecionado = true;
            }
            else if(t == TipoPrimitivo.TRIANGULO)
            {
                if(selecionarTriangulo(x, y, painel.formas.getFigura(i).getX1(), painel.formas.getFigura(i).getY1(), painel.formas.getFigura(i).getX2(), painel.formas.getFigura(i).getY2(), painel.formas.getFigura(i).getX3(), painel.formas.getFigura(i).getY3(), i))
                    selecionado = true;
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
            Graphics g = painel.getGraphics();
            if (painel.getTipo() == TipoPrimitivo.DELETAR) {
                FiguraPontos.desenharPonto(g, xP, yP, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                painel.formas.apagarElemento(i);
            } else if(painel.getTipo() == TipoPrimitivo.MOVER) {
                FiguraPontos.desenharPonto(g, xP, yP, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                painel.formas.setCoordenadas(i, 0, xP+10, yP+10);
            } else if(painel.getTipo() == TipoPrimitivo.ESCALA) {
                FiguraPontos.desenharPonto(g, xP, yP, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
            } else if(painel.getTipo() == TipoPrimitivo.ROTACAO) {
                FiguraPontos.desenharPonto(g, xP, yP, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                double theta = Math.toRadians(90);

                // Alterar (300;300) pelo ponto (x;y) dado pelo usuário
                xP = (int)((xP - 300)*Math.cos(theta) - (yP - 300)*Math.sin(theta) + 300); 
                yP = (int)((xP - 300)*Math.sin(theta) + (yP - 300)*Math.cos(theta) + 300);
                painel.formas.setCoordenadas(i, 0, xP, yP);
            }
            return true;
        }
        return false;
    }

    public boolean selecionarReta(Integer x, Integer y, Integer x1R, Integer y1R, Integer x2R, Integer y2R, int i)
    {
        // Fórmula da distância ponto para reta
        double distancia = distanciaParaAresta(x, y, x1R, y1R, x2R, y2R);
        if (distancia <= tolerancia) { // Tolerância de 4 pixels
            Graphics g = painel.getGraphics();
            if (painel.getTipo() == TipoPrimitivo.DELETAR) {
                FiguraRetas.desenharReta(g, x1R, y1R, x2R, y2R, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                painel.formas.apagarElemento(i);
            } else if(painel.getTipo() == TipoPrimitivo.MOVER) {
                FiguraRetas.desenharReta(g, x1R, y1R, x2R, y2R, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                painel.formas.setCoordenadas(i, 0, x1R+10, y1R+10);
                painel.formas.setCoordenadas(i, 1, x2R+10, y2R+10);
            } else if(painel.getTipo() == TipoPrimitivo.ESCALA) {
                FiguraRetas.desenharReta(g, x1R, y1R, x2R, y2R, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                // Supondo que xEscala e yEscala sejam os fatores de escala em x e y respectivamente
                double xEscala = 2; // Exemplo de fator de escala em x
                double yEscala = 1.5; // Exemplo de fator de escala em y

                // Coordenadas do ponto clicado pelo usuário (em relação a esse ponto que a escala será aplicada)
                double px = 300; // Coordenada x do ponto clicado
                double py = 300; // Coordenada y do ponto clicado

                // Passo 1: Deslocar p1 e p2 para a origem em relação ao ponto (px, py)
                double x1Deslocado = x1R - px;
                double y1Deslocado = y1R - py;
                double x2Deslocado = x2R - px;
                double y2Deslocado = y2R - py;

                // Passo 2: Aplicar a escala separadamente para x e y
                x1Deslocado *= xEscala;
                y1Deslocado *= yEscala;
                x2Deslocado *= xEscala;
                y2Deslocado *= yEscala;

                // Passo 3: Deslocar de volta para o ponto (px, py)
                x1R = (int)(px + x1Deslocado);
                y1R = (int)(py + y1Deslocado);
                x2R = (int)(px + x2Deslocado);
                y2R = (int)(py + y2Deslocado);

                // Atualizar as coordenadas dos pontos
                painel.formas.setCoordenadas(i, 0, x1R, y1R); // Atualiza p1
                painel.formas.setCoordenadas(i, 1, x2R, y2R); // Atualiza p2

            } else if(painel.getTipo() == TipoPrimitivo.ROTACAO) {
                FiguraRetas.desenharReta(g, x1R, y1R, x2R, y2R, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                double theta = Math.toRadians(90); // Ângulo de rotação em radianos

                // Coordenadas do ponto de rotação
                double px = 300;
                double py = 300;

                // Passo 1: Deslocar p1 e p2 para a origem em relação ao ponto (300, 300)
                double x1Deslocado = x1R - px;
                double y1Deslocado = y1R - py;
                double x2Deslocado = x2R - px;
                double y2Deslocado = y2R - py;

                // Passo 2: Aplicar a rotação a cada ponto em relação ao ponto de rotação (300, 300)
                double rotacaoX1 = x1Deslocado * Math.cos(theta) - y1Deslocado * Math.sin(theta);
                double rotacaoY1 = x1Deslocado * Math.sin(theta) + y1Deslocado * Math.cos(theta);

                double rotacaoX2 = x2Deslocado * Math.cos(theta) - y2Deslocado * Math.sin(theta);
                double rotacaoY2 = x2Deslocado * Math.sin(theta) + y2Deslocado * Math.cos(theta);

                // Passo 3: Deslocar de volta para o ponto (300, 300)
                x1R = (int)(px + rotacaoX1);
                y1R = (int)(py + rotacaoY1);
                x2R = (int)(px + rotacaoX2);
                y2R = (int)(py + rotacaoY2);

                // Atualizar as coordenadas dos pontos
                painel.formas.setCoordenadas(i, 0, x1R, y1R); // Atualiza p1
                painel.formas.setCoordenadas(i, 1, x2R, y2R); // Atualiza p2

            }
            return true;
        }
        return false;
    }

    public boolean selecionarRetangulo(Integer x, Integer y, Integer x1R, Integer y1R, Integer x2R, Integer y2R, Integer x3R, Integer y3R, Integer x4R, Integer y4R, int i) {
        // Definindo as quatro arestas do retângulo
        boolean proximoAresta1 = distanciaParaAresta(x, y, x1R, y1R, x3R, y3R) <= tolerancia; // Aresta superior
        boolean proximoAresta2 = distanciaParaAresta(x, y, x3R, y3R, x2R, y2R) <= tolerancia; // Aresta direita
        boolean proximoAresta3 = distanciaParaAresta(x, y, x2R, y2R, x4R, y4R) <= tolerancia; // Aresta inferior
        boolean proximoAresta4 = distanciaParaAresta(x, y, x4R, y4R, x1R, y1R) <= tolerancia; // Aresta esquerda

        // Se o ponto estiver próximo de alguma das arestas, apagamos o retângulo
        if (proximoAresta1 || proximoAresta2 || proximoAresta3 || proximoAresta4) {
            Graphics g = painel.getGraphics();
            if (painel.getTipo() == TipoPrimitivo.DELETAR) {
                FiguraRetas.desenharRetangulo(g, x1R, y1R, x2R, y2R, x3R, y3R, x4R, y4R, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                painel.formas.apagarElemento(i);
            } else if(painel.getTipo() == TipoPrimitivo.MOVER) {
                FiguraRetas.desenharRetangulo(g, x1R, y1R, x2R, y2R, x3R, y3R, x4R, y4R, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                painel.formas.setCoordenadas(i, 0, x1R+10, y1R+10);
                painel.formas.setCoordenadas(i, 1, x2R+10, y2R+10);
                painel.formas.setCoordenadas(i, 2, x3R+10, y3R+10);
                painel.formas.setCoordenadas(i, 3, x4R+10, y4R+10);
            } else if(painel.getTipo() == TipoPrimitivo.ESCALA) {
                FiguraRetas.desenharRetangulo(g, x1R, y1R, x2R, y2R, x3R, y3R, x4R, y4R, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                // painel.formas.setCoordenadas(i, 0, x1R*2, y1R*2);
                // painel.formas.setCoordenadas(i, 1, x2R*2, y2R*2);

                // Supondo que xEscala e yEscala sejam os fatores de escala em x e y respectivamente
                double xEscala = 2; // Exemplo de fator de escala em x
                double yEscala = 1.5; // Exemplo de fator de escala em y

                // Coordenadas do ponto de referência para a escala
                double refX = 300;
                double refY = 300;

                // Deslocar cada ponto para a origem em relação ao ponto de referência (300, 300)
                double x1Deslocado = x1R - refX;
                double y1Deslocado = y1R - refY;
                double x2Deslocado = x2R - refX;
                double y2Deslocado = y2R - refY;
                double x3Deslocado = x3R - refX;
                double y3Deslocado = y3R - refY;
                double x4Deslocado = x4R - refX;
                double y4Deslocado = y4R - refY;

                // Aplicar a escala separadamente para x e y
                x1Deslocado *= xEscala;
                y1Deslocado *= yEscala;
                x2Deslocado *= xEscala;
                y2Deslocado *= yEscala;
                x3Deslocado *= xEscala;
                y3Deslocado *= yEscala;
                x4Deslocado *= xEscala;
                y4Deslocado *= yEscala;

                // Deslocar de volta para o ponto de referência (300, 300)
                x1R = (int)(refX + x1Deslocado);
                y1R = (int)(refY + y1Deslocado);
                x2R = (int)(refX + x2Deslocado);
                y2R = (int)(refY + y2Deslocado);
                x3R = (int)(refX + x3Deslocado);
                y3R = (int)(refY + y3Deslocado);
                x4R = (int)(refX + x4Deslocado);
                y4R = (int)(refY + y4Deslocado);

                // Atualizar as coordenadas dos pontos
                painel.formas.setCoordenadas(i, 0, x1R, y1R); // Atualiza p1
                painel.formas.setCoordenadas(i, 1, x2R, y2R); // Atualiza p2
                painel.formas.setCoordenadas(i, 2, x3R, y3R); // Atualiza p3
                painel.formas.setCoordenadas(i, 3, x4R, y4R); // Atualiza p4

            } else if (painel.getTipo() == TipoPrimitivo.ROTACAO) {
                FiguraRetas.desenharRetangulo(g, x1R, y1R, x2R, y2R, x3R, y3R, x4R, y4R, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                // Ângulo de rotação em radianos
                double theta = Math.toRadians(45); // Exemplo de ângulo de 90 graus

                // Coordenadas do ponto de referência para a rotação
                double refX = 300;
                double refY = 300;

                // Passo 1: Deslocar cada ponto para a origem em relação ao ponto de referência (300, 300)
                double x1Deslocado = x1R - refX;
                double y1Deslocado = y1R - refY;
                double x2Deslocado = x2R - refX;
                double y2Deslocado = y2R - refY;
                double x3Deslocado = x3R - refX;
                double y3Deslocado = y3R - refY;
                double x4Deslocado = x4R - refX;
                double y4Deslocado = y4R - refY;

                // Passo 2: Aplicar a rotação a cada ponto
                double rotacaoX1 = x1Deslocado * Math.cos(theta) - y1Deslocado * Math.sin(theta);
                double rotacaoY1 = x1Deslocado * Math.sin(theta) + y1Deslocado * Math.cos(theta);

                double rotacaoX2 = x2Deslocado * Math.cos(theta) - y2Deslocado * Math.sin(theta);
                double rotacaoY2 = x2Deslocado * Math.sin(theta) + y2Deslocado * Math.cos(theta);

                double rotacaoX3 = x3Deslocado * Math.cos(theta) - y3Deslocado * Math.sin(theta);
                double rotacaoY3 = x3Deslocado * Math.sin(theta) + y3Deslocado * Math.cos(theta);

                double rotacaoX4 = x4Deslocado * Math.cos(theta) - y4Deslocado * Math.sin(theta);
                double rotacaoY4 = x4Deslocado * Math.sin(theta) + y4Deslocado * Math.cos(theta);

                // Passo 3: Deslocar de volta para o ponto de referência (300, 300)
                x1R = (int)(refX + rotacaoX1);
                y1R = (int)(refY + rotacaoY1);
                x2R = (int)(refX + rotacaoX2);
                y2R = (int)(refY + rotacaoY2);
                x3R = (int)(refX + rotacaoX3);
                y3R = (int)(refY + rotacaoY3);
                x4R = (int)(refX + rotacaoX4);
                y4R = (int)(refY + rotacaoY4);

                // Atualizar as coordenadas dos pontos
                painel.formas.setCoordenadas(i, 0, x1R, y1R); // Atualiza p1
                painel.formas.setCoordenadas(i, 1, x2R, y2R); // Atualiza p2
                painel.formas.setCoordenadas(i, 2, x3R, y3R); // Atualiza p3
                painel.formas.setCoordenadas(i, 3, x4R, y4R); // Atualiza p4

            }
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
        if (Math.abs(distancia - raio) <= tolerancia) { // Tolerância de 4 pixels
            Graphics g = painel.getGraphics();
            if (painel.getTipo() == TipoPrimitivo.DELETAR) {
                FiguraCirculos.desenharCirculo(g, xC, yC, xB, yB, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                painel.formas.apagarElemento(i);
            } else if(painel.getTipo() == TipoPrimitivo.MOVER) {
                FiguraCirculos.desenharCirculo(g, xC, yC, xB, yB, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                painel.formas.setCoordenadas(i, 0, xC+10, yC+10);
                painel.formas.setCoordenadas(i, 1, xB+10, yB+10);                
            } else if(painel.getTipo() == TipoPrimitivo.ESCALA) {
                FiguraCirculos.desenharCirculo(g, xC, yC, xB, yB, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                // Supondo que 'fatorDeEscala' seja o fator de escala
                double fatorDeEscala = 2; // Exemplo de fator de escala

                // Coordenadas do ponto de referência para a escala
                double refX = 300;
                double refY = 300;

                // Passo 1: Calcular o vetor de deslocamento do centro em relação ao ponto de referência (300, 300)
                double xCDeslocado = xC - refX;
                double yCDeslocado = yC - refY;

                // Passo 2: Calcular o vetor de deslocamento da borda em relação ao ponto de referência
                double xBDeslocado = xB - refX;
                double yBDeslocado = yB - refY;

                // Passo 3: Aplicar a escala a ambos os deslocamentos
                xCDeslocado *= fatorDeEscala;
                yCDeslocado *= fatorDeEscala;
                xBDeslocado *= fatorDeEscala;
                yBDeslocado *= fatorDeEscala;

                // Passo 4: Deslocar de volta para o ponto de referência
                xC = (int)(refX + xCDeslocado);
                yC = (int)(refY + yCDeslocado);
                xB = (int)(refX + xBDeslocado);
                yB = (int)(refY + yBDeslocado);

                // Atualizar as coordenadas do centro e da borda
                painel.formas.setCoordenadas(i, 0, xC, yC); // Atualiza o centro
                painel.formas.setCoordenadas(i, 1, xB, yB); // Atualiza a borda
            }
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
            Graphics g = painel.getGraphics();
            if (painel.getTipo() == TipoPrimitivo.DELETAR) {
                FiguraRetas.desenharTriangulo(g, x1T, y1T, x2T, y2T, x3T, y3T, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                painel.formas.apagarElemento(i);
            } else if(painel.getTipo() == TipoPrimitivo.MOVER) {
                FiguraRetas.desenharTriangulo(g, x1T, y1T, x2T, y2T, x3T, y3T, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                painel.formas.setCoordenadas(i, 0, x1T+10, y1T+10);
                painel.formas.setCoordenadas(i, 1, x2T+10, y2T+10);
                painel.formas.setCoordenadas(i, 2, x3T+10, y3T+10);
            } else if(painel.getTipo() == TipoPrimitivo.ESCALA) {
                FiguraRetas.desenharTriangulo(g, x1T, y1T, x2T, y2T, x3T, y3T, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                // Supondo que 'fatorDeEscalaX' e 'fatorDeEscalaY' sejam os fatores de escala
                double fatorDeEscalaX = 2; // Fator de escala no eixo X
                double fatorDeEscalaY = 1.5; // Fator de escala no eixo Y

                // Coordenadas do ponto de referência para a escala
                double refX = 300;
                double refY = 300;

                // Passo 1: Calcular o vetor de deslocamento dos pontos em relação ao ponto de referência (300, 300)
                double x1Deslocado = x1T - refX;
                double y1Deslocado = y1T - refY;
                double x2Deslocado = x2T - refX;
                double y2Deslocado = y2T - refY;
                double x3Deslocado = x3T - refX;
                double y3Deslocado = y3T - refY;

                // Passo 2: Aplicar a escala
                x1Deslocado *= fatorDeEscalaX;
                y1Deslocado *= fatorDeEscalaY;
                x2Deslocado *= fatorDeEscalaX;
                y2Deslocado *= fatorDeEscalaY;
                x3Deslocado *= fatorDeEscalaX;
                y3Deslocado *= fatorDeEscalaY;

                // Passo 3: Deslocar de volta para o ponto de referência
                x1T = (int)(refX + x1Deslocado);
                y1T = (int)(refY + y1Deslocado);
                x2T = (int)(refX + x2Deslocado);
                y2T = (int)(refY + y2Deslocado);
                x3T = (int)(refX + x3Deslocado);
                y3T = (int)(refY + y3Deslocado);

                // Atualizar as coordenadas dos pontos do triângulo
                painel.formas.setCoordenadas(i, 0, x1T, y1T); // Atualiza o ponto 1
                painel.formas.setCoordenadas(i, 1, x2T, y2T); // Atualiza o ponto 2
                painel.formas.setCoordenadas(i, 2, x3T, y3T); // Atualiza o ponto 3

            } else if(painel.getTipo() == TipoPrimitivo.ROTACAO) {
                FiguraRetas.desenharTriangulo(g, x1T, y1T, x2T, y2T, x3T, y3T, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                double theta = Math.toRadians(90); // Ângulo de rotação em radianos

                // Ponto de referência para a rotação
                double refX = 300;
                double refY = 300;

                // Rotaciona cada ponto do triângulo em relação ao ponto de referência
                int[] novoPonto1 = rotacionarPonto(x1T, y1T, refX, refY, theta);
                int[] novoPonto2 = rotacionarPonto(x2T, y2T, refX, refY, theta);
                int[] novoPonto3 = rotacionarPonto(x3T, y3T, refX, refY, theta);

                // Atualizar as coordenadas do triângulo
                painel.formas.setCoordenadas(i, 0, novoPonto1[0], novoPonto1[1]); // Atualiza o ponto 1
                painel.formas.setCoordenadas(i, 1, novoPonto2[0], novoPonto2[1]); // Atualiza o ponto 2
                painel.formas.setCoordenadas(i, 2, novoPonto3[0], novoPonto3[1]); // Atualiza o ponto 3

            }
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

}
