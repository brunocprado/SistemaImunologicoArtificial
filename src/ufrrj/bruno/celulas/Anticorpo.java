package ufrrj.bruno.celulas;

import ufrrj.bruno.atributos.Posicao;

/**
 *
 * @author bruno
 */
public class Anticorpo extends Celula{

    private final int formato;
    
    public Anticorpo(Posicao pos,int formato) {
        super(TIPO_CELULA.ANTICORPO);
        this.formato = formato;
    }
    
    @Override
    public void loop() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
