package ong.UI;

import javax.swing.*;
import java.awt.*;


public class CustomPasswordField extends JPasswordField{
    public CustomPasswordField(){
        super();
        configurarEstilo();
    }

    private void configurarEstilo(){
        setFont(new Font("Quicksand", Font.PLAIN, 16));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        setAlignmentX(Component.LEFT_ALIGNMENT);
    }
}