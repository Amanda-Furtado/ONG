package ong.menu;

import ong.UI.*;
import ong.service.Inventario;
import ong.service.Notificacao;
import ong.usuario.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MenuNotificacoes {
    private Usuario user;
    private Inventario inv;

    public MenuNotificacoes(Usuario user, Inventario inv) {
        this.user = user;
        this.inv = inv;
    }

    public void mostrarTela() {
        CustomFrame tela = new CustomFrame("Central de Notificações - Abrigo Amigos Fofos", 820, 560);

        JPanel painelPrincipal = new JPanel(new BorderLayout(16, 16));
        painelPrincipal.setPreferredSize(new Dimension(780, 500));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));
        painelPrincipal.setOpaque(false);

        CustomLabel lblTitulo = new CustomLabel("CENTRAL DE NOTIFICAÇÕES E AVISOS", Font.BOLD, 22);

        String[] colunas = {"ID", "Categoria", "Data", "Mensagem"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Runnable atualizar = () -> {
            modelo.setRowCount(0);
            ArrayList<Notificacao> lista = inv.getNotificacoesParaUsuario(user);
            for (Notificacao n : lista) {
                modelo.addRow(new Object[]{
                    String.format("%03d", n.getId()),
                    n.getCategoria(),
                    n.getData(),
                    n.getMensagem()
                });
            }
        };
        atualizar.run();

        JTable tabela = new JTable(modelo);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setRowHeight(28);
        tabela.getTableHeader().setFont(new Font("Quicksand", Font.BOLD, 13));
        tabela.setFont(new Font("Quicksand", Font.PLAIN, 13));
        tabela.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(180);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(95);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(420);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 4));
        painelBotoes.setOpaque(false);

        CustomBotao btnLerCompleta = new CustomBotao("Ler Aviso Completo", e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(tela, "Selecione uma notificação na tabela.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String cat = modelo.getValueAt(linha, 1).toString();
            String data = modelo.getValueAt(linha, 2).toString();
            String msg = modelo.getValueAt(linha, 3).toString();
            JOptionPane.showMessageDialog(tela, "Categoria: " + cat + "\nData: " + data + "\n\n" + msg, "Detalhe da Notificação", JOptionPane.INFORMATION_MESSAGE);
        });
        btnLerCompleta.setFont(new Font("Quicksand", Font.BOLD, 15));

        painelBotoes.add(btnLerCompleta);

        if (user instanceof Administrador) {
            CustomBotao btnPublicarAviso = new CustomBotao("Publicar Aviso Geral", e -> {
                CustomTextField txtAviso = new CustomTextField();
                Object[] campos = {
                    new CustomLabel("Digite o aviso geral para todos os usuários do abrigo:"),
                    txtAviso
                };
                if (JOptionPane.showConfirmDialog(tela, campos, "Novo Aviso Geral", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                    String texto = txtAviso.getText().trim();
                    if (!texto.isEmpty()) {
                        String hoje = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                        inv.registrarNotificacao("Aviso Geral do Abrigo", texto, hoje, null);
                        atualizar.run();
                    }
                }
            });
            btnPublicarAviso.setFont(new Font("Quicksand", Font.BOLD, 15));
            painelBotoes.add(btnPublicarAviso);
        }

        CustomBotao btnFechar = new CustomBotao("Fechar", e -> tela.dispose());
        btnFechar.setFont(new Font("Quicksand", Font.BOLD, 15));
        painelBotoes.add(btnFechar);

        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        painelPrincipal.add(new JScrollPane(tabela), BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        tela.setConteudo(painelPrincipal);
        tela.setVisible(true);
    }
}