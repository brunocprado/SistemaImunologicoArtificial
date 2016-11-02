package ufrrj.bruno.ia.celulas;

import ufrrj.bruno.ia.atributos.Posicao;
import java.util.Iterator;
import ufrrj.bruno.ia.SistemaImunologico;
import ufrrj.bruno.ia.quimica.CompostoQuimico;

public class Macrofago extends Celula{
    
    public Macrofago(SistemaImunologico sistema){
        super(sistema,TIPO_CELULA.Macrofago);
        setVelMovimento(2);
    }
    
    @Override
    public void loop(){
        Posicao pos = getPosicao();
        
        //TODO:
        //Armazenar Patogeno detectado (otimização)
        
        for (Iterator<CompostoQuimico> i = getSistema().getCamada().compostos.iterator(); i.hasNext();) {
            CompostoQuimico composto = i.next();
            //VERIFICA DISTANCIA EUCLIDIANA
            double deltaX = pos.getX() - composto.getX();
            double deltaY = pos.getY() - composto.getY();
            double dist = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
            if(dist <= 4){
                //INICIA FAGOCITAÇÃO
            }
            if(dist <= composto.getDiametro()/2 + 4){
                //if(getSistema().isDebug()){ getSistema().imprime("Macrofago (" + getId() + ") identificou Patogeno (" + composto.x + "," + composto.y + ")"); }
                move(new Posicao(composto.getX(),composto.getY()));
                break;
            }
        }
    }

}