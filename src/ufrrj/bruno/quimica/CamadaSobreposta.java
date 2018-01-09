package ufrrj.bruno.quimica;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.awt.Color;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import javafx.application.Platform;
import ufrrj.bruno.SistemaImunologico;
import ufrrj.bruno.renderizacao.GraficoAvancado;

/**
 * Conjunto de compostos quimicos. <br>
 * @param compostos compostos quimicos(posicao,tipo,raio,intensidade) atuais.
 * @author Bruno Prado
 */
public class CamadaSobreposta{
    
    private final SistemaImunologico sistema = SistemaImunologico.getInstancia();
    @JsonIgnore
    public final ConcurrentLinkedQueue<CompostoQuimico> compostos = new ConcurrentLinkedQueue<>();
    
    //=====| STATIC |=====//
    public static final Color corPAMP = new Color(255,150,150);
    
    public CamadaSobreposta(){
        iniciaThread();
    }
    
    @JsonIgnore
    private Timer timer;
    
    private final GraficoAvancado grafico = GraficoAvancado.getInstancia();
    
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
//                        composto.setOpacity(0);
//                        Platform.runLater(() -> {
//                            grafico.remove(composto);
//                            
//                        });
//                        composto.setOpacity(0.001);
//                        Timer t = new Timer("Render Quimica");
//                        t.schedule(new TimerTask() {
//                            @Override
//                            public void run() { composto.setVisible(false); }
//                        }, 0,20);

//                        grafico.remove(composto);
                    }
                }
            }
        }, 0,(int) (sistema.getParametro("TEMPO_PROPAGACAO_QUIMICOS") * sistema.getVelocidade())); //(int) (sistema.getParametro("TEMPO_PROPAGACAO_QUIMICOS") * sistema.getVelocidade())
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