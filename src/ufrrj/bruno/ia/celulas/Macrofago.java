package ufrrj.bruno.ia.celulas;

import ufrrj.bruno.ia.atributos.Posicao;
import java.util.Iterator;
import ufrrj.bruno.ia.Parametros;
import ufrrj.bruno.ia.SistemaImunologico;
import ufrrj.bruno.ia.quimica.CompostoQuimico;

public class Macrofago extends Celula{
    
    Posicao pos = getPosicao();
    //=====| Fagocitacao |======//
    private CompostoQuimico alvo = null;
    private long inicioFagocitacao;
    private boolean fagocitando = false;
    
    public Macrofago(SistemaImunologico sistema){
        super(sistema,TIPO_CELULA.Macrofago);
        setVelMovimento(2);
    }
    
    private double calculaDistancia(Posicao posicaoInicial,Posicao posicaoAlvo){
        double deltaX = posicaoInicial.getX() - posicaoAlvo.getX();
        double deltaY = posicaoInicial.getY() - posicaoAlvo.getY();
        return Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
    }
    
    @Override
    public void loop(){
        if(fagocitando){
            if(System.currentTimeMillis() - inicioFagocitacao >= Parametros.TEMPO_FAGOCITACAO){
                if(alvo.getEmissor() != null){
                    getSistema().eliminaCelula(alvo.getEmissor()); 
                    alvo.setEmissor(null);
                }          
                fagocitando = false;
                alvo = null;
                return;
            } else {
                return;
            }
        }
        
        if(alvo != null){
            if(calculaDistancia(pos,alvo.getPos()) <= 4){
                inicioFagocitacao = System.currentTimeMillis();
                fagocitando = true;
            }
            move(alvo.getPos());
            return;
        }
        
        for (Iterator<CompostoQuimico> i = getSistema().getCamada().compostos.iterator(); i.hasNext();) {
            CompostoQuimico composto = i.next();
            double dist = calculaDistancia(pos,composto.getPos());      
            if(dist <= composto.getDiametro()/2 + 4){
                //if(getSistema().isDebug()){ getSistema().imprime("Macrofago (" + getId() + ") identificou Patogeno (" + composto.x + "," + composto.y + ")"); }
                alvo = composto;
                if(dist <= 4){
                    inicioFagocitacao = System.currentTimeMillis();
                    fagocitando = true;
                }
                move(alvo.getPos());
                break;
            }
        }
    }

}