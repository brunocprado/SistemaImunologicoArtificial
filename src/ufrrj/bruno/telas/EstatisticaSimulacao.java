package ufrrj.bruno.telas;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import ufrrj.bruno.SistemaImunologico;
import ufrrj.bruno.quimica.CompostoQuimico;

public class EstatisticaSimulacao extends PieChart{
    
    public EstatisticaSimulacao(int momento){
        
        int pamp = 0,citocina = 0;
        
        for(CompostoQuimico composto : SistemaImunologico.getInstancia().getCamada().compostos){
            if(composto.getTipo() == CompostoQuimico.TIPO_COMPOSTO.PAMP) pamp += composto.getQuantidade();
            if(composto.getTipo() == CompostoQuimico.TIPO_COMPOSTO.CITOCINA) citocina += composto.getQuantidade();
        }
        
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("PAMP", pamp),
                new PieChart.Data("Citocinas", citocina),
                new PieChart.Data("Histamina", 10));
        setTitle("Concentração de compostos químicos após " + momento + " segundos");
        setData(pieChartData);
        
    }
    
}
