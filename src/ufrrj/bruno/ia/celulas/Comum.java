
package ufrrj.bruno.ia.celulas;

import ufrrj.bruno.ia.SistemaImunologico;


public class Comum extends Celula {
    
    public Comum(SistemaImunologico sistema){
        super(sistema,false);
        setTipo(TIPO_CELULA.Comum);
    }

    @Override
    public void loop() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
