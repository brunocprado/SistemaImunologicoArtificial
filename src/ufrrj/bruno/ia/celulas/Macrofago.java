package ufrrj.bruno.ia.celulas;

import ufrrj.bruno.ia.atributos.Posicao;
import java.util.Iterator;
import ufrrj.bruno.ia.quimica.CompostoQuimico;

public class Macrofago extends Celula{
    
    Posicao pos = getPosicao();
    //=====| Fagocitacao |======//
    private CompostoQuimico alvo = null;
    private long tempoDetectado;
    private boolean fagocitando = false;
    
    public Macrofago(){
        super(TIPO_CELULA.Macrofago);
    }
    
    @Override
    public void loop(){
        
        if(fagocitando) return;
        
        if(alvo != null){
            if(calculaDistancia(alvo.getPos()) <= 4){
                fagocitando = true;
                Fagocitacao fagocitacao = new Fagocitacao();
                fagocitacao.iniciaFagocitacao();
            }
            move(alvo.getPos());
            return;
        }
        
        for (Iterator<CompostoQuimico> i = getSistema().getCamada().compostos.iterator(); i.hasNext();) {
            CompostoQuimico composto = i.next();
            
            double dist = calculaDistancia(composto.getPos());      
            if(dist <= composto.getDiametro()/2 + 6){
                alvo = composto;
                tempoDetectado = System.currentTimeMillis();
                if(dist <= 4){
                    fagocitando = true;
                    Fagocitacao fagocitacao = new Fagocitacao();
                    fagocitacao.iniciaFagocitacao();
                }
                move(alvo.getPos());
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
            try {
                Thread.sleep(getSistema().getParametro("TEMPO_FAGOCITACAO"));
            } catch (InterruptedException ex) {}
            if(alvo.getEmissor() != null){
                Patogeno tmp = (Patogeno) alvo.getEmissor();
                tmp.getVirus().setQuantidade(tmp.getVirus().getQuantidade() - 1);
                getSistema().eliminaCelula(tmp); 
                getSistema().imprime("Patogeno " + tmp.getId() 
                        + " [<span style='color:red;'>" + tmp.getVirus().getIdentificador()+ "</span>] eliminado. {Tempo de detecção : " + (tempoDetectado - tmp.getEntrada()) 
                        + "ms, Tempo até ser eliminado: " + (System.currentTimeMillis() - tmp.getEntrada()) + "ms}");
                alvo.setEmissor(null);                 
            } else {
                fagocitando = false;
                alvo = null;
            } 
        }
        
    }
    
}