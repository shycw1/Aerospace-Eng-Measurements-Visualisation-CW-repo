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
import java.util.Arrays;
import binmethod.*;

// press shift+F6 to run this unit test file

public class Unit_test_binmethod {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        List<Double> exampleData = Arrays.asList(1., 2., 3., 4., 5., 6., 7., 8., 9., 10.,11.);
        System.out.println("[Info] Example Data: "+exampleData); // expected results should be 5
        // test SturgesFormula class 
        SturgesFormula SturgesInstance = new SturgesFormula(exampleData);  
        SturgesInstance.calculateNumberOfBins(); 
        System.out.printf("By Sturges Formula: %d \n", SturgesInstance.getNumberOfBins()); // expected results should be 5
        
        // test SquareRootChoice class 
        SquareRootChoice SquareRootChoiceInstance = new SquareRootChoice(exampleData); 
        SquareRootChoiceInstance.calculateNumberOfBins(); 
        System.out.printf("By Square Root Formula: %d \n", SquareRootChoiceInstance.getNumberOfBins());  // expected results should be 4
        
        
        // test Rice rule class
        RiceRule RiceRuleInstance = new RiceRule(exampleData);
        RiceRuleInstance.calculateNumberOfBins();
        System.out.printf("By Rice Rule Formula: %d \n", RiceRuleInstance.getNumberOfBins()); // expected results should be 5
    }
    
}
