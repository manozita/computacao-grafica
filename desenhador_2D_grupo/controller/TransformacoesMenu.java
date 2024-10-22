package controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransformacoesMenu {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Exemplo de Transforma��es");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        JButton transformButton = new JButton("Transforma��es");
        JPopupMenu menu = new JPopupMenu();

        JMenuItem translacao = new JMenuItem("Transla��o");
        JMenuItem rotacao = new JMenuItem("Rota��o");
        JMenuItem escala = new JMenuItem("Escala");

        menu.add(translacao);
        menu.add(rotacao);
        menu.add(escala);

        translacao.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    abrirJanelaTranslacao();
                }
            });

        rotacao.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    abrirJanelaRotacao();
                }
            });

        escala.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    abrirJanelaEscala();
                }
            });

        transformButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    menu.show(transformButton, transformButton.getWidth() / 2, transformButton.getHeight());
                }
            });

        frame.getContentPane().add(transformButton);
        frame.setVisible(true);
    }

    // Abre a janela para inserir dados de Transla��o
    private static void abrirJanelaTranslacao() {
        JFrame janela = new JFrame("Transla��o");
        janela.setSize(300, 150);
        janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Dx:"));
        JTextField dxField = new JTextField(5);
        panel.add(dxField);

        panel.add(new JLabel("Dy:"));
        JTextField dyField = new JTextField(5);
        panel.add(dyField);

        JButton confirmar = new JButton("Confirmar");
        panel.add(confirmar);

        janela.add(panel);
        janela.setVisible(true);
    }

    // Abre a janela para inserir dados de Rota��o
    private static void abrirJanelaRotacao() {
        JFrame janela = new JFrame("Rota��o");
        janela.setSize(300, 150);
        janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.add(new JLabel("�ngulo:"));
        JTextField anguloField = new JTextField(5);
        panel.add(anguloField);

        JButton confirmar = new JButton("Confirmar");
        panel.add(confirmar);

        janela.add(panel);
        janela.setVisible(true);
    }

    // Abre a janela para inserir dados de Escala
    private static void abrirJanelaEscala() {
        JFrame janela = new JFrame("Escala");
        janela.setSize(300, 150);
        janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Sx:"));
        JTextField sxField = new JTextField(5);
        panel.add(sxField);

        panel.add(new JLabel("Sy:"));
        JTextField syField = new JTextField(5);
        panel.add(syField);

        JButton confirmar = new JButton("Confirmar");
        panel.add(confirmar);

        janela.add(panel);
        janela.setVisible(true);
    }
}
