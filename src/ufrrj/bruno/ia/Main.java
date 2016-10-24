package ufrrj.bruno.ia;

import javax.swing.JFrame;
import ufrrj.bruno.ia.Telas.Janela;

public class Main{
    public static void main(String[] args) {

        SistemaImunologico sistema = new SistemaImunologico();
        
        Janela tela = new Janela("SIA",sistema);
        tela.setSize(Parametros.LARGURA,Parametros.ALTURA);
        tela.setLocationRelativeTo(null);
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tela.setVisible(true);
        
    } 
}
