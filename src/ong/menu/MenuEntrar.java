package ong.menu;

import ong.UI.*;

import javax.swing.*;
import java.awt.*;

public class MenuEntrar {

    public void mostrarTela() {
        CustomFrame janela = new CustomFrame("Login - Abrigo Amigos Fofos", 720, 480);

        CustomFormPanel painelFormulario = new CustomFormPanel(720, 480);

        CustomLabel lblTitulo = new CustomLabel("Insira seus dados:", Font.BOLD, 24);

        CustomLabel lblEmail = new CustomLabel("e-mail");
        CustomTextField txtEmail = new CustomTextField();

        CustomLabel lblSenha = new CustomLabel("senha");
        CustomPasswordField txtSenha = new CustomPasswordField();
  
        CustomBotao btnEntrar = new CustomBotao("Entrar", e -> {
            String email = txtEmail.getText();
            String senha = new String(txtSenha.getPassword());
            JOptionPane.showMessageDialog(janela, "email: " + email);
        });

        CustomLabel lblEsqueceuSenha = new CustomLabel("<html><u style='color:blue;'>Esqueceu a senha?</u></html>", true);
        lblEsqueceuSenha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JOptionPane.showMessageDialog(janela, "um dia vai ser feito");
            }
        });

        //Espacamento feito na base do achismo
        painelFormulario.addComEspacamento(lblTitulo, 30);
    
        painelFormulario.addComEspacamento(lblEmail, 5);
    
        painelFormulario.addComEspacamento(txtEmail, 15);
  
        painelFormulario.addComEspacamento(lblSenha, 5);

        painelFormulario.addComEspacamento(txtSenha, 25);   

        painelFormulario.addComEspacamento(btnEntrar, 15);

        painelFormulario.addComEspacamento(lblEsqueceuSenha, 0);

        janela.setConteudo(painelFormulario);
        janela.setVisible(true);
    }
}