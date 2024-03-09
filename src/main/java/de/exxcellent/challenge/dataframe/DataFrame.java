package de.exxcellent.challenge.dataframe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataFrame {

    private final String[] columnNames;
    private List<String[]> rows;

    public DataFrame(String[] columnNames) {
        this.columnNames = columnNames;
        this.rows = new ArrayList<>();
    }

    public void addRow(String[] row) throws IllegalArgumentException {
        if (row.length != columnNames.length)
            throw new IllegalArgumentException("The row does not match the amount of columns indicated by the column names. Column names: %s Row: %s".formatted(Arrays.toString(columnNames), Arrays.toString(row)));
        this.rows.add(row);
    }

    @Override
    public String toString() {
        String delimiter = "|";
        int maxLength = 0;

        maxLength = Math.max(maxLength, maxLengthOfRow(columnNames));
        for (String[] row : this.rows)
            maxLength = Math.max(maxLength, maxLengthOfRow(row));

        StringBuilder sb = new StringBuilder();
        sb.append(rowToString(columnNames, maxLength));
        for (String[] row : this.rows) {
            sb.append("\n");
            sb.append(rowToString(row, maxLength));
        }
        return sb.toString();
    }

    private String rowToString(String[] row, int maxLength) {
        String delimiter = "|";
        StringBuilder sb = new StringBuilder();
        sb.append(delimiter);
        for (int col = 0; col < row.length; col++) {
            sb.append(String.format("%-" + maxLength + "s", row[col]));
            sb.append(delimiter);
        }
        return sb.toString();
    }

    private int maxLengthOfRow(String[] row) {
        int maxLength = 0;
        for (String s : row) {
            int currLength = s.length();
            maxLength = Math.max(currLength, maxLength);
        }
        return maxLength;
    }


}