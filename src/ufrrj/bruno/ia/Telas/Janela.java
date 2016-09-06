package ufrrj.bruno.ia.Telas;

//import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import ufrrj.bruno.ia.Parametros;
import ufrrj.bruno.ia.celulas.Celula;

public class Janela extends JFrame{
    
    private JPanel tela;
    private GL gl;
    Canvas canvass;
    public Janela(String titulo,FPSAnimator fps,int x,int y){
        super(titulo);
        this.setSize(new Dimension(x,y));
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        tela = new JPanel(new BorderLayout());
        this.getContentPane().add(tela,BorderLayout.CENTER);
        
        
        canvass = new Canvas();
        tela.add(canvass);
        canvass.setSize(500,500);
        canvass.createBufferStrategy(2);
        canvass.setIgnoreRepaint(true);
        
        
        BufferStrategy bs = canvass.getBufferStrategy();
         ArrayList<Celula> celulas = new ArrayList<Celula>();
                             java.awt.Graphics2D g = (java.awt.Graphics2D) bs.getDrawGraphics();
        // celulas.add(new Celula(new SistemaImunologico(),true));
        while (true) {
//            do {
                    //for(int i = 1;i<20;i++){
                        g.drawOval(20, 10, 10, 20);
                                                g.drawOval(50, 20, 10, 20);

                    
                    g.dispose();
        
//            }
        }
        
        
        criaMenus();    
       
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent arg0){
                if(fps.isStarted()){
                    fps.stop();
                }
                System.exit(0);
            }
        });
    }
    
    public void criaMenus(){
        JMenuBar menu = new JMenuBar();
        JMenu menu1 = new JMenu();
        JMenu menu2 = new JMenu();
        JMenu menu3 = new JMenu();
        JMenu menu4 = new JMenu();
        
        menu1.setText("Novo");
        menu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                System.out.println("novo");
            }
        });
        menu.add(menu1);

        menu2.setText("Pausar");
        menu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                gl.getSistema().pausada = !gl.getSistema().pausada;
                if(gl.getSistema().pausada){
                    menu2.setText("Resumir");
                    setTitle("SIA - Pausado");
                } else {
                    menu2.setText("Pausar");
                    setTitle("SIA");
                }
            }
        });
        menu.add(menu2);
        
        menu3.setText("Estatisticas");
        menu3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                System.out.println("novo");
            }
        });
        menu.add(menu3);

        menu4.setText("Sobre");
        menu4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                //insere(evt);
            }
        });
        menu.add(menu4);

        setJMenuBar(menu);
    }
//    
//    public void setOpenGLCanvas(GLCanvas canvas){
//        tela.add(canvas);
//    }
//    
    public void setGL(GL gl){
        this.gl = gl;
    }
    
    public void setVisivel(boolean visivel){
        this.setVisible(visivel);
    }
}
