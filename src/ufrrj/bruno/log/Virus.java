package ufrrj.bruno.log;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Random;
import javafx.scene.paint.Color;

/**
 * Classe para fins de Histórico e estatisticas sobre determinada classe de Patogenos.
 * @author Bruno Prado
 */
public class Virus {
    
    private long INICIO = System.currentTimeMillis();
    
    private int quantidade = 0;
    private final String identificador;
    @JsonIgnore
    private  Color cor;
    private final int nLados;
    
    public Virus(){ 
        Random r = new Random();
        nLados = r.nextInt(10) + 3;
//        cor = new Color(r.nextDouble(),r.nextDouble(),r.nextDouble());
        //nome = "Só pra teste";
        identificador = "Teste";
    }
    
    public Virus(Color cor,int nLados,String identificador){
        this.cor = cor;
        this.nLados = nLados;
        this.identificador = identificador;
    }
 
    public String getIdentificador() {
        return identificador;
    }

    public Color getCor() {
        return cor;
    }

    public int getnLados() {
        return nLados;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void add(){
        this.quantidade++;
    }

    public long getINICIO() {
        return INICIO;
    }

    public void setINICIO(long INICIO) {
        this.INICIO = INICIO;
    }
    
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
}