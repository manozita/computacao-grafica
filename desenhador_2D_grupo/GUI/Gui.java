package GUI;
import controller.*;
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
public class Gui extends JFrame {
    private TipoPrimitivo tipoAtual = TipoPrimitivo.NENHUM; // Tipo atual de primitivo como nenhum
    private Color corAtual = Color.BLACK;                   // Cor atual como preto
    private int espAtual = 1;                               // Espessura atual do primitivo como 1

    // COMPONENTES DO GUI
    private JToolBar barraComandos = new JToolBar();                                     // Barra de menu (inserir componente)
    private JLabel msg = new JLabel("Msg: ");                                            // Mensagens
    private PainelDesenho areaDesenho = new PainelDesenho(msg, tipoAtual, corAtual, 10); // Painel de desenho
    // Bot?es
    private JButton jbPonto = new JButton("Ponto");
    private JButton jbReta = new JButton("Reta");
    private JButton jbCirculo = new JButton("C?rculo");
    private JButton jbRetangulo = new JButton("Ret?ngulo");
    private JButton jbTriangulo = new JButton("Tri?ngulo");
    private JButton jbSelecionar = new JButton("Selecionar");
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
        // DEFINI??ES DA JANELA
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
        barraComandos.add(jbSelecionar);
        barraComandos.add(jbLimpar);    // Bot?o de Limpar
        barraComandos.add(jbCor);       // Bot?o de Cores

        barraComandos.add(jlEsp);       // Label para espessura
        barraComandos.add(jsEsp);       // Slider para espa?amento
        areaDesenho.setEsp(espAtual);   // Define a espessura inicial
        barraComandos.add(jbSair);      // Botao para sair

        // Adiciona os componentes com os respectivos layouts
        add(barraComandos, BorderLayout.NORTH);                
        add(areaDesenho, BorderLayout.CENTER);                
        add(msg, BorderLayout.SOUTH);

        // TRATAMENTO DE EVENTOS PARA CADA COMPONENTE
        jbPonto.addActionListener(e -> {     // Bot?o de ponto
                    tipoAtual = TipoPrimitivo.PONTO;
                    areaDesenho.setTipo(tipoAtual);
            });        
        jbReta.addActionListener(e -> {      // Bot?o de reta
                    tipoAtual = TipoPrimitivo.RETA;
                    areaDesenho.setTipo(tipoAtual);
            });        
        jbCirculo.addActionListener(e -> {   // Bot?o de c?rculo
                    tipoAtual = TipoPrimitivo.CIRCULO;
                    areaDesenho.setTipo(tipoAtual);
            });        
        jbRetangulo.addActionListener(e -> { // Bot?o de ret?ngulo
                    tipoAtual = TipoPrimitivo.RETANGULO;
                    areaDesenho.setTipo(tipoAtual);
            }); 
        jbTriangulo.addActionListener(e -> { // Bot?o de tri?ngulo
                    tipoAtual = TipoPrimitivo.TRIANGULO;
                    areaDesenho.setTipo(tipoAtual);
            });
        jbSelecionar.addActionListener(e -> { // Bot?o de sele??o
                    tipoAtual = TipoPrimitivo.NENHUM;
                    areaDesenho.setTipo(tipoAtual);
            });
        jbLimpar.addActionListener(e -> {    // Bot?o de limpar
                    //jsEsp.setValue(1); // Inicia slider (necessario para limpar ultimo primitivo da tela) 
                    //repaint();
                    areaDesenho.restaurarPainel();
            });
        jbCor.addActionListener(e -> {       // Bot?o de escolha de cor
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
        jbSair.addActionListener(e -> {     // Bot?o de sair
                    System.exit(0);
            });

    }
}