package ufrrj.bruno.log;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import javafx.scene.paint.Color;
import ufrrj.bruno.celulas.Anticorpo;
import ufrrj.bruno.celulas.CelulaB;

/**
 * Classe para fins de Histórico e estatisticas sobre determinada classe de Patogenos.
 * @author Bruno Prado
 */
public class Virus {
    
    private long INICIO = System.currentTimeMillis();
    
    private int quantidade = 0;
    private final String identificador;
    private final int epitopo;
    @JsonIgnore private Color cor;
    private final int nLados;
    
    public ConcurrentLinkedQueue<Anticorpo> anticorpos = new ConcurrentLinkedQueue<>();
    
    public Virus(){ 
        Random r = new Random();
        nLados = r.nextInt(10) + 3;
        epitopo = r.nextInt(Integer.MAX_VALUE);
        identificador = "Teste";
    }
    
    public Virus(Color cor,int nLados,String identificador){
        this.cor = cor;
        this.nLados = nLados;
        this.identificador = identificador;
        epitopo = new Random().nextInt(Integer.MAX_VALUE);
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

    public int getEpitopo() {
        return epitopo;
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