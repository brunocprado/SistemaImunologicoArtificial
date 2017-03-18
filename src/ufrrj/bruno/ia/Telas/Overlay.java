package ufrrj.bruno.ia.telas;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import ufrrj.bruno.ia.SistemaImunologico;
import static ufrrj.bruno.ia.celulas.Celula.TIPO_CELULA.*;

public class Overlay extends JInternalFrame{
    
    private final SistemaImunologico sistema;
    
    //INSERIR SELEÇÃO DE WBCs
    
    public Overlay(SistemaImunologico sistema){
        super("Opções",false,true);
        setSize(180,300);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setFocusable(false);
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        this.sistema = sistema;
        
        //putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
 
        //CONSTROI TELA
        
        JRadioButton camadaQuimica = new JRadioButton("Camada Química",true);
        camadaQuimica.setFocusable(false);
        camadaQuimica.addChangeListener(l -> {
            sistema.setMostraCamada(camadaQuimica.isSelected());
        });
        add(camadaQuimica);
        
        add(new JRadioButton("Temperatura"));   
        
        JRadioButton debug = new JRadioButton("Debug");
        debug.setFocusable(false);
        debug.addChangeListener(l -> {
            sistema.setDebug(debug.isSelected());
        });
        add(debug);
        
        setFrameIcon(new ImageIcon(ClassLoader.getSystemResource("img/opcoes.png")));    
 
        JPanel painelExibir = new JPanel();    
        painelExibir.setBorder(BorderFactory.createTitledBorder("Exibir"));
        painelExibir.setPreferredSize(new Dimension(150,120));
        painelExibir.setLayout(new BoxLayout(painelExibir, BoxLayout.PAGE_AXIS)); 
        
        //CRIA RADIOS
        JRadioButton c1 = new JRadioButton("Macrofagos",true);
        JRadioButton c2 = new JRadioButton("Linfocitos",true);
        JRadioButton c3 = new JRadioButton("Neutrofilos",true);
        JRadioButton c4 = new JRadioButton("Patogenos",true);
        
        painelExibir.add(c1); painelExibir.add(c2); painelExibir.add(c3); painelExibir.add(c4);
   
        add(painelExibir);
        
        c1.addChangeListener(l -> {
            sistema.exibir.put(Macrofago,c1.isSelected());
        });
        
        c2.addChangeListener(l -> {
            sistema.exibir.put(Linfocito,c2.isSelected());
        });
        
        c3.addChangeListener(l -> {
            sistema.exibir.put(Neutrofilo,c3.isSelected());
        });
        
        c4.addChangeListener(l -> {
            sistema.exibir.put(Patogeno,c4.isSelected());
        });
        
        JSlider slider = new JSlider(0, 100, 50);
        slider.setMajorTickSpacing(25);
        slider.setSnapToTicks(true);
        slider.setPreferredSize(new Dimension(155,20));
        
        slider.addChangeListener((ChangeEvent e) -> {
            if (!slider.getValueIsAdjusting()) {
                sistema.setVelocidade(slider.getValue());
            }
        });
        
        add(slider);
    }
    
}