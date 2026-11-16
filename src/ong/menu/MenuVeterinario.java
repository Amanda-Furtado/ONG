package ong.menu;

import ong.UI.*;
import ong.usuario.Veterinario;

import javax.swing.*;
import java.awt.*;

public class MenuVeterinario{
    private Veterinario user;

    public MenuVeterinario(Veterinario user){
        this.user = user;
    }

    public void mostrarTela() {
        CustomFrame janela = new CustomFrame("Área do Voluntario - Abrigo Amigos Fofos", 720, 640);

        CustomFormPanel painelFormulario = new CustomFormPanel(720, 640);

        CustomLabel lblTitulo = new CustomLabel("Bem-vindo(a) Dr(a). " + user.getNome() + "!", Font.BOLD, 24);

        CustomBotao btnConsultarAnimais = new CustomBotao("Consultar Animais", e -> {
            JOptionPane.showMessageDialog(null, "consultar animais...");
        });

        CustomBotao btnRegistrarAtendimento = new CustomBotao("Registrar Atendimento", e -> {
            JOptionPane.showMessageDialog(null, "Registrar Atendimento...");
        });

        CustomBotao btnAtualizarTratamento = new CustomBotao("Atualizar Tratamento", e -> {
            JOptionPane.showMessageDialog(null, "AtualizarTratamento...");
        });

        CustomBotao btnRegistrarVacinacao = new CustomBotao("Registrar Vacinacao", e -> {
            JOptionPane.showMessageDialog(null, "RegistrarVacinacao...");
        });

        CustomBotao btnEmitirRelatorioMedico = new CustomBotao("Emitir Relatorio Medico", e -> {
            JOptionPane.showMessageDialog(null, "EmitirRelatorioMedico..");
        });

        CustomBotao btnNotificacoes = new CustomBotao("Notificacões", e -> {
            JOptionPane.showMessageDialog(null, "notificacoes...");
        });

        painelFormulario.addComEspacamento(lblTitulo, 48);
    
        painelFormulario.addComEspacamento(btnConsultarAnimais, 24);
    
        painelFormulario.addComEspacamento(btnRegistrarAtendimento, 24);
  
        painelFormulario.addComEspacamento(btnAtualizarTratamento, 24);

        painelFormulario.addComEspacamento(btnRegistrarVacinacao, 24);   

        painelFormulario.addComEspacamento(btnEmitirRelatorioMedico, 24);

        painelFormulario.addComEspacamento(btnNotificacoes, 0);

        janela.setConteudo(painelFormulario);

        janela.setVisible(true);
    }
}