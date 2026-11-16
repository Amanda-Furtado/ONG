package ong.menu;

import ong.UI.*;
import ong.usuario.*;

import javax.swing.*;
import java.awt.*;

public class MenuEventos {
    private Usuario User;

    public MenuEventos(Usuario User){
        this.User = User;
    }
    
    public void mostrarTela() {
        CustomFrame janela = new CustomFrame("Eventos - Abrigo Amigos Fofos", 720, 480);

        CustomFormPanel painelFormulario = new CustomFormPanel(720, 480);

        CustomLabel lblTitulo = new CustomLabel("Eventos atuais:", Font.BOLD, 24);

        CustomLabel lblFeirasAdocao = new CustomLabel("Feiras de Adocao");
        CustomLabel lblCampanhaVacinacao = new CustomLabel("Campanhas de Vacinacao");
        CustomLabel lblMultiraoLimpeza = new CustomLabel("Multiraões de Limpeza");
        CustomLabel lblEventosBeneficentes = new CustomLabel("Eventos Beneficentes");
        
  
        CustomBotao btnParticipar = new CustomBotao("Participar", e -> {
            JOptionPane.showMessageDialog(janela, "participando...", JOptionPane.MESSAGE);
        });


        painelFormulario.addComEspacamento(lblTitulo, 30);
    
        painelFormulario.addComEspacamento(lblFeirasAdocao, 5);
      
        painelFormulario.addComEspacamento(lblCampanhaVacinacao, 5);
 
        painelFormulario.addComEspacamento(lblMultiraoLimpeza, 15);

        painelFormulario.addComEspacamento(lblEventosBeneficentes, 0);

        janela.setConteudo(painelFormulario);
        
        janela.setVisible(true);
    }
}