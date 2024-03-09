package de.exxcellent.challenge;

import de.exxcellent.challenge.dataframe.DataFrame;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class DataFrameTest {

    @Test
    void testAddRow() {
        String[] columnNames = {"Header1", "Header2", "Header3"};
        DataFrame df = new DataFrame(columnNames);
        String[] row = {"Value1", "Value2", "Value3"};
        df.addRow(row);

        assertEquals(1, df.getRowCount());
    }

    @Test
    void testAddRowWithWrongLength() {
        String[] columnNames = {"Header1", "Header2", "Header3"};
        DataFrame df = new DataFrame(columnNames);
        String[] row = {"Value1", "Value2"};

        Exception e = assertThrows(IllegalArgumentException.class, () -> df.addRow(row));
        System.out.println(e.getMessage());
        assertTrue(e.getMessage().contains("The row does not match the amount of columns indicated by the column names"));
    }

    private DataFrame prepareDataFrame() {
        String[] columnNames = {"Header1", "Header2", "Header3"};
        DataFrame df = new DataFrame(columnNames);
        String[] row = {"Value1", "Value2", "Value3"};
        df.addRow(row);
        String[] row2 = {"Value4", "Value5", "Value6"};
        df.addRow(row2);
        String[] row3 = {"Value7", "Value8", "Value9"};
        df.addRow(row3);
        return df;
    }

    @Test
    void getColumn() {
        DataFrame df = prepareDataFrame();
        List<String> actual = df.getColumn("Header2");
        List<String> expected = List.of("Value2", "Value5", "Value8");
        assertIterableEquals(expected, actual);
    }

    @Test
    void getColumnDoesntExist() {
        DataFrame df = prepareDataFrame();
        Exception e = assertThrows(IllegalArgumentException.class, () -> df.getColumn("Header4"));
        assertTrue(e.getMessage().contains("Column Header4 does not exist on the DataFrame!"));
    }

    @Test
    void getIndexOfColumnName() {
        DataFrame df = prepareDataFrame();
        int actual = df.getIndexOfColumnName("Header2");
        assertEquals(1, actual);
    }

    @Test
    void getIndexOfColumnNameDoesntExist() {
        DataFrame df = prepareDataFrame();
        int actual = df.getIndexOfColumnName("Header4");
        assertEquals(-1, actual);
    }

    @Test
    void applyOperationToColumns() {
        String[] columnNames = {"Header1", "Header2", "Header3"};
        DataFrame df = new DataFrame(columnNames);
        String[] row = {"1.0", "2.0", "3.0"};
        df.addRow(row);
        String[] row2 = {"2.0", "3.0", "4.0"};
        df.addRow(row2);
        String[] row3 = {"3.0", "6.0", "9.0"};
        df.addRow(row3);

        List<Float> actual = df.applyOperationToColumns(
                "Header2",
                "Header3",
                (a, b) -> Float.parseFloat(a) + Float.parseFloat(b)
        );
        List<Float> expected = List.of(5.0f, 7.0f, 15.0f);
        assertIterableEquals(expected, actual);
    }

    @Test
    void applyOperationToColumnsColumnDoesntExist() {
        String[] columnNames = {"Header1", "Header2", "Header3"};
        DataFrame df = new DataFrame(columnNames);
        String[] row = {"1.0", "2.0", "3.0"};
        df.addRow(row);

        Exception e = assertThrows(IllegalArgumentException.class, () -> df.applyOperationToColumns(
                "Header2",
                "Header4",
                (a, b) -> Float.parseFloat(a) + Float.parseFloat(b)
        ));
        assertTrue(e.getMessage().contains("Column Header4 does not exist on the DataFrame!"));
    }

}
