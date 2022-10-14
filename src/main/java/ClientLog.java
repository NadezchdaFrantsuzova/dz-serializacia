import com.opencsv.CSVWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClientLog {
    protected List<String[]> listShopping = new ArrayList<>();
    protected String[] title = {"productNum, amount"};

    //CSV-файл:
    public void log(int productNum, int amount) {
        listShopping.add(new String[]{
                String.valueOf(productNum),
                String.valueOf(amount)
        });
    }

    public void exportAsCSV(File txtFile) throws IOException {
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(txtFile, true))) {
            csvWriter.writeNext(title);
            for (String[] list : listShopping) {
                csvWriter.writeNext(list);
            }
        }
    }
}
