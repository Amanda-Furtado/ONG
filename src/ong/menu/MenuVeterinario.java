package ong.menu;

import ong.UI.*;
import ong.animais.*;
import ong.service.Inventario;
import ong.usuario.Veterinario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MenuVeterinario {
    private Veterinario user;
    private Inventario inv;

    public MenuVeterinario(Veterinario user, Inventario inv) {
        this.user = user;
        this.inv = inv;
    }

    public void mostrarTela() {
        CustomFrame janela = new CustomFrame("Área do Veterinário - Abrigo Amigos Fofos", 720, 720);

        CustomFormPanel painelFormulario = new CustomFormPanel(560, 620);

        CustomLabel lblTitulo = new CustomLabel("Bem-vindo(a) Dr(a). " + user.getNome() + "!", Font.BOLD, 24);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        CustomBotao btnConsultarAnimais = new CustomBotao("Consultar Animais e Prontuários", e -> {
            abrirJanelaConsultarAnimais();
        });

        CustomBotao btnRegistrarAtendimento = new CustomBotao("Registrar Consulta", e -> {
            abrirJanelaRegistrarConsulta();
        });

        CustomBotao btnRegistrarProcedimento = new CustomBotao("Registrar Cirurgia / Tratamento", e -> {
            abrirJanelaRegistrarProcedimento();
        });

        CustomBotao btnAtualizarTratamento = new CustomBotao("Atualizar Tratamento e Saúde", e -> {
            abrirJanelaAtualizarTratamento();
        });

        CustomBotao btnRegistrarVacinacao = new CustomBotao("Registrar Vacinação", e -> {
            abrirJanelaRegistrarVacinacao();
        });

        CustomBotao btnEmitirRelatorioMedico = new CustomBotao("Emitir Relatório Médico", e -> {
            abrirJanelaEmitirRelatorio();
        });

        CustomBotao btnNotificacoes = new CustomBotao("Notificações", e -> {
            new MenuNotificacoes(user, inv).mostrarTela();
        });

        btnConsultarAnimais.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegistrarAtendimento.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegistrarProcedimento.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAtualizarTratamento.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegistrarVacinacao.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEmitirRelatorioMedico.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnNotificacoes.setAlignmentX(Component.CENTER_ALIGNMENT);

        painelFormulario.addComEspacamento(lblTitulo, 32);
        painelFormulario.addComEspacamento(btnConsultarAnimais, 16);
        painelFormulario.addComEspacamento(btnRegistrarAtendimento, 16);
        painelFormulario.addComEspacamento(btnRegistrarProcedimento, 16);
        painelFormulario.addComEspacamento(btnAtualizarTratamento, 16);
        painelFormulario.addComEspacamento(btnRegistrarVacinacao, 16);
        painelFormulario.addComEspacamento(btnEmitirRelatorioMedico, 16);
        painelFormulario.addComEspacamento(btnNotificacoes, 0);

        janela.setConteudo(painelFormulario);
        janela.setExtendedState(JFrame.MAXIMIZED_BOTH);
        janela.setVisible(true);
    }

    private void abrirJanelaConsultarAnimais() {
        CustomFrame tela = new CustomFrame("Prontuários dos Animais - Abrigo Amigos Fofos", 760, 560);

        JPanel painelPrincipal = new JPanel(new BorderLayout(16, 16));
        painelPrincipal.setPreferredSize(new Dimension(720, 500));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));
        painelPrincipal.setOpaque(false);

        CustomLabel lblTitulo = new CustomLabel("ANIMAIS CADASTRADOS NO ABRIGO", Font.BOLD, 22);

        String[] colunas = {"ID", "Nome", "Espécie", "Raça", "Estado de Saúde", "Status Adoção"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Animal a : inv.getAnimais()) {
            modelo.addRow(new Object[]{
                String.format("%02d", a.getId()),
                a.getNome(),
                a.getEspecie(),
                a.getRaca(),
                a.getEstadoSaude(),
                a.getStatusAdocao()
            });
        }

        JTable tabela = new JTable(modelo);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setRowHeight(26);
        tabela.getTableHeader().setFont(new Font("Quicksand", Font.BOLD, 13));
        tabela.setFont(new Font("Quicksand", Font.PLAIN, 13));

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 4));
        painelBotoes.setOpaque(false);

        CustomBotao btnProntuario = new CustomBotao("Ver Histórico Médico Completo", e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(tela, "Selecione um animal na tabela.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = Integer.parseInt(modelo.getValueAt(linha, 0).toString());
            exibirProntuarioCompleto(tela, id);
        });
        btnProntuario.setFont(new Font("Quicksand", Font.BOLD, 16));

        CustomBotao btnFechar = new CustomBotao("Fechar", e -> tela.dispose());
        btnFechar.setFont(new Font("Quicksand", Font.BOLD, 16));

        painelBotoes.add(btnProntuario);
        painelBotoes.add(btnFechar);

        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        painelPrincipal.add(new JScrollPane(tabela), BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        tela.setConteudo(painelPrincipal);
        tela.setVisible(true);
    }

    private void abrirJanelaRegistrarConsulta() {
        CustomFrame tela = new CustomFrame("Registrar Consulta Veterinária", 560, 620);
        CustomFormPanel painel = new CustomFormPanel(460, 540);

        CustomLabel lblTitulo = new CustomLabel("Registrar Consulta", Font.BOLD, 24);
        CustomTextField txtIdAnimal = new CustomTextField();
        CustomTextField txtData = new CustomTextField();
        CustomTextField txtDiagnostico = new CustomTextField();
        CustomTextField txtPrescricao = new CustomTextField();
        CustomTextField txtNovoEstado = new CustomTextField();

        CustomBotao btnSalvar = new CustomBotao("Salvar Consulta", e -> {
            try {
                int idAnimal = Integer.parseInt(txtIdAnimal.getText().trim());
                String data = txtData.getText().trim();
                String diag = txtDiagnostico.getText().trim();
                String presc = txtPrescricao.getText().trim();
                String estado = txtNovoEstado.getText().trim();

                if (data.isEmpty() || diag.isEmpty() || presc.isEmpty() || estado.isEmpty()) {
                    JOptionPane.showMessageDialog(tela, "Preencha todos os campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int idConsulta = (int) (System.currentTimeMillis() % 10000);
                boolean ok = inv.registrarConsulta(idConsulta, idAnimal, user, data, diag, presc, estado);
                if (ok) {
                    JOptionPane.showMessageDialog(tela, "Consulta #" + idConsulta + " registrada com sucesso!");
                    tela.dispose();
                } else {
                    JOptionPane.showMessageDialog(tela, "Animal não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(tela, "ID do animal inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        CustomBotao btnCancelar = new CustomBotao("Cancelar", e -> tela.dispose());

        painel.addComEspacamento(lblTitulo, 20);
        painel.addComEspacamento(new CustomLabel("ID do Animal:"), 4);
        painel.addComEspacamento(txtIdAnimal, 12);
        painel.addComEspacamento(new CustomLabel("Data (dd/mm/aaaa):"), 4);
        painel.addComEspacamento(txtData, 12);
        painel.addComEspacamento(new CustomLabel("Diagnóstico:"), 4);
        painel.addComEspacamento(txtDiagnostico, 12);
        painel.addComEspacamento(new CustomLabel("Prescrição / Medicamentos:"), 4);
        painel.addComEspacamento(txtPrescricao, 12);
        painel.addComEspacamento(new CustomLabel("Novo Estado de Saúde (ex: Saudável, Tratamento Critico):"), 4);
        painel.addComEspacamento(txtNovoEstado, 20);
        painel.addComEspacamento(btnSalvar, 10);
        painel.addComEspacamento(btnCancelar, 0);

        tela.setConteudo(painel);
        tela.setVisible(true);
    }

    private void abrirJanelaRegistrarProcedimento() {
        CustomFrame tela = new CustomFrame("Registrar Cirurgia ou Tratamento", 560, 620);
        CustomFormPanel painel = new CustomFormPanel(460, 540);

        CustomLabel lblTitulo = new CustomLabel("Registrar Cirurgia / Tratamento", Font.BOLD, 22);
        CustomTextField txtIdAnimal = new CustomTextField();
        CustomTextField txtTipo = new CustomTextField();
        CustomTextField txtDescricao = new CustomTextField();
        CustomTextField txtData = new CustomTextField();
        CustomTextField txtStatus = new CustomTextField();

        CustomBotao btnSalvar = new CustomBotao("Registrar Procedimento", e -> {
            try {
                int idAnimal = Integer.parseInt(txtIdAnimal.getText().trim());
                String tipo = txtTipo.getText().trim();
                String desc = txtDescricao.getText().trim();
                String data = txtData.getText().trim();
                String status = txtStatus.getText().trim();

                if (tipo.isEmpty() || desc.isEmpty() || data.isEmpty() || status.isEmpty()) {
                    JOptionPane.showMessageDialog(tela, "Preencha todos os campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int idProc = (int) (System.currentTimeMillis() % 10000);
                boolean ok = inv.registrarProcedimento(idProc, idAnimal, user, tipo, desc, data, status);
                if (ok) {
                    JOptionPane.showMessageDialog(tela, "Procedimento #" + idProc + " registrado com sucesso!");
                    tela.dispose();
                } else {
                    JOptionPane.showMessageDialog(tela, "Animal não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(tela, "ID do animal inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        CustomBotao btnCancelar = new CustomBotao("Cancelar", e -> tela.dispose());

        painel.addComEspacamento(lblTitulo, 20);
        painel.addComEspacamento(new CustomLabel("ID do Animal:"), 4);
        painel.addComEspacamento(txtIdAnimal, 12);
        painel.addComEspacamento(new CustomLabel("Tipo (ex: Cirurgia, Tratamento):"), 4);
        painel.addComEspacamento(txtTipo, 12);
        painel.addComEspacamento(new CustomLabel("Descrição:"), 4);
        painel.addComEspacamento(txtDescricao, 12);
        painel.addComEspacamento(new CustomLabel("Data (dd/mm/aaaa):"), 4);
        painel.addComEspacamento(txtData, 12);
        painel.addComEspacamento(new CustomLabel("Status (ex: Em andamento, Concluído):"), 4);
        painel.addComEspacamento(txtStatus, 20);
        painel.addComEspacamento(btnSalvar, 10);
        painel.addComEspacamento(btnCancelar, 0);

        tela.setConteudo(painel);
        tela.setVisible(true);
    }

    private void abrirJanelaAtualizarTratamento() {
        CustomFrame tela = new CustomFrame("Atualizar Tratamento e Saúde", 560, 560);
        CustomFormPanel painel = new CustomFormPanel(460, 480);

        CustomLabel lblTitulo = new CustomLabel("Atualizar Tratamento", Font.BOLD, 24);
        CustomTextField txtIdAnimal = new CustomTextField();
        CustomTextField txtIdProc = new CustomTextField();
        CustomTextField txtNovoStatusProc = new CustomTextField();
        CustomTextField txtNovoEstadoSaude = new CustomTextField();

        CustomBotao btnAtualizar = new CustomBotao("Atualizar", e -> {
            try {
                int idAnimal = Integer.parseInt(txtIdAnimal.getText().trim());
                int idProc = Integer.parseInt(txtIdProc.getText().trim());
                String statusProc = txtNovoStatusProc.getText().trim();
                String estadoSaude = txtNovoEstadoSaude.getText().trim();

                if (statusProc.isEmpty() || estadoSaude.isEmpty()) {
                    JOptionPane.showMessageDialog(tela, "Preencha todos os campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                boolean ok = inv.atualizarStatusTratamento(idAnimal, idProc, user, statusProc, estadoSaude);
                if (ok) {
                    JOptionPane.showMessageDialog(tela, "Tratamento e estado de saúde atualizados!");
                    tela.dispose();
                } else {
                    JOptionPane.showMessageDialog(tela, "Animal ou procedimento não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(tela, "IDs devem ser numéricos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        CustomBotao btnCancelar = new CustomBotao("Cancelar", e -> tela.dispose());

        painel.addComEspacamento(lblTitulo, 20);
        painel.addComEspacamento(new CustomLabel("ID do Animal:"), 4);
        painel.addComEspacamento(txtIdAnimal, 12);
        painel.addComEspacamento(new CustomLabel("ID do Procedimento:"), 4);
        painel.addComEspacamento(txtIdProc, 12);
        painel.addComEspacamento(new CustomLabel("Novo Status do Procedimento:"), 4);
        painel.addComEspacamento(txtNovoStatusProc, 12);
        painel.addComEspacamento(new CustomLabel("Novo Estado de Saúde do Animal:"), 4);
        painel.addComEspacamento(txtNovoEstadoSaude, 20);
        painel.addComEspacamento(btnAtualizar, 10);
        painel.addComEspacamento(btnCancelar, 0);

        tela.setConteudo(painel);
        tela.setVisible(true);
    }

    private void abrirJanelaRegistrarVacinacao() {
        CustomFrame tela = new CustomFrame("Registrar Vacinação", 560, 620);
        CustomFormPanel painel = new CustomFormPanel(460, 540);

        CustomLabel lblTitulo = new CustomLabel("Controle de Vacinas", Font.BOLD, 24);
        CustomTextField txtIdAnimal = new CustomTextField();
        CustomTextField txtNomeVacina = new CustomTextField();
        CustomTextField txtDose = new CustomTextField();
        CustomTextField txtDataAplicacao = new CustomTextField();
        CustomTextField txtProximaDose = new CustomTextField();

        CustomBotao btnSalvar = new CustomBotao("Registrar Vacina", e -> {
            try {
                int idAnimal = Integer.parseInt(txtIdAnimal.getText().trim());
                String vacina = txtNomeVacina.getText().trim();
                String dose = txtDose.getText().trim();
                String dataAp = txtDataAplicacao.getText().trim();
                String proxData = txtProximaDose.getText().trim();

                if (vacina.isEmpty() || dose.isEmpty() || dataAp.isEmpty() || proxData.isEmpty()) {
                    JOptionPane.showMessageDialog(tela, "Preencha todos os campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int idVacina = (int) (System.currentTimeMillis() % 10000);
                boolean ok = inv.registrarVacinacao(idVacina, idAnimal, user, vacina, dose, dataAp, proxData);
                if (ok) {
                    JOptionPane.showMessageDialog(tela, "Vacina registrada com sucesso!");
                    tela.dispose();
                } else {
                    JOptionPane.showMessageDialog(tela, "Erro ao registrar (animal inexistente ou vacina duplicada nesta data).", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(tela, "ID do animal inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        CustomBotao btnCancelar = new CustomBotao("Cancelar", e -> tela.dispose());

        painel.addComEspacamento(lblTitulo, 20);
        painel.addComEspacamento(new CustomLabel("ID do Animal:"), 4);
        painel.addComEspacamento(txtIdAnimal, 12);
        painel.addComEspacamento(new CustomLabel("Nome da Vacina:"), 4);
        painel.addComEspacamento(txtNomeVacina, 12);
        painel.addComEspacamento(new CustomLabel("Dose (ex: 1ª Dose, Reforço):"), 4);
        painel.addComEspacamento(txtDose, 12);
        painel.addComEspacamento(new CustomLabel("Data de Aplicação (dd/mm/aaaa):"), 4);
        painel.addComEspacamento(txtDataAplicacao, 12);
        painel.addComEspacamento(new CustomLabel("Data da Próxima Dose (dd/mm/aaaa):"), 4);
        painel.addComEspacamento(txtProximaDose, 20);
        painel.addComEspacamento(btnSalvar, 10);
        painel.addComEspacamento(btnCancelar, 0);

        tela.setConteudo(painel);
        tela.setVisible(true);
    }

    private void abrirJanelaEmitirRelatorio() {
        CustomTextField txtId = new CustomTextField();
        Object[] msg = {new CustomLabel("Informe o ID do Animal para emitir o relatório de saúde:"), txtId};
        if (JOptionPane.showConfirmDialog(null, msg, "Emitir Relatório Médico", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                exibirProntuarioCompleto(null, id);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "ID inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void exibirProntuarioCompleto(Component parent, int idAnimal) {
        Animal animal = inv.buscarPorId(idAnimal);
        if (animal == null) {
            JOptionPane.showMessageDialog(parent, "Animal não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("PRONTUÁRIO MÉDICO - ").append(animal.getNome().toUpperCase()).append("\n");
        sb.append("ID: ").append(String.format("%02d", animal.getId()))
          .append(" | Espécie: ").append(animal.getEspecie())
          .append(" | Raça: ").append(animal.getRaca()).append("\n");
        sb.append("Estado de Saúde Atual: ").append(animal.getEstadoSaude()).append("\n\n");

        sb.append("=== CONSULTAS E MEDICAMENTOS ===\n");
        if (animal.getHistoricoConsultas().isEmpty()) {
            sb.append("Nenhuma consulta registrada.\n");
        } else {
            for (Consulta c : animal.getHistoricoConsultas()) {
                sb.append("#").append(c.getIdConsulta()).append(" [").append(c.getData()).append("] Dr(a). ")
                  .append(c.getVeterinario().getNome()).append("\nDiagnóstico: ").append(c.getDiagnostico())
                  .append(" | Prescrição: ").append(c.getPrescricao()).append("\n\n");
            }
        }

        sb.append("=== CIRURGIAS E TRATAMENTOS ===\n");
        if (animal.getHistoricoProcedimentos().isEmpty()) {
            sb.append("Nenhum procedimento registrado.\n");
        } else {
            for (ProcedimentoMedico p : animal.getHistoricoProcedimentos()) {
                sb.append("#").append(p.getIdProcedimento()).append(" [").append(p.getData()).append("] ")
                  .append(p.getTipo()).append(" - ").append(p.getStatus()).append("\nDescrição: ")
                  .append(p.getDescricao()).append(" (Dr(a). ").append(p.getVeterinario().getNome()).append(")\n\n");
            }
        }

        sb.append("=== CARTEIRA DE VACINAÇÃO ===\n");
        if (animal.getCarteiraVacinacao().isEmpty()) {
            sb.append("Nenhuma vacina registrada.\n");
        } else {
            for (Vacina v : animal.getCarteiraVacinacao()) {
                sb.append("#").append(v.getIdVacina()).append(" ").append(v.getNomeVacina())
                  .append(" (").append(v.getDose()).append(") - Aplicada: ").append(v.getDataAplicacao())
                  .append(" | Próxima: ").append(v.getDataProximaDose()).append("\n");
            }
        }

        JTextArea area = new JTextArea(sb.toString(), 22, 52);
        area.setFont(new Font("Quicksand", Font.PLAIN, 13));
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        JOptionPane.showMessageDialog(parent, new JScrollPane(area), "Relatório de Saúde", JOptionPane.INFORMATION_MESSAGE);
    }
}