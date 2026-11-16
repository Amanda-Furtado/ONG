package ong.UI;

import javax.swing.*;
import java.awt.*;

public class CustomFrame extends JFrame{
    private JPanel painelPrincipal;

    public CustomFrame(String titulo, int largura, int altura){
        setTitle(titulo);
        setSize(largura, altura);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBackground(Color.WHITE);

        add(painelPrincipal);

    }

    public void setConteudo(JComponent componente){
        painelPrincipal.removeAll();
        painelPrincipal.add(componente, new GridBagConstraints());
        revalidate();
        repaint();
    }
}