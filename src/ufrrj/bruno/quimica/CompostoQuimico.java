package ufrrj.bruno.quimica;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import ufrrj.bruno.SistemaImunologico;
import ufrrj.bruno.celulas.Celula;
import ufrrj.bruno.renderizacao.GraficoAvancado;

public class CompostoQuimico extends Circle{   
    
    /**
     * PAMP - Padrões moleculares associados a patógenos
     * TNFA - Factor de necrose tumoral
    */
    public static enum TIPO_COMPOSTO {PAMP,HISTAMINA,CITOCINA,IL2,INTERLEUCINA1,TNFA};
    
    private TIPO_COMPOSTO tipo;
    private Celula emissor;
    private double temperatura = 30.0; // ?????
    private int raio = 8;
    private int aumentoRaioPorTick = 42; //TODO
    private int quantidade = 0;
    public double opacidade = 1.0;

    double x,y;
    
    public CompostoQuimico(){ } //SÓ PRA SER POSSÍVEL (DE)SERIALIZAR
    
    public CompostoQuimico(TIPO_COMPOSTO tipo,int quantidade,double x,double y,Celula emissor){
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.emissor = emissor;
        
        this.x = x; this.y = y;
        setCenterX(x);
        setCenterY(y);
        
        setRadius(raio);
        
        if(tipo == TIPO_COMPOSTO.PAMP) setFill(Color.FIREBRICK); else setFill(Color.LIGHTGREEN);
        
        GraficoAvancado.getInstancia().renderiza(this,0); //setClip(GraficoAvancado.getInstancia().p);
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
        setOpacity(opacidade/2);
    }

    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }

    public int getRaio() {
        return raio;
    }

    public void setRaio(int raio) {
        this.raio = raio;
        setRadius(raio/2);
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
    
    public void aumentaRaio(int tam){
        raio += tam;
        setRadius(raio);
        diminuiQuantidade(1);
        if(quantidade == 1) { setVisible(false); SistemaImunologico.getInstancia().getCamada().compostos.remove(this); }
    }
    
}