package ong.menu;

import ong.UI.*;
import ong.service.Inventario;
import ong.sistema.SistemaUsuario;

import javax.swing.*;
import java.awt.*;

public class MenuRelatorios {
    private SistemaUsuario sisUser;
    private Inventario inv;

    public MenuRelatorios(SistemaUsuario sisUser, Inventario inv) {
        this.sisUser = sisUser;
        this.inv = inv;
    }

    public void mostrarTela() {
        CustomFrame tela = new CustomFrame("Relatórios e Estatísticas - Abrigo Amigos Fofos", 780, 600);

        JPanel painelPrincipal = new JPanel(new BorderLayout(16, 16));
        painelPrincipal.setPreferredSize(new Dimension(740, 540));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));
        painelPrincipal.setOpaque(false);

        JPanel painelTopo = new JPanel();
        painelTopo.setLayout(new BoxLayout(painelTopo, BoxLayout.Y_AXIS));
        painelTopo.setOpaque(false);

        CustomLabel lblTitulo = new CustomLabel("RELATÓRIOS E ESTATÍSTICAS DA ONG", Font.BOLD, 22);

        JPanel painelFiltroMeses = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        painelFiltroMeses.setOpaque(false);
        painelFiltroMeses.setAlignmentX(Component.LEFT_ALIGNMENT);

        CustomLabel lblMeses = new CustomLabel("Período de operação (meses):");
        CustomTextField txtMeses = new CustomTextField();
        txtMeses.setText("1");
        txtMeses.setPreferredSize(new Dimension(70, 34));

        JTextArea areaRelatorio = new JTextArea();
        areaRelatorio.setFont(new Font("Quicksand", Font.PLAIN, 14));
        areaRelatorio.setEditable(false);
        areaRelatorio.setLineWrap(true);
        areaRelatorio.setWrapStyleWord(true);
        areaRelatorio.setMargin(new Insets(12, 12, 12, 12));

        Runnable atualizarRelatorio = () -> {
            try {
                int meses = Integer.parseInt(txtMeses.getText().trim());
                if (meses <= 0) {
                    JOptionPane.showMessageDialog(tela, "Informe uma quantidade de meses maior que zero.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                areaRelatorio.setText(inv.gerarRelatorioGeral(sisUser, meses));
                areaRelatorio.setCaretPosition(0);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(tela, "Digite um número inteiro válido para os meses.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        };

        CustomBotao btnAtualizar = new CustomBotao("Atualizar Relatório", e -> atualizarRelatorio.run());
        btnAtualizar.setFont(new Font("Quicksand", Font.BOLD, 15));

        painelFiltroMeses.add(lblMeses);
        painelFiltroMeses.add(txtMeses);
        painelFiltroMeses.add(btnAtualizar);

        painelTopo.add(lblTitulo);
        painelTopo.add(Box.createVerticalStrut(10));
        painelTopo.add(painelFiltroMeses);

        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 4));
        painelRodape.setOpaque(false);

        CustomBotao btnFechar = new CustomBotao("Fechar", e -> tela.dispose());
        btnFechar.setFont(new Font("Quicksand", Font.BOLD, 16));
        painelRodape.add(btnFechar);

        atualizarRelatorio.run();

        painelPrincipal.add(painelTopo, BorderLayout.NORTH);
        painelPrincipal.add(new JScrollPane(areaRelatorio), BorderLayout.CENTER);
        painelPrincipal.add(painelRodape, BorderLayout.SOUTH);

        tela.setConteudo(painelPrincipal);
        tela.setVisible(true);
    }
}