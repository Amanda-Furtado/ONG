package ong;

import ong.sistema.*;
import ong.animais.*;
import ong.usuario.*;
import ong.service.Inventario;
import ong.sistema.*;
import ong.menu.*;
import ong.service.*;

public class Main {
    public static void main (String args[]){
        SistemaUsuario sisUser = new SistemaUsuario();
        Inventario sisInventario = new Inventario();

        Usuario voluntario1 = new Voluntario("Marina Gomes", "abc@gmail.com", "123456", "11122233344");
        Usuario veterinario1 = new Veterinario("Carla Machado", "vet.carlam@gmail.com", "abcdef", "12312312399");
        Usuario admin1 = new Administrador("Mário da Silva", "quemario@gmail.com", "senha1", "99988877700");

        Cachorro cachorro1 = new Cachorro(
            1, 
            "Maicon", 
            "Labrador", 
            3, 
            "Grande", 
            "Saudável e vacinado", 
            "10/05/2026", 
            "Disponível"
        );

        Gato gato1 = new Gato(
            2, 
            "Dog", 
            "Siamês", 
            2, 
            "Pequeno", 
            "Castrada e vermifugada", 
            "22/08/2026", 
            "Disponível"
        );

        Outros outro1 = new Outros(
            3, 
            "Macarrao", 
            "Coelho Mini Lop", 
            1, 
            "Pequeno", 
            "Em recuperação", 
            "04/11/2026", 
            "Em tratamento"
        );


        sisUser.cadastrarUsuario(voluntario1);
        sisUser.cadastrarUsuario(veterinario1);
        sisUser.cadastrarUsuario(admin1);

        sisInventario.addAnimal(gato1);
        sisInventario.addAnimal(cachorro1);
        sisInventario.addAnimal(outro1);
        
        MenuPrincipal mPrincipal = new MenuPrincipal(sisUser, sisInventario);

        mPrincipal.mostrarMenu();

    }
}