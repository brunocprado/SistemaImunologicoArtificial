package ufrrj.bruno.ia.graficos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JFrame;
import ufrrj.bruno.ia.SistemaImunologico;
import ufrrj.bruno.ia.celulas.Celula;

public class GraficoPizza extends JComponent implements Runnable{
    private ArrayList fatias = new ArrayList<Fatia>();
    private Rectangle area;
    private SistemaImunologico mundo;
    private Thread t;
    public void paint(Graphics g){
        g.clearRect(0, 0, getWidth(), getHeight());
        desenhaGrafico((Graphics2D) g, area, fatias);
    }
    
    public void adicionaFatia(Fatia fatia){
        fatias.add(fatia);
    }
    
    public void desenhaGrafico(Graphics2D g, Rectangle area,ArrayList<Fatia> fatias){
        atualizaFatias();
        double soma = 0;
        int angulo = 0;
        double valAtual = 0;
        for(Fatia fatia : fatias){
            soma += fatia.valor;
        }
        for(Fatia fatia : fatias){
            angulo = (int) ((valAtual * 360)/soma);
            int anguloF = (int) ((fatia.valor * 360)/soma);
            g.setColor(fatia.cor);
            g.fillArc(area.x, area.y, area.width, area.height, angulo, anguloF);
            //g.drawString("teste", anguloF, 6);
            valAtual += fatia.valor;
        }
        System.out.println("aaaa");
    }
    
    public void desenhaGrafico(Graphics2D g, Rectangle area,Fatia[] fatias){
        double soma = 0;
        int angulo = 0;
        double valAtual = 0;
        
        for(int i = 0;i<fatias.length;i++){
            soma += fatias[i].valor;
        }
        for(int i = 0;i<fatias.length;i++){
            angulo = (int) ((valAtual * 360)/soma);
            int anguloF = (int) ((fatias[i].valor * 360)/soma);
            g.setColor(fatias[i].cor);
            g.fillArc(area.x, area.y, area.width, area.height, angulo, anguloF);
            //g.drawString("teste", anguloF, 6);
            valAtual += fatias[i].valor;
        }
        System.out.println("Teste");
    }
    
    
    public GraficoPizza(){
        area = getBounds();
    }
    public GraficoPizza(Fatia[] fatias,Rectangle area){
        //this.fatias = fatias;
        this.area = area;
        t = new Thread(this);
        t.start();
    }
    public GraficoPizza(SistemaImunologico mundo,Rectangle area){
        this.area = area;
        this.mundo = mundo;
        t = new Thread(this);
        t.start();
    }
    public void atualizaFatias(){
        fatias.clear();
        int n = 0;
        for(Celula celula : mundo.getCelulas()) {
            n++;
        }
        fatias.add(new Fatia(n,Color.RED));
        fatias.add(new Fatia(10,Color.BLUE));
    }

    @Override
    public void run() {
        while(true){
            repaint();
            try {
                t.sleep(500);
            } catch (InterruptedException ex) {
               // Logger.getLogger(Visual.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
