package ong.menu;

import ong.UI.*;
import ong.animais.*;
import ong.eventos.*;
import ong.service.Inventario;
import ong.sistema.SistemaUsuario;
import ong.usuario.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MenuAdmin {
    private Administrador user;
    private SistemaUsuario sisUser;
    private Inventario inv;

    public MenuAdmin(Administrador user, SistemaUsuario sisUser, Inventario inv) {
        this.user = user;
        this.sisUser = sisUser;
        this.inv = inv;
    }

    public void mostrarTela() {
        CustomFrame janela = new CustomFrame("Área do Administrador - Abrigo Amigos Fofos", 720, 720);

        CustomFormPanel painelFormulario = new CustomFormPanel(560, 620);

        CustomLabel lblTitulo = new CustomLabel("Bem-vindo(a) Administrador(a) " + user.getNome() + "!", Font.BOLD, 22);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        CustomBotao btnGerenciarAnimais = new CustomBotao("Gerenciar Animais", e -> {
            abrirJanelaGerenciarAnimais();
        });

        CustomBotao btnGerenciarUsuarios = new CustomBotao("Gerenciar Usuários", e -> {
            abrirJanelaGerenciarUsuarios();
        });

        CustomBotao btnAprovarAdocoes = new CustomBotao("Gerenciar Adoções e Devoluções", e -> {
            abrirJanelaGerenciarAdocoes();
        });

        CustomBotao btnGerenciarEventos = new CustomBotao("Gerenciar Eventos", e -> {
            abrirJanelaGerenciarEventos();
        });

        CustomBotao btnRelatorios = new CustomBotao("Relatórios e Estatísticas", e -> {
            new MenuRelatorios(sisUser, inv).mostrarTela();
        });

        CustomBotao btnNotificacoes = new CustomBotao("Notificações e Avisos", e -> {
            new MenuNotificacoes(user, inv).mostrarTela();
        });

        btnGerenciarAnimais.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnGerenciarUsuarios.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAprovarAdocoes.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnGerenciarEventos.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRelatorios.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnNotificacoes.setAlignmentX(Component.CENTER_ALIGNMENT);

        painelFormulario.addComEspacamento(lblTitulo, 36);
        painelFormulario.addComEspacamento(btnGerenciarAnimais, 18);
        painelFormulario.addComEspacamento(btnGerenciarUsuarios, 18);
        painelFormulario.addComEspacamento(btnAprovarAdocoes, 18);
        painelFormulario.addComEspacamento(btnGerenciarEventos, 18);
        painelFormulario.addComEspacamento(btnRelatorios, 18);
        painelFormulario.addComEspacamento(btnNotificacoes, 0);

        janela.setConteudo(painelFormulario);
        janela.setExtendedState(JFrame.MAXIMIZED_BOTH);
        janela.setVisible(true);
    }

    private void abrirJanelaGerenciarAnimais() {
        CustomFrame tela = new CustomFrame("Gestão de Animais - Abrigo Amigos Fofos", 820, 580);

        JPanel painelPrincipal = new JPanel(new BorderLayout(16, 16));
        painelPrincipal.setPreferredSize(new Dimension(780, 520));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));
        painelPrincipal.setOpaque(false);

        CustomLabel lblTitulo = new CustomLabel("GESTÃO DE ANIMAIS", Font.BOLD, 22);

        String[] colunas = {"ID", "Nome", "Espécie", "Raça", "Idade", "Porte", "Saúde", "Status"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Runnable atualizar = () -> {
            modelo.setRowCount(0);
            for (Animal a : inv.getAnimais()) {
                modelo.addRow(new Object[]{
                    String.format("%02d", a.getId()),
                    a.getNome(),
                    a.getEspecie(),
                    a.getRaca(),
                    a.getIdade(),
                    a.getPorte(),
                    a.getEstadoSaude(),
                    a.getStatusAdocao()
                });
            }
        };
        atualizar.run();

        JTable tabela = new JTable(modelo);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setRowHeight(26);
        tabela.getTableHeader().setFont(new Font("Quicksand", Font.BOLD, 12));
        tabela.setFont(new Font("Quicksand", Font.PLAIN, 12));

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 4));
        painelBotoes.setOpaque(false);

        CustomBotao btnCadastrar = new CustomBotao("Cadastrar Animal", e -> {
            abrirFormularioAnimal(tela, null, atualizar);
        });
        btnCadastrar.setFont(new Font("Quicksand", Font.BOLD, 15));

        CustomBotao btnEditar = new CustomBotao("Editar Selecionado", e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(tela, "Selecione um animal na tabela.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = Integer.parseInt(modelo.getValueAt(linha, 0).toString());
            Animal alvo = inv.buscarPorId(id);
            if (alvo != null) {
                abrirFormularioAnimal(tela, alvo, atualizar);
            }
        });
        btnEditar.setFont(new Font("Quicksand", Font.BOLD, 15));

        CustomBotao btnRemover = new CustomBotao("Remover Selecionado", e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(tela, "Selecione um animal na tabela.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = Integer.parseInt(modelo.getValueAt(linha, 0).toString());
            boolean removido = inv.removerAnimal(id);
            if (removido) {
                JOptionPane.showMessageDialog(tela, "Animal removido do sistema.");
                atualizar.run();
            } else {
                JOptionPane.showMessageDialog(tela, "Não é possível remover um animal já adotado.", "Operação Bloqueada", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnRemover.setFont(new Font("Quicksand", Font.BOLD, 15));

        CustomBotao btnFechar = new CustomBotao("Fechar", e -> tela.dispose());
        btnFechar.setFont(new Font("Quicksand", Font.BOLD, 15));

        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnFechar);

        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        painelPrincipal.add(new JScrollPane(tabela), BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        tela.setConteudo(painelPrincipal);
        tela.setVisible(true);
    }

    private void abrirFormularioAnimal(Component parent, Animal existente, Runnable aoSalvar) {
        String[] especies = {"Cachorro", "Gato", "Outros"};
        JComboBox<String> cbEspecie = new JComboBox<>(especies);
        CustomTextField txtNome = new CustomTextField();
        CustomTextField txtRaca = new CustomTextField();
        CustomTextField txtIdade = new CustomTextField();
        CustomTextField txtPorte = new CustomTextField();
        CustomTextField txtSaude = new CustomTextField();
        CustomTextField txtData = new CustomTextField();
        CustomTextField txtStatus = new CustomTextField();

        if (existente != null) {
            cbEspecie.setSelectedItem(existente.getEspecie());
            cbEspecie.setEnabled(false);
            txtNome.setText(existente.getNome());
            txtRaca.setText(existente.getRaca());
            txtIdade.setText(String.valueOf(existente.getIdade()));
            txtPorte.setText(existente.getPorte());
            txtSaude.setText(existente.getEstadoSaude());
            txtData.setText(existente.getDataResgate());
            txtStatus.setText(existente.getStatusAdocao());
        } else {
            txtStatus.setText("Disponível");
        }

        Object[] campos = {
            new CustomLabel("Espécie:"), cbEspecie,
            new CustomLabel("Nome:"), txtNome,
            new CustomLabel("Raça:"), txtRaca,
            new CustomLabel("Idade (anos):"), txtIdade,
            new CustomLabel("Porte:"), txtPorte,
            new CustomLabel("Estado de Saúde:"), txtSaude,
            new CustomLabel("Data de Resgate (dd/mm/aaaa):"), txtData,
            new CustomLabel("Status de Adoção:"), txtStatus
        };

        String titulo = existente == null ? "Cadastrar Animal Resgatado" : "Editar Registro de Animal";
        if (JOptionPane.showConfirmDialog(parent, campos, titulo, JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                String nome = txtNome.getText().trim();
                String raca = txtRaca.getText().trim();
                int idade = Integer.parseInt(txtIdade.getText().trim());
                String porte = txtPorte.getText().trim();
                String saude = txtSaude.getText().trim();
                String data = txtData.getText().trim();
                String status = txtStatus.getText().trim();

                if (nome.isEmpty() || raca.isEmpty() || porte.isEmpty() || saude.isEmpty() || data.isEmpty() || status.isEmpty()) {
                    JOptionPane.showMessageDialog(parent, "Preencha todos os campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (existente == null) {
                    int novoId = 1;
                    for (Animal a : inv.getAnimais()) {
                        if (a.getId() >= novoId) {
                            novoId = a.getId() + 1;
                        }
                    }
                    String esp = (String) cbEspecie.getSelectedItem();
                    Animal novo;
                    if ("Cachorro".equals(esp)) {
                        novo = new Cachorro(novoId, nome, raca, idade, porte, saude, data, status);
                    } else if ("Gato".equals(esp)) {
                        novo = new Gato(novoId, nome, raca, idade, porte, saude, data, status);
                    } else {
                        novo = new Outros(novoId, nome, raca, idade, porte, saude, data, status);
                    }
                    inv.addAnimal(novo);
                } else {
                    inv.editarAnimal(existente.getId(), nome, raca, idade, porte, saude, data, status);
                }
                aoSalvar.run();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(parent, "A idade deve ser um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void abrirJanelaGerenciarUsuarios() {
        CustomFrame tela = new CustomFrame("Gestão de Usuários - Abrigo Amigos Fofos", 760, 540);

        JPanel painelPrincipal = new JPanel(new BorderLayout(16, 16));
        painelPrincipal.setPreferredSize(new Dimension(720, 480));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));
        painelPrincipal.setOpaque(false);

        CustomLabel lblTitulo = new CustomLabel("USUÁRIOS CADASTRADOS NO SISTEMA", Font.BOLD, 22);

        String[] colunas = {"Nome", "E-mail", "CPF", "Perfil"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Runnable atualizar = () -> {
            modelo.setRowCount(0);
            for (Usuario u : sisUser.getTodosUsuarios()) {
                modelo.addRow(new Object[]{
                    u.getNome(),
                    u.getEmail(),
                    u.getCpf(),
                    u.getClass().getSimpleName()
                });
            }
        };
        atualizar.run();

        JTable tabela = new JTable(modelo);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setRowHeight(26);
        tabela.getTableHeader().setFont(new Font("Quicksand", Font.BOLD, 13));
        tabela.setFont(new Font("Quicksand", Font.PLAIN, 13));

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 4));
        painelBotoes.setOpaque(false);

        CustomBotao btnPromover = new CustomBotao("Promover a Admin", e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(tela, "Selecione um usuário.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String email = modelo.getValueAt(linha, 1).toString();
            if (sisUser.promoverParaAdmin(email)) {
                JOptionPane.showMessageDialog(tela, "Usuário promovido a Administrador!");
                atualizar.run();
            } else {
                JOptionPane.showMessageDialog(tela, "Este usuário já é Administrador.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        btnPromover.setFont(new Font("Quicksand", Font.BOLD, 15));

        CustomBotao btnRemover = new CustomBotao("Remover Usuário", e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(tela, "Selecione um usuário.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String email = modelo.getValueAt(linha, 1).toString();
            if (email.equalsIgnoreCase(user.getEmail())) {
                JOptionPane.showMessageDialog(tela, "Você não pode remover sua própria conta logada.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (sisUser.removerUsuario(email)) {
                JOptionPane.showMessageDialog(tela, "Usuário removido.");
                atualizar.run();
            }
        });
        btnRemover.setFont(new Font("Quicksand", Font.BOLD, 15));

        CustomBotao btnFechar = new CustomBotao("Fechar", e -> tela.dispose());
        btnFechar.setFont(new Font("Quicksand", Font.BOLD, 15));

        painelBotoes.add(btnPromover);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnFechar);

        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        painelPrincipal.add(new JScrollPane(tabela), BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        tela.setConteudo(painelPrincipal);
        tela.setVisible(true);
    }

    private void abrirJanelaGerenciarAdocoes() {
        CustomFrame tela = new CustomFrame("Processo de Adoção - Abrigo Amigos Fofos", 820, 560);

        JPanel painelPrincipal = new JPanel(new BorderLayout(16, 16));
        painelPrincipal.setPreferredSize(new Dimension(780, 500));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));
        painelPrincipal.setOpaque(false);

        CustomLabel lblTitulo = new CustomLabel("SOLICITAÇÕES E HISTÓRICO DE ADOÇÕES", Font.BOLD, 22);

        String[] colunas = {"ID Sol.", "ID Animal", "Animal", "Candidato", "CPF", "Status"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Runnable atualizar = () -> {
            modelo.setRowCount(0);
            for (SolicitacaoAdocao sol : inv.getSolicitacoes()) {
                modelo.addRow(new Object[]{
                    String.format("%04d", sol.getIdSolicitacao()),
                    String.format("%02d", sol.getAnimal().getId()),
                    sol.getAnimal().getNome(),
                    sol.getNomeCandidato(),
                    sol.getCpfCandidato(),
                    sol.getStatus()
                });
            }
        };
        atualizar.run();

        JTable tabela = new JTable(modelo);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setRowHeight(26);
        tabela.getTableHeader().setFont(new Font("Quicksand", Font.BOLD, 13));
        tabela.setFont(new Font("Quicksand", Font.PLAIN, 13));

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 4));
        painelBotoes.setOpaque(false);

        CustomBotao btnAprovar = new CustomBotao("Aprovar Adoção", e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(tela, "Selecione uma solicitação.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int idSol = Integer.parseInt(modelo.getValueAt(linha, 0).toString());
            if (inv.aprovarAdocao(idSol, true)) {
                JOptionPane.showMessageDialog(tela, "Adoção aprovada com sucesso!");
                atualizar.run();
            } else {
                JOptionPane.showMessageDialog(tela, "Não foi possível aprovar (já processada ou animal já adotado).", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });
        btnAprovar.setFont(new Font("Quicksand", Font.BOLD, 15));

        CustomBotao btnRejeitar = new CustomBotao("Rejeitar Adoção", e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(tela, "Selecione uma solicitação.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int idSol = Integer.parseInt(modelo.getValueAt(linha, 0).toString());
            if (inv.rejeitarAdocao(idSol, true)) {
                JOptionPane.showMessageDialog(tela, "Solicitação rejeitada.");
                atualizar.run();
            } else {
                JOptionPane.showMessageDialog(tela, "Solicitação já foi processada.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });
        btnRejeitar.setFont(new Font("Quicksand", Font.BOLD, 15));

        CustomBotao btnDevolucao = new CustomBotao("Registrar Devolução", e -> {
            CustomTextField txtIdAnimal = new CustomTextField();
            Object[] msg = {new CustomLabel("ID do Animal devolvido:"), txtIdAnimal};
            if (JOptionPane.showConfirmDialog(tela, msg, "Registrar Devolução", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                try {
                    int idAnimal = Integer.parseInt(txtIdAnimal.getText().trim());
                    if (inv.registrarDevolucao(idAnimal)) {
                        JOptionPane.showMessageDialog(tela, "Devolução registrada! O animal está disponível novamente.");
                        atualizar.run();
                    } else {
                        JOptionPane.showMessageDialog(tela, "Animal não encontrado ou não está com status Adotado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(tela, "ID inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnDevolucao.setFont(new Font("Quicksand", Font.BOLD, 15));

        CustomBotao btnFechar = new CustomBotao("Fechar", e -> tela.dispose());
        btnFechar.setFont(new Font("Quicksand", Font.BOLD, 15));

        painelBotoes.add(btnAprovar);
        painelBotoes.add(btnRejeitar);
        painelBotoes.add(btnDevolucao);
        painelBotoes.add(btnFechar);

        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        painelPrincipal.add(new JScrollPane(tabela), BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        tela.setConteudo(painelPrincipal);
        tela.setVisible(true);
    }

    private void abrirJanelaGerenciarEventos() {
        CustomFrame tela = new CustomFrame("Gestão de Atividades e Eventos", 800, 560);

        JPanel painelPrincipal = new JPanel(new BorderLayout(16, 16));
        painelPrincipal.setPreferredSize(new Dimension(760, 500));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));
        painelPrincipal.setOpaque(false);

        CustomLabel lblTitulo = new CustomLabel("ATIVIDADES E EVENTOS DA ONG", Font.BOLD, 22);

        String[] colunas = {"ID", "Título", "Categoria", "Data", "Local", "Vagas", "Inscritos"};
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
                    a.getVagasDisponiveis(),
                    a.getVoluntariosInscritos().size()
                });
            }
        };
        atualizar.run();

        JTable tabela = new JTable(modelo);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setRowHeight(26);
        tabela.getTableHeader().setFont(new Font("Quicksand", Font.BOLD, 13));
        tabela.setFont(new Font("Quicksand", Font.PLAIN, 13));

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 4));
        painelBotoes.setOpaque(false);

        CustomBotao btnNovoEvento = new CustomBotao("Criar Atividade", e -> {
            String[] tipos = {"Mutirão de Limpeza", "Campanha de Vacinação", "Feira de Adoção", "Evento Beneficente"};
            JComboBox<String> cbTipo = new JComboBox<>(tipos);
            CustomTextField txtTitulo = new CustomTextField();
            CustomTextField txtDesc = new CustomTextField();
            CustomTextField txtData = new CustomTextField();
            CustomTextField txtLocal = new CustomTextField();
            CustomTextField txtVagas = new CustomTextField();

            Object[] campos = {
                new CustomLabel("Tipo de Atividade:"), cbTipo,
                new CustomLabel("Título:"), txtTitulo,
                new CustomLabel("Descrição:"), txtDesc,
                new CustomLabel("Data (dd/mm/aaaa):"), txtData,
                new CustomLabel("Local:"), txtLocal,
                new CustomLabel("Vagas Disponíveis:"), txtVagas
            };

            if (JOptionPane.showConfirmDialog(tela, campos, "Nova Atividade", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                try {
                    String titulo = txtTitulo.getText().trim();
                    String desc = txtDesc.getText().trim();
                    String data = txtData.getText().trim();
                    String local = txtLocal.getText().trim();
                    int vagas = Integer.parseInt(txtVagas.getText().trim());

                    if (titulo.isEmpty() || desc.isEmpty() || data.isEmpty() || local.isEmpty()) {
                        JOptionPane.showMessageDialog(tela, "Preencha todos os campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    int novoId = 1;
                    for (Atividade a : inv.getAtividades()) {
                        if (a.getId() >= novoId) {
                            novoId = a.getId() + 1;
                        }
                    }

                    String sel = (String) cbTipo.getSelectedItem();
                    Atividade nova;
                    if ("Mutirão de Limpeza".equals(sel)) {
                        nova = new MultiraoLimpeza(novoId, titulo, desc, data, local, vagas);
                    } else if ("Campanha de Vacinação".equals(sel)) {
                        nova = new CampanhaVacinacao(novoId, titulo, desc, data, local, vagas);
                    } else if ("Feira de Adoção".equals(sel)) {
                        nova = new FeiraAdocao(novoId, titulo, desc, data, local, vagas);
                    } else {
                        nova = new EventoBeneficente(novoId, titulo, desc, data, local, vagas);
                    }
                    inv.addAtividade(nova);
                    atualizar.run();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(tela, "Vagas deve ser um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnNovoEvento.setFont(new Font("Quicksand", Font.BOLD, 15));

        CustomBotao btnRemoverEvento = new CustomBotao("Remover Atividade", e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(tela, "Selecione uma atividade.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = Integer.parseInt(modelo.getValueAt(linha, 0).toString());
            if (inv.removerAtividade(id)) {
                atualizar.run();
            }
        });
        btnRemoverEvento.setFont(new Font("Quicksand", Font.BOLD, 15));

        CustomBotao btnFechar = new CustomBotao("Fechar", e -> tela.dispose());
        btnFechar.setFont(new Font("Quicksand", Font.BOLD, 15));

        painelBotoes.add(btnNovoEvento);
        painelBotoes.add(btnRemoverEvento);
        painelBotoes.add(btnFechar);

        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        painelPrincipal.add(new JScrollPane(tabela), BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        tela.setConteudo(painelPrincipal);
        tela.setVisible(true);
    }
}