package ong.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import ong.animais.*;
import ong.eventos.*;
import ong.sistema.SistemaUsuario;
import ong.usuario.*;

public class Inventario {
    private ArrayList<Animal> animais;
    private ArrayList<SolicitacaoAdocao> solicitacoes;
    private ArrayList<Atividade> atividades;
    private ArrayList<Consulta> historicoConsultas = new ArrayList<>();
    private ArrayList<Notificacao> notificacoes = new ArrayList<>();
    private int contadorNotificacoes = 1;

    public Inventario() {
        animais = new ArrayList<>();
        solicitacoes = new ArrayList<>();
        atividades = new ArrayList<>();
    }

    private String dataHoje() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public void registrarNotificacao(String categoria, String mensagem, String data, String cpfDestinatario) {
        notificacoes.add(new Notificacao(contadorNotificacoes++, categoria, mensagem, data, cpfDestinatario));
    }

    public ArrayList<Notificacao> getNotificacoesParaUsuario(Usuario user) {
        ArrayList<Notificacao> resultado = new ArrayList<>();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate hoje = LocalDate.now();

        for (Animal a : animais) {
            for (Vacina v : a.getCarteiraVacinacao()) {
                try {
                    LocalDate dataProx = LocalDate.parse(v.getDataProximaDose(), fmt);
                    long diasRestantes = ChronoUnit.DAYS.between(hoje, dataProx);
                    if (diasRestantes <= 30) {
                        String textoDias = diasRestantes < 0
                            ? "vencida há " + Math.abs(diasRestantes) + " dia(s)"
                            : "vence em " + diasRestantes + " dia(s) (" + v.getDataProximaDose() + ")";
                        resultado.add(new Notificacao(
                            0,
                            "Vacina Próxima do Vencimento",
                            "Animal " + a.getNome() + " (ID " + String.format("%02d", a.getId()) + ") - Vacina " + v.getNomeVacina() + " " + textoDias + ".",
                            dataHoje(),
                            null
                        ));
                    }
                } catch (Exception ignored) {
                }
            }
        }

        for (int i = notificacoes.size() - 1; i >= 0; i--) {
            Notificacao n = notificacoes.get(i);
            if (n.getCpfDestinatario() == null ||
                n.getCpfDestinatario().equals(user.getCpf()) ||
                user instanceof Administrador) {
                resultado.add(n);
            }
        }

        return resultado;
    }

    public ArrayList<Animal> getAnimais() {
        return this.animais;
    }

    public ArrayList<SolicitacaoAdocao> getSolicitacoes() {
        return this.solicitacoes;
    }

    public ArrayList<Atividade> getAtividades() {
        return this.atividades;
    }

    public void addAtividade(Atividade atividade) {
        atividades.add(atividade);
        registrarNotificacao(
            "Próximo Evento",
            "Nova atividade agendada: " + atividade.getTitulo() + " (" + atividade.getTipo() + ") em " + atividade.getData() + " - Local: " + atividade.getLocal() + ".",
            dataHoje(),
            null
        );
    }

    public boolean removerAtividade(int id) {
        Atividade alvo = buscarAtividadePorId(id);
        if (alvo != null) {
            atividades.remove(alvo);
            return true;
        }
        return false;
    }

    public Atividade buscarAtividadePorId(int id) {
        for (Atividade a : atividades) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    public void addAnimal(Animal animal) {
        animais.add(animal);
        registrarNotificacao(
            "Novo Animal Cadastrado",
            "Novo animal resgatado: " + animal.getNome() + " (" + animal.getEspecie() + " - " + animal.getRaca() + ").",
            animal.getDataResgate(),
            null
        );
    }

    public Animal buscarPorId(int id) {
        for (Animal animal : animais) {
            if (animal.getId() == id) {
                return animal;
            }
        }
        return null;
    }

    public boolean removerAnimal(int id) {
        Animal animal = buscarPorId(id);
        if (animal != null) {
            if (animal.getStatusAdocao().equalsIgnoreCase("Adotado")) {
                return false;
            }
            animais.remove(animal);
            return true;
        }
        return false;
    }

    public boolean editarAnimal(int id, String novoNome, String novaRaca, int novaIdade,
                                String novoPorte, String novoEstadoSaude, String novaDataResgate,
                                String novoStatusAdocao) {
        Animal animal = buscarPorId(id);
        if (animal != null) {
            animal.setNome(novoNome);
            animal.setRaca(novaRaca);
            animal.setIdade(novaIdade);
            animal.setPorte(novoPorte);
            animal.setEstadoSaude(novoEstadoSaude);
            animal.setDataResgate(novaDataResgate);
            animal.setStatusAdocao(novoStatusAdocao);
            return true;
        }
        return false;
    }

    public ArrayList<Animal> filtrarPorEspecie(String especie) {
        ArrayList<Animal> resultados = new ArrayList<>();
        for (Animal animal : animais) {
            if (especie.equalsIgnoreCase("Cachorro") && animal instanceof Cachorro) {
                resultados.add(animal);
            } else if (especie.equalsIgnoreCase("Gato") && animal instanceof Gato) {
                resultados.add(animal);
            }
        }
        return resultados;
    }

    public ArrayList<Animal> buscarPorNome(String termoBusca) {
        ArrayList<Animal> resultados = new ArrayList<>();
        for (Animal animal : animais) {
            if (animal.getNome().toLowerCase().contains(termoBusca.toLowerCase())) {
                resultados.add(animal);
            }
        }
        return resultados;
    }

    public void displayInventario() {
        for (Animal animal : animais) {
            System.out.println(animal.toString());
        }
    }

    public boolean solicitarAdocao(int idSolicitacao, int idAnimal, String nomeCandidato, String cpfCandidato) {
        Animal animal = buscarPorId(idAnimal);
        if (animal == null) {
            throw new IllegalArgumentException("Erro: Animal nao encontrado.");
        }

        if (animal.getEstadoSaude().equalsIgnoreCase("Critico") || animal.getEstadoSaude().equalsIgnoreCase("Tratamento Critico")) {
            throw new IllegalStateException("Erro: O animal " + animal.getNome() + " esta em tratamento critico e nao pode ser adotado.");
        }

        if (!animal.getStatusAdocao().equalsIgnoreCase("Disponivel") && !animal.getStatusAdocao().equalsIgnoreCase("Disponível")) {
            throw new IllegalStateException("Erro: O animal " + animal.getNome() + " esta indisponivel para adocao (Status: " + animal.getStatusAdocao() + ").");
        }

        SolicitacaoAdocao novaSol = new SolicitacaoAdocao(idSolicitacao, animal, nomeCandidato, cpfCandidato);
        solicitacoes.add(novaSol);
        registrarNotificacao(
            "Atualização de Adoção",
            "Solicitação #" + String.format("%04d", idSolicitacao) + " recebida para " + animal.getNome() + " (Candidato: " + nomeCandidato + ") - Status: Pendente.",
            dataHoje(),
            cpfCandidato
        );
        return true;
    }

    public ArrayList<SolicitacaoAdocao> buscarSolicitacoesPorCpf(String cpf) {
        ArrayList<SolicitacaoAdocao> resultados = new ArrayList<>();
        for (SolicitacaoAdocao sol : solicitacoes) {
            if (sol.getCpfCandidato().equals(cpf)) {
                resultados.add(sol);
            }
        }
        return resultados;
    }

    public SolicitacaoAdocao buscarSolicitacaoPorId(int idSolicitacao) {
        for (SolicitacaoAdocao sol : solicitacoes) {
            if (sol.getIdSolicitacao() == idSolicitacao) {
                return sol;
            }
        }
        return null;
    }

    public boolean aprovarAdocao(int idSolicitacao, boolean isAdmin) {
        if (!isAdmin) {
            return false;
        }

        SolicitacaoAdocao sol = buscarSolicitacaoPorId(idSolicitacao);
        if (sol != null && sol.getStatus().equalsIgnoreCase("Pendente")) {
            if (sol.getAnimal().getStatusAdocao().equalsIgnoreCase("Adotado")) {
                return false;
            }
            sol.setStatus("Aprovado");
            sol.getAnimal().setStatusAdocao("Adotado");
            registrarNotificacao(
                "Atualização de Adoção",
                "Solicitação #" + String.format("%04d", sol.getIdSolicitacao()) + " para " + sol.getAnimal().getNome() + " foi APROVADA!",
                dataHoje(),
                sol.getCpfCandidato()
            );

            for (SolicitacaoAdocao outra : solicitacoes) {
                if (outra.getIdSolicitacao() != idSolicitacao &&
                    outra.getAnimal().getId() == sol.getAnimal().getId() &&
                    outra.getStatus().equalsIgnoreCase("Pendente")) {
                    outra.setStatus("Rejeitado");
                    registrarNotificacao(
                        "Atualização de Adoção",
                        "Solicitação #" + String.format("%04d", outra.getIdSolicitacao()) + " para " + outra.getAnimal().getNome() + " foi REJEITADA (animal adotado).",
                        dataHoje(),
                        outra.getCpfCandidato()
                    );
                }
            }
            return true;
        }
        return false;
    }

    public boolean rejeitarAdocao(int idSolicitacao, boolean isAdmin) {
        if (!isAdmin) {
            return false;
        }

        SolicitacaoAdocao sol = buscarSolicitacaoPorId(idSolicitacao);
        if (sol != null && sol.getStatus().equalsIgnoreCase("Pendente")) {
            sol.setStatus("Rejeitado");
            registrarNotificacao(
                "Atualização de Adoção",
                "Solicitação #" + String.format("%04d", sol.getIdSolicitacao()) + " para " + sol.getAnimal().getNome() + " foi REJEITADA.",
                dataHoje(),
                sol.getCpfCandidato()
            );
            return true;
        }
        return false;
    }

    public boolean registrarDevolucao(int idAnimal) {
        Animal animal = buscarPorId(idAnimal);
        if (animal != null && animal.getStatusAdocao().equalsIgnoreCase("Adotado")) {
            animal.setStatusAdocao("Disponível");
            registrarNotificacao(
                "Aviso Geral do Abrigo",
                "O animal " + animal.getNome() + " (ID " + String.format("%02d", animal.getId()) + ") retornou ao abrigo e está disponível para adoção.",
                dataHoje(),
                null
            );
            return true;
        }
        return false;
    }

    public void adicionarConsulta(Consulta consulta) {
        this.historicoConsultas.add(consulta);
    }

    public ArrayList<Consulta> getHistoricoConsultas() {
        return historicoConsultas;
    }

    public boolean registrarConsulta(int idConsulta, int idAnimal, Usuario usuarioLogado, String data, String diagnostico, String prescricao, String novoEstadoSaude) {
        if (!(usuarioLogado instanceof Veterinario)) {
            return false;
        }

        Animal animal = buscarPorId(idAnimal);
        if (animal != null) {
            Veterinario vet = (Veterinario) usuarioLogado;
            Consulta novaConsulta = new Consulta(idConsulta, animal, vet, data, diagnostico, prescricao);
            animal.adicionarConsulta(novaConsulta);
            historicoConsultas.add(novaConsulta);
            animal.setEstadoSaude(novoEstadoSaude);
            return true;
        }
        return false;
    }

    public boolean registrarProcedimento(int idProcedimento, int idAnimal, Usuario usuarioLogado, String tipo, String descricao, String data, String status) {
        if (!(usuarioLogado instanceof Veterinario)) {
            return false;
        }

        Animal animal = buscarPorId(idAnimal);
        if (animal != null) {
            Veterinario vet = (Veterinario) usuarioLogado;
            ProcedimentoMedico proc = new ProcedimentoMedico(idProcedimento, animal, vet, tipo, descricao, data, status);
            animal.adicionarProcedimento(proc);
            return true;
        }
        return false;
    }

    public boolean atualizarStatusTratamento(int idAnimal, int idProcedimento, Usuario usuarioLogado, String novoStatus, String novoEstadoSaude) {
        if (!(usuarioLogado instanceof Veterinario)) {
            return false;
        }

        Animal animal = buscarPorId(idAnimal);
        if (animal != null) {
            for (ProcedimentoMedico p : animal.getHistoricoProcedimentos()) {
                if (p.getIdProcedimento() == idProcedimento) {
                    p.setStatus(novoStatus);
                    animal.setEstadoSaude(novoEstadoSaude);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean registrarVacinacao(int idVacina, int idAnimal, Usuario usuarioLogado, String nomeVacina, String dose, String dataAplicacao, String dataProximaDose) {
        if (!(usuarioLogado instanceof Veterinario)) {
            return false;
        }

        Animal animal = buscarPorId(idAnimal);
        if (animal != null) {
            for (Vacina v : animal.getCarteiraVacinacao()) {
                if (v.getNomeVacina().equalsIgnoreCase(nomeVacina) && v.getDataAplicacao().equals(dataAplicacao)) {
                    return false;
                }
            }

            Veterinario vet = (Veterinario) usuarioLogado;
            Vacina novaVacina = new Vacina(idVacina, animal, vet, nomeVacina, dose, dataAplicacao, dataProximaDose);
            animal.adicionarVacina(novaVacina);
            return true;
        }
        return false;
    }

    public int getQuantidadeAnimaisResgatados() {
        return animais.size();
    }

    public int getQuantidadeAdocoesRealizadas() {
        int total = 0;
        for (SolicitacaoAdocao sol : solicitacoes) {
            if (sol.getStatus().equalsIgnoreCase("Aprovado")) {
                total++;
            }
        }
        return total;
    }

    public String getEspecieMaisResgatada() {
        if (animais.isEmpty()) {
            return "Nenhum animal cadastrado";
        }
        int caes = 0;
        int gatos = 0;
        int outros = 0;
        for (Animal a : animais) {
            if (a instanceof Cachorro) {
                caes++;
            } else if (a instanceof Gato) {
                gatos++;
            } else {
                outros++;
            }
        }
        if (caes >= gatos && caes >= outros) {
            return "Cachorro (Cachorros: " + caes + " | Gatos: " + gatos + " | Outros: " + outros + ")";
        } else if (gatos >= caes && gatos >= outros) {
            return "Gato (Cachorros: " + caes + " | Gatos: " + gatos + " | Outros: " + outros + ")";
        } else {
            return "Outros (Cachorros: " + caes + " | Gatos: " + gatos + " | Outros: " + outros + ")";
        }
    }

    public double calcularTaxaAdocaoMensal(int totalMesesPeriodo) {
        if (totalMesesPeriodo <= 0 || animais.isEmpty()) {
            return 0.0;
        }
        double mediaMensal = (double) getQuantidadeAdocoesRealizadas() / totalMesesPeriodo;
        return (mediaMensal / animais.size()) * 100.0;
    }

    public int getNumeroVoluntariosAtivos(SistemaUsuario sisUser) {
        return sisUser.contarVoluntariosAtivos();
    }

    public String gerarRelatorioGeral(SistemaUsuario sisUser, int mesesOperacao) {
        String relatorio = "=== RELATÓRIO E ESTATÍSTICAS DA ONG ===\n\n" +
            "- Quantidade de animais resgatados: " + getQuantidadeAnimaisResgatados() + "\n" +
            "- Quantidade de adoções realizadas: " + getQuantidadeAdocoesRealizadas() + "\n" +
            "- Espécie mais resgatada: " + getEspecieMaisResgatada() + "\n" +
            String.format("- Taxa de adoção mensal (%d meses): %.2f%%\n", mesesOperacao, calcularTaxaAdocaoMensal(mesesOperacao)) +
            "- Número de voluntários ativos: " + getNumeroVoluntariosAtivos(sisUser) + "\n\n" +
            "--- Animais em Tratamento ---\n";

        boolean temTratamento = false;
        for (Animal a : animais) {
            String saude = a.getEstadoSaude().toLowerCase();
            String status = a.getStatusAdocao().toLowerCase();
            if (saude.contains("tratamento") || saude.contains("recupera") || saude.contains("critico") || status.contains("tratamento")) {
                relatorio += "- ID " + a.getId() + " | " + a.getNome() + " (" + a.getEspecie() + ") | Estado: " + a.getEstadoSaude() + "\n";
                temTratamento = true;
            }
        }
        if (!temTratamento) {
            relatorio += "Nenhum animal em tratamento no momento.\n";
        }
        return relatorio;
    }
}