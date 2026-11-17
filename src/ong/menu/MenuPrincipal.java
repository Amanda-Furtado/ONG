package ong.menu;

import java.awt.*;
import javax.swing.*;
import ong.UI.*;
import ong.service.*;
import ong.sistema.SistemaUsuario;


public class MenuPrincipal {
    private SistemaUsuario sisUser;
    private Inventario inv;

    public MenuPrincipal(SistemaUsuario sisUser, Inventario inv){
        this.sisUser = sisUser;
        this.inv = inv;
    }

    public void mostrarMenu(){
        CustomFrame janela = new CustomFrame("Login - Abrigo Amigos Fofos", 720, 480);

        CustomFormPanel painelConteudo = new CustomFormPanel(700, 480);

        CustomLabel lblLinha1 = new CustomLabel("Bem vindo ao sistema do", Font.BOLD, 34);
        CustomLabel lblLinha2 = new CustomLabel("Abrigo Amigos Fofos", Font.BOLD, 34);

        lblLinha1.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblLinha2.setAlignmentX(Component.CENTER_ALIGNMENT);

        CustomBotao btnEntrar = new CustomBotao("Entrar", e -> {
            new MenuEntrar(this.sisUser, this.inv).mostrarTela();
        });

        CustomBotao btnRegistrar = new CustomBotao("Registrar", e -> {
            new MenuRegistrar(this.sisUser).mostrarTela();
        });

        CustomBotao btnVisuAnimais = new CustomBotao("Visualizar Animais Disponíveis", e -> {
            new MenuAnimaisDisponiveis(this.inv).mostrarTela();

        });

        CustomBotao btnSobreSis = new CustomBotao("Sobre o Sistema", e -> {
            JOptionPane.showMessageDialog(janela, "Sistema de Gerenciamento para ONG - Abrigo Amigos Fofos v1.0, feito por Amanda e Victor!");
        });
       
        btnEntrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegistrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVisuAnimais.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSobreSis.setAlignmentX(Component.CENTER_ALIGNMENT);

        painelConteudo.addComEspacamento(lblLinha1, 5);
        painelConteudo.addComEspacamento(lblLinha2, 60);
        painelConteudo.addComEspacamento(btnEntrar, 8);
        painelConteudo.addComEspacamento(btnRegistrar, 8);
        painelConteudo.addComEspacamento(btnVisuAnimais, 8);
        painelConteudo.addComEspacamento(btnSobreSis, 8);

        janela.setConteudo(painelConteudo);
        janela.setExtendedState(JFrame.MAXIMIZED_BOTH);
        janela.setVisible(true);
    }

}