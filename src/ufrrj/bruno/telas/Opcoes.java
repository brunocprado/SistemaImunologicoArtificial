package ufrrj.bruno.telas;

import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import ufrrj.bruno.SistemaImunologico;
import static ufrrj.bruno.celulas.Celula.TIPO_CELULA.*;

public class Opcoes implements Initializable {

    @FXML private JFXToggleButton radioQuimica;
    @FXML private JFXToggleButton radioDebug;
    @FXML private JFXToggleButton radioMacrofago;
    @FXML private JFXToggleButton radioLinfocito;
    @FXML private JFXToggleButton radioNeutrofilo;
    @FXML private JFXToggleButton radioPatogeno;
    @FXML private JFXSlider sliderVelocidade;

    private final SistemaImunologico sistema = SistemaImunologico.getInstancia();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        sliderVelocidade.setSnapToTicks(true);
        radioQuimica.setOnAction((evt) -> {
            sistema.setMostraCamada(radioQuimica.isSelected());
        });
        
        radioDebug.setOnAction((evt) -> {
            sistema.setDebug(radioDebug.isSelected());
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

