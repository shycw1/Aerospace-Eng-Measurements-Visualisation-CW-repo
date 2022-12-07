/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statutils;

/**
 *
 * @author ASUS
 */
//20123739

import java.util.Arrays;
import java.util.HashMap;
import java.util.List; 


public class UnNormalisedHist extends StatisticalUtilities{
    public UnNormalisedHist(List<Double> _exampleData, int _numberOfBins){
        super(_exampleData, _numberOfBins);  // call parent class constructor
        
    }
    
    @Override
    public void calculateHist(){
        
        HashMap<String, Double> hist_stats_map = this.getStatisticalFigures();
        double max = hist_stats_map.get("max");
        double min = hist_stats_map.get("min");
        double bin_resolution = (double)(max - min)/this.getNumberOfBins();
        List<Double> HistInfo = Arrays.asList(new Double[this.getNumberOfBins()]);
        for (int i = 0; i < this.getNumberOfBins(); i++) {
            HistInfo.set(i, 0.0);
        }
        for (int i = 0; i < this.getExampleData().size(); i++){
            for (int j = 0; j < this.getNumberOfBins(); j++){
                double bin_min = bin_resolution*j + min;
                double bin_max = bin_min +bin_resolution;
                
                if(this.getExampleData().get(i)>=bin_min && this.getExampleData().get(i)< bin_max){
                    double count = HistInfo.get(j);
                    HistInfo.set(j, count+1);
                } // count the number of data falling in each bin (range)
            }
            
        }
        HistInfo.set(this.getNumberOfBins()-1, HistInfo.get(this.getNumberOfBins()-1)+1);
        this.setHist(HistInfo);
    }
}
