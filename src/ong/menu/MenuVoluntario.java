package ong.menu;

import ong.UI.*;
import ong.usuario.Voluntario;

import javax.swing.*;
import java.awt.*;

public class MenuVoluntario{
    private Voluntario user;

    public MenuVoluntario(Voluntario user){
        this.user = user;
    }

    public void mostrarTela() {
        CustomFrame janela = new CustomFrame("Área do Voluntario - Abrigo Amigos Fofos", 720, 640);

        CustomFormPanel painelFormulario = new CustomFormPanel(720, 640);

        CustomLabel lblTitulo = new CustomLabel("Bem-vindo(a) " + user.getNome() + "!", Font.BOLD, 24);

        CustomBotao btnVisuAnimais = new CustomBotao("Visualizar animais disponivéis", e -> {
            JOptionPane.showMessageDialog(null, "Mostrando animais...");
        });

        CustomBotao btnSolicitarAdocao = new CustomBotao("Solicitar Adocao", e -> {
            JOptionPane.showMessageDialog(null, "solicitando adocao...");
        });

        CustomBotao btnConsultarAdocoes = new CustomBotao("Consultar minhas solicitacoes", e -> {
            JOptionPane.showMessageDialog(null, "consultadon adocao...");
        });

        CustomBotao btnParticiparEventos = new CustomBotao("Participar de Eventos", e -> {
            JOptionPane.showMessageDialog(null, "cpariticpar...");
        });

        CustomBotao btnHistoricoParticipacoes = new CustomBotao("Histórico de Participacões", e -> {
            JOptionPane.showMessageDialog(null, "historico..");
        });

        CustomBotao btnNotificacoes = new CustomBotao("Notificacões", e -> {
            JOptionPane.showMessageDialog(null, "notificacoes...");
        });

        painelFormulario.addComEspacamento(lblTitulo, 48);
    
        painelFormulario.addComEspacamento(btnVisuAnimais, 24);
    
        painelFormulario.addComEspacamento(btnSolicitarAdocao, 24);
  
        painelFormulario.addComEspacamento(btnConsultarAdocoes, 24);

        painelFormulario.addComEspacamento(btnParticiparEventos, 24);   

        painelFormulario.addComEspacamento(btnHistoricoParticipacoes, 24);

        painelFormulario.addComEspacamento(btnNotificacoes, 0);

        janela.setConteudo(painelFormulario);

        janela.setVisible(true);
    }
}