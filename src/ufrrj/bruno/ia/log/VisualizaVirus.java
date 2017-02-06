package ufrrj.bruno.ia.log;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;
import ufrrj.bruno.ia.SistemaImunologico;
import ufrrj.bruno.ia.celulas.Patogeno;

/**
 *
 * @author bruno
 */
public class VisualizaVirus extends JInternalFrame{
    
    private final Virus virus;
    
    public VisualizaVirus(Virus virus){
        super("Estatísticas : " + virus.getIdentificador(),false,true);
        this.virus = virus;
        setSize(300,200);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setFocusable(false);
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        
        JLabel qt1 = new JLabel("Quantidade de anticorpos contra : ");
        
        JButton btn = new JButton("Nova entrada");
        btn.addActionListener(e -> {
            SistemaImunologico.getInstancia().adicionaCelula(new Patogeno(virus));
        });
        
        add(qt1);
        add(btn);
        
        add(new JLabel("Inserir resto das estatísticas"));
    }
    
}
