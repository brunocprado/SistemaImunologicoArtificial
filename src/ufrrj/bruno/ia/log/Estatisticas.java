package ufrrj.bruno.ia.log;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import ufrrj.bruno.ia.SistemaImunologico;
import ufrrj.bruno.ia.celulas.Celula;

/**
 *
 * @author Bruno Prado
 */
public class Estatisticas extends JFrame implements Runnable {

    private SistemaImunologico sistema = SistemaImunologico.getInstancia();
    private int tick = 0;

    //====| GRAFICO |====//
    private final XYSeries macrofagos = new XYSeries("Magrofagos");
    private final XYSeries neutrofilos = new XYSeries("Neutrófilos");
    private final XYSeries linfocitos = new XYSeries("Linfocitos");
    private final XYSeries patogenos = new XYSeries("Patogenos");
    private final XYSeriesCollection dados;
    
    public Estatisticas(){
        super("Estatisticas");
        setSize(800,600);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setFocusable(false);
        setLayout(new GridLayout(2,2));
        setResizable(true);
        
        Thread t = new Thread(this,"Estatisticas");
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                t.interrupt();
            }
        });

        dados = new XYSeriesCollection(macrofagos);
        dados.addSeries(patogenos); 
        dados.addSeries(neutrofilos);
        dados.addSeries(linfocitos);
        
        JFreeChart grafico = ChartFactory.createXYLineChart(
            "População",           
            "Tempo",
            "Volume", 
            dados,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );

        ChartPanel painelGrafico = new ChartPanel(grafico);
        //painelGrafico.setPreferredSize(new Dimension(380, 280));
        
//        ChartPanel painelGrafico2 = new ChartPanel(grafico);
        //painelGrafico.setPreferredSize(new Dimension(380, 280));
        
        add(painelGrafico);  
//        add(painelGrafico2);  
//        add(painelGrafico);  
//        add(painelGrafico);  

        t.start();
    }
    
    private void atualizaGraficos(){
        int qtMacrofagos = 0,qtPatogenos = 0,qtNeutrofilos = 0,qtLinfocitos = 0;   
        
        //REMOVER ISSO DEPOIS
        for(Celula celula : sistema.getCelulas()){
            if(null != celula.getTipo()) switch (celula.getTipo()) {
                case Macrofago:
                    qtMacrofagos++;
                    break;
                case Patogeno:
                    qtPatogenos++;
                    break;
                case Neutrofilo:
                    qtNeutrofilos++;
                    break;
                case Linfocito:
                    qtLinfocitos++;
                    break;
            }
        }
        
        macrofagos.add(tick,qtMacrofagos);
        patogenos.add(tick,qtPatogenos);
        neutrofilos.add(tick,qtNeutrofilos);
        linfocitos.add(tick,qtLinfocitos);
        
        tick++;
    }
    
    @Override
    public void run() {
        try{
           while(!Thread.interrupted()){
                atualizaGraficos();
                Thread.sleep(1000);
           }
        } catch (InterruptedException consumed){
            
        }
   }
}