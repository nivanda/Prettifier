package net.nivanda.prettifier.util;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileIO {
    public static String readTxt(String path) {
        Path filepath = Paths.get(path);
        try {
            return Files.readString(filepath);
        } catch (IOException e) {
            System.err.println("Input not found");
            return null;
        }
    }

    public static void writeTxt(String path, String content) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
            System.err.println("An error occurred while writing output to a file");
            e.printStackTrace();
        }
    }

    public static List<String[]> readCsv(String path) {
        try {
            FileReader fileReader = new FileReader(path);
            CSVReader csvReader = new CSVReaderBuilder(fileReader).build();
            List<String[]> data = csvReader.readAll();
            csvReader.close();
            return data;
        } catch (IOException e) {
            System.err.println("Airport lookup not found");
            return null;
        } catch (CsvException e) {
            System.err.println("Something went wrong when reading the CSV file");
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, Integer> getCSVColumnOrderMap(String[] keys) {
        Map<String, Integer> OrderMap = new HashMap<>();
        if (Arrays.asList(keys).contains("name") &&
            Arrays.asList(keys).contains("iso_country") &&
            Arrays.asList(keys).contains("municipality") &&
            Arrays.asList(keys).contains("icao_code") &&
            Arrays.asList(keys).contains("iata_code") &&
            Arrays.asList(keys).contains("coordinates")) {

            for (int i = 0; i < keys.length; i++) {
                OrderMap.put(keys[i], i);
            }
            return OrderMap;
        }
        return null;
    }

    public static boolean isCSVCorrupted(List<String[]> csv) {
        final String LEGAL_CHARACTERS = "QWERTYUIOPASDFGHJKLZXCVBNM" +
                                        "qwertyuiopasdfghjklzxcvbnm" +
                                        "1234567890" +
                                        "!@#$%^&*()-_=+\\|[]{};':\",.<>/?`~ " +
                                        "\n\f\r";
        for (String[] line : csv) {
            for (String value : line) {
                for (int i = 0; i < value.length(); i++) {
                    if (LEGAL_CHARACTERS.indexOf(value.charAt(i)) == -1) {
                        System.out.println("Airport lookup malformed");
                        return true;
                    }
                }
                if (value.isEmpty()) {
                    System.out.println("Airport lookup malformed");
                    return true;
                }
            }
        }
        return false;
    }
}
