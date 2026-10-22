package ong.usuario;

import java.util.ArrayList;
import java.util.List;

public class Voluntario extends Usuario {
    //private List<Atividade> historico = new ArrayList<>();
    //private List<Atividade> ativas = new ArrayList<>();
    //private List<Animal> animaisResponsavel = new ArrayList<>();

    public Voluntario(String nome, String email, String senha){
        super(nome, email, senha);
        this.setTipo("VOLUNTARIO");
    }
}