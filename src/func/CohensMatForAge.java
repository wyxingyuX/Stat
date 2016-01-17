package func;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import utils.FileTool;

public class CohensMatForAge extends Mat{

	public CohensMatForAge(List<String> fspath) {
		super(fspath);
		// TODO Auto-generated constructor stub
	}
	public int[][] getMat(String rater1Path,String rater2Path) throws Exception{
		boolean DEBUG=true;
		int matRowNum=4;
		int matColNum=4;
		int[][] mat=new int[matRowNum][matColNum];
		InputStream is1=new FileInputStream(rater1Path);
		InputStream is2=new FileInputStream(rater2Path);
		Workbook wb1=this.getAdaptWorkbook(is1, FileTool.getFilePostfix(rater1Path));
		Workbook wb2=this.getAdaptWorkbook(is2, FileTool.getFilePostfix(rater2Path));
		Sheet st1=wb1.getSheetAt(0);
		Sheet st2=wb2.getSheetAt(0);


		// 得到总行数
		if(st1.getLastRowNum()!=st2.getLastRowNum()) throw new IllegalArgumentException(rater1Path+"\t"+rater2Path+",Two sheet row num not equal!");
		int rowNum=st2.getLastRowNum()+1;
		Row row1=st1.getRow(0);
		Row row2=st2.getRow(0);
		//if(row1.getPhysicalNumberOfCells()!=row2.getPhysicalNumberOfCells()) throw new IllegalArgumentException(rater1Path+" colNum="+row1.getPhysicalNumberOfCells()+"\t"+rater2Path+"colNum="+row2.getPhysicalNumberOfCells()+",Two sheet col num not equal!");
		//int colNum=row2.getPhysicalNumberOfCells();

		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i < rowNum; i++) {
			row1 = st1.getRow(i);
			row2 = st2.getRow(i);
			int mi=this.getReceiveCateIndex(row1);
			int mj=this.getReceiveCateIndex(row2);
			if(DEBUG){
				if(mi==-1) {
					String logText=rater1Path+ " "+(i+1)+" row don't tag age\n";
					System.out.print(logText);
					this.log(logText);
				}
				if(mj==-1){
					String logText=rater2Path+" "+(i+1)+" row don't tag age\n";
					System.out.print(logText);
					this.log(logText);
				}
			}
			if(mi!=-1&&mj!=-1)
				mat[mi][mj]+=1;
		}
		return mat;
	}

	@Override
	public int[][] getMat() throws Exception {
		// TODO Auto-generated method stub
		if(this.getmMat()==null)
			this.setmMat(this.getMat(this.getFilesPath().get(0), this.getFilesPath().get(1)));
		return this.getmMat();
	}
	protected Workbook getAdaptWorkbook(InputStream is,String fPostfix) throws IOException{
		Workbook wb=null;	
		switch(fPostfix){
		case "xlsx":
			wb=new XSSFWorkbook(is);
			break;
		case "xls":
			wb=new HSSFWorkbook(is);
			break;
		default:
			wb=new XSSFWorkbook(is);   
		}
		return wb;
	}
	protected String getCellStringVallue(Cell cell){
		String str="";
		if(cell!=null)
			str=getStringCellValue(cell);
		return str;
	}

	private  String getStringCellValue(Cell cell) {
		String strCell = "";
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			strCell = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			strCell = String.valueOf(cell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			strCell = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_BLANK:
			strCell = "";
			break;
		default:
			strCell = "";
			break;
		}
		if (strCell.equals("") || strCell == null) {
			return "";
		}
		if (cell == null) {
			return "";
		}
		return strCell;
	}

	protected int getReceiveCateIndex(Row row){
		String tag="1.0";
		int cateIndex=-1;
		//从第4,5，6，7列分别为18-22，28-32，38-42，48-52;
		Cell cell4=row.getCell(3);
		Cell cell5=row.getCell(4);
		Cell cell6=row.getCell(5);
		Cell cell7=row.getCell(6);

		String str0=this.getCellStringVallue(cell4); String str1=this.getCellStringVallue(cell5);String str2=this.getCellStringVallue(cell6);String str3=this.getCellStringVallue(cell7);
		if(str0.equals(tag)) cateIndex=0;
		if(str1.equals(tag)) cateIndex=1;
		if(str2.equals(tag)) cateIndex=2;
		if(str3.equals(tag)) cateIndex=3;
		return cateIndex;
	}

}
