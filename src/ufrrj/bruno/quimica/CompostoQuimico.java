package ufrrj.bruno.quimica;

import ufrrj.bruno.atributos.Posicao;
import ufrrj.bruno.celulas.Celula;

public class CompostoQuimico {   
    
    /**
     * PAMP - Padrões moleculares associados a patógenos
     * TNFA - Factor de necrose tumoral
    */
    public static enum TIPO_COMPOSTO {PAMP,HISTAMINA,CITOCINA,INTERLEUCINA1,TNFA};
    
    private TIPO_COMPOSTO tipo;
    private Celula emissor;
    private double temperatura = 30.0; // ?????
    private Posicao pos;
    private int diametro = 8;
    private int quantidade = 0;
    public double opacidade = 1.0;

    public CompostoQuimico(){ } //SÓ PRA SER POSSÍVEL (DE)SERIALIZAR
    
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
        opacidade -= 0.025 * qt;
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