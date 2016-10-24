package ufrrj.bruno.ia.celulas;

import ufrrj.bruno.ia.atributos.Posicao;
import java.awt.Point;
import java.util.Iterator;
import ufrrj.bruno.ia.SistemaImunologico;
import ufrrj.bruno.ia.quimica.CamadaSobreposta;
import ufrrj.bruno.ia.quimica.CompostoQuimico;

public class Macrofago extends Celula implements Runnable{
    
    public Macrofago(SistemaImunologico sistema){
        super(sistema,TIPO_CELULA.Macrofago);
        setVelMovimento(2);
    }
    
    private Point verificaMaisProximo(){
        Point maisProximo = null;
        double distMaisProximo = Double.MAX_VALUE;
//        try {
//            for (Iterator<Celula> it = mundo.getCelulas().iterator(); it.hasNext();) {
//                Celula celula = it.next();
//                if(!celula.getClass().getSimpleName().equals("Invasor")){ continue; }         
//                double dist = Math.abs(getPosicao().x - celula.getPosicao().x)  +  Math.abs(getPosicao().y - celula.getPosicao().y);
//                if(dist < distMaisProximo && dist != 0){
//                    distMaisProximo = dist;
//                    maisProximo = celula.getPosicao();
//                }
//            }
//        } catch (Exception ex) {
//            pausa(2);
//            return verificaMaisProximo\
//        }    
        return maisProximo;
    }
    
    @Override
    public void loop(){
        Posicao pos = getPosicao();
        
        for (Iterator<CompostoQuimico> i = getSistema().getCamada().compostos.iterator(); i.hasNext();) {
            CompostoQuimico composto = i.next();
            //VERIFICA DISTANCIA EUCLIDIANA
            double deltaX = (pos.getX() + 4) - (composto.x + 2);
            double deltaY = (pos.getY() + 4) - (composto.y + 2);
            
            if(Math.sqrt(deltaX * deltaX + deltaY*deltaY) <= composto.raio){
                move(new Posicao(composto.x,composto.y));
                break;
            }
            
            
        }
    }
    
    @Override
    public void run() {
//        while(ativa){       
//            getPosicao().setPosicao(getPosicao().getX() + 1, getPosicao().getY() + 1);
//            Point pos = verificaMaisProximo();
//            if(pos == null){ 
//                pausa(5);               
//                continue;
//            }
            //EXECUTA MOVIMENTO
//            double deltaX = pos.x - (getPosicao().x - 10);
//            double deltaY = pos.y - (getPosicao().y - 10);
//            if(pos == getPosicao()){continue;}
//           
//            double angulo = Math.atan2(deltaY,deltaX);    
//            double movX = getPosicao().x + (getVelMovimento() * Math.cos(angulo));
//            double movY = getPosicao().y + (getVelMovimento() * Math.sin(angulo));
//            if(deltaX == 0){ movX = getPosicao().x; }
//            if(deltaY == 0){ movY = getPosicao().y; }
//            setPosicao(movX,movY);

            //pausa(20);   
       // }
        //t.interrupt();
    }

}