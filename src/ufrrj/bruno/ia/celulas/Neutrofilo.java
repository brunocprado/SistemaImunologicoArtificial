
package ufrrj.bruno.ia.celulas;

import ufrrj.bruno.ia.SistemaImunologico;

public class Neutrofilo extends Celula{
    
    public Neutrofilo(SistemaImunologico sistema){
        super(sistema,false);
        setTipo(TIPO_CELULA.Neutrofilo);
    }

    @Override
    public void loop() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
