package ong.menu;

import ong.UI.*;
import ong.animais.*;
import ong.service.Inventario;
import ong.usuario.Voluntario;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MenuAnimaisDisponiveis {
    private Inventario inv;
    private Voluntario voluntarioLogado; // null se acessado pela tela inicial sem login
    private JTable tabAnimais;
    private DefaultTableModel modeloTabela;
    private CustomTextField txtPesquisar;
    private String filtroEspecieAtual = "Todos";

    // Construtor para visitantes (sem login)
    public MenuAnimaisDisponiveis(Inventario inv) {
        this(inv, null);
    }

    // Construtor para voluntário logado
    public MenuAnimaisDisponiveis(Inventario inv, Voluntario voluntarioLogado) {
        this.inv = inv;
        this.voluntarioLogado = voluntarioLogado;
    }

    public void mostrarTela() {
        CustomFrame janela = new CustomFrame("Animais Disponíveis - Abrigo Amigos Fofos", 760, 700);

        JPanel painelPrincipal = new JPanel(new BorderLayout(16, 16));
        painelPrincipal.setPreferredSize(new Dimension(720, 640));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));
        painelPrincipal.setOpaque(false);

        // Pesquisa/filtro
        JPanel painelTopo = new JPanel();
        painelTopo.setLayout(new BoxLayout(painelTopo, BoxLayout.Y_AXIS));
        painelTopo.setOpaque(false);

        CustomLabel lblTitulo = new CustomLabel("ANIMAIS DISPONÍVEIS", Font.BOLD, 24);

        JPanel painelPesquisa = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 4));
        painelPesquisa.setOpaque(false);
        painelPesquisa.setAlignmentX(Component.LEFT_ALIGNMENT);

        CustomLabel lblPesquisar = new CustomLabel("Pesquisar:  ");
        txtPesquisar = new CustomTextField();
        txtPesquisar.setPreferredSize(new Dimension(320, 36));

        txtPesquisar.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { atualizarTabela(); }
            public void removeUpdate(DocumentEvent e) { atualizarTabela(); }
            public void changedUpdate(DocumentEvent e) { atualizarTabela(); }
        });

        painelPesquisa.add(lblPesquisar);
        painelPesquisa.add(txtPesquisar);

        // Filtros por Espécie
        JPanel painelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        painelFiltros.setOpaque(false);
        painelFiltros.setAlignmentX(Component.LEFT_ALIGNMENT);

        CustomLabel lblFiltro = new CustomLabel("Filtro por Espécie: ");

        CustomBotao btnTodos = new CustomBotao("Todos", e -> {
            filtroEspecieAtual = "Todos"; atualizarTabela();
        });
        CustomBotao btnCao = new CustomBotao("Cão", e -> {
            filtroEspecieAtual = "Cachorro"; atualizarTabela();
        });
        CustomBotao btnGato = new CustomBotao("Gato", e -> {
            filtroEspecieAtual = "Gato"; atualizarTabela();
        });
        CustomBotao btnOutros = new CustomBotao("Outros", e -> {
            filtroEspecieAtual = "Outros"; atualizarTabela();
        });

        Font fonteFiltro = new Font("Quicksand", Font.BOLD, 14);
        btnTodos.setFont(fonteFiltro);
        btnCao.setFont(fonteFiltro);
        btnGato.setFont(fonteFiltro);
        btnOutros.setFont(fonteFiltro);

        painelFiltros.add(lblFiltro);
        painelFiltros.add(btnTodos);
        painelFiltros.add(btnCao);
        painelFiltros.add(btnGato);
        painelFiltros.add(btnOutros);

        painelTopo.add(lblTitulo);
        painelTopo.add(Box.createVerticalStrut(15));
        painelTopo.add(painelPesquisa);
        painelTopo.add(Box.createVerticalStrut(5));
        painelTopo.add(painelFiltros);

        // Tabela
        String[] colunas = {"ID", "Nome", "Espécie", "Idade", "Status"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabAnimais = new JTable(modeloTabela);
        tabAnimais.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabAnimais.setRowHeight(24);
        tabAnimais.getTableHeader().setFont(new Font("Quicksand", Font.BOLD, 12));
        tabAnimais.setFont(new Font("Quicksand", Font.PLAIN, 12));

        JScrollPane scrollTabela = new JScrollPane(tabAnimais);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 4));
        painelBotoes.setOpaque(false);

        CustomBotao btnVerDetalhes = new CustomBotao("Ver Detalhes", e -> {
            Animal selecionado = getAnimalSelecionado(janela);
            if (selecionado != null) {
                String detalhes = String.format(
                    "ID: %02d\nNome: %s\nEspécie: %s\nRaça: %s\nIdade: %d ano(s)\nPorte: %s\nSaúde: %s\nData de Resgate: %s\nStatus: %s",
                    selecionado.getId(), selecionado.getNome(), selecionado.getEspecie(),
                    selecionado.getRaca(), selecionado.getIdade(), selecionado.getPorte(),
                    selecionado.getEstadoSaude(), selecionado.getDataResgate(), selecionado.getStatusAdocao()
                );
                JOptionPane.showMessageDialog(janela, detalhes, "Detalhes de " + selecionado.getNome(), JOptionPane.INFORMATION_MESSAGE);
            }
        });

        CustomBotao btnSolicitarAdocao = new CustomBotao("Solicitar Adoção", e -> {
            Animal selecionado = getAnimalSelecionado(janela);
            if (selecionado != null) {
                abrirDialogoAdocao(janela, selecionado);
            }
        });

        painelBotoes.add(btnVerDetalhes);
        painelBotoes.add(btnSolicitarAdocao);

        painelPrincipal.add(painelTopo, BorderLayout.NORTH);
        painelPrincipal.add(scrollTabela, BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        atualizarTabela();

        janela.setConteudo(painelPrincipal);
        janela.setVisible(true);
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        String termo = txtPesquisar.getText().trim().toLowerCase();

        List<Animal> listaFiltrada;
        if (filtroEspecieAtual.equals("Cachorro") || filtroEspecieAtual.equals("Gato")) {
            listaFiltrada = inv.filtrarPorEspecie(filtroEspecieAtual);
        } else if (filtroEspecieAtual.equals("Outros")) {
            listaFiltrada = new ArrayList<>();
            for (Animal a : inv.getAnimais()) {
                if (!(a instanceof Cachorro) && !(a instanceof Gato)) {
                    listaFiltrada.add(a);
                }
            }
        } else {
            listaFiltrada = inv.getAnimais();
        }

        for (Animal a : listaFiltrada) {
            String status = a.getStatusAdocao().toLowerCase();
            if (!status.contains("disponivel") && !status.contains("disponível")) {
                continue;
            }
            if (termo.isEmpty() || a.getNome().toLowerCase().contains(termo)) {
                String idFormatado = String.format("%02d", a.getId());
                String idadeFormatada = a.getIdade() + (a.getIdade() == 1 ? " ano" : " anos");

                modeloTabela.addRow(new Object[]{
                    idFormatado,
                    a.getNome(),
                    a.getEspecie(),
                    idadeFormatada,
                    a.getStatusAdocao()
                });
            }
        }
    }

    private Animal getAnimalSelecionado(Component parent) {
        int linha = tabAnimais.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(parent, "Selecione um animal na tabela primeiro.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        int idAnimal = Integer.parseInt(modeloTabela.getValueAt(linha, 0).toString());
        return inv.buscarPorId(idAnimal);
    }

    private void abrirDialogoAdocao(Component parent, Animal animal) {
        CustomTextField txtNomeCandidato = new CustomTextField();
        CustomTextField txtCpfCandidato = new CustomTextField();

        if (voluntarioLogado != null) {
            txtNomeCandidato.setText(voluntarioLogado.getNome());
            txtNomeCandidato.setEditable(false);
        }

        Object[] campos = {
            new CustomLabel("Animal selecionado: " + animal.getNome() + " (ID: " + String.format("%02d", animal.getId()) + ")", Font.BOLD, 16),
            new CustomLabel("Nome do Candidato:"), txtNomeCandidato,
            new CustomLabel("CPF do Candidato:"), txtCpfCandidato
        };

        int opcao = JOptionPane.showConfirmDialog(parent, campos, "Solicitar Adoção", JOptionPane.OK_CANCEL_OPTION);
        if (opcao == JOptionPane.OK_OPTION) {
            String nome = txtNomeCandidato.getText().trim();
            String cpf = txtCpfCandidato.getText().trim();

            if (nome.isEmpty() || cpf.isEmpty()) {
                JOptionPane.showMessageDialog(parent, "Preencha nome e CPF.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int idSolicitacao = (int) (System.currentTimeMillis() % 10000);
                boolean sucesso = inv.solicitarAdocao(idSolicitacao, animal.getId(), nome, cpf);

                if (sucesso) {
                    JOptionPane.showMessageDialog(
                        parent,
                        "Solicitação #" + String.format("%04d", idSolicitacao) + " registrada com sucesso para " + animal.getNome() + "!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    atualizarTabela();
                }
            } catch (IllegalArgumentException | IllegalStateException ex) {
                JOptionPane.showMessageDialog(parent, ex.getMessage(), "Não foi possível solicitar", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}