package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileTool {
  public static String getFilePostfix(String filePath){
	  File file=new File(filePath);
	  String fname=file.getName();
	  String[] es=fname.split("\\.");
	  return es[es.length-1];
  }
  public static void guaranteeDirExist(String dirPath){
		File dir=new File(dirPath);
		if(!dir.exists()) dir.mkdirs();
	}
	public static void guaranteeFileDirExist(String file){
		File f=new File(file);
		FileTool.guaranteeDirExist(f.getParent());
	}
	public static void glanceFileContent(String filePath,int start,int end) throws IOException{
		BufferedReader br=new BufferedReader(new FileReader(filePath));
		String curLine="";
		int lineNum=0;
		while((curLine=br.readLine())!=null){
			++lineNum;
			if(lineNum>=start&&lineNum<=end) System.out.println(curLine);
			if(lineNum>end) break;
		}
	}
	public static int getFileLineNum(String filePath) throws Exception{
		BufferedReader br=new BufferedReader(new FileReader(filePath));

		String curLine="";
		int lineNum=0;
		while((curLine=br.readLine())!=null){
			++lineNum;
		}
		return lineNum;
	}

	public static String getParentPath(String filePath){
    return FileTool.getParentPath(new File(filePath));
	}
	public static String getParentPath(File file){
      return file.getParent();
	}
}
