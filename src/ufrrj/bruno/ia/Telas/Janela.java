package ufrrj.bruno.ia.Telas;

import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import sun.applet.Main;
import ufrrj.bruno.ia.Parametros;

public class Janela extends JFrame{
    
    private JPanel tela;
    private GL gl;
    
    public Janela(String titulo,FPSAnimator fps,int x,int y){
        super(titulo);
        this.setSize(new Dimension(x,y));
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        tela = new JPanel(new BorderLayout());
        this.getContentPane().add(tela,BorderLayout.CENTER);
        
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
                } else {
                    menu2.setText("Pausar");
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
    
    public void setOpenGLCanvas(GLCanvas canvas){
        tela.add(canvas);
    }
    
    public void setGL(GL gl){
        this.gl = gl;
    }
    
    public void setVisivel(boolean visivel){
        this.setVisible(visivel);
    }
}
