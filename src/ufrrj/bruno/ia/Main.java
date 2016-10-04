package ufrrj.bruno.ia;

import javax.swing.JFrame;
import ufrrj.bruno.ia.renderizacao.Grafico2D;
import ufrrj.bruno.ia.Telas.Janela;

public class Main{
    public static void main(String[] args) {

        SistemaImunologico sistema = new SistemaImunologico();
        Grafico2D grafico = new Grafico2D(sistema);
        
        Janela tela = new Janela("SIA",sistema);
        tela.setSize(Parametros.LARGURA,Parametros.ALTURA);
        tela.setLocationRelativeTo(null);
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        tela.getContentPane().add(grafico);
        tela.setVisivel(true);
        
    } 
}
