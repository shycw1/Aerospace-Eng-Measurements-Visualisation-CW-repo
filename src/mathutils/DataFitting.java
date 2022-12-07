/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mathutils;

/**
 *
 * @author ASUS
 */

// 20123739
import java.util.List;

import org.apache.commons.math3.fitting.*; // [Info] this library must be menually added to netbeans. The maths library provided on Moodle 

public class DataFitting {
    public DataFitting(){
    }
    public DataFitting(List<Double> _BinCentrePoints, List<Double> _Frequencies){
        BinCentrePoints = _BinCentrePoints;
        Frequencies = _Frequencies;
        
    }
    public static double[] calculatePDFparameters(){
        WeightedObservedPoints obs = new WeightedObservedPoints();
         
         int bin_number = BinCentrePoints.size();
         for(int i = 0; i < bin_number; i++){
            obs.add(BinCentrePoints.get(i),  Frequencies.get(i));

        }
        double[] parameters = GaussianCurveFitter.create().fit(obs.toList());
       
        return parameters;
    }
    
    private static List<Double> BinCentrePoints;
    private static List<Double> Frequencies;
}
