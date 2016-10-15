package ufrrj.bruno.ia.Telas;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import ufrrj.bruno.ia.SistemaImunologico;
import ufrrj.bruno.ia.celulas.Patogeno;

public class Janela extends JFrame{
    
    private final JPanel tela;
    private final JFrame fEstatisticas;
    private final SistemaImunologico sistema;
    
    public Janela(String titulo,SistemaImunologico sistema){
        super(titulo);
        this.sistema = sistema;
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        tela = new JPanel(new BorderLayout());
        this.getContentPane().add(tela,BorderLayout.CENTER);
     
        fEstatisticas = new JFrame("Estatisticas");
        fEstatisticas.setSize(200,720);
        fEstatisticas.getContentPane().add(new Estatisticas(sistema));
        
        criaMenus();    
       
        setIconImage(Toolkit.getDefaultToolkit().createImage(getClass().getResource("/icone.png")));    
        
        Monitor monitor = new Monitor();
    }
    
    public final void criaMenus(){
        JMenuBar menu = new JMenuBar();
        JMenu menu1 = new JMenu("Novo");
        JMenu menu2 = new JMenu("Pausar");
        JMenu menu3 = new JMenu("Estatisticas");
        JMenu menu4 = new JMenu("Camada Quimica (N)");
        JMenu menu5 = new JMenu("Sobre");
        
        menu1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                for(int i=0;i<10;i++){
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
                fEstatisticas.setVisible(true);
            }
        });
        menu.add(menu3);

        menu4.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sistema.setMostraCamada(!sistema.getMostraCamada());
                if(sistema.getMostraCamada()){
                    menu4.setText("Camada Quimica (S)");
                } else {
                    menu4.setText("Camada Quimica (N)");
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
    
    public void setVisivel(boolean visivel){
        this.setVisible(visivel);
    }
    
    private class Monitor implements Runnable{
        
        private final Thread t;
        
        public Monitor(){
            t = new Thread(this);
            t.start();
        }
        
        @Override
        public void run() {
            while(true){
                String tmp = "SIA";

                if(sistema.pausada){
                    tmp += "  -  Pausado";
                }
                tmp += "  |  Tempo de execução : " + (System.currentTimeMillis() - sistema.getInicio())/1000; 
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