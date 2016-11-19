package ufrrj.bruno.ia;

import ufrrj.bruno.ia.quimica.CamadaSobreposta;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import ufrrj.bruno.ia.log.Log;
import ufrrj.bruno.ia.celulas.Celula;
import ufrrj.bruno.ia.celulas.Linfocito;
import ufrrj.bruno.ia.celulas.Macrofago;
import ufrrj.bruno.ia.celulas.Neutrofilo;
import ufrrj.bruno.ia.log.Virus;

/**
 * Classe principal <br>
 * 
 * @author Bruno Prado
 * @param nInicial  Numero de leucocitos a serem gerados
 * @param camada  Camada sobreposta com os compostos quimicos
 * @param celulas ArrayList de todas as celulas exibidas
 */
public class SistemaImunologico implements Runnable{
    //======| Variaveis |=======//
    private final int nInicial;
    private final ConcurrentLinkedQueue<Celula> celulas = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Virus> virus = new ConcurrentLinkedQueue<>();
    private final CamadaSobreposta camada;
    private final Log log = new Log();
    //======|  RUNTIME  |======//
    private final long inicio = System.currentTimeMillis();
    private boolean mostraCamada = false;
    public boolean pausada = false;
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
        Thread t = new Thread(this,"Sistema Imunologico - IA");
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
    
    public ConcurrentLinkedQueue<Celula> getCelulas() {
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
            
            Iterator<Celula> i = celulas.iterator();
            while(i.hasNext()){
                i.next().loop();
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

    public ConcurrentLinkedQueue<Virus> getVirus() {
        return virus;
    }
    
    public void setMostraCamada(boolean mostraCamada) {
        this.mostraCamada = mostraCamada;
    }
    
    @Override
    public String toString() {
        return "SistemaImunologico{" + "nInicial=" + nInicial + ", celulas=" + celulas + ", pausada=" + pausada + '}';
    }
    
}