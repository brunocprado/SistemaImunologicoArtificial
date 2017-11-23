package ufrrj.bruno.telas;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import ufrrj.bruno.SistemaImunologico;
import ufrrj.bruno.celulas.Patogeno;
import ufrrj.bruno.log.Virus;

public class VisualizaVirus extends Stage{
    
    private final Virus virus;
    
    public VisualizaVirus(Virus virus){
        this.virus = virus;
        
        setTitle("Estatísticas : " + virus.getIdentificador());
        
        VBox container = new VBox();
        container.setPadding(new Insets(10,10,10,10));
        container.setSpacing(10);
        container.setAlignment(Pos.CENTER_RIGHT);
//        setScene(new Scene(container,600,400));
        
        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();
        
        x.setLabel("Tempo");
        y.setLabel("Quantidade");
        
        LineChart<Number,Number> grafico = new LineChart<>(x,y);     
        grafico.setTitle(virus.getIdentificador());
        XYChart.Series<Number,Number> series = new XYChart.Series();
        series.setName("Antigenos");
        XYChart.Series<Number,Number> anticorpos = new XYChart.Series();
        anticorpos.setName("Anticorpos");
        grafico.getData().add(series);
        grafico.getData().add(anticorpos);
        //container.getChildren().add(grafico);
        
        setScene(new Scene(grafico,600,400));
        getScene().getStylesheets().add(getClass().getResource("chart.css").toExternalForm());
        
//        Button btn = new Button("Nova entrada");
//        btn.setOnAction((evt)->{
//            SistemaImunologico.getInstancia().getPatogenos().add(new Patogeno(virus));
//        });
//        container.getChildren().add(btn);
        
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
            new KeyFrame(Duration.millis(1000),(rvt) -> {
                XYChart.Data<Number, Number> d = new XYChart.Data<>(series.getData().size(),virus.getQuantidade());
                series.getData().add(d);
                
                XYChart.Data<Number, Number> tmp = new XYChart.Data<>(anticorpos.getData().size(),virus.anticorpos.size());
                anticorpos.getData().add(tmp);
                
                d.getNode().setCursor(javafx.scene.Cursor.HAND);          
                
                Tooltip tp = new Tooltip();
                tp.setGraphic(new EstatisticaSimulacao(series.getData().size()));
//                tp.setShowDelay(javafx.util.Duration.ZERO);   
//                tp.setHideDelay(javafx.util.Duration.ONE);
                Tooltip.install(d.getNode(), tp);

                d.getNode().setOnMouseEntered(event -> d.getNode().getStyleClass().add("onHover"));
                d.getNode().setOnMouseExited(event -> d.getNode().getStyleClass().remove("onHover"));

                if(virus.getQuantidade() <= 0) timeline.stop();
            }
        ));
        timeline.playFromStart();
    }
    
}