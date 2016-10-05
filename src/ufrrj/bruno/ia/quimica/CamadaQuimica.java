package ufrrj.bruno.ia.quimica;

import java.util.HashSet;
import ufrrj.bruno.ia.Parametros;
import ufrrj.bruno.ia.SistemaImunologico;

public class CamadaQuimica {
    
    private final SistemaImunologico sistema;
    private CompostoQuimico matriz[][];

    public CamadaQuimica(SistemaImunologico sistema){
        this.sistema = sistema;
        matriz = new CompostoQuimico[Parametros.TAMY/8][Parametros.TAMX/8];
    }
    
    public void editaPosicao(int y,int x,CompostoQuimico elemento){
        matriz[y][x] = elemento;
    }
    
}
