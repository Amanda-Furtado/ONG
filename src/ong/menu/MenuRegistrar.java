package ong.menu;

import ong.UI.*;
import ong.sistema.SistemaUsuario;
import ong.usuario.*;

import javax.swing.*;
import java.awt.*;

public class MenuRegistrar {
    private SistemaUsuario sisUser;
    private String tipoSelecionado = "Voluntario";

    public MenuRegistrar(SistemaUsuario sisUser) {
        this.sisUser = sisUser;
    }

    public void mostrarTela() {
        CustomFrame janela = new CustomFrame("Cadastro - Abrigo Amigos Fofos", 540, 700);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));
        painelPrincipal.setOpaque(false);
        painelPrincipal.setPreferredSize(new Dimension(460, 520));

        
        CustomLabel lblTitulo = new CustomLabel("Registrar como:", Font.BOLD, 22);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        CustomLabel lblPerfilAtivo = new CustomLabel("Perfil selecionado: Voluntário", Font.ITALIC, 13);
        lblPerfilAtivo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel painelTipo = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 5));
        painelTipo.setOpaque(false);
        painelTipo.setMaximumSize(new Dimension(450, 50));

        CustomBotao btnVoluntario = new CustomBotao("Voluntário", e -> {
            tipoSelecionado = "Voluntario";
            lblPerfilAtivo.setText("Perfil selecionado: Voluntário");
        });

        CustomBotao btnVeterinario = new CustomBotao("Veterinário", e -> {
            tipoSelecionado = "Veterinario";
            lblPerfilAtivo.setText("Perfil selecionado: Veterinário");
        });

        painelTipo.add(btnVoluntario);
        painelTipo.add(btnVeterinario);

       
        JTextField txtNome = new JTextField(22);
        JTextField txtCpf = new JTextField(22);
        JTextField txtEmail = new JTextField(22);
        JPasswordField txtSenha = new JPasswordField(22); 
  
        JPanel painelBotaoFinal = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 10));
        painelBotaoFinal.setOpaque(false);

        CustomBotao btnRegistrar = new CustomBotao("Registrar", e -> {
            String nome = txtNome.getText().trim();
            String cpf = txtCpf.getText().trim();
            String email = txtEmail.getText().trim();
            String senha = new String(txtSenha.getPassword()).trim();


            if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(janela, "Por favor, preencha todos os campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Usuario novoUsuario;
           
            if (tipoSelecionado.equals("Voluntario")) {
                novoUsuario = new Voluntario(nome, cpf, email, senha);
            } else {
                novoUsuario = new Veterinario(nome, cpf, email, senha);
            }


            sisUser.cadastrarUsuario(novoUsuario);

            JOptionPane.showMessageDialog(janela, tipoSelecionado + " cadastrado com sucesso!");
            janela.dispose();
        });

        painelBotaoFinal.add(btnRegistrar);


        painelPrincipal.add(lblTitulo);
        painelPrincipal.add(Box.createVerticalStrut(10));
        painelPrincipal.add(painelTipo);
        painelPrincipal.add(Box.createVerticalStrut(4));
        painelPrincipal.add(lblPerfilAtivo);
        painelPrincipal.add(Box.createVerticalStrut(20));

        painelPrincipal.add(criarLinhaCampo("Nome:", txtNome));
        painelPrincipal.add(Box.createVerticalStrut(12));
        painelPrincipal.add(criarLinhaCampo("CPF:", txtCpf));
        painelPrincipal.add(Box.createVerticalStrut(12));
        painelPrincipal.add(criarLinhaCampo("E-mail:", txtEmail));
        painelPrincipal.add(Box.createVerticalStrut(12));
        painelPrincipal.add(criarLinhaCampo("Senha:", txtSenha));
        painelPrincipal.add(Box.createVerticalStrut(25));

        painelPrincipal.add(painelBotaoFinal);

        janela.setConteudo(painelPrincipal);
        janela.setVisible(true);
    }

    private JPanel criarLinhaCampo(String textoLabel, JComponent campo) {
        JPanel linha = new JPanel();
        linha.setLayout(new BoxLayout(linha, BoxLayout.Y_AXIS));
        linha.setOpaque(false);
        linha.setMaximumSize(new Dimension(380, 55));
        linha.setAlignmentX(Component.CENTER_ALIGNMENT);

        CustomLabel lbl = new CustomLabel(textoLabel);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.setMaximumSize(new Dimension(380, 30));

        linha.add(lbl);
        linha.add(Box.createVerticalStrut(4));
        linha.add(campo);
        return linha;
    }
}