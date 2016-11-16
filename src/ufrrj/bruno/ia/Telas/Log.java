package ufrrj.bruno.ia.Telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

/**
 * Log <br>
 * 
 * @author Bruno Prado
 */
public class Log extends JFrame{
    
    private JLabel txt;
    private String log = "<html>";
    JScrollPane scroll;
    
    public Log(){
        super("Log");
        setSize(640,480);
        setResizable(true);
        
        setIconImage(Toolkit.getDefaultToolkit().createImage(getClass().getResource("/icone.png"))); 
        
        JPanel p = new JPanel();      
        p.setBackground(Color.BLACK);
        
        txt = new JLabel();
        txt.setForeground(Color.green);
        txt.setLocation(10,10);
        txt.setHorizontalTextPosition(JLabel.LEFT);
        
        p.add(txt);
      
        scroll = new JScrollPane(p);

        getContentPane().add(scroll,BorderLayout.CENTER);
        setVisible(true);
    }
    
    public void imprime(String texto){
        log += "<br>" + texto;
        txt.setText(log + "</html>");
        scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
    }
    
}