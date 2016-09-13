package ufrrj.bruno.ia.atributos;

import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class Poligono {
    
    private Polygon forma;
    private Posicao pos;
    private int[] x;
    private int[] y;    
    private int nLados;
    
    public Poligono(int nLados,Posicao pos){
        this.pos = pos;
        this.nLados = nLados;
        x = new int[nLados];
        y = new int[nLados];
        geraPoligono(nLados);
    }
    
    public void geraPoligono(int nLados){
        forma = new Polygon();
        double variacao = 360/nLados;
        double fatia = 2 * Math.PI / nLados;
        
//        AffineTransform trans = new AffineTransform();
//    Polygon poly = new Polygon();
//    nLados = 8;
//
//    for (int i = 0; i < nLados; i++) {
//      trans.rotate(Math.PI * 2 / (float) nLados / 2);
//      Point2D out = trans.transform(new Point2D.Float(0, 4), null);
//      x[i] = (int) out.getX();
//      y[i] = (int) out.getY();
//      poly.addPoint((int) out.getX(), (int) out.getY());
//    //  trans.rotate(Math.PI * 2 / (float) nLados / 2);
//    }
        
//        
        for(int i = 0;i<nLados;i++){
            x[i] = (int) (Math.cos(i * fatia) * 4);
            y[i] = (int) (Math.sin(i * fatia) * 4);
        }
    }
    
    public Polygon getPoligono(){
        Polygon p = new Polygon();
        for(int i = 0;i<x.length;i++){
            p.addPoint(pos.getX() + x[i], pos.getY() + y[i]);
        }
        return p;
    }

    public int getnLados() {
        return nLados;
    }
    
}