package ufrrj.bruno.ia.Telas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import ufrrj.bruno.ia.SistemaImunologico;
import ufrrj.bruno.ia.graficos.Fatia;
import ufrrj.bruno.ia.graficos.GraficoPizza;

public class Estatisticas extends JPanel implements Runnable{
    private Thread t;
    private SistemaImunologico mundo;
    public Estatisticas(SistemaImunologico mundo){
        this.mundo = mundo;
        t = new Thread(this);
        t.start();
        //revalidate();
        
    }
    
    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g.create();
        //g2d.scale(3, 3);
        g2d.drawLine(10, 20, 30, 50);
        
//        int largura =  (int) g.getFontMetrics().getStringBounds("Distribuição",g).getWidth();
//        g.drawString("Distribuição",(getWidth()-largura)/2,20);
//        g.fillRect(0, 260, getWidth(), getHeight());
    }
    
    @Override
    public void run() {
        while(true){
            repaint();
            try {
                t.sleep(500);
            } catch (InterruptedException ex) {
                //Logger.getLogger(Visual.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
    }
    
}
