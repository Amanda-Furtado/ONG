package ong.usuario;

public abstract class Usuario {
    private String nome;
    private String email;
    private String senha;
    private String tipo;

    public Usuario (String nome, String email, String senha){
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public String getNome(){
        return this.nome;
    }
    
    public String getTipo(){
        return this.tipo;
    }

    public void setTipo(String t){
        this.tipo = t;
    }

}