package ufrrj.bruno.ia;

import java.util.logging.Level;
import java.util.logging.Logger;
import ufrrj.bruno.ia.celulas.Macrofago;

abstract public class Celula implements Runnable{
    private static int id;
    //=====| CARACTERISTICAS |=====//
    public int tamanhoX,tamanhoY;
    private double velMovimento;
    //========|  RUNTIME  |=======//
    private Thread t;
    private Posicao posicao;    
    public Mundo mundo;
    public boolean ativa = true;
    
    public Celula(Mundo mundo,Posicao pos,boolean roda){
        id++; this.mundo = mundo;
        posicao = pos;
        if(roda){ t = new Thread(this); t.start(); }
    }

    public void pausa(int tempo){
        try {
            t.sleep(tempo);
        } catch (InterruptedException ex) {
            Logger.getLogger(Macrofago.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public double getVelMovimento() {
        return velMovimento;
    }

    public void setVelMovimento(double velMovimento) {
        this.velMovimento = velMovimento;
    }

    public Posicao getPosicao() {
        return posicao;
    }

    public void setPosicao(Posicao posicao) {
        this.posicao = posicao;
    }

    public void setPosicao(double x,double y) {
        //posicao.setLocation(x,y);
    }

    @Override
    public void run() {
        
    }

}