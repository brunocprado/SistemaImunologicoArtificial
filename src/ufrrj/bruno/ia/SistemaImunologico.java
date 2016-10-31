package ufrrj.bruno.ia;

import ufrrj.bruno.ia.quimica.CamadaSobreposta;
import java.util.ArrayList;
import java.util.Random;
import ufrrj.bruno.ia.Telas.Log;
import ufrrj.bruno.ia.celulas.Celula;
import ufrrj.bruno.ia.celulas.Linfocito;
import ufrrj.bruno.ia.celulas.Macrofago;
import ufrrj.bruno.ia.celulas.Neutrofilo;

public class SistemaImunologico implements Runnable{
    //======| Variaveis |=======//
    private final int nInicial;
    private final ArrayList<Celula> celulas = new ArrayList<>();
    private final CamadaSobreposta camada;
    private final Log log = new Log();
    //======|  RUNTIME  |======//
    private final long inicio = System.currentTimeMillis();
    private boolean mostraCamada = false;
    public boolean pausada = false;
    private Thread t;
    private int biblioteca;
    private boolean debug = false;
    
    public SistemaImunologico(){
        camada = new CamadaSobreposta(this);
        nInicial = new Random().nextInt(Parametros.TAM_MEDIO_SUPERIOR - Parametros.TAM_MEDIO_INFERIOR) + Parametros.TAM_MEDIO_INFERIOR;
        geraPrimeiraGeracao();    
        iniciaThread();
    }
    
    public SistemaImunologico(int nInicial){
        camada = new CamadaSobreposta(this);
        this.nInicial = nInicial;
        geraPrimeiraGeracao();    
        iniciaThread();
    }
    
    private void iniciaThread(){
        t = new Thread(this,"Sistema Imunologico - IA");
        t.start();
    }
    
    private void geraPrimeiraGeracao(){
        imprime("Gerando sistema com " + nInicial * 10 + " leucócitos por microlitro de sangue");
        int i;
//        for(i=0;i<10;i++){
//            celulas.add(new Comum(this));
//        }
        for(i=0;i<(nInicial*Parametros.NEUTROFILOS);i++){
            celulas.add(new Neutrofilo(this));
        }
        for(i=0;i<(nInicial*Parametros.MACROFAGOS);i++){
            celulas.add(new Macrofago(this));
        }
        for(i=0;i<(nInicial*Parametros.LINFOCITOS);i++){
            celulas.add(new Linfocito(this));
        }
        //celulas.add(new Patogeno(this));
    }
    
    public void pausa(int tempo){
        try {
            Thread.sleep(tempo);
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
        System.out.println(texto);
        log.imprime(texto);
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
    
    @Override
    public void run() {     
        while(true){
            while(pausada){
                pausa(5);
            }
            int tamanho = celulas.size();
            for(int i = 0;i < tamanho; i++){
                celulas.get(i).loop();
            }
            pausa(20);
        }     
    }

    public long getInicio() {
        return inicio;
    }

    public CamadaSobreposta getCamada() {
        return camada;
    }
    
    public boolean getMostraCamada() {
        return mostraCamada;
    }

    public void setMostraCamada(boolean mostraCamada) {
        this.mostraCamada = mostraCamada;
    }
    
    @Override
    public String toString() {
        return "SistemaImunologico{" + "nInicial=" + nInicial + ", celulas=" + celulas + ", pausada=" + pausada + '}';
    }
    
}