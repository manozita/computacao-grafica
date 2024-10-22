package controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransformacoesMenu {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Exemplo de Transformações");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        JButton transformButton = new JButton("Transformações");
        JPopupMenu menu = new JPopupMenu();

        JMenuItem translacao = new JMenuItem("Translação");
        JMenuItem rotacao = new JMenuItem("Rotação");
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

    // Abre a janela para inserir dados de Translação
    private static void abrirJanelaTranslacao() {
        JFrame janela = new JFrame("Translação");
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

    // Abre a janela para inserir dados de Rotação
    private static void abrirJanelaRotacao() {
        JFrame janela = new JFrame("Rotação");
        janela.setSize(300, 150);
        janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Ângulo:"));
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
