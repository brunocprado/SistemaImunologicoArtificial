package ufrrj.bruno.ia.telas;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import ufrrj.bruno.ia.Parametros;
import ufrrj.bruno.ia.SistemaImunologico;
import ufrrj.bruno.ia.log.Virus;
import ufrrj.bruno.ia.log.VisualizaVirus;
import ufrrj.bruno.ia.renderizacao.Grafico2D;

public class Janela extends JFrame{
        
    private final Overlay overlay;
    private final SistemaImunologico sistema;
    private final JDesktopPane fundo = new JDesktopPane();
    private Map<Virus,VisualizaVirus> estatisticas = new HashMap<>();
    
    public Janela(String titulo,SistemaImunologico sistema){
        super(titulo);
        this.sistema = sistema;
        setLayout(new BorderLayout());
        setSize(Parametros.LARGURA,Parametros.ALTURA); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusable(true);
        
        Grafico2D grafico = new Grafico2D(sistema);
        overlay = new Overlay(sistema);
        
        add(fundo,BorderLayout.CENTER);      
        
        fundo.add(overlay);
        fundo.add(grafico);      
        
        criaMenus();    
       
        setIconImage(Toolkit.getDefaultToolkit().createImage(getClass().getResource("/icone.png")));    
         
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e){
                grafico.setBounds(0,0,getWidth(),getContentPane().getSize().height);
            }
        });
        
        addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                grafico.keyPressed(e);
//                setExtendedState(JFrame.MAXIMIZED_BOTH); 
//                setUndecorated(true);
            }
        });  
        
        Monitor monitor = new Monitor();
    }
    
    public final void criaMenus(){
        JMenuBar menu = new JMenuBar();
        JMenu menu1 = new JMenu("Novo");
            JMenuItem submenu1 = new JMenuItem("Patogeno");
        JMenu menu2 = new JMenu("Pausar");
        JMenu menu3 = new JMenu("Opções");
        JMenu menu4 = new JMenu("Sobre");
        
        submenu1.addActionListener(e -> {
            NovoVirus novoVirus = new NovoVirus(sistema,this);
            novoVirus.setLocationRelativeTo(getContentPane());
            novoVirus.setIconImage(getIconImage());
            novoVirus.setVisible(true);
        });      
        menu1.add(submenu1);
        menu.add(menu1);

        menu2.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sistema.pausada = !sistema.pausada;
            }
        });
        menu.add(menu2);

        menu3.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if(overlay.isVisible()){
                    overlay.setVisible(false);
                } else {
                    overlay.setLocation(getWidth() - 215,20);
                    overlay.setVisible(true);
                }
            }
        });
        menu.add(menu3);
        
        menu4.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Sobre sobre = new Sobre();
                sobre.setLocationRelativeTo(null);
                sobre.setVisible(true);
            }
        });
        menu.add(menu4);

        setJMenuBar(menu);
    }
    
    public void visualizaVirus(Virus virus){
        VisualizaVirus tmp = estatisticas.get(virus);
        tmp.setVisible(!tmp.isVisible());
    }
    
    public void novoVirus(Virus virus){
        VisualizaVirus tmp = new VisualizaVirus(virus,sistema);   
        estatisticas.put(virus, tmp);
        fundo.add(tmp,0);
    }
    
    private final class Monitor implements Runnable{
        
        public Monitor(){
            Thread t = new Thread(this,"Monitor");
            t.start();
        }
        
        @Override
        public void run() {
            while(true){
                String tmp = "SIA";

                if(sistema.pausada){
                    tmp += "  -  Pausado";
                }
                tmp += "  |  Tempo de execução : " + (System.currentTimeMillis() - sistema.getInicio())/1000 + " segundos"; 
                setTitle(tmp);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
//                    Logger.getLogger(Janela.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }      
}