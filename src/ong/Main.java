package ong;

import ong.animais.*;
import ong.menu.*;
import ong.service.Inventario;
import ong.sistema.SistemaUsuario;
import ong.usuario.*;

public class Main {
    public static void main(String[] args) {
        SistemaUsuario sisUser = new SistemaUsuario();
        Inventario inventario = new Inventario();

        // 1. Cadastrando usuários (incluindo voluntários para a contagem do relatório)
        Usuario voluntario1 = new Voluntario("Marina Gomes", "abc@gmail.com", "123456", "11122233344");
        Usuario voluntario2 = new Voluntario("Lucas Pereira", "lucas@gmail.com", "123456", "55566677788");
        Usuario veterinario1 = new Veterinario("Carla Machado", "vet.carlam@gmail.com", "abcdef", "12312312399");
        Usuario admin1 = new Administrador("Mário da Silva", "quemario@gmail.com", "senha1", "99988877700");

        sisUser.cadastrarUsuario(voluntario1);
        sisUser.cadastrarUsuario(voluntario2);
        sisUser.cadastrarUsuario(veterinario1);
        sisUser.cadastrarUsuario(admin1);

        // 2. Cadastrando animais de espécies e estados de saúde variados
        Animal dog1 = new Cachorro(1, "Rex", "Vira-lata", 3, "Médio", "Saudavel", "10/01/2026", "Disponivel");
        Animal dog2 = new Cachorro(2, "Thor", "Labrador", 2, "Grande", "Em tratamento", "15/02/2026", "Disponivel");
        Animal gato1 = new Gato(3, "Mia", "Siamês", 1, "Pequeno", "Saudavel", "20/03/2026", "Disponivel");
        Animal outro1 = new Outros(4, "Piu", "Calopsita", 1, "Pequeno", "Recuperacao", "05/04/2026", "Disponivel");

        inventario.addAnimal(dog1);
        inventario.addAnimal(dog2);
        inventario.addAnimal(gato1);
        inventario.addAnimal(outro1);

        // 3. Simulando solicitação e aprovação de adoção
        inventario.solicitarAdocao(101, 1, "João Silva", "12345678900");
        inventario.aprovarAdocao(101, true); // true = aprovado por administrador

        // 4. Exibindo o relatório completo do Módulo 7 no console (considerando 2 meses de operação)
        System.out.println("\n" + inventario.gerarRelatorioGeral(sisUser, 2));

        // 5. Mantém a abertura normal da interface gráfica
        MenuPrincipal mPrincipal = new MenuPrincipal(sisUser);
        mPrincipal.mostrarMenu();
    }
}