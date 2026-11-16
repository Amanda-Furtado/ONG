package ong.UI;

import javax.swing.*;
import java.awt.*;

public class CustomFormPanel extends JPanel{
    public CustomFormPanel(int largura, int altura){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(largura, altura));
    }

    public void addComEspacamento(JComponent componente, int espacamento){
        add(componente);
        if(espacamento > 0){
            add(Box.createRigidArea(new Dimension(0, espacamento)));
        }
    }
}