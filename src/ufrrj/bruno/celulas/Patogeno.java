package ufrrj.bruno.celulas;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Timer;
import java.util.TimerTask;
import ufrrj.bruno.atributos.Poligono;
import ufrrj.bruno.atributos.Posicao;
import ufrrj.bruno.log.Virus;
import ufrrj.bruno.quimica.CompostoQuimico.TIPO_COMPOSTO;

public class Patogeno extends Celula{

    public final Virus virus;
    private final Poligono forma;
    
    //====| RUNTIME |=====//
    
    private Celula prox = null;
    private boolean processando = false;
    private long inicioProc = Long.MAX_VALUE;
    
    public Patogeno() {
        super(TIPO_CELULA.PATOGENO);
        
        setVelMovimento(2);
        virus = new Virus();
        forma = new Poligono(virus.getnLados());
        
        if(sistema.isDebug()) { sistema.imprime("Novo patogeno com identificador: "  + virus.getIdentificador()); }
        emiteQuimica(TIPO_COMPOSTO.PAMP);
        inicia();
    }
    
    public Patogeno(Virus tipo) {
        super(TIPO_CELULA.PATOGENO);
        forma = new Poligono(tipo.getnLados());
        this.virus = tipo;
        tipo.add();
        emiteQuimica(TIPO_COMPOSTO.PAMP);
        inicia();
    }
    
    public Patogeno(Virus tipo,Posicao pos) {
        super(TIPO_CELULA.PATOGENO,pos);
        forma = new Poligono(tipo.getnLados());
        this.virus = tipo;
        tipo.add();
        emiteQuimica(TIPO_COMPOSTO.PAMP);
        inicia();
    }
    
    @JsonIgnore
    public Timer quimica = new Timer("QUIM");
    private void inicia(){
        quimica.schedule(new TimerTask() {
            @Override
            public void run() { emiteQuimica(TIPO_COMPOSTO.PAMP); }
        }, 0,sistema.getParametro("DELAY_PROPAGACAO"));
    }
    
    public void clona(){
        sistema.getPatogenos().add(new Patogeno(virus,posicao));
    }
    
    public void clona(Posicao p){
        sistema.getPatogenos().add(new Patogeno(virus,p));
    }
    
    @Override
    public void loop() {   
        
        if(prox != null && (!sistema.getLinfocitos().contains(prox))) {
            processando = false;
            prox = null;
        }
        
        if(processando){
            if((System.currentTimeMillis() - inicioProc) >= 1000){
                sistema.getLinfocitos().remove(prox);  
                clona(prox.posicao);
                processando = false;
                prox = null;
                inicioProc = Long.MAX_VALUE;
            } else {  return; }
        }              
        
        if(prox != null){
            if(calculaDistancia(prox.posicao) < 6){
                processando = true;
                inicioProc = System.currentTimeMillis();
            } else {
                move(prox.posicao);
            }
        }
        
        double maisProx = Double.MAX_VALUE;
        
        for(Celula celula : sistema.getLinfocitos()){              
            double dist = calculaDistancia(celula.posicao);
            if(maisProx > dist) {
                maisProx = dist;
                prox = celula;
            }
        }
    }

    public Poligono getForma() {
        return forma;
    }

    public Virus getVirus() {
        return virus;
    }

    @Override
    public String toString() {
        return "\nPatogeno{id = " + getId() + ", posicao = " + posicao + ", inicio=" + inicio + '}';
    }
    
}