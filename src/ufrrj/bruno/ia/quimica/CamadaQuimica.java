package ufrrj.bruno.ia.quimica;

import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import ufrrj.bruno.ia.Parametros;
import ufrrj.bruno.ia.SistemaImunologico;

public class CamadaQuimica implements Runnable{
    
    private final SistemaImunologico sistema;
    private int matriz[][] = new int[Parametros.TAMY/8][Parametros.TAMX/8];
    private Thread t;
    
    public CamadaQuimica(SistemaImunologico sistema){
        this.sistema = sistema;
        t = new Thread(this,"Camada Quimica");
        t.start();
//        matriz = new CompostoQuimico[Parametros.TAMY/8][Parametros.TAMX/8];
    }
    
    public void editaPosicao(int x,int y,int elemento){
        matriz[y][x] = elemento;
    }

    public int[][] getMatriz() {
        return matriz;
    }

    //implementar pause
    @Override
    public void run() {
        while(true) {
            for(int y = 0;y<Parametros.TAMY/8;y++){
                for(int x = 0;x<Parametros.TAMX/8;x++){
                    if(matriz[y][x] > 0) {
                        matriz[y-1][x-1] = 2;
                        matriz[y-1][x] = 2;
                        matriz[y-1][x+1] = 2;
                        matriz[y][x-1] = 2;
                        matriz[y][x+1] = 2;
                        matriz[y+1][x-1] = 2;
                        matriz[y+1][x] = 2;
                        matriz[y+1][x+1] = 2;

                        
                    }
                }
            }
            
            try {
                Thread.sleep(Parametros.TEMPO_PROPAGACAO_QUIMICOS);
            } catch (InterruptedException ex) {
                Logger.getLogger(CamadaQuimica.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
      
    
}
