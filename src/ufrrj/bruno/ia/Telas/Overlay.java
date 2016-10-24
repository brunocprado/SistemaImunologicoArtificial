package ufrrj.bruno.ia.Telas;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javafx.scene.layout.Border;
import javax.swing.BorderFactory;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import ufrrj.bruno.ia.SistemaImunologico;

public class Overlay extends JInternalFrame{
    
    private final SistemaImunologico sistema;
    
    public Overlay(SistemaImunologico sistema){
        super("Opções",false,true);
        setSize(200,300);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setFocusable(false);
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        this.sistema = sistema;
        
        //CONSTROI TELA
        
        
        JRadioButton camadaQuimica = new JRadioButton("Camada Química");
        add(camadaQuimica);
        camadaQuimica.setFocusable(false);
        camadaQuimica.addChangeListener(l -> {
            sistema.setMostraCamada(camadaQuimica.isSelected());
        });
        
        add(new JRadioButton("Temperatura"));   
        
    }
    
}