package func;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import interf.EvalKappa;
import interf.ReadMatFromExcel;
import utils.FileTool;

public class FleissKappa implements EvalKappa{
	@Test
    public void test() throws Exception{
		int[][] mat={
				{0,0,0,0,14},
	            {0,2,6,4,2},
	            {0,0,3,5,6},
	            {0,3,9,2,0},
	            {2,2,8,1,1},
	            {7,7,0,0,0},
	            {3,2,6,3,0},
	            {2,5,3,2,2},
	            {6,5,2,1,0},
	            {0,2,2,3,7}
		};
		computeKappa(mat);
    }
	/**
	 * Computes the Kappa value
	 * @param n Number of rating per subjects (number of human raters)
	 * @param mat Matrix[subjects][categories]
	 * @return The Kappa value
	 */
	@Override
	public double computeKappa(int[][] mat) {
		// TODO Auto-generated method stub
		boolean DEBUG=true;
		final int n = checkEachLineCount(mat) ;  // PRE : every line count must be equal to n
		final int N = mat.length ;          
		final int k = mat[0].length ;       

		if(DEBUG) System.out.println(n+" raters.") ;
		if(DEBUG) System.out.println(N+" subjects.") ;
		if(DEBUG) System.out.println(k+" categories.") ;

		// Computing p[]
		float[] p = new float[k] ;
		for(int j=0 ; j<k ; j++)
		{
			p[j] = 0 ;
			for(int i=0 ; i<N ; i++)
				p[j] += mat[i][j] ;
			p[j] /= N*n ;
		}
		if(DEBUG) System.out.println("p = "+Arrays.toString(p)) ;

		// Computing P[]    
		float[] P = new float[N] ;
		for(int i=0 ; i<N ; i++)
		{
			P[i] = 0 ;
			for(int j=0 ; j<k ; j++)
				P[i] += mat[i][j] * mat[i][j] ;
			P[i] = (P[i] - n) / (n * (n - 1)) ;
		}
		if(DEBUG) System.out.println("P = "+Arrays.toString(P)) ;

		// Computing Pbar
		float Pbar = 0 ;
		for(float Pi : P)
			Pbar += Pi ;
		Pbar /= N ;
		if(DEBUG) System.out.println("Pbar = "+Pbar) ;

		// Computing PbarE
		float PbarE = 0 ;
		for(float pj : p)
			PbarE += pj * pj ;
		if(DEBUG) System.out.println("PbarE = "+PbarE) ;

		final float kappa = (Pbar - PbarE)/(1 - PbarE) ;
		if(DEBUG) System.out.println("kappa = "+kappa) ;

		return kappa ;
	}
	/**
	 * Assert that each line has a constant number of ratings
	 * @param mat The matrix checked
	 * @return The number of ratings
	 * @throws IllegalArgumentException If lines contain different number of ratings
	 */
	protected  int checkEachLineCount(int[][] mat)
	{
		int n = 0 ;
		boolean firstLine = true ;

		for(int[] line : mat)
		{
			int count = 0 ;
			for(int cell : line)
				count += cell ;
			if(firstLine)
			{
				n = count ;
				firstLine = false ;
			}
			if(n != count)
				throw new IllegalArgumentException("Line count != "+n+" (n value).") ;
		}
		return n ;
	}
	
	@Override
	public double computeKappa(Mat rm) throws Exception {
		// TODO Auto-generated method stub
		return this.computeKappa(rm.getMat());
	}

}
