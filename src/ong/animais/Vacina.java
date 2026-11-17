package ong.animais;

import ong.usuario.Veterinario;

public class Vacina{
    private final int idVacina;
    private Animal animal;
    private Veterinario veterinario;
    private String nomeVacina; 
    private String dose; 
    private String dataAplicacao;
    private String dataProximaDose;

    public Vacina(int idVacina, Animal animal, Veterinario veterinario, String nomeVacina, String dose, String dataAplicacao, String dataProximaDose){
        this.idVacina = idVacina;
        this.animal = animal;
        this.veterinario = veterinario;
        this.nomeVacina = nomeVacina;
        this.dose = dose;
        this.dataAplicacao = dataAplicacao;
        this.dataProximaDose = dataProximaDose;
    }

    public int getIdVacina(){
        return idVacina;
    }

    public Animal getAnimal(){
        return animal;
    }

    public Veterinario getVeterinario(){
        return veterinario;
    }

    public String getNomeVacina(){
        return nomeVacina;
    }

    public String getDose(){
        return dose;
    }

    public String getDataAplicacao(){
        return dataAplicacao;
    }

    public String getDataProximaDose(){
        return dataProximaDose;
    }

    public void setDataProximaDose(String dataProximaDose){
        this.dataProximaDose = dataProximaDose;
    }

    @Override
    public String toString(){
        return "Vacina {" +
                "id = " + idVacina +
                ", vacina = " + nomeVacina +
                ", dose = " + dose +
                ", aplicacao = " + dataAplicacao +
                ", proximaDose = " + dataProximaDose +
                ", veterinario = Dr(a). " + veterinario.getNome() +
                '}';
    }
}