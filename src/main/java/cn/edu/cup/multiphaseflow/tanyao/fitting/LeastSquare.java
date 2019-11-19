package cn.edu.cup.multiphaseflow.tanyao.fitting;

import org.ejml.simple.SimpleMatrix;

/**
 * @author tanyao
 * @date 2019/11/4
 */
public class LeastSquare {

    private SimpleMatrix baseVector (double var,
                                     int power) {
        SimpleMatrix value = new SimpleMatrix(power+1, 1);
        double temp = 1;
        for (int i = 0; i < power+1; i++) {
            value.set(i, 0, temp);
            temp *= var;
        }
        return value;
    }



    public static void main(String[] args) {
        LeastSquare leastSquare = new LeastSquare();
        System.out.println(leastSquare.baseVector(2, 1));
    }
}
