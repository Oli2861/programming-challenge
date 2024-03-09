package de.exxcellent.challenge;

import de.exxcellent.challenge.dataframe.CSVReader;
import de.exxcellent.challenge.dataframe.DataFrame;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class CSVReaderTests {

    @Test
    void testReadCSV() {
        CSVReader subject = new CSVReader();
        DataFrame actual = subject.readDataFrame("./src/main/resources/de/exxcellent/challenge/weather.csv");

        String[] expectedColumnNames = {
                "Day", "MxT", "MnT", "AvT", "AvDP", "1HrP TPcpn",
                "PDir", "AvSp", "Dir", "MxS", "SkyC", "MxR", "Mn", "R AvSLP"
        };

        assertArrayEquals(expectedColumnNames, actual.getColumnNames());
    }

    @Test
    void testReadDoesNotExist() {
        CSVReader subject = new CSVReader();
        Exception e = assertThrows(RuntimeException.class, () -> subject.readDataFrame("does_not_exist.csv"));
        assertTrue(e.getMessage().contains("The system cannot find the file specified"));
    }

    @Test
    void testReadEmptyCSV() {
        CSVReader subject = new CSVReader();
        Exception e = assertThrows(RuntimeException.class, () -> subject.readDataFrame("./src/test/resources/de/exxcellent/challenge/empty.csv"));
        assertTrue(e.getMessage().contains("The file does not contain any data."));
    }

    @Test
    void testNoData() {
        CSVReader subject = new CSVReader();
        DataFrame actual = subject.readDataFrame("./src/test/resources/de/exxcellent/challenge/no_entries.csv");

        String[] expectedColumnNames = {
                "Day", "MxT", "MnT", "AvT", "AvDP", "1HrP TPcpn",
                "PDir", "AvSp", "Dir", "MxS", "SkyC", "MxR", "Mn", "R AvSLP"
        };

        assertArrayEquals(expectedColumnNames, actual.getColumnNames());
        assertEquals(0, actual.getRowCount());
    }

}
