package ufrrj.bruno.ia.Telas;

import ufrrj.bruno.ia.renderizacao.Grafico2D;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import ufrrj.bruno.ia.SistemaImunologico;
import ufrrj.bruno.ia.celulas.Patogeno;

public class Janela extends JFrame{
    
    private final JPanel tela;
    private final JFrame fEstatisticas;
    private Grafico2D grafico;
    private SistemaImunologico sistema;
    
    public Janela(String titulo,SistemaImunologico sistema){
        super(titulo);
        this.sistema = sistema;
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        tela = new JPanel(new BorderLayout());
        this.getContentPane().add(tela,BorderLayout.CENTER);
     
        fEstatisticas = new JFrame("Estatisticas");
        fEstatisticas.setSize(100,720);
        fEstatisticas.getContentPane().add(new Estatisticas(sistema));
        
        criaMenus();    
       
        setIconImage(Toolkit.getDefaultToolkit().createImage(getClass().getResource("/icone.png")));
        
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent arg0){
                System.exit(0);
            }
        });
        
        Monitor monitor = new Monitor();
    }
    
    public void criaMenus(){
        JMenuBar menu = new JMenuBar();
        JMenu menu1 = new JMenu();
        JMenu menu2 = new JMenu();
        JMenu menu3 = new JMenu();
        JMenu menu4 = new JMenu();
        
        menu1.setText("Novo");
        menu1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                for(int i=0;i<10;i++){
                    sistema.adicionaCelula(new Patogeno(sistema));
                }
                //System.out.println(sistema);
            }
        });
        menu.add(menu1);

        menu2.setText("Pausar");
        menu2.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
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
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                
                fEstatisticas.setVisible(true);
            }
        });
        menu.add(menu3);

        menu4.setText("Sobre");
        menu4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Sobre sobre = new Sobre();
                sobre.setLocationRelativeTo(null);
                sobre.setVisible(true);
            }
        });
        menu.add(menu4);

        setJMenuBar(menu);
    }
    
    public void setVisivel(boolean visivel){
        this.setVisible(visivel);
    }
    
    private class Monitor implements Runnable{
        
        private Thread t;
        
        public Monitor(){
            t = new Thread(this);
            t.start();
        }
        
        @Override
        public void run() {
            while(true){
                String tmp = "SIA";

                if(sistema.pausada){
                    tmp += " - Pausado";
                }
                tmp += " | Tempo de execução : " + (System.currentTimeMillis() - sistema.getInicio())/1000; 
                setTitle(tmp);

                try {
                    t.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Janela.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
      
    }
        
}