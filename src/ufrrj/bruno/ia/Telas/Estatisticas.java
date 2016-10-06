package ufrrj.bruno.ia.Telas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import ufrrj.bruno.ia.Parametros;
import ufrrj.bruno.ia.SistemaImunologico;
import ufrrj.bruno.ia.graficos.Fatia;
import ufrrj.bruno.ia.graficos.GraficoPizza;
import ufrrj.bruno.ia.quimica.CompostoQuimico;
import static ufrrj.bruno.ia.quimica.CompostoQuimico.TIPO_COMPOSTO.HISTAMINA;

public class Estatisticas extends JPanel implements Runnable{
    private Thread t;
    private SistemaImunologico sistema;
    private CompostoQuimico matriz[][];
    private static final int tamX = Parametros.TAMX/8;
    private static final int tamY = Parametros.TAMY/8;
   
    public Estatisticas(SistemaImunologico sistema){
        this.sistema = sistema;
        matriz = sistema.getCamada().getMatriz();
        matriz[0][1] = new CompostoQuimico(HISTAMINA,2);
        setSize(200,200);
        t = new Thread(this);
        t.start();
    }
    
    public void paint(Graphics g){
        //Graphics2D g2d = (Graphics2D) g.create();

        g.clearRect(0,0, getWidth(), getHeight());
        g.setColor(Color.ORANGE);
        g.fillRect(10, 10, 1000, 1000);
        for(int y=0;y<tamY;y++){
            for(int x=0;x<tamX;x++){
                if(matriz[y][x].getQuantidade() > 0){
                    g.drawRect(x, y, 10, 10);
                }
            }
        }    
//        int largura =  (int) g.getFontMetrics().getStringBounds("Distribuição",g).getWidth();
//        g.drawString("Distribuição",(getWidth()-largura)/2,20);
        //g.fillRect(0, 260, getWidth(), getHeight());
    }
    
    @Override
    public void run() {
        while(true){
            repaint();
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                //Logger.getLogger(Visual.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
    }
    
}
