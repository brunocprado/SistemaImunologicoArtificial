package ufrrj.bruno.ia.log;

import java.awt.Color;
import java.util.Random;

/**
 * Classe para fins de Histórico e estatisticas sobre determinada classe de Patogenos.
 * @author Bruno Prado
 */
public class Virus {
    
    private int quantidade = 0;
    private final String identificador;
    private final Color cor;
    private final int nLados;
    
    public Virus(){ 
        Random r = new Random();
        nLados = r.nextInt(10) + 3;
        cor = new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255));
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
    
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
}
