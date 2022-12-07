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
import java.util.List;
import java.util.Collections;
import java.lang.Math;
import java.util.HashMap;
import java.util.ArrayList;
public abstract class StatisticalUtilities{ // abstract class and super class for UnNormalisedHist and NormalisedHist
    private List<Double> exampleData;
    private List<Double> histogram;
    private List<Double> binCentrePoints;
    private int numberOfBins;
    private double mean;
    private double variance;
    private double max;
    private double min;
    private double median;
    private double std;
    public StatisticalUtilities(){ // empty constructor
    }
    public StatisticalUtilities(List<Double> _exampleData, int _numberOfBins){  // constructor 2 with measurement data, bin number initialisation
        setExampleData(_exampleData);
        setNumberOfBins(_numberOfBins);    
        this.calculateStatisticalFigures(); // calculate statistical figrues such as min, max, std...etc 
        this.setbinCentre(); // define bin centre based on number of bins and max and min values.
        
    }
    public void setExampleData(List<Double> _exampleData){ // set medthod for example data list
        this.exampleData = _exampleData;
    }
    public List<Double> getExampleData(){ // get method for example data
        return this.exampleData;
    }
    public void setNumberOfBins(int _numberOfBins){ // set medthod for number of bins
        this.numberOfBins = _numberOfBins;
    }
    public int getNumberOfBins(){ // set medthod for number of bins
        return this.numberOfBins;
    }
    public void calculateStatisticalFigures(){   // calculating all kinds of statistical figures such as mean, max, min... and set them.
        int DataSize = this.exampleData.size();
        double sum = this.exampleData.stream().mapToDouble(f -> f.doubleValue()).sum();
        this.mean = sum/DataSize;                    
        this.max = Collections.max(this.exampleData);
        this.min = Collections.min(this.exampleData);
        List<Double> exampleData_copy = this.exampleData;
        Collections.sort(exampleData_copy);
        if (exampleData_copy.size() % 2 == 0)
            median = (exampleData_copy.get(DataSize/2) + exampleData_copy.get(DataSize/2-1))/2;
        else
            median = exampleData_copy.get(DataSize/2);
        
        for(double value : this.exampleData)
        {
            double diff = value - this.mean;
            diff *= diff;
            this.variance += diff;
        }
        this.variance =  this.variance / (DataSize-1);
        this.std = Math.sqrt(this.variance); 
        // calculate mean, variance, max, min, median and std
    }
    public HashMap<String, Double> getStatisticalFigures(){
        HashMap<String, Double> StatisticalHashMap = new HashMap<String, Double>();
        StatisticalHashMap.put("mean", this.mean);
        StatisticalHashMap.put("variance", this.variance);
        StatisticalHashMap.put("max", this.max);
        StatisticalHashMap.put("min", this.min);
        StatisticalHashMap.put("median", this.median);
        StatisticalHashMap.put("std", this.std);
        return StatisticalHashMap;
    } // format statistical figures into a dictionary (HashMap) for easy accessing later
    public abstract void calculateHist(); // abstract class
    public List<Double> getHist(){ // return histogram
        return this.histogram;
    }
    public void setHist(List<Double> _hist){ // set histogram
        this.histogram = _hist;
    }
    
    private void setbinCentre(){  // set bin centre
        int k = this.numberOfBins;
        List<Double> binCentreList =  new ArrayList<>(Collections.nCopies(k, 0.0));
        double w = (this.max - this.min)/k;
        for(int i =0; i < k; i++){
            double bin_min = this.min+i*w;
            double bin_max = this.min+(i+1)*w;
            double bin_centre = (bin_min + bin_max)/2;
            binCentreList.set(i,bin_centre);
        }
        this.binCentrePoints = binCentreList;
        
    }
    public List<Double> getbinCentre(){ // get bin centre
        return this.binCentrePoints;
    }
}
