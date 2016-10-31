package ufrrj.bruno.ia.Telas;

import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JRadioButton;
import ufrrj.bruno.ia.SistemaImunologico;

public class Overlay extends JInternalFrame{
    
    private final SistemaImunologico sistema;
    
    //INSERIR SELEÇÃO DE WBCs
    
    public Overlay(SistemaImunologico sistema){
        super("Opções",false,true);
        setSize(180,150);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setFocusable(false);
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        this.sistema = sistema;
        
        //CONSTROI TELA
        
        JRadioButton camadaQuimica = new JRadioButton("Camada Química");
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
        
        setFrameIcon(new ImageIcon(ClassLoader.getSystemResource("opcoes.png")));    
        
//        JButton telaCheia = new JButton("Tela cheia");
////        telaCheia.setLayout(null);
////        telaCheia.setPreferredSize(new Dimension(getWidth() - 30,20));
//        telaCheia.addActionListener(l -> {;
//            System.out.println("aaa");
//        });
//        add(telaCheia)

//        JPanel painelExibir = new JPanel();
//        painelExibir.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
//        painelExibir.add(new JRadioButton("TESTE"));
//        
//        add(painelExibir);
    }
    
}