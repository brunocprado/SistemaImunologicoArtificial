package ufrrj.bruno.renderizacao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import ufrrj.bruno.SistemaImunologico;
import ufrrj.bruno.celulas.Celula;
import ufrrj.bruno.quimica.CompostoQuimico;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Grafico 2D 2.0 <br>
 * 
 * Pane p = javafx.Pane onde será renderizado
 * Substitui o uso do canvas pela biblioteca do JavaFX
 * 
 * @author Bruno Prado
 */
public class GraficoAvancado {
    //===========| STATIC |=============//
    private static final GraficoAvancado instancia = new GraficoAvancado();
    //==================================//
        
    private static final SistemaImunologico sistema = SistemaImunologico.getInstancia();
    private final Map<CompostoQuimico.TIPO_COMPOSTO,double[]> cor = new HashMap<>();
    public Pane p;
    public Pane quimica;
    
    //==========| RUNTIME |============//
    public double zoom = 1.0;
    private double sx = 0,sy = 0;
    private int cameraX = 0,cameraY = 0;
    //=================================//
    
    public GraficoAvancado(){
        cor.put(CompostoQuimico.TIPO_COMPOSTO.PAMP,new double[] {1,0.6,0.6});
        cor.put(CompostoQuimico.TIPO_COMPOSTO.CITOCINA,new double[] {0.6,1,0.6});
    }
    
    public static synchronized GraficoAvancado getInstancia(){
        return instancia;
    }
    
    public void setPane(Pane p){
        this.p = p;
        
        p.widthProperty().addListener((t)->{
            sx = (double) ((p.getWidth()*zoom)/1600d);        
            p.setScaleX(sx);
            p.setTranslateX(0 - (((1-sx)/2)* p.getWidth()) - 2);
        });
        
        p.heightProperty().addListener((t)->{
            sy = (double) ((p.getHeight()*zoom)/900d);
            p.setScaleY(sy);
            p.setTranslateY(0 - (((1-sy)/2)* p.getHeight()));
        });
        
        renderiza();
    }
    
    public void setQuimica(Pane p){
        this.quimica = p;
        
//        Timer t = new Timer("Render Quimica");
//        t.schedule(new TimerTask() {
//            @Override
//            public void run() { renderizaQuimica(); }
//        }, 0,20);
    }
    
    private void redimensiona(){
        //if(sx != 0 || sy != 0) p.scale(1/sx,1/sy);
        sx = (double) ((p.getWidth()*zoom)/1600d);        
        p.setScaleX(sx);
        p.setTranslateX(0 - (((1-sx)/2)* p.getWidth()) - cameraX);
        sy = (double) ((p.getHeight()*zoom)/900d);
        p.setScaleY(sy);
        p.setTranslateY(0 - (((1-sy)/2)* p.getHeight()) - cameraY);
//        quimica.scale(sx, sy);
    }
    
    public void renderiza(){
        for(Celula celula : sistema.getMacrofagos()){          
            p.getChildren().add(celula);
        }
        
        for(Celula celula : sistema.getNeutrofilos()){          
            p.getChildren().add(celula);
        }
        
        for(Celula celula : sistema.getLinfocitos()){          
            p.getChildren().add(celula);
        }
        
        for(Celula celula : sistema.getPatogenos()){          
            p.getChildren().add(celula);
        }
    }
    
    public void renderiza(Node n){
        Platform.runLater(() -> {
            p.getChildren().add(n);
        }); 
    }
    
    public void renderiza(Node n,int index){
        Platform.runLater(() -> {
            p.getChildren().add(index,n);
        }); 
    }
    
    public void renderizaQuimica(){
//        quimica.clearRect(0,0,p.getWidth(),p.getHeight());
//        
//        if(!sistema.getMostraCamada()) return;
//        
//        CompostoQuimico composto;;
//        Iterator<CompostoQuimico> i = sistema.getCamada().compostos.iterator();
//        while(i.hasNext()){
//            composto = i.next();
//            double[] tmp = cor.get(composto.getTipo());
//            quimica.setFill(new Color(tmp[0],tmp[1],tmp[2],composto.opacidade)); //(composto.getQuantidade() * 4)/255)
//            int diametro = composto.getRaio()*2;
//            quimica.fillOval(sx, sy, sx, sx);
//            quimica.fillOval(composto.getX() - diametro/2 - cameraX, composto.getY() - diametro/2 - cameraY, diametro, diametro);
//        }
    }
    
    public void renderizaQuimica(Node n){
        Platform.runLater(() -> {
            quimica.getChildren().add(n);
        }); 
    }
//    
//    public void removeQuimica(Node n){
//        Platform.runLater(() -> {
//            if(quimica.getChildren().contains(n)) {
//                quimica.getChildren().remove(n);
//            }
//        }); 
//    }
    
    public void remove(Node n){
        Platform.runLater(() -> {
            if(p.getChildren().contains(n) && p.getChildren().indexOf(n) != -1) {
                p.getChildren().remove(n);
            }
        }); 
    }
    
    public void moveX(Integer x){
        if(x<0 || zoom == 1) return;
//        if(x >= 1600/zoom) return;
        cameraX = x;
        redimensiona();
    }
    
    public void moveY(Integer y){
        if(y<0 || zoom == 1) return;
        cameraY = y;
        redimensiona();
    }

    public int getX() {
        return cameraX;
    }

    public int getY() {
        return cameraY;
    }
    
    public void setZoom(Double zoom){
        if(zoom < 1) { setZoom(1d); return; }
        if(zoom == 1) { cameraX = 0; cameraY = 0; }
        this.zoom = zoom;
        redimensiona();
    }
    
    public Double getZoom() { return zoom; }
    
}
