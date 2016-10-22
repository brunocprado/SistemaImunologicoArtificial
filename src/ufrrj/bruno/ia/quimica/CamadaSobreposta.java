package ufrrj.bruno.ia.quimica;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import ufrrj.bruno.ia.Parametros;
import ufrrj.bruno.ia.SistemaImunologico;

public class CamadaSobreposta implements Runnable{
    
    private final SistemaImunologico sistema;
    private CompostoQuimico matriz[][] = new CompostoQuimico[Parametros.TAMY/8][Parametros.TAMX/8];
    private Thread t;
    private static final int tamX = Parametros.TAMX/8;
    private static final int tamY = Parametros.TAMY/8;
    
    public CamadaSobreposta(SistemaImunologico sistema){
        this.sistema = sistema;
        t = new Thread(this,"Camada Quimica");
        t.start();
//        matriz = new CompostoQuimico[Parametros.TAMY/8][Parametros.TAMX/8];
    }
    
    public void editaPosicao(int x,int y,CompostoQuimico elemento){
        matriz[y][x] = elemento;
    }
    
    public CompostoQuimico getPosicao(int x,int y){
        return matriz[y][x];
    }

    public CompostoQuimico[][] getMatriz() {
        return matriz;
    }

    private void pausa(int tempo){
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException ex) {
            Logger.getLogger(CamadaSobreposta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //implementar pause
    @Override
    public void run() {
        while(true) {
            while(sistema.pausada){
                pausa(5);
            }            
            //long inicio = System.currentTimeMillis();           
            Set<int[]> tmp = new HashSet<int[]>();
            
            for(int y = 0;y<tamY;y++){
                for(int x = 0;x<tamX;x++){
                    if(matriz[y][x] != null && matriz[y][x].getQuantidade() > 0){            
                        int[] a = new int[2];
                        a[0] = y; a[1] = x;
                        tmp.add(a);
//                        matriz[y][x].diminuiQuantidade(1);
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
            
    //System.out.println(System.currentTimeMillis() - inicio);
            
            pausa(Parametros.TEMPO_PROPAGACAO_QUIMICOS);
        }
    }
      
    
}
