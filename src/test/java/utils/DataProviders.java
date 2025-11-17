package utils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.testng.annotations.DataProvider;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class DataProviders {

    @DataProvider(name = "loginCSV")
    public Object[][] loginData() {
        CSVFileManger csv = new CSVFileManger("src/test/resources/logindata.csv");
        List<String[]> rows = csv.getRow();

        Object[][] data = new Object[rows.size()][2];
        for (int i = 0; i < rows.size(); i++) {
            data[i][0] = rows.get(i)[0]; // username
            data[i][1] = rows.get(i)[1]; // password
        }
        return data;
    }

    @DataProvider(name = "checkoutDataExcel")
    public Object[][] checkoutData() {
        ExcelFileManger excel = new ExcelFileManger("src/test/resources/New Microsoft Excel Worksheet.xlsx");

        int rows = excel.getRowCount();
        int cols = excel.getColumncount();

        Object[][] data = new Object[rows-1][cols];

        for (int i = 1; i <rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i-1][j] = excel.getCellData(i,j);
            }
        }
        return data;
    }


    @DataProvider(name = "loginJSON")
    public Object[][] getLoginDataFromJson() throws IOException {
        Gson gson = new Gson();


        Type listType = new TypeToken<List<logindatajson>>() {}.getType();


        FileReader reader = new FileReader("src/test/resources/data.json");
        List<logindatajson> dataList = gson.fromJson(reader, listType);
        reader.close();

        // تحويل الـ List إلى Object[][] عشان يناسب TestNG
        Object[][] data = new Object[dataList.size()][2];
        for (int i = 0; i < dataList.size(); i++) {
            data[i][0] = dataList.get(i).getUsername();
            data[i][1] = dataList.get(i).getPassword();
        }

        return data;
    }
}





