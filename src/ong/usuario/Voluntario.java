package ong.usuario;

import java.util.ArrayList;
import java.util.List;

public class Voluntario extends Usuario {
    private List<Atividade> historico = new ArrayList<>();
    private List<Atividade> atvAtivas = new ArrayList<>();
    private List<Animal> animaisResponsavel = new ArrayList<>();

    public Voluntario(String nome, String email, String senha, String CPF){
        super(nome, email, senha, CPF);
    }

    public List<Atividade> getHistorico(){
        return this.historico;
    }

    public void addAtividade(Atividade atv){
        this.atvAtivas.add(atv);
    }

    public void concluirAtividade(Atividade atv){
        this.historico.add(atv);
        this.atvAtivas.remove(atv);
    }

    public void getAnimaisResponsavel(){
        return this.animaisResponsavel;
    }
}