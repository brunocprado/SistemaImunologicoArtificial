package ufrrj.bruno.ia.celulas;

import ufrrj.bruno.ia.atributos.Posicao;
import java.util.Iterator;
import ufrrj.bruno.ia.quimica.CompostoQuimico;

public class Macrofago extends Celula{
    
    Posicao pos = getPosicao();
    //=====| Fagocitacao |======//
    private Patogeno alvo = null;
    private long tempoDetectado;
    private boolean fagocitando = false;
    
    public Macrofago(){
        super(TIPO_CELULA.Macrofago);
    }
//    
//    @Override
//    public void loop(){
//        
//        if(fagocitando) return;
//        
//        if(alvo != null){
//            if(calculaDistancia(alvo.getPos()) <= 4){
//                fagocitando = true;
//                Fagocitacao fagocitacao = new Fagocitacao();
//                fagocitacao.iniciaFagocitacao();
//            }
//            move(alvo.getPos());
//            return;
//        }
//        
//        for (Iterator<CompostoQuimico> i = getSistema().getCamada().compostos.iterator(); i.hasNext();) {
//            CompostoQuimico composto = i.next();
//            
//            double dist = calculaDistancia(composto.getPos());      
//            if(dist <= composto.getDiametro()/2 + 6){
//                alvo = composto;
//                tempoDetectado = System.currentTimeMillis();
//                if(alvo.getEmissor() != null) getSistema().addTemporizacao((int) (System.currentTimeMillis() - ((Patogeno)alvo.getEmissor()).getEntrada()));
//                if(dist <= 4){
//                    fagocitando = true;
//                    Fagocitacao fagocitacao = new Fagocitacao();
//                    fagocitacao.iniciaFagocitacao();
//                }
//                move(alvo.getPos());
//                break;
//            }
//        }
//    }
//    
    @Override
    public void loop(){
        
        if(fagocitando) return;
        
        if(alvo != null){
            if(calculaDistancia(alvo.getPosicao()) <= 4){
                fagocitando = true;
                Fagocitacao fagocitacao = new Fagocitacao();
                fagocitacao.iniciaFagocitacao();
            }
            move(alvo.getPosicao());
            return;
        }
        
        CompostoQuimico composto;
        for (Iterator<CompostoQuimico> i = getSistema().getCamada().compostos.iterator(); i.hasNext();) {
            composto = i.next();
            
            double dist = calculaDistancia(composto.getPos());      
            if(dist <= composto.getDiametro()/2 + 6){
                alvo = (Patogeno) composto.getEmissor();
                tempoDetectado = System.currentTimeMillis();
                if(alvo != null) getSistema().addTemporizacao((int) (System.currentTimeMillis() - alvo.getEntrada()));
                if(dist <= 4){
                    fagocitando = true;
                    Fagocitacao fagocitacao = new Fagocitacao();
                    fagocitacao.iniciaFagocitacao();
                }
                move(alvo.getPosicao());
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
            if(alvo != null){
                alvo.getVirus().setQuantidade(alvo.getVirus().getQuantidade() - 1);
                getSistema().eliminaCelula(alvo); 
                getSistema().imprime("Patogeno " + alvo.getId() 
                        + " [<span style='color:red;'>" + alvo.getVirus().getIdentificador()+ "</span>] eliminado. {Tempo de detecção : " + (tempoDetectado - alvo.getEntrada()) 
                        + "ms, Tempo até ser eliminado: " + (System.currentTimeMillis() - alvo.getEntrada()) + "ms}");
                fagocitando = false;
                alvo = null;                 
            } else {
                fagocitando = false;
                alvo = null;
            }
        }   
    }
}