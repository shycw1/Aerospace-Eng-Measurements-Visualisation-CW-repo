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
// 20123739
import java.util.Arrays;
import java.util.HashMap;
import java.util.List; 


public class NormalisedHist extends StatisticalUtilities{
    public NormalisedHist(List<Double> _exampleData, int _numberOfBins){
        super(_exampleData, _numberOfBins); // call parent class constructor
        
    }
    
    @Override
    public void calculateHist(){
        
        HashMap<String, Double> hist_stats_map = this.getStatisticalFigures();
        double max = hist_stats_map.get("max");
        double min = hist_stats_map.get("min");
        double bin_resolution = (double)(max - min)/this.getNumberOfBins();
        List<Double> Unnormalised_HistInfo = Arrays.asList(new Double[this.getNumberOfBins()]);
        for (int i = 0; i < this.getNumberOfBins(); i++) {
            Unnormalised_HistInfo.set(i, 0.0);
        }
        for (int i = 0; i < this.getExampleData().size(); i++){
            for (int j = 0; j < this.getNumberOfBins(); j++){
                double bin_min = bin_resolution*j + min;
                double bin_max = bin_min +bin_resolution;
                
                if(this.getExampleData().get(i)>=bin_min && this.getExampleData().get(i)< bin_max){
                    double count = Unnormalised_HistInfo.get(j);
                    Unnormalised_HistInfo.set(j, count+1);
                }// count the number of data falling in each bin (range)
            }
            
        } 
        Unnormalised_HistInfo.set(this.getNumberOfBins()-1, Unnormalised_HistInfo.get(this.getNumberOfBins()-1)+1);
        this.setHist(Unnormalised_HistInfo);
        this.normaliseHist(); // normalisation

    }
    
    private void normaliseHist(){ // this private method provides normalisation of the count in each bin
        HashMap<String, Double> hist_stats_map = this.getStatisticalFigures();
        List<Double> hist = this.getHist();
        double max = hist_stats_map.get("max");
        double min = hist_stats_map.get("min");
        int k = this.getNumberOfBins();
        double W = (double)(max - min)/k;
        double area = 0.0;
        for (int i = 0; i < k; i ++){
            area = area + (double) hist.get(i) * W;
        }
        for (int i = 0; i < k; i++){
            double bin_value = (double) hist.get(i) / area;
            hist.set(i, bin_value);
        }
        this.setHist(hist);
    }
}
