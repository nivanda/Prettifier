package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AirportCodeManager {
    public static String detectAndReplaceCodes(String content, List<String[]> lookup, Map<String, Integer> map, boolean bonusContent) {
        Pattern pattern;
        if (bonusContent) {
            pattern = Pattern.compile("[*^~]?#{1,2}[A-Z]{3,4}");
        } else {
            pattern = Pattern.compile("[*]?#{1,2}[A-Z]{3,4}");
        }
        Matcher matcher = pattern.matcher(content);
        StringBuilder out = new StringBuilder();
        while (matcher.find()) {
            String replacement = getAirportName(matcher.group(), lookup, map, bonusContent);
            if (replacement == null) continue;
            matcher.appendReplacement(out, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(out);
        return out.toString();
    }

    public static String getAirportName(String code, List<String[]> airportLookup, Map<String, Integer> airportLookupIndexMap, boolean bonusContent) {
        int indexOfReturn = airportLookupIndexMap.get("name");
        if (bonusContent) {
            switch (code.charAt(0)) {
                case '*':
                    indexOfReturn = airportLookupIndexMap.get("municipality");
                    code = code.replace("*", "");
                    break;
                case '^':
                    indexOfReturn = airportLookupIndexMap.get("iso_country");
                    code = code.replace("^", "");
                    break;
                case '~':
                    indexOfReturn = airportLookupIndexMap.get("coordinates");
                    code = code.replace("~", "");
                    break;
            }
        }
        if (code.charAt(0) == '*') {
            indexOfReturn = airportLookupIndexMap.get("municipality");
            code = code.replace("*", "");
        }
        boolean borked = false;
        if (code.length() - code.replace("#", "").length() == 1 && code.length() - code.replaceAll("A-Z", "").length() == 4) borked = true;
        if (code.length() - code.replace("#", "").length() == 2 && code.length() - code.replaceAll("A-Z", "").length() == 3) borked = true;
        if (borked) return null;
        code = code.replace("#", "");
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