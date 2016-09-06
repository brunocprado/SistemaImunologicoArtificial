package ufrrj.bruno.ia.celulas;

import java.util.Random;
import ufrrj.bruno.ia.Parametros;
import ufrrj.bruno.ia.SistemaImunologico;

abstract public class Celula implements Comportamento{
    private static int id;
    //=====| CARACTERISTICAS |=====//
    public int tamanhoX,tamanhoY;
    private double velMovimento;
    //========|  RUNTIME  |=======//
    //private Thread t;
    private Posicao posicao;    
    public SistemaImunologico sistema;
    public boolean ativa = true;
    
    public Celula(SistemaImunologico sistema,boolean roda){
        id++; this.sistema = sistema;
        Random gerador = new Random();
        posicao = new Posicao(
                gerador.nextInt(Parametros.TAMX),
                gerador.nextInt(Parametros.TAMY));
       // if(roda){ t = new Thread(this); t.start(); }
    }
    
    public Celula(SistemaImunologico sistema,Posicao pos,boolean roda){
        id++; this.sistema = sistema;
        posicao = pos;
        //if(roda){ t = new Thread(this); t.start(); }
    }

//    public void pausa(int tempo){
//        try {
//            t.sleep(tempo);
//        } catch (InterruptedException ex) {
//            System.out.println("Erro ao pausar a Thread da Celula");
//        }
//    }     

    public void move(Posicao dest){
            double deltaX = dest.getX() - (getPosicao().getX() - 10);
            double deltaY = dest.getY() - (posicao.getY() - 10);
           
            double angulo = Math.atan2(deltaY,deltaX);    
            int movX = (int) (posicao.getX() + (getVelMovimento() * Math.cos(angulo)));
            int movY = (int) (posicao.getY() + (getVelMovimento() * Math.sin(angulo)));
            if(deltaX == 0){ movX = posicao.getX(); }
            if(deltaY == 0){ movY = posicao.getY(); }
            setPosicao(movX,movY);
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

    public void setPosicao(int x,int y) {
        posicao.setX(x);
        posicao.setY(y);
    }
    
//    @Override
//    public void run() {  
//    }

}