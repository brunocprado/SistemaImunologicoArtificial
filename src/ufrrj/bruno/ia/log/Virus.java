package ufrrj.bruno.ia.log;

import java.awt.Color;
import java.util.Random;

/**
 * Classe para fins de Histórico e estatisticas sobre determinada classe de Patogenos.
 * @author Bruno Prado
 */
public class Virus {
    
    private int quantidade = 0;
//    private final SistemaImunologico sistema;
    private final int identificador;
    private Color cor;
    private int nLados;
    
    public Virus(){
        Random r = new Random();
        nLados = r.nextInt(10) + 3;
        cor = new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255));
        identificador = Integer.MAX_VALUE - new Random().nextInt(Integer.MAX_VALUE/10);
    }
    
    public Virus(Color cor,int nLados,int identificador){
        this.cor = cor;
        this.nLados = nLados;
        this.identificador = identificador;
    }
 
    public int getIdentificador() {
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

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
}
