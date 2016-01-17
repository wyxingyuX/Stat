package func;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import interf.ReadMatFromExcel;

public abstract class Mat implements ReadMatFromExcel{
	private int[][] mMat;
	private List<String> filesPath;
	private OutputStreamWriter logOsw;
	public Mat(List<String> fspath){
		this.setFilesPath(fspath);
	}
	public int[][] getmMat() {
		return mMat;
	}
	public void setmMat(int[][] mMat) {
		this.mMat = mMat;
	}
	public List<String> getFilesPath() {
		return filesPath;
	}
	public void setFilesPath(List<String> filesPath) {
		this.filesPath = filesPath;
	}
	public OutputStreamWriter getLogOsw() {
		return logOsw;
	}
	public void setLogOsw(OutputStreamWriter logOsw) {
		this.logOsw = logOsw;
	}
	public void log(String logText) throws IOException{
		if(this.getLogOsw()!=null){
			this.getLogOsw().write(logText);
			this.getLogOsw().flush();
		
		}
	}

}
