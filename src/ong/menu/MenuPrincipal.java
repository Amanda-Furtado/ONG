package ong.menu;
import javax.swing.*;

import java.awt.*;


public class MenuPrincipal {
    
    public void mostrarMenu(){
        JFrame janela = new JFrame();
        janela.setSize(600, 600);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);
        janela.setLayout(new BorderLayout());

        // Textos
        JPanel painelTexto = new JPanel();
        painelTexto.setLayout(new BoxLayout(painelTexto, BoxLayout.Y_AXIS));
        painelTexto.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));

        JLabel lblLinha1 = new JLabel("Bem vindo ao sistema do");
        JLabel lblLinha2 = new JLabel("Abrigo Amigos Fofos");

        Font fontePrincipal = new Font("Quicksand", Font.BOLD, 36);
        lblLinha1.setFont(fontePrincipal);
        lblLinha2.setFont(fontePrincipal);

        lblLinha1.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblLinha2.setAlignmentX(Component.CENTER_ALIGNMENT);

        painelTexto.add(lblLinha1);
        painelTexto.add(lblLinha2);

        janela.add(painelTexto, BorderLayout.CENTER);
        
        // Botoes
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(0, 0, 100, 0));


        JButton btnEntrar = new JButton("Entrar");
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnVisuAnimais = new JButton("Visualizar Animais Disponíveis");
        JButton btnSobreSis = new JButton("Sobre o Sistema");
        
        
        Font fonteBotoes = new Font("Quicksand", Font.BOLD, 24);
        btnEntrar.setFont(fonteBotoes);
        btnRegistrar.setFont(fonteBotoes);
        btnVisuAnimais.setFont(fonteBotoes);
        btnSobreSis.setFont(fonteBotoes);

        Dimension dimBotao = new Dimension(300, 36);
        btnEntrar.setPreferredSize(dimBotao);
        btnRegistrar.setPreferredSize(dimBotao);
        btnVisuAnimais.setPreferredSize(dimBotao);
        btnSobreSis.setPreferredSize(dimBotao);

        painelBotoes.add(btnEntrar);
        painelBotoes.add(btnRegistrar);
        painelBotoes.add(btnVisuAnimais);
        painelBotoes.add(btnSobreSis);

        janela.add(painelBotoes, BorderLayout.SOUTH);

        janela.setVisible(true);
    }

}