package ufrrj.bruno.ia.quimica;

import ufrrj.bruno.ia.atributos.Posicao;
import ufrrj.bruno.ia.celulas.Celula;

public class CompostoQuimico {   
    
    /**
     * PAMP - Padrões moleculares associados a patógenos
    */
    public static enum TIPO_COMPOSTO {PAMP,HISTAMINA,PROSTAGLANDINA};
    
    private final TIPO_COMPOSTO tipo;
    private Celula emissor;
    private double temperatura = 30.0; // ?????
    private final Posicao pos;
    private int diametro = 8;
    private int quantidade = 0;

    public CompostoQuimico(TIPO_COMPOSTO tipo,int quantidade,Posicao pos,Celula emissor){
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.pos = pos;
        this.emissor = emissor;
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

    public Celula getEmissor() {
        return emissor;
    }

    public void setEmissor(Celula emissor) {
        this.emissor = emissor;
    }
    
    public double getTemperatura() {
        return temperatura;
    }
    
    public void aumentaDiametro(int tam){
        diametro += tam;
    }
    
}