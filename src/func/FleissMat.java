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

public class FleissMat extends Mat {

    public FleissMat(List<String> fspath){
    	super(fspath);
    }
    public int[][] getMat() throws Exception{
    	if(this.getmMat()==null)
			this.setmMat(this.getMat(this.getFilesPath().get(0)));
		return this.getmMat();
    }
    
	
	public int[][] getMat(String excelFilePath) throws Exception {
		// TODO Auto-generated method stub
		InputStream is=new FileInputStream(excelFilePath);
		String fPostfix=FileTool.getFilePostfix(excelFilePath);
		Workbook wb=null;
		int matColNum=2;
		String tag="是";
		
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
		// 得到总行数
		int rowNum = st.getLastRowNum()+1;
		Row row = st.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();
		int[][] mat=new int[rowNum-1][matColNum];
		int raterCount=3;
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i < rowNum; i++) {
			row = st.getRow(i);
			//从第二列开始，第一列为uid
			for(int j=1;j<colNum;++j){
				Cell cell=row.getCell(j);
				if(cell!=null){
					String str=cell.getStringCellValue();
					if(str!=null&&str.equals(tag)){
						mat[i-1][0]= (raterCount+1-j);
						mat[i-1][1]= (j-1);
					}
				}
			}	
		}
		is.close();
		return mat;
	}

}
