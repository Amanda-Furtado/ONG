package ong.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class CustomBotao extends JButton {
    public CustomBotao(String texto, ActionListener acaoClique){
        super(texto);

        setFont(new Font("Quicksand", Font.BOLD, 24));

        setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        setAlignmentX(Component.LEFT_ALIGNMENT);

        setFocusPainted(false);

        if (acaoClique != null) {
            addActionListener(acaoClique);
        }
    }
}