package net.nivanda.prettifier.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AirportCodeManager {
    public static List<String> detectCodes(String content) {
        final String LETTERS_CAPS = "QWERTYUIOPASDFGHJKLZXCVBNM";
        List<String> codesInContent = new ArrayList<>();
        String[] words = content.split(" ");
        for (String word : words) {
            if (!word.isEmpty()) {
                switch (word.length()) {
                    case 4:
                        if (word.charAt(0) == '#' &&
                            LETTERS_CAPS.indexOf(word.charAt(1)) != -1 &&
                            LETTERS_CAPS.indexOf(word.charAt(2)) != -1 &&
                            LETTERS_CAPS.indexOf(word.charAt(3)) != -1) {

                            codesInContent.add(word);
                        }
                        break;
                    case 5:
                        if (word.charAt(0) == '#' && word.charAt(4) == '\n' &&
                            LETTERS_CAPS.indexOf(word.charAt(1)) != -1 &&
                            LETTERS_CAPS.indexOf(word.charAt(2)) != -1 &&
                            LETTERS_CAPS.indexOf(word.charAt(3)) != -1) {

                            codesInContent.add(word.replace("\n", ""));
                        }
                        if (word.charAt(0) == '*' && word.charAt(1) == '#' &&
                            LETTERS_CAPS.indexOf(word.charAt(2)) != -1 &&
                            LETTERS_CAPS.indexOf(word.charAt(3)) != -1 &&
                            LETTERS_CAPS.indexOf(word.charAt(4)) != -1) {

                            codesInContent.add(word);
                        }
                        break;
                    case 6:
                        if (word.charAt(0) == '#' && word.charAt(1) == '#' &&
                            LETTERS_CAPS.indexOf(word.charAt(2)) != -1 &&
                            LETTERS_CAPS.indexOf(word.charAt(3)) != -1 &&
                            LETTERS_CAPS.indexOf(word.charAt(4)) != -1 &&
                            LETTERS_CAPS.indexOf(word.charAt(5)) != -1) {

                            codesInContent.add(word);
                        }
                        if (word.charAt(0) == '*' && word.charAt(1) == '#' && word.charAt(5) == '\n' &&
                            LETTERS_CAPS.indexOf(word.charAt(2)) != -1 &&
                            LETTERS_CAPS.indexOf(word.charAt(3)) != -1 &&
                            LETTERS_CAPS.indexOf(word.charAt(4)) != -1) {

                            codesInContent.add(word.replace("\n", ""));
                        }
                        break;
                    case 7:
                        if (word.charAt(0) == '#' && word.charAt(1) == '#' && word.charAt(6) == '\n' &&
                            LETTERS_CAPS.indexOf(word.charAt(2)) != -1 &&
                            LETTERS_CAPS.indexOf(word.charAt(3)) != -1 &&
                            LETTERS_CAPS.indexOf(word.charAt(4)) != -1 &&
                            LETTERS_CAPS.indexOf(word.charAt(5)) != -1) {

                            codesInContent.add(word.replace("\n", ""));
                        }
                        if (word.charAt(0) == '*' && word.charAt(1) == '#' && word.charAt(2) == '#' &&
                            LETTERS_CAPS.indexOf(word.charAt(3)) != -1 &&
                            LETTERS_CAPS.indexOf(word.charAt(4)) != -1 &&
                            LETTERS_CAPS.indexOf(word.charAt(5)) != -1 &&
                            LETTERS_CAPS.indexOf(word.charAt(6)) != -1) {

                            codesInContent.add(word);
                        }
                        break;
                    case 8:
                        if (word.charAt(0) == '*' && word.charAt(1) == '#' && word.charAt(2) == '#' && word.charAt(7) == '\n' &&
                            LETTERS_CAPS.indexOf(word.charAt(3)) != -1 &&
                            LETTERS_CAPS.indexOf(word.charAt(4)) != -1 &&
                            LETTERS_CAPS.indexOf(word.charAt(5)) != -1 &&
                            LETTERS_CAPS.indexOf(word.charAt(6)) != -1) {

                            codesInContent.add(word.replace("\n", ""));
                        }
                        break;
                }
            }
        }
        return codesInContent;
    }

    public static String getAirportName(String code, List<String[]> airportLookup, Map<String, Integer> airportLookupIndexMap) {
        code = code.replaceAll("#", "");
        int indexOfReturn = airportLookupIndexMap.get("name");
        if (code.indexOf('*') == 0) {
            indexOfReturn = airportLookupIndexMap.get("municipality");
            code = code.replace("*", "");
        }
        int indexOfCode = airportLookupIndexMap.get("icao_code");
        if (code.length() == 3) indexOfCode = airportLookupIndexMap.get("iata_code");
        for (String[] line : airportLookup) {
            if (line[indexOfCode].equals(code)) {
                return line[indexOfReturn];
            }
        }
        return null;
    }
}