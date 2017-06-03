package ufrrj.bruno.telas;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ufrrj.bruno.Main;
import ufrrj.bruno.SistemaImunologico;
import ufrrj.bruno.celulas.Patogeno;
import ufrrj.bruno.log.Virus;

public class NovoVirus implements Initializable {

    private final SistemaImunologico sistema = SistemaImunologico.getInstancia();
    
    @FXML private TextField txtNome;
    @FXML private TextField txtNLados;
    @FXML private ColorPicker cor;
    
    @FXML
    public void insere(){
        if(txtNome.getText().equals("")){
            Alert dialogoErro = new Alert(Alert.AlertType.ERROR);
            dialogoErro.setTitle("Erro");
            dialogoErro.setHeaderText("Nenhum Identificador");
            dialogoErro.show();
            return;
        }
        int nLados = 3;
        if(!txtNLados.getText().equals("")){ nLados = Integer.parseInt(txtNLados.getText()); }
        Virus virus = new Virus(cor.getValue(),nLados,txtNome.getText());
        sistema.getVirus().add(virus);

        Map<Virus,VisualizaVirus> estatisticas = (HashMap) cor.getScene().getWindow().getProperties().get("estatisticas");
        Menu mEstatisticas = (Menu) cor.getScene().getWindow().getProperties().get("menu");
        
        VisualizaVirus visualizaVirus = new VisualizaVirus(virus);   
        estatisticas.put(virus, visualizaVirus);
        
        visualizaVirus.show();
           
        MenuItem a = new MenuItem(txtNome.getText());
        a.setOnAction(((event) -> {
            VisualizaVirus t = estatisticas.get(virus);
            if(t.isShowing()) t.hide(); else t.show();
        }));
        mEstatisticas.getItems().add(a);
        
        for(int i=0;i<10;i++){
            sistema.adicionaCelula(new Patogeno(virus));
        }
        
        if(Main.timeline.getStatus() == Timeline.Status.STOPPED) Main.timeline.playFromStart();
        Stage stage = (Stage) txtNome.getScene().getWindow();
        stage.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {       
        cor.setValue(new Color(0d, 0.8, 0d, 1d));
    }    
    
}