package ufrrj.bruno.ia.celulas;

import java.awt.Polygon;
import ufrrj.bruno.ia.SistemaImunologico;
import ufrrj.bruno.ia.atributos.Poligono;
import ufrrj.bruno.ia.atributos.Posicao;
import ufrrj.bruno.ia.log.Virus;
import ufrrj.bruno.ia.quimica.CompostoQuimico;
import ufrrj.bruno.ia.quimica.CompostoQuimico.TIPO_COMPOSTO;

public class Patogeno extends Celula{
    
    private final long entrada = System.currentTimeMillis();
    private Virus tipo;
    private Poligono forma;
    
    public Patogeno(SistemaImunologico sistema) {
        super(sistema,TIPO_CELULA.Patogeno);
        tipo = new Virus();
        forma = new Poligono(tipo.getnLados(),getPosicao());
        
        if(sistema.isDebug()) { sistema.imprime("Novo patogeno com identificador: "  + tipo.getIdentificador()); }
        emiteQuimica();
    }
    
    public Patogeno(SistemaImunologico sistema,Virus tipo) {
        super(sistema,TIPO_CELULA.Patogeno);
        forma = new Poligono(tipo.getnLados(),getPosicao());
        this.tipo = tipo;
        tipo.setQuantidade(tipo.getQuantidade()+1);
        emiteQuimica();
    }
    
    public Patogeno(SistemaImunologico sistema,Virus tipo,Posicao pos) {
        super(sistema,TIPO_CELULA.Patogeno,pos);
        forma = new Poligono(tipo.getnLados(),getPosicao());
        this.tipo = tipo;
        tipo.setQuantidade(tipo.getQuantidade()+1);
        emiteQuimica();
    }
    
    private void emiteQuimica(){
        getSistema().getCamada().compostos.add(new CompostoQuimico(TIPO_COMPOSTO.PAMP, 40,getPosicao(),this));
    }
    
    public void clona(){
        getSistema().adicionaCelula(new Patogeno(getSistema(),tipo,getPosicao()));
    }
   
    public void run() {
        
//        mov;
        
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
        //System.out.println("a");
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Polygon getForma() {
        return forma.getPoligono();
    }

    public long getEntrada() {
        return entrada;
    }

    public Virus getVirus() {
        return tipo;
    }
    
}
