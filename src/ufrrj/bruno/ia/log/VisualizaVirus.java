package ufrrj.bruno.ia.log;

import java.awt.FlowLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
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
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        dados = new XYSeriesCollection(antigenos);
        dados.addSeries(anticorpos);
         
        JFreeChart chart = ChartFactory.createXYLineChart(
            virus.getIdentificador(),           
            "Tempo",
            "Volume", 
            dados,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(370, 220));
        
        add(chartPanel);
        
        JButton btn = new JButton("Nova entrada");
        btn.addActionListener(e -> {
            SistemaImunologico.getInstancia().adicionaCelula(new Patogeno(virus));
        });
        
        add(btn);
        
        Thread t = new Thread(this);
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
