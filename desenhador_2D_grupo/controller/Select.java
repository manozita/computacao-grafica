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
                if(selecionarRetangulo(x, y, painel.formas.getFigura(i).getX1(), painel.formas.getFigura(i).getY1(), painel.formas.getFigura(i).getX2(), painel.formas.getFigura(i).getY2(), i))
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
                // painel.formas.setCoordenadas(i, 0, x1R*2, y1R*2);
                // painel.formas.setCoordenadas(i, 1, x2R*2, y2R*2);

                // Supondo que 'scaleFactor' seja o fator de escala
                double scaleFactor = 2; // Exemplo de fator de escala (pode ser definido como necessário)

                // Passo 1: Calcular o ponto médio
                double midX = (x1R + x2R) / 2.0;
                double midY = (y1R + y2R) / 2.0;

                // Passo 2: Deslocar p1 e p2 para a origem em relação ao ponto médio
                double translatedX1 = x1R - midX;
                double translatedY1 = y1R - midY;
                double translatedX2 = x2R - midX;
                double translatedY2 = y2R - midY;

                // Passo 3: Aplicar a escala
                translatedX1 *= scaleFactor;
                translatedY1 *= scaleFactor;
                translatedX2 *= scaleFactor;
                translatedY2 *= scaleFactor;

                // Passo 4: Deslocar de volta para o ponto médio
                x1R = (int)(midX + translatedX1);
                y1R = (int)(midY + translatedY1);
                x2R = (int)(midX + translatedX2);
                y2R = (int)(midY + translatedY2);

                // Atualizar as coordenadas dos pontos
                painel.formas.setCoordenadas(i, 0, x1R, y1R); // Atualiza p1
                painel.formas.setCoordenadas(i, 1, x2R, y2R); // Atualiza p2

            } else if(painel.getTipo() == TipoPrimitivo.ROTACAO) {
                FiguraRetas.desenharReta(g, x1R, y1R, x2R, y2R, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                double theta = Math.toRadians(90); // Ângulo de rotação em radianos
                // Passo 1: Deslocar p2 para a origem
                double translatedX = x2R - x1R;
                double translatedY = y2R - y1R;

                // Passo 2: Aplicar a rotação
                double rotatedX = translatedX * Math.cos(theta) - translatedY * Math.sin(theta);
                double rotatedY = translatedX * Math.sin(theta) + translatedY * Math.cos(theta);

                // Passo 3: Deslocar de volta
                x2R = x1R + (int)rotatedX;
                y2R = y1R + (int)rotatedY;

                // Atualizar as coordenadas do ponto final
                painel.formas.setCoordenadas(i, 1, x2R, y2R);

            }
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
            Graphics g = painel.getGraphics();
            if (painel.getTipo() == TipoPrimitivo.DELETAR) {
                FiguraRetas.desenharRetangulo(g, x1R, y1R, x2R, y2R, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                painel.formas.apagarElemento(i);
            } else if(painel.getTipo() == TipoPrimitivo.MOVER) {
                FiguraRetas.desenharRetangulo(g, x1R, y1R, x2R, y2R, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                painel.formas.setCoordenadas(i, 0, x1R+10, y1R+10);
                painel.formas.setCoordenadas(i, 1, x2R+10, y2R+10);
            } else if(painel.getTipo() == TipoPrimitivo.ESCALA) {
                FiguraRetas.desenharRetangulo(g, x1R, y1R, x2R, y2R, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                // painel.formas.setCoordenadas(i, 0, x1R*2, y1R*2);
                // painel.formas.setCoordenadas(i, 1, x2R*2, y2R*2);

                // Supondo que 'scaleFactor' seja o fator de escala
                double scaleFactor = 2; // Exemplo de fator de escala (pode ser definido como necessário)

                // Passo 1: Calcular o ponto médio
                double midX = (x1R + x2R) / 2.0;
                double midY = (y1R + y2R) / 2.0;

                // Passo 2: Deslocar p1 e p2 para a origem em relação ao ponto médio
                double translatedX1 = x1R - midX;
                double translatedY1 = y1R - midY;
                double translatedX2 = x2R - midX;
                double translatedY2 = y2R - midY;

                // Passo 3: Aplicar a escala
                translatedX1 *= scaleFactor;
                translatedY1 *= scaleFactor;
                translatedX2 *= scaleFactor;
                translatedY2 *= scaleFactor;

                // Passo 4: Deslocar de volta para o ponto médio
                x1R = (int)(midX + translatedX1);
                y1R = (int)(midY + translatedY1);
                x2R = (int)(midX + translatedX2);
                y2R = (int)(midY + translatedY2);

                // Atualizar as coordenadas dos pontos
                painel.formas.setCoordenadas(i, 0, x1R, y1R); // Atualiza p1
                painel.formas.setCoordenadas(i, 1, x2R, y2R); // Atualiza p2
            } else if(painel.getTipo() == TipoPrimitivo.ROTACAO) {

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
        if (Math.abs(distancia - raio) <= 4) { // Tolerância de 4 pixels
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

                // Supondo que 'scaleFactor' seja o fator de escala
                double scaleFactor = 2; // Exemplo de fator de escala (pode ser definido como necessário)

                // Passo 1: Calcular o vetor de deslocamento da borda em relação ao centro
                double translatedX = xB - xC;
                double translatedY = yB - yC;

                // Passo 2: Aplicar a escala
                translatedX *= scaleFactor;
                translatedY *= scaleFactor;

                // Passo 3: Deslocar de volta para o centro
                xB = (int)(xC + translatedX);
                yB = (int)(yC + translatedY);

                // Atualizar as coordenadas da borda
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
                // painel.formas.setCoordenadas(i, 0, x1T*2, y1T*2);
                // painel.formas.setCoordenadas(i, 1, x2T*2, y2T*2);
                // painel.formas.setCoordenadas(i, 2, x3T*2, y3T*2);

                // Supondo que 'scaleFactor' seja o fator de escala
                double scaleFactor = 2; // Exemplo de fator de escala (pode ser definido como necessário)

                // Passo 1: Calcular o baricentro
                double baricentroX = (x1T + x2T + x3T) / 3.0;
                double baricentroY = (y1T + y2T + y3T) / 3.0;

                // Passo 2: Deslocar os pontos para a origem em relação ao baricentro
                double translatedX1 = x1T - baricentroX;
                double translatedY1 = y1T - baricentroY;
                double translatedX2 = x2T - baricentroX;
                double translatedY2 = y2T - baricentroY;
                double translatedX3 = x3T - baricentroX;
                double translatedY3 = y3T - baricentroY;

                // Passo 3: Aplicar a escala
                translatedX1 *= scaleFactor;
                translatedY1 *= scaleFactor;
                translatedX2 *= scaleFactor;
                translatedY2 *= scaleFactor;
                translatedX3 *= scaleFactor;
                translatedY3 *= scaleFactor;

                // Passo 4: Deslocar de volta para o baricentro
                x1T = (int)(baricentroX + translatedX1);
                y1T = (int)(baricentroY + translatedY1);
                x2T = (int)(baricentroX + translatedX2);
                y2T = (int)(baricentroY + translatedY2);
                x3T = (int)(baricentroX + translatedX3);
                y3T = (int)(baricentroY + translatedY3);

                // Atualizar as coordenadas dos pontos do triângulo
                painel.formas.setCoordenadas(i, 0, x1T, y1T); // Atualiza o ponto 1
                painel.formas.setCoordenadas(i, 1, x2T, y2T); // Atualiza o ponto 2
                painel.formas.setCoordenadas(i, 2, x3T, y3T); // Atualiza o ponto 3

            } else if(painel.getTipo() == TipoPrimitivo.ROTACAO) {

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

}
