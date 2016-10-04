package ufrrj.bruno.ia;

import java.util.HashSet;

public class CamadaQuimica {
    
    private final SistemaImunologico sistema;
    private int matriz[][];

    public CamadaQuimica(SistemaImunologico sistema){
        this.sistema = sistema;
        matriz = new int[Parametros.TAMY/8][Parametros.TAMX/8];
    }
    
}
