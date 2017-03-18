package ufrrj.bruno.ia.quimica;

import java.awt.Color;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import ufrrj.bruno.ia.SistemaImunologico;

/**
 * Conjunto de compostos quimicos. <br>
 * @param compostos compostos quimicos(posicao,tipo,raio,intensidade) atuais.
 * @author Bruno Prado
 */
public class CamadaSobreposta implements Runnable{
    
    private final SistemaImunologico sistema;
    public ConcurrentLinkedQueue<CompostoQuimico> compostos = new ConcurrentLinkedQueue<>();
    
    //=====| STATIC |=====//
    public static final Color corPAMP = new Color(255,150,150);
    
    public CamadaSobreposta(SistemaImunologico sistema){
        this.sistema = sistema;
        Thread t = new Thread(this,"Camada Quimica");
        t.start();
    }
    
    private void pausa(int tempo){
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException ex) {
            Logger.getLogger(CamadaSobreposta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        while(true) {
            while(sistema.pausada){
                pausa(2);
            }            
            for (Iterator<CompostoQuimico> i = compostos.iterator(); i.hasNext();) {
                CompostoQuimico composto = i.next();
                composto.aumentaDiametro(6);
                if(composto.getQuantidade() > 1){
                    composto.diminuiQuantidade(1);
                } else {
                    i.remove();
                }
            }
            
            pausa((int) (sistema.getParametro("TEMPO_PROPAGACAO_QUIMICOS") * sistema.getVelocidade()));
        }
    }   
}