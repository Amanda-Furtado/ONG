package ong.animal;


public abstract class Animal {
    private int id;
    private String nome;
    private String raca;
    private int idade;
    private Porte porte;
    private String estadoSaude;
    private String dataResgate;
    private StatusAdocao statusAdocao;

    public Animal(int id, String nome, String raca, int idade, Porte porte, 
                  String estadoSaude, String dataResgate, StatusAdocao statusAdocao) {
        this.id = id;
        this.nome = nome;
        this.raca = raca;
        this.idade = idade;
        this.porte = porte;
        this.estadoSaude = estadoSaude;
        this.dataResgate = dataResgate;
        this.statusAdocao = statusAdocao;
    }

    public abstract String getEspecie();

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
    public Porte getPorte(){ 
        return porte; 
    }
    public void setPorte(Porte porte){ 
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
    public StatusAdocao getStatusAdocao(){ 
        return statusAdocao; 
    }
    public void setStatusAdocao(StatusAdocao statusAdocao){ 
        this.statusAdocao = statusAdocao; 
    }

    @Override
    public String toString() {
        return String.format("[%d] %s (%s - %s) | Idade: %d | Porte: %s | Saude: %s | Data: %s | Status: %s", 
                id, nome, getEspecie(), raca, idade, porte, estadoSaude, dataResgate, statusAdocao);
    }
}