package ufrrj.bruno.ia.celulas;

import ufrrj.bruno.ia.SistemaImunologico;

public class Linfocito extends Celula{
    
    public static final int tempoVida = 2; 
    
    //DEVE ARMAZENAR PADROES CONHECIDOS
    
    public Linfocito(SistemaImunologico sistema) {
        super(sistema,TIPO_CELULA.Linfocito);
    }

    @Override
    public void loop() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
