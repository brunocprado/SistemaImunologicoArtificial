package ufrrj.bruno.ia.Telas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import ufrrj.bruno.ia.Mundo;
import ufrrj.bruno.ia.graficos.Fatia;
import ufrrj.bruno.ia.graficos.GraficoPizza;

public class Estatisticas extends JPanel implements Runnable{
    private Thread t;
    private Mundo mundo;
    Estatisticas(Mundo mundo){
        this.mundo = mundo;
        t = new Thread(this);
        t.start();
        //revalidate();
        
    }
    
    public void paint(Graphics g){
        int largura =  (int) g.getFontMetrics().getStringBounds("Distribuição",g).getWidth();
        g.drawString("Distribuição",(getWidth()-largura)/2,20);
        g.fillRect(0, 260, getWidth(), getHeight());
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
