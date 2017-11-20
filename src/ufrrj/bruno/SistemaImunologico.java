package ufrrj.bruno;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.javafx.geom.ConcentricShapePair;
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
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ufrrj.bruno.celulas.Celula;
import ufrrj.bruno.celulas.Celula.TIPO_CELULA;
import static ufrrj.bruno.celulas.Celula.TIPO_CELULA.*;
import ufrrj.bruno.celulas.Linfocito;
import ufrrj.bruno.celulas.Macrofago;
import ufrrj.bruno.celulas.Neutrofilo;
import ufrrj.bruno.celulas.Patogeno;
import ufrrj.bruno.log.Virus;
import ufrrj.bruno.telas.Console;

/**
 * Classe principal <br>
 * 
 * @author Bruno Prado
 * @param nInicial  Numero de leucocitos a serem gerados
 * @param camada  Camada sobreposta com os compostos quimicos
 * @param celulas ArrayList de todas as celulas exibidas
 */
@JsonInclude(Include.NON_EMPTY)
public class SistemaImunologico{
    @JsonIgnore private static final SistemaImunologico instancia = new SistemaImunologico();
    //======| Variaveis |=======//
    private final int nInicial;
    private ConcurrentLinkedQueue<Macrofago> macrofagos = new ConcurrentLinkedQueue<>(); //TROCAR PRA FAGOCITARIAS
    private ConcurrentLinkedQueue<Linfocito> linfocitos = new ConcurrentLinkedQueue<>(); //TODO: UNIR CELULAS DO SI HUMORAL
    private ConcurrentLinkedQueue<Neutrofilo> neutrofilos = new ConcurrentLinkedQueue<>(); //UNIR A MACROFAGOS
    private ConcurrentLinkedQueue<Patogeno> patogenos = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<Virus> virus = new ConcurrentLinkedQueue<>();
    private CamadaSobreposta camada;
    @JsonIgnore 
    public final Console log = Console.getInstancia();
    private final Map<String,Integer> parametros = new HashMap<>();
    //======|  RUNTIME  |======//
    @JsonIgnore public Scene cena;
    @JsonIgnore private double velocidade = 1.0;
    @JsonIgnore private static final long inicio = System.currentTimeMillis();
    @JsonIgnore private boolean mostraCamada = true;
    private boolean pausado = false;
    @JsonIgnore private boolean debug = false;
    private int temporizacao = 0;
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
         
    }
    
    public static synchronized SistemaImunologico getInstancia(){
        return instancia;
    }
    
    void geraPrimeiraGeracao(){
        imprime("Gerando sistema com " + nInicial * 10 + " leucócitos por microlitro de sangue");
        camada = new CamadaSobreposta();
        int i;
        for(i=0;i<(nInicial*parametros.get("NEUTROFILOS"))/1000;i++){
            neutrofilos.add(new Neutrofilo());
        }
        for(i=0;i<(nInicial*parametros.get("MACROFAGOS"))/1000;i++){
            macrofagos.add(new Macrofago());
        }
        for(i=0;i<(nInicial*parametros.get("LINFOCITOS"))/1000;i++){
            linfocitos.add(new Linfocito());
        }

    }
    
    public void reiniciaTimers(){
        pausaThread(); iniciaThread();
        camada.pausaThread(); camada.iniciaThread();
        Iterator<Patogeno> t = patogenos.iterator();
        while(t.hasNext()){
            t.next().reinicia();
        }
    }
    
    public void setVelocidade(int velo){
        double vel = velo/25;
        velocidade = (velo == 0) ? 2 : 2/vel;
        reiniciaTimers();
    }
    
    public ConcurrentLinkedQueue<Macrofago> getMacrofagos() {
        return macrofagos;
    }

    public ConcurrentLinkedQueue<Linfocito> getLinfocitos() {
        return linfocitos;
    }

    public ConcurrentLinkedQueue<Neutrofilo> getNeutrofilos() {
        return neutrofilos;
    }

    public ConcurrentLinkedQueue<Patogeno> getPatogenos() {
        return patogenos;
    }
    
    public void carrega(SistemaImunologico tmp){
        macrofagos = tmp.getMacrofagos();
        linfocitos = tmp.getLinfocitos();
        neutrofilos = tmp.getNeutrofilos();
        patogenos = tmp.getPatogenos();
        camada = tmp.getCamada();
        imprime(".JSON Carregado com " + (macrofagos.size() + linfocitos.size() + neutrofilos.size()) * 10 + " leucócitos por microlitro de sangue",Color.RED);
    }
   
    
    public void imprime(String texto){
        System.out.println(texto);
        log.imprime(texto,Color.GREEN);
    }
    
    public void imprime(String texto,Color c){
        System.out.println(texto);
        log.imprime(texto,c);
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
                log.imprime("[ " + tmp.getNodeName() + " ] = " + tmp.getTextContent(), Color.WHITE);
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
        temporizacao = (temporizacao * qtTempo + tempo)/(qtTempo + 1);
        qtTempo++;
    }
    
    public int getTemporizacao(){
        return temporizacao;
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
                Iterator<Macrofago> m = macrofagos.iterator();
                while(m.hasNext()){
                    m.next().loop();
                }
                
                Iterator<Neutrofilo> n = neutrofilos.iterator();
                while(n.hasNext()){
                    n.next().loop();
                }
                
                Iterator<Linfocito> l = linfocitos.iterator();
                while(l.hasNext()){
                    l.next().loop();
                }
                
                Iterator<Patogeno> t = patogenos.iterator();
                while(t.hasNext()){
                    t.next().loop();
                }
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
        return "SistemaImunologico\n{" + "nInicial=" + nInicial + ", celulas=\n, pausada=" + pausado + '}';
    }
    
}