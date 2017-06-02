package ufrrj.bruno.telas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.swing.JMenu;
import ufrrj.bruno.Main;
import ufrrj.bruno.SistemaImunologico;
import ufrrj.bruno.celulas.Patogeno;
import ufrrj.bruno.log.Virus;

public class NovoVirusController implements Initializable {

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
            dialogoErro.showAndWait();
            return;
        }
        int nLados = 3;
        if(!txtNLados.getText().equals("")){ nLados = Integer.parseInt(txtNLados.getText()); }
        Virus virus = new Virus(cor.getValue(),nLados,txtNome.getText());
        sistema.getVirus().add(virus);
//        janela.novoVirus(virus);
//        janela.getJMenuBar().add(new JMenu("|"));
        JMenu menu = new JMenu(txtNome.getText());
//        menu.addMouseListener(new java.awt.event.MouseAdapter() {
//            @Override
//            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                janela.visualizaVirus(virus);
//            }
//        });
//        janela.getJMenuBar().add(menu);
//        janela.getJMenuBar().updateUI();
//        if(radioInicio.isSelected()){
        for(int i=0;i<10;i++){
            sistema.adicionaCelula(new Patogeno(virus));
        }
        Main.timeline.playFromStart();
        Stage stage = (Stage) txtNome.getScene().getWindow();
        stage.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cor.setValue(new Color(0d, 0.8, 0d, 1d));
    }    
    
}