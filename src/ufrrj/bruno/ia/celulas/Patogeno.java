package ufrrj.bruno.ia.celulas;

import java.awt.Polygon;
import ufrrj.bruno.ia.atributos.Poligono;
import ufrrj.bruno.ia.atributos.Posicao;
import ufrrj.bruno.ia.log.Virus;
import ufrrj.bruno.ia.quimica.CompostoQuimico;
import ufrrj.bruno.ia.quimica.CompostoQuimico.TIPO_COMPOSTO;

public class Patogeno extends Celula{
    
    private final Virus patogeno;
    private final Poligono forma;
    
    //====| RUNTIME |=====//
    private long inicio = System.currentTimeMillis();
    private Celula prox = null;
    private boolean processando = false;
    private long inicioProc = Long.MAX_VALUE;
    
    public Patogeno() {
        super(TIPO_CELULA.PATOGENO);
        
        setVelMovimento(2);
        patogeno = new Virus();
        forma = new Poligono(patogeno.getnLados(),posicao);
        
        if(sistema.isDebug()) { sistema.imprime("Novo patogeno com identificador: "  + patogeno.getIdentificador()); }
        emiteQuimica(TIPO_COMPOSTO.PAMP);
    }
    
    public Patogeno(Virus tipo) {
        super(TIPO_CELULA.PATOGENO);
        forma = new Poligono(tipo.getnLados(),posicao);
        this.patogeno = tipo;
        tipo.setQuantidade(tipo.getQuantidade()+1);
        emiteQuimica(TIPO_COMPOSTO.PAMP);
    }
    
    public Patogeno(Virus tipo,Posicao pos) {
        super(TIPO_CELULA.PATOGENO,pos);
        forma = new Poligono(tipo.getnLados(),posicao);
        this.patogeno = tipo;
        tipo.setQuantidade(tipo.getQuantidade()+1);
        emiteQuimica(TIPO_COMPOSTO.PAMP);
    }
    
    public void clona(){
        sistema.adicionaCelula(new Patogeno(patogeno,posicao));
    }
    
    public void clona(Posicao p){
        sistema.adicionaCelula(new Patogeno(patogeno,p));
        patogeno.add();
    }
    
    @Override
    public void loop() {   
        if((System.currentTimeMillis() - inicio) >= (sistema.getParametro("DELAY_PROPAGACAO") * sistema.getVelocidade())){
            inicio += sistema.getParametro("DELAY_PROPAGACAO")  * sistema.getVelocidade();
            emiteQuimica(TIPO_COMPOSTO.PAMP);
        }
        
        if(prox != null && (!sistema.getCelulas().contains(prox))) {
            processando = false;
            prox = null;
        }
        
        if(processando){
            if((System.currentTimeMillis() - inicioProc) >= 1000){
                sistema.eliminaCelula(prox);  
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
        
        for(Celula celula : sistema.getCelulas()){  
            if(celula.getTipo() != TIPO_CELULA.LINFOCITO) continue;
            
            double dist = calculaDistancia(celula.posicao);
            if(maisProx > dist) {
                maisProx = dist;
                prox = celula;
            }
        }
    }

    public Polygon getForma() {
        return forma.getPoligono();
    }

    public Virus getVirus() {
        return patogeno;
    }

    public long getInicio() {
        return inicio;
    }

    @Override
    public String toString() {
        return "Patogeno{id = " + getId() + ", posicao = " + posicao + ", inicio=" + inicio + '}';
    }
    
}