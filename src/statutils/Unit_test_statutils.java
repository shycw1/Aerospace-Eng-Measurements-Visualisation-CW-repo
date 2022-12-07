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
import binmethod.*;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;

public class Unit_test_statutils {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        List<Double> exampleData = Arrays.asList(1., 2., 3. ,4. ,5. ,6. , 7. , 8. , 9., 10., 11.);

        // test SturgesFormula class 
        SturgesFormula SturgesInstance = new SturgesFormula(exampleData);
        SturgesInstance.calculateNumberOfBins();
        
        // test unnormalised class
        UnNormalisedHist hist_unnormalised_Sturges = new UnNormalisedHist(exampleData, SturgesInstance.getNumberOfBins());
        hist_unnormalised_Sturges.calculateHist();
        
        // test normalised class
        NormalisedHist hist_normalised_Sturges = new NormalisedHist(exampleData, SturgesInstance.getNumberOfBins());
        HashMap<String, Double> hist_stats_map_Sturges_noramlised = hist_normalised_Sturges.getStatisticalFigures(); // store statistical data in a dictionary for easy accessings
        hist_normalised_Sturges.calculateHist();
        
        double mean = hist_stats_map_Sturges_noramlised.get("mean"); // obtain each statistical data
        double variance = hist_stats_map_Sturges_noramlised.get("variance");
        double max = hist_stats_map_Sturges_noramlised.get("max");
        double min = hist_stats_map_Sturges_noramlised.get("min");
        double median = hist_stats_map_Sturges_noramlised.get("median");
        double std = hist_stats_map_Sturges_noramlised.get("std");
        System.out.println("[Info] Example Data: "+ exampleData);
        
        String statistics=String.format("[Info] mean: %.2f, variance: %.2f, max: %.2f, min: %.2f, median: %.2f, std: %.2f", mean, variance, max, min, median, std);
        System.out.println(statistics);
        
        
        System.out.println("=============================================================================================================================================================");
        System.out.println("[Info] Sturge's Rule: ");
        System.out.println("bin centre points with Sturge Formulae "+hist_normalised_Sturges.getbinCentre());
        System.out.println("unnormalised histogram with Sturge Formulae: "+hist_unnormalised_Sturges.getHist());
        System.out.println("normalised histogram with Sturge Formulae: "+hist_normalised_Sturges.getHist());
        System.out.println("=============================================================================================================================================================");
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // test SquareRootChoice class 
        SquareRootChoice SquareRootChoiceInstance = new SquareRootChoice(exampleData);
        SquareRootChoiceInstance.calculateNumberOfBins();
        
        UnNormalisedHist hist_unnormalised_sqrtChoice = new UnNormalisedHist(exampleData, SquareRootChoiceInstance.getNumberOfBins());
        hist_unnormalised_sqrtChoice.calculateHist();
        
        NormalisedHist hist_normalised_sqrtChoice = new NormalisedHist(exampleData, SquareRootChoiceInstance.getNumberOfBins());
        hist_normalised_sqrtChoice.calculateHist();

        System.out.println("=============================================================================================================================================================");
        System.out.println("[Info] Square Root Choice: ");
        System.out.println("bin centre points with Square Root Choice "+hist_normalised_sqrtChoice.getbinCentre());
        System.out.println("unnormalised histogram with Square Root Choice: "+hist_unnormalised_sqrtChoice.getHist());
        System.out.println("normalised histogram with Square Root Choice: "+hist_normalised_sqrtChoice.getHist());
        System.out.println("=============================================================================================================================================================");
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        RiceRule RiceRuleInstance = new RiceRule(exampleData);
        RiceRuleInstance.calculateNumberOfBins();
        UnNormalisedHist hist_unnormalised_Rice = new UnNormalisedHist(exampleData, RiceRuleInstance.getNumberOfBins());
        hist_unnormalised_Rice.calculateHist();
        
        NormalisedHist hist_normalised_Rice= new NormalisedHist(exampleData, RiceRuleInstance.getNumberOfBins());
        hist_normalised_Rice.calculateHist();
        System.out.println("=============================================================================================================================================================");
        System.out.println("[Info] Rice Rule: ");
        System.out.println("bin centre points with Rice Rule: "+hist_unnormalised_Rice.getbinCentre());
        System.out.println("unnormalised histogram with Rice Rule: "+hist_unnormalised_Rice.getHist());
        System.out.println("normalised histogram with Rice Rule: "+hist_normalised_Rice.getHist());
        System.out.println("=============================================================================================================================================================");
    }

}
