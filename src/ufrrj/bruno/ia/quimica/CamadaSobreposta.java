package ufrrj.bruno.ia.quimica;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import ufrrj.bruno.ia.Parametros;
import ufrrj.bruno.ia.SistemaImunologico;

/**
 * Conjunto de compostos quimicos. <br>
 * @param compostos compostos quimicos(posicao,tipo,raio,intensidade) atuais.
 * @author Bruno Prado
 */
public class CamadaSobreposta implements Runnable{
    
    private final SistemaImunologico sistema;
    public ConcurrentLinkedQueue<CompostoQuimico> compostos = new ConcurrentLinkedQueue<>();
    private Thread t;
    private static final int tamX = Parametros.TAMX/8;
    private static final int tamY = Parametros.TAMY/8;
    
    public CamadaSobreposta(SistemaImunologico sistema){
        this.sistema = sistema;
        t = new Thread(this,"Camada Quimica");
        t.start();
//        matriz = new CompostoQuimico[Parametros.TAMY/8][Parametros.TAMX/8];
    }
    
    private void pausa(int tempo){
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException ex) {
            Logger.getLogger(CamadaSobreposta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //implementar pause
    @Override
    public void run() {
        while(true) {
            while(sistema.pausada){
                pausa(5);
            }            
            
            //for(CompostoQuimico composto : compostos){
            for (Iterator<CompostoQuimico> i = compostos.iterator(); i.hasNext();) {
                CompostoQuimico composto = i.next();
                composto.aumentaDiametro(6);
                if(composto.getQuantidade() > 1){
                    composto.diminuiQuantidade(1);
                } else {
                    i.remove();
                }
            }
        
            pausa(Parametros.TEMPO_PROPAGACAO_QUIMICOS);
        }
    }
      
    
}
