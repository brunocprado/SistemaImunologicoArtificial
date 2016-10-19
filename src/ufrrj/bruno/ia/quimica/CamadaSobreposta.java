package ufrrj.bruno.ia.quimica;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import ufrrj.bruno.ia.Parametros;
import ufrrj.bruno.ia.SistemaImunologico;

public class CamadaSobreposta implements Runnable{
    
    private final SistemaImunologico sistema;
    private int matriz[][] = new int[Parametros.TAMY/8][Parametros.TAMX/8];
    private Thread t;
    private static final int tamX = Parametros.TAMX/8;
    private static final int tamY = Parametros.TAMY/8;
    
    public CamadaSobreposta(SistemaImunologico sistema){
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
            //INSERIR ALG Incendio florestal]
            
            long inicio = System.currentTimeMillis();
            
            Set<int[]> tmp = new HashSet<int[]>();
            
            for(int y = 0;y<tamY;y++){
                for(int x = 0;x<tamX;x++){
                    if(matriz[y][x] > 0){            
                        int[] a = new int[2];
                        a[0] = y; a[1] = x;
                        tmp.add(a);
                        matriz[y][x] -= 1;
                    }
                }
            }
            
            for(int [] pos : tmp){
                if(pos[0] > 0){
                    if(pos[1] > 0) matriz[pos[0] - 1][pos[1]-1] = matriz[pos[0]][pos[1]];
                    matriz[pos[0] - 1][pos[1]] = matriz[pos[0]][pos[1]];
                    if(pos[1] < tamX - 1) matriz[pos[0] - 1][pos[1]+1] = matriz[pos[0]][pos[1]];
                }
                if(pos[1] > 0) matriz[pos[0]][pos[1]-1] = matriz[pos[0]][pos[1]];
                if(pos[1] < tamX - 1) matriz[pos[0]][pos[1]+1] = matriz[pos[0]][pos[1]];
                if(pos[0] < tamY - 1){
                    if(pos[1] > 0) matriz[pos[0] + 1][pos[1]-1] = matriz[pos[0]][pos[1]];
                    matriz[pos[0] + 1][pos[1]] = matriz[pos[0]][pos[1]];
                    if(pos[1] < tamX - 1) matriz[pos[0] + 1][pos[1]+1] = matriz[pos[0]][pos[1]];
                }
//                matriz[pos[0]][pos[1]] += 1;
            }
            
            
//            System.out.println(System.currentTimeMillis() - inicio);
            






//for(int [] pos : tmp){
//                if(pos[0] > 0){
//                    if(pos[1] > 0) matriz[pos[0] - 1][pos[1]-1] = matriz[pos[0]][pos[1]] - 1;
//                    matriz[pos[0] - 1][pos[1]] = matriz[pos[0]][pos[1]] - 1;
//                    if(pos[1] < tamX - 1) matriz[pos[0] - 1][pos[1]+1] = matriz[pos[0]][pos[1]] - 1;
//                }
//                if(pos[1] > 0) matriz[pos[0]][pos[1]-1] = matriz[pos[0]][pos[1]] - 1;
//                if(pos[1] < tamX - 1) matriz[pos[0]][pos[1]+1] = matriz[pos[0]][pos[1]] - 1;
//                if(pos[0] < tamY - 1){
//                    if(pos[1] > 0) matriz[pos[0] + 1][pos[1]-1] = matriz[pos[0]][pos[1]] - 1;
//                    matriz[pos[0] + 1][pos[1]] = matriz[pos[0]][pos[1]] - 1;
//                    if(pos[1] < tamX - 1) matriz[pos[0] + 1][pos[1]+1] = matriz[pos[0]][pos[1]] - 1;
//                }
//            }
            






//            for(int y = 0;y<tamY;y++){
//                for(int x = 0;x<tamY;x++){
//                    if(matriz[y][x] > 0){
//                        matriz[y][x] -= 1;
//                    }
//                }
//            }
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
                Logger.getLogger(CamadaSobreposta.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
      
    
}
