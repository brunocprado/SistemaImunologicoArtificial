package ufrrj.bruno.celulas;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Timer;
import java.util.TimerTask;
import ufrrj.bruno.atributos.Posicao;
import static ufrrj.bruno.celulas.Celula.TIPO_CELULA.ANTICORPO;
import static ufrrj.bruno.celulas.CelulaB.ESTADO.REPOUSO;
import ufrrj.bruno.log.Virus;
import ufrrj.bruno.quimica.CompostoQuimico;

/**
 *
 * @author bruno
 */
public class CelulaB extends Celula{

    public static enum ESTADO {REPOUSO,ATIVO};
    private int anticorpo;
    private Virus virus;
    //========| RUNTIME |========//
    private ESTADO estado = REPOUSO;
    
    public CelulaB(TIPO_CELULA tipo) {
        super(tipo);
        
    }
    
    public CelulaB(TIPO_CELULA tipo, Posicao pos) {
        super(tipo);
    }

    public void ativa(Virus virus){
        this.virus = virus;
        anticorpo = virus.getEpitopo();
        //INICIA TIMER PRA PRODUZIR ANTICORPOS XYZ
        inicia();
    }
    
    @JsonIgnore public Timer prodAnticorpos;
    private void inicia(){
        prodAnticorpos = new Timer("ANTI");
        prodAnticorpos.schedule(new TimerTask() {
            @Override
            public void run() { 
//                virus.anticorpos.add(new Anticorpo( anticorpo)); 
            }
        }, 0,sistema.getParametro("DELAY_PROPAGACAO"));
    }
    
    @Override
    public void loop() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getAnticorpo() {
        return anticorpo;
    }

    public ESTADO getEstado() {
        return estado;
    }

    public void setEstado(ESTADO estado) {
        this.estado = estado;
    }
    
}
