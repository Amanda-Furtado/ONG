package ong.service;

import java.util.ArrayList;
import ong.animais.*;
import ong.eventos.*;
import ong.usuario.*;

public class Inventario {
    private ArrayList<Animal> animais;
    private ArrayList<SolicitacaoAdocao> solicitacoes;
    private ArrayList<Atividade> atividades;
    private ArrayList<Consulta> historicoConsultas = new ArrayList<>();

    public Inventario() {
        animais = new ArrayList<>();
        solicitacoes = new ArrayList<>();
        atividades = new ArrayList<>();
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
            for (SolicitacaoAdocao outra : solicitacoes) {
                if (outra.getIdSolicitacao() != idSolicitacao &&
                    outra.getAnimal().getId() == sol.getAnimal().getId() &&
                    outra.getStatus().equalsIgnoreCase("Pendente")) {
                    outra.setStatus("Rejeitado");
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
            return true;
        }
        return false;
    }

    public boolean registrarDevolucao(int idAnimal) {
        Animal animal = buscarPorId(idAnimal);
        if (animal != null && animal.getStatusAdocao().equalsIgnoreCase("Adotado")) {
            animal.setStatusAdocao("Disponível");
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
}