package ufrrj.bruno.ia;

import java.util.ArrayList;
import ufrrj.bruno.ia.celulas.Celula;
import ufrrj.bruno.ia.celulas.Comum;
import ufrrj.bruno.ia.celulas.Patogeno;
import ufrrj.bruno.ia.celulas.Linfocito;
import ufrrj.bruno.ia.celulas.Macrofago;
import ufrrj.bruno.ia.celulas.Neutrofilo;

public class SistemaImunologico implements Runnable{
    private int nInicial = Parametros.TAM_INICIAL;
    private ArrayList<Celula> celulas = new ArrayList<Celula>();
    public boolean pausada = false;
    private Thread t;
    
    public SistemaImunologico(){
        geraPrimeiraGeracao();    
        t = new Thread(this,"Sistema Imunologico - IA");
        t.start();
    }
    
    private void geraPrimeiraGeracao(){
        int i;
        for(i=0;i<10;i++){
            celulas.add(new Comum(this));
        }
        for(i=0;i<(nInicial*Parametros.NEUTROFILOS);i++){
            celulas.add(new Neutrofilo(this));
        }
        for(i=0;i<(nInicial*Parametros.MACROFAGOS);i++){
            celulas.add(new Macrofago(this));
        }
        for(i=0;i<(nInicial*Parametros.LINFOCITOS);i++){
            celulas.add(new Linfocito(this));
        }
        celulas.add(new Patogeno(this));
    }
    
    public void pausa(int tempo){
        try {
            t.sleep(tempo);
        } catch (InterruptedException ex) {
            System.out.println("Erro ao pausar a Thread principal");
        }
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
        while(true){
            while(pausada){
                pausa(5);
            }
            for(Celula cel : celulas){
               cel.loop();                
            }
            pausa(20);
        }     
    }
    
}
