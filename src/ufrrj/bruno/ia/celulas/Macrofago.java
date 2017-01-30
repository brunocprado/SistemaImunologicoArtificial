package ufrrj.bruno.ia.celulas;

import ufrrj.bruno.ia.atributos.Posicao;
import java.util.Iterator;
import ufrrj.bruno.ia.SistemaImunologico;
import ufrrj.bruno.ia.quimica.CompostoQuimico;

public class Macrofago extends Celula{
    
    Posicao pos = getPosicao();
    //=====| Fagocitacao |======//
    private CompostoQuimico alvo = null;
    private long tempoDetectado;
    private boolean fagocitando = false;
    
    public Macrofago(SistemaImunologico sistema){
        super(sistema,TIPO_CELULA.Macrofago);
        setVelMovimento(2);
    }
    
    private double calculaDistancia(Posicao posicaoInicial,Posicao posicaoAlvo){
        double deltaX = posicaoInicial.getX() - posicaoAlvo.getX();
        double deltaY = posicaoInicial.getY() - posicaoAlvo.getY();
        return Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
    }
    
    @Override
    public void loop(){
        
        if(fagocitando) return;
        
        if(alvo != null){
            if(calculaDistancia(pos,alvo.getPos()) <= 4){
                fagocitando = true;
                Fagocitacao fagocitacao = new Fagocitacao();
                fagocitacao.iniciaFagocitacao();
            }
            move(alvo.getPos());
            return;
        }
        
        for (Iterator<CompostoQuimico> i = getSistema().getCamada().compostos.iterator(); i.hasNext();) {
            CompostoQuimico composto = i.next();
            double dist = calculaDistancia(pos,composto.getPos());      
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