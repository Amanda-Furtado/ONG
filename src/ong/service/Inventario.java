package ong.service;

import java.util.ArrayList;
import ong.animais.*;

public class Inventario{
    private ArrayList<Animal> animais;
    private ArrayList<SolicitacaoAdocao> solicitacoes;

    public Inventario(){
        animais = new ArrayList<>();
        solicitacoes = new ArrayList<>();
    }

    public ArrayList<Animal> getAnimais() {
        return this.animais;
    }

    public void addAnimal(Animal animal){
        animais.add(animal);
    }

    public Animal buscarPorId(int id){
        for(Animal animal : animais){
            if(animal.getId() == id){
                    return animal;
             }
        }
        return null;
    }

    public boolean removerAnimal(int id){
        Animal animal = buscarPorId(id);
        if(animal != null){
            if(animal.getStatusAdocao().equalsIgnoreCase("Adotado")){
                System.out.println("Erro: Um animal adotado não pode ser removido do sistema/histórico.");
                return false;
            }
            animais.remove(animal);
            return true;
        }
        return false;
    }
    
    public boolean editarAnimal(int id, String novoNome, String novaRaca, int novaIdade, 
                            String novoPorte, String novoEstadoSaude, String novaDataResgate, 
                            String novoStatusAdocao){
        Animal animal = buscarPorId(id);
        if(animal != null){
            animal.setNome(novoNome);
            animal.setRaca(novaRaca);
            animal.setIdade(novaIdade);
            animal.setPorte(novoPorte);
            animal.setEstadoSaude(novoEstadoSaude);
            animal.setDataResgate(novaDataResgate);
            animal.setStatusAdocao(novoStatusAdocao);
            return true;
        }else{
            return false;
        }
}
    public ArrayList<Animal> filtrarPorEspecie(String especie){
        ArrayList<Animal> resultados = new ArrayList<>();
        for(Animal animal : animais){
            if(especie.equalsIgnoreCase("Cachorro") && animal instanceof Cachorro){
                resultados.add(animal);
            }else if(especie.equalsIgnoreCase("Gato") && animal instanceof Gato){
                resultados.add(animal);
            }
        }
        return resultados;
    }
// SISTEMA DE BUSCA POR NOME (ou parte do nome)
    public ArrayList<Animal> buscarPorNome(String termoBusca){
        ArrayList<Animal> resultados = new ArrayList<>();
        for(Animal animal : animais){
            // toLowerCase ajuda a encontrar independente de maiúsculas/minúsculas
            if(animal.getNome().toLowerCase().contains(termoBusca.toLowerCase())){
                resultados.add(animal);
            }
        }
        return resultados;
    }
    
    public void displayInventario(){
        for(Animal animal : animais){
            System.out.println(animal.toString());
        }
    }
// MODULO 3 COMEÇO ---------------------------------------------------------------------------------------------------------------------------------------------
    public boolean solicitarAdocao(int idSolicitacao, int idAnimal, String nomeCandidato, String cpfCandidato){
        Animal animal = buscarPorId(idAnimal);
        if(animal != null){
            // REGRA: Um animal adotado não pode receber novas solicitações
            if(animal.getStatusAdocao().equalsIgnoreCase("Adotado")){
                System.out.println("Erro: O animal " + animal.getNome() + " já foi adotado e não aceita novas solicitações.");
                return false;
            }
            
            SolicitacaoAdocao novaSol = new SolicitacaoAdocao(idSolicitacao, animal, nomeCandidato, cpfCandidato);
            solicitacoes.add(novaSol);
            System.out.println("Solicitação de adoção criada para o(a) " + animal.getNome() + " em nome de " + nomeCandidato);
            return true;
        }
        return false;
    }
    // Método para exibir todas as solicitações/candidatos registrados
    public void exibirSolicitacoes(){
        System.out.println("--- Lista de Candidatos e Solicitações ---");
        for(SolicitacaoAdocao sol : solicitacoes){
            System.out.println("ID Solicitação: " + sol.getIdSolicitacao() + 
                               " | Candidato: " + sol.getNomeCandidato() + 
                               " (CPF: " + sol.getCpfCandidato() + ")" +
                               " | Animal: " + sol.getAnimal().getNome() + 
                               " | Status: " + sol.getStatus());
        }
    }

    // Método para buscar solicitações pelo CPF do candidato
    public ArrayList<SolicitacaoAdocao> buscarSolicitacoesPorCpf(String cpf){
        ArrayList<SolicitacaoAdocao> resultados = new ArrayList<>();
        for(SolicitacaoAdocao sol : solicitacoes){
            if(sol.getCpfCandidato().equals(cpf)){
                resultados.add(sol);
            }
        }
        return resultados;
    }
    // Método auxiliar para buscar uma solicitação pelo ID dela
    public SolicitacaoAdocao buscarSolicitacaoPorId(int idSolicitacao){
        for(SolicitacaoAdocao sol : solicitacoes){
            if(sol.getIdSolicitacao() == idSolicitacao){
                return sol;
            }
        }
        return null;
    }
    // REGRA: Apenas administradores podem concluir/aprovar uma adoção
    public boolean aprovarAdocao(int idSolicitacao, boolean isAdmin){
        if(!isAdmin){
            System.out.println("Acesso negado: Apenas administradores podem aprovar uma adoção.");
            return false;
        }

        SolicitacaoAdocao sol = buscarSolicitacaoPorId(idSolicitacao);
        if(sol != null){
            sol.setStatus("Aprovado");
            // Atualiza o status do animal para adotado
            sol.getAnimal().setStatusAdocao("Adotado");
            System.out.println("Adoção do(a) " + sol.getAnimal().getNome() + " aprovada com sucesso para " + sol.getNomeCandidato() + "!");
            return true;
        }
        return false;
    }

    // Método para rejeitar uma solicitação de adoção
    public boolean rejeitarAdocao(int idSolicitacao, boolean isAdmin){
        if(!isAdmin){
            System.out.println("Acesso negado: Apenas administradores podem rejeitar uma adoção.");
            return false;
        }

        SolicitacaoAdocao sol = buscarSolicitacaoPorId(idSolicitacao);
        if(sol != null){
            sol.setStatus("Rejeitado");
            System.out.println("A solicitação " + idSolicitacao + " foi rejeitada.");
            return true;
        }
        return false;
    }
    // Método para exibir apenas as adoções com status "Aprovado"
    public void exibirHistoricoAdocoesAprovadas(){
        System.out.println("--- Histórico de Adoções com Status Aprovado ---");
        boolean encontrou = false;
        
        for(SolicitacaoAdocao sol : solicitacoes){
            if(sol.getStatus().equalsIgnoreCase("Aprovado")){
                System.out.println("ID Solicitação: " + sol.getIdSolicitacao() + 
                                   " | Animal: " + sol.getAnimal().getNome() + 
                                   " | Adotante: " + sol.getNomeCandidato() + 
                                   " | Status: " + sol.getStatus());
                encontrou = true;
            }
        }
        
        if(!encontrou){
            System.out.println("Nenhuma adoção aprovada encontrada.");
        }
    }
    // REGRA: Registro de devolução quando necessário (sem motivo)
    public boolean registrarDevolucao(int idAnimal){
        Animal animal = buscarPorId(idAnimal);
        if(animal != null && animal.getStatusAdocao().equalsIgnoreCase("Adotado")){
            animal.setStatusAdocao("Disponivel");
            System.out.println("Registro de devolução realizado para o(a) " + animal.getNome() + ". Animal novamente disponível para adoção.");
            return true;
        }
        System.out.println("Erro: Animal não encontrado ou não consta como adotado.");
        return false;
    }
// MODULO 3 FIM ---------------------------------------------------------------------------------------------------------------------------------------------
// MODULO 4

}

