package ong.animais;

import ong.usuario.Veterinario;

public class ProcedimentoMedico{
    private final int idProcedimento;
    private Animal animal;
    private Veterinario veterinario;
    private String tipo;
    private String descricao;
    private String data;
    private String status;

    public ProcedimentoMedico(int idProcedimento, Animal animal, Veterinario veterinario, String tipo, String descricao, String data, String status){
        this.idProcedimento = idProcedimento;
        this.animal = animal;
        this.veterinario = veterinario;
        this.tipo = tipo;
        this.descricao = descricao;
        this.data = data;
        this.status = status;
    }

    public int getIdProcedimento(){
        return idProcedimento;
    }

    public Animal getAnimal(){
        return animal;
    }

    public Veterinario getVeterinario(){
        return veterinario;
    }

    public String getTipo(){
        return tipo;
    }

    public String getDescricao(){
        return descricao;
    }

    public String getData(){
        return data;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    @Override
    public String toString(){
        return "ProcedimentoMedico {" +
                "id = " + idProcedimento +
                ", tipo = " + tipo +
                ", descricao = " + descricao +
                ", data = " + data +
                ", status = " + status +
                ", veterinario = Dr(a). " + veterinario.getNome() +
                '}';
    }
}