package ufrrj.bruno.ia;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public Celula(Mundo mundo,boolean roda){
        id++; this.mundo = mundo;
        Random gerador = new Random();
        posicao = new Posicao(gerador.nextInt(1312),gerador.nextInt(738),1312,738);
        if(roda){ t = new Thread(this); t.start(); }
    }
    
    public Celula(Mundo mundo,Posicao pos,boolean roda){
        id++; this.mundo = mundo;
        posicao = pos;
        if(roda){ t = new Thread(this); t.start(); }
    }

    public void pausa(int tempo){
        try {
            t.sleep(tempo);
        } catch (InterruptedException ex) {
            System.out.println("Erro ao pausar a Thread da Celula");
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