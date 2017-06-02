package ufrrj.bruno.telas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Novo extends Stage{

    private VBox root = new VBox(20);
    
    public Novo(){
        setTitle("Estatísticas");
        setScene(new Scene(root, 600, 400));
        criaTela();
        show();
    }
    
    private void criaTela(){
        root.setPadding(new Insets(20,20,20,20));
        root.getChildren().add(new Label("teste"));
        
//        LineChart
    }
    
}
