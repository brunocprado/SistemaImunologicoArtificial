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
    private static final int tamX = Parametros.TAMX/8;
    private static final int tamY = Parametros.TAMY/8;
    
    public CamadaQuimica(SistemaImunologico sistema){
        this.sistema = sistema;
        matriz = new int[Parametros.TAMY / 8][Parametros.TAMX / 8];
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
            //INSERIR ALG Incendio florestal
            for(int y = 0;y<tamY;y++){
                for(int x = 0;x<tamY;x++){
                    if(matriz[y][x] > 0){
                        matriz[y][x] -= 1;
                    }
                }
            }
//            for(int y = 0;y<tamY;y++){
//                for(int x = 0;x<tamY;x++){
//                    if(matriz[y][x+1] > 0){ //DIREITA
//                        matriz[y][x] = matriz[y][x+1];
//                        break;
//                    }
//                    if(x >0 && matriz[y][x-1] > 0 ){ //ESQUERDA
//                        matriz[y][x] = matriz[y][x-1];
//                        break;
//                    }
//                    if(y > 0 && matriz[y-1][x] > 0){ //CIMA
//                        matriz[y][x] = matriz[y-1][x];
//                        break;
//                    }
//                    if(y < tamY - 1 && matriz[y+1][x] > 0){ //BAIXO
//                        matriz[y][x] = matriz[y+1][x];
//                        break;
//                    }
//                }
//            }
            
//             if(matriz[y][x] * 50 > 0) {
//                        if(y>0 && y<tamY){
//                            matriz[y-1][x] = matriz[y][x] - 1;
//                            matriz[y+1][x-1] = matriz[y][x] - 1;
//                            matriz[y+1][x] = matriz[y][x] - 1;
//                            matriz[y+1][x+1] = matriz[y][x] - 1;
//                            if(x>0){
//                                matriz[y-1][x-1] = matriz[y][x] - 1;
//                                matriz[y][x-1] = matriz[y][x] - 1;
//                            }
//                            if(x<tamX){
//                                matriz[y-1][x+1] = matriz[y][x] - 1;
//                                matriz[y][x+1] = matriz[y][x] - 1;
//                            }
//                        }
////                           y++;
////                           x++;
//
//                         matriz[y][x] -= 1;   
//                        
//                    }
            
            try {
                Thread.sleep(Parametros.TEMPO_PROPAGACAO_QUIMICOS);
            } catch (InterruptedException ex) {
                Logger.getLogger(CamadaQuimica.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
      
    
}
