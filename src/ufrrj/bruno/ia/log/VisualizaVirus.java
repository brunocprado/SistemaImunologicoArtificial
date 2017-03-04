package ufrrj.bruno.ia.log;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import ufrrj.bruno.ia.SistemaImunologico;
import ufrrj.bruno.ia.celulas.Patogeno;

/**
 *
 * @author bruno
 */
public class VisualizaVirus extends JInternalFrame implements Runnable{
    
    private final Virus virus;
    private int tick = 0;

    //====| GRAFICO |====//
    private final XYSeries antigenos = new XYSeries("Antigenos");
    private final XYSeries anticorpos = new XYSeries("Anticorpos");
    private final XYSeriesCollection dados;
    
    public VisualizaVirus(Virus virus){
        super("Estatísticas : " + virus.getIdentificador(),false,true);
        this.virus = virus;
        setSize(400,320);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setFocusable(false);
        setBackground(Color.WHITE);
        setLayout(new BorderLayout(0,10));
        setResizable(true);
        
        dados = new XYSeriesCollection(antigenos);
        dados.addSeries(anticorpos);
         
        JFreeChart grafico = ChartFactory.createXYLineChart(
            virus.getIdentificador(),           
            "Tempo",
            "Volume", 
            dados,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );

        ChartPanel painelGrafico = new ChartPanel(grafico);
        painelGrafico.setPreferredSize(new Dimension(370, 220));
        
        add(painelGrafico,BorderLayout.CENTER);  
        
        JPanel painel = new JPanel(new BorderLayout());
        JButton btn = new JButton("Nova entrada");   
                
        painel.add(btn,BorderLayout.CENTER);
                
        add(painel,BorderLayout.SOUTH);
         
        btn.addActionListener(e -> {
            SistemaImunologico.getInstancia().adicionaCelula(new Patogeno(virus));
        });
        
        Thread t = new Thread(this,"Estatisticas " + virus.getIdentificador());
        t.start();
    }

    @Override
    public void run() {
        while(true){
            antigenos.add(tick++,virus.getQuantidade());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(VisualizaVirus.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}