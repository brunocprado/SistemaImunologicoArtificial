package ufrrj.bruno.ia.celulas;

import ufrrj.bruno.ia.atributos.Posicao;
import java.util.Random;
import ufrrj.bruno.ia.SistemaImunologico;

abstract public class Celula implements Comportamento{
    public enum TIPO_CELULA {Comum,Linfocito,Neutrofilo,Patogeno,Macrofago};
            
    private static int qt = 0;
    private int id;
    private TIPO_CELULA tipo;
    //=====| CARACTERISTICAS |=====//
    public int tamanhoX,tamanhoY;
    private double velMovimento = 2;
    //========|  RUNTIME  |=======//
    private Posicao posicao;    
    private final SistemaImunologico sistema = SistemaImunologico.getInstancia();

    public Celula(TIPO_CELULA tipo){
        qt++; id = qt; 
        this.tipo = tipo;
        Random gerador = new Random();
        posicao = new Posicao(
                gerador.nextInt(sistema.getParametro("TAMX")),
                gerador.nextInt(sistema.getParametro("TAMY")));
    }
    
    public Celula(TIPO_CELULA tipo,Posicao pos){
        qt++; id = qt; this.tipo = tipo;
        posicao = pos;
    }
    
    public void move(Posicao dest){
        double deltaX = dest.getX() - getPosicao().getX();
        double deltaY = dest.getY() - posicao.getY();

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

    public TIPO_CELULA getTipo() {
        return tipo;
    }

    public SistemaImunologico getSistema() {
        return sistema;
    }

    public int getId() {
        return id;
    }
    
    @Override
    public String toString() {
        return "Celula{" + "tipo=" + tipo + ", tamanhoX=" + tamanhoX + ", tamanhoY=" + tamanhoY + ", velMovimento=" + velMovimento + ", posicao=" + posicao + '}';
    }
    
}