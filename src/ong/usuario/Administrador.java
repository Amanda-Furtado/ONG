package ong.usuario;

public class Administrador extends Usuario {

    public Administrador(String nome, String email, String senha){
        super(nome, email, senha);
        this.setTipo("ADMIN");
    }
}