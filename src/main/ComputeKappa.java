package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import func.CohensKappa;
import func.CohensMat;
import func.CohensMatForAge;
import func.CohensMatForGender;
import func.FleissKappa;
import func.FleissMat;
import func.Mat;
import interf.EvalKappa;
import interf.ReadMatFromExcel;
import utils.FileTool;

public class ComputeKappa {
	public static void computeCohhensKappa() throws Exception{
		List<String> fsPath=new LinkedList<String>();
		fsPath.add("F:\\ExpData\\Spam\\DataFromOther\\qty\\test\\ExpandID0_Normal12-孙继丰.xlsx");
		fsPath.add("F:\\ExpData\\Spam\\DataFromOther\\qty\\test\\ExpandID0_Normal12-张晨燕.xlsx");
		Mat rm=new CohensMatForGender(fsPath);
		EvalKappa ek=new CohensKappa();
		System.out.println(ek.computeKappa(rm));
	}
	public static void copmputeFleissKappa() throws Exception{
		List<String> fsPath=new LinkedList<String>();
		fsPath.add("F:\\ExpData\\Spam\\DataFromOther\\qty\\垃圾用户微博\\SpammerAll_combined.xlsx");
		Mat rm=new FleissMat(fsPath);
		EvalKappa ek=new FleissKappa();
		System.out.println(ek.computeKappa(rm));

	}
	public static void computeBatchCohensKappaForGender() throws Exception{
		String base="F:\\ExpData\\Spam\\DataFromOther\\qty\\年龄性别标注数据\\";
		List<File> fileList=new ArrayList<File>();
		for(File f:new File(base).listFiles()){
			fileList.add(f);
		}
		String result="F:\\ExpData\\Spam\\DataFromOther\\qty\\"+"年龄性别标注数据_gender_result.txt";
		FileTool.guaranteeFileDirExist(result);
		OutputStreamWriter osw=new OutputStreamWriter(new FileOutputStream(result),"UTF-8");
		EvalKappa ek=new CohensKappa();
		double sumKappa=0;
		int count=0;
		for(int i=0;i<fileList.size()-1;i+=2){
			++count;
			File f1=fileList.get(i);
			File f2=fileList.get(i+1);
			List<String> fsPath=new LinkedList<String>();
			fsPath.add(f1.getAbsolutePath());
		    fsPath.add(f2.getAbsolutePath());
		    Mat rm=new CohensMatForGender(fsPath);
		    rm.setLogOsw(osw);
		    double cohensKappa=ek.computeKappa(rm);
		    sumKappa+=cohensKappa;
		    saveResult(osw,cohensKappa,f1.getAbsolutePath(),f2.getAbsolutePath());    
		}
		osw.write("Avg CohenssKappa="+sumKappa/count);
		osw.flush();
		osw.close();
	}
	public static void computeBatchCohensKappaForAge() throws Exception{
		String base="F:\\ExpData\\Spam\\DataFromOther\\qty\\年龄性别标注数据\\";
		List<File> fileList=new ArrayList<File>();
		for(File f:new File(base).listFiles()){
			fileList.add(f);
		}
		String result="F:\\ExpData\\Spam\\DataFromOther\\qty\\"+"年龄性别标注数据_age_result.txt";
		FileTool.guaranteeFileDirExist(result);
		OutputStreamWriter osw=new OutputStreamWriter(new FileOutputStream(result),"UTF-8");
		EvalKappa ek=new CohensKappa();
		double sumKappa=0;
		int count=0;
		for(int i=0;i<fileList.size()-1;i+=2){
			++count;
			File f1=fileList.get(i);
			File f2=fileList.get(i+1);
			List<String> fsPath=new LinkedList<String>();
			fsPath.add(f1.getAbsolutePath());
		    fsPath.add(f2.getAbsolutePath());
		    Mat rm=new CohensMatForAge(fsPath);
		    rm.setLogOsw(osw);
		    double cohensKappa=ek.computeKappa(rm);
		    sumKappa+=cohensKappa;
		    saveResult(osw,cohensKappa,f1.getAbsolutePath(),f2.getAbsolutePath());    
		}
		osw.write("Avg CohenssKappa="+sumKappa/count);
		osw.flush();
		osw.close();
	}
	public static void saveResult(OutputStreamWriter osw,double kappa,String f1,String f2) throws IOException{
		osw.write(f1+"\t"+f2+"\t"+"CohensKappa="+kappa+"\r\n");
		osw.write("------------------------------------------------------------------------------------------------------\n");
		osw.flush();
	}
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//computeCohhensKappa();
		//copmputeFleissKappa();
		//computeBatchCohensKappaForGender();
		//computeBatchCohensKappaForAge();
	}

}
