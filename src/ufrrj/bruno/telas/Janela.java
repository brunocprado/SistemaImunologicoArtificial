package ufrrj.bruno.telas;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDrawer;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import ufrrj.bruno.ia.telas.Sobre;
import javafx.stage.Stage;
import ufrrj.bruno.Main;
import static ufrrj.bruno.Main.timeline;
import ufrrj.bruno.SistemaImunologico;
import static ufrrj.bruno.celulas.Celula.TIPO_CELULA.*;
import ufrrj.bruno.log.Virus;
import ufrrj.bruno.renderizacao.Grafico2D;

public class Janela implements Initializable {
    
    @FXML private Canvas canvas;
    @FXML private StackPane painelTeste;
    @FXML private VBox opcoes;  
    @FXML private BorderPane painel;  
    @FXML private MenuItem menuSalvar;
    @FXML private MenuItem menuCarregar;
    @FXML private Menu menuPausar;
    @FXML private Menu menuOpcoes;
    @FXML private Menu menuEstatisticas;
    @FXML private MenuItem menuEstatisticasSistema;
    @FXML private Menu menuSobre;
    
    // OPCOES //
    @FXML private RadioButton radioQuimica;
    @FXML private RadioButton radioMacrofago;
    @FXML private RadioButton radioLinfocito;
    @FXML private RadioButton radioNeutrofilo;
    @FXML private RadioButton radioPatogeno;
    
    @FXML private Slider sliderVelocidade;
     
    private SistemaImunologico sistema = SistemaImunologico.getInstancia();
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
            stage.getProperties().put("estatisticas", estatisticas);
            stage.getProperties().put("menu", menuEstatisticas);
            stage.show();
        } catch(IOException e){}
    }
    
    Grafico2D grafico;
    public void handlerTeclado(KeyCode tecla){
        switch (tecla) {
            case A: grafico.setZoom(grafico.getZoom() + 0.2);  break;
            case S: grafico.setZoom(grafico.getZoom() - 0.2);  break;
            case RIGHT: grafico.moveX(grafico.getX() + 20);  break;
            case LEFT: grafico.moveX(grafico.getX() - 20);  break;
            case UP: grafico.moveY(grafico.getY() - 20);  break;
            case DOWN: grafico.moveY(grafico.getY() + 20);  break;
        }
    }
//    public void teste(Scene scene){
//        PannableCanvas canvass = new PannableCanvas();
//        painel.setCenter(canvass);
//        SceneGestures sceneGestures = new SceneGestures(canvass);
//        scene.addEventFilter( MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
//        scene.addEventFilter( MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
//        scene.addEventFilter( ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());
//        
//        canvass.getChildren().add(canvas);
//        
//        final GraphicsContext gc = canvas.getGraphicsContext2D();
//
//        grafico = new Grafico2D(gc);
//        grafico.iniciaRenderizacao();
//    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {   
        sliderVelocidade.setSnapToTicks(true);
        painel.setRight(null);

        canvas.widthProperty().bind(painelTeste.widthProperty());
        canvas.heightProperty().bind(painelTeste.heightProperty());
        
//        JFXDialog dialog = new JFXDialog();
//dialog.setContent(new Label("Content"));
//dialog.show(painelTeste);
        final GraphicsContext gc = canvas.getGraphicsContext2D();

        grafico = new Grafico2D(gc);
        grafico.iniciaRenderizacao();
        
        Estatisticas tmp = new Estatisticas();
        FileChooser janelaArq = new FileChooser();
        
        janelaArq.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("JSON", "*.json")
        );
        
        menuSalvar.setOnAction((event) -> {
            File file = janelaArq.showSaveDialog(null);
            if (file != null) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    mapper.writeValue(file, sistema);
                } catch (IOException ex) {
                    Logger.getLogger(Janela.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        menuCarregar.setOnAction((event) -> {
            File file = janelaArq.showOpenDialog(null);
            if (file != null) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    SistemaImunologico sis = mapper.readValue(file, SistemaImunologico.class);     
                    Console.getInstancia().apaga();
                    sistema.carrega(sis);
                } catch (IOException ex) {
                    Logger.getLogger(Janela.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
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
        
        menuEstatisticasSistema.setOnAction((event) -> {
            if(tmp.isShowing()){
                tmp.hide();
            } else {
                tmp.show();
            }
        });
        
        Label lblSobre = new Label("Sobre");
        lblSobre.setOnMouseClicked((MouseEvent evt) -> {
            Sobre sobre = new ufrrj.bruno.ia.telas.Sobre();
            sobre.setLocationRelativeTo(null);
            sobre.setAlwaysOnTop(true);
            sobre.setVisible(true);     
        });
        menuSobre.setGraphic(lblSobre);
        
        //OPCOES
        
        radioQuimica.setOnAction((evt) -> {
            sistema.setMostraCamada(radioQuimica.isSelected());
        });
        
        radioMacrofago.setOnAction((evt) -> {
            sistema.exibir.put(MACROFAGO,radioMacrofago.isSelected());
        });
        
        radioLinfocito.setOnAction((evt) -> {
            sistema.exibir.put(LINFOCITO,radioLinfocito.isSelected());
        });
        
        radioNeutrofilo.setOnAction((evt) -> {
            sistema.exibir.put(NEUTROFILO,radioNeutrofilo.isSelected());
        });
        
        radioPatogeno.setOnAction((evt) -> {
            sistema.exibir.put(PATOGENO,radioPatogeno.isSelected());
        });    
        
        sliderVelocidade.valueProperty().addListener((observable, oldValue, newValue) -> {
            sistema.setVelocidade((int) sliderVelocidade.getValue());
        });
    }    
}
