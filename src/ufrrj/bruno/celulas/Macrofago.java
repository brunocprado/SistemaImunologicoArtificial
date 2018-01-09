package ufrrj.bruno.celulas;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.scene.paint.Color;
import ufrrj.bruno.SistemaImunologico;
import static ufrrj.bruno.celulas.Macrofago.ESTADO.*;
import ufrrj.bruno.log.Virus;
import ufrrj.bruno.quimica.CompostoQuimico;
import ufrrj.bruno.quimica.CompostoQuimico.TIPO_COMPOSTO;
import ufrrj.bruno.renderizacao.GraficoAvancado;

public class Macrofago extends Celula{

    private boolean flag;
    
    public static enum ESTADO {REPOUSO,FAGOCITANDO,ATIVO};
    
    //======| Fagocitacao |======//
    private ESTADO estado = REPOUSO;
    private Celula alvo = null;
    private long tempoDetectado;
    private final ConcurrentLinkedQueue<Patogeno> celulas = SistemaImunologico.getInstancia().getPatogenos();
    
    public Macrofago(){
        super(TIPO_CELULA.MACROFAGO);
        setVelMovimento(2);
    }

    public ESTADO getEstado() {
        return this.estado;
    }

    @Override
    public void loop(){
        if(!celulas.contains(alvo) || alvo == null) { estado = REPOUSO; alvo = null; }
        if(estado == FAGOCITANDO) return;
                
        if(alvo != null && celulas.contains(alvo)){
            if(calculaDistancia((int)alvo.getX(),(int)alvo.getY()) <= 4 && celulas.contains(alvo)){
                fagocita();
                if(sistema.celulasB.size() > 0) sistema.celulasB.poll().ativa(((Patogeno)alvo).getVirus());
            }
            move(alvo);
            return;
        }
        
        CompostoQuimico composto;
        for (Iterator<CompostoQuimico> i = sistema.getCamada().compostos.iterator(); i.hasNext();) {
            composto = i.next();
            
            if(composto.getTipo() != TIPO_COMPOSTO.PAMP && composto.getTipo() != TIPO_COMPOSTO.CITOCINA) continue;
            
            double dist = calculaDistancia(composto.getX(),composto.getY());      
            if(dist <= composto.getRaio() + 6){
                if(composto.getEmissor() != null && !celulas.contains(composto.getEmissor())) continue;
                alvo = composto.getEmissor();
                tempoDetectado = System.currentTimeMillis();
                if(dist <= 4){
                    fagocita();
                }
                if(alvo != null) {
                    if(estado == REPOUSO && !flag) emiteQuimica(TIPO_COMPOSTO.CITOCINA);
                    estado = ATIVO;
//                    System.out.println(getId() + " Detectou " + alvo + " " + System.currentTimeMillis());
                    try {
                        sistema.addTemporizacao((int) (System.currentTimeMillis() - alvo.getInicio()));
                    } catch (Exception e) { }

                    move(alvo);
                }
                break;
            }
        }
        flag = false;
    }

//    TODO;
//            
//    Criar stack nos patogenos com os macrofagos que estão o fagocitando
//    Ao eliminar patogeno cancelar a fagocitacao
    
    private void fagocita(){
        estado = FAGOCITANDO;
        
        ScheduledExecutorService thread = Executors.newScheduledThreadPool(1);
 
        thread.schedule(() -> {
            if(!celulas.contains(alvo) || alvo == null) { estado = REPOUSO; alvo = null; return false; }
            if(alvo != null && celulas.contains(alvo)){
                if(alvo.getTipo() == TIPO_CELULA.PATOGENO){
                    Patogeno tmp = (Patogeno) alvo;
                    tmp.getVirus().setQuantidade(tmp.getVirus().getQuantidade() - 1);
                    tmp.quimica.cancel();
                }
                sistema.getPatogenos().remove(alvo);
//                alvo.setVisible(false);
                GraficoAvancado.getInstancia().remove(alvo);
//                GraficoAvancado.getInstancia().remove(alvo);

                if(sistema.isDebug() && alvo.getTipo() == TIPO_CELULA.PATOGENO){
                    sistema.imprime("Patogeno " + alvo.getID()
                            + " [" + ((Patogeno) alvo).getVirus().getIdentificador()+ "] eliminado. {Tempo de detecção : " + (tempoDetectado - alvo.getInicio())
                            + "ms, Tempo até ser eliminado: " + (System.currentTimeMillis() - alvo.getInicio()) + "ms}",Color.YELLOW);
                }      
                
                if(alvo.getTipo() == TIPO_CELULA.PATOGENO && ((Patogeno) alvo).getVirus().getQuantidade() == 0){
                    Virus tmp = ((Patogeno) alvo).getVirus();
                    sistema.imprime("[" + tmp.getIdentificador() + "] eliminado. {Tempo de até ser eliminado : " + (System.currentTimeMillis() - tmp.getINICIO())
                            + "ms, Quantidade de anticorpos : 0}",Color.GREEN);
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

    public Celula getAlvo() {
        return alvo;
    }  
    
    @Override
    public String toString(){
        return "\nMacrofago{estado = " + estado + ",posicao = " + "}";
    }

}