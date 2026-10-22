package ong.usuario;

public class Veterinario extends Usuario {
    private final int CPF;

    public Veterinario(String nome, String email, String senha, int cpf){
        super(nome, email, senha);
        this.setTipo("VETERINARIO");
        this.CPF = cpf;
    }

    public void consultarAnimais(){

    }

    public void registrarAtendimento(){

    }

    public void registrarVacinacao(){

    }

    public void atualizarTratamento(){

    }

    public void emitirRelatorioMedico(){

    }
}