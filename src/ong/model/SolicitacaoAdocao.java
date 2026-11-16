package model;

public class SolicitacaoAdocao{
    private int idSolicitacao;
    private Animal animal;
    private String nomeCandidato;
    private String cpfCandidato;
    private String status;

    public SolicitacaoAdocao(int idSolicitacao, Animal animal, String nomeCandidato, String cpfCandidato){
        this.idSolicitacao = idSolicitacao;
        this.animal = animal;
        this.nomeCandidato = nomeCandidato;
        this.cpfCandidato = cpfCandidato;
        this.status = "Pendente"; // Toda solicitação nasce como pendente
    }

    public int getIdSolicitacao(){
        return idSolicitacao;
    }
    public Animal getAnimal(){
        return animal;
    }
    public String getNomeCandidato(){
        return nomeCandidato;
    }
    public String getCpfCandidato(){
        return cpfCandidato;
    }
    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status = status;
    }

    @Override
    public String toString(){
        return "SolicitacaoAdocao {" +
                "idSolicitacao=" + idSolicitacao +
                ", animal=" + animal.getNome() +
                ", candidato='" + nomeCandidato + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}