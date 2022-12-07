/**
 * Sample Skeleton for 'GUI_FXML.fxml' Controller Class
 */

//20123739
package gui_driver;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.chart.LineChart;
import javafx.stage.FileChooser;
import java.io.FileNotFoundException;
import javafx.stage.*;
import binmethod.*;
import statutils.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.image.WritableImage;
import javafx.scene.SnapshotParameters;
import javafx.embed.swing.SwingFXUtils;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Collections;
import java.lang.Math;
import mathutils.DataFitting; // [Info] inside this class, an external commons-math library from apache was included as demonstrated in the mathutils package
// This library was provided on moodel to be used and was manually linked to this NetBeans project.
// the libraries were located in the work directory of this project - exactly can be found at .../library/*.jar

// [info] measurementData.txt was also located in the work directory of this project and can be found.

public class GUI_FXMLController{
    
    // User defined code
    private List<Double> dataPoints; // vaiable that's defined by user
    private int numberOfBins;
    HashMap<String, Double> StatParameters;
    private List<Double> hist_Y;
    private List<Double> hist_X;
    private double PDFCentre;
    private double PDFnormCoef;
    private double PDFWidth;
    /// User end

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="MeanTextDisplay"
    private TextField MeanTextDisplay; // Value injected by FXMLLoader

    @FXML // fx:id="MedianTextDisplay"
    private TextField MedianTextDisplay; // Value injected by FXMLLoader

    @FXML // fx:id="VarianceTextDisplay"
    private TextField VarianceTextDisplay; // Value injected by FXMLLoader

    @FXML // fx:id="STDTextDisplay"
    private TextField STDTextDisplay; // Value injected by FXMLLoader

    @FXML // fx:id="CalculateStatsButton"
    private Button CalculateStatsButton; // Value injected by FXMLLoader

    @FXML // fx:id="FileLocationDisplay"
    private TextField FileLocationDisplay; // Value injected by FXMLLoader

    @FXML // fx:id="FileLocatorButton"
    private Button FileLocatorButton; // Value injected by FXMLLoader

    @FXML // fx:id="BinMethodSelection"
    private ComboBox<String> BinMethodSelection; // Value injected by FXMLLoader

    @FXML // fx:id="PlotButton"
    private Button PlotButton; // Value injected by FXMLLoader

    @FXML // fx:id="BarChartMap"
    private BarChart<Double, Double> BarChartMap; // Value injected by FXMLLoader

    @FXML // fx:id="BinNumTextBox"
    private TextField BinNumTextBox; // Value injected by FXMLLoader

    @FXML // fx:id="CoefTextBox"
    private TextField CoefTextBox; // Value injected by FXMLLoader

    @FXML // fx:id="CentreTextBox"
    private TextField CentreTextBox; // Value injected by FXMLLoader

    @FXML // fx:id="WidthTextBox"
    private TextField WidthTextBox; // Value injected by FXMLLoader
    @FXML // fx:id="SaveButton"
    private Button SaveButton; // Value injected by FXMLLoader
    @FXML // fx:id="LineChart"
    private LineChart<Number,Number> LineChart; // Value injected by FXMLLoader


    @FXML
    void FileLocatorClicked(ActionEvent event) throws FileNotFoundException { // on click file locator button
        if(event.getSource()==FileLocatorButton){
            System.out.println("[Info] File Locator Button Clicked");
            
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open File Dialog");
            Stage stage = (Stage)FileLocatorButton.getScene().getWindow(); // opena file locator dialogue window

            File selectedFile = fileChooser.showOpenDialog(stage);
            FileLocationDisplay.setText(selectedFile.getPath());  // store the selected file path
            List<Double> measurementData = new ArrayList<>();
            Scanner sc = new Scanner(selectedFile);

            while (sc.hasNextLine()){                             // read the data in the file and store the data in a java double list
                String line_read = sc.nextLine();
                double line_data = Double.parseDouble(line_read);
                 measurementData.add(line_data);
            }
            this.dataPoints = measurementData;  
            System.out.println(measurementData.size());  // find out the data size
            BinMethodSelection.getSelectionModel().selectFirst(); // the defualt method is square root choice
            SquareRootChoice SquareRootChoiceInstance = new SquareRootChoice(this.dataPoints);  
            SquareRootChoiceInstance.calculateNumberOfBins(); 
            this.numberOfBins = SquareRootChoiceInstance.getNumberOfBins();
            
            NormalisedHist hist_normalised = new NormalisedHist(this.dataPoints, this.numberOfBins); 
            this.StatParameters = hist_normalised.getStatisticalFigures(); // calcculate statistical figures 
            hist_normalised.calculateHist(); // calculate normalised histogram with sqrt method
            this.hist_Y = hist_normalised.getHist();  // initialise the histogram
            this.hist_X = hist_normalised.getbinCentre(); // inistilase the bin centre point list
            
        }
    }

    @FXML
    void PlotButtonClicked(ActionEvent event) {
        if(event.getSource()==PlotButton){
            System.out.println("[Info] Plot & Save Button Clicked!");
            BarChartMap.getData().clear(); // always clear the existing diagram first
            XYChart.Series dataSeriesBarchart = new  XYChart.Series();
            dataSeriesBarchart.setName("Measurements Histogram");
            for(int i = 0; i < this.numberOfBins ; i++){
                String histx = String.format("%.10f", this.hist_X.get(i));
                dataSeriesBarchart.getData().add(new XYChart.Data(histx, this.hist_Y.get(i)));
            }
            BarChartMap.getData().add(dataSeriesBarchart); // finish updating the histogram
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // centre with + or - 6 times of the width as the maximum and minimum boundary
            double lineChart_min = this.PDFCentre - 6*this.PDFWidth; // set the boundaries for the PDF function
            double lineChart_max = this.PDFCentre + 6*this.PDFWidth;
            int lineChartSize = 50; // the PDF function plotted based on 50 points
            double lineChartResoluation = (double)(lineChart_max-lineChart_min)/lineChartSize; // calculate the width of different points on the x axis
            List<Double> lineChartx =  new ArrayList<>(Collections.nCopies(lineChartSize, 0.0));
            List<Double> lineCharty =  new ArrayList<>(Collections.nCopies(lineChartSize, 0.0));
            for(int i = 0; i < lineChartSize; i++){
                double PDF_X = lineChart_min+(i-1)*lineChartResoluation;
                lineChartx.set(i,PDF_X);
                double PDF_y = this.PDFnormCoef*Math.exp(-0.5*((PDF_X-this.PDFCentre)/this.PDFWidth)*((PDF_X-this.PDFCentre)/this.PDFWidth));
                
                lineCharty.set(i,PDF_y);
            }

            LineChart.getData().clear(); // clear the previously existing PDF.
            XYChart.Series dataSeriesLineChart = new  XYChart.Series();
            dataSeriesLineChart.setName("PDF Line Chart");
            for(int i = 0; i < lineChartSize; i++){
                String linx = String.format("%.10f", lineChartx.get(i));
                dataSeriesLineChart.getData().add(new XYChart.Data(linx, lineCharty.get(i)));
            }
            LineChart.setCreateSymbols(false);
            LineChart.getData().add(dataSeriesLineChart); // finish updating the PDF chart

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            
            
            
        }
    }
    @FXML
    void SaveButtonClicked(ActionEvent event) {
        
        if(event.getSource()==SaveButton){
            WritableImage barchartimage = BarChartMap.snapshot(new SnapshotParameters(), null);
	
        // TODO: probably use a file chooser here
            File Barchartfile = new File("BarChart.png"); 

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(barchartimage, null), "png", Barchartfile); // save histogram
            System.out.println("[Info] barchart saved!");
                } catch (IOException e) {
            // TODO: handle exception here
            System.out.println("[Info] barchart saving failed!");
            }  
        
        WritableImage linechartimage = LineChart.snapshot(new SnapshotParameters(), null);
	
        // TODO: probably use a file chooser here
            File Linechartfile = new File("LineChart.png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(linechartimage, null), "png", Linechartfile); // save PDF
            System.out.println("[Info] line chart saved!");
                } catch (IOException e) {
            // TODO: handle exception here
            System.out.println("[Info] line chart saving failed!");
            }
        
        }
        
    }

    @FXML
    void StatsButtonClicked(ActionEvent event) {
        if(event.getSource()==CalculateStatsButton){
            String bin_method = (String) BinMethodSelection.getValue();
            
            switch(bin_method) { // caculate different bin number based on different selected bin method.
                case "Sturge's formula":
                    System.out.println("[Info] Calculate Statistics Button Clicked - bin method: "+bin_method); 
                    SturgesFormula SturgesInstance = new SturgesFormula(this.dataPoints);  // create an instance for this particular method
                    SturgesInstance.calculateNumberOfBins();
                    this.numberOfBins = SturgesInstance.getNumberOfBins();
                  // code block
                  break;
                case "Rice rule formula":
                  // code block
                    System.out.println("[Info] Calculate Statistics Button Clicked - bin method: "+bin_method);
                    RiceRule RiceRuleInstance = new RiceRule(this.dataPoints);
                    RiceRuleInstance.calculateNumberOfBins();
                    this.numberOfBins =  RiceRuleInstance.getNumberOfBins();
                  break;
                default:
                    System.out.println("[Info] Calculate Statistics Button Clicked - bin method: "+bin_method);
                    SquareRootChoice SquareRootChoiceInstance = new SquareRootChoice(this.dataPoints); 
                    SquareRootChoiceInstance.calculateNumberOfBins(); 
                    this.numberOfBins = SquareRootChoiceInstance.getNumberOfBins();
                  // code block
            }
            NormalisedHist hist_normalised = new NormalisedHist(this.dataPoints, this.numberOfBins);
            this.StatParameters = hist_normalised.getStatisticalFigures(); // calculate statistical figures and store them in a hashmap (dictionary) for easy access
            hist_normalised.calculateHist();
            this.hist_Y = hist_normalised.getHist();
            this.hist_X = hist_normalised.getbinCentre();
            double std, mean, variance, median;
            std = this.StatParameters.get("std");
            mean = this.StatParameters.get("mean");
            variance = this.StatParameters.get("variance");
            median = this.StatParameters.get("median");
            STDTextDisplay.setText(String.format("%.9f", std));
            MeanTextDisplay.setText(String.format("%.9f", mean));
            VarianceTextDisplay.setText(String.format("%.9f", variance));
            MedianTextDisplay.setText(String.format("%.9f", median));
            BinNumTextBox.setText(String.format("%d",  this.numberOfBins));
            this.hist_Y = hist_normalised.getHist();
            this.hist_X = hist_normalised.getbinCentre();
            
            double[] PDF_Para = new DataFitting(this.hist_X,this.hist_Y).calculatePDFparameters(); // calculate PDF parameters
            this.PDFnormCoef = PDF_Para[0];
            this.PDFCentre = PDF_Para[1];
            this.PDFWidth = PDF_Para[2];
            CoefTextBox.setText(String.format("%.9f", PDFnormCoef));
            CentreTextBox.setText(String.format("%.9f", PDFCentre));
            WidthTextBox.setText(String.format("%.9f", PDFWidth));
        }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert MeanTextDisplay != null : "fx:id=\"MeanTextDisplay\" was not injected: check your FXML file 'GUI_FXML.fxml'.";
        assert MedianTextDisplay != null : "fx:id=\"MedianTextDisplay\" was not injected: check your FXML file 'GUI_FXML.fxml'.";
        assert VarianceTextDisplay != null : "fx:id=\"VarianceTextDisplay\" was not injected: check your FXML file 'GUI_FXML.fxml'.";
        assert STDTextDisplay != null : "fx:id=\"STDTextDisplay\" was not injected: check your FXML file 'GUI_FXML.fxml'.";
        assert CalculateStatsButton != null : "fx:id=\"CalculateStatsButton\" was not injected: check your FXML file 'GUI_FXML.fxml'.";
        assert FileLocationDisplay != null : "fx:id=\"FileLocationDisplay\" was not injected: check your FXML file 'GUI_FXML.fxml'.";
        assert FileLocatorButton != null : "fx:id=\"FileLocatorButton\" was not injected: check your FXML file 'GUI_FXML.fxml'.";
        assert BinMethodSelection != null : "fx:id=\"BinMethodSelection\" was not injected: check your FXML file 'GUI_FXML.fxml'.";
        assert PlotButton != null : "fx:id=\"PlotButton\" was not injected: check your FXML file 'GUI_FXML.fxml'.";
        assert BarChartMap != null : "fx:id=\"BarChartMap\" was not injected: check your FXML file 'GUI_FXML.fxml'.";
        assert LineChart != null : "fx:id=\"LineChart\" was not injected: check your FXML file 'GUI_FXML.fxml'.";
        assert BinNumTextBox != null : "fx:id=\"BinNumTextBox\" was not injected: check your FXML file 'GUI_FXML.fxml'.";
        assert CoefTextBox != null : "fx:id=\"CoefTextBox\" was not injected: check your FXML file 'GUI_FXML.fxml'.";
        assert CentreTextBox != null : "fx:id=\"CentreTextBox\" was not injected: check your FXML file 'GUI_FXML.fxml'.";
        assert WidthTextBox != null : "fx:id=\"WidthTextBox\" was not injected: check your FXML file 'GUI_FXML.fxml'.";
        assert SaveButton != null : "fx:id=\"SaveButton\" was not injected: check your FXML file 'GUI_FXML.fxml'.";

        
        BinMethodSelection.getItems().addAll("Square root choice formula","Sturge's formula",
                                     "Rice rule formula");
        BinMethodSelection.getSelectionModel().selectFirst();
        
    }
}
