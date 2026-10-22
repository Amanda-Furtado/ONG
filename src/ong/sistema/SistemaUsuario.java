package ong.sistema;

import java.util.ArrayList;
import java.util.List;

import ong.usuario.Usuario;


public class SistemaUsuario {
    private List<Usuario> todosUsuarios = new ArrayList<>();

    public void cadastrarUsuario(Usuario u) {
        todosUsuarios.add(u);
    }

    public void mostrarUsuarios(){
        for(Usuario u : todosUsuarios){
            System.out.printf(u.getNome() + "\n");
        }
    }
}