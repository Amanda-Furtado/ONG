package ong.menu;

import ong.UI.*;
import ong.animais.*;
import ong.eventos.Atividade;
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
        CustomFrame janela = new CustomFrame("Área do Voluntário - Abrigo Amigos Fofos", 720, 680);

        CustomFormPanel painelFormulario = new CustomFormPanel(560, 560);

        CustomLabel lblTitulo = new CustomLabel("Bem-vindo(a) " + user.getNome() + "!", Font.BOLD, 24);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        CustomBotao btnVisuAnimais = new CustomBotao("Visualizar Animais Disponíveis", e -> {
            new MenuAnimaisDisponiveis(inv, user).mostrarTela();
        });

        CustomBotao btnConsultarAdocoes = new CustomBotao("Consultar Minhas Solicitações", e -> {
            abrirJanelaConsultarAdocoes();
        });

        CustomBotao btnAnimaisResp = new CustomBotao("Animais Sob Minha Responsabilidade", e -> {
            abrirJanelaAnimaisResponsabilidade();
        });

        CustomBotao btnParticiparEventos = new CustomBotao("Participar de Eventos", e -> {
            new MenuEventos(user, inv).mostrarTela();
        });

        CustomBotao btnHistoricoParticipacoes = new CustomBotao("Histórico de Participações", e -> {
            abrirJanelaHistoricoParticipacoes();
        });

        CustomBotao btnNotificacoes = new CustomBotao("Notificações", e -> {
            new MenuNotificacoes(user, inv).mostrarTela();
        });

        btnVisuAnimais.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnConsultarAdocoes.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAnimaisResp.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnParticiparEventos.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnHistoricoParticipacoes.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnNotificacoes.setAlignmentX(Component.CENTER_ALIGNMENT);

        painelFormulario.addComEspacamento(lblTitulo, 36);
        painelFormulario.addComEspacamento(btnVisuAnimais, 18);
        painelFormulario.addComEspacamento(btnConsultarAdocoes, 18);
        painelFormulario.addComEspacamento(btnAnimaisResp, 18);
        painelFormulario.addComEspacamento(btnParticiparEventos, 18);
        painelFormulario.addComEspacamento(btnHistoricoParticipacoes, 18);
        painelFormulario.addComEspacamento(btnNotificacoes, 0);

        janela.setConteudo(painelFormulario);
        janela.setExtendedState(JFrame.MAXIMIZED_BOTH);
        janela.setVisible(true);
    }

    private void abrirJanelaConsultarAdocoes() {
        ArrayList<SolicitacaoAdocao> minhasSolicitacoes = inv.buscarSolicitacoesPorCpf(user.getCpf());

        CustomFrame telaConsulta = new CustomFrame("Minhas Solicitações - Abrigo Amigos Fofos", 720, 500);

        JPanel painelPrincipal = new JPanel(new BorderLayout(16, 16));
        painelPrincipal.setPreferredSize(new Dimension(680, 440));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));
        painelPrincipal.setOpaque(false);

        CustomLabel lblTitulo = new CustomLabel("MINHAS SOLICITAÇÕES DE ADOÇÃO", Font.BOLD, 20);

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
            JOptionPane.showMessageDialog(telaConsulta, "Você ainda não possui solicitações registradas.", "Informação", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void abrirJanelaAnimaisResponsabilidade() {
        CustomFrame telaResp = new CustomFrame("Animais Sob Minha Responsabilidade", 740, 520);

        JPanel painelPrincipal = new JPanel(new BorderLayout(16, 16));
        painelPrincipal.setPreferredSize(new Dimension(700, 460));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));
        painelPrincipal.setOpaque(false);

        CustomLabel lblTitulo = new CustomLabel("ANIMAIS SOB MINHA RESPONSABILIDADE", Font.BOLD, 20);

        String[] colunas = {"ID", "Nome", "Espécie", "Saúde", "Status Adoção"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Runnable recarregar = () -> {
            modelo.setRowCount(0);
            for (Animal a : user.getAnimaisResponsavel()) {
                modelo.addRow(new Object[]{
                    String.format("%02d", a.getId()),
                    a.getNome(),
                    a.getEspecie(),
                    a.getEstadoSaude(),
                    a.getStatusAdocao()
                });
            }
        };
        recarregar.run();

        JTable tabela = new JTable(modelo);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setRowHeight(26);
        tabela.getTableHeader().setFont(new Font("Quicksand", Font.BOLD, 13));
        tabela.setFont(new Font("Quicksand", Font.PLAIN, 13));

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 4));
        painelBotoes.setOpaque(false);

        CustomBotao btnAssumir = new CustomBotao("Assumir Cuidado (por ID)", e -> {
            CustomTextField txtId = new CustomTextField();
            Object[] msg = {new CustomLabel("ID do Animal no abrigo:"), txtId};
            if (JOptionPane.showConfirmDialog(telaResp, msg, "Assumir Responsabilidade", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                try {
                    int id = Integer.parseInt(txtId.getText().trim());
                    Animal a = inv.buscarPorId(id);
                    if (a != null) {
                        user.addAnimalResponsavel(a);
                        recarregar.run();
                    } else {
                        JOptionPane.showMessageDialog(telaResp, "Animal não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(telaResp, "ID inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnAssumir.setFont(new Font("Quicksand", Font.BOLD, 16));

        CustomBotao btnFechar = new CustomBotao("Fechar", e -> telaResp.dispose());
        btnFechar.setFont(new Font("Quicksand", Font.BOLD, 16));

        painelBotoes.add(btnAssumir);
        painelBotoes.add(btnFechar);

        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        painelPrincipal.add(new JScrollPane(tabela), BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        telaResp.setConteudo(painelPrincipal);
        telaResp.setVisible(true);
    }

    private void abrirJanelaHistoricoParticipacoes() {
        CustomFrame telaHist = new CustomFrame("Participações em Ações da ONG", 760, 520);

        JPanel painelPrincipal = new JPanel(new BorderLayout(16, 16));
        painelPrincipal.setPreferredSize(new Dimension(720, 460));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));
        painelPrincipal.setOpaque(false);

        CustomLabel lblTitulo = new CustomLabel("MINHAS ATIVIDADES E HISTÓRICO", Font.BOLD, 20);

        String[] colunas = {"ID", "Título", "Tipo", "Data", "Local", "Situação"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Runnable recarregar = () -> {
            modelo.setRowCount(0);
            for (Atividade atv : user.getAtvAtivas()) {
                modelo.addRow(new Object[]{atv.getId(), atv.getTitulo(), atv.getTipo(), atv.getData(), atv.getLocal(), "Inscrito (Ativa)"});
            }
            for (Atividade atv : user.getHistorico()) {
                modelo.addRow(new Object[]{atv.getId(), atv.getTitulo(), atv.getTipo(), atv.getData(), atv.getLocal(), "Concluída"});
            }
        };
        recarregar.run();

        JTable tabela = new JTable(modelo);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setRowHeight(26);
        tabela.getTableHeader().setFont(new Font("Quicksand", Font.BOLD, 13));
        tabela.setFont(new Font("Quicksand", Font.PLAIN, 13));

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 4));
        painelBotoes.setOpaque(false);

        CustomBotao btnConcluir = new CustomBotao("Marcar como Concluída", e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(telaHist, "Selecione uma atividade ativa na tabela.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String situacao = modelo.getValueAt(linha, 5).toString();
            if (!situacao.startsWith("Inscrito")) {
                JOptionPane.showMessageDialog(telaHist, "Esta atividade já foi concluída.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            int idAtv = Integer.parseInt(modelo.getValueAt(linha, 0).toString());
            Atividade alvo = inv.buscarAtividadePorId(idAtv);
            if (alvo != null) {
                user.concluirAtividadeAtiva(alvo);
                recarregar.run();
            }
        });
        btnConcluir.setFont(new Font("Quicksand", Font.BOLD, 16));

        CustomBotao btnFechar = new CustomBotao("Fechar", e -> telaHist.dispose());
        btnFechar.setFont(new Font("Quicksand", Font.BOLD, 16));

        painelBotoes.add(btnConcluir);
        painelBotoes.add(btnFechar);

        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        painelPrincipal.add(new JScrollPane(tabela), BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        telaHist.setConteudo(painelPrincipal);
        telaHist.setVisible(true);
    }
}