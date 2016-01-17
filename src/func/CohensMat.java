package func;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import interf.ReadMatFromExcel;
import utils.FileTool;

public class  CohensMat extends Mat {
	public CohensMat(List<String> fspath){
		super(fspath);
	}
	public int[][] getMat() throws Exception{
		if(this.getmMat()==null)
			this.setmMat(this.getMat(this.getFilesPath().get(0)));
		return this.getmMat();
	}
	
	public int[][] getMat(String excelFilePath) throws Exception {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		InputStream is=new FileInputStream(excelFilePath);
		String fPostfix=FileTool.getFilePostfix(excelFilePath);
		
		int matRowNum = 2,matColNum=2;
		int[][] mat=new int[matRowNum][matColNum];
		Workbook wb=null;	
		String receive="��";
		String refuse="����";

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
		Sheet st = wb.getSheetAt(0);
		// �õ�������
		int rowNum = st.getLastRowNum()+1;
		Row row = st.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();
		// ��������Ӧ�ôӵڶ��п�ʼ,��һ��Ϊ��ͷ�ı���
		for (int i = 1; i < rowNum; i++) {
			row = st.getRow(i);
			//�ӵ�2,4�зֱ�Ϊ���������ߵ�������������Ǽ��� ��һ�������� Ϊmat��Row��Ӧ��������
			Cell cell2=row.getCell(1);
			Cell cell4=row.getCell(3);
			String r1Judge=this.getCellStringVallue(cell2);
			String r2Judge=this.getCellStringVallue(cell4);

			if(r1Judge.equals(receive)){
				if(r2Judge.equals(receive)) mat[0][0]+=1;
				else mat[0][1]+=1;
			}
			else{
				if(r2Judge.equals(receive)) mat[1][0]+=1;
				else mat[1][1]+=1;
			}

		}
		return mat;
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

}
