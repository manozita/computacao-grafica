package aplicacao;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JToolBar;

@SuppressWarnings("serial")
/**
 * Cria a interface com o usuario (GUI)
 * 
 * @author Julio Arakaki 
 * @version 20220815
 */
class Gui extends JFrame {
    private TipoPrimitivo tipoAtual = TipoPrimitivo.NENHUM; // Tipo atual de primitivo como nenhum
    private Color corAtual = Color.BLACK;                   // Cor atual como preto
    private int espAtual = 1;                               // Espessura atual do primitivo como 1

    // COMPONENTES DO GUI
    private JToolBar barraComandos = new JToolBar();                                     // Barra de menu (inserir componente)
    private JLabel msg = new JLabel("Msg: ");                                            // Mensagens
    private PainelDesenho areaDesenho = new PainelDesenho(msg, tipoAtual, corAtual, 10); // Painel de desenho
    // Botões
    private JButton jbPonto = new JButton("Ponto");
    private JButton jbReta = new JButton("Reta");
    private JButton jbCirculo = new JButton("Círculo");
    private JButton jbRetangulo = new JButton("Retângulo");
    private JButton jbTriangulo = new JButton("Triângulo");
    private JButton jbLimpar = new JButton("Limpar");
    private JButton jbCor = new JButton("Cor");
    private JButton jbSair = new JButton("Sair");

    // Entrada (slider) para definir espessura dos primitivos
    private JLabel jlEsp = new JLabel("   Espessura: " + String.format("%-5s", 1)); // Nome para o slider
    private JSlider jsEsp = new JSlider(1, 50, 1);

    /**
    * Constroi a GUI
    *
    * @param larg largura da janela
    * @param alt altura da janela
    */
    public Gui(int larg, int alt) {
        // DEFINIÇÕES DA JANELA
        super("TESTE DE PRIMITIVOS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(larg, alt);
        setVisible(true);
        setResizable(false);

        // COMPONENETES
        barraComandos.add(jbPonto);
        barraComandos.add(jbReta);
        barraComandos.add(jbCirculo);
        barraComandos.add(jbRetangulo);
        barraComandos.add(jbTriangulo);
        barraComandos.add(jbLimpar);    // Botão de Limpar
        barraComandos.add(jbCor);       // Botão de Cores

        barraComandos.add(jlEsp);       // Label para espessura
        barraComandos.add(jsEsp);       // Slider para espaçamento
        areaDesenho.setEsp(espAtual);   // Define a espessura inicial
        barraComandos.add(jbSair);      // Botao para sair

        // Adiciona os componentes com os respectivos layouts
        add(barraComandos, BorderLayout.NORTH);                
        add(areaDesenho, BorderLayout.CENTER);                
        add(msg, BorderLayout.SOUTH);

        // TRATAMENTO DE EVENTOS PARA CADA COMPONENTE
        jbPonto.addActionListener(e -> {     // Botão de ponto
            tipoAtual = TipoPrimitivo.PONTO;
            areaDesenho.setTipo(tipoAtual);
        });        
        jbReta.addActionListener(e -> {      // Botão de reta
            tipoAtual = TipoPrimitivo.RETA;
            areaDesenho.setTipo(tipoAtual);
        });        
        jbCirculo.addActionListener(e -> {   // Botão de círculo
            tipoAtual = TipoPrimitivo.CIRCULO;
            areaDesenho.setTipo(tipoAtual);
        });        
        jbRetangulo.addActionListener(e -> { // Botão de retângulo
            tipoAtual = TipoPrimitivo.RETANGULO;
            areaDesenho.setTipo(tipoAtual);
        }); 
        jbTriangulo.addActionListener(e -> { // Botão de triângulo
            tipoAtual = TipoPrimitivo.TRIANGULO;
            areaDesenho.setTipo(tipoAtual);
        }); 
        jbLimpar.addActionListener(e -> {    // Botão de limpar
            areaDesenho.removeAll();
            jsEsp.setValue(1); // Inicia slider (necessario para limpar ultimo primitivoda tela) 
            repaint();
        });
        jbCor.addActionListener(e -> {       // Botão de escolha de cor
            Color novaCor = JColorChooser.showDialog(null, "Escolha uma cor", msg.getForeground()); 
            if (novaCor != null){ 
                corAtual = novaCor; // Pega do chooserColor 
            }
            areaDesenho.setCorAtual(corAtual); // cor atual
        });  
        jsEsp.addChangeListener(e -> {      // Slider de espessura
            espAtual = jsEsp.getValue();
            jlEsp.setText("   Espessura: " + String.format("%-5s", espAtual));
            areaDesenho.setEsp(espAtual);        
        });

        jbSair.addActionListener(e -> {     // Botão de sair
            System.exit(0);
        });        
    }
}
