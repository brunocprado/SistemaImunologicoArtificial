package ufrrj.bruno;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import ufrrj.bruno.quimica.CamadaSobreposta;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ufrrj.bruno.log.Log;
import ufrrj.bruno.celulas.Celula;
import ufrrj.bruno.celulas.Celula.TIPO_CELULA;
import static ufrrj.bruno.celulas.Celula.TIPO_CELULA.*;
import ufrrj.bruno.celulas.Linfocito;
import ufrrj.bruno.celulas.Macrofago;
import ufrrj.bruno.celulas.Neutrofilo;
import ufrrj.bruno.celulas.Patogeno;
import ufrrj.bruno.log.Virus;

/**
 * Classe principal <br>
 * 
 * @author Bruno Prado
 * @param nInicial  Numero de leucocitos a serem gerados
 * @param camada  Camada sobreposta com os compostos quimicos
 * @param celulas ArrayList de todas as celulas exibidas
 */
public class SistemaImunologico{
    private static final SistemaImunologico instancia = new SistemaImunologico();
    //======| Variaveis |=======//
    private final int nInicial;
    private ConcurrentLinkedQueue<Celula> celulas = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<Virus> virus = new ConcurrentLinkedQueue<>();
    private final CamadaSobreposta camada;
    private final Log log = new Log();
    private final Map<String,Integer> parametros = new HashMap<>();
    //======|  RUNTIME  |======//
    private double velocidade = 1.0;
    private static long inicio = System.currentTimeMillis();
    private boolean mostraCamada = true;
    private boolean pausado = false;
    private boolean debug = true;
    private int tempoMedio = 0;
    private int qtTempo = 0;
    //======| DISPLAY |======//
    public Map<TIPO_CELULA,Boolean> exibir = new HashMap<>();   

    private SistemaImunologico(){
        carregaParametros();
        
        nInicial = new Random().nextInt(parametros.get("TAM_MEDIO_SUPERIOR") - parametros.get("TAM_MEDIO_INFERIOR")) + parametros.get("TAM_MEDIO_INFERIOR");      
        exibir.put(MACROFAGO, true);
        exibir.put(LINFOCITO, true);
        exibir.put(NEUTROFILO, true);
        exibir.put(PATOGENO, true);   
        camada = new CamadaSobreposta(this);
    }
    
    public static synchronized SistemaImunologico getInstancia(){
        return instancia;
    }
    
    void geraPrimeiraGeracao(){
        imprime("Gerando sistema com " + nInicial * 10 + " leucócitos por microlitro de sangue");
        int i;
//        for(i=0;i<10;i++){
//            celulas.add(new Comum(this));
////        }
        for(i=0;i<(nInicial*parametros.get("NEUTROFILOS"))/1000;i++){
            celulas.add(new Neutrofilo());
        }
        for(i=0;i<(nInicial*parametros.get("MACROFAGOS"))/1000;i++){
            celulas.add(new Macrofago());
        }
        for(i=0;i<(nInicial*parametros.get("LINFOCITOS"))/1000;i++){
            celulas.add(new Linfocito());
        }
    }
    
    public void setVelocidade(int velo){
        double vel = velo/25;
        velocidade = (velo == 0) ? 2 : 2/vel;
        pausaThread(); iniciaThread();
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
    
    public static long getInicio() {
        return inicio;
    }

    public CamadaSobreposta getCamada() {
        return camada;
    }

    public double getVelocidade() {
        return velocidade;
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
    
    private void carregaParametros(){

        imprime("Carregando Parametros");
        
        try {
            File arquivo = new File("res/parametros.xml");
            DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document documento = parser.parse(arquivo);                  
            NodeList propriedades = documento.getDocumentElement().getChildNodes();
           
            Node tmp;  
            for (int i = 0; i < propriedades.getLength(); i++) {
                tmp = propriedades.item(i);
                if(tmp.getNodeType() != Node.ELEMENT_NODE) continue;
                parametros.put(tmp.getNodeName(), Integer.parseInt(tmp.getTextContent()));
                System.out.println("[ " + tmp.getNodeName() + " ] = " + tmp.getTextContent());
                log.imprime("[ " + tmp.getNodeName() + " ] = " + tmp.getTextContent(), "#ffffff");
            }    
        } catch (IOException ex) {
            Logger.getLogger(Patogeno.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException | SAXException ex) {
            Logger.getLogger(SistemaImunologico.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public int getParametro(String nome){
        return parametros.get(nome);
    }

    public Map<String, Integer> getParametros() {
        return parametros;
    }
    
    public void mudaParametro(String nome,int valor){
        parametros.put(nome.toUpperCase(), valor);
    }
    
    public void addTemporizacao(int tempo){
        tempoMedio = (tempoMedio * qtTempo + tempo)/(qtTempo + 1);
        qtTempo++;
    }
    
    public int getTemporizacao(){
        return tempoMedio;
    }

    public boolean isPausado() {
        return pausado;
    }
    
    private Timer timer;
    
    public void iniciaThread(){
        timer = new Timer("Sistema Imunologico - IA");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Iterator<Celula> i = celulas.iterator();
                while(i.hasNext()){
                    i.next().loop();
                }
//                    Thread.sleep(((int) (velocidade * 30)));
            }
        }, 0,(int) (velocidade * 30));
        pausado = false;
    }
    
    public void pausaThread() {
        pausado = true;
        timer.cancel();
    }
    
    @Override
    public String toString() {
        return "SistemaImunologico\n{" + "nInicial=" + nInicial + ", celulas=\n" + celulas + ", pausada=" + pausado + '}';
    }
    
}