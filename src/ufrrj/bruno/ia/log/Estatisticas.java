package ufrrj.bruno.ia.log;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import ufrrj.bruno.ia.SistemaImunologico;
import ufrrj.bruno.ia.celulas.Celula;

/**
 *
 * @author Bruno Prado
 */
public class Estatisticas extends JFrame implements Runnable {

    private final SistemaImunologico sistema = SistemaImunologico.getInstancia();
    private int tick = 0;

    //====| GRAFICO |====//
    private final XYSeries macrofagos = new XYSeries("Magrofagos");
    private final XYSeries neutrofilos = new XYSeries("Neutrófilos");
    private final XYSeries linfocitos = new XYSeries("Linfocitos");
    private final XYSeries patogenos = new XYSeries("Patogenos");
    
    private final XYSeries temporizacoes = new XYSeries("Temporizacoes");
    
    private final XYSeriesCollection dados;
    private final XYSeriesCollection dados2 = new XYSeriesCollection(temporizacoes);
    
    public Estatisticas(){
        super("Estatisticas");
        setSize(650,600);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setFocusable(false);
        setResizable(true);
        
        setIconImage(Toolkit.getDefaultToolkit().createImage(getClass().getResource("/img/estatisticas.png"))); 
        
        JPanel contentPanel = new JPanel(new GridLayout(2,1,10,10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.setBackground(new Color(150,100,100));
        setContentPane(contentPanel);

        Thread t = new Thread(this,"Estatisticas");

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
        grafico.setPadding(new RectangleInsets(10,10,10,10));

        ChartPanel painelGrafico = new ChartPanel(grafico);

        JFreeChart grafico2 = ChartFactory.createXYLineChart(
            "Tempo médio detecção",           
            "",
            "", 
            dados2,
            PlotOrientation.VERTICAL,
            false,
            true,
            false
        );
        grafico2.setPadding(new RectangleInsets(10,10,10,10));

        ChartPanel painelGrafico2 = new ChartPanel(grafico2);      
        
        add(painelGrafico);  
        add(painelGrafico2);
        
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
        
        temporizacoes.add(tick,sistema.getTemporizacao());
        
        tick++;
    }
    
    @Override
    public void run() {
        try{
            while(true){
                atualizaGraficos();
                Thread.sleep(1000);
            }
        } catch (InterruptedException consumed){} 
   }
}