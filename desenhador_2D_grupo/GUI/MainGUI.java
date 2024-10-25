package GUI;
import controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainGUI extends JFrame {
    private TipoPrimitivo tipoAtual = TipoPrimitivo.NENHUM; // Tipo atual de primitivo como nenhum
    private Color corAtual = Color.BLACK;
    private int espessuraAtual = 3;

    private Dimension tamanhoBotao = new Dimension(50, 50); // Tamanho fixo dos botões
    private JButton btnAtual = null; // Armazena o botão atualmente selecionado
    private JLabel lblCoord = new JLabel(""); // JLabel para exibir mensagens
    private JLabel lblEspessura = new JLabel("3px"); // JLabel para exibir a espessura
    private JLabel lblTipoAtual = new JLabel("NENHUM");
    private JPopupMenu popupEspessura; // Menu pop-up para redimensionar
    
    PainelDesenho painelDesenho = new PainelDesenho(lblCoord, tipoAtual, corAtual, espessuraAtual); // Painel de desenho

    public MainGUI() {
        setTitle("PROGRAMA GRÁFICO");
        setSize(1000, 750); // Aumentar a altura da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // Define a janela como não redimensionável
        setLayout(new BorderLayout());

        // Definir as cores principais
        Color corCinzaEscuro = new Color(45, 45, 45); // Fundo cinza escuro
        Color corCinzaClaro = new Color(120, 120, 120); // Cor ao clicar
        Color corCinzaClarissimo = new Color(200, 200, 200); // Cor clara para divisórias
        Color corCinza = new Color(80, 80, 80); // Cor ao passar o mouse
        Color corTexto = corCinzaClarissimo; // Cor do texto

        // Painel principal de ferramentas
        JPanel painel1 = new JPanel();
        painel1.setLayout(new GridLayout(1, 3, 10, 10)); // Adicionar espaçamento entre seções
        painel1.setBackground(corCinzaEscuro);

        // Seção IMAGEM
        JPanel painelImagem = new JPanel();
        painelImagem.setLayout(new GridBagLayout()); // Usar GridBagLayout para centralizar
        painelImagem.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(corCinzaClarissimo), "IMAGEM", 0, 0, null, corTexto));
        painelImagem.setBackground(corCinzaEscuro);
        JButton btnEspessura = criarBotao("icons/espessura.png", corCinzaEscuro); // Botões com fundo da cor da janela
        JButton btnRotacao = criarBotao("icons/rotacao.png", corCinzaEscuro);
        JButton btnTranslacao = criarBotao("icons/mover.png", corCinzaEscuro);
        JButton btnEscala = criarBotao("icons/escala.png", corCinzaEscuro);
        adicionarBotoesAoPainel(painelImagem, btnEspessura, btnRotacao, btnTranslacao, btnEscala);

        // Seção FORMAS
        JPanel painelFormas = new JPanel();
        painelFormas.setLayout(new GridBagLayout()); // Usar GridBagLayout para centralizar
        painelFormas.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(corCinzaClarissimo), "FORMAS", 0, 0, null, corTexto));
        painelFormas.setBackground(corCinzaEscuro);
        JButton btnRetangulo = criarBotao("icons/retangulo.png", corCinzaEscuro);
        JButton btnCirculo = criarBotao("icons/circulo.png", corCinzaEscuro);
        JButton btnTriangulo = criarBotao("icons/triangulo.png", corCinzaEscuro);
        JButton btnPonto = criarBotao("icons/ponto.png", corCinzaEscuro);
        JButton btnReta = criarBotao("icons/linha.png", corCinzaEscuro);
        adicionarBotoesAoPainel(painelFormas, btnRetangulo, btnCirculo, btnTriangulo, btnPonto, btnReta);

        // Seção FERRAMENTAS
        JPanel painelFerramentas = new JPanel();
        painelFerramentas.setLayout(new GridBagLayout()); // Usar GridBagLayout para centralizar
        painelFerramentas.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(corCinzaClarissimo), "FERRAMENTAS", 0, 0, null, corTexto));
        painelFerramentas.setBackground(corCinzaEscuro);
        JButton btnDeletar = criarBotao("icons/deletar.png", corCinzaEscuro);
        JButton btnLimpar = criarBotao("icons/limpar.png", corCinzaEscuro); // Botão de limpar especial
        JButton btnColorir = criarBotao("icons/colorir.png", corCinzaEscuro); // Botão de colorir especial
        adicionarBotoesAoPainel(painelFerramentas, btnDeletar, btnLimpar, btnColorir);

        painel1.add(painelImagem);
        painel1.add(painelFormas);
        painel1.add(painelFerramentas);

        // Painel de desenho
        JPanel painel2 = new JPanel();
        painel2.setBackground(Color.WHITE);
        painel2.setLayout(new BorderLayout());
    
        painelDesenho.setBackground(Color.WHITE);

        // Criando os JLabels
        lblCoord.setForeground(corCinzaClarissimo); // Define a cor do texto como branco
        lblCoord.setFont(new Font("Arial", Font.PLAIN, 16)); // Define o tamanho da fonte

        lblEspessura.setForeground(corCinzaClarissimo); // Define a cor do texto como branco
        lblEspessura.setFont(new Font("Arial", Font.PLAIN, 16)); // Define o tamanho da fonte
        
        lblTipoAtual.setForeground(corCinzaClarissimo); // Define a cor do texto como branco
        lblTipoAtual.setFont(new Font("Arial", Font.PLAIN, 16)); // Define o tamanho da fonte

        // Painéis individuais para Coordenadas, Espessura e TipoAtual
        JPanel painelCoord = new JPanel(); // Painel para mensagem
        painelCoord.setLayout(new GridBagLayout());
        painelCoord.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(corCinzaClarissimo), "COORDENADAS", 0, 0, null, corTexto));
        painelCoord.setBackground(corCinzaEscuro);
        painelCoord.add(lblCoord); // Adiciona a JLabel de mensagem ao painel

        JPanel painelEspessura = new JPanel(); // Painel para espessura
        painelEspessura.setLayout(new GridBagLayout());
        painelEspessura.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(corCinzaClarissimo), "ESPESSURA", 0, 0, null, corTexto));
        painelEspessura.setBackground(corCinzaEscuro);
        painelEspessura.add(lblEspessura); // Adiciona a JLabel de espessura ao painel
        
        JPanel painelTipoPrimitivo = new JPanel(); // Painel para mensagem
        painelTipoPrimitivo.setLayout(new GridBagLayout());
        painelTipoPrimitivo.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(corCinzaClarissimo), "TIPO ATUAL", 0, 0, null, corTexto));
        painelTipoPrimitivo.setBackground(corCinzaEscuro);
        painelTipoPrimitivo.add(lblTipoAtual); // Adiciona a JLabel de mensagem ao painel

        // Painel principal para juntar as informacoes
        JPanel painelInfos = new JPanel();
        painelInfos.setLayout(new GridLayout(1, 3, 10, 10)); // Layout com 3 colunas
        painelInfos.setBackground(corCinzaEscuro); // Fundo cinza escuro
        painelInfos.add(painelCoord); // Adiciona o painel de mensagem
        painelInfos.add(painelTipoPrimitivo); // Adiciona o painel de espessura
        painelInfos.add(painelEspessura); // Adiciona o painel de espessura

        painel2.add(painelDesenho, BorderLayout.CENTER);
        painel2.add(painelInfos, BorderLayout.NORTH); // Adiciona o painel de mensagens na parte norte

        add(painel1, BorderLayout.NORTH);
        add(painel2, BorderLayout.CENTER);

        // Ação do botão de limpar
        btnLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                painelDesenho.restaurarPainel();
            }
        });
        // Ação do botão de redimensionar
        btnEspessura.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (popupEspessura.isShowing()) {
                    popupEspessura.setVisible(false); // Se o menu já estiver visível, esconda-o
                } else {
                    popupEspessura.show(btnEspessura, 0, btnEspessura.getHeight()); // Mostra o menu abaixo do botão
                }
            }
        });
        btnPonto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblTipoAtual.setText("PONTO");
                tipoAtual = TipoPrimitivo.PONTO;
                painelDesenho.setTipo(tipoAtual);
            }
        });
        btnReta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblTipoAtual.setText("RETA");
                tipoAtual = TipoPrimitivo.RETA;
                painelDesenho.setTipo(tipoAtual);
            }
        });
        btnCirculo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblTipoAtual.setText("CIRCULO");
                tipoAtual = TipoPrimitivo.CIRCULO;
                painelDesenho.setTipo(tipoAtual);                
            }
        });
        btnRetangulo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblTipoAtual.setText("RETANGULO");
                tipoAtual = TipoPrimitivo.RETANGULO;
                painelDesenho.setTipo(tipoAtual);
            }
        });
        btnTriangulo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblTipoAtual.setText("TRIANGULO");
                tipoAtual = TipoPrimitivo.TRIANGULO;
                painelDesenho.setTipo(tipoAtual);
            }
        });
        btnColorir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color corNova = JColorChooser.showDialog(null, "Escolha uma Cor", corAtual);
                if (corNova != null) {
                    corAtual = corNova;
                }
                painelDesenho.setCorAtual(corAtual); // cor atual
            }
        });
        btnDeletar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblTipoAtual.setText("DELETAR FIGURA");
                tipoAtual = TipoPrimitivo.DELETAR;
                painelDesenho.setTipo(tipoAtual);
            }
        });
        btnTranslacao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblTipoAtual.setText("MOVER FIGURA");
                tipoAtual = TipoPrimitivo.MOVER;
                painelDesenho.setTipo(tipoAtual);
            }
        });
        btnEscala.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblTipoAtual.setText("ALTERAR ESCALA");
                tipoAtual = TipoPrimitivo.ESCALA;
                painelDesenho.setTipo(tipoAtual);
            }
        });
        btnRotacao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblTipoAtual.setText("ROTAÇÃO DA FIGURA");
                tipoAtual = TipoPrimitivo.ROTACAO;
                painelDesenho.setTipo(tipoAtual);
            }
        });
        // Configuração do menu pop-up para redimensionar
        popupEspessura = new JPopupMenu();
        String[] opcEspessura = {"3px", "5px", "8px", "15px", "20px"}; // Opções de espessura
        for (String option : opcEspessura) {
            JMenuItem itemMenu = new JMenuItem(option);
            itemMenu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Aqui você pode adicionar a lógica para mudar a espessura
                    espessuraAtual = Integer.parseInt(option.replace("px", ""));
                    painelDesenho.setEsp(espessuraAtual);
                    lblEspessura.setText(option); // Atualiza a mensagem de espessura
                }
            });
            popupEspessura.add(itemMenu);
        }
    }

    // Criação de botões quadrados com ícones
    private JButton criarBotao(String localIcone, Color corDeFundo) {
        // Carregar a imagem do ícone original (100x100 pixels ou qualquer tamanho maior)
        ImageIcon icone = new ImageIcon(getClass().getResource("/" + localIcone));

        // Redimensionar a imagem para caber no botão (50x50 pixels)
        Image image = icone.getImage().getScaledInstance(tamanhoBotao.width, tamanhoBotao.height, Image.SCALE_SMOOTH);

        // Criar novo ImageIcon com a imagem redimensionada
        ImageIcon scaledIcon = new ImageIcon(image);

        // Criar o botão quadrado com o ícone redimensionado
        JButton btnNovo = new JButton(scaledIcon);
        btnNovo.setPreferredSize(tamanhoBotao);
        btnNovo.setMaximumSize(tamanhoBotao);
        btnNovo.setFocusable(false); // Remover foco visual
        btnNovo.setContentAreaFilled(false); // Fundo transparente por padrão
        btnNovo.setBorderPainted(false); // Sem borda ao redor
        btnNovo.setBackground(corDeFundo); // Cor do fundo igual ao fundo da janela
        btnNovo.setOpaque(true); // Tornar a cor de fundo visível

        // Adicionando efeito de hover
        btnNovo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Altera a cor apenas se o botão não estiver selecionado
                if (btnAtual != btnNovo) {
                    btnNovo.setBackground(new Color(80, 80, 80)); // Cor ao passar o mouse
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Se o botão não estiver selecionado, volta à cor original do fundo
                if (btnAtual != btnNovo) {
                    btnNovo.setBackground(corDeFundo);
                }
            }

            public void mousePressed(MouseEvent e) {
                // Se já existe um botão selecionado, redefine sua cor
                if (btnAtual != null) {
                    btnAtual.setBackground(corDeFundo); // Volta à cor original
                }
                if (localIcone == "icons/colorir.png" || localIcone == "icons/limpar.png" || localIcone == "icons/espessura.png") {
                    btnNovo.setBackground(corDeFundo); // Mantém a cor ao clicar
                    btnAtual = null;
                } else {
                    btnNovo.setBackground(new Color(120, 120, 120)); // Cor ao clicar
                    btnAtual = btnNovo; // Atualiza o botão selecionado
                }
            }
        });
        return btnNovo;
    }

    // Método para adicionar botões centralizados em um painel usando GridBagLayout
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
