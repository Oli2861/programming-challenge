package de.exxcellent.challenge;

import de.exxcellent.challenge.dataframe.CSVReader;
import de.exxcellent.challenge.dataframe.DataFrame;
import de.exxcellent.challenge.dataframe.DataFrameReader;

import java.util.List;
import java.util.Optional;

/**
 * The entry class for your solution. This class is only aimed as starting point and not intended as baseline for your software
 * design. Read: create your own classes and packages as appropriate.
 *
 * @author Benjamin Schmid <benjamin.schmid@exxcellent.de>
 */
public final class App {

    /**
     * This is the main entry method of your program.
     *
     * @param args The CLI arguments passed
     */
    public static void main(String... args) {
        String basePath = "./src/main/resources/de/exxcellent/challenge/";
        String weatherPath = basePath + "weather.csv";
        String footballPath = basePath + "football.csv";

        System.out.printf("Day with smallest temperature spread : %s%n", getDayWithSmallestTemperatureSpread(weatherPath));

        System.out.printf("Team with smallest goal spread       : %s%n", getTeamWithSmallestGoalSpread(footballPath));
    }

    private static String getDayWithSmallestTemperatureSpread(String path) {
        DataFrameReader csvReader = new CSVReader();
        DataFrame weatherDataFrame = csvReader.readDataFrame(path);
        List<String> days = weatherDataFrame.getColumn("Day");

        List<Float> difference = weatherDataFrame.applyOperationToColumns(
                "MxT",
                "MnT",
                (mxT, mnT) -> Float.parseFloat(mxT) - Float.parseFloat(mnT)
        );

        Optional<Float> min = difference.stream().min(Float::compareTo);
        if (min.isPresent()) {
            int minIndex = difference.indexOf(min.get());
            return days.get(minIndex);
        }

        throw new IllegalStateException("Failed to find the day with the smallest temperature spread. Might be due to an empty DataFrame or incorrect column names.");
    }

    private static String getTeamWithSmallestGoalSpread(String path) {
        return "";
    }

}
