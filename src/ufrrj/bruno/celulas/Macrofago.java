package ufrrj.bruno.celulas;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import ufrrj.bruno.SistemaImunologico;
import static ufrrj.bruno.celulas.Macrofago.ESTADO.*;
import ufrrj.bruno.quimica.CompostoQuimico;

public class Macrofago extends Celula{

    private boolean flag;
    
    public static enum ESTADO {REPOUSO,FAGOCITANDO,ATIVO};
    
    //======| Fagocitacao |======//
    private ESTADO estado = REPOUSO;
    private Patogeno alvo = null;
    private long tempoDetectado;
    private final ConcurrentLinkedQueue<Celula> celulas = SistemaImunologico.getInstancia().getCelulas();
    
    public Macrofago(){
        super(TIPO_CELULA.MACROFAGO);
        setVelMovimento(2);
    }

    public ESTADO getEstado() {
        return estado;
    }

    @Override
    public void loop(){
        
        if(!celulas.contains(alvo) || alvo == null) { estado = REPOUSO; alvo = null; }
        if(estado == FAGOCITANDO) return;
                
        if(alvo != null && celulas.contains(alvo)){
            if(calculaDistancia(alvo.getPosicao()) <= 4 && celulas.contains(alvo)){
                fagocita();
            }
            move(alvo.getPosicao());
            return;
        }
        
        CompostoQuimico composto;
        for (Iterator<CompostoQuimico> i = sistema.getCamada().compostos.iterator(); i.hasNext();) {
            composto = i.next();
            
            if(composto.getEmissor().getTipo() != TIPO_CELULA.PATOGENO) continue;
            
            double dist = calculaDistancia(composto.getPos());      
            if(dist <= composto.getDiametro()/2 + 6){
                if(composto.getEmissor() != null && !celulas.contains(composto.getEmissor())) continue;
                alvo = (Patogeno) composto.getEmissor();
                tempoDetectado = System.currentTimeMillis();
                if(dist <= 4){
                    fagocita();
                }
                if(alvo != null) {
                    if(estado == REPOUSO && !flag) emiteQuimica(CompostoQuimico.TIPO_COMPOSTO.CITOCINA);
                    estado = ATIVO;
//                    System.out.println(getId() + " Detectou " + alvo + " " + System.currentTimeMillis());
                    sistema.addTemporizacao((int) (System.currentTimeMillis() - alvo.getInicio()));
                    move(alvo.getPosicao());
                }
                break;
            }
        }
        flag = false;
    }

    private void fagocita(){
        estado = FAGOCITANDO;
        
        ScheduledExecutorService thread = Executors.newScheduledThreadPool(1);
 
        thread.schedule(() -> {
            if(!celulas.contains(alvo) || alvo == null) { estado = REPOUSO; alvo = null; return false; }
            if(alvo != null && celulas.contains(alvo)){
                alvo.getVirus().setQuantidade(alvo.getVirus().getQuantidade() - 1);
                sistema.eliminaCelula(alvo); 
                alvo.quimica.cancel();
                
                if(sistema.isDebug()){
                    sistema.imprime("Patogeno " + alvo.getId()
                            + " [<span style='color:red;'>" + alvo.getVirus().getIdentificador()+ "</span>] eliminado. {Tempo de detecção : " + (tempoDetectado - alvo.getInicio())
                            + "ms, Tempo até ser eliminado: " + (System.currentTimeMillis() - alvo.getInicio()) + "ms}");
                }             
            }
            estado = REPOUSO; //MUDAR ISSO
            flag = true;
//            loop();
            alvo = null;           
            return true;
        },
        sistema.getParametro("TEMPO_FAGOCITACAO"),
        TimeUnit.MILLISECONDS);
        
        thread.shutdown();
    }
    
    @Override
    public String toString(){
        return "\nMacrofago{estado = " + estado + ",posicao = " + posicao + "}";
    }

}