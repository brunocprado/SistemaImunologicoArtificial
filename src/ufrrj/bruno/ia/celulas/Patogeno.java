package ufrrj.bruno.ia.celulas;

import ufrrj.bruno.ia.SistemaImunologico;


public class Patogeno extends Celula{
    
    private int chave = 0xFFFFF; 
    
    public Patogeno(SistemaImunologico sistema) {
        super(sistema,true);
        setVelMovimento(1);
    }
    
    
    private int verificaMaisProximo(){
        int maisProximo = 0,i = 0;
        double distMaisProximo = Double.MAX_VALUE;
//        try {
//            for (Iterator<Celula> it = mundo.getCelulas().iterator(); it.hasNext();) {
//                Celula celula = it.next();
//                if(!celula.getClass().getSimpleName().equals("Comum")){ continue; }         
//                double dist = Math.abs(getPosicao().x - celula.getPosicao().x)  +  Math.abs(getPosicao().y - celula.getPosicao().y);
//                if(dist < distMaisProximo && dist != 0){
//                    distMaisProximo = dist;
//                    maisProximo = i;
//                }
//                i++;
//            }
//        } catch (Exception ex) {
//            pausa(2);
//            return verificaMaisProximo();
//        }    
        return maisProximo;
    }
    
    private int nCelulas(){
        int qt = 0;
        try {
            for(Celula celula : sistema.getCelulas()){
                if(celula.getClass().getSimpleName().equals("Comum")){ qt++; }    
            }
        } catch (Exception ex) {
            //pausa(2);
            return nCelulas();
        }  
        return qt;
    }
    
   
    public void run() {
        while(ativa){    
            if(nCelulas() == 0){
                //pausa(20);
                continue;
            }
//            Point pos = mundo.getCelulas().get(verificaMaisProximo()).getPosicao();
//            if(pos == null){ 
//                pausa(5);               
//                continue;
//            }
            //EXECUTA MOVIMENTO
//            double deltaX = pos.x - getPosicao().x;
//            double deltaY = pos.y - getPosicao().y;
//            
//            if(pos == getPosicao()){continue;}
//            double dist = Math.abs(getPosicao().x - pos.x)  +  Math.abs(getPosicao().y - pos.y);
//             
//            if(dist < 16){
//                pausa(100);
//                mundo.eliminaCelula(mundo.getCelulas().get(verificaMaisProximo()));
//                Invasor tmp = new Invasor(mundo);
//                tmp.setPosicao(pos);
//                mundo.adicionaCelula(tmp);
//            }
//            
//            double angulo = Math.atan2(deltaY,deltaX);    
//            double movX = getPosicao().x + (getVelMovimento() * Math.cos(angulo));
//            double movY = getPosicao().y + (getVelMovimento() * Math.sin(angulo));
//            if(deltaX == 0){ movX = getPosicao().x; }
//            if(deltaY == 0){ movY = getPosicao().y; }
//            setPosicao(movX,movY);

            //pausa(20);   
        }
        //t.interrupt();
    }

    @Override
    public void loop() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}