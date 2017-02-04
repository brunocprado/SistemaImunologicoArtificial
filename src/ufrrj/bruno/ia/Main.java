package ufrrj.bruno.ia;

import ufrrj.bruno.ia.telas.Janela;

/**
 * Sistema Imunológico Artificial <br>
 * 
 * @author Bruno Prado
 */
public class Main{
    public static void main(String[] args) {    
        Janela tela = new Janela(new SistemaImunologico());      
        tela.setVisible(true);    
    } 
}
