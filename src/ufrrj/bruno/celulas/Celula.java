package ufrrj.bruno.celulas;

import java.util.Random;
import javafx.scene.Cursor;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import ufrrj.bruno.SistemaImunologico;
import static ufrrj.bruno.celulas.Celula.TIPO_CELULA.MACROFAGO;
import ufrrj.bruno.quimica.CompostoQuimico;
import ufrrj.bruno.renderizacao.GraficoAvancado;
import ufrrj.bruno.telas.InfoCelula;

abstract public class Celula extends ImageView{
    
    public static enum TIPO_CELULA {COMUM,LINFOCITO,NEUTROFILO,PATOGENO,MACROFAGO,CELULAB,ANTICORPO};
    
    protected long inicio = System.currentTimeMillis();
    
    private static int qt = 0;
    protected final int id;
    protected final TIPO_CELULA tipo;
    //=====| CARACTERISTICAS |=====//
    public int tamanhoX,tamanhoY;
    private double velMovimento = 2;
    //========|  RUNTIME  |=======//
    protected final SistemaImunologico sistema = SistemaImunologico.getInstancia();
    protected Tooltip tp;
    
    public Celula(TIPO_CELULA tipo){
        qt++; id = qt; 
        this.tipo = tipo;
        Random gerador = new Random();
        
        setX(gerador.nextInt(sistema.getParametro("TAMX")));
        setY(gerador.nextInt(sistema.getParametro("TAMY")));

        switch(tipo){
            case MACROFAGO: setImage(sistema.macrofago); break;
            case NEUTROFILO: setImage(sistema.neutrofilo); break;
            case LINFOCITO: setImage(sistema.linfocito); break;
            case PATOGENO: setImage(sistema.comum); break;
        }
        
//        setCache(true);
        setFitHeight(10); setFitWidth(10);
        
        if(tipo == TIPO_CELULA.NEUTROFILO) return;
        
        setCursor(Cursor.HAND);
        
        setOnMouseEntered((e)->{        
            tp = new Tooltip();
            tp.setGraphic(new InfoCelula(this));
            tp.setShowDelay(javafx.util.Duration.millis(500));   
            tp.setHideDelay(javafx.util.Duration.millis(500));
            Tooltip.install(this, tp);
        });
        
    }

    public Celula(TIPO_CELULA tipo,double x,double y){
        qt++; id = qt; this.tipo = tipo;
        
        GraficoAvancado.getInstancia().renderiza(this);
        
        setX(x);
        setY(y);
        
        switch(tipo){
            case MACROFAGO: setImage(sistema.macrofago); break;
            case NEUTROFILO: setImage(sistema.neutrofilo); break;
            case LINFOCITO: setImage(sistema.linfocito); break;
            case PATOGENO: setImage(sistema.comum); break;
        }
        
        setFitHeight(10); setFitWidth(10);
        
        setCursor(Cursor.HAND);
        
        setOnMouseEntered((e)->{
            tp = new Tooltip();
            tp.setGraphic(new InfoCelula(this));
            tp.setShowDelay(javafx.util.Duration.millis(500));   
            tp.setHideDelay(javafx.util.Duration.millis(500));
            Tooltip.install(this, tp);
        });
        
    }
    
    public void move(Celula dest){
        double deltaX = dest.getX() - getX();
        double deltaY = dest.getY() - getY();

        double angulo = Math.atan2(deltaY,deltaX);    
        double movX = getX() + (getVelMovimento() * Math.cos(angulo));
        double movY = getY() + (getVelMovimento() * Math.sin(angulo));

        if(deltaX == 0){ movX = getX(); }
        if(deltaY == 0){ movY =  getY(); }
        setPosicao(movX,movY);
    }
    
    public double getVelMovimento() {
        return velMovimento;
    }

    public void setVelMovimento(double velocidade) {
        this.velMovimento = velocidade;
    }

    public void setPosicao(double x,double y) {
//        posicao.setX(x);
        setX(x); setY(y);
//        posicao.setY(y);
    }

    public TIPO_CELULA getTipo() {
        return tipo;
    }
    
    public int getID() {
        return id;
    }
    
    public double calculaDistancia(double x, double y){
        double deltaX = getX() - x;
        double deltaY = getY() - y;
        return Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
    }
    
    protected void emiteQuimica(CompostoQuimico.TIPO_COMPOSTO tipo){
//        Posicao tmp = new Posicao((int)getX(), (int)getY());
        sistema.getCamada().compostos.add(new CompostoQuimico(tipo, 40,getX(),getY(),this));
    }
    public abstract void loop();
            
    public long getInicio() {
        return inicio;
    }
    
    @Override
    public String toString() {
        return "\nCelula{" + "tipo=" + tipo + ", tamanhoX=" + tamanhoX + ", tamanhoY=" + tamanhoY + ", velMovimento=" + velMovimento + ", posicao="  + "}";
    }
   
}