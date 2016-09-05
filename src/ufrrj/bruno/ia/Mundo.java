package ufrrj.bruno.ia;

import ufrrj.bruno.ia.Telas.Tela;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import ufrrj.bruno.ia.celulas.*;

public class Mundo {
    int tamX,tamY;
    private ArrayList<Celula> celulas = new ArrayList<Celula>();
    private int nInicial = 1000;
    
    //====| Proporcao real |====//
    private double macrofagos = 0.053;
    private double neutrofilos = 0.65;
    private double linfocitos = 0.26;
    //=========================//
    
    public Mundo(){ 
        geraPrimeiraGeracao();          
    }

    private void geraPrimeiraGeracao(){
        int i;
        for(i=0;i<10;i++){
            celulas.add(new Comum(this));
        }
        for(i=0;i<(nInicial*neutrofilos);i++){
            celulas.add(new Neutrofilo(this));
        }
        for(i=0;i<(nInicial*macrofagos);i++){
            celulas.add(new Macrofago(this));
        }
        for(i=0;i<(nInicial*linfocitos);i++){
            celulas.add(new Linfocito(this));
        }
        celulas.add(new Invasor(this));
    }
    
    public ArrayList<Celula> getCelulas() {
        return celulas;
    }
     
    public void redimensiona(int x,int y){
        tamX = x;
        tamY = y;
    }
    
    public void adicionaCelula(Celula celula){
        this.celulas.add(celula);
    }
    
    public void eliminaCelula(Celula celula){
        this.celulas.remove(celula);
    }
    
    public void imprime(String texto){
        System.out.print(texto);
    }

}
