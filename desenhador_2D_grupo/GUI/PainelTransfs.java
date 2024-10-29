package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PainelTransfs extends JDialog {
    private int tipoTransformacao; // 1 = Rotação, 2 = Mover, 3 = Escala
    private JTextField txtRotacaoGrau, txtRefX, txtRefY;
    private JTextField txtMoverX, txtMoverY;
    private JTextField txtEscalasX, txtEscalasY, txtEscalasRefX, txtEscalasRefY, txtEscala;
    private PainelDesenho painel;
    private double refX, refY;
    private boolean aplicarPressed = false;

    public PainelTransfs(PainelDesenho painel, int tipoTransformacao) {
        super((JFrame) null, true); // Define como modal
        this.tipoTransformacao = tipoTransformacao;
        this.painel = painel;
        this.refX = 0;
        this.refY = 0;

        setTitle("Configurar Transformação");
        setSize(250, 200);
        setLocationRelativeTo(painel);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);

        switch (tipoTransformacao) {
            case 1: // Rotação
                gbc.gridx = 0; gbc.gridy = 0;
                panel.add(new JLabel("Insira o valor da rotação:"), gbc);

                gbc.gridx = 1;
                txtRotacaoGrau = new JTextField(5);
                panel.add(txtRotacaoGrau, gbc);

                gbc.gridx = 0; gbc.gridy = 1;
                panel.add(new JLabel("Coord. x:"), gbc);

                gbc.gridx = 1;
                txtRefX = new JTextField(5);
                panel.add(txtRefX, gbc);

                gbc.gridx = 0; gbc.gridy = 2;
                panel.add(new JLabel("Coord. y:"), gbc);

                gbc.gridx = 1;
                txtRefY = new JTextField(5);
                panel.add(txtRefY, gbc);
                break;

            case 2: // Mover
                gbc.gridx = 0; gbc.gridy = 0;
                panel.add(new JLabel("Tx:"), gbc);

                gbc.gridx = 1;
                txtMoverX = new JTextField(5);
                panel.add(txtMoverX, gbc);

                gbc.gridx = 0; gbc.gridy = 1;
                panel.add(new JLabel("Ty:"), gbc);

                gbc.gridx = 1;
                txtMoverY = new JTextField(5);
                panel.add(txtMoverY, gbc);
                break;

            case 3: // Escala
                gbc.gridx = 0; gbc.gridy = 0;
                panel.add(new JLabel("Sx:"), gbc);

                gbc.gridx = 1;
                txtEscalasX = new JTextField(5);
                panel.add(txtEscalasX, gbc);

                gbc.gridx = 0; gbc.gridy = 1;
                panel.add(new JLabel("Sy:"), gbc);

                gbc.gridx = 1;
                txtEscalasY = new JTextField(5);
                panel.add(txtEscalasY, gbc);

                gbc.gridx = 0; gbc.gridy = 2;
                panel.add(new JLabel("Coord. x:"), gbc);

                gbc.gridx = 1;
                txtEscalasRefX = new JTextField(5);
                panel.add(txtEscalasRefX, gbc);

                gbc.gridx = 0; gbc.gridy = 3;
                panel.add(new JLabel("Coord. y:"), gbc);

                gbc.gridx = 1;
                txtEscalasRefY = new JTextField(5);
                panel.add(txtEscalasRefY, gbc);
                break;

            case 4: // Escala única
                gbc.gridx = 0; gbc.gridy = 0;
                panel.add(new JLabel("Fator de escala:"), gbc);

                gbc.gridx = 1;
                txtEscala = new JTextField(5);
                panel.add(txtEscala, gbc);

                gbc.gridx = 0; gbc.gridy = 1;
                panel.add(new JLabel("Coord. x:"), gbc);

                gbc.gridx = 1;
                txtEscalasRefX = new JTextField(5);
                panel.add(txtEscalasRefX, gbc);

                gbc.gridx = 0; gbc.gridy = 2;
                panel.add(new JLabel("Coord. y:"), gbc);

                gbc.gridx = 1;
                txtEscalasRefY = new JTextField(5);
                panel.add(txtEscalasRefY, gbc);
                break;
        }

        add(panel, BorderLayout.CENTER);

        JButton btnAplicar = new JButton("Aplicar");
        btnAplicar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validarCampos()) {
                    aplicarPressed = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(PainelTransfs.this, "Valores inválidos ou fora dos limites.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton btnEscolherCoordenadas = new JButton("Escolher Coordenadas");
        btnEscolherCoordenadas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Esconder a janela atual para permitir o clique no PainelDesenho
                setVisible(false);
                painel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        refX = e.getX();
                        refY = e.getY();
                        if (txtRefX != null && txtRefY != null) {
                            txtRefX.setText(String.valueOf(refX));
                            txtRefY.setText(String.valueOf(refY));
                        } else if (txtEscalasRefX != null && txtEscalasRefY != null) {
                            txtEscalasRefX.setText(String.valueOf(refX));
                            txtEscalasRefY.setText(String.valueOf(refY));
                        }
                        // Remover o listener após o clique
                        painel.removeMouseListener(this);
                        // Mostrar a janela novamente
                        setVisible(true);
                    }
                });
            }
        });

        JPanel southPanel = new JPanel();
        southPanel.add(btnEscolherCoordenadas);
        southPanel.add(btnAplicar);
        add(southPanel, BorderLayout.SOUTH);
    }

    private boolean validarCampos() {
        try {
            switch (tipoTransformacao) {
                case 1: // Rotação
                    int grau = Integer.parseInt(txtRotacaoGrau.getText());
                    int refX = Integer.parseInt(txtRefX.getText());
                    int refY = Integer.parseInt(txtRefY.getText());
                    return (grau >= -360 && grau <= 360) && (refX >= 0 && refX <= 1000) && (refY >= 0 && refY <= 600);

                case 2: // Mover
                    int moverX = Integer.parseInt(txtMoverX.getText());
                    int moverY = Integer.parseInt(txtMoverY.getText());
                    return (moverX >= -1000 && moverX <= 1000) && (moverY >= -1000 && moverY <= 1000);

                case 3: // Escalas
                    double escalaX = Double.parseDouble(txtEscalasX.getText());
                    double escalaY = Double.parseDouble(txtEscalasY.getText());
                    int escalasRefX = Integer.parseInt(txtEscalasRefX.getText());
                    int escalasRefY = Integer.parseInt(txtEscalasRefY.getText());
                    return (escalaX > 0 && escalaX <= 15) && (escalaY > 0 && escalaY <= 15) &&
                            (escalasRefX >= 0 && escalasRefX <= 1000) && (escalasRefY >= 0 && escalasRefY <= 600);
                case 4: // Escala única
                    double escala = Double.parseDouble(txtEscala.getText());
                    int escalaRefX = Integer.parseInt(txtEscalasRefX.getText());
                    int escalaRefY = Integer.parseInt(txtEscalasRefY.getText());
                    return (escala > 0 && escala <= 15) &&
                            (escalaRefX >= 0 && escalaRefX <= 1000) && (escalaRefY >= 0 && escalaRefY <= 600);
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }

    public static double[] showDialog(PainelDesenho painel, int tipoTransformacao) {
        PainelTransfs dialog = new PainelTransfs(painel, tipoTransformacao);
        dialog.setVisible(true);
        //===========================================================================
        double[] valores = new double[5];
        switch (tipoTransformacao) {
            case 1: // Rotação
                valores[0] = Double.parseDouble(dialog.txtRotacaoGrau.getText());
                valores[1] = Double.parseDouble(dialog.txtRefX.getText());
                valores[2] = Double.parseDouble(dialog.txtRefY.getText());
                break;
            case 2: // Mover
                valores[0] = Double.parseDouble(dialog.txtMoverX.getText());
                valores[1] = Double.parseDouble(dialog.txtMoverY.getText());
                break;
            case 3: // Escala
                valores[0] = Double.parseDouble(dialog.txtEscalasX.getText());
                valores[1] = Double.parseDouble(dialog.txtEscalasY.getText());
                valores[2] = Double.parseDouble(dialog.txtEscalasRefX.getText());
                valores[3] = Double.parseDouble(dialog.txtEscalasRefY.getText());
                break;
            case 4: // Escala única
                valores[0] = Double.parseDouble(dialog.txtEscala.getText());
                valores[1] = Double.parseDouble(dialog.txtEscalasRefX.getText());
                valores[2] = Double.parseDouble(dialog.txtEscalasRefY.getText());
                break;
        }
        return valores;
    }
}
