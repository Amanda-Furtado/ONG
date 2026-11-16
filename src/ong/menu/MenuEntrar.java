package ong.menu;

import ong.UI.*;
import ong.sistema.SistemaUsuario;
import ong.usuario.*;

import javax.swing.*;
import java.awt.*;

public class MenuEntrar {
    private SistemaUsuario sisUser;

    public MenuEntrar(SistemaUsuario sisUser){
        this.sisUser = sisUser;
    }
    
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
            
            Usuario userLogado = sisUser.validarLogin(email, senha);

            if(userLogado != null){
                if(userLogado instanceof Voluntario){
                    new MenuVoluntario((Voluntario) userLogado).mostrarTela();
                }else if(userLogado instanceof Veterinario){
                    new MenuVeterinario((Veterinario) userLogado).mostrarTela();
                }else{
                    new MenuAdmin((Administrador) userLogado).mostrarTela();
                }
                janela.dispose();
            }else{
                JOptionPane.showMessageDialog(janela, "E-mail ou senha inválidos.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
            }

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