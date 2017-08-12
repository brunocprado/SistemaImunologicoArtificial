package ufrrj.bruno.atributos;

public class Poligono {
    
    public double[] x;
    public double[] y;    
    private int nLados;
    
    
    public Poligono(){
        nLados = 3;
    }
    
    public Poligono(int nLados){
        this.nLados = nLados;
        x = new double[nLados];
        y = new double[nLados];
        geraPoligono(nLados);
    }
    
    public void geraPoligono(int nLados){
//        forma = new Polygon();
        double fatia = 2 * Math.PI / nLados;
     
        for(int i = 0;i<nLados;i++){
            x[i] = Math.cos(i * fatia) * 6;
            y[i] = Math.sin(i * fatia) * 6;
        }
    }

    public int getnLados() {
        return nLados;
    }

    public void setnLados(int nLados) {
        this.nLados = nLados;
    }
    
}