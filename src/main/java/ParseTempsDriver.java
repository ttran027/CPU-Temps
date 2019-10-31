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
import static edu.odu.cs.cs417.DataAnalyzer.LeastSquares;

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
        double[][] lhs = {
				{1, 0},
				{1, 1},
				{1, 2},
				{1, 3},
				{1, 4},
				{1, 5},
				{1, 6},
				{1, 7},
				{1, 8},
				{1, 9},
				{1,10}
		};
        double[] rhs = {0, 1, 4, 9, 16, 25, 36 ,49 ,64 , 81, 100}; 
		       
		
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
        		fw.write(DataAnalyzer.LeastSquares.produce(time, coretemps[i]));
        	}catch(IOException exc) {
        		System.out.println("I/O Error: " + exc);
        	}
        }
        
        for (CoreTempReading aReading : allTheTemps) {
        	
        	System.out.println(aReading);
        }
    }
}
