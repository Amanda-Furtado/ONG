package ong;

import ong.sistema.*;
import ong.usuario.*;


public class Main {
    public static void main (String args[]){
        SistemaUsuario sisUser = new SistemaUsuario();

        Usuario user1 = new Voluntario("ABC", "ABC", "ABC");

        sisUser.cadastrarUsuario(user1);
        sisUser.mostrarUsuarios();
    }
}