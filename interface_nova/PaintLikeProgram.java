import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PaintLikeProgram extends JFrame {

    private JPanel drawingPanel;
    private Color currentColor = Color.BLACK;
    private int currentThickness = 1;
    private Dimension buttonSize = new Dimension(50, 50); // Tamanho fixo dos botões
    private JButton selectedButton = null; // Armazena o botão atualmente selecionado
    private JLabel messageLabel; // JLabel para exibir mensagens
    private JLabel thicknessLabel; // JLabel para exibir a espessura
    private JPopupMenu resizeMenu; // Menu pop-up para redimensionar

    public PaintLikeProgram() {
        setTitle("PROGRAMA GRÁFICO");
        setSize(1000, 750); // Aumentar a altura da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // Define a janela como não redimensionável
        setLayout(new BorderLayout());

        // Definir as cores principais
        Color darkGray = new Color(45, 45, 45); // Fundo cinza escuro
        Color lightGray = new Color(200, 200, 200); // Cor clara para divisórias
        Color textColor = lightGray; // Cor do texto
        Color hoverColor = new Color(80, 80, 80); // Cor ao passar o mouse
        Color clickedColor = new Color(120, 120, 120); // Cor ao clicar

        // Painel principal de ferramentas
        JPanel toolPanel = new JPanel();
        toolPanel.setLayout(new GridLayout(1, 3, 10, 10)); // Adicionar espaçamento entre seções
        toolPanel.setBackground(darkGray);

        // Seção IMAGEM
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new GridBagLayout()); // Usar GridBagLayout para centralizar
        imagePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(lightGray), "IMAGEM", 0, 0, null, textColor));
        imagePanel.setBackground(darkGray);
        JButton resizeButton = criarBotaoCliqueUnico("icons/espessura.png", darkGray); // Botões com fundo da cor da janela
        JButton rotateButton = createButton("icons/rotacao.png", darkGray);
        JButton translateButton = createButton("icons/mover.png", darkGray);
        JButton scaleButton = createButton("icons/escala.png", darkGray);
        addButtonsToPanel(imagePanel, resizeButton, rotateButton, translateButton, scaleButton);

        // Seção FORMAS
        JPanel shapePanel = new JPanel();
        shapePanel.setLayout(new GridBagLayout()); // Usar GridBagLayout para centralizar
        shapePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(lightGray), "FORMAS", 0, 0, null, textColor));
        shapePanel.setBackground(darkGray);
        JButton rectButton = createButton("icons/retangulo.png", darkGray);
        JButton circleButton = createButton("icons/circulo.png", darkGray);
        JButton triangleButton = createButton("icons/triangulo.png", darkGray);
        JButton pointButton = createButton("icons/ponto.png", darkGray);
        JButton lineButton = createButton("icons/linha.png", darkGray);
        addButtonsToPanel(shapePanel, rectButton, circleButton, triangleButton, pointButton, lineButton);

        // Seção FERRAMENTAS
        JPanel toolSubPanel = new JPanel();
        toolSubPanel.setLayout(new GridBagLayout()); // Usar GridBagLayout para centralizar
        toolSubPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(lightGray), "FERRAMENTAS", 0, 0, null, textColor));
        toolSubPanel.setBackground(darkGray);
        JButton deleteButton = createButton("icons/deletar.png", darkGray);
        JButton clearButton = criarBotaoCliqueUnico("icons/limpar.png", darkGray); // Botão de limpar especial
        JButton colorButton = criarBotaoCliqueUnico("icons/colorir.png", darkGray); // Botão de colorir especial
        addButtonsToPanel(toolSubPanel, deleteButton, clearButton, colorButton);

        toolPanel.add(imagePanel);
        toolPanel.add(shapePanel);
        toolPanel.add(toolSubPanel);

        // Painel de desenho
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        drawingPanel = new JPanel();
        drawingPanel.setBackground(Color.WHITE);
        drawingPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(darkGray, 20), // Borda externa
                BorderFactory.createEmptyBorder(20, 20, 0, 20) // Borda interna
        ));

        // Criando os JLabels
        messageLabel = new JLabel("Msg:"); // Inicializa a JLabel com "Msg:"
        messageLabel.setForeground(lightGray); // Define a cor do texto como branco
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // Define o tamanho da fonte

        thicknessLabel = new JLabel("Espessura: " + currentThickness + "px"); // Inicializa a JLabel de espessura
        thicknessLabel.setForeground(lightGray); // Define a cor do texto como branco
        thicknessLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // Define o tamanho da fonte

        // Painéis individuais para Msg e Espessura
        JPanel msgPanel = new JPanel(); // Painel para mensagem
        msgPanel.setLayout(new GridBagLayout());
        msgPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(lightGray), "Mensagem", 0, 0, null, textColor));
        msgPanel.setBackground(darkGray);
        msgPanel.add(messageLabel); // Adiciona a JLabel de mensagem ao painel

        JPanel espessuraPanel = new JPanel(); // Painel para espessura
        espessuraPanel.setLayout(new GridBagLayout());
        espessuraPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(lightGray), "Espessura", 0, 0, null, textColor));
        espessuraPanel.setBackground(darkGray);
        espessuraPanel.add(thicknessLabel); // Adiciona a JLabel de espessura ao painel

        // Painel principal para juntar os dois
        JPanel messageAndThicknessPanel = new JPanel();
        messageAndThicknessPanel.setLayout(new GridLayout(1, 2, 10, 10)); // Layout com 2 colunas
        messageAndThicknessPanel.setBackground(darkGray); // Fundo cinza escuro
        messageAndThicknessPanel.add(msgPanel); // Adiciona o painel de mensagem
        messageAndThicknessPanel.add(espessuraPanel); // Adiciona o painel de espessura

        mainPanel.add(drawingPanel, BorderLayout.CENTER);
        mainPanel.add(messageAndThicknessPanel, BorderLayout.NORTH); // Adiciona o painel de mensagens na parte norte

        add(toolPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        // Ação do botão de limpar
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.repaint();
            }
        });

        // Ação do botão de redimensionar
        resizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (resizeMenu.isShowing()) {
                    resizeMenu.setVisible(false); // Se o menu já estiver visível, esconda-o
                } else {
                    resizeMenu.show(resizeButton, 0, resizeButton.getHeight()); // Mostra o menu abaixo do botão
                }
            }
        });

        // Configuração do menu pop-up para redimensionar
        resizeMenu = new JPopupMenu();
        String[] sizeOptions = {"1px", "3px", "5px", "8px"}; // Opções de espessura
        for (String option : sizeOptions) {
            JMenuItem menuItem = new JMenuItem(option);
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Aqui você pode adicionar a lógica para mudar a espessura
                    currentThickness = Integer.parseInt(option.replace("px", ""));
                    thicknessLabel.setText("Espessura: " + option); // Atualiza a mensagem de espessura
                }
            });
            resizeMenu.add(menuItem);
        }
    }

    // Criação de botões quadrados com ícones
    private JButton createButton(String iconPath, Color backgroundColor) {
        // Carregar a imagem do ícone original (100x100 pixels ou qualquer tamanho maior)
        ImageIcon icon = new ImageIcon(getClass().getResource("/" + iconPath));

        // Redimensionar a imagem para caber no botão (50x50 pixels)
        Image image = icon.getImage().getScaledInstance(buttonSize.width, buttonSize.height, Image.SCALE_SMOOTH);

        // Criar novo ImageIcon com a imagem redimensionada
        ImageIcon scaledIcon = new ImageIcon(image);

        // Criar o botão quadrado com o ícone redimensionado
        JButton button = new JButton(scaledIcon);
        button.setPreferredSize(buttonSize);
        button.setMaximumSize(buttonSize);
        button.setFocusable(false); // Remover foco visual
        button.setContentAreaFilled(false); // Fundo transparente por padrão
        button.setBorderPainted(false); // Sem borda ao redor
        button.setBackground(backgroundColor); // Cor do fundo igual ao fundo da janela
        button.setOpaque(true); // Tornar a cor de fundo visível

        // Adicionando efeito de hover
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Altera a cor apenas se o botão não estiver selecionado
                if (selectedButton != button) {
                    button.setBackground(new Color(80, 80, 80)); // Cor ao passar o mouse
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Se o botão não estiver selecionado, volta à cor original do fundo
                if (selectedButton != button) {
                    button.setBackground(backgroundColor);
                }
            }

            public void mousePressed(MouseEvent e) {
                // Se já existe um botão selecionado, redefine sua cor
                if (selectedButton != null) {
                    selectedButton.setBackground(backgroundColor); // Volta à cor original
                }

                button.setBackground(new Color(120, 120, 120)); // Cor ao clicar
                selectedButton = button; // Atualiza o botão selecionado
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Quando soltar o botão, mantém a cor de clique
                // Não muda a cor aqui, pois queremos que fique claro após o clique
            }
        });

        return button;
    }

    // Criação de um botão especial para escolha de cor e limpeza
    private JButton criarBotaoCliqueUnico(String iconPath, Color backgroundColor) {
        JButton colorButton = createButton(iconPath, backgroundColor); // Usa a mesma lógica de criação de botões
        colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Se for o botão de limpar, limpar a área de desenho
                if (iconPath.equals("icons/limpar.png")) {
                    drawingPanel.repaint();
                } else if (iconPath.equals("icons/colorir.png")) {
                    // Se for o botão de colorir, escolher uma nova cor
                    Color chosenColor = JColorChooser.showDialog(null, "Escolha uma Cor", currentColor);
                    if (chosenColor != null) {
                        currentColor = chosenColor;
                    }
                }
                // O botão pode ser clicado várias vezes sem desmarcá-lo
                colorButton.setBackground(backgroundColor); // Mantém a cor ao clicar
                selectedButton = null;
            }
        });
        return colorButton;
    }

    // Método para adicionar botões centralizados em um painel usando GridBagLayout
    private void addButtonsToPanel(JPanel panel, JButton... buttons) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        for (JButton button : buttons) {
            panel.add(button, gbc);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PaintLikeProgram().setVisible(true);
            }
        });
    }
}
