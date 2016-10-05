package ufrrj.bruno.ia.quimica;

public class CompostoQuimico {   
    
    public enum TIPO_COMPOSTO {HISTAMINA,PROSTAGLANDINA};
    
    private TIPO_COMPOSTO tipo;
    private double quantidade;
    
    public CompostoQuimico(TIPO_COMPOSTO tipo,double quantidade){
        this.tipo = tipo;
        this.quantidade = quantidade;
    }
    
}
