package ufrrj.bruno.ia.atributos;

import java.awt.Polygon;

public class Poligono {
    
    private Polygon forma;
    private Posicao pos;
    private int[] x;
    private int[] y;
    
    
    public Poligono(int nLados,Posicao pos){
        this.pos = pos;
        x = new int[nLados];
        y = new int[nLados];
        geraPoligono(nLados);
    }
    
    public void geraPoligono(int nLados){
        forma = new Polygon();
        double variacao = 360/nLados;
        double fatia = 2 * Math.PI / nLados;
        for(int i = 0;i<nLados;i++){
            x[i] = 4 + (int) (Math.cos(i * fatia) * 4);
            y[i] = 4 + (int) (Math.sin(i * fatia) * 4);
        }
    }
    
    public Polygon getPoligono(){
        Polygon p = new Polygon();
        for(int i = 0;i<x.length;i++){
            p.addPoint(pos.getX() + x[i], pos.getY() + y[i]);
        }
        return p;
    }
    
}
