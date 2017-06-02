package ufrrj.bruno.quimica;

import java.awt.Color;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import ufrrj.bruno.SistemaImunologico;

/**
 * Conjunto de compostos quimicos. <br>
 * @param compostos compostos quimicos(posicao,tipo,raio,intensidade) atuais.
 * @author Bruno Prado
 */
public class CamadaSobreposta{
    
    private final SistemaImunologico sistema;
    public final ConcurrentLinkedQueue<CompostoQuimico> compostos = new ConcurrentLinkedQueue<>();
    
    //=====| STATIC |=====//
    public static final Color corPAMP = new Color(255,150,150);
    
    public CamadaSobreposta(SistemaImunologico sis){
        sistema = sis;
        iniciaThread();
    }
    
    private Timer timer;
    
    public void iniciaThread(){
        timer = new Timer("Camada Quimica");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (Iterator<CompostoQuimico> i = compostos.iterator(); i.hasNext();) {
                    CompostoQuimico composto = i.next();
                    composto.aumentaDiametro(6);
                    if(composto.getQuantidade() > 1){
                        composto.diminuiQuantidade(1);
                    } else {
                        i.remove();
                    }
                }
            }
        }, 0,100); //(int) (sistema.getParametro("TEMPO_PROPAGACAO_QUIMICOS") * sistema.getVelocidade())
    }
    
    public void pausaThread() {
        timer.cancel();
    }
    
//    @Override
//    public void run() {
//        while(true) {
////            while(sistema.pausada){
////                pausa(2);
////            }            
//            for (Iterator<CompostoQuimico> i = compostos.iterator(); i.hasNext();) {
//                CompostoQuimico composto = i.next();
//                composto.aumentaDiametro(6);
//                if(composto.getQuantidade() > 1){
//                    composto.diminuiQuantidade(1);
//                } else {
//                    i.remove();
//                }
//            }
//            
//            pausa((int) (sistema.getParametro("TEMPO_PROPAGACAO_QUIMICOS") * sistema.getVelocidade()));
//        }
//    }   
}