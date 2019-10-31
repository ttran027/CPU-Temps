package edu.odu.cs.cs417;

public class DataAnalyzer {
	public static class MatrixSolver {
	
		public static double[][] solve(double[][] a){
			for(int i = 0; i < a.length; ++i) {
				a = swap_row(a,i,getLargestRow(a,i));
				a = scale(a,i);
				a[i][i] = 1;
			    a = eliminate(a,i);
			}
			return backsolve(a);
		}
		
		public static double[][] backsolve(double[][] a){
			for(int i = a.length - 1; i >= 1; i--) {
				for(int j = i - 1; j >= 0; j--) {
					double s = a[j][i];
					a[j][i] -= s * a[i][i];
					a[j][a[0].length - 1] -= s * a[i][a[0].length - 1] ;
				}
			}
			return a;
		}
		
		public static double[][] eliminate(double[][] a, int idx){
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
			return a;
		}
		
		public static double[][] scale(double[][] a, int idx){
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
			return a;
		}
		
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
		
		
		public static double[][] swap_row(double[][] a, int i, int idx){
			double tmp;
			for(int j = 0; j < a[0].length; ++j) {
				tmp = a[i][j];
				a[i][j] = a[idx][j];
				a[idx][j] = tmp;
			}
			return a;
		}
		public static double[][] transpose(double[][] before){
			double[][] after = new double[before[0].length][before.length];
			for(int i = 0; i < after.length; ++i) {
				for(int j = 0; j < after[0].length; ++j) {
					after[i][j] = before[j][i];
				}
			}
			return after;
		}
		
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

	public static class LeastSquares{
		public static String produce(double[][] x, double[] y) {
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
           
            double[][] result = MatrixSolver.solve(matrix);
            
            StringBuilder bld = new StringBuilder();
            bld.append(x[0][1] + " <= x < " + x[x.length-1][1] + "; ");
            bld.append("y = " + result[0][2] + " + " + result[1][2] + "x; least-squares");
            
            return bld.toString();
		}
		
	}
	
	public static class Interpolation{
		
	}
	
}
