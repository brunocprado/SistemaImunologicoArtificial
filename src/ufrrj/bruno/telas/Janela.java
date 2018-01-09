package ufrrj.bruno.telas;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXDrawer;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
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
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import ufrrj.bruno.ia.telas.Sobre;
import javafx.stage.Stage;
import ufrrj.bruno.Main;
import static ufrrj.bruno.Main.timeline;
import ufrrj.bruno.SistemaImunologico;
import ufrrj.bruno.log.Virus;
import ufrrj.bruno.renderizacao.GraficoAvancado;

public class Janela implements Initializable {
    
//    @FXML private Pane painelTeste;
    @FXML private Pane overlay;  
    @FXML private Canvas quimica;
    @FXML private Pane celulas;  
    
    @FXML private MenuBar menu;
    @FXML private MenuItem menuSalvar;
    @FXML private MenuItem menuCarregar;
    @FXML private Menu menuPausar;
    @FXML private Menu menuOpcoes;
    @FXML private Menu menuEstatisticas;
    @FXML private MenuItem menuEstatisticasSistema;
    @FXML private Menu menuSobre;
    
    @FXML private JFXDrawer opcoes;
     
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
            stage.getProperties().put("estatisticas", estatisticas);
            stage.getProperties().put("menu", menuEstatisticas);
            stage.show();
        } catch(IOException e){}
    }
    
    GraficoAvancado grafico;
    public void handlerTeclado(KeyCode tecla){
        switch (tecla) {
            case A:     grafico.setZoom(grafico.getZoom() + 0.2);  break;
            case S:     grafico.setZoom(grafico.getZoom() - 0.2);  break;
            case RIGHT: grafico.moveX(grafico.getX() + 10);  break;
            case LEFT:  grafico.moveX(grafico.getX() - 10);  break;
            case UP:    grafico.moveY(grafico.getY() - 10);  break;
            case DOWN:  grafico.moveY(grafico.getY() + 10);  break;
            case L: grafico.moveX(grafico.getX() + 10);  break;
            case J:  grafico.moveX(grafico.getX() - 10);  break;
            case I:    grafico.moveY(grafico.getY() - 10);  break;
            case K:  grafico.moveY(grafico.getY() + 10);  break;
        }
    }

    public void handlerMouse(){
        
    }

    public void mostraOpcoes(){
        opcoes.setVisible(true);
        overlay.setVisible(true);
        opcoes.open();
    }
    
    public void escondeOpcoes(){
        overlay.setVisible(false);
        opcoes.close();
        opcoes.setVisible(false);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {    
        grafico = GraficoAvancado.getInstancia();
        grafico.setPane(celulas);
        grafico.setQuimica(quimica);
                
        Estatisticas tmp = new Estatisticas();
        FileChooser janelaArq = new FileChooser();
        
        janelaArq.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        
        try { 
            AnchorPane box = FXMLLoader.load(getClass().getResource("Opcoes.fxml"));
            opcoes.setSidePane(box);
        } catch (IOException ex) {
            Logger.getLogger(Janela.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
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
            if(opcoes.isShown()){
                escondeOpcoes();
            } else{
                mostraOpcoes();
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
       
        
    }    
}
