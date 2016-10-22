package ufrrj.bruno.ia.celulas;

import ufrrj.bruno.ia.atributos.Posicao;
import java.awt.Point;
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
        //move(new Posicao(640,330));
        Posicao pos = getPosicao();
        CamadaSobreposta camada = getSistema().getCamada();
        CompostoQuimico tmp = camada.getPosicao(pos.getX()/8, pos.getY()/8);
        if(tmp != null && tmp.getQuantidade() > 0){
            //QUIMICO DETECTADO
            //move(new Posicao(640,330));
            //ESQUERDA
            if(camada.getPosicao((pos.getX()/8)-1, pos.getY()/8) != null && camada.getPosicao((pos.getX()/8)-1, pos.getY()/8).getQuantidade() > 0) {
                move(new Posicao(0,pos.getY()));
            }
            //DIREITA
            if(camada.getPosicao((pos.getX()/8)-1, pos.getY()/8) != null && camada.getPosicao((pos.getX()/8)-1, pos.getY()/8).getQuantidade() > 0) {
                move(new Posicao(Integer.MAX_VALUE,pos.getY()));
            }
            //CIMA
            if(camada.getPosicao((pos.getX()/8)-1, pos.getY()/8) != null && camada.getPosicao((pos.getX()/8)-1, pos.getY()/8).getQuantidade() > 0) {
                move(new Posicao(pos.getX(),0));
            }
            //
            if(camada.getPosicao((pos.getX()/8)-1, pos.getY()/8) != null && camada.getPosicao((pos.getX()/8)-1, pos.getY()/8).getQuantidade() > 0) {
                move(new Posicao(pos.getX(),Integer.MAX_VALUE));
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