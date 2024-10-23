package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainGUI extends JFrame {

    private JPanel painelDesenho;
    private Color corAtual = Color.BLACK;
    private int espessuraAtual = 1;
    private Dimension tamanhoBotao = new Dimension(50, 50); // Tamanho fixo dos bot�es
    private JButton btnAtual = null; // Armazena o bot�o atualmente selecionado
    private JLabel lblCoord; // JLabel para exibir mensagens
    private JLabel lblEspessura; // JLabel para exibir a espessura
    private JPopupMenu popupEspessura; // Menu pop-up para redimensionar

    public MainGUI() {
        setTitle("PROGRAMA GR�FICO");
        setSize(1000, 750); // Aumentar a altura da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // Define a janela como n�o redimension�vel
        setLayout(new BorderLayout());

        // Definir as cores principais
        Color corCinzaEscuro = new Color(45, 45, 45); // Fundo cinza escuro
        Color corCinzaClaro = new Color(120, 120, 120); // Cor ao clicar
        Color corCinzaClarissimo = new Color(200, 200, 200); // Cor clara para divis�rias
        Color corCinza = new Color(80, 80, 80); // Cor ao passar o mouse
        Color corTexto = corCinzaClarissimo; // Cor do texto

        // Painel principal de ferramentas
        JPanel painel1 = new JPanel();
        painel1.setLayout(new GridLayout(1, 3, 10, 10)); // Adicionar espa�amento entre se��es
        painel1.setBackground(corCinzaEscuro);

        // Se��o IMAGEM
        JPanel painelImagem = new JPanel();
        painelImagem.setLayout(new GridBagLayout()); // Usar GridBagLayout para centralizar
        painelImagem.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(corCinzaClarissimo), "IMAGEM", 0, 0, null, corTexto));
        painelImagem.setBackground(corCinzaEscuro);
        JButton btnRedimensionar = criarBotaoCliqueUnico("icons/espessura.png", corCinzaEscuro); // Bot�es com fundo da cor da janela
        JButton btnRotacao = criarBotao("icons/rotacao.png", corCinzaEscuro);
        JButton btnTranslacao = criarBotao("icons/mover.png", corCinzaEscuro);
        JButton btnEscala = criarBotao("icons/escala.png", corCinzaEscuro);
        adicionarBotoesAoPainel(painelImagem, btnRedimensionar, btnRotacao, btnTranslacao, btnEscala);

        // Se��o FORMAS
        JPanel painelFormas = new JPanel();
        painelFormas.setLayout(new GridBagLayout()); // Usar GridBagLayout para centralizar
        painelFormas.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(corCinzaClarissimo), "FORMAS", 0, 0, null, corTexto));
        painelFormas.setBackground(corCinzaEscuro);
        JButton btnRetangulo = criarBotao("icons/retangulo.png", corCinzaEscuro);
        JButton btnCirculo = criarBotao("icons/circulo.png", corCinzaEscuro);
        JButton btnTriangulo = criarBotao("icons/triangulo.png", corCinzaEscuro);
        JButton btnPonto = criarBotao("icons/ponto.png", corCinzaEscuro);
        JButton btnLinha = criarBotao("icons/linha.png", corCinzaEscuro);
        adicionarBotoesAoPainel(painelFormas, btnRetangulo, btnCirculo, btnTriangulo, btnPonto, btnLinha);

        // Se��o FERRAMENTAS
        JPanel painelFerramentas = new JPanel();
        painelFerramentas.setLayout(new GridBagLayout()); // Usar GridBagLayout para centralizar
        painelFerramentas.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(corCinzaClarissimo), "FERRAMENTAS", 0, 0, null, corTexto));
        painelFerramentas.setBackground(corCinzaEscuro);
        JButton btnDeletar = criarBotao("icons/deletar.png", corCinzaEscuro);
        JButton btnLimpar = criarBotaoCliqueUnico("icons/limpar.png", corCinzaEscuro); // Bot�o de limpar especial
        JButton btnColorir = criarBotaoCliqueUnico("icons/colorir.png", corCinzaEscuro); // Bot�o de colorir especial
        adicionarBotoesAoPainel(painelFerramentas, btnDeletar, btnLimpar, btnColorir);

        painel1.add(painelImagem);
        painel1.add(painelFormas);
        painel1.add(painelFerramentas);

        // Painel de desenho
        JPanel painel2 = new JPanel();
        painel2.setLayout(new BorderLayout());

        painelDesenho = new JPanel();
        painelDesenho.setBackground(Color.WHITE);
        painelDesenho.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(corCinzaEscuro, 20), // Borda externa
                BorderFactory.createEmptyBorder(20, 20, 0, 20) // Borda interna
        ));

        // Criando os JLabels
        lblCoord = new JLabel("Msg:"); // Inicializa a JLabel com "Msg:"
        lblCoord.setForeground(corCinzaClarissimo); // Define a cor do texto como branco
        lblCoord.setFont(new Font("Arial", Font.PLAIN, 16)); // Define o tamanho da fonte

        lblEspessura = new JLabel(String.format("%-5s", 1)); // Inicializa a JLabel de espessura
        lblEspessura.setForeground(corCinzaClarissimo); // Define a cor do texto como branco
        lblEspessura.setFont(new Font("Arial", Font.PLAIN, 16)); // Define o tamanho da fonte

        // Pain�is individuais para Msg e Espessura
        JPanel painelCoord = new JPanel(); // Painel para mensagem
        painelCoord.setLayout(new GridBagLayout());
        painelCoord.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(corCinzaClarissimo), "Mensagem", 0, 0, null, corTexto));
        painelCoord.setBackground(corCinzaEscuro);
        painelCoord.add(lblCoord); // Adiciona a JLabel de mensagem ao painel

        JPanel painelEspessura = new JPanel(); // Painel para espessura
        painelEspessura.setLayout(new GridBagLayout());
        painelEspessura.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(corCinzaClarissimo), "Espessura", 0, 0, null, corTexto));
        painelEspessura.setBackground(corCinzaEscuro);
        painelEspessura.add(lblEspessura); // Adiciona a JLabel de espessura ao painel

        // Painel principal para juntar os dois
        JPanel painelInfos = new JPanel();
        painelInfos.setLayout(new GridLayout(1, 2, 10, 10)); // Layout com 2 colunas
        painelInfos.setBackground(corCinzaEscuro); // Fundo cinza escuro
        painelInfos.add(painelCoord); // Adiciona o painel de mensagem
        painelInfos.add(painelEspessura); // Adiciona o painel de espessura

        painel2.add(painelDesenho, BorderLayout.CENTER);
        painel2.add(painelInfos, BorderLayout.NORTH); // Adiciona o painel de mensagens na parte norte

        add(painel1, BorderLayout.NORTH);
        add(painel2, BorderLayout.CENTER);

        // A��o do bot�o de limpar
        btnLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                painelDesenho.repaint();
            }
        });

        // A��o do bot�o de redimensionar
        btnRedimensionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (popupEspessura.isShowing()) {
                    popupEspessura.setVisible(false); // Se o menu j� estiver vis�vel, esconda-o
                } else {
                    popupEspessura.show(btnRedimensionar, 0, btnRedimensionar.getHeight()); // Mostra o menu abaixo do bot�o
                }
            }
        });

        // Configura��o do menu pop-up para redimensionar
        popupEspessura = new JPopupMenu();
        String[] opcEspessura = {"1px", "3px", "5px", "8px"}; // Op��es de espessura
        for (String option : opcEspessura) {
            JMenuItem itemMenu = new JMenuItem(option);
            itemMenu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Aqui voc� pode adicionar a l�gica para mudar a espessura
                    espessuraAtual = Integer.parseInt(option.replace("px", ""));
                    lblEspessura.setText("Espessura: " + option); // Atualiza a mensagem de espessura
                }
            });
            popupEspessura.add(itemMenu);
        }
    }

    // Cria��o de bot�es quadrados com �cones
    private JButton criarBotao(String localIcone, Color backgroundColor) {
        // Carregar a imagem do �cone original (100x100 pixels ou qualquer tamanho maior)
        ImageIcon icone = new ImageIcon(getClass().getResource("/" + localIcone));

        // Redimensionar a imagem para caber no bot�o (50x50 pixels)
        Image image = icone.getImage().getScaledInstance(tamanhoBotao.width, tamanhoBotao.height, Image.SCALE_SMOOTH);

        // Criar novo ImageIcon com a imagem redimensionada
        ImageIcon scaledIcon = new ImageIcon(image);

        // Criar o bot�o quadrado com o �cone redimensionado
        JButton btnNovo = new JButton(scaledIcon);
        btnNovo.setPreferredSize(tamanhoBotao);
        btnNovo.setMaximumSize(tamanhoBotao);
        btnNovo.setFocusable(false); // Remover foco visual
        btnNovo.setContentAreaFilled(false); // Fundo transparente por padr�o
        btnNovo.setBorderPainted(false); // Sem borda ao redor
        btnNovo.setBackground(backgroundColor); // Cor do fundo igual ao fundo da janela
        btnNovo.setOpaque(true); // Tornar a cor de fundo vis�vel

        // Adicionando efeito de hover
        btnNovo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Altera a cor apenas se o bot�o n�o estiver selecionado
                if (btnAtual != btnNovo) {
                    btnNovo.setBackground(new Color(80, 80, 80)); // Cor ao passar o mouse
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Se o bot�o n�o estiver selecionado, volta � cor original do fundo
                if (btnAtual != btnNovo) {
                    btnNovo.setBackground(backgroundColor);
                }
            }

            public void mousePressed(MouseEvent e) {
                // Se j� existe um bot�o selecionado, redefine sua cor
                if (btnAtual != null) {
                    btnAtual.setBackground(backgroundColor); // Volta � cor original
                }

                btnNovo.setBackground(new Color(120, 120, 120)); // Cor ao clicar
                btnAtual = btnNovo; // Atualiza o bot�o selecionado
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Quando soltar o bot�o, mant�m a cor de clique
                // N�o muda a cor aqui, pois queremos que fique claro ap�s o clique
            }
        });

        return btnNovo;
    }

    // Cria��o de um bot�o especial para escolha de cor, limpeza e espessura
    private JButton criarBotaoCliqueUnico(String localIcone, Color corDeFundo) {
        JButton btnNovo = criarBotao(localIcone, corDeFundo); // Usa a mesma l�gica de cria��o de bot�es
        btnNovo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Se for o bot�o de limpar, limpar a �rea de desenho
                if (localIcone.equals("icons/limpar.png")) {
                    painelDesenho.repaint();
                } else if (localIcone.equals("icons/colorir.png")) {
                    // Se for o bot�o de colorir, escolher uma nova cor
                    Color corNova = JColorChooser.showDialog(null, "Escolha uma Cor", corAtual);
                    if (corNova != null) {
                        corAtual = corNova;
                    }
                }
                // O bot�o pode ser clicado v�rias vezes sem desmarc�-lo
                btnNovo.setBackground(corDeFundo); // Mant�m a cor ao clicar
                btnAtual = null;
            }
        });
        return btnNovo;
    }

    // M�todo para adicionar bot�es centralizados em um painel usando GridBagLayout
    private void adicionarBotoesAoPainel(JPanel panel, JButton... btnNovos) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        for (JButton btnNovo : btnNovos) {
            panel.add(btnNovo, gbc);
        }
    }
}
