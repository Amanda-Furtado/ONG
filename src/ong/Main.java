package ong;

import ong.menu.*;
import ong.sistema.*;
import ong.usuario.*;

public class Main {
    public static void main (String args[]){
        SistemaUsuario sisUser = new SistemaUsuario();
        MenuPrincipal mPrincipal = new MenuPrincipal(sisUser);

        Usuario voluntario1 = new Voluntario("Marina Gomes", "abc@gmail.com", "123456", "11122233344");
        Usuario veterinario1 = new Veterinario("Carla Machado", "vet.carlam@gmail.com", "abcdef", "12312312399");
        Usuario admin1 = new Administrador("Mário da Silva", "quemario@gmail.com", "senha1", "99988877700");
        

        sisUser.cadastrarUsuario(voluntario1);
        sisUser.cadastrarUsuario(veterinario1);
        sisUser.cadastrarUsuario(admin1);
        
        mPrincipal.mostrarMenu();

    }
}