package de.exxcellent.challenge.dataframe;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class CSVReader implements DataFrameReader {
    private static final String SEPARATOR = ",";

    public DataFrame readDataFrame(String path) {
        try (FileReader fr = new FileReader(path); BufferedReader br = new BufferedReader(fr)) {
            String firstLine = br.readLine();
            if (firstLine == null) throw new IOException("The file does not contain any data.");

            String[] columnNames = firstLine.split(SEPARATOR);
            DataFrame dataFrame = new DataFrame(columnNames);

            String line;
            while ((line = br.readLine()) != null) {
                String[] row = line.split(SEPARATOR);
                if (row.length != columnNames.length)
                    throw new IOException("The row does not match the amount of columns indicated by the column names. Column names: %s Row: %s".formatted(Arrays.toString(columnNames), Arrays.toString(row)));
                dataFrame.addRow(row);
            }

            return dataFrame;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
