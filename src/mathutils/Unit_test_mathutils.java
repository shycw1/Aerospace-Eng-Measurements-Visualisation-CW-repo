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
import binmethod.SturgesFormula;
import java.util.List;
import statutils.*;
import java.io.*;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import mathutils.DataFitting;

public class Unit_test_mathutils {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  throws FileNotFoundException
    {
        // TODO code application logic here
        List<Double> measurementData = new ArrayList<>();
        
        File file = new File("measurementData.txt");
        Scanner sc = new Scanner(file);

            while (sc.hasNextLine()){
                String line_read = sc.nextLine();
                double line_data = Double.parseDouble(line_read);
                 measurementData.add(line_data);
            }
            System.out.println("[Info] Measurement Data data size: "+measurementData.size());
            //List<Double> exampleData = Arrays.asList(1., 3., 2., 4., 5., 6., 11., 8., 9., 10., 7.);
        SturgesFormula SturgesInstance = new SturgesFormula(measurementData);
        SturgesInstance.calculateNumberOfBins();
        // get the number of bins
        System.out.printf("By Sturges Formula: %d \n", SturgesInstance.getNumberOfBins());
            
        NormalisedHist hist_normalised_Sturges = new NormalisedHist(measurementData, SturgesInstance.getNumberOfBins());
        hist_normalised_Sturges.calculateHist();
        // statically invoke DataFitting class's calculatePDFparameters method
        double[] Sturges_PDF_Para = new DataFitting(hist_normalised_Sturges.getbinCentre(),hist_normalised_Sturges.getHist()).calculatePDFparameters(); 
        System.out.printf("Normalization factor = %f\n",Sturges_PDF_Para[0]);
        System.out.printf("Mean = %f\n",Sturges_PDF_Para[1]);
        System.out.printf("Sigma = %f\n",Sturges_PDF_Para[2]);

    }
    
}
