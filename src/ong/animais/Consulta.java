package ong.animais;

import ong.usuario.Veterinario;

public class Consulta{
    private final int idConsulta;
    private Animal animal;
    private Veterinario veterinario;
    private String data;
    private String diagnostico;
    private String prescricao;

    public Consulta(int idConsulta, Animal animal, Veterinario veterinario, String data, String diagnostico, String prescricao){
        this.idConsulta = idConsulta;
        this.animal = animal;
        this.veterinario = veterinario;
        this.data = data;
        this.diagnostico = diagnostico;
        this.prescricao = prescricao;
    }

    public int getIdConsulta(){
        return idConsulta;
    }

    public Animal getAnimal(){
        return animal;
    }

    public Veterinario getVeterinario(){
        return veterinario;
    }

    public String getData(){
        return data;
    }

    public String getDiagnostico(){
        return diagnostico;
    }

    public String getPrescricao(){
        return prescricao;
    }

    @Override
    public String toString(){
        return "Consulta {" +
                "idConsulta = " + idConsulta +
                ", animal = " + animal.getNome() +
                ", veterinario = " + veterinario.getNome() +
                ", data = " + data +
                ", diagnostico = " + diagnostico +
                ", prescricao = " + prescricao +
                '}';
    }
}