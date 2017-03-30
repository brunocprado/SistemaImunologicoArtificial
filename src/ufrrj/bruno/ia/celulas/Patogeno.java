package ufrrj.bruno.ia.celulas;

import java.awt.Polygon;
import ufrrj.bruno.ia.SistemaImunologico;
import ufrrj.bruno.ia.atributos.Poligono;
import ufrrj.bruno.ia.atributos.Posicao;
import ufrrj.bruno.ia.log.Virus;
import ufrrj.bruno.ia.quimica.CompostoQuimico;
import ufrrj.bruno.ia.quimica.CompostoQuimico.TIPO_COMPOSTO;

public class Patogeno extends Celula{
    
    private final long entrada = System.currentTimeMillis();
    private final Virus tipo;
    private final Poligono forma;
    
    //====| RUNTIME |=====//
    private long inicio = System.currentTimeMillis();
    private Celula prox = null;
    private boolean processando = false;
    private long inicioProc = Long.MAX_VALUE;
    
    
    public Patogeno() {
        super(TIPO_CELULA.Patogeno);
        
        setVelMovimento(2);
        tipo = new Virus();
        forma = new Poligono(tipo.getnLados(),posicao);
        
        if(sistema.isDebug()) { sistema.imprime("Novo patogeno com identificador: "  + tipo.getIdentificador()); }
        emiteQuimica();
    }
    
    public Patogeno(Virus tipo) {
        super(TIPO_CELULA.Patogeno);
        forma = new Poligono(tipo.getnLados(),posicao);
        this.tipo = tipo;
        tipo.setQuantidade(tipo.getQuantidade()+1);
        emiteQuimica();
    }
    
    public Patogeno(Virus tipo,Posicao pos) {
        super(TIPO_CELULA.Patogeno,pos);
        forma = new Poligono(tipo.getnLados(),posicao);
        this.tipo = tipo;
        tipo.setQuantidade(tipo.getQuantidade()+1);
        emiteQuimica();
    }
    
    private void emiteQuimica(){
        Posicao tmp = new Posicao(posicao.getX(), posicao.getY());
        sistema.getCamada().compostos.add(new CompostoQuimico(TIPO_COMPOSTO.PAMP, 40,tmp,this));
    }
    
    public void clona(){
        sistema.adicionaCelula(new Patogeno(tipo,posicao));
    }
    
    public void clona(Posicao p){
        sistema.adicionaCelula(new Patogeno(tipo,p));
        tipo.add();
    }
    
    @Override
    public void loop() {   
        if((System.currentTimeMillis() - inicio) >= (sistema.getParametro("DELAY_PROPAGACAO") * sistema.getVelocidade())){
            inicio += sistema.getParametro("DELAY_PROPAGACAO") * sistema.getVelocidade();
            emiteQuimica();
        }
        
        if(processando){
            if((System.currentTimeMillis() - inicioProc) >= 300){
//                System.out.println(System.currentTimeMillis() - inicioProc);
//                System.out.println(prox);
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
            if(celula.getTipo() != TIPO_CELULA.Linfocito) continue;
            
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

    public long getEntrada() {
        return entrada;
    }

    public Virus getVirus() {
        return tipo;
    }
    
}