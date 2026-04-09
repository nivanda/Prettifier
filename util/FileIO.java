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
            Map<String, Integer> map = getCSVColumnOrderMap(lines.getFirst().split(","));
            lines.removeFirst();
            for (String line : lines) {
                String tempLine = line;
                boolean skipComa = false;
                String[] splitLine = new String[6];
                int wordIndex = 0;
                for (int i = 0; i < line.length(); i++) {
                    if (LEGAL_CHARACTERS.indexOf(line.charAt(i)) == -1) {
                        System.err.println("Airport lookup malformed");
                        return null;
                    } else if (line.charAt(i) == ',' && line.charAt(i + 1) == ',') {
                        System.err.println("Airport lookup malformed");
                        return null;
                    } else if (line.charAt(i) == ',' && line.charAt(i + 1) == '"'){
                        skipComa = true;
                        if (wordIndex > 5) {
                                System.err.println("Airport lookup malformed");
                                return null;
                        }
                        String[] temp = cutStrHere(tempLine, i);
                        splitLine[wordIndex] = temp[0];
                        tempLine = temp[1];
                        wordIndex++;
                    } else if (line.charAt(i) == ',') {
                        if (skipComa) {
                            skipComa = false;
                        } else {
                            if (wordIndex > 5) {
                                System.err.println("Airport lookup malformed");
                                return null;
                            }
                            String[] temp = cutStrHere(tempLine, i);
                            splitLine[wordIndex] = temp[0];
                            tempLine = temp[1];
                            wordIndex++;
                        }
                    } else if (i++ == line.length()) {
                        if (wordIndex > 5) {
                            System.err.println("Airport lookup malformed");
                            return null;
                        }
                        splitLine[wordIndex] = tempLine;
                    }
                }
                csv.add(splitLine);
            }
            AirportLookup out = new AirportLookup(map, csv);
            return out;
        } catch (IOException e) {
            System.err.println("Airport lookup not found");
            return null;
        }
    }

    private static String[] cutStrHere(String str, int i) {
        return new String[] {str.substring(0, 1), str.substring(i + 1)};
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
