package ong.service;

public class Notificacao {
    private final int id;
    private String categoria;
    private String mensagem;
    private String data;
    private String cpfDestinatario;

    public Notificacao(int id, String categoria, String mensagem, String data, String cpfDestinatario) {
        this.id = id;
        this.categoria = categoria;
        this.mensagem = mensagem;
        this.data = data;
        this.cpfDestinatario = cpfDestinatario;
    }

    public int getId() {
        return id;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getData() {
        return data;
    }

    public String getCpfDestinatario() {
        return cpfDestinatario;
    }
}