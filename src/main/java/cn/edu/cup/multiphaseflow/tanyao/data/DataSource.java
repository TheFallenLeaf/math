package cn.edu.cup.multiphaseflow.tanyao.data;

import java.util.Map;

/**
 * @author tanyao
 * @date 2019/11/4
 */
public class DataSource {
    private double[] xi;
    private double[] yi;
    private double[] f_xy;

    public void init(Map data) {
        setXi((double[]) data.get("xi"));
        setYi((double[]) data.get("yi"));
        setF_xy((double[]) data.get("f_xy"));
    }

    public double[] getXi() {
        return xi;
    }

    public void setXi(double[] xi) {
        this.xi = xi;
    }

    public double[] getYi() {
        return yi;
    }

    public void setYi(double[] yi) {
        this.yi = yi;
    }

    public double[] getF_xy() {
        return f_xy;
    }

    public void setF_xy(double[] f_xy) {
        this.f_xy = f_xy;
    }
}
