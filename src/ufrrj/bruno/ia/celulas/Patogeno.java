package ufrrj.bruno.ia.celulas;

import java.awt.Polygon;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import ufrrj.bruno.ia.Parametros;
import ufrrj.bruno.ia.SistemaImunologico;
import ufrrj.bruno.ia.atributos.Poligono;
import ufrrj.bruno.ia.atributos.Posicao;
import ufrrj.bruno.ia.log.Virus;
import ufrrj.bruno.ia.quimica.CompostoQuimico;
import ufrrj.bruno.ia.quimica.CompostoQuimico.TIPO_COMPOSTO;

public class Patogeno extends Celula{
    
    private final long entrada = System.currentTimeMillis();
    private final Virus tipo;
    private Poligono forma;
    
    //====| RUNTIME |=====//
    private long inicio = System.currentTimeMillis();
    
    public Patogeno(SistemaImunologico sistema) {
        super(sistema,TIPO_CELULA.Patogeno);
        tipo = new Virus();
        forma = new Poligono(tipo.getnLados(),getPosicao());
        
        if(sistema.isDebug()) { sistema.imprime("Novo patogeno com identificador: "  + tipo.getIdentificador()); }
        emiteQuimica();
    }
    
    public Patogeno(SistemaImunologico sistema,Virus tipo) {
        super(sistema,TIPO_CELULA.Patogeno);
        forma = new Poligono(tipo.getnLados(),getPosicao());
        this.tipo = tipo;
        tipo.setQuantidade(tipo.getQuantidade()+1);
        emiteQuimica();
    }
    
    public Patogeno(SistemaImunologico sistema,Virus tipo,Posicao pos) {
        super(sistema,TIPO_CELULA.Patogeno,pos);
        forma = new Poligono(tipo.getnLados(),getPosicao());
        this.tipo = tipo;
        tipo.setQuantidade(tipo.getQuantidade()+1);
        emiteQuimica();
    }
    
    private void emiteQuimica(){
        getSistema().getCamada().compostos.add(new CompostoQuimico(TIPO_COMPOSTO.PAMP, 40,getPosicao(),this));
    }
    
    public void clona(){
        getSistema().adicionaCelula(new Patogeno(getSistema(),tipo,getPosicao()));
    }

    @Override
    public void loop() {             
        if((System.currentTimeMillis() - inicio) > getSistema().getParametro("DELAY_PROPAGACAO")){
            inicio += Parametros.DELAY_PROPAGACAO;
            emiteQuimica();
        }
        
        
        
	
        //move(new Posicao(400,400));
        //System.out.println("a");
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Polygon getForma() {
        return forma.getPoligono();
    }

    public long getEntrada() {
        return entrada;
    }

    public Virus getVirus() {
        return tipo;
    }
    
}
