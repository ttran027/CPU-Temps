package edu.odu.cs.cs417;

import java.text.DecimalFormat;

/**
 * This is a tool that do the following:
 * 		-solve matrix (solve matrix of equations, get transposed matrix, and get result of matrix multiplication)
 * 		-generate a linear least squares approximation function
 * 		-generate a piecewise linear interpolation  	
 * @author Thinh Tran
 *
 */
public class DataAnalyzer {
	/**
	 * 
	 * This is a tool set that handle matrix mathematics
	 * @author Thinh Tran
	 */
	public static class MatrixSolver {
		
		/**
		 * Solve a two dimensional matrix using Gaussian Elimination method 
		 * @param a the two dimensional array to be solved
		 */
		public static void solve(double[][] a){
			for(int i = 0; i < a.length; ++i) {
				swap_row(a,i,getLargestRow(a,i));
				scale(a,i);
				a[i][i] = 1;
			    eliminate(a,i);
			}
			backsolve(a);
		}
		
		/**
		 * Do the backsolve step of Gaussian Elimination method
		 * @param a the two dimensional array
		 */
		public static void backsolve(double[][] a){
			for(int i = a.length - 1; i >= 1; i--) {
				for(int j = i - 1; j >= 0; j--) {
					double s = a[j][i];
					a[j][i] -= s * a[i][i];
					a[j][a[0].length - 1] -= s * a[i][a[0].length - 1] ;
				}
			}
		}
		
		/**
		 * Do the eliminate step of Gaussian Elimination method
		 * @param a the two dimensional array
		 * @param idx the row to do elimination
		 */
		public static void eliminate(double[][] a, int idx){
			int start_col = 0;
			for(int i = 0; i < a[0].length; ++i) {
				if(a[idx][i] != 0) {
					start_col = i;
					break;
				}
			}
			for(int i = idx + 1; i < a.length; ++i) {
				double s = a[i][start_col];
				for(int j = start_col; j < a[0].length; ++j) {
					a[i][j] = a[i][j] - s * a[idx][j];
				}
			}
		}
		
		/**
		 * Do the scale step of Gaussian Elimination method
		 * @param a the two dimensional array
		 * @param idx the row to do scaling
		 */
		public static void scale(double[][] a, int idx){
			double s = 0;
			for(int i = 0; i <  a[0].length; ++i) {
				if(a[idx][i] != 0) {
					s = a[idx][i];
					break;
				}
			}
			for(int i = 0; i < a[0].length; ++i) {
				a[idx][i] = a[idx][i] / s; 
			}
		}
		
		/**
		 * Find the row that have a largest value accross the specific column
		 * @param a the two dimensional array
		 * @param idx the column to find largest value on
		 * @return the row number that have the largest value
		 */
		public static int getLargestRow(double[][] a, int idx) {
			double max = 0;
			int row = idx;
			for(int i = idx; i < a.length; ++i) {
				if(a[i][idx] > max) {
					max = a[i][idx];
					row = i;
				}
			}
			return row;
		}
		
		/**
		 * Swap two row of the matrix/array
		 * @param a the two dimensional array
		 * @param i the row to be swapped
		 * @param idx the row to be swapped with
		 */
		public static void swap_row(double[][] a, int i, int idx){
			double tmp;
			for(int j = 0; j < a[0].length; ++j) {
				tmp = a[i][j];
				a[i][j] = a[idx][j];
				a[idx][j] = tmp;
			}
		}
		
		/**
		 * Generate the transpose of a matrix
		 * @param before the two dimensional matrix
		 * @return the transpose of input matrix
		 */
		public static double[][] transpose(double[][] before){
			double[][] after = new double[before[0].length][before.length];
			for(int i = 0; i < after.length; ++i) {
				for(int j = 0; j < after[0].length; ++j) {
					after[i][j] = before[j][i];
				}
			}
			return after;
		}
		
		/**
		 * Multiply two matrixes, the first matrix's column must be equal to second matrix's row
		 * @param lhs a two dimensional array with n columns
		 * @param rhs a two dimensional array with n rows
		 * @return the new matrix resulting from multiplication
		 */
		public static double[][] multiply(double[][] lhs, double[][] rhs){
			double[][] result = new double[lhs.length][rhs[0].length];
			for(int i = 0; i < result.length; ++i) {
				for(int j = 0; j < result[0].length; ++j) {
					result[i][j] = 0;
					for(int k = 0; k < lhs[0].length; ++k) {
						result[i][j] += lhs[i][k] * rhs[k][j];
					}
				}
			}
			return result;
		}
		/**
		 * Multiply two matrixes, the first matrix's column must be equal to second matrix's row
		 * This case the second matrix only have 1 column
		 * @param lhs a two dimensional array with n columns
		 * @param rhs a two dimensional array with n rows and 1 column
		 * @return the re matrix resulting from multiplication
		 */
		public static double[] multiply(double[][] lhs, double[] rhs){
			double[] result = new double[lhs.length];
			for(int i = 0; i < result.length; ++i) {
				result[i] = 0;
				for(int j = 0; j < lhs[0].length; ++j) {
					result[i] += lhs[i][j] * rhs[j];
				}
			}
			return result;
		}
}
	
	/**
	 * Product a linear least squares approximation from two sets of data
	 * @param x the two dimensional array as the data over x-axis
	 * @param y the 1 dimensional array as the data over y-axis
	 * @return the string presentation of the least squares approximation function
	 */
	public static String produceLeastSquares(double[][] x, double[] y) {
		double[][] xTx = MatrixSolver.multiply(MatrixSolver.transpose(x), x);
		double[] xTy = MatrixSolver.multiply(MatrixSolver.transpose(x), y);
		double[][] matrix = new double[2][3];
        for(int i = 0; i < matrix.length; ++i) {
           	for(int j = 0; j < xTx[0].length; ++j) {
           		matrix[i][j] = xTx[i][j];
           	}
        }
        for(int i = 0; i < matrix.length; ++i) {
           	matrix[i][matrix[0].length - 1] = xTy[i];
        }
           
        MatrixSolver.solve(matrix);
        DecimalFormat df = new DecimalFormat("#.####");    
        StringBuilder bld = new StringBuilder();
        bld.append(x[0][1] + " <= x < " + x[x.length-1][1] + "; ");
        bld.append("y1 = " + df.format(matrix[0][2]) + " + " + df.format(matrix[1][2]) + "x; least-squares\n");
            
        return bld.toString();
	}
		
	
	/**
	 * Populated the divided-difference table until the order exceeds the number of data points
	 * @param f the divided-difference table
	 * @param order the order to populate
	 */
	private static void interpolate(double[][] f, int order) {
		
		if(order > f.length) {
			return;
		}
		
		for(int i = 0; i < f.length-order; ++i) {
			f[i][order+1] = (f[i+1][order]-f[i][order])/(f[i+order][0] - f[i][0]);   
			
		}
		
		interpolate(f, order+1);
	}
	
	/**
	 * Produce a divided-difference table to an order equal to the number of data points
	 * @param x the two dimensional array as the data over x-axis
	 * @param y the 1 dimensional array as the data over y-axis
	 * @return the populated table 
	 */
	public static double[][] getDivTable(double[][] x, double[] y){
		double[][] divTable = new double[x.length][x.length+1];
		for(int i = 0; i < divTable.length; ++i) {
			divTable[i][0] = x[i][1];
			divTable[i][1] = y[i];
		}
		interpolate(divTable,1);
		return divTable;	
	}
	
	/**
	 * Produce a linear interpolation between points
	 * @param f the divided-difference table
	 * @param p the starting point the interval
	 * @return the string presentation of the linear interpolation
	 */
	public static String produceLinearInterpolation(double[][] f, int p) {
		double b = f[p][1]-(f[p][2]*f[p][0]);
		double a = f[p][2];
		DecimalFormat df = new DecimalFormat("#.####"); 
		StringBuilder bld = new StringBuilder();
        bld.append(f[p][0] + " <= x < " + f[p+1][0] + "; ");
        bld.append("y1 = " + df.format(b) + " + " + df.format(a) + "x; interpolation\n");
            
        return bld.toString();
		
	}
}
