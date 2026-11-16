package ong.eventos;

import ong.animais.Animal;

import java.util.ArrayList;
import java.util.List;


public class FeiraAdocao extends Atividade{
    private List<Animal> animaisDisponiveis = new ArrayList<>();

    public FeiraAdocao(int id, String titulo, String descricao, String data, String local, int vagasDisponiveis){
        super(id, titulo, descricao, data, local, vagasDisponiveis);
    }
}