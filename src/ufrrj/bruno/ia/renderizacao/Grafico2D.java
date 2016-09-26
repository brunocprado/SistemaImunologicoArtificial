package ufrrj.bruno.ia.renderizacao;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import javax.swing.JPanel;
import ufrrj.bruno.ia.Parametros;
import ufrrj.bruno.ia.SistemaImunologico;
import ufrrj.bruno.ia.celulas.Celula;
import static ufrrj.bruno.ia.celulas.Celula.TIPO_CELULA.Patogeno;
import ufrrj.bruno.ia.celulas.Patogeno;

public class Grafico2D extends JPanel implements Runnable{
    private Thread t;
    private SistemaImunologico sistema = null; 
    Toolkit tool = Toolkit.getDefaultToolkit();
    Image sangue = tool.createImage(getClass().getResource("/img/blood.jpg"));
    //=========| CELULAS |==========//
    Image comum = tool.createImage(getClass().getResource("/img/celula.png"));
    Image virus = tool.createImage(getClass().getResource("/img/virus.png"));
    Image linfocito = tool.createImage(getClass().getResource("/img/Linfocito.png"));
    Image macrofago = tool.createImage(getClass().getResource("/img/macro.png"));
    Image neutrofilo = tool.createImage(getClass().getResource("/img/neutrofilo.png"));
    //=========| RUNTIME |=========//
    private int cameraX,cameraY;   
    private double zoom = 1;
    
    public Grafico2D(SistemaImunologico sistema){
        this.sistema = sistema;    
        
        addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                switch(e.getKeyCode()){
                    case KeyEvent.VK_RIGHT:
                        if(cameraX - getWidth()/zoom > -getWidth()) cameraX -= 10;
                        break;
                    case KeyEvent.VK_LEFT:
                        if(cameraX < 0) cameraX += 10;
                        break;
                    case KeyEvent.VK_UP:
                        if(cameraY < 0) cameraY += 10;
                        break;
                    case KeyEvent.VK_DOWN:
                        if(cameraY - getHeight()/zoom > -getHeight()) cameraY -= 10;
                        break;       
                    case KeyEvent.VK_A:
                        zoom += 0.1;
                        break;
                    case KeyEvent.VK_S:
                        if (zoom >= 1.1){ zoom -= 0.1; }
                        break;
                }
            }
        });
        
        setFocusable(true);
        
        addMouseMotionListener(new MouseAdapter() {
            int antX = getX();
            int antY = getY();
            @Override
            public void mouseDragged(MouseEvent e) {
                Point pos = e.getPoint();
                if(pos.x > antX && cameraX < 0){
                    cameraX += 10/zoom;
                } else if(cameraX - getWidth()/zoom > -getWidth()){
                    cameraX -= 10/zoom;
                }              
//                if(pos.y < antY && cameraY < 0){
//                    System.out.println("C");
//                    cameraY -= 10;
//                } else if(cameraY - getHeight()/zoom > -getHeight()){
//                    //cameraY += 10;
//                }
                
                if(pos.y < antY && cameraY < 0){
                    cameraY += 10;
                } else if(cameraY - getHeight()/zoom > -getHeight()){
                    cameraY -= 10;
                }
                antX = pos.x; antY = pos.y;
            }
        });
        
        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getPreciseWheelRotation() < 0) {
                    zoom += 0.1;
                } else if (zoom >= 1.1){
                    zoom -= 0.1;
                }
            }
        });
        
        t = new Thread(this,"Sistema Imunologico - Renderizacao (Graphics2D)");
        t.start();
    }
    
    @Override
    public void paint(Graphics gd){
        Graphics2D g = (Graphics2D) gd.create();

        g.clearRect(0, 0, getWidth(), getHeight());
        g.setColor(new Color(245,146,146));
        
        g.drawImage(sangue,0,0,this);
        
        g.scale(zoom * getWidth()/Parametros.TAMX, zoom * getHeight()/Parametros.TAMY);

        if(zoom > 1){;
            g.translate(cameraX,cameraY);
        }  
                
        for(Celula celula : sistema.getCelulas()){  
            switch(celula.getTipo()){
                case Macrofago:
                    g.drawImage(macrofago, celula.getPosicao().getX(), celula.getPosicao().getY(),8,8, this);
                    break;
                case Neutrofilo:
                    g.drawImage(neutrofilo, celula.getPosicao().getX(), celula.getPosicao().getY(),8,8, this);
                    break;
                case Linfocito:
                    g.drawImage(linfocito, celula.getPosicao().getX(), celula.getPosicao().getY(),8,8, this);
                    break;
                case Patogeno:
                    Patogeno tmp = (Patogeno)celula;
                    g.setColor(tmp.getCor());
                    g.fillPolygon(tmp.getForma());
                    tmp = null;
                    break;
                default:
                    g.drawImage(comum, celula.getPosicao().getX(), celula.getPosicao().getY(),8,8, this);
                    break;
            }
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
    public void run(){
        while(true){
            repaint();
            pausa(1000/Parametros.LIMITE_FPS);
        }
    }
}