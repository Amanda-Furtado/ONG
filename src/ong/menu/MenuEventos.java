package ong.menu;

import ong.UI.*;
import ong.eventos.Atividade;
import ong.service.Inventario;
import ong.usuario.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MenuEventos {
    private Usuario user;
    private Inventario inv;

    public MenuEventos(Usuario user, Inventario inv) {
        this.user = user;
        this.inv = inv;
    }

    public void mostrarTela() {
        CustomFrame janela = new CustomFrame("Eventos e Atividades - Abrigo Amigos Fofos", 780, 560);

        JPanel painelPrincipal = new JPanel(new BorderLayout(16, 16));
        painelPrincipal.setPreferredSize(new Dimension(740, 500));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));
        painelPrincipal.setOpaque(false);

        CustomLabel lblTitulo = new CustomLabel("EVENTOS E ATIVIDADES DISPONÍVEIS", Font.BOLD, 22);

        String[] colunas = {"ID", "Título", "Categoria", "Data", "Local", "Vagas"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Runnable atualizar = () -> {
            modelo.setRowCount(0);
            for (Atividade a : inv.getAtividades()) {
                modelo.addRow(new Object[]{
                    String.format("%02d", a.getId()),
                    a.getTitulo(),
                    a.getTipo(),
                    a.getData(),
                    a.getLocal(),
                    a.getVagasDisponiveis()
                });
            }
        };
        atualizar.run();

        JTable tabela = new JTable(modelo);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setRowHeight(26);
        tabela.getTableHeader().setFont(new Font("Quicksand", Font.BOLD, 13));
        tabela.setFont(new Font("Quicksand", Font.PLAIN, 13));

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 4));
        painelBotoes.setOpaque(false);

        CustomBotao btnDetalhes = new CustomBotao("Ver Descrição", e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(janela, "Selecione um evento primeiro.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = Integer.parseInt(modelo.getValueAt(linha, 0).toString());
            Atividade a = inv.buscarAtividadePorId(id);
            if (a != null) {
                String info = "Título: " + a.getTitulo() +
                              "\nTipo: " + a.getTipo() +
                              "\nData: " + a.getData() +
                              "\nLocal: " + a.getLocal() +
                              "\nVagas Restantes: " + a.getVagasDisponiveis() +
                              "\nDescrição: " + a.getDescricao();
                JOptionPane.showMessageDialog(janela, info, "Detalhes da Atividade", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        btnDetalhes.setFont(new Font("Quicksand", Font.BOLD, 16));

        CustomBotao btnParticipar = new CustomBotao("Inscrever-se", e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(janela, "Selecione um evento na tabela.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!(user instanceof Voluntario)) {
                JOptionPane.showMessageDialog(janela, "Apenas voluntários podem se inscrever nas atividades.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = Integer.parseInt(modelo.getValueAt(linha, 0).toString());
            Atividade a = inv.buscarAtividadePorId(id);
            if (a != null) {
                boolean inscrito = a.inscreverVoluntario((Voluntario) user);
                if (inscrito) {
                    JOptionPane.showMessageDialog(janela, "Inscrição confirmada em: " + a.getTitulo() + "!");
                    atualizar.run();
                } else {
                    JOptionPane.showMessageDialog(janela, "Não foi possível se inscrever (sem vagas ou você já está inscrito).", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        btnParticipar.setFont(new Font("Quicksand", Font.BOLD, 16));

        CustomBotao btnFechar = new CustomBotao("Fechar", e -> janela.dispose());
        btnFechar.setFont(new Font("Quicksand", Font.BOLD, 16));

        painelBotoes.add(btnDetalhes);
        painelBotoes.add(btnParticipar);
        painelBotoes.add(btnFechar);

        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        painelPrincipal.add(new JScrollPane(tabela), BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        janela.setConteudo(painelPrincipal);
        janela.setVisible(true);
    }
}