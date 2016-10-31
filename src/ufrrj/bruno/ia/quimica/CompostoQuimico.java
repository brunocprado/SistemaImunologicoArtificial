package ufrrj.bruno.ia.quimica;

public class CompostoQuimico {   
    
    public enum TIPO_COMPOSTO {HISTAMINA,PROSTAGLANDINA};
    
    private TIPO_COMPOSTO tipo;
    private double temperatura = 30.0; // ?????
    private final int x;
    private final int y;
    private int raio = 8;
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRaio() {
        return raio;
    }

    public void setRaio(int raio) {
        this.raio = raio;
    }
    
    public void aumentaRaio(int tam){
        raio += tam;
    }
    
}
