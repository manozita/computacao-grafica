package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import controller.Select;
import controller.TipoPrimitivo;

/**
 * Classe PainelTransfs para um pequeno painel que l� a entrada do usu�rio
 * 
 */
public class PainelTransfs extends JDialog {
    // Vari�veis da classe
    private TipoPrimitivo tipoTransformacao; // Tipo de transforma��o selecionada (Rota��o, Mover, Escala)
    private TipoPrimitivo tipoFigura; // Tipo de figura selecionada
    private JTextField txtRotacaoGrau, txtRefX, txtRefY;
    private JTextField txtMoverX, txtMoverY;
    private JTextField txtEscalasX, txtEscalasY, txtEscalasRefX, txtEscalasRefY, txtEscala;
    private PainelDesenho painel; 
    private Select select;
    private int refX, refY; // Coordenadas de refer�ncia

    // Construtor do painel de transforma��es
    public PainelTransfs(PainelDesenho painel, Select select, TipoPrimitivo tipoFigura) {
        super((JFrame) null, true); // Define como modal
        this.tipoFigura = tipoFigura;
        this.tipoTransformacao = painel.getTipo();
        // Define o tipo de transforma��o caso seja escala de um c�rculo
        if (tipoTransformacao == TipoPrimitivo.ESCALA && tipoFigura == TipoPrimitivo.CIRCULO) tipoTransformacao = TipoPrimitivo.ESCALACIRC;
        
        this.painel = painel; 
        this.select = select;
        this.refX = 0;
        this.refY = 0;

        // Configura��es da janela
        setTitle("Configurar Transforma��o"); // Define o t�tulo da janela
        setSize(300, 200); // Define o tamanho da janela
        setLocationRelativeTo(painel); // Centraliza a janela em rela��o ao painel
        setLayout(new BorderLayout()); // Define o layout da janela

        // Cria��o do painel principal com GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout()); // Cria um painel com layout GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints(); // Cria um objeto para gerenciar as restri��es do layout
        gbc.insets = new Insets(2, 2, 2, 2); // Define o espa�amento entre os componentes

        // Configura��o dos campos conforme o tipo de transforma��o
        switch (tipoTransformacao) {
            case ROTACAO: // Campos para Rota��o
                gbc.gridx = 0; gbc.gridy = 0;
                panel.add(new JLabel("Insira o valor da rota��o:"), gbc); // Adiciona r�tulo para o valor da rota��o

                gbc.gridx = 1;
                txtRotacaoGrau = new JTextField(5); // Campo de texto para o valor do �ngulo de rota��o
                panel.add(txtRotacaoGrau, gbc);

                gbc.gridx = 0; gbc.gridy = 1;
                panel.add(new JLabel("Coord. x:"), gbc); // Adiciona r�tulo para a coordenada X de refer�ncia

                gbc.gridx = 1;
                txtRefX = new JTextField(5); // Campo de texto para a coordenada X de refer�ncia
                panel.add(txtRefX, gbc);

                gbc.gridx = 0; gbc.gridy = 2;
                panel.add(new JLabel("Coord. y:"), gbc); // Adiciona r�tulo para a coordenada Y de refer�ncia

                gbc.gridx = 1;
                txtRefY = new JTextField(5); // Campo de texto para a coordenada Y de refer�ncia
                panel.add(txtRefY, gbc);
                break;

            case MOVER: // Campos para Mover
                gbc.gridx = 0; gbc.gridy = 0;
                panel.add(new JLabel("Tx:"), gbc); // Adiciona r�tulo para o deslocamento em X

                gbc.gridx = 1;
                txtMoverX = new JTextField(5); // Campo de texto para o deslocamento em X
                panel.add(txtMoverX, gbc);

                gbc.gridx = 0; gbc.gridy = 1;
                panel.add(new JLabel("Ty:"), gbc); // Adiciona r�tulo para o deslocamento em Y

                gbc.gridx = 1;
                txtMoverY = new JTextField(5); // Campo de texto para o deslocamento em Y
                panel.add(txtMoverY, gbc);
                break;

            case ESCALA: // Campos para Escala
                gbc.gridx = 0; gbc.gridy = 0;
                panel.add(new JLabel("Sx:"), gbc); // Adiciona r�tulo para o fator de escala em X

                gbc.gridx = 1;
                txtEscalasX = new JTextField(5); // Campo de texto para o fator de escala em X
                panel.add(txtEscalasX, gbc);

                gbc.gridx = 0; gbc.gridy = 1;
                panel.add(new JLabel("Sy:"), gbc); // Adiciona r�tulo para o fator de escala em Y

                gbc.gridx = 1;
                txtEscalasY = new JTextField(5); // Campo de texto para o fator de escala em Y
                panel.add(txtEscalasY, gbc);

                gbc.gridx = 0; gbc.gridy = 2;
                panel.add(new JLabel("Coord. x:"), gbc); // Adiciona r�tulo para a coordenada X de refer�ncia

                gbc.gridx = 1;
                txtEscalasRefX = new JTextField(5); // Campo de texto para a coordenada X de refer�ncia
                panel.add(txtEscalasRefX, gbc);

                gbc.gridx = 0; gbc.gridy = 3;
                panel.add(new JLabel("Coord. y:"), gbc); // Adiciona r�tulo para a coordenada Y de refer�ncia

                gbc.gridx = 1;
                txtEscalasRefY = new JTextField(5); // Campo de texto para a coordenada Y de refer�ncia
                panel.add(txtEscalasRefY, gbc);
                break;

            case ESCALACIRC: // Campos para Escala �nica (c�rculo)
                gbc.gridx = 0; gbc.gridy = 0;
                panel.add(new JLabel("Fator de escala:"), gbc); // Adiciona r�tulo para o fator de escala

                gbc.gridx = 1;
                txtEscala = new JTextField(5); // Campo de texto para o fator de escala
                panel.add(txtEscala, gbc);

                gbc.gridx = 0; gbc.gridy = 1;
                panel.add(new JLabel("Coord. x:"), gbc); // Adiciona r�tulo para a coordenada X de refer�ncia

                gbc.gridx = 1;
                txtEscalasRefX = new JTextField(5); // Campo de texto para a coordenada X de refer�ncia
                panel.add(txtEscalasRefX, gbc);

                gbc.gridx = 0; gbc.gridy = 2;
                panel.add(new JLabel("Coord. y:"), gbc); // Adiciona r�tulo para a coordenada Y de refer�ncia

                gbc.gridx = 1;
                txtEscalasRefY = new JTextField(5); // Campo de texto para a coordenada Y de refer�ncia
                panel.add(txtEscalasRefY, gbc);
                break;
        }

        // Adiciona o painel principal � janela
        add(panel, BorderLayout.CENTER);

        // Bot�o para aplicar as transforma��es
        JButton btnAplicar = new JButton("Aplicar"); // Cria o bot�o "Aplicar"
        btnAplicar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validarCampos()) { // Valida os campos antes de aplicar
                    dispose(); // Fecha a janela
                    desenhar(); // Realiza o desenho com a transforma��o
                } else {
                    // Mostra mensagem de erro se os valores forem inv�lidos
                    JOptionPane.showMessageDialog(PainelTransfs.this, "Valores inv�lidos ou fora dos limites.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        // Bot�o para escolher as coordenadas clicando na tela
        JButton btnEscolherCoordenadas = new JButton("Escolher Coordenadas"); // Cria o bot�o "Escolher Coordenadas"
        btnEscolherCoordenadas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Esconde a janela atual para permitir o clique no PainelDesenho
                setVisible(false);
                painel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        refX = (int)e.getX(); // Obt�m a coordenada X do clique
                        refY = (int)e.getY(); // Obt�m a coordenada Y do clique
                        if (txtRefX != null && txtRefY != null) { // Atualiza os campos de coordenadas de refer�ncia
                            txtRefX.setText(String.valueOf(refX));
                            txtRefY.setText(String.valueOf(refY));
                        } else if (txtEscalasRefX != null && txtEscalasRefY != null) { // Atualiza os campos de escala de refer�ncia
                            txtEscalasRefX.setText(String.valueOf(refX));
                            txtEscalasRefY.setText(String.valueOf(refY));
                        }
                        // Remove o listener ap�s o clique
                        painel.removeMouseListener(this);
                        // Mostra a janela novamente
                        setVisible(true);
                    }
                });
            }
        });

        // Painel inferior com os bot�es
        JPanel southPanel = new JPanel(); // Cria o painel para os bot�es na parte inferior
        if (tipoTransformacao != TipoPrimitivo.MOVER) { // Adiciona o bot�o de coordenadas, se aplic�vel
            southPanel.add(btnEscolherCoordenadas);
        }
        southPanel.add(btnAplicar); // Adiciona o bot�o de aplicar
        add(southPanel, BorderLayout.SOUTH); // Adiciona o painel de bot�es � janela
        this.setVisible(true); // Torna a janela vis�vel
    }

    // M�todo para validar os campos de entrada
    private boolean validarCampos() {
        try {
            switch (tipoTransformacao) {
                case ROTACAO: // Valida��o para rota��o
                    int grau = Integer.parseInt(txtRotacaoGrau.getText()); // Valor do �ngulo de rota��o
                    int refX = Integer.parseInt(txtRefX.getText()); // Coordenada X de refer�ncia
                    int refY = Integer.parseInt(txtRefY.getText()); // Coordenada Y de refer�ncia
                    return (refX >= 0 && refX <= 1000) && (refY >= 0 && refY <= 600);

                case MOVER: // Valida��o para mover
                    int moverX = Integer.parseInt(txtMoverX.getText()); // Deslocamento em X
                    int moverY = Integer.parseInt(txtMoverY.getText()); // Deslocamento em Y
                    return (moverX >= -1000 && moverX <= 1000) && (moverY >= -1000 && moverY <= 1000);

                case ESCALA: // Valida��o para escalas
                    double escalaX = Double.parseDouble(txtEscalasX.getText()); // Fator de escala em X
                    double escalaY = Double.parseDouble(txtEscalasY.getText()); // Fator de escala em Y
                    int escalasRefX = Integer.parseInt(txtEscalasRefX.getText()); // Coordenada X de refer�ncia
                    int escalasRefY = Integer.parseInt(txtEscalasRefY.getText()); // Coordenada Y de refer�ncia
                    return (escalaX > 0 && escalaX <= 15) && (escalaY > 0 && escalaY <= 15) &&
                            (escalasRefX >= 0 && escalasRefX <= 1000) && (escalasRefY >= 0 && escalasRefY <= 600);
                            
                case ESCALACIRC: // Valida��o para escala �nica (c�rculo)
                    double escala = Double.parseDouble(txtEscala.getText()); // Fator de escala
                    int escalaRefX = Integer.parseInt(txtEscalasRefX.getText()); // Coordenada X de refer�ncia
                    int escalaRefY = Integer.parseInt(txtEscalasRefY.getText()); // Coordenada Y de refer�ncia
                    return (escala > 0 && escala <= 15) &&
                            (escalaRefX >= 0 && escalaRefX <= 1000) && (escalaRefY >= 0 && escalaRefY <= 600);
            }
        } catch (NumberFormatException e) {
            return false; // Retorna falso em caso de erro de formata��o
        }
        return false;
    }
    
    // M�todo para realizar o desenho com a transforma��o aplicada
    public void desenhar() {
        double[] valores;
        painel.setAcaoAtual("NENHUM"); // Define a a��o atual como nenhuma
        switch (tipoTransformacao) {
            case ROTACAO: // Rota��o
                valores = new double[3];
                valores[0] = Double.parseDouble(txtRotacaoGrau.getText()); // �ngulo de rota��o
                valores[1] = Double.parseDouble(txtRefX.getText()); // Coordenada X de refer�ncia
                valores[2] = Double.parseDouble(txtRefY.getText()); // Coordenada Y de refer�ncia
                break;
                
            case MOVER: // Mover
                valores = new double[2];
                valores[0] = Double.parseDouble(txtMoverX.getText()); // Deslocamento em X
                valores[1] = Double.parseDouble(txtMoverY.getText()); // Deslocamento em Y
                break;
                
            case ESCALA: // Escala
                valores = new double[4];
                valores[0] = Double.parseDouble(txtEscalasX.getText()); // Fator de escala em X
                valores[1] = Double.parseDouble(txtEscalasY.getText()); // Fator de escala em Y
                valores[2] = Double.parseDouble(txtEscalasRefX.getText()); // Coordenada X de refer�ncia
                valores[3] = Double.parseDouble(txtEscalasRefY.getText()); // Coordenada Y de refer�ncia
                break;
                
            default: // Escala �nica
                valores = new double[3];
                valores[0] = Double.parseDouble(txtEscala.getText()); // Fator de escala
                valores[1] = Double.parseDouble(txtEscalasRefX.getText()); // Coordenada X de refer�ncia
                valores[2] = Double.parseDouble(txtEscalasRefY.getText()); // Coordenada Y de refer�ncia
                break;
        }
        // Aplica a transforma��o conforme o tipo de figura
        switch(tipoFigura) {
            case PONTO:
                select.transformacoesPonto(tipoTransformacao, valores); // Aplica transforma��o no ponto
                break;
            case RETA:
                select.transformacoesReta(tipoTransformacao, valores); // Aplica transforma��o na reta
                break;
            case RETANGULO:
                select.transformacoesRetangulo(tipoTransformacao, valores); // Aplica transforma��o no ret�ngulo
                break;
            case CIRCULO:
                select.transformacoesCirculo(tipoTransformacao, valores); // Aplica transforma��o no c�rculo
                break;
            case TRIANGULO:
                select.transformacoesTriangulo(tipoTransformacao, valores); // Aplica transforma��o no tri�ngulo
                break;
        }
    }
}
