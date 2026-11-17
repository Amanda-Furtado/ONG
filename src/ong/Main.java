package ong;

import ong.animais.*;
import ong.menu.*;
<<<<<<< HEAD
import ong.sistema.*;
import ong.animais.*;
import ong.eventos.*;
=======
import ong.service.Inventario;
import ong.sistema.SistemaUsuario;
>>>>>>> main
import ong.usuario.*;
import ong.service.*;

public class Main {
<<<<<<< HEAD
    public static void main(String args[]) {
        SistemaUsuario sisUser = new SistemaUsuario();
        Inventario sisInventario = new Inventario();
=======
    public static void main(String[] args) {
        SistemaUsuario sisUser = new SistemaUsuario();
        Inventario inventario = new Inventario();
>>>>>>> main

        // 1. Cadastrando usuários (incluindo voluntários para a contagem do relatório)
        Usuario voluntario1 = new Voluntario("Marina Gomes", "abc@gmail.com", "123456", "11122233344");
        Usuario voluntario2 = new Voluntario("Lucas Pereira", "lucas@gmail.com", "123456", "55566677788");
        Usuario veterinario1 = new Veterinario("Carla Machado", "vet.carlam@gmail.com", "abcdef", "12312312399");
        Usuario admin1 = new Administrador("Mário da Silva", "quemario@gmail.com", "senha1", "99988877700");
<<<<<<< HEAD

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
=======
>>>>>>> main

        sisUser.cadastrarUsuario(voluntario1);
        sisUser.cadastrarUsuario(voluntario2);
        sisUser.cadastrarUsuario(veterinario1);
        sisUser.cadastrarUsuario(admin1);

<<<<<<< HEAD
        sisInventario.addAnimal(gato1);
        sisInventario.addAnimal(cachorro1);
        sisInventario.addAnimal(outro1);

        sisInventario.registrarVacinacao(1, 1, veterinario1, "V10 Polivalente", "Reforço Anual", "25/11/2025", "25/11/2026");

        sisInventario.addAtividade(new MultiraoLimpeza(1, "Mutirão de Sábado", "Limpeza geral dos canis e gatis", "21/11/2026", "Sede do Abrigo", 10));
        sisInventario.addAtividade(new CampanhaVacinacao(2, "Vacinação Antirrábica", "Aplicação gratuita de vacinas", "28/11/2026", "Praça Central", 15));
        sisInventario.addAtividade(new FeiraAdocao(3, "Feira Amigos Fofos", "Feira de adoção responsável", "05/12/2026", "Parque Municipal", 8));
        sisInventario.addAtividade(new EventoBeneficente(4, "Bazar Solidário", "Arrecadação de ração e fundos", "12/12/2026", "Centro Comunitário", 12));

        sisInventario.registrarNotificacao("Aviso Geral do Abrigo", "Reunião mensal da equipe marcada para sexta-feira às 18h na sede.", "16/11/2026", null);

        MenuPrincipal mPrincipal = new MenuPrincipal(sisUser, sisInventario);
=======
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
>>>>>>> main
        mPrincipal.mostrarMenu();
    }
}