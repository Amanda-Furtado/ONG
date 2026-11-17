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

    public String getCpf(){
        return this.CPF;
    }

    public boolean validarSenha(String senhaDigitada){
        return this.senha != null && this.senha.equals(senhaDigitada);
    }
    
}