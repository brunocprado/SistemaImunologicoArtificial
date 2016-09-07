package ufrrj.bruno.ia.Telas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;
import ufrrj.bruno.ia.Parametros;
import ufrrj.bruno.ia.SistemaImunologico;
import ufrrj.bruno.ia.celulas.Celula;

public class Grafico2D extends JPanel implements Runnable{
    private Thread t;
    private SistemaImunologico sistema = null; 
    Image sangue = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/img/blood.jpg"));
    //=========| CELULAS |==========//
    Image comum = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/img/celula.png"));
    Image virus = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/img/virus.png"));
    Image linfocito = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/img/Linfocito.png"));
    Image macrofago = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/img/macro.png"));
    Image neutrofilo = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/img/neutrofilo.png"));
    
    public Grafico2D(SistemaImunologico sistema){
        this.sistema = sistema;    
        t = new Thread(this,"Sistema Imunologico - Renderizacao (Graphics2D)");
        t.start();
    }
 
    @Override
    public void paint(Graphics gd){
        Graphics2D g = (Graphics2D) gd.create();
        
        // LIMPA TELA
        g.clearRect(0, 0, getWidth(), getHeight());
        g.setColor(new Color(245,146,146));
        for(Celula celula : sistema.getCelulas()){  
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

            g.drawImage(tmp, celula.getPosicao().getX(), celula.getPosicao().getY(), this);
        }
    }
    
    public void pausa(int tempo){
        try {
            t.sleep(tempo);
        } catch (InterruptedException ex) {
            System.out.println("Erro ao pausar a Thread Grafica");
        }
    }  

    @Override
    public void run() {
        while(true){
            repaint();
            pausa(1000/Parametros.LIMITE_FPS);
        }
    }
}
