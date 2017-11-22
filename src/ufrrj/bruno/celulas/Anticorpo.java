package ufrrj.bruno.celulas;

import ufrrj.bruno.atributos.Posicao;

/**
 *
 * @author bruno
 */
public class Anticorpo extends Celula{

    private final int formato;
    
    public Anticorpo(TIPO_CELULA tipo, Posicao pos,int formato) {
        super(tipo, pos);
        this.formato = formato;
    }
    
    @Override
    public void loop() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
