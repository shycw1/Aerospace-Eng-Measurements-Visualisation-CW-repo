/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binmethod;

import java.util.HashSet;
import java.util.List;
import java.lang.Math;
/**
 *
 * @author ASUS
 */
// 20123739
public class RiceRule extends BinFormulae{
    public RiceRule(List<Double> _exampleData){
        super(_exampleData); // call parent class constructor
    }
    @Override
    public  void calculateNumberOfBins(){  // calculate k based on rice rule
        List<Double> exampleData  =  this.getExampleData();
        int numberOfMeasurement = exampleData.size();
        int k = (int)(Math.ceil((2*Math.cbrt(numberOfMeasurement))));  // use the ceiling of the double decimal result
        this.setNumberOfBins(k);
    }

}
