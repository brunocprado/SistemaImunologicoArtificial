package ufrrj.bruno;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * Sistema Imunológico Artificial <br>
 * 
 * @author Bruno Prado
 */
public class Main extends Application {
    
    private static long INICIO = System.currentTimeMillis();
    private int runtime = 0;
    StringProperty titulo = new SimpleStringProperty("Sistema Imunológico Artificial");

    public static Stage stage;
    public static Timeline timeline;
    
    @Override
    public void start(Stage stage) throws Exception {
        SistemaImunologico sistema = SistemaImunologico.getInstancia();
        sistema.geraPrimeiraGeracao();
        sistema.iniciaThread(); 
        
        Parent root = FXMLLoader.load(getClass().getResource("telas/Janela.fxml"));
        Main.stage = stage;
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Sistema Imunológico Artificial");
        stage.getIcons().add(new Image("img/icone.png"));
        stage.show();
                
        stage.setOnCloseRequest((WindowEvent t) -> {
            Platform.exit();
            System.exit(0);
        });
        
        stage.setMinWidth(1024);
        stage.setMinHeight(640);
            
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
            new KeyFrame(Duration.millis(200),(rvt) -> {
                runtime +=1;
                titulo.set("Sistema Imunológico Artificial  -  Tempo  executando: " + runtime/5 + " segundos");
            }
        ));

        stage.titleProperty().bind(titulo);
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
