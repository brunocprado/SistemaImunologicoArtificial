/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufrrj.bruno.telas;

import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import ufrrj.bruno.celulas.Celula;
import ufrrj.bruno.celulas.Macrofago;

/**
 *
 * @author bruno
 */
public class InfoCelula extends Pane{
    
    private final Celula cel;
    
    public InfoCelula(Celula cel){
        this.cel = cel;
    
        VBox container = new VBox(20);
        HBox divisor = new HBox(20);     
        getChildren().add(container);
        
        Text nome = new Text(cel.getTipo().toString());
        nome.setFont(new Font(18d));
        nome.setFill(Color.WHITESMOKE);
        nome.setTextAlignment(TextAlignment.CENTER);
        container.getChildren().add(nome);
        container.getChildren().add(divisor);
        
        ImageView a = new ImageView(cel.getImage());
        a.setFitHeight(50);
        a.setFitWidth(50);
        divisor.getChildren().add(a);
        
        VBox conteudo = new VBox(5);
        divisor.getChildren().add(conteudo);
        
        conteudo.getChildren().add(geraTxt("ID : " + cel.getID()));
        
        if(cel.getTipo() == Celula.TIPO_CELULA.MACROFAGO){
            Macrofago tmp = (Macrofago) cel;
            conteudo.getChildren().add(geraTxt("Estado : " + tmp.getEstado()));
            if(tmp.getAlvo() != null) conteudo.getChildren().add(geraTxt("Alvo : " + tmp.getAlvo().getID()));
        }
        
        conteudo.getChildren().add(geraTxt("Posicao : " + Math.floor(cel.getX()*100) / 100  + " x " + Math.floor(cel.getY()*100) / 100));
        
        
        
    }
    
    Font fonte = new Font(12d);
    private Text geraTxt(String txt){
        Text tmp = new Text(txt);
        tmp.setFont(fonte);
        tmp.setFill(Color.WHITE);
        
        return tmp;
    }
    
}
