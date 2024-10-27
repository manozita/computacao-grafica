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

                // Supondo que 'fatorDeEscala' seja o fator de escala
                double fatorDeEscala = 2; // Exemplo de fator de escala (pode ser definido como necessário)

                // Passo 1: Calcular o ponto médio
                double ptMedioX = (x1R + x2R) / 2.0;
                double ptMedioY = (y1R + y2R) / 2.0;

                // Passo 2: Deslocar p1 e p2 para a origem em relação ao ponto médio
                double x1Deslocado = x1R - ptMedioX;
                double y1Deslocado = y1R - ptMedioY;
                double x2Deslocado = x2R - ptMedioX;
                double y2Deslocado = y2R - ptMedioY;

                // Passo 3: Aplicar a escala
                x1Deslocado *= fatorDeEscala;
                y1Deslocado *= fatorDeEscala;
                x2Deslocado *= fatorDeEscala;
                y2Deslocado *= fatorDeEscala;

                // Passo 4: Deslocar de volta para o ponto médio
                x1R = (int)(ptMedioX + x1Deslocado);
                y1R = (int)(ptMedioY + y1Deslocado);
                x2R = (int)(ptMedioX + x2Deslocado);
                y2R = (int)(ptMedioY + y2Deslocado);

                // Atualizar as coordenadas dos pontos
                painel.formas.setCoordenadas(i, 0, x1R, y1R); // Atualiza p1
                painel.formas.setCoordenadas(i, 1, x2R, y2R); // Atualiza p2

            } else if(painel.getTipo() == TipoPrimitivo.ROTACAO) {
                FiguraRetas.desenharReta(g, x1R, y1R, x2R, y2R, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                double theta = Math.toRadians(90); // Ângulo de rotação em radianos
                // Passo 1: Deslocar p2 para a origem
                double xDeslocado = x2R - x1R;
                double yDeslocado = y2R - y1R;

                // Passo 2: Aplicar a rotação
                double rotacaoX = xDeslocado * Math.cos(theta) - yDeslocado * Math.sin(theta);
                double rotacaoY = xDeslocado * Math.sin(theta) + yDeslocado * Math.cos(theta);

                // Passo 3: Deslocar de volta
                x2R = x1R + (int)rotacaoX;
                y2R = y1R + (int)rotacaoY;

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

                // Supondo que 'fatorDeEscala' seja o fator de escala
                double fatorDeEscala = 2; // Exemplo de fator de escala (pode ser definido como necessário)

                // Passo 1: Calcular o ponto médio
                double ptMedioX = (x1R + x2R) / 2.0;
                double ptMedioY = (y1R + y2R) / 2.0;

                // Passo 2: Deslocar p1 e p2 para a origem em relação ao ponto médio
                double x1Deslocado = x1R - ptMedioX;
                double y1Deslocado = y1R - ptMedioY;
                double x2Deslocado = x2R - ptMedioX;
                double y2Deslocado = y2R - ptMedioY;

                // Passo 3: Aplicar a escala
                x1Deslocado *= fatorDeEscala;
                y1Deslocado *= fatorDeEscala;
                x2Deslocado *= fatorDeEscala;
                y2Deslocado *= fatorDeEscala;

                // Passo 4: Deslocar de volta para o ponto médio
                x1R = (int)(ptMedioX + x1Deslocado);
                y1R = (int)(ptMedioY + y1Deslocado);
                x2R = (int)(ptMedioX + x2Deslocado);
                y2R = (int)(ptMedioY + y2Deslocado);

                // Atualizar as coordenadas dos pontos
                painel.formas.setCoordenadas(i, 0, x1R, y1R); // Atualiza p1
                painel.formas.setCoordenadas(i, 1, x2R, y2R); // Atualiza p2
            } else if (painel.getTipo() == TipoPrimitivo.ROTACAO) {
                FiguraRetas.desenharRetangulo(g, x1R, y1R, x2R, y2R, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                double theta = Math.toRadians(90); // Ângulo de rotação em radianos

                // Define o centro do retângulo
                double xCentro = (x1R + x2R) / 2.0;
                double yCentro = (y1R + y2R) / 2.0;

                // Definir os quatro vértices do retângulo usando x1R, y1R e x2R, y2R
                int x3R = x2R;
                int y3R = y1R;
                int x4R = x1R;
                int y4R = y2R;

                // Rotaciona cada ponto do retângulo
                int[] novoPonto1 = rotacionarPonto(x1R, y1R, xCentro, yCentro, theta);
                int[] novoPonto2 = rotacionarPonto(x2R, y2R, xCentro, yCentro, theta);
                int[] novoPonto3 = rotacionarPonto(x3R, y3R, xCentro, yCentro, theta);
                int[] novoPonto4 = rotacionarPonto(x4R, y4R, xCentro, yCentro, theta);

                // Atualizar as coordenadas do retângulo
                painel.formas.setCoordenadas(i, 0, novoPonto1[0], novoPonto1[1]);
                painel.formas.setCoordenadas(i, 1, novoPonto2[0], novoPonto2[1]);
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

                // Supondo que 'fatorDeEscala' seja o fator de escala
                double fatorDeEscala = 2; // Exemplo de fator de escala (pode ser definido como necessário)

                // Passo 1: Calcular o vetor de deslocamento da borda em relação ao centro
                double xDeslocado = xB - xC;
                double yDeslocado = yB - yC;

                // Passo 2: Aplicar a escala
                xDeslocado *= fatorDeEscala;
                yDeslocado *= fatorDeEscala;

                // Passo 3: Deslocar de volta para o centro
                xB = (int)(xC + xDeslocado);
                yB = (int)(yC + yDeslocado);

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

                // Supondo que 'fatorDeEscala' seja o fator de escala
                double fatorDeEscala = 2; // Exemplo de fator de escala (pode ser definido como necessário)

                // Passo 1: Calcular o baricentro/ponto medio
                double ptoMedioX = (x1T + x2T + x3T) / 3.0;
                double ptoMedioY = (y1T + y2T + y3T) / 3.0;

                // Passo 2: Deslocar os pontos para a origem em relação ao ptoMedio
                double x1Deslocado = x1T - ptoMedioX;
                double y1Deslocado = y1T - ptoMedioY;
                double x2Deslocado = x2T - ptoMedioX;
                double y2Deslocado = y2T - ptoMedioY;
                double x3Deslocado = x3T - ptoMedioX;
                double y3Deslocado = y3T - ptoMedioY;

                // Passo 3: Aplicar a escala
                x1Deslocado *= fatorDeEscala;
                y1Deslocado *= fatorDeEscala;
                x2Deslocado *= fatorDeEscala;
                y2Deslocado *= fatorDeEscala;
                x3Deslocado *= fatorDeEscala;
                y3Deslocado *= fatorDeEscala;

                // Passo 4: Deslocar de volta para o ptoMedio
                x1T = (int)(ptoMedioX + x1Deslocado);
                y1T = (int)(ptoMedioY + y1Deslocado);
                x2T = (int)(ptoMedioX + x2Deslocado);
                y2T = (int)(ptoMedioY + y2Deslocado);
                x3T = (int)(ptoMedioX + x3Deslocado);
                y3T = (int)(ptoMedioY + y3Deslocado);

                // Atualizar as coordenadas dos pontos do triângulo
                painel.formas.setCoordenadas(i, 0, x1T, y1T); // Atualiza o ponto 1
                painel.formas.setCoordenadas(i, 1, x2T, y2T); // Atualiza o ponto 2
                painel.formas.setCoordenadas(i, 2, x3T, y3T); // Atualiza o ponto 3

            } else if(painel.getTipo() == TipoPrimitivo.ROTACAO) {
                FiguraRetas.desenharTriangulo(g, x1T, y1T, x2T, y2T, x3T, y3T, "", painel.formas.getFigura(i).getEsp(), painel.getBackground());
                double theta = Math.toRadians(90); // Ângulo de rotação em radianos
            
                // Define o centro do triângulo (baricentro)
                double xCentro = (x1T + x2T + x3T) / 3.0;
                double yCentro = (y1T + y2T + y3T) / 3.0;
            
                // Rotaciona cada ponto do triângulo
                int[] novoPonto1 = rotacionarPonto(x1T, y1T, xCentro, yCentro, theta);
                int[] novoPonto2 = rotacionarPonto(x2T, y2T, xCentro, yCentro, theta);
                int[] novoPonto3 = rotacionarPonto(x3T, y3T, xCentro, yCentro, theta);
            
                // Atualizar as coordenadas do triângulo
                painel.formas.setCoordenadas(i, 0, novoPonto1[0], novoPonto1[1]);
                painel.formas.setCoordenadas(i, 1, novoPonto2[0], novoPonto2[1]);
                painel.formas.setCoordenadas(i, 2, novoPonto3[0], novoPonto3[1]);
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
    
    int[] rotacionarPonto(int x, int y, double xCentro, double yCentro, double theta) {
        // Passo 1: Deslocar para a origem
        double xDeslocado = x - xCentro;
        double yDeslocado = y - yCentro;

        // Passo 2: Aplicar rotação
        double rotacaoX = xDeslocado * Math.cos(theta) - yDeslocado * Math.sin(theta);
        double rotacaoY = xDeslocado * Math.sin(theta) + yDeslocado * Math.cos(theta);

        // Passo 3: Deslocar de volta
        int xNovo = (int)(xCentro + rotacaoX);
        int yNovo = (int)(yCentro + rotacaoY);

        return new int[] { xNovo, yNovo };
    }
}
