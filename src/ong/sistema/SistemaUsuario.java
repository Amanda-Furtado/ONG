package ong.sistema;

import java.util.ArrayList;
import java.util.List;

import ong.usuario.Usuario;


public class SistemaUsuario {
    private ArrayList<Usuario> todosUsuarios = new ArrayList<>();

    public void cadastrarUsuario(Usuario u) {
        todosUsuarios.add(u);
    }

    public void mostrarUsuarios(){
        for(Usuario u : todosUsuarios){
            System.out.printf(u.getNome() +  " - email: " + u.getEmail() + ".\n");
        }
    }

    public Usuario validarLogin(String email, String senha){
        mostrarUsuarios();
        for (Usuario u : todosUsuarios){
            if(validarEmail(u, email) && u.validarSenha(senha)){
                return u;
            }
        }
        return null;
    }

    public boolean validarEmail(Usuario u, String email){
        return u.getEmail().equalsIgnoreCase(email.trim());
    }

}