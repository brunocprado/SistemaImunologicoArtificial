package ufrrj.bruno.ia.celulas;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import ufrrj.bruno.ia.SistemaImunologico;
import ufrrj.bruno.ia.quimica.CompostoQuimico;

public class Macrofago extends Celula{
    
    //=====| Fagocitacao |======//
    private Patogeno alvo = null;
    private long tempoDetectado;
    private boolean fagocitando = false;
    private final ConcurrentLinkedQueue<Celula> celulas = SistemaImunologico.getInstancia().getCelulas();
    
    public Macrofago(){
        super(TIPO_CELULA.Macrofago);
        setVelMovimento(2);
    }

    @Override
    public void loop(){
        
        if(!celulas.contains(alvo) || alvo == null) { fagocitando = false; alvo = null; }
        if(fagocitando) return;
        
        if(alvo != null && celulas.contains(alvo)){
            if(calculaDistancia(alvo.getPosicao()) <= 4 && celulas.contains(alvo)){
                fagocitando = true;
                Fagocitacao fagocitacao = new Fagocitacao();
                fagocitacao.iniciaFagocitacao();
            }
            move(alvo.getPosicao());
            return;
        }
        
        CompostoQuimico composto;
        for (Iterator<CompostoQuimico> i = sistema.getCamada().compostos.iterator(); i.hasNext();) {
            composto = i.next();
            
            double dist = calculaDistancia(composto.getPos());      
            if(dist <= composto.getDiametro()/2 + 6){
                alvo = (Patogeno) composto.getEmissor();
                tempoDetectado = System.currentTimeMillis();
                if(dist <= 4){
                    fagocitando = true;
                    Fagocitacao fagocitacao = new Fagocitacao();
                    fagocitacao.iniciaFagocitacao();
                }
                if(alvo != null) {
                    sistema.addTemporizacao((int) (System.currentTimeMillis() - alvo.getEntrada()));
                    move(alvo.getPosicao());
                }
                break;
            }
        }
    }
    
    public class Fagocitacao implements Runnable{
        
        public void iniciaFagocitacao(){
            Thread t = new Thread(this,"Fagocitando");
            t.start();
        }
        
        @Override
        public void run() { 
            if(!celulas.contains(alvo) || alvo == null) { fagocitando = false; alvo = null; return; }
            try {
                Thread.sleep(sistema.getParametro("TEMPO_FAGOCITACAO"));
            } catch (InterruptedException ex) {
                Logger.getLogger(Macrofago.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(alvo != null && celulas.contains(alvo)){
                alvo.getVirus().setQuantidade(alvo.getVirus().getQuantidade() - 1);
                sistema.eliminaCelula(alvo); 
                
                if(sistema.isDebug()){
                    sistema.imprime("Patogeno " + alvo.getId() 
                        + " [<span style='color:red;'>" + alvo.getVirus().getIdentificador()+ "</span>] eliminado. {Tempo de detecção : " + (tempoDetectado - alvo.getEntrada()) 
                        + "ms, Tempo até ser eliminado: " + (System.currentTimeMillis() - alvo.getEntrada()) + "ms}");
                }
                fagocitando = false;
                alvo = null;                 
            } else {
                fagocitando = false;
                alvo = null;
            }
        }
    }
}