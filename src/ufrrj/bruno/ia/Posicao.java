package ufrrj.bruno.ia;

public class Posicao {
    private int x,y;
    private float vx,vy;
    
    public Posicao(int x,int y,int tamX,int tamY){
        //656 de cada lado
        //1312
        //System.out.println(y);
        //0 - 655 = negativo
        //656 - 1311 = positivo
        
        this.x = x; this.y = y;

        //1000
        float divX = (float) x/tamX;
        float divY = (float) y/tamY;
        if(divX < 0.5){
            vx = (float) divX * -2;
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
