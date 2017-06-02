package ufrrj.bruno.renderizacao;

import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ufrrj.bruno.SistemaImunologico;
import ufrrj.bruno.celulas.Celula;
import ufrrj.bruno.celulas.Macrofago;
import ufrrj.bruno.celulas.Macrofago.ESTADO;
import ufrrj.bruno.celulas.Patogeno;
import ufrrj.bruno.quimica.CompostoQuimico;
import ufrrj.bruno.quimica.CompostoQuimico.TIPO_COMPOSTO;

public class Grafico2D{
    
    private final GraphicsContext g;
    private final Canvas canvas;
    
    private final SistemaImunologico sistema = SistemaImunologico.getInstancia(); 
    
    private final Map<TIPO_COMPOSTO,double[]> cor = new HashMap<>();

    //=========| IMAGENS |==========//
    final Image sangue = new Image("/img/blood.jpg");
    final Image comum = new Image("/img/celula.png");
    final Image linfocito = new Image("/img/Linfocito.png");
    final Image macrofago = new Image("/img/macro.png");
    final Image neutrofilo = new Image("/img/neutrofilo.png");
    //=========| RUNTIME |=========//
    private int cameraX,cameraY;   
    private double zoom = 1;
    
    private int gambiarra = 0;
    private double sx,sy;
    
    public Grafico2D(GraphicsContext gc){
        this.g = gc;
        canvas = gc.getCanvas();
        
        cor.put(TIPO_COMPOSTO.PAMP,new double[] {1,0.6,0.6});
        cor.put(TIPO_COMPOSTO.CITOCINA,new double[] {0.6,1,0.6});
        
        canvas.widthProperty().addListener(observable -> redimensiona());
        canvas.heightProperty().addListener(observable -> redimensiona());
    }
    
    public void redimensiona(){
        if(gambiarra < 3) ++gambiarra; if(gambiarra == 2) return;
        if(gambiarra > 2) g.scale(1/sx,1/sy);
        
        double a = (double) ((canvas.getWidth())/1600d);
        double b = (double) ((canvas.getHeight())/900d);
        
        if(canvas.getHeight() == 0) b = (double) ((695)/900d);
        
        sx = a; sy = b;
        g.scale(a, b);
        
        renderiza();
    }
    
    public void iniciaRenderizacao(){
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(!sistema.isPausado()) renderiza();
            }
        }.start();
    }
    
    private void renderiza(){
        double largura = canvas.getWidth(),altura = canvas.getHeight();

        g.clearRect(0,0,largura,altura);
        g.setFill(new Color(245/255,146/255,146/255,1));
         
        g.drawImage(sangue,0,0,1604,904);
        
////
//        if(zoom > 1){
//            g.translate(cameraX,cameraY);
//        }
//           
        if(sistema.getMostraCamada()){ desenhaCamadaQuimica(); }
        
        for(Celula celula : sistema.getCelulas()){  
            if(!sistema.exibir.get(celula.getTipo())){
                continue;
            }
            switch(celula.getTipo()){
                case MACROFAGO:
                    if(((Macrofago)celula).getEstado() == ESTADO.ATIVO || ((Macrofago)celula).getEstado() == ESTADO.FAGOCITANDO) {
                        g.drawImage(macrofago, celula.getPosicao().getX(), celula.getPosicao().getY(),12,12);
                    } else {
                        g.drawImage(macrofago, celula.getPosicao().getX(), celula.getPosicao().getY(),8,8);
                    }
                    break;
                case NEUTROFILO:
                    g.drawImage(neutrofilo, celula.getPosicao().getX(), celula.getPosicao().getY(),8,8);
                    break;
                case LINFOCITO:
                    g.drawImage(linfocito, celula.getPosicao().getX(), celula.getPosicao().getY(),8,8);
                    break;
                case PATOGENO:
                    Patogeno tmp = (Patogeno)celula;
                    g.setFill(tmp.getVirus().getCor());
                    double[] x = new double[tmp.getForma().x.length];
                    double[] y = new double[tmp.getForma().y.length];;  
                    for(int i=0;i<tmp.getForma().x.length;i++){
                        x[i] += tmp.getForma().x[i] + tmp.getPosicao().getX();
                        y[i] += tmp.getForma().y[i] + tmp.getPosicao().getY();
                    }
                    g.fillPolygon(x,y,tmp.getForma().x.length);
             
//                    g.drawImage(comum, celula.getPosicao().getX(), celula.getPosicao().getY(),18,18);
//                    g.fillRect(celula.getPosicao().getX(), celula.getPosicao().getY(),18,18);
                    tmp = null;
                    break;
                default:
//                    g.drawImage(comum, celula.getPosicao().getX(), celula.getPosicao().getY(),8,8);
                    break;
            }
        }
    }
    
    public void desenhaCamadaQuimica(){    
        CompostoQuimico composto;
        Iterator<CompostoQuimico> i = sistema.getCamada().compostos.iterator();
        while(i.hasNext()){
            composto = i.next();
            double[] tmp = cor.get(composto.getTipo());
            g.setFill(new Color(tmp[0],tmp[1],tmp[2],composto.opacidade)); //(composto.getQuantidade() * 4)/255)
            int diametro = composto.getDiametro();
            g.fillOval(composto.getPos().getX() - diametro/2, composto.getPos().getY() - diametro/2, diametro, diametro);
        }
    }
}
