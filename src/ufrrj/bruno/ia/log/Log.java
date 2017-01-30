package ufrrj.bruno.ia.log;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import ufrrj.bruno.ia.SistemaImunologico;

/**
 * Log <br>
 * 
 * @author Bruno Prado
 */
public class Log extends JFrame{
    
    private SistemaImunologico sistema;
    private JLabel txt;
    private String log = "<html>";
    JScrollPane scroll;
    
    public Log(SistemaImunologico sistema){
        super("Log");
        this.sistema = sistema;
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
        
        JTextField comando = new JTextField();
        
        getContentPane().add(comando,BorderLayout.SOUTH);
        
        comando.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    executaComando(comando.getText());
                    comando.setText("");
                }
            }
        }); 
               
        setVisible(true);
    }
    
    public void executaComando(String comando){
        String[] tmp = comando.split(" ");
        sistema.mudaParametro(tmp[0], Float.parseFloat(tmp[1]));
    }
    
    public void imprime(String texto){
        log += "<br>" + texto;
        txt.setText(log + "</html>");
        scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
    }
    
}