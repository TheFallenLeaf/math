package cn.edu.cup.multiphaseflow.tanyao.data;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

/**
 * @author tanyao
 * @date 2019/11/3
 */
public class Input {
    /**
     * 文件名
     */
    private String fileName;

    public Input (String fileName) {
        this.fileName = fileName;
    }

    private XSSFWorkbook getWorkbook () {
        XSSFWorkbook workbook = null;
        File file = new File(fileName);
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(file);
            workbook = new XSSFWorkbook(inputStream);
        } catch (FileNotFoundException e1) {
            System.out.println("找不到文件！");
        } catch (IOException e2) {
            System.out.println("文件打开出错！");
        }
        return workbook;
    }

    private Map getData (XSSFWorkbook workbook) {
        //行数
        int rowCount = workbook.getSheetAt(0).getLastRowNum() + 1;
//        //列数
//        int columnCount = workbook.getSheetAt(0).getPhysicalNumberOfRows();
        //sheet
        Sheet sheet = workbook.getSheetAt(0);

        Map data = new HashMap();

        //去掉标题行
        double[] xi = new double[rowCount-1];
        double[] yi = new double[rowCount-1];
        double[] f_xy = new double[rowCount-1];

        for (int i = 0; i < rowCount-1; i++) {
            xi[i] = sheet.getRow(i+1).getCell(0).getNumericCellValue();
            yi[i] = sheet.getRow(i+1).getCell(1).getNumericCellValue();
            f_xy[i] = sheet.getRow(i+1).getCell(2).getNumericCellValue();
        }

        data.put("xi", xi);
        data.put("yi", yi);
        data.put("f_xy", f_xy);
        return data;
    }

    public Map read () {
        XSSFWorkbook workbook = getWorkbook();
        return getData(workbook);
    }

}
