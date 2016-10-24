package ufrrj.bruno.ia.quimica;

public class CompostoQuimico {   
    
    public enum TIPO_COMPOSTO {HISTAMINA,PROSTAGLANDINA};
    
    private TIPO_COMPOSTO tipo;
    private double temperatura = 30.0; // ?????
    public int x;
    public int y;
    public int raio = 8;
    private int quantidade = 0;

    public CompostoQuimico(TIPO_COMPOSTO tipo,int quantidade,int x,int y){
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.x = x;
        this.y = y; 
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
