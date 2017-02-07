package ufrrj.bruno.ia.renderizacao;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Iterator;
import javax.swing.JPanel;
import ufrrj.bruno.ia.SistemaImunologico;
import ufrrj.bruno.ia.celulas.Celula;
import static ufrrj.bruno.ia.celulas.Celula.TIPO_CELULA.Linfocito;
import static ufrrj.bruno.ia.celulas.Celula.TIPO_CELULA.Neutrofilo;
import ufrrj.bruno.ia.celulas.Patogeno;
import ufrrj.bruno.ia.quimica.CompostoQuimico;

/**
 * Renderiza sistema usando awt.Graphics2D. <br>
 * 
 * @param Parametros.LIMITE_FPS quantidade de atualizações da tela por segundo.
 * @author Bruno Prado
 */
public class Grafico2D extends JPanel implements Runnable{
    private final SistemaImunologico sistema = SistemaImunologico.getInstancia(); 
    final Toolkit tool = Toolkit.getDefaultToolkit();
    //=========| IMAGENS |==========//
    final Image sangue = tool.createImage(getClass().getResource("/img/blood.jpg"));
    final Image comum = tool.createImage(getClass().getResource("/img/celula.png"));
    final Image virus = tool.createImage(getClass().getResource("/img/virus.png"));
    final Image linfocito = tool.createImage(getClass().getResource("/img/Linfocito.png"));
    final Image macrofago = tool.createImage(getClass().getResource("/img/macro.png"));
    final Image neutrofilo = tool.createImage(getClass().getResource("/img/neutrofilo.png"));
    //=========| RUNTIME |=========//
    private int cameraX,cameraY;   
    private double zoom = 1;
    
    public Grafico2D(){
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
        
        Thread t = new Thread(this,"Sistema Imunologico - Renderizacao (Graphics2D)");
        t.start();
    }
    
    @Override
    public void paint(Graphics gd){
        Graphics2D g = (Graphics2D) gd.create();

        g.clearRect(0, 0, getWidth(), getHeight());
        g.setColor(new Color(245,146,146));
        
        g.drawImage(sangue,0,0,this);
        
        g.scale(zoom * getWidth()/sistema.getParametro("TAMX"), zoom * getHeight()/sistema.getParametro("TAMY"));

        if(zoom > 1){
            g.translate(cameraX,cameraY);
        }
           
        if(sistema.getMostraCamada()){ desenhaCamadaQuimica(g); }
        
        for(Celula celula : sistema.getCelulas()){  
            if(!sistema.exibir.get(celula.getTipo())){
                continue;
            }
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
                    g.setColor(tmp.getVirus().getCor());
                    g.fillPolygon(tmp.getForma());
                    tmp = null;
                    break;
                default:
                    g.drawImage(comum, celula.getPosicao().getX(), celula.getPosicao().getY(),8,8, this);
                    break;
            }
        }
  
    }
    
    public void desenhaCamadaQuimica(Graphics2D g){    
        CompostoQuimico composto;
        for (Iterator<CompostoQuimico> i = sistema.getCamada().compostos.iterator(); i.hasNext();) {
            composto = i.next();
            g.setColor(new Color(255,150,150,composto.getQuantidade() * 4));
            int diametro = composto.getDiametro();
            g.fillOval(composto.getPos().getX() - diametro/2, composto.getPos().getY() - diametro/2, diametro, diametro);
        }
    }
    
    public void pausa(int tempo){
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException ex) {
            System.out.println("Erro ao pausar a Thread Grafica");
        }
    }  
    
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

    @Override
    public void run(){
        while(true){
            repaint();      
            pausa(16);
        }
    }
}