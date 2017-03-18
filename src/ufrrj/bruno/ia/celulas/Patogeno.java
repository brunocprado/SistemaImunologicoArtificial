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
    
    public Patogeno() {
        super(TIPO_CELULA.Patogeno);
        
        SistemaImunologico sistema = SistemaImunologico.getInstancia();

        setVelMovimento(2);
        tipo = new Virus();
        forma = new Poligono(tipo.getnLados(),getPosicao());
        
        if(sistema.isDebug()) { sistema.imprime("Novo patogeno com identificador: "  + tipo.getIdentificador()); }
        emiteQuimica();
    }
    
    public Patogeno(Virus tipo) {
        super(TIPO_CELULA.Patogeno);
        forma = new Poligono(tipo.getnLados(),getPosicao());
        this.tipo = tipo;
        tipo.setQuantidade(tipo.getQuantidade()+1);
        emiteQuimica();
    }
    
    public Patogeno(Virus tipo,Posicao pos) {
        super(TIPO_CELULA.Patogeno,pos);
        forma = new Poligono(tipo.getnLados(),getPosicao());
        this.tipo = tipo;
        tipo.setQuantidade(tipo.getQuantidade()+1);
        emiteQuimica();
    }
    
    private void emiteQuimica(){
        Posicao tmp = new Posicao(getPosicao().getX(), getPosicao().getY());
        getSistema().getCamada().compostos.add(new CompostoQuimico(TIPO_COMPOSTO.PAMP, 40,tmp,this));
    }
    
    public void clona(){
        getSistema().adicionaCelula(new Patogeno(tipo,getPosicao()));
    }
    
    public void clona(Posicao p){
        getSistema().adicionaCelula(new Patogeno(tipo,p));
        tipo.add();
    }
    
    @Override
    public void loop() {   
        if((System.currentTimeMillis() - inicio) >= (getSistema().getParametro("DELAY_PROPAGACAO") * getSistema().getVelocidade())){
            inicio += getSistema().getParametro("DELAY_PROPAGACAO") * getSistema().getVelocidade();
            emiteQuimica();
        }
        
        //IMPLEMENTACAO BASICA MULTIPLICACAO (EXEMPLO)
        
        double maisProx = Double.MAX_VALUE;
        Celula prox = null;
        
        for(Celula celula : getSistema().getCelulas()){  
            if(celula.getTipo() != TIPO_CELULA.Linfocito) continue;
            
            double dist = calculaDistancia(celula.getPosicao());
            if(maisProx > dist) {
                maisProx = dist;
                prox = celula;
            }
        }
        
	if(prox != null && maisProx < 6){
            clona(prox.getPosicao());
            getSistema().eliminaCelula(prox);  
        }
        if(prox != null) move(prox.getPosicao());
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
