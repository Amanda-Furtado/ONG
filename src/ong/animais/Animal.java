package ong.animais;

import java.util.ArrayList;

public abstract class Animal{
    private final int id;
    private String nome;
    private String raca;
    private int idade;
    private String porte;
    private String estadoSaude;
    private String dataResgate;
    private String statusAdocao;

    public Animal(int id, String nome, String raca, int idade, String porte, 
                  String estadoSaude, String dataResgate, String statusAdocao){
        this.id = id;
        this.nome = nome;
        this.raca = raca;
        this.idade = idade;
        this.porte = porte;
        this.estadoSaude = estadoSaude;
        this.dataResgate = dataResgate;
        this.statusAdocao = statusAdocao;
    }


    public int getId(){
        return id;
    }
    public String getNome(){
        return nome;
    }
    public void setNome(String nome){ 
        this.nome = nome; 
    }
    public String getRaca(){
        return raca; 
    }
    public void setRaca(String raca){
        this.raca = raca;
    }
    public int getIdade(){
        return idade; 
    }
    public void setIdade(int idade){ 
        this.idade = idade; 
    }
    public String getPorte(){ 
        return porte; 
    }
    public void setPorte(String porte){ 
        this.porte = porte; 
    }
    public String getEstadoSaude(){ 
        return estadoSaude; 
    }
    public void setEstadoSaude(String estadoSaude){ 
        this.estadoSaude = estadoSaude; 
    }
    public String getDataResgate(){ 
        return dataResgate; 
    }
    public void setDataResgate(String dataResgate){ 
        this.dataResgate = dataResgate; 
    }
    public String getStatusAdocao(){ 
        return statusAdocao; 
    }
    public void setStatusAdocao(String statusAdocao){ 
        this.statusAdocao = statusAdocao; 
    }
    public String getEspecie(){
        return this.getClass().getSimpleName();
    }

    @Override 
    public String toString(){
        return "Animal {" +
                "id = " + id +
                ", nome = " + nome +
                ", especie = " + getEspecie() +
                ", raca = " + raca +
                ", idade = " + idade +
                ", porte = " + porte +
                ", estadoSaude = " + estadoSaude +
                ", dataResgate = " + dataResgate +
                ", statusAdocao = " + statusAdocao +
                '}';
    }
//MODULO 4 -----------------------------------------------------
    private ArrayList<ProcedimentoMedico> historicoProcedimentos = new ArrayList<>();

        public void adicionarProcedimento(ProcedimentoMedico procedimento){
            this.historicoProcedimentos.add(procedimento);
        }

        public ArrayList<ProcedimentoMedico> getHistoricoProcedimentos(){
            return historicoProcedimentos;
        }
    private ArrayList<Vacina> carteiraVacinacao = new ArrayList<>();

    public void adicionarVacina(Vacina vacina){
        this.carteiraVacinacao.add(vacina);
    }

    public ArrayList<Vacina> getCarteiraVacinacao(){
        return carteiraVacinacao;
    }
    private ArrayList<Consulta> historicoConsultas = new ArrayList<>();

    public void adicionarConsulta(Consulta consulta){
        this.historicoConsultas.add(consulta);
    }

    public ArrayList<Consulta> getHistoricoConsultas(){
        return historicoConsultas;
    }

}