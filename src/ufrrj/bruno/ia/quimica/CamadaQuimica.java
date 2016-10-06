package ufrrj.bruno.ia.quimica;

import java.util.HashSet;
import ufrrj.bruno.ia.Parametros;
import ufrrj.bruno.ia.SistemaImunologico;

public class CamadaQuimica {
    
    private final SistemaImunologico sistema;
    private CompostoQuimico matriz[][]= new CompostoQuimico[Parametros.TAMY/8][Parametros.TAMX/8];;

    public CamadaQuimica(SistemaImunologico sistema){
        this.sistema = sistema;
//        matriz = new CompostoQuimico[Parametros.TAMY/8][Parametros.TAMX/8];
    }
    
    public void editaPosicao(int x,int y,CompostoQuimico elemento){
        matriz[y][x] = elemento;
    }

    public CompostoQuimico[][] getMatriz() {
        return matriz;
    }
    
    
    
}
