package ufrrj.bruno.ia;

import ufrrj.bruno.ia.telas.Janela;

/**
 * Sistema Imunológico Artificial <br>
 * 
 * @author Bruno Prado
 */
public class Main{
    public static void main(String[] args) {   
        SistemaImunologico sistema = SistemaImunologico.getInstancia();
        sistema.geraPrimeiraGeracao();
        sistema.iniciaThread();
        
        Janela tela = new Janela();   
        tela.setVisible(true);    
    } 
}
