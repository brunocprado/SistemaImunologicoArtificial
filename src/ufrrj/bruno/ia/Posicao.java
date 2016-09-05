package ufrrj.bruno.ia;

public class Posicao {
    private int x,y;
    private float vx,vy;
    private float tamX,tamY;
    
    public Posicao(int x,int y,int tamX,int tamY){
        this.x = x; this.y = y;
        this.tamX = tamX; this.tamY = tamY;
       
        calcula();
    }
    
    public void calcula(){
        //TAMX = 2000
        // 500 
        ///divX = 0.25
        
        
        
        float divX = (float) x/tamX;
        float divY = (float) y/tamY;
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
    }

    public void setY(int y) {
        this.y = y;
    }

    public float getVx() {
        return vx;
    }
    public float getVy() {
        return vy;
    }
    
}
