package ong.usuario;
import ong.service.Inventario;

public class Veterinario extends Usuario {

    public Veterinario(String nome, String email, String senha, String cpf){
        super(nome, email, senha, cpf);
    }

    public void consultarAnimais(){

    }

    public void registrarAtendimento(){

    }

    public void registrarVacinacao(){

    }

    public void atualizarTratamento(){

    }

    public void emitirRelatorioMedico(){

    }
    public boolean registrarAtendimento(Inventario inventario, int idConsulta, int idAnimal, String data, String diagnostico, String prescricao, String novoEstadoSaude){
        return inventario.registrarConsulta(idConsulta, idAnimal, this, data, diagnostico, prescricao, novoEstadoSaude);
    }
    public boolean registrarVacinacao(Inventario inventario, int idVacina, int idAnimal, String nomeVacina, String dose, String dataAplicacao, String dataProximaDose){
        return inventario.registrarVacinacao(idVacina, idAnimal, this, nomeVacina, dose, dataAplicacao, dataProximaDose);
    }
}