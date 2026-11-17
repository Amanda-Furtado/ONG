package ong.service;

import java.util.ArrayList;
import ong.animais.*;
import ong.usuario.*;

public class Inventario{
    private ArrayList<Animal> animais;
    private ArrayList<SolicitacaoAdocao> solicitacoes;

    public Inventario(){
        animais = new ArrayList<>();
        solicitacoes = new ArrayList<>();
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
                System.out.println("Erro: Um animal adotado nao pode ser removido do sistema/historico.");
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
    public ArrayList<Animal> buscarPorNome(String termoBusca){
        ArrayList<Animal> resultados = new ArrayList<>();
        for(Animal animal : animais){
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
        if(animal == null){
            throw new IllegalArgumentException("Erro: Animal nao encontrado.");
        }

        if(animal.getEstadoSaude().equalsIgnoreCase("Critico") || animal.getEstadoSaude().equalsIgnoreCase("Tratamento Critico")){
            throw new IllegalStateException("Erro: O animal " + animal.getNome() + " esta em tratamento critico e nao pode ser adotado.");
        }

        if(!animal.getStatusAdocao().equalsIgnoreCase("Disponivel")){
            throw new IllegalStateException("Erro: O animal " + animal.getNome() + " esta indisponivel para adocao (Status: " + animal.getStatusAdocao() + ").");
        }

        SolicitacaoAdocao novaSol = new SolicitacaoAdocao(idSolicitacao, animal, nomeCandidato, cpfCandidato);
        solicitacoes.add(novaSol);
        System.out.println("Solicitacao de adocao criada para o(a) " + animal.getNome() + " em nome de " + nomeCandidato);
        return true;
    }
    public void exibirSolicitacoes(){
        System.out.println("--- Lista de Candidatos e Solicitacoes ---");
        for(SolicitacaoAdocao sol : solicitacoes){
            System.out.println("ID Solicitacao: " + sol.getIdSolicitacao() + 
                               " | Candidato: " + sol.getNomeCandidato() + 
                               " (CPF: " + sol.getCpfCandidato() + ")" +
                               " | Animal: " + sol.getAnimal().getNome() + 
                               " | Status: " + sol.getStatus());
        }
    }

    public ArrayList<SolicitacaoAdocao> buscarSolicitacoesPorCpf(String cpf){
        ArrayList<SolicitacaoAdocao> resultados = new ArrayList<>();
        for(SolicitacaoAdocao sol : solicitacoes){
            if(sol.getCpfCandidato().equals(cpf)){
                resultados.add(sol);
            }
        }
        return resultados;
    }
    public SolicitacaoAdocao buscarSolicitacaoPorId(int idSolicitacao){
        for(SolicitacaoAdocao sol : solicitacoes){
            if(sol.getIdSolicitacao() == idSolicitacao){
                return sol;
            }
        }
        return null;
    }

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
//MODULO 4 COMECO -----------------------------------------------------
    private ArrayList<Consulta> historicoConsultas = new ArrayList<>();

    public void adicionarConsulta(Consulta consulta){
        this.historicoConsultas.add(consulta);
    }

    public ArrayList<Consulta> getHistoricoConsultas(){
        return historicoConsultas;
    }
    public boolean registrarConsulta(int idConsulta, int idAnimal, Usuario usuarioLogado, String data, String diagnostico, String prescricao, String novoEstadoSaude){
        if(!(usuarioLogado instanceof Veterinario)){
            System.out.println("Acesso negado: Apenas veterinarios podem registrar consultas.");
            return false;
        }

        Animal animal = buscarPorId(idAnimal);
        if(animal != null){
            Veterinario vet = (Veterinario) usuarioLogado;
            Consulta novaConsulta = new Consulta(idConsulta, animal, vet, data, diagnostico, prescricao);
            animal.adicionarConsulta(novaConsulta);
            animal.setEstadoSaude(novoEstadoSaude);
            System.out.println("Consulta #" + idConsulta + " registrada para " + animal.getNome() + " pelo(a) Dr(a). " + vet.getNome());
            return true;
        }
        System.out.println("Erro: Animal nao encontrado.");
        return false;
    }
    public boolean registrarProcedimento(int idProcedimento, int idAnimal, Usuario usuarioLogado, String tipo, String descricao, String data, String status){
        if(!(usuarioLogado instanceof Veterinario)){
            System.out.println("Acesso negado: Apenas veterinarios podem registrar procedimentos medicos.");
            return false;
        }

        Animal animal = buscarPorId(idAnimal);
        if(animal != null){
            Veterinario vet = (Veterinario) usuarioLogado;
            ProcedimentoMedico proc = new ProcedimentoMedico(idProcedimento, animal, vet, tipo, descricao, data, status);
            animal.adicionarProcedimento(proc);
            System.out.println("Procedimento (" + tipo + ") registrado para " + animal.getNome() + ".");
            return true;
        }
        System.out.println("Erro: Animal nao encontrado.");
        return false;
    }

    public boolean atualizarStatusTratamento(int idAnimal, int idProcedimento, Usuario usuarioLogado, String novoStatus, String novoEstadoSaude){
        if(!(usuarioLogado instanceof Veterinario)){
            System.out.println("Acesso negado: Apenas veterinarios podem atualizar tratamentos.");
            return false;
        }

        Animal animal = buscarPorId(idAnimal);
        if(animal != null){
            for(ProcedimentoMedico p : animal.getHistoricoProcedimentos()){
                if(p.getIdProcedimento() == idProcedimento){
                    p.setStatus(novoStatus);
                    animal.setEstadoSaude(novoEstadoSaude);
                    System.out.println("Tratamento #" + idProcedimento + " atualizado para: " + novoStatus);
                    return true;
                }
            }
            System.out.println("Erro: Procedimento nao encontrado para este animal.");
            return false;
        }
        System.out.println("Erro: Animal nao encontrado.");
        return false;
    }

    public void exibirHistoricoMedicoAnimal(int idAnimal){
        Animal animal = buscarPorId(idAnimal);
        if(animal != null){
            System.out.println("--- Prontuario Medico Completo: " + animal.getNome() + " ---");
            System.out.println("ID: " + animal.getId() + 
                               " | Especie: " + animal.getEspecie() + 
                               " | Raca: " + animal.getRaca() + 
                               " | Estado de Saude Atual: " + animal.getEstadoSaude());

            System.out.println("--- Historico de Consultas ---");
            if(animal.getHistoricoConsultas().isEmpty()){
                System.out.println("Nenhuma consulta registrada.");
            }else{
                for(Consulta c : animal.getHistoricoConsultas()){
                    System.out.println(c.toString());
                }
            }

            System.out.println("--- Historico de Procedimentos e Tratamentos ---");
            if(animal.getHistoricoProcedimentos().isEmpty()){
                System.out.println("Nenhum procedimento registrado.");
            }
            else{
                for(ProcedimentoMedico p : animal.getHistoricoProcedimentos()){
                    System.out.println(p.toString());
                }
            }
            System.out.println("--- Carteira de Vacinacao ---");
            if(animal.getCarteiraVacinacao().isEmpty()){
                System.out.println("Nenhuma vacina registrada.");
            }else{
                for(Vacina v : animal.getCarteiraVacinacao()){
                    System.out.println(v.toString());
                }
            }
        }else{
            System.out.println("Erro: Animal nao encontrado.");
        }
    }

    public boolean registrarVacinacao(int idVacina, int idAnimal, Usuario usuarioLogado, String nomeVacina, String dose, String dataAplicacao, String dataProximaDose){
        if(!(usuarioLogado instanceof Veterinario)){
            System.out.println("Acesso negado: Apenas veterinarios podem registrar vacinacao.");
            return false;
        }

        Animal animal = buscarPorId(idAnimal);
        if(animal != null){
            for(Vacina v : animal.getCarteiraVacinacao()){
                if(v.getNomeVacina().equalsIgnoreCase(nomeVacina) && v.getDataAplicacao().equals(dataAplicacao)){
                    System.out.println("Erro: Vacina " + nomeVacina + " ja registrada na data " + dataAplicacao + " para este animal.");
                    return false;
                }
            }

            Veterinario vet = (Veterinario) usuarioLogado;
            Vacina novaVacina = new Vacina(idVacina, animal, vet, nomeVacina, dose, dataAplicacao, dataProximaDose);
            animal.adicionarVacina(novaVacina);
            System.out.println("Vacina " + nomeVacina + " (" + dose + ") registrada para " + animal.getNome() + ".");
            return true;
        }
        System.out.println("Erro: Animal nao encontrado.");
        return false;
    }
}

