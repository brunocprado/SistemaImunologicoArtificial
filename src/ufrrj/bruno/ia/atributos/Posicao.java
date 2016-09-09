package ufrrj.bruno.ia.atributos;

import ufrrj.bruno.ia.Parametros;

public class Posicao {
    private int x,y;
    private float vx,vy;
    
    public Posicao(int x,int y){
        this.x = x; this.y = y;     
        calcula();
    }
    
    public void calcula(){
        float divX = (float) x/Parametros.TAMX;
        float divY = (float) y/Parametros.TAMY;
        if(divX < 0.5){
            vx =  (divX * 2) -1;
        } else if(divX == 0.5){
            vx = 0;
        } else{
            vx = (divX * 2) - 1;
        } 
        
        if(divY < 0.5){
            vy = 1 - (divY *2);
        } else if(divY == 0.5){
            vy = 0;
        } else{
            vy = (float) ((divY - 0.5) * -2);
        }
    }
    
    public void setPosicao(int x,int y){
        this.x = x; this.y = y;
        calcula();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
        calcula();
    }

    public void setY(int y) {
        this.y = y;
        calcula();
    }

    public float getVx() {
        return vx;
    }
    public float getVy() {
        return vy;
    }
    
}
