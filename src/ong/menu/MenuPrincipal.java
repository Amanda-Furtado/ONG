package ong.menu;

import ong.sistema.SistemaUsuario;
import ong.service.*;
import ong.UI.*;

import javax.swing.*;
import java.awt.*;


public class MenuPrincipal {
    private SistemaUsuario sisUser;
    private Inventario inv;

    public MenuPrincipal(SistemaUsuario sisUser, Inventario inv){
        this.sisUser = sisUser;
        this.inv = inv;
    }

    public void mostrarMenu(){
        JFrame janela = new JFrame();
        janela.setSize(750, 500);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);
        janela.setLayout(new BorderLayout());

        // Textos
        JPanel painelTexto = new JPanel();
        painelTexto.setLayout(new BoxLayout(painelTexto, BoxLayout.Y_AXIS));
        painelTexto.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 40));

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


        CustomBotao btnEntrar = new CustomBotao("Entrar", e -> {
            new MenuEntrar(this.sisUser).mostrarTela();
        });

        CustomBotao btnRegistrar = new CustomBotao("Registrar", e -> {
            JOptionPane.showMessageDialog(null, "Abrindo tela de Registro...");
            // quando tiver uma janela eu boto um comando aq.
        });

        CustomBotao btnVisuAnimais = new CustomBotao("Visualizar Animais Disponíveis", e -> {
            new MenuAnimaisDisponiveis(inv).mostrarTela();

        });

        CustomBotao btnSobreSis = new CustomBotao("Sobre o Sistema", e -> {
            JOptionPane.showMessageDialog(janela, "Sistema de Gerenciamento para ONG - Abrigo Amigos Fofos v1.0, feito por Amanda e Victor!");
            // quando tiver uma janela eu boto um comando aq.
        });
       

        painelBotoes.add(btnEntrar);
        painelBotoes.add(btnRegistrar);
        painelBotoes.add(btnVisuAnimais);
        painelBotoes.add(btnSobreSis);

        janela.add(painelBotoes, BorderLayout.SOUTH);
        janela.setExtendedState(JFrame.MAXIMIZED_BOTH);
        janela.setVisible(true);
    }

}