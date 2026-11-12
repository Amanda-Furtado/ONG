package ong.usuario;

public abstract class Usuario {
    private String nome;
    private String email;
    private String senha;
    private final String CPF;


    public Usuario (String nome, String email, String senha, String CPF){
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.CPF = CPF;
    }

    public String getNome(){
        return this.nome;
    }

    public String getEmail(){
        return this.email;
    }

    public boolean validarSenha(String senha){
        return this.senha.equals(senha);
    }

    public int getCPF(){
        return this.CPF;
    }
}