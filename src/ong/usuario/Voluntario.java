package ong.usuario;

import ong.animais.Animal;
import ong.eventos.Atividade;
import java.util.ArrayList;
import java.util.List;

public class Voluntario extends Usuario {
    private List<Atividade> historico = new ArrayList<>();
    private List<Atividade> atvAtivas = new ArrayList<>();
    private List<Animal> animaisResponsavel = new ArrayList<>();

    public Voluntario(String nome, String email, String senha, String CPF) {
        super(nome, email, senha, CPF);
    }

    public List<Atividade> getHistorico() {
        return this.historico;
    }

    public List<Atividade> getAtvAtivas() {
        return this.atvAtivas;
    }

    public void addAtividadeAtiva(Atividade atv) {
        this.atvAtivas.add(atv);
    }

    public void concluirAtividadeAtiva(Atividade atv) {
        if (this.atvAtivas.contains(atv)) {
            this.historico.add(atv);
            this.atvAtivas.remove(atv);
        }
    }

    public void addAnimalResponsavel(Animal animal) {
        if (!this.animaisResponsavel.contains(animal)) {
            this.animaisResponsavel.add(animal);
        }
    }

    public List<Animal> getAnimaisResponsavel() {
        return this.animaisResponsavel;
    }
}