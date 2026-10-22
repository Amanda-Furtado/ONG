package ong;

import ong.sistema.*;
import ong.usuario.*;
import ong.menu.*;

public class Main {
    public static void main (String args[]){
        SistemaUsuario sisUser = new SistemaUsuario();
        MenuLogin mLogin = new MenuLogin();
        
        mLogin.mostrarMenu();


    }
}