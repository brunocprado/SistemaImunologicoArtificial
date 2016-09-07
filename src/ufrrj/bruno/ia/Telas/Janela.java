package ufrrj.bruno.ia.Telas;

import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import ufrrj.bruno.ia.SistemaImunologico;

public class Janela extends JFrame{
    
    private JPanel tela;
    private OpenGL gl;
    private Grafico2D grafico;
    private FPSAnimator fps;
    private SistemaImunologico sistema;
    
    public Janela(String titulo,SistemaImunologico sistema){
        super(titulo);
        this.sistema = sistema;
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        tela = new JPanel(new BorderLayout());
        this.getContentPane().add(tela,BorderLayout.CENTER);
     
        criaMenus();    
       
        setIconImage(Toolkit.getDefaultToolkit().createImage(getClass().getResource("/icone.png")));
        
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent arg0){
                if(fps != null && fps.isStarted()){
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
                sistema.pausada = !sistema.pausada;
                if(sistema.pausada){
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
   
    public void setOpenGLCanvas(GLCanvas canvas){
        tela.add(canvas);
    }
   
    public void setGL(OpenGL gl,FPSAnimator fps){
        this.gl = gl;
        this.fps = fps;
    }
    
    public void setVisivel(boolean visivel){
        this.setVisible(visivel);
    }
}
