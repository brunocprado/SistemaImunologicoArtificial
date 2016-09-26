package ufrrj.bruno.ia.Telas;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Log extends JFrame{
    
    private JLabel txt;
    private String log = "<html>";
    
    public Log(){
        super("Log");
        setSize(640,480);
        setResizable(false);
        setLocationRelativeTo(null);
        
        JPanel p = new JPanel();      
        p.setSize(600,440);
        p.setBackground(Color.BLACK);
        
        txt = new JLabel();
        txt.setForeground(Color.green);
        txt.setLocation(10,10);
        
        p.add(txt);
      
        getContentPane().add(p,BorderLayout.CENTER);
        setVisible(true);
//        for(int i=0;i<5;i++){
//            imprime("teste");
//        }
    }
    
    public void imprime(String texto){
        log += "<br>" + texto;
        txt.setText(log + "</html>");
    }
    
}