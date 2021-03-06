package ufrrj.bruno.log;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import ufrrj.bruno.SistemaImunologico;

/**
 * Log <br>
 * 
 * @author Bruno Prado
 */
public class Log extends JFrame {
    
    private JLabel txt;
    private String log = "<html>";
    private final JScrollPane scroll;
    
    public Log(){
        super("Log");
        setSize(650,480);
        setMinimumSize(new Dimension(500,400));
        setResizable(true);
        setLocation(2, 10);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        setIconImage(Toolkit.getDefaultToolkit().createImage(getClass().getResource("/img/terminal.png"))); 
        
        JPanel p = new JPanel();      
        p.setBackground(Color.BLACK);
        
        txt = new JLabel();
        txt.setForeground(Color.green);
        txt.setLocation(10,10);
        txt.setHorizontalTextPosition(JLabel.LEFT);
        
        p.add(txt);
      
        scroll = new JScrollPane(p);

        getContentPane().add(scroll,BorderLayout.CENTER);
        
        JPanel p2 = new JPanel(new BorderLayout(5,5));
        p2.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        JTextField comando = new JTextField();
        JButton btnComando = new JButton("OK"); 
        
        p2.add(comando,BorderLayout.CENTER);
        p2.add(btnComando,BorderLayout.EAST);
        
        getContentPane().add(p2,BorderLayout.SOUTH);
        
        comando.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER && !comando.getText().trim().equals("")){
                    executaComando(comando.getText());
                    comando.setText("");
                }
            }
            
            @Override
            public void keyReleased(KeyEvent e){
                int qt = 0;
                String ultimo = "";
                Set<String> chaves = SistemaImunologico.getInstancia().getParametros().keySet();
                for(String tmp : chaves){
                    if(tmp.toLowerCase().startsWith(comando.getText())){
                        qt++; ultimo = tmp;
                    }
                }
                if(qt == 1) comando.setText(ultimo + " ");       
            }
        }); 
           
        btnComando.addActionListener(l -> {
            if(comando.getText().trim().equals("")) return;
            executaComando(comando.getText());
            comando.setText("");
            comando.requestFocus();
        });
        
        setVisible(true);
    }
    
    public void executaComando(String comando){     
        String[] tmp = comando.split(" ");
        if(tmp.length < 2) return;
        SistemaImunologico.getInstancia().mudaParametro(tmp[0], Integer.parseInt(tmp[1]));
        imprime("[ " + tmp[0] + " ] alterado para " + tmp[1],"#ffffff");
    }
    
    public void imprime(String texto){
        log += "<br>" + texto;     
        txt.setText(log + "</html>");
        scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
    }
    
    public void imprime(String texto,String cor){
        log += "<span style='color:" + cor + ";'><br>" + texto + "</span>";
        txt.setText(log + "</html>");
        scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
    }
    
}