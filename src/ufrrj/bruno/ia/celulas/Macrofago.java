package ufrrj.bruno.ia.celulas;

import java.awt.Point;
import java.util.Iterator;
import ufrrj.bruno.ia.Celula;
import ufrrj.bruno.ia.Mundo;

public class Macrofago extends Celula implements Runnable{
    
    public Macrofago(Mundo mundo){
        super(mundo,true);
        setVelMovimento(2);
    }
    
    private Point verificaMaisProximo(){
        Point maisProximo = null;
        double distMaisProximo = Double.MAX_VALUE;
        try {
            for (Iterator<Celula> it = mundo.getCelulas().iterator(); it.hasNext();) {
                Celula celula = it.next();
                if(!celula.getClass().getSimpleName().equals("Invasor")){ continue; }         
                double dist = Math.abs(getPosicao().x - celula.getPosicao().x)  +  Math.abs(getPosicao().y - celula.getPosicao().y);
                if(dist < distMaisProximo && dist != 0){
                    distMaisProximo = dist;
                    maisProximo = celula.getPosicao();
                }
            }
        } catch (Exception ex) {
            pausa(2);
            return verificaMaisProximo();
        }    
        return maisProximo;
    }
    
    @Override
    public void run() {
        while(ativa){       
            Point pos = verificaMaisProximo();
            if(pos == null){ 
                pausa(5);               
                continue;
            }
            //EXECUTA MOVIMENTO
            double deltaX = pos.x - (getPosicao().x - 10);
            double deltaY = pos.y - (getPosicao().y - 10);
            if(pos == getPosicao()){continue;}
           
            double angulo = Math.atan2(deltaY,deltaX);    
            double movX = getPosicao().x + (getVelMovimento() * Math.cos(angulo));
            double movY = getPosicao().y + (getVelMovimento() * Math.sin(angulo));
            if(deltaX == 0){ movX = getPosicao().x; }
            if(deltaY == 0){ movY = getPosicao().y; }
            setPosicao(movX,movY);

            pausa(20);   
        }
        //t.interrupt();
    }

}