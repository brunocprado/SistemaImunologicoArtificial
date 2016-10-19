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
    private int matriz[][];
    private static final int tamX = Parametros.TAMX/8;
    private static final int tamY = Parametros.TAMY/8;
   
    public Estatisticas(SistemaImunologico sistema){
        this.sistema = sistema;
        //matriz = sistema.getCamada().getMatriz();
        //matriz[2][4] = 5;
        setSize(200,200);
        t = new Thread(this);
        t.start();
    }
    
    public void paint(Graphics g){
        //Graphics2D g2d = (Graphics2D) g.create();

//        g.clearRect(0,0, getWidth(), getHeight());
//        g.setColor(Color.BLACK);
//        g.fillRect(0, 0, 2000, 1000);
//        g.setColor(Color.RED);
//        for(int y=0;y<tamY;y++){
//            for(int x=0;x<tamX;x++){
////                System.out.println(x + "|" + y + "|" + matriz[y][x]);
//                if(matriz[y][x] > 0){
//                    g.fillRect(x*8, y*8, 8, 8);
//                }
//            }
//        }    
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
