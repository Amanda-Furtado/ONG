package ong.menu;

import ong.UI.*;
import ong.animais.SolicitacaoAdocao;
import ong.usuario.Voluntario;
import ong.service.Inventario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class MenuVoluntario {
    private Voluntario user;
    private Inventario inv;

    public MenuVoluntario(Voluntario user, Inventario inv) {
        this.user = user;
        this.inv = inv;
    }

    public void mostrarTela() {
        CustomFrame janela = new CustomFrame("Área do Voluntário - Abrigo Amigos Fofos", 720, 640);

        CustomFormPanel painelFormulario = new CustomFormPanel(540, 460);

        CustomLabel lblTitulo = new CustomLabel("Bem-vindo(a) " + user.getNome() + "!", Font.BOLD, 24);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        CustomBotao btnVisuAnimais = new CustomBotao("Visualizar Animais Disponíveis", e -> {
            new MenuAnimaisDisponiveis(inv, user).mostrarTela();
        });

        CustomBotao btnConsultarAdocoes = new CustomBotao("Consultar Minhas Solicitações", e -> {
            abrirJanelaConsultarAdocoes();
        });

        CustomBotao btnParticiparEventos = new CustomBotao("Participar de Eventos", e -> {
            JOptionPane.showMessageDialog(janela, "participar...");
        });

        CustomBotao btnHistoricoParticipacoes = new CustomBotao("Histórico de Participações", e -> {
            JOptionPane.showMessageDialog(janela, "historico...");
        });

        CustomBotao btnNotificacoes = new CustomBotao("Notificações", e -> {
            JOptionPane.showMessageDialog(janela, "notificacoes...");
        });

        btnVisuAnimais.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnConsultarAdocoes.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnParticiparEventos.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnHistoricoParticipacoes.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnNotificacoes.setAlignmentX(Component.CENTER_ALIGNMENT);

        painelFormulario.addComEspacamento(lblTitulo, 40);
        painelFormulario.addComEspacamento(btnVisuAnimais, 20);
        painelFormulario.addComEspacamento(btnConsultarAdocoes, 20);
        painelFormulario.addComEspacamento(btnParticiparEventos, 20);
        painelFormulario.addComEspacamento(btnHistoricoParticipacoes, 20);
        painelFormulario.addComEspacamento(btnNotificacoes, 0);

        janela.setConteudo(painelFormulario);
        janela.setExtendedState(JFrame.MAXIMIZED_BOTH);
        janela.setVisible(true);
    }

    private void abrirJanelaConsultarAdocoes() {
        CustomTextField txtCpfConsulta = new CustomTextField();
        Object[] mensagem = {
            new CustomLabel("Digite o CPF usado nas solicitações:"),
            txtCpfConsulta
        };

        int opcao = JOptionPane.showConfirmDialog(null, mensagem, "Consultar Solicitações", JOptionPane.OK_CANCEL_OPTION);
        if (opcao != JOptionPane.OK_OPTION) {
            return;
        }

        String cpfDigitado = txtCpfConsulta.getText().trim();
        if (cpfDigitado.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Informe um CPF válido.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ArrayList<SolicitacaoAdocao> minhasSolicitacoes = inv.buscarSolicitacoesPorCpf(cpfDigitado);

        CustomFrame telaConsulta = new CustomFrame("Minhas Solicitações - Abrigo Amigos Fofos", 720, 500);

        JPanel painelPrincipal = new JPanel(new BorderLayout(16, 16));
        painelPrincipal.setPreferredSize(new Dimension(680, 440));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));
        painelPrincipal.setOpaque(false);

        CustomLabel lblTitulo = new CustomLabel("SOLICITAÇÕES PARA O CPF: " + cpfDigitado, Font.BOLD, 20);

        String[] colunas = {"ID Solicitação", "ID Animal", "Animal", "Espécie", "Status"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (SolicitacaoAdocao sol : minhasSolicitacoes) {
            modelo.addRow(new Object[]{
                String.format("%04d", sol.getIdSolicitacao()),
                String.format("%02d", sol.getAnimal().getId()),
                sol.getAnimal().getNome(),
                sol.getAnimal().getEspecie(),
                sol.getStatus()
            });
        }

        JTable tabela = new JTable(modelo);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setRowHeight(26);
        tabela.getTableHeader().setFont(new Font("Quicksand", Font.BOLD, 13));
        tabela.setFont(new Font("Quicksand", Font.PLAIN, 13));

        JScrollPane scroll = new JScrollPane(tabela);

        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelRodape.setOpaque(false);

        CustomBotao btnFechar = new CustomBotao("Fechar", e -> telaConsulta.dispose());
        painelRodape.add(btnFechar);

        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        painelPrincipal.add(scroll, BorderLayout.CENTER);
        painelPrincipal.add(painelRodape, BorderLayout.SOUTH);

        telaConsulta.setConteudo(painelPrincipal);
        telaConsulta.setVisible(true);

        if (minhasSolicitacoes.isEmpty()) {
            JOptionPane.showMessageDialog(telaConsulta, "Nenhuma solicitação encontrada para este CPF.", "Informação", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}