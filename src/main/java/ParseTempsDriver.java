import java.util.List;

import edu.odu.cs.cs417.DataAnalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

import static edu.odu.cs.cs417.TemperatureParser.CoreTempReading;
import static edu.odu.cs.cs417.TemperatureParser.parseRawTemps;


/**
 * A simple command line test driver for TemperatureParser.
 */
public class ParseTempsDriver {

    /**
     * The main function used to demonstrate the TemperatureParser class.
     *
     * @param args used to pass in a single filename
     */
    public static void main(String[] args)
    {
        BufferedReader tFileStream = null;

        // Parse command line argument 1
        try {
            tFileStream = new BufferedReader(new FileReader(new File(args[0])));
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array out of bound: " + e);
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found: " + e);
        }
        
        
        double[][] x = {
        			{1, 1},
        			{1, 2},
        			{1, 3},
        			{1, 4}
        			};
        double[] y = {6, 9, 2, 5};		
        double [][] tab = DataAnalyzer.getDivTable(x, y);
        for(int i = 0; i < x.length-1;++i) {
        	System.out.print(DataAnalyzer.produceLinearInterpolation(tab, i));
        }
               
        
        List<CoreTempReading> allTheTemps = parseRawTemps(tFileStream);
        double[][] coretemps = new double[allTheTemps.get(0).readings.length][allTheTemps.size()];
        double[][] time = new double[allTheTemps.size()][2];
        
        for(int i = 0; i < coretemps.length; ++i) {
        	for(int j = 0; j < coretemps[0].length; ++j) {
        		coretemps[i][j] = allTheTemps.get(j).readings[i];
        	}
        }
        
        for(int i = 0; i < time.length; ++i) {
        	time[i][0] = 1;
        	time[i][1] = allTheTemps.get(i).step;
        }
         
        
        String filename = args[0].substring(0, args[0].lastIndexOf("."));
        for(int i = 0; i < coretemps.length; ++i) {
        	try (FileWriter fw = new FileWriter(filename + "-core-" + i + ".txt"))
        	{
        		fw.write(DataAnalyzer.produceLeastSquares(time, coretemps[i]));
        		double[][] table = DataAnalyzer.getDivTable(time, coretemps[i]);
        		for(int k = 0; k < time.length-1; ++k) {
        			fw.write(DataAnalyzer.produceLinearInterpolation(table, k));
        		}
        		
        	}catch(IOException exc) {
        		System.out.println("I/O Error: " + exc);
        	}
        }
        
    }
}
