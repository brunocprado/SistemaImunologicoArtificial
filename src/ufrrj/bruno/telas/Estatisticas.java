package ufrrj.bruno.telas;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import ufrrj.bruno.SistemaImunologico;

public class Estatisticas extends Stage{

    private final SistemaImunologico sistema = SistemaImunologico.getInstancia();
    private final VBox container = new VBox();
    
    XYChart.Series macrofagos = new XYChart.Series();
    XYChart.Series patogenos = new XYChart.Series();
    XYChart.Series neutrofilos = new XYChart.Series();
    XYChart.Series linfocitos = new XYChart.Series();
    
    XYChart.Series temporizacoes = new XYChart.Series();
    
    private Timeline timeline; //TODO
    
    public Estatisticas(){
        setTitle("Estatísticas");
        
        container.setPadding(new Insets(10,10,10,10));
        container.setSpacing(10);
        
        setScene(new Scene(container, 800, 600));
        criaGraficos();
    }
    
    private void criaGraficos(){
        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();
        
        x.setLabel("Tempo");
        y.setLabel("Quantidade");
        
        LineChart<Number,Number> grafico = new LineChart<>(x,y);     
        grafico.setTitle("População");
        
        macrofagos.setName("Macrofagos");
        patogenos.setName("Patogenos");
        neutrofilos.setName("Neutrofilos");
        linfocitos.setName("Linfocitos");
        
        grafico.getData().add(macrofagos);
        grafico.getData().add(patogenos);
        grafico.getData().add(neutrofilos);
        grafico.getData().add(linfocitos);
        
        NumberAxis x2 = new NumberAxis();
        NumberAxis y2 = new NumberAxis();
        
        LineChart<Number,Number> grafico2 = new LineChart<>(x2,y2);     
        grafico2.setTitle("Tempo médio detecção");
        
        temporizacoes.setName("Tempo médio");
        grafico2.getData().add(temporizacoes);
        
        container.getChildren().add(grafico);
        container.getChildren().add(grafico2);
        
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
            new KeyFrame(Duration.seconds(2),(evt) -> {
                atualizaGraficos();
            }
        ));
        timeline.playFromStart();
    }
    
    private void atualizaGraficos(){        
        int tick = macrofagos.getData().size();
        macrofagos.getData().add(new XYChart.Data<>(tick,sistema.getMacrofagos().size()));
        patogenos.getData().add(new XYChart.Data<>(tick,sistema.getPatogenos().size()));
        neutrofilos.getData().add(new XYChart.Data<>(tick,sistema.getNeutrofilos().size()));
        linfocitos.getData().add(new XYChart.Data<>(tick,sistema.getLinfocitos().size()));     
        temporizacoes.getData().add(new XYChart.Data<>(tick,sistema.getTemporizacao()));
    }
    
}