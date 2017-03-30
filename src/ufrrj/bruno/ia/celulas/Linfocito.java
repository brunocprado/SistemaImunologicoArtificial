package ufrrj.bruno.ia.celulas;

import java.util.Random;

public class Linfocito extends Celula{
    
    private final int receptor;
    
    public Linfocito() {
        super(TIPO_CELULA.LINFOCITO);
        receptor = new Random().nextInt(Integer.MAX_VALUE);
    }

//    public Linfocito(SistemaImunologico sistema,int receptor) {
//        super(sistema,TIPO_CELULA.Linfocito);
//        this.receptor = receptor;
//    }
    
    @Override
    public void loop() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}