package GUI;
import controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Classe MainGUI para o GUI principal
 * 
 */
public class MainGUI extends JFrame {
    private TipoPrimitivo tipoAtual = TipoPrimitivo.NENHUM; // Tipo atual de primitivo inicializado como nenhum
    private Color corAtual = Color.BLACK; // Cor padrão inicial
    private int espessuraAtual = 3; // Espessura padrão inicial

    private Dimension tamanhoBotao = new Dimension(50, 50); // Tamanho fixo dos botões
    private JButton btnAtual = null; // Armazena o botão atualmente selecionado
    private JLabel lblCoord = new JLabel(""); // JLabel para exibir coordenadas do mouse
    private JLabel lblEspessura = new JLabel("3px"); // JLabel para exibir a espessura atual
    private JLabel lblTipoAtual = new JLabel("NENHUM"); // JLabel para exibir o tipo atual de primitivo
    private JPopupMenu popupEspessura; // Menu pop-up para selecionar espessura
    
    PainelDesenho painelDesenho = new PainelDesenho(lblCoord, lblTipoAtual, tipoAtual, corAtual, espessuraAtual); // Painel de desenho

    public MainGUI() {
        setTitle("PROGRAMA GRÁFICO"); // Título da janela principal
        setSize(1000, 750); // Definir tamanho da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Definir operação de fechamento da janela
        setResizable(false); // Definir a janela como não redimensionável
        setLayout(new BorderLayout()); // Layout da janela

        // Definir as cores principais
        Color corCinzaEscuro = new Color(45, 45, 45); // Cor de fundo cinza escuro
        Color corCinzaClaro = new Color(120, 120, 120); // Cor ao clicar
        Color corCinzaClarissimo = new Color(200, 200, 200); // Cor clara para divisórias
        Color corCinza = new Color(80, 80, 80); // Cor ao passar o mouse
        Color corTexto = corCinzaClarissimo; // Cor do texto

        // Painel principal de ferramentas
        JPanel painel1 = new JPanel();
        painel1.setLayout(new GridLayout(1, 3, 10, 10)); // Adicionar espaçamento entre seções
        painel1.setBackground(corCinzaEscuro); // Definir cor de fundo do painel de ferramentas

        // Seção IMAGEM
        JPanel painelImagem = new JPanel();
        painelImagem.setLayout(new GridBagLayout()); // Usar GridBagLayout para centralizar botões
        painelImagem.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(corCinzaClarissimo), "IMAGEM", 0, 0, null, corTexto)); // Borda para seção
        painelImagem.setBackground(corCinzaEscuro); // Fundo da seção IMAGEM
        JButton btnEspessura = criarBotao("icons/espessura.png", corCinzaEscuro); // Botão para selecionar espessura
        JButton btnRotacao = criarBotao("icons/rotacao.png", corCinzaEscuro); // Botão para rotação
        JButton btnTranslacao = criarBotao("icons/mover.png", corCinzaEscuro); // Botão para translação
        JButton btnEscala = criarBotao("icons/escala.png", corCinzaEscuro); // Botão para escala
        adicionarBotoesAoPainel(painelImagem, btnEspessura, btnRotacao, btnTranslacao, btnEscala); // Adicionar botões ao painel

        // Seção FORMAS
        JPanel painelFormas = new JPanel();
        painelFormas.setLayout(new GridBagLayout()); // Usar GridBagLayout para centralizar botões
        painelFormas.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(corCinzaClarissimo), "FORMAS", 0, 0, null, corTexto)); // Borda para seção
        painelFormas.setBackground(corCinzaEscuro); // Fundo da seção FORMAS
        JButton btnRetangulo = criarBotao("icons/retangulo.png", corCinzaEscuro); // Botão para desenhar retângulo
        JButton btnCirculo = criarBotao("icons/circulo.png", corCinzaEscuro); // Botão para desenhar círculo
        JButton btnTriangulo = criarBotao("icons/triangulo.png", corCinzaEscuro); // Botão para desenhar triângulo
        JButton btnPonto = criarBotao("icons/ponto.png", corCinzaEscuro); // Botão para desenhar ponto
        JButton btnReta = criarBotao("icons/linha.png", corCinzaEscuro); // Botão para desenhar reta
        adicionarBotoesAoPainel(painelFormas, btnRetangulo, btnCirculo, btnTriangulo, btnPonto, btnReta); // Adicionar botões ao painel

        // Seção FERRAMENTAS
        JPanel painelFerramentas = new JPanel();
        painelFerramentas.setLayout(new GridBagLayout()); // Usar GridBagLayout para centralizar botões
        painelFerramentas.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(corCinzaClarissimo), "FERRAMENTAS", 0, 0, null, corTexto)); // Borda para seção
        painelFerramentas.setBackground(corCinzaEscuro); // Fundo da seção FERRAMENTAS
        JButton btnDeletar = criarBotao("icons/deletar.png", corCinzaEscuro); // Botão para deletar figura
        JButton btnLimpar = criarBotao("icons/limpar.png", corCinzaEscuro); // Botão para limpar o painel
        JButton btnColorir = criarBotao("icons/colorir.png", corCinzaEscuro); // Botão para escolher cor
        adicionarBotoesAoPainel(painelFerramentas, btnDeletar, btnLimpar, btnColorir); // Adicionar botões ao painel

        painel1.add(painelImagem); // Adicionar seção IMAGEM ao painel principal
        painel1.add(painelFormas); // Adicionar seção FORMAS ao painel principal
        painel1.add(painelFerramentas); // Adicionar seção FERRAMENTAS ao painel principal

        // Painel de desenho
        JPanel painel2 = new JPanel();
        painel2.setBackground(Color.WHITE); // Fundo branco para painel de desenho
        painel2.setLayout(new BorderLayout()); // Layout do painel de desenho
    
        painelDesenho.setBackground(Color.WHITE); // Fundo branco para o painel de desenho real

        // Criando os JLabels
        lblCoord.setForeground(corCinzaClarissimo); // Define a cor do texto da coordenada
        lblCoord.setFont(new Font("Arial", Font.PLAIN, 16)); // Define a fonte da coordenada

        lblEspessura.setForeground(corCinzaClarissimo); // Define a cor do texto da espessura
        lblEspessura.setFont(new Font("Arial", Font.PLAIN, 16)); // Define a fonte da espessura
        
        lblTipoAtual.setForeground(corCinzaClarissimo); // Define a cor do texto do tipo atual
        lblTipoAtual.setFont(new Font("Arial", Font.PLAIN, 16)); // Define a fonte do tipo atual

        // Painéis individuais para saída, espessura e tipo atual
        JPanel painelCoord = new JPanel(); // Painel para exibir coordenadas do mouse
        painelCoord.setLayout(new GridBagLayout());
        painelCoord.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(corCinzaClarissimo), "COORDENADAS", 0, 0, null, corTexto)); // Título "saida"
        painelCoord.setBackground(corCinzaEscuro); // Cor de fundo
        painelCoord.add(lblCoord); // Adicionar JLabel ao painel de coordenadas

        JPanel painelEspessura = new JPanel(); // Painel para exibir espessura
        painelEspessura.setLayout(new GridBagLayout());
        painelEspessura.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(corCinzaClarissimo), "ESPESSURA", 0, 0, null, corTexto)); // Título "ESPESSURA"
        painelEspessura.setBackground(corCinzaEscuro); // Cor de fundo
        painelEspessura.add(lblEspessura); // Adicionar JLabel ao painel de espessura
        
        JPanel painelTipoPrimitivo = new JPanel(); // Painel para exibir tipo atual
        painelTipoPrimitivo.setLayout(new GridBagLayout());
        painelTipoPrimitivo.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(corCinzaClarissimo), "TIPO ATUAL", 0, 0, null, corTexto)); // Título "TIPO ATUAL"
        painelTipoPrimitivo.setBackground(corCinzaEscuro); // Cor de fundo
        painelTipoPrimitivo.add(lblTipoAtual); // Adicionar JLabel ao painel de tipo atual

        // Painel principal para juntar as informações
        JPanel painelInfos = new JPanel();
        painelInfos.setLayout(new GridLayout(1, 3, 10, 10)); // Layout com 3 colunas para as informações
        painelInfos.setBackground(corCinzaEscuro); // Fundo cinza escuro
        painelInfos.add(painelCoord); // Adicionar o painel de coordenadas
        painelInfos.add(painelTipoPrimitivo); // Adicionar o painel de tipo atual
        painelInfos.add(painelEspessura); // Adicionar o painel de espessura

        painel2.add(painelDesenho, BorderLayout.CENTER); // Adicionar painel de desenho ao centro
        painel2.add(painelInfos, BorderLayout.NORTH); // Adicionar painel de informações na parte superior

        add(painel1, BorderLayout.NORTH); // Adicionar painel de ferramentas ao topo
        add(painel2, BorderLayout.CENTER); // Adicionar painel de desenho ao centro

        // Ação do botão de limpar
        btnLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                painelDesenho.restaurarPainel(); // Limpa todas as figuras do painel de desenho
            }
        });
        // Ação do botão de selecionar espessura
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
        // Ação dos botões de desenho para definir o tipo atual
        btnPonto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblTipoAtual.setText("PONTO"); // Atualiza o JLabel com o tipo atual
                tipoAtual = TipoPrimitivo.PONTO;
                painelDesenho.setTipo(tipoAtual); // Define o tipo no painel de desenho
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
        // Ação do botão de colorir
        btnColorir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color corNova = JColorChooser.showDialog(null, "Escolha uma Cor", corAtual); // Abre diálogo para escolher uma nova cor
                if (corNova != null) {
                    corAtual = corNova; // Atualiza a cor atual
                }
                painelDesenho.setCorAtual(corAtual); // Define a cor no painel de desenho
            }
        });
        // Ação do botão de deletar
        btnDeletar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblTipoAtual.setText("DELETAR FIGURA");
                tipoAtual = TipoPrimitivo.DELETAR;
                painelDesenho.setTipo(tipoAtual);
            }
        });
        // Ação do botão de translação
        btnTranslacao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblTipoAtual.setText("MOVER FIGURA");
                tipoAtual = TipoPrimitivo.MOVER;
                painelDesenho.setTipo(tipoAtual);
            }
        });
        // Ação do botão de escala
        btnEscala.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblTipoAtual.setText("ALTERAR ESCALA");
                tipoAtual = TipoPrimitivo.ESCALA;
                painelDesenho.setTipo(tipoAtual);
            }
        });
        // Ação do botão de rotação
        btnRotacao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblTipoAtual.setText("ROTAÇÃO DA FIGURA");
                tipoAtual = TipoPrimitivo.ROTACAO;
                painelDesenho.setTipo(tipoAtual);
            }
        });
        // Configuração do menu pop-up para redimensionar
        popupEspessura = new JPopupMenu(); // Criação do menu pop-up para opções de espessura
        String[] opcEspessura = {"3px", "5px", "8px", "15px", "20px"}; // Opções de espessura disponíveis
        for (String option : opcEspessura) {
            JMenuItem itemMenu = new JMenuItem(option); // Cria item do menu para cada opção de espessura
            itemMenu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Atualiza a espessura e a exibição no painel
                    espessuraAtual = Integer.parseInt(option.replace("px", "")); // Converte o valor de espessura para inteiro
                    painelDesenho.setEsp(espessuraAtual); // Atualiza a espessura no painel de desenho
                    lblEspessura.setText(option); // Atualiza o JLabel de espessura
                }
            });
            popupEspessura.add(itemMenu); // Adiciona item ao menu pop-up
        }
    }

    // Criação de botões quadrados com ícones
    private JButton criarBotao(String localIcone, Color corDeFundo) {
        // Carregar a imagem do ícone original
        ImageIcon icone = new ImageIcon(getClass().getResource("/" + localIcone)); // Carrega o ícone do arquivo

        // Redimensionar a imagem para caber no botão (50x50 pixels)
        Image image = icone.getImage().getScaledInstance(tamanhoBotao.width, tamanhoBotao.height, Image.SCALE_SMOOTH); // Redimensiona a imagem

        // Criar novo ImageIcon com a imagem redimensionada
        ImageIcon scaledIcon = new ImageIcon(image); // Cria ImageIcon a partir da imagem redimensionada

        // Criar o botão quadrado com o ícone redimensionado
        JButton btnNovo = new JButton(scaledIcon);
        btnNovo.setPreferredSize(tamanhoBotao); // Define o tamanho preferencial do botão
        btnNovo.setMaximumSize(tamanhoBotao); // Define o tamanho máximo do botão
        btnNovo.setFocusable(false); // Remover foco visual
        btnNovo.setContentAreaFilled(false); // Fundo transparente por padrão
        btnNovo.setBorderPainted(false); // Sem borda ao redor
        btnNovo.setBackground(corDeFundo); // Define a cor de fundo
        btnNovo.setOpaque(true); // Tornar a cor de fundo visível

        // Adicionando efeito de hover
        btnNovo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Altera a cor ao passar o mouse se o botão não estiver selecionado
                if (btnAtual != btnNovo) {
                    btnNovo.setBackground(new Color(80, 80, 80)); // Cor ao passar o mouse
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Volta à cor original se o botão não estiver selecionado
                if (btnAtual != btnNovo) {
                    btnNovo.setBackground(corDeFundo);
                }
            }
            // Para botões de reclique
            public void mousePressed(MouseEvent e) {
                // Se já existe um botão selecionado, redefine sua cor
                if (btnAtual != null) {
                    btnAtual.setBackground(corDeFundo); // Volta à cor original
                }
                // Manter cor especial para determinados botões
                if (localIcone == "icons/colorir.png" || localIcone == "icons/limpar.png" || localIcone == "icons/espessura.png"
                ||  localIcone == "icons/mover.png" || localIcone == "icons/escala.png" || localIcone == "icons/rotacao.png") {
                    btnNovo.setBackground(corDeFundo); // Mantém a cor ao clicar
                    btnAtual = null; // Nenhum botão atualmente selecionado
                } else {
                    btnNovo.setBackground(new Color(120, 120, 120)); // Cor ao clicar
                    btnAtual = btnNovo; // Atualiza o botão selecionado
                }
            }
        });
        return btnNovo; // Retorna o botão criado
    }

    // Método para adicionar botões centralizados em um painel usando GridBagLayout
    private void adicionarBotoesAoPainel(JPanel panel, JButton... btnNovos) {
        GridBagConstraints gbc = new GridBagConstraints(); // Configurações para posicionamento
        gbc.gridx = GridBagConstraints.RELATIVE; // Posicionamento relativo no eixo X
        gbc.gridy = GridBagConstraints.RELATIVE; // Posicionamento relativo no eixo Y
        gbc.insets = new Insets(5, 5, 5, 5); // Espaçamento entre botões
        gbc.anchor = GridBagConstraints.CENTER; // Centralizar os botões
        for (JButton btnNovo : btnNovos) {
            panel.add(btnNovo, gbc); // Adiciona cada botão ao painel com as configurações definidas
        }
    }

}
