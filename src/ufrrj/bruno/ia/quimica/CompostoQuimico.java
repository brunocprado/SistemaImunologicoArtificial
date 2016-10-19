package ufrrj.bruno.ia.quimica;

public class CompostoQuimico {   
    
    public enum TIPO_COMPOSTO {HISTAMINA,PROSTAGLANDINA};
    
    private TIPO_COMPOSTO tipo;
    private double temperatura; // ?????
    private int quantidade = 0;
    
    public CompostoQuimico(TIPO_COMPOSTO tipo,int quantidade){
        this.tipo = tipo;
        this.quantidade = quantidade;
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
    
}
