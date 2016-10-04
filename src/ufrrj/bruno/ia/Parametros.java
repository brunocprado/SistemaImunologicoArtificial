package ufrrj.bruno.ia;

public class Parametros {
    //===| Config Visualização |===//
    public final static int LIMITE_FPS = 60;
    public final static int LARGURA = 1280;
    public final static int ALTURA  = 720;
    
    //====| Config Simulacao |====//
    public final static int TAMX = 1280;
    public final static int TAMY = 720;
    
    public static int TAM_MEDIO_INFERIOR = 400;
    public static int TAM_MEDIO_SUPERIOR = 1100;
    //Fonte :In the US this is usually expressed as 4,000–11,000 white blood cells per microliter of blood. 
    //Total leukocytes: 4.00-11.0 x 10 9/L
    //Adult	3.5 3.9 4.1 4.5	  9.0 10.0 10.9 11    - x109/L
    
    //====| Proporcao real |====//
    public static double MACROFAGOS  = 0.053;
    public static double NEUTROFILOS = 0.65;
    public static double LINFOCITOS  = 0.26;
    //=========================//
    //Fonte : Wikipedia - PT
    
    public final static int nVer = 21;
}