package ufrrj.bruno.ia.Telas;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import ufrrj.bruno.ia.Parametros;
import ufrrj.bruno.ia.SistemaImunologico;
import ufrrj.bruno.ia.celulas.Patogeno;
import ufrrj.bruno.ia.renderizacao.Grafico2D;

public class Janela extends JFrame{
        
//    private final JFrame fEstatisticas;
    private final Overlay overlay;
    private final SistemaImunologico sistema;
    
    public Janela(String titulo,SistemaImunologico sistema){
        super(titulo);
        this.sistema = sistema;
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setSize(Parametros.LARGURA,Parametros.ALTURA);      
        setFocusable(true);
        
        Grafico2D grafico = new Grafico2D(sistema);
        overlay = new Overlay(sistema);
        
        JDesktopPane fundo = new JDesktopPane();
                
        add(fundo,BorderLayout.CENTER);      
        
        fundo.add(overlay);
        fundo.add(grafico);      
        
//        fEstatisticas = new JFrame("Estatisticas");
//        fEstatisticas.setSize(200,720);
//        fEstatisticas.getContentPane().add(new Estatisticas(sistema));
        
        criaMenus();    
       
        setIconImage(Toolkit.getDefaultToolkit().createImage(getClass().getResource("/icone.png")));    
        
        Monitor monitor = new Monitor();
        
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
        
    }
    
    public final void criaMenus(){
        JMenuBar menu = new JMenuBar();
        JMenu menu1 = new JMenu("Novo");
        JMenu menu2 = new JMenu("Pausar");
        JMenu menu3 = new JMenu("Estatisticas");
        JMenu menu4 = new JMenu("Opções");
        JMenu menu5 = new JMenu("Sobre");
        
        menu1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                for(int i=0;i<5;i++){
                    //SÓ PRA TESTE MESMO
                    sistema.adicionaCelula(new Patogeno(sistema));
                }
            }
        });
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
                //fEstatisticas.setVisible(true);
            }
        });
        menu.add(menu3);

        menu4.addMouseListener(new java.awt.event.MouseAdapter() {
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
        menu.add(menu4);
        
        menu5.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Sobre sobre = new Sobre();
                sobre.setLocationRelativeTo(null);
                sobre.setVisible(true);
            }
        });
        menu.add(menu5);

        setJMenuBar(menu);
    }
    
    private class Monitor implements Runnable{
        
        private final Thread t;
        
        public Monitor(){
            t = new Thread(this,"Monitor");
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