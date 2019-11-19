package cn.edu.cup.multiphaseflow.tanyao;

import cn.edu.cup.multiphaseflow.tanyao.data.DataSource;
import cn.edu.cup.multiphaseflow.tanyao.data.Input;

import java.util.Map;

/**
 * @author tanyao
 * @date 2019/11/18
 */
public class Test {
    public static void main(String[] args) {
        DataSource dataSource = new DataSource();
        Input input = new Input("src/main/resources/data.xlsx");
        Map map = input.read();
        dataSource.init(map);
        System.out.println();
    }
}
