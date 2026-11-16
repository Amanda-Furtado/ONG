package ong.eventos;


import ong.usuario.Voluntario;

import java.util.ArrayList;
import java.util.List;


public abstract class Atividade{
    private int id;
    private String titulo;
    private String descricao;
    private String data;
    private String local;
    private int vagasDisponiveis;
    private List<Voluntario> voluntariosInscritos;
    
    public Atividade(int id, String titulo, String descricao, String data, String local, int vagasDisponiveis){
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
        this.local = local;
        this.vagasDisponiveis = vagasDisponiveis;
        this.voluntariosInscritos = new ArrayList();
    }
    
    //vou testar fazer a inscricao aqui, mas se 
    // fica mto baguncado eu mudo depois.
    public boolean inscreverVoluntario(Voluntario voluntario){
        if(vagasDisponiveis > 0 && !voluntariosInscritos.contains(voluntario)){
            voluntariosInscritos.add(voluntario);
            vagasDisponiveis--;
            voluntario.addAtividadeAtiva(this);
            return true;
        }
        return false;
    }

    public int getId(){
        return this.id;
    }
    
    public String getTitulo(){
        return this.titulo;
    }

    public String getData(){
        return this.data;
    }

    public String getLocal(){
        return this.local;
    }

    public int getVagasDisponiveis(){
        return this.vagasDisponiveis;
    }

}