package ong.UI;

import javax.swing.*;
import java.awt.*;

public class CustomLabel extends JLabel{
    //texto comum
    public CustomLabel(String txt){
        super(txt);
        configurarEstilo(Font.PLAIN, 16);
    }

    //titulos ou texto personalizado
    public CustomLabel(String txt, int estilo, int tamanho){
        super(txt);
        configurarEstilo(estilo, tamanho);
    }

    //texto clicavel
    public CustomLabel(String txt, boolean isHtml){
        super(txt);
        if(isHtml){
            configurarEstilo(Font.PLAIN, 12);
            setAlignmentX(Component.LEFT_ALIGNMENT);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }


    private void configurarEstilo(int estilo, int tamanho){
        setFont(new Font("Quicksand", estilo, tamanho));
        setAlignmentX(Component.LEFT_ALIGNMENT);
    }
}