package ufrrj.bruno.ia;

import java.util.ArrayList;
import ufrrj.bruno.ia.celulas.Celula;
import ufrrj.bruno.ia.celulas.Comum;
import ufrrj.bruno.ia.celulas.Invasor;
import ufrrj.bruno.ia.celulas.Linfocito;
import ufrrj.bruno.ia.celulas.Macrofago;
import ufrrj.bruno.ia.celulas.Neutrofilo;

public class SistemaImunologico implements Runnable{
    private int nInicial = Parametros.TAM_INICIAL;
    private ArrayList<Celula> celulas = new ArrayList<Celula>();
    private Thread t;
    
    public SistemaImunologico(){
        geraPrimeiraGeracao();     
    }
    
    private void geraPrimeiraGeracao(){
        int i;
        for(i=0;i<10;i++){
            celulas.add(new Comum(this));
        }
        for(i=0;i<(nInicial*Parametros.neutrofilos);i++){
            celulas.add(new Neutrofilo(this));
        }
        for(i=0;i<(nInicial*Parametros.macrofagos);i++){
            celulas.add(new Macrofago(this));
        }
        for(i=0;i<(nInicial*Parametros.linfocitos);i++){
            celulas.add(new Linfocito(this));
        }
        celulas.add(new Invasor(this));
    }
    
    public ArrayList<Celula> getCelulas() {
        return celulas;
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
    
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}