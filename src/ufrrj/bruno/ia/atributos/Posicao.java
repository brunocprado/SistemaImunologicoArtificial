package ufrrj.bruno.ia.atributos;

public class Posicao {
    private int x,y;
    
    public Posicao(int x,int y){
        this.x = x; this.y = y;     
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "{X = " + x + ",Y = " + y + '}';
    }
    
}
