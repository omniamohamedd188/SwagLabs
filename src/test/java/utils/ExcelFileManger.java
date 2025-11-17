package utils;

import net.bytebuddy.asm.Advice;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

public class ExcelFileManger {
Sheet sheet;
    public ExcelFileManger(String filepath ) {
        try {
            FileInputStream    fileInputStream = new FileInputStream(new File(filepath));
            XSSFWorkbook workbook =new XSSFWorkbook(fileInputStream);
            sheet =workbook.getSheet("Sheet1");


        }
        catch (Exception e){
          e.printStackTrace();
        }
    }

    public int getColumncount(){
        try {
            return sheet.getRow(0).getPhysicalNumberOfCells();


        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }
    public int getRowCount() {

        try {
            return sheet.getPhysicalNumberOfRows();


        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public String getCellData(int row, int col) {
        return sheet.getRow(row).getCell(col).toString();
    }
}

