package cn.edu.cup.multiphaseflow.tanyao.fitting;

import Jama.Matrix;
import Jama.SingularValueDecomposition;
import org.openxmlformats.schemas.drawingml.x2006.main.CTRegularTextRun;

/**
 * @author tanyao
 * @date 2019/11/20
 */
public class LeastSquare {
    /**
     * 线性空间x的最高次项
     */
    private int p = 2;

    /**
     * 线性空间y的最高次项
     */
    private int q = 2;

    //测试数据
    private int point = 6;
    private double[] xi = {1.1, 1.2, 1.3, 1.4, 1.5, 1.6};
    private double[] yi = {0.5, 0.6, 0.7, 0.8, 0.9, 1};
    private double[] fxy = {1.46, 1.8, 2.18, 2.6, 3.06, 3.56};

    private Matrix gramMatrix () {
        //实对称矩阵,可以优化
        int dimension = (p + 1) * (q + 1);
        double[][] value = new double[dimension][dimension];
//        String[][] var = new String[dimension][dimension];
        //行
        for (int i = 0; i < dimension; i++) {
            //列
            for (int j = 0; j < dimension; j++) {
                int xPower1 = j / (p+1);
                int yPower1 = j % (p+1);
                int xPower2 = i / (p+1);
                int yPower2 = i % (p+1);
//                var[i][j] = String.valueOf(xPower1) + String.valueOf(yPower1) + String.valueOf(xPower2) + String.valueOf(yPower2);
                value[i][j] = scalar1(xPower1, yPower1, xPower2, yPower2);
            }
        }
        return new Matrix(value);
    }

    private Matrix fMatrix () {
        int dimension = (p + 1) * (q + 1);
        double[][] value = new double[dimension][1];
//        String[][] var = new String[dimension][1];
        for (int i = 0; i < dimension; i++) {
            int xPower = i / (p+1);
            int yPower = i % (p+1);
            value[i][0] = scalar2(xPower, yPower);
//            var[i][0] = String.valueOf(xPower) + String.valueOf(yPower);
        }
        return new Matrix(value);
    }

    private double scalar1 (int xPower1, int yPower1,
                            int xPower2, int yPower2) {
        double value = 0;
        for (int i = 0; i < 6; i++) {
            double temp = Math.pow(xi[i], xPower1+xPower2)
                        * Math.pow(yi[i], yPower1+yPower2);
            value += temp;
        }
        return value;
    }

    private double scalar2 (int xPower, int yPower) {
        double value = 0;
        for (int i = 0; i < point; i++) {
            double temp = fxy[i]
                        * Math.pow(xi[i], xPower)
                        * Math.pow(yi[i], yPower);
            value += temp;
        }
        return value;
    }

    public Matrix baseVector (double var) {
        Matrix value = new Matrix(p+1, 1);
        double temp = 1;
        for (int i = 0; i < p + 1; i++) {
            value.set(i, 0, temp);
            temp *= var;
        }
        return value;
    }

    public Matrix run () {
        Matrix G = gramMatrix();
        Matrix F = fMatrix();
        SingularValueDecomposition svd = new SingularValueDecomposition(G);
        Matrix singular = svd.getS();
        Matrix value = svd.getV();
        Matrix decomposition = svd.getU();
        for (int i = 0; i < singular.getColumnDimension(); i++) {
            if (singular.get(i, i) < 0.000001) {
                singular.set(i, i, 0);
            }
            else {
                double s = 1.0 / singular.get(i, i);
                singular.set(i, i, s);
            }
        }
        Matrix C = value.times(singular).times(decomposition.transpose()).times(F);

        Matrix coefficient = new Matrix(p+1, q+1);
        for (int i = 0; i < C.getRowDimension(); i++) {
            int m = i % (p+1);
            int n = i / (p+1);
//            System.out.println(C.get(i,0));
            coefficient.set(n, m, C.get(i, 0));
        }
        return coefficient;
    }

    public static void main(String[] args) {
        LeastSquare leastSquare = new LeastSquare();
        Matrix temp = leastSquare.run();
//        temp.print(1, 5);
        double x = 1.25;
        double y = 0.75;
        Matrix fxy = leastSquare.baseVector(1.25).transpose().times(temp).times(leastSquare.baseVector(0.75));
        fxy.print(1, 4);
        System.out.println();
    }
}
