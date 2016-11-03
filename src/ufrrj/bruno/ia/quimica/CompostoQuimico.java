package ufrrj.bruno.ia.quimica;

import ufrrj.bruno.ia.atributos.Posicao;

public class CompostoQuimico {   
    
    public enum TIPO_COMPOSTO {HISTAMINA,PROSTAGLANDINA};
    
    private TIPO_COMPOSTO tipo;
    private double temperatura = 30.0; // ?????
    private final Posicao pos;
    private int diametro = 8;
    private int quantidade = 0;

    public CompostoQuimico(TIPO_COMPOSTO tipo,int quantidade,Posicao pos){
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.pos = pos;
    }
    
    public TIPO_COMPOSTO getTipo() {
        return tipo;
    }

    public int getQuantidade() {
        return quantidade;
    }
    
    void diminuiQuantidade(int qt) {
        quantidade -= qt;
    }

    public Posicao getPos() {
        return pos;
    }

    public int getDiametro() {
        return diametro;
    }

    public void setRaio(int raio) {
        this.diametro = raio;
    }
    
    public void aumentaDiametro(int tam){
        diametro += tam;
    }
    
}
