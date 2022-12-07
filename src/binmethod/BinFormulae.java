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
// 20123739

import java.util.List; 

// abstract class
public abstract class BinFormulae { // parent class for three different rules for calculatiing bin number.

    private List<Double> exampleData;
    private int numberOfBins;
    public BinFormulae(){               // empty constructor
        // do nothing here
    }
    public BinFormulae(List<Double> _exampleData){  // constructor to initialise data list containing measurement data
        setExampleData(_exampleData);
    }
    public void setExampleData(List<Double> _exampleData){ // set medthod for setting example data list
        this.exampleData = _exampleData;
    }
    public List<Double> getExampleData(){ // get medthod for returning example data list
        return this.exampleData;
    }
    public void setNumberOfBins(int _numberOfBins){ // set method for number of bins (or k)
        this.numberOfBins = _numberOfBins;
    }
    public int getNumberOfBins(){  // return method for number of bins (or k)
        return this.numberOfBins;
    }
    public abstract void calculateNumberOfBins(); // calculate number of bins based on different child class
}
