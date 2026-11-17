package ong.sistema;

import java.util.ArrayList;
import ong.usuario.*;

public class SistemaUsuario {
    private ArrayList<Usuario> todosUsuarios = new ArrayList<>();

    public void cadastrarUsuario(Usuario u) {
        todosUsuarios.add(u);
    }

    public ArrayList<Usuario> getTodosUsuarios() {
        return todosUsuarios;
    }

    public boolean removerUsuario(String email) {
        Usuario alvo = buscarPorEmail(email);
        if (alvo != null) {
            todosUsuarios.remove(alvo);
            return true;
        }
        return false;
    }

    public boolean promoverParaAdmin(String email) {
        Usuario alvo = buscarPorEmail(email);
        if (alvo != null && !(alvo instanceof Administrador)) {
            Administrador novoAdmin = new Administrador(alvo.getNome(), alvo.getEmail(), alvo.getSenha(), alvo.getCpf());
            todosUsuarios.remove(alvo);
            todosUsuarios.add(novoAdmin);
            return true;
        }
        return false;
    }

    public Usuario buscarPorEmail(String email) {
        for (Usuario u : todosUsuarios) {
            if (validarEmail(u, email)) {
                return u;
            }
        }
        return null;
    }

    public void mostrarUsuarios() {
        for (Usuario u : todosUsuarios) {
            System.out.printf(u.getNome() + " - email: " + u.getEmail() + ".\n");
        }
    }

    public Usuario validarLogin(String email, String senha) {
        for (Usuario u : todosUsuarios) {
            if (validarEmail(u, email) && u.validarSenha(senha)) {
                return u;
            }
        }
        return null;
    }

    public boolean validarEmail(Usuario u, String email) {
        return u.getEmail().equalsIgnoreCase(email.trim());
    }

    public int contarVoluntariosAtivos() {
        int total = 0;
        for (Usuario u : todosUsuarios) {
            if (u instanceof Voluntario) {
                total++;
            }
        }
        return total;
    }
}