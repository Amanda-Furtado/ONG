import model.*;
import service.Inventario;

public class Main{
    public static void main(String args[]){
        Inventario inventario = new Inventario();

        Cachorro c1 = new Cachorro(1, "Thor", "Golden", 5, "Grande", "Saudável", "15/02/2026", "Disponivel");
        Gato g1 = new Gato(2, "Mel", "Siamês", 3, "Pequeno", "Saudável", "10/01/2026", "Disponivel");

        inventario.addAnimal(c1);
        inventario.addAnimal(g1);

        inventario.displayInventario();
    }
}