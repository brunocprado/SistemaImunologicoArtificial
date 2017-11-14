package ufrrj.bruno.telas;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.IOException;
import java.util.Set;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import static javafx.scene.input.KeyCode.ENTER;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ufrrj.bruno.SistemaImunologico;

/**
 * FXML Controller class
 *
 * @author bruno
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Console {

    public final SistemaImunologico sistema = SistemaImunologico.getInstancia();
    
    private static final Console instancia = new Console();
    
    @FXML private ScrollPane scroll;
    @FXML private TextFlow txtConsole;
    @FXML private TextField txtComando;
    
    public Console(){
        
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Console.fxml"));
            fxmlLoader.setController(this);
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Console");
            stage.setScene(new Scene(root1));
            stage.setMinHeight(400);
            stage.setMinWidth(600);
            stage.setX(10);
            stage.setY(10);
            stage.show();
            stage.getIcons().add(new Image("img/terminal.png"));
            stage.setOnCloseRequest((WindowEvent t) -> {
                Platform.exit();
                System.exit(0);
            });
        } catch(IOException e){}
        
        txtComando.setOnKeyReleased(e -> {
            if(e.getCode() == ENTER && !txtComando.getText().trim().equals("")){;
                executaComando(txtComando.getText());;
                txtComando.setText("");
            } else {
                int qt = 0;
                String ultimo = "";
                Set<String> chaves = SistemaImunologico.getInstancia().getParametros().keySet();
                for(String tmp : chaves){
                    if(tmp.toLowerCase().startsWith(txtComando.getText())){
                        qt++; ultimo = tmp;
                    }
                }
                if(qt == 1) { 
                    txtComando.setText(ultimo + " ");
                    txtComando.positionCaret(txtComando.getText().length());
                }       
            }
        });
        
//        btnComando.addActionListener(l -> {
//            if(comando.getText().trim().equals("")) return;
//            executaComando(comando.getText());
//            comando.setText("");
//            comando.requestFocus();
//        });

        
    }

    public static synchronized Console getInstancia(){
        return instancia;
    }
    
    public void executaComando(String comando){     
        String[] tmp = comando.split(" ");
        if(tmp.length < 2) return;
        SistemaImunologico.getInstancia().mudaParametro(tmp[0], Integer.parseInt(tmp[1]));
        imprime("[ " + tmp[0] + " ] alterado para " + tmp[1],Color.WHITE);
    }
    
    public void imprime(String t){
        Platform.runLater(() -> {
            txtConsole.getChildren().add(new Text(t));
            scroll.setVvalue(1.0);
        });
    }
    
    public void imprime(String texto,Color cor){
        Text tmp = new Text(texto + "\n");
        tmp.setFill(cor);
        Platform.runLater(() -> {
            txtConsole.getChildren().add(tmp);
            scroll.setVvalue(1.0); //scroll.getVmax()
        });
    }
    
    public void apaga(){
        txtConsole.getChildren().clear();
        scroll.setVvalue(1.0);
    }
    
}
