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
 * Classe PainelTransfs para um pequeno painel que lê a entrada do usuário
 * 
 */
public class PainelTransfs extends JDialog {
    // Variáveis da classe
    private TipoPrimitivo tipoTransformacao; // Tipo de transformação selecionada (Rotação, Mover, Escala)
    private TipoPrimitivo tipoFigura; // Tipo de figura selecionada
    private JTextField txtRotacaoGrau, txtRefX, txtRefY;
    private JTextField txtMoverX, txtMoverY;
    private JTextField txtEscalasX, txtEscalasY, txtEscalasRefX, txtEscalasRefY, txtEscala;
    private PainelDesenho painel; 
    private Select select;
    private int refX, refY; // Coordenadas de referência

    // Construtor do painel de transformações
    public PainelTransfs(PainelDesenho painel, Select select, TipoPrimitivo tipoFigura) {
        super((JFrame) null, true); // Define como modal
        this.tipoFigura = tipoFigura;
        this.tipoTransformacao = painel.getTipo();
        // Define o tipo de transformação caso seja escala de um círculo
        if (tipoTransformacao == TipoPrimitivo.ESCALA && tipoFigura == TipoPrimitivo.CIRCULO) tipoTransformacao = TipoPrimitivo.ESCALACIRC;
        
        this.painel = painel; 
        this.select = select;
        this.refX = 0;
        this.refY = 0;

        // Configurações da janela
        setTitle("Configurar Transformação"); // Define o título da janela
        setSize(300, 200); // Define o tamanho da janela
        setLocationRelativeTo(painel); // Centraliza a janela em relação ao painel
        setLayout(new BorderLayout()); // Define o layout da janela

        // Criação do painel principal com GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout()); // Cria um painel com layout GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints(); // Cria um objeto para gerenciar as restrições do layout
        gbc.insets = new Insets(2, 2, 2, 2); // Define o espaçamento entre os componentes

        // Configuração dos campos conforme o tipo de transformação
        switch (tipoTransformacao) {
            case ROTACAO: // Campos para Rotação
                gbc.gridx = 0; gbc.gridy = 0;
                panel.add(new JLabel("Insira o valor da rotação:"), gbc); // Adiciona rótulo para o valor da rotação

                gbc.gridx = 1;
                txtRotacaoGrau = new JTextField(5); // Campo de texto para o valor do ângulo de rotação
                panel.add(txtRotacaoGrau, gbc);

                gbc.gridx = 0; gbc.gridy = 1;
                panel.add(new JLabel("Coord. x:"), gbc); // Adiciona rótulo para a coordenada X de referência

                gbc.gridx = 1;
                txtRefX = new JTextField(5); // Campo de texto para a coordenada X de referência
                panel.add(txtRefX, gbc);

                gbc.gridx = 0; gbc.gridy = 2;
                panel.add(new JLabel("Coord. y:"), gbc); // Adiciona rótulo para a coordenada Y de referência

                gbc.gridx = 1;
                txtRefY = new JTextField(5); // Campo de texto para a coordenada Y de referência
                panel.add(txtRefY, gbc);
                break;

            case MOVER: // Campos para Mover
                gbc.gridx = 0; gbc.gridy = 0;
                panel.add(new JLabel("Tx:"), gbc); // Adiciona rótulo para o deslocamento em X

                gbc.gridx = 1;
                txtMoverX = new JTextField(5); // Campo de texto para o deslocamento em X
                panel.add(txtMoverX, gbc);

                gbc.gridx = 0; gbc.gridy = 1;
                panel.add(new JLabel("Ty:"), gbc); // Adiciona rótulo para o deslocamento em Y

                gbc.gridx = 1;
                txtMoverY = new JTextField(5); // Campo de texto para o deslocamento em Y
                panel.add(txtMoverY, gbc);
                break;

            case ESCALA: // Campos para Escala
                gbc.gridx = 0; gbc.gridy = 0;
                panel.add(new JLabel("Sx:"), gbc); // Adiciona rótulo para o fator de escala em X

                gbc.gridx = 1;
                txtEscalasX = new JTextField(5); // Campo de texto para o fator de escala em X
                panel.add(txtEscalasX, gbc);

                gbc.gridx = 0; gbc.gridy = 1;
                panel.add(new JLabel("Sy:"), gbc); // Adiciona rótulo para o fator de escala em Y

                gbc.gridx = 1;
                txtEscalasY = new JTextField(5); // Campo de texto para o fator de escala em Y
                panel.add(txtEscalasY, gbc);

                gbc.gridx = 0; gbc.gridy = 2;
                panel.add(new JLabel("Coord. x:"), gbc); // Adiciona rótulo para a coordenada X de referência

                gbc.gridx = 1;
                txtEscalasRefX = new JTextField(5); // Campo de texto para a coordenada X de referência
                panel.add(txtEscalasRefX, gbc);

                gbc.gridx = 0; gbc.gridy = 3;
                panel.add(new JLabel("Coord. y:"), gbc); // Adiciona rótulo para a coordenada Y de referência

                gbc.gridx = 1;
                txtEscalasRefY = new JTextField(5); // Campo de texto para a coordenada Y de referência
                panel.add(txtEscalasRefY, gbc);
                break;

            case ESCALACIRC: // Campos para Escala única (círculo)
                gbc.gridx = 0; gbc.gridy = 0;
                panel.add(new JLabel("Fator de escala:"), gbc); // Adiciona rótulo para o fator de escala

                gbc.gridx = 1;
                txtEscala = new JTextField(5); // Campo de texto para o fator de escala
                panel.add(txtEscala, gbc);

                gbc.gridx = 0; gbc.gridy = 1;
                panel.add(new JLabel("Coord. x:"), gbc); // Adiciona rótulo para a coordenada X de referência

                gbc.gridx = 1;
                txtEscalasRefX = new JTextField(5); // Campo de texto para a coordenada X de referência
                panel.add(txtEscalasRefX, gbc);

                gbc.gridx = 0; gbc.gridy = 2;
                panel.add(new JLabel("Coord. y:"), gbc); // Adiciona rótulo para a coordenada Y de referência

                gbc.gridx = 1;
                txtEscalasRefY = new JTextField(5); // Campo de texto para a coordenada Y de referência
                panel.add(txtEscalasRefY, gbc);
                break;
        }

        // Adiciona o painel principal à janela
        add(panel, BorderLayout.CENTER);

        // Botão para aplicar as transformações
        JButton btnAplicar = new JButton("Aplicar"); // Cria o botão "Aplicar"
        btnAplicar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validarCampos()) { // Valida os campos antes de aplicar
                    dispose(); // Fecha a janela
                    desenhar(); // Realiza o desenho com a transformação
                } else {
                    // Mostra mensagem de erro se os valores forem inválidos
                    JOptionPane.showMessageDialog(PainelTransfs.this, "Valores inválidos ou fora dos limites.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        // Botão para escolher as coordenadas clicando na tela
        JButton btnEscolherCoordenadas = new JButton("Escolher Coordenadas"); // Cria o botão "Escolher Coordenadas"
        btnEscolherCoordenadas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Esconde a janela atual para permitir o clique no PainelDesenho
                setVisible(false);
                painel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        refX = (int)e.getX(); // Obtém a coordenada X do clique
                        refY = (int)e.getY(); // Obtém a coordenada Y do clique
                        if (txtRefX != null && txtRefY != null) { // Atualiza os campos de coordenadas de referência
                            txtRefX.setText(String.valueOf(refX));
                            txtRefY.setText(String.valueOf(refY));
                        } else if (txtEscalasRefX != null && txtEscalasRefY != null) { // Atualiza os campos de escala de referência
                            txtEscalasRefX.setText(String.valueOf(refX));
                            txtEscalasRefY.setText(String.valueOf(refY));
                        }
                        // Remove o listener após o clique
                        painel.removeMouseListener(this);
                        // Mostra a janela novamente
                        setVisible(true);
                    }
                });
            }
        });

        // Painel inferior com os botões
        JPanel southPanel = new JPanel(); // Cria o painel para os botões na parte inferior
        if (tipoTransformacao != TipoPrimitivo.MOVER) { // Adiciona o botão de coordenadas, se aplicável
            southPanel.add(btnEscolherCoordenadas);
        }
        southPanel.add(btnAplicar); // Adiciona o botão de aplicar
        add(southPanel, BorderLayout.SOUTH); // Adiciona o painel de botões à janela
        this.setVisible(true); // Torna a janela visível
    }

    // Método para validar os campos de entrada
    private boolean validarCampos() {
        try {
            switch (tipoTransformacao) {
                case ROTACAO: // Validação para rotação
                    int grau = Integer.parseInt(txtRotacaoGrau.getText()); // Valor do ângulo de rotação
                    int refX = Integer.parseInt(txtRefX.getText()); // Coordenada X de referência
                    int refY = Integer.parseInt(txtRefY.getText()); // Coordenada Y de referência
                    return (refX >= 0 && refX <= 1000) && (refY >= 0 && refY <= 600);

                case MOVER: // Validação para mover
                    int moverX = Integer.parseInt(txtMoverX.getText()); // Deslocamento em X
                    int moverY = Integer.parseInt(txtMoverY.getText()); // Deslocamento em Y
                    return (moverX >= -1000 && moverX <= 1000) && (moverY >= -1000 && moverY <= 1000);

                case ESCALA: // Validação para escalas
                    double escalaX = Double.parseDouble(txtEscalasX.getText()); // Fator de escala em X
                    double escalaY = Double.parseDouble(txtEscalasY.getText()); // Fator de escala em Y
                    int escalasRefX = Integer.parseInt(txtEscalasRefX.getText()); // Coordenada X de referência
                    int escalasRefY = Integer.parseInt(txtEscalasRefY.getText()); // Coordenada Y de referência
                    return (escalaX > 0 && escalaX <= 15) && (escalaY > 0 && escalaY <= 15) &&
                            (escalasRefX >= 0 && escalasRefX <= 1000) && (escalasRefY >= 0 && escalasRefY <= 600);
                            
                case ESCALACIRC: // Validação para escala única (círculo)
                    double escala = Double.parseDouble(txtEscala.getText()); // Fator de escala
                    int escalaRefX = Integer.parseInt(txtEscalasRefX.getText()); // Coordenada X de referência
                    int escalaRefY = Integer.parseInt(txtEscalasRefY.getText()); // Coordenada Y de referência
                    return (escala > 0 && escala <= 15) &&
                            (escalaRefX >= 0 && escalaRefX <= 1000) && (escalaRefY >= 0 && escalaRefY <= 600);
            }
        } catch (NumberFormatException e) {
            return false; // Retorna falso em caso de erro de formatação
        }
        return false;
    }
    
    // Método para realizar o desenho com a transformação aplicada
    public void desenhar() {
        double[] valores;
        painel.setAcaoAtual("NENHUM"); // Define a ação atual como nenhuma
        switch (tipoTransformacao) {
            case ROTACAO: // Rotação
                valores = new double[3];
                valores[0] = Double.parseDouble(txtRotacaoGrau.getText()); // Ângulo de rotação
                valores[1] = Double.parseDouble(txtRefX.getText()); // Coordenada X de referência
                valores[2] = Double.parseDouble(txtRefY.getText()); // Coordenada Y de referência
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
                valores[2] = Double.parseDouble(txtEscalasRefX.getText()); // Coordenada X de referência
                valores[3] = Double.parseDouble(txtEscalasRefY.getText()); // Coordenada Y de referência
                break;
                
            default: // Escala única
                valores = new double[3];
                valores[0] = Double.parseDouble(txtEscala.getText()); // Fator de escala
                valores[1] = Double.parseDouble(txtEscalasRefX.getText()); // Coordenada X de referência
                valores[2] = Double.parseDouble(txtEscalasRefY.getText()); // Coordenada Y de referência
                break;
        }
        // Aplica a transformação conforme o tipo de figura
        switch(tipoFigura) {
            case PONTO:
                select.transformacoesPonto(tipoTransformacao, valores); // Aplica transformação no ponto
                break;
            case RETA:
                select.transformacoesReta(tipoTransformacao, valores); // Aplica transformação na reta
                break;
            case RETANGULO:
                select.transformacoesRetangulo(tipoTransformacao, valores); // Aplica transformação no retângulo
                break;
            case CIRCULO:
                select.transformacoesCirculo(tipoTransformacao, valores); // Aplica transformação no círculo
                break;
            case TRIANGULO:
                select.transformacoesTriangulo(tipoTransformacao, valores); // Aplica transformação no triângulo
                break;
        }
    }
}
