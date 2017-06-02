package ufrrj.bruno.atributos;

import javafx.scene.shape.Polygon;

public class Poligono {
    
    private Polygon forma;
    public double[] x;
    public double[] y;    
    private int nLados;
    
    public Poligono(int nLados){
        this.nLados = nLados;
        x = new double[nLados];
        y = new double[nLados];
        geraPoligono(nLados);
    }
    
    public void geraPoligono(int nLados){
        forma = new Polygon();
        double fatia = 2 * Math.PI / nLados;
//        
        for(int i = 0;i<nLados;i++){
            x[i] = Math.cos(i * fatia) * 6;
            y[i] = Math.sin(i * fatia) * 6;
        }
    }
    
    public Polygon getPoligono(){
//        forma = new Polygon();
//        for(int i = 0;i<x.length;i++){
//            forma.getPoints().addAll(new Double[]{
//                pos.getX() + x[i],pos.getY() + y[i]
//            });
//        }
        return forma;
    }

    public int getnLados() {
        return nLados;
    }
    
}