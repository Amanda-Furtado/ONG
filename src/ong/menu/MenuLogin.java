package ong.menu;
import javax.swing.*;

import java.awt.*;


public class MenuLogin {
    
    public void mostrarMenu(){
        JFrame janela = new JFrame();
        janela.setSize(600, 600);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);
        janela.setLayout(new BorderLayout());

        JPanel painelTexto = new JPanel();
        painelTexto.setLayout(new BoxLayout(painelTexto, BoxLayout.Y_AXIS));
        painelTexto.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));

        JLabel lblLinha1 = new JLabel("Bem vindo a porta de entrada do");
        JLabel lblLinha2 = new JLabel("Abrigo Amigos Fofos");

        Font fontePrincipal = new Font("Quicksand", Font.BOLD, 20);
        lblLinha1.setFont(fontePrincipal);
        lblLinha2.setFont(fontePrincipal);

        lblLinha1.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblLinha2.setAlignmentX(Component.CENTER_ALIGNMENT);

        painelTexto.add(lblLinha1);
        painelTexto.add(lblLinha2);

        janela.add(painelTexto, BorderLayout.CENTER);

        janela.setVisible(true);
    }

}