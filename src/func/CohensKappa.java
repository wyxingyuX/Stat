package func;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

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

public class CohensKappa implements EvalKappa{
	private OutputStreamWriter logOsw; 
	@Override
	public double computeKappa(int[][] mat) {
		boolean DEBUG=false;
		int colNum=mat[0].length;
		int rowNum=mat.length;

		int[] rowSum=new int[rowNum];
		int[] colSum=new int[colNum];
		int digSum=0;
		int total=0;
		for(int i=0;i<rowNum;++i){
			int rsum=0;
			for(int j=0;j<colNum;++j){
				rsum+=mat[i][j];
				total+=mat[i][j];
			}
			rowSum[i]=rsum;
		}

		for(int j=0;j<colNum;++j){
			int csum=0;
			for(int i=0;i<rowNum;++i){
				csum+=mat[i][j];
			}
			colSum[j]=csum;
		}

		for(int i=0;i<rowNum;++i){
			for(int j=0;j<colNum;++j){
				if(i==j) digSum+=mat[i][j];
			}
		}

		int totalColSum=0;
		for(int sum:colSum) totalColSum+=sum;

		int totalRowSum=0;
		for(int sum:rowSum) totalRowSum+=sum;
		if(totalRowSum!=totalColSum) 
			throw new IllegalArgumentException("Two Rater Rate Count not Equal. colRateCount="+totalColSum+",rowRateCount="+totalRowSum);

		int peNumeratorSum=0; 
		for(int i=0;i<rowNum;++i){
			peNumeratorSum+=rowSum[i]*colSum[i];
		}

		double po=(double) (digSum/(total*1.0));
		double pe=(double) (peNumeratorSum/(total*total*1.0));
		if(DEBUG){
			System.out.println("mat=");
			for(int[] r:mat){
				for(int c:r) System.out.print(c+" ");
				System.out.println();
			}
			System.out.print("rowSum=");
			for(int sum:rowSum) System.out.print(sum+" ");
			System.out.print("\n");
			System.out.print("colSum=");
			for(int sum:colSum) System.out.print(sum+" ");
			System.out.print("\n");
			System.out.println("rowNum="+rowNum);
			System.out.println("colNum="+colNum);
			System.out.println("digSum="+digSum);
			System.out.println("total="+total);
			System.out.println("po="+po);
			System.out.println("pe="+pe);
		}
		return (po-pe)/(1-pe);
	}
	@Override
	public double computeKappa(Mat rm) throws Exception {
		// TODO Auto-generated method stub
		double kappa=this.computeKappa(rm.getMat());
		return kappa;
	}

	@Test
	public void test() throws Exception{
		int[][] mat={{22,3},
				     {4,14}};
		System.out.println(computeKappa(mat));
	}
	public OutputStreamWriter getLogOsw() {
		return logOsw;
	}
	public void setLogOsw(OutputStreamWriter logOsw) {
		this.logOsw = logOsw;
	}
	
}
