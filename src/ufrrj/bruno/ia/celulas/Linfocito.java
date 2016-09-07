
package ufrrj.bruno.ia.celulas;

import ufrrj.bruno.ia.SistemaImunologico;


public class Linfocito extends Celula{
    
    public Linfocito(SistemaImunologico sistema) {
        super(sistema,false);
        setTipo(TIPO_CELULA.Linfocito);
    }

    @Override
    public void loop() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
