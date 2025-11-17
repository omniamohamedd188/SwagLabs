package utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CSVFileManger {

    Iterable<CSVRecord> csvRecord;

    public CSVFileManger(String csvfilepath) {

        try {
            FileReader fileReader = new FileReader(csvfilepath);

            CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(fileReader);
            csvRecord = csvParser.getRecords();
        }
        catch (Exception e) {

            e.printStackTrace();

        }

    }

    public List<String[]> getRow() {
        List<String[]> rows = new ArrayList<>();
        try {
            for (CSVRecord record : csvRecord) {
                String[] data = new String[record.size()];
                for (int i = 0; i < data.length; i++) {
                    data[i] = record.get(i);

                }
                rows.add(data);

            }
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
        return rows;


    }
}