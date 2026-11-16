package ong.menu;

import ong.UI.*;
import ong.usuario.Administrador;

import javax.swing.*;
import java.awt.*;

public class MenuAdmin{
    private Administrador user;

    public MenuAdmin(Administrador user){
        this.user = user;
    }

    public void mostrarTela() {
        CustomFrame janela = new CustomFrame("Área do Administrador - Abrigo Amigos Fofos", 720, 480);

        CustomFormPanel painelFormulario = new CustomFormPanel(720, 640);

        CustomLabel lblTitulo = new CustomLabel("Bem-vindo(a) administrador " + user.getNome() + "!", Font.BOLD, 24);

        CustomBotao btnGerenciarAnimais = new CustomBotao("Gerenciar animais", e -> {
            JOptionPane.showMessageDialog(null, "btnGerenciarAnimais...");
        });

        CustomBotao btnGerenciarUsuarios = new CustomBotao("Gerenciar usuairos", e -> {
            JOptionPane.showMessageDialog(null, "btnGerenciarUsuarios...");
        });

        CustomBotao btnAprovarAdocoes = new CustomBotao("Aprovar adocões", e -> {
            JOptionPane.showMessageDialog(null, "AprovarAdocoes...");
        });

        CustomBotao btnGerenciarEventos = new CustomBotao("Gerenciar eventos", e -> {
            JOptionPane.showMessageDialog(null, "Gerenciar eventos...");
        });

        CustomBotao btnRelatorios = new CustomBotao("Relatorios", e -> {
            JOptionPane.showMessageDialog(null, "Relatorios...");
        });

        CustomBotao btnEstatisticas = new CustomBotao("Estatísticas", e -> {
            JOptionPane.showMessageDialog(null, "btnEstatisticas...");
        });

        CustomBotao btnNotificacoes = new CustomBotao("Notificacões", e -> {
            JOptionPane.showMessageDialog(null, "Notificacões...");
        });

        painelFormulario.addComEspacamento(lblTitulo, 48);
    
        painelFormulario.addComEspacamento(btnGerenciarAnimais, 24);
    
        painelFormulario.addComEspacamento(btnGerenciarUsuarios, 24);
  
        painelFormulario.addComEspacamento(btnAprovarAdocoes, 24);

        painelFormulario.addComEspacamento(btnGerenciarEventos, 24);   

        painelFormulario.addComEspacamento(btnRelatorios, 24);
        
        painelFormulario.addComEspacamento(btnEstatisticas, 24);

        painelFormulario.addComEspacamento(btnNotificacoes, 0);

        janela.setConteudo(painelFormulario);

        janela.setVisible(true);
    }
}