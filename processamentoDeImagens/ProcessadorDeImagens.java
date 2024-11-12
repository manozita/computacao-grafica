package processamentoDeImagens;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class ProcessadorDeImagens extends JFrame {

    private BufferedImage imagemOriginal;
    private BufferedImage imagemProcessada;
    private JLabel rotuloImagem;

    public ProcessadorDeImagens() {
        // Configurações da janela
        setTitle("Processamento de Imagens");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Define fundo da janela
        getContentPane().setBackground(Color.DARK_GRAY);

        // Painel para os botões de "Carregar" e "Salvar" com uma linha de separação
        JPanel painelCarregarSalvar = new JPanel();
        painelCarregarSalvar.setBackground(Color.DARK_GRAY);
        painelCarregarSalvar.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        painelCarregarSalvar.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE), "", 0, 0, null, Color.WHITE));

        // Botão para carregar imagem
        JButton botaoCarregar = new JButton("Carregar Imagem");
        configurarBotao(botaoCarregar);
        botaoCarregar.addActionListener(e -> carregarImagem());
        painelCarregarSalvar.add(botaoCarregar);

        // Botão para salvar imagem
        JButton botaoSalvar = new JButton("Salvar Imagem");
        configurarBotao(botaoSalvar);
        botaoSalvar.addActionListener(e -> salvarImagem());
        painelCarregarSalvar.add(botaoSalvar);

        // Painel para os botões de edição
        JPanel painelBotoesEdicao = new JPanel();
        painelBotoesEdicao.setBackground(Color.DARK_GRAY);
        painelBotoesEdicao.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        painelBotoesEdicao.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE), "", 0, 0, null, Color.WHITE));

        // Botões de edição
        adicionarBotaoEdicao("Criar Histograma", painelBotoesEdicao, e -> criarHistograma());
        adicionarBotaoEdicao("Converter para Tons de Cinza", painelBotoesEdicao, e -> converterParaTonsDeCinza());
        adicionarBotaoEdicao("Converter para Binário", painelBotoesEdicao, e -> converterParaBinario());
        adicionarBotaoEdicao("Filtro Passa-Baixa", painelBotoesEdicao, e -> aplicarFiltroPassaBaixa());
        adicionarBotaoEdicao("Filtro Passa-Alta", painelBotoesEdicao, e -> aplicarFiltroPassaAlta());
        adicionarBotaoEdicao("Reverter Alterações", painelBotoesEdicao, e -> reverterImagemOriginal());

        // Painel principal que organiza os dois grupos de botões
        JPanel painelSuperior = new JPanel();
        painelSuperior.setLayout(new BorderLayout());
        painelSuperior.setBackground(Color.DARK_GRAY);

        painelSuperior.add(painelBotoesEdicao, BorderLayout.CENTER);
        painelSuperior.add(painelCarregarSalvar, BorderLayout.WEST);

        // Adiciona o painel principal à interface
        painelSuperior.setPreferredSize(new Dimension(1400, 40));
        add(painelSuperior, BorderLayout.NORTH);

        // Rótulo para exibir a imagem
        rotuloImagem = new JLabel();
        rotuloImagem.setHorizontalAlignment(JLabel.CENTER);
        rotuloImagem.setBackground(Color.DARK_GRAY);
        rotuloImagem.setOpaque(true);
        JScrollPane painelRolagem = new JScrollPane(rotuloImagem);
        add(painelRolagem, BorderLayout.CENTER);

        // Configura o tamanho da janela
        setSize(1240, 800); // Largura ajustada
        setLocationRelativeTo(null); // Centraliza a janela
        setVisible(true);
    }

    /**
     * Método para adicionar botões de edição ao painel de edição.
     *
     * @param nome Nome do botão.
     * @param painel Painel onde o botão será adicionado.
     * @param acao Ação a ser executada ao clicar no botão.
     */
    private void adicionarBotaoEdicao(String nome, JPanel painel, ActionListener acao) {
        JButton botao = new JButton(nome);
        configurarBotao(botao);
        botao.addActionListener(acao);
        painel.add(botao);
    }

    /**
     * Método para configurar os botões da interface.
     *
     * @param botao Botão que será configurado.
     */
    private void configurarBotao(JButton botao) {
        botao.setBackground(Color.DARK_GRAY);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
    }

    /**
     * Método para carregar uma imagem do computador.
     */
    private void carregarImagem() {
        JFileChooser seletorArquivo = new JFileChooser();
        seletorArquivo.setDialogTitle("Selecionar Imagem");
        FileNameExtensionFilter filtro = new FileNameExtensionFilter(
                "Arquivos de Imagem", "jpg", "jpeg", "png", "bmp");
        seletorArquivo.setFileFilter(filtro);
        int resultado = seletorArquivo.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            try {
                imagemOriginal = ImageIO.read(seletorArquivo.getSelectedFile());
                imagemProcessada = imagemOriginal;
                exibirImagem(imagemProcessada);
                JOptionPane.showMessageDialog(this, "Imagem carregada com sucesso.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Erro ao carregar a imagem.");
            }
        }
    }

    /**
     * Método para exibir a imagem no rótulo.
     *
     * @param imagem Imagem a ser exibida.
     */
    private void exibirImagem(BufferedImage imagem) {
        if (imagem != null) {
            Image imagemRedimensionada = imagem.getScaledInstance(
                    rotuloImagem.getWidth(), rotuloImagem.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon icone = new ImageIcon(imagemRedimensionada);
            rotuloImagem.setIcon(icone);
        }
    }

    /**
     * Método para criar e exibir o histograma da imagem processada.
     */
    private void criarHistograma() {
        if (imagemProcessada != null) {
            int[] histograma = new int[256];
            // Calcula o histograma
            for (int y = 0; y < imagemProcessada.getHeight(); y++) {
                for (int x = 0; x < imagemProcessada.getWidth(); x++) {
                    int rgb = imagemProcessada.getRGB(x, y);
                    int cinza = (rgb >> 16) & 0xff; // Obtém o valor do canal vermelho
                    histograma[cinza]++;
                }
            }
            // Cria e exibe o painel do histograma
            PainelHistograma painelHistograma = new PainelHistograma(histograma);
            JFrame janelaHistograma = new JFrame("Histograma");
            janelaHistograma.add(painelHistograma);
            janelaHistograma.setSize(500, 400);
            janelaHistograma.setLocationRelativeTo(this);
            janelaHistograma.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Carregue uma imagem primeiro.");
        }
    }

    /**
     * Método para converter a imagem processada para tons de cinza.
     */
    private void converterParaTonsDeCinza() {
        if (imagemProcessada != null) {
            BufferedImage imagemCinza = new BufferedImage(
                    imagemProcessada.getWidth(),
                    imagemProcessada.getHeight(),
                    BufferedImage.TYPE_BYTE_GRAY);
            Graphics g = imagemCinza.getGraphics();
            g.drawImage(imagemProcessada, 0, 0, null);
            g.dispose();
            imagemProcessada = imagemCinza;
            exibirImagem(imagemProcessada);
        } else {
            JOptionPane.showMessageDialog(this, "Carregue uma imagem primeiro.");
        }
    }

    /**
     * Método para converter a imagem processada para binário (preto e branco).
     */
    private void converterParaBinario() {
        if (imagemProcessada != null) {
            BufferedImage imagemBinaria = new BufferedImage(
                    imagemProcessada.getWidth(),
                    imagemProcessada.getHeight(),
                    BufferedImage.TYPE_BYTE_BINARY);
            Graphics g = imagemBinaria.getGraphics();
            g.drawImage(imagemProcessada, 0, 0, null);
            g.dispose();
            imagemProcessada = imagemBinaria;
            exibirImagem(imagemProcessada);
        } else {
            JOptionPane.showMessageDialog(this, "Carregue uma imagem primeiro.");
        }
    }

    /**
     * Método para aplicar um filtro passa-baixa (desfoque) na imagem processada.
     */
    private void aplicarFiltroPassaBaixa() {
        if (imagemProcessada != null) {

            // Matriz Kernel que representa a vizinhança de cada pixel
            // cada valor do kernel é 1/9, para que a soma dos pesos seja igual a 1
            float[] kernel = {
                    1f / 9f, 1f / 9f, 1f / 9f,
                    1f / 9f, 1f / 9f, 1f / 9f,
                    1f / 9f, 1f / 9f, 1f / 9f
            };

            // Cada pixel será substituído pela média dos seus valores vizinhos.
    
            // ConvolveOp aplica a convolução da imagem com o kernel
            ConvolveOp op = new ConvolveOp(
                    new Kernel(3, 3, kernel),  // Cria um kernel de 3x3 com os valores definidos
                    ConvolveOp.EDGE_NO_OP,     // Define a estratégia de borda (EDGE_NO_OP = sem alteração nas bordas)
                    null                       // Não necessita de uma RenderingHints (pode ser nulo)
            );
    
            // Aplica o filtro (a convolução) à imagem processada
            imagemProcessada = op.filter(imagemProcessada, null);
    
            // Exibe a imagem suavizada na interface
            exibirImagem(imagemProcessada);
        } else {
            JOptionPane.showMessageDialog(this, "Carregue uma imagem primeiro.");
        }
    }

    /**
     * Método para aplicar um filtro passa-alta (detecção de bordas) na imagem processada.
     */
    private void aplicarFiltroPassaAlta() {
        if (imagemProcessada != null) {
            // Este kernel aplica um realce ao pixel central e subtrai a influência dos vizinhos
            // que cria um efeito que destaca as bordas e as áreas de transição de contraste
            float[] kernel = {
                    -1f, -1f, -1f,
                    -1f,  8f, -1f,
                    -1f, -1f, -1f
            };
    
            // Remove áreas suaves e destaca áreas onde há uma mudança abrupta no valor dos pixels.
    
            // ConvolveOp aplica a convolução da imagem com o kernel
            ConvolveOp op = new ConvolveOp(
                    new Kernel(3, 3, kernel),  // Cria um kernel de 3x3 com os valores definidos
                    ConvolveOp.EDGE_NO_OP,     // Define a estratégia de borda (EDGE_NO_OP = sem alteração nas bordas)
                    null                       // Não necessita de uma RenderingHints (pode ser nulo)
            );
    
            // Aplica o filtro (a convolução) à imagem processada
            imagemProcessada = op.filter(imagemProcessada, null);
    
            // Exibe a imagem com bordas destacadas na interface
            exibirImagem(imagemProcessada);
        } else {
            JOptionPane.showMessageDialog(this, "Carregue uma imagem primeiro.");
        }
    }

    /**
     * Método para reverter a imagem processada para a imagem original.
     */
    private void reverterImagemOriginal() {
        if (imagemOriginal != null) {
            imagemProcessada = imagemOriginal;
            exibirImagem(imagemProcessada);
            JOptionPane.showMessageDialog(this, "Imagem revertida para o original.");
        } else {
            JOptionPane.showMessageDialog(this, "Nenhuma imagem original disponível.");
        }
    }

    /**
     * Método para salvar a imagem processada em um arquivo.
     */
    private void salvarImagem() {
        if (imagemProcessada != null) {
            JFileChooser seletorArquivo = new JFileChooser();
            seletorArquivo.setDialogTitle("Salvar Imagem");
    
            // Configura os filtros de extensão de arquivo para PNG e JPG
            FileNameExtensionFilter filtroPNG = new FileNameExtensionFilter(
                    "PNG Imagem (*.png)", "png");
            FileNameExtensionFilter filtroJPG = new FileNameExtensionFilter(
                    "JPG Imagem (*.jpg, *.jpeg)", "jpg", "jpeg");
    
            // Adiciona ambos os filtros ao JFileChooser
            seletorArquivo.addChoosableFileFilter(filtroPNG);
            seletorArquivo.addChoosableFileFilter(filtroJPG);
    
            // Define o filtro padrão como PNG
            seletorArquivo.setFileFilter(filtroPNG);
    
            int resultado = seletorArquivo.showSaveDialog(this);
            if (resultado == JFileChooser.APPROVE_OPTION) {
                try {
                    File arquivoSaida = seletorArquivo.getSelectedFile();
                    String caminhoArquivo = arquivoSaida.getAbsolutePath().toLowerCase();
    
                    // Verifica qual filtro está selecionado e define o formato
                    String formatoArquivo; // Formato padrão
                    FileNameExtensionFilter filtroSelecionado = (FileNameExtensionFilter) seletorArquivo.getFileFilter();
                    if (filtroSelecionado == filtroJPG) {
                        formatoArquivo = "jpg";
                    } else {
                        formatoArquivo = "png";
                    }
    
                    // Adiciona a extensão correta se o usuário não a forneceu
                    if (!caminhoArquivo.endsWith("." + formatoArquivo)) {
                        arquivoSaida = new File(arquivoSaida.getAbsolutePath() + "." + formatoArquivo);
                    }
    
                    // Salva a imagem processada no formato determinado
                    ImageIO.write(imagemProcessada, formatoArquivo, arquivoSaida);
                    JOptionPane.showMessageDialog(this, "Imagem salva com sucesso.");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar a imagem.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Não há imagem para salvar.");
        }
    }
    
}