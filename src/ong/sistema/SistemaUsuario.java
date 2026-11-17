package ong.sistema;

import java.util.ArrayList;
import ong.usuario.Usuario;
import ong.usuario.Voluntario;


public class SistemaUsuario {
    private ArrayList<Usuario> todosUsuarios = new ArrayList<>();

    public void cadastrarUsuario(Usuario u) {
        todosUsuarios.add(u);
    }

    public void mostrarUsuarios(){
        for(Usuario u : todosUsuarios){
            System.out.printf(u.getNome() + ".\n");
        }
    }

    public Usuario validarLogin(String email, String senha){
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

    public int getQuantidadeVoluntarios() {
        int total = 0;
        for (Usuario u : todosUsuarios) {
            if (u instanceof Voluntario) {
                total++;
            }
        }
        return total;
    }

}