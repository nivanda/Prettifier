package util;

import java.io.*;
import java.nio.file.*;
import java.util.*;

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
    
    public static AirportLookup readCsv(String path) {
        String LEGAL_CHARACTERS = "QWERTYUIOPASDFGHJKLZXCVBNM" +
                                    "qwertyuiopasdfghjklzxcvbnm" +
                                    "1234567890" +
                                    "!@#$%^&*()-_=+\\|[]{};':\",.<>/?`~ ";
        try {
            List<String[]> csv = new ArrayList<>();
            List<String> lines = Files.readAllLines(Paths.get(path));
            if (lines.isEmpty()) {
                System.err.println("Airport lookup malformed");
                return null;
            }
            Map<String, Integer> map = getCSVColumnOrderMap(lines.getFirst().split(","));
            if (map == null) {
                System.err.println("Airport lookup malformed");
                return null;
            }
            lines.removeFirst();
            for (String line : lines) {
                for (int i = 0; i < line.length(); i++) {
                    if (LEGAL_CHARACTERS.indexOf(line.charAt(i)) == -1) {
                        System.err.println("Airport lookup malformed");
                        return null;
                    }
                    if (line.charAt(i) == ',' && line.charAt(i+1) == ',') {
                        System.err.println("Airport lookup malformed");
                        return null;
                    }
                }
                csv.add(line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"));
            }
            return new AirportLookup(map, csv);
        } catch (IOException e) {
            System.err.println("Airport lookup not found");
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
}
