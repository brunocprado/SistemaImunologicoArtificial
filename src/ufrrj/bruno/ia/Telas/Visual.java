package ufrrj.bruno.ia.Telas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import ufrrj.bruno.ia.Celula;
import ufrrj.bruno.ia.Mundo;

public class Visual extends JPanel implements Runnable {
    private Thread t;
    private int tamX,tamY;
    private Mundo mundo;
    private int fps = 45;
    //private int pausa = 1000/fps;
    //Image alimento = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/img/Plant2.png"));
    Image sangue = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/img/blood.jpg"));
    //=========| CELULAS |==========//
    Image comum = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/img/celula.png"));
    Image virus = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/img/virus.png"));
    Image linfocito = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/img/Linfocito.png"));
    Image macrofago = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/img/macro.png"));
    Image neutrofilo = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/img/neutrofilo.png"));
    
    //BufferStrategy b = this.Bu
    Graphics ga = null;
    public Visual(Mundo mundo){
        this.mundo = mundo;
        t = new Thread(this);
        t.start();
    }
    
    private void pausa(int tempo){
        try {
            t.sleep(tempo);
        } catch (InterruptedException ex) {
            Logger.getLogger(Visual.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void desenhaIndividuos(Graphics g){
        try {
            for(Celula celula : mundo.getCelulas()){  
                Image tmp;
                switch(celula.getClass().getSimpleName()){
                    case "Macrofago":
                        tmp = macrofago;
                        break;
                    case "Neutrofilo":
                        tmp = neutrofilo;
                        break;
                    case "Linfocito":
                        tmp = linfocito;
                        break;
                    case "Invasor":
                        tmp = virus;
                        break;
                    default:
                        tmp = comum;
                        break;
                }
                
                g.drawImage(tmp, celula.getPosicao().x, celula.getPosicao().y, this);
            }
        } catch (Exception ex) {
            try {
                t.sleep(1);
            } catch (InterruptedException exx) {}     
            desenhaIndividuos(g);
        } 
    }
    
    public void paint(Graphics g){
        //if(g != null){ ga = g; }
        // LIMPA TELA
        g.clearRect(0, 0, getWidth(), getHeight());
        g.setColor(new Color(245,146,146));
        //g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(sangue, 0, 0, this);

        //DESENHA INDIVIDUOS
        desenhaIndividuos(g);


    }

    @Override
    public void run() {
        while(true){
            repaint();
            try {
                t.sleep(1000/fps);
            } catch (InterruptedException ex) {
                Logger.getLogger(Visual.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
    }
}
