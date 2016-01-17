package interf;

import func.Mat;

public interface EvalKappa {
  public double computeKappa(int[][] mat);
  public double computeKappa(Mat rm) throws Exception;
}
