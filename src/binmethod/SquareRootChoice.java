/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binmethod;

/**
 *
 * @author ASUS
 */

import java.util.List;
import java.lang.Math;
public class SquareRootChoice extends BinFormulae{
    public SquareRootChoice(List<Double> _exampleData){ // call parent class constructor
        super(_exampleData);
    }
    @Override
    public  void calculateNumberOfBins(){ // calculate k based on sqrt rule
        List<Double> exampleData  =  this.getExampleData();
        int numberOfMeasurement = exampleData.size();
        int k = (int)(Math.ceil(Math.sqrt(numberOfMeasurement)));   // use the ceiling of the double decimal result
        this.setNumberOfBins(k);
    }
}
