package ufrrj.bruno.ia.celulas;

import java.awt.Color;
import java.awt.Polygon;
import java.util.Random;
import ufrrj.bruno.ia.SistemaImunologico;
import ufrrj.bruno.ia.atributos.Poligono;
import ufrrj.bruno.ia.atributos.Posicao;
import ufrrj.bruno.ia.quimica.CompostoQuimico;
import static ufrrj.bruno.ia.quimica.CompostoQuimico.TIPO_COMPOSTO.HISTAMINA;

public class Patogeno extends Celula{
    
    private final int codBiologico;  //Reconhecimento de padrao
    private Poligono forma;
    private Color cor;
    
    public Patogeno(SistemaImunologico sistema) {
        super(sistema,TIPO_CELULA.Patogeno);
        Random r = new Random();
        forma = new Poligono(new Random().nextInt(10) + 3,getPosicao());
        cor = new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255));
        codBiologico = new Random().nextInt(Integer.MAX_VALUE);

        sistema.getCamada().compostos.add(new CompostoQuimico(HISTAMINA, 20,getPosicao()));
        
        if(sistema.isDebug()) { sistema.imprime("Novo patogeno com identificador: "  + codBiologico); }
    }
    
    public Patogeno(SistemaImunologico sistema,int nLados,Color cor) {
        super(sistema,TIPO_CELULA.Patogeno);
        forma = new Poligono(nLados,getPosicao());
        this.cor = cor;
        codBiologico = new Random().nextInt(Integer.MAX_VALUE);
    }
    
    public Patogeno(SistemaImunologico sistema,int nLados,Color cor,Posicao pos) {
        super(sistema,TIPO_CELULA.Patogeno,pos);
        forma = new Poligono(nLados,getPosicao());
        this.cor = cor;
        codBiologico = new Random().nextInt(Integer.MAX_VALUE);
    }
    
    public Patogeno(SistemaImunologico sistema,int nLados,Color cor,Posicao pos,int bio) {
        super(sistema,TIPO_CELULA.Patogeno,pos);
        forma = new Poligono(nLados,getPosicao());
        this.cor = cor;
        codBiologico = bio;
    }
    
    private void emiteQuimica(){
        //getSistema().getCamada().editaPosicao(getPosicao().getX(), getPosicao().getY(), new CompostoQuimico(HISTAMINA,2));
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
    
//    private int nCelulas(){
//        int qt = 0;
//        try {
//            for(Celula celula : getSistema().getCelulas()){
//                if(celula.getClass().getSimpleName().equals("Comum")){ qt++; }    
//            }
//        } catch (Exception ex) {
//            //pausa(2);
//            return nCelulas();
//        }  
//        return qt;
//    }
    
    public void clona(){
        getSistema().adicionaCelula(new Patogeno(getSistema(),forma.getnLados(),cor,getPosicao()));
    }
   
    public void run() {
//        while(ativa){    
//            if(nCelulas() == 0){
//                //pausa(20);
//                continue;
//            }
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

    @Override
    public void loop() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getCodBiologico() {
        return codBiologico;
    }

    public Polygon getForma() {
        return forma.getPoligono();
    }

    public Color getCor() {
        return cor;
    }
    
}
