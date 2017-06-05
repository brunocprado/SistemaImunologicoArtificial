package ufrrj.bruno.telas;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ufrrj.bruno.ia.telas.Sobre;
import javafx.stage.Stage;
import ufrrj.bruno.Main;
import static ufrrj.bruno.Main.timeline;
import ufrrj.bruno.SistemaImunologico;
import ufrrj.bruno.log.Virus;
import ufrrj.bruno.renderizacao.Grafico2D;

public class Janela implements Initializable {
    
    @FXML private Canvas canvas;
    @FXML private Pane painelTeste;
    @FXML private VBox opcoes;  
    @FXML private BorderPane painel;  
    @FXML private Menu menuPausar;
    @FXML private Menu menuOpcoes;
    @FXML private Menu menuEstatisticas;
    @FXML private MenuItem menuEstatisticasSistema;
    @FXML private Menu menuSobre;
     
    private final SistemaImunologico sistema = SistemaImunologico.getInstancia();
    private final Map<Virus,VisualizaVirus> estatisticas = new HashMap<>();
    
    @FXML
    public void novoPatogeno(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NovoVirus.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Novo Patogeno");
            stage.setScene(new Scene(root1));  
            stage.setResizable(false);
            stage.setWidth(442);
//            stage.setAlwaysOnTop(true);
            stage.getProperties().put("estatisticas", estatisticas);
            stage.getProperties().put("menu", menuEstatisticas);
            stage.show();
        } catch(IOException e){}
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {   
        painel.setRight(null);

        canvas.widthProperty().bind(painelTeste.widthProperty());
        canvas.heightProperty().bind(painelTeste.heightProperty());
        
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Grafico2D grafico = new Grafico2D(gc);
        grafico.iniciaRenderizacao();
                
        Estatisticas tmp = new Estatisticas();
          
        Label lblPausar = new Label("Pausar");
        lblPausar.setOnMouseClicked((MouseEvent evt) -> {
            if(sistema.isPausado()) {
                sistema.iniciaThread();
                sistema.getCamada().iniciaThread();
                if(timeline.getStatus() != Animation.Status.STOPPED) Main.timeline.play();
                lblPausar.setText("Pausar");
            } else {
                sistema.pausaThread();
                sistema.getCamada().pausaThread();
                Main.timeline.pause();
                lblPausar.setText("Resumir");
            }
        });
        menuPausar.setGraphic(lblPausar);
        
        Label lblOpcoes = new Label("Opções");
        lblOpcoes.setOnMouseClicked((MouseEvent evt) -> {
            if (painel.getRight() == null){
                painel.setRight(opcoes);
            } else {
                painel.setRight(null);
            }
        });
        menuOpcoes.setGraphic(lblOpcoes);
        
        menuEstatisticasSistema.setOnAction(((event) -> {
            if(tmp.isShowing()){
                tmp.hide();
            } else {
                tmp.show();
            }
        }));
        
        Label lblSobre = new Label("Sobre");
        lblSobre.setOnMouseClicked((MouseEvent evt) -> {
            Sobre sobre = new ufrrj.bruno.ia.telas.Sobre();
            sobre.setLocationRelativeTo(null);
            sobre.setAlwaysOnTop(true);
            sobre.setVisible(true);     
        });
        menuSobre.setGraphic(lblSobre);
        
    }    
    
}