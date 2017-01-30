package ufrrj.bruno.ia;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import ufrrj.bruno.ia.quimica.CamadaSobreposta;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
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
import ufrrj.bruno.ia.log.Log;
import ufrrj.bruno.ia.celulas.Celula;
import ufrrj.bruno.ia.celulas.Celula.TIPO_CELULA;
import static ufrrj.bruno.ia.celulas.Celula.TIPO_CELULA.Comum;
import ufrrj.bruno.ia.celulas.Linfocito;
import ufrrj.bruno.ia.celulas.Macrofago;
import ufrrj.bruno.ia.celulas.Neutrofilo;
import ufrrj.bruno.ia.celulas.Patogeno;
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
    private final Log log = new Log(this);
    private Map<String,Integer> parametros = new HashMap<>();
    //======|  RUNTIME  |======//
    private final long inicio = System.currentTimeMillis();
    private boolean mostraCamada = true;
    public boolean pausada = false;
    private boolean debug = false;
    //======| DISPLAY |======//
    public Map<TIPO_CELULA,Boolean> exibir = new HashMap<>();
    
    public SistemaImunologico(){
        carregaParametros();
        camada = new CamadaSobreposta(this);
        nInicial = new Random().nextInt(parametros.get("TAM_MEDIO_SUPERIOR") - parametros.get("TAM_MEDIO_INFERIOR")) + parametros.get("TAM_MEDIO_INFERIOR");
        geraPrimeiraGeracao();
        exibir.put(TIPO_CELULA.Macrofago, true);
        exibir.put(TIPO_CELULA.Linfocito, true);
        exibir.put(TIPO_CELULA.Neutrofilo, true);
        exibir.put(TIPO_CELULA.Patogeno, true);
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
        for(i=0;i<(nInicial*parametros.get("NEUTROFILOS"))/1000;i++){
            celulas.add(new Neutrofilo(this));
        }
        for(i=0;i<(nInicial*parametros.get("MACROFAGOS"))/1000;i++){
            celulas.add(new Macrofago(this));
        }
        for(i=0;i<(nInicial*parametros.get("LINFOCITOS"))/1000;i++){
            celulas.add(new Linfocito(this));
        }
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
    
    private void carregaParametros(){
        try {
            File arquivo = new File("res/parametros.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;
            dBuilder = dbFactory.newDocumentBuilder();
            Document documento = dBuilder.parse(arquivo);
            
            Node user = documento.getDocumentElement();
            NodeList childs = user.getChildNodes();
            Node child;
            for (int i = 0; i < childs.getLength(); i++) {
                child = childs.item(i);
                if(child.getNodeType() != child.ELEMENT_NODE) continue;
                parametros.put(child.getNodeName(), Integer.parseInt(child.getTextContent()));
            }     
            System.out.println(parametros);
                        

        } catch (IOException ex) {
            Logger.getLogger(Patogeno.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(SistemaImunologico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(SistemaImunologico.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public int getParametro(String nome){
        return parametros.get(nome);
    }
    
    public void mudaParametro(String nome,int valor){
        parametros.put(nome.toUpperCase(), valor);
    }
    
    @Override
    public String toString() {
        return "SistemaImunologico{" + "nInicial=" + nInicial + ", celulas=" + celulas + ", pausada=" + pausada + '}';
    }
    
}