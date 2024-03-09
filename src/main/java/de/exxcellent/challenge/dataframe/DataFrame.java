package de.exxcellent.challenge.dataframe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public class DataFrame {

    private final String[] columnNames;
    private List<String[]> rows;

    public DataFrame(String[] columnNames) {
        this.columnNames = columnNames;
        this.rows = new ArrayList<>();
    }

    /**
     * Add a row to the DataFrame.
     * @param row The row to add.
     * @throws IllegalArgumentException If the length of the row does not match the amount of columns of the DataFrame.
     */
    public void addRow(String[] row) throws IllegalArgumentException {
        if (row.length != columnNames.length)
            throw new IllegalArgumentException("The row does not match the amount of columns indicated by the column names. Column names: %s Row: %s".formatted(Arrays.toString(columnNames), Arrays.toString(row)));
        this.rows.add(row);
    }

    /**
     * Get a column of the DataFrame as a list.
     * @param columName The name of the desired column.
     * @return The column as a list.
     */
    public List<String> getColumn(String columName) {
        int colIndex = getIndexOfColumnName(columName);

        if (colIndex == -1)
            throw new IllegalArgumentException(String.format("Column %s does not exist on the DataFrame!", columName));

        return rows
                .stream()
                .map(arr -> arr[colIndex])
                .toList();
    }

    /**
     * Apply some operation element-wise to two columns and get the list of results returned.
     * @param columnName List of the first column.
     * @param columnName1 List of the second column.
     * @param operation The operation to apply to the columns.
     * @return The result obtained by applying the operation to the columns.
     * @throws IllegalArgumentException If a column was not found.
     */
    public List<Float> applyOperationToColumns(
            String columnName,
            String columnName1,
            BiFunction<String, String, Float> operation
    ) throws IllegalArgumentException {

        int colIndex = getIndexOfColumnName(columnName);
        int colIndex1 = getIndexOfColumnName(columnName1);

        if (colIndex == -1)
            throw new IllegalArgumentException(String.format("Column %s does not exist on the DataFrame!", columnName));
        if (colIndex1 == -1)
            throw new IllegalArgumentException(String.format("Column %s does not exist on the DataFrame!", columnName1));

        return rows
                .stream()
                .map(arr -> operation.apply(arr[colIndex], arr[colIndex1]))
                .toList();
    }

    /**
     * Get the index of the column with the provided name.
     * @param name The name of the column.
     * @return The index of the column.
     */
    private int getIndexOfColumnName(String name) {
        for (int i = 0; i < columnNames.length; i++) {
            if (name.equals(columnNames[i]))
                return i;
        }
        return -1;
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