package net.nivanda.prettifier.util;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.ZonedDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTimeSolver {

    public static String solveDateTime(String content) {
        Pattern pattern = Pattern.compile("(D|T12|T24)\\(([^)]+)\\)");
        Matcher matcher = pattern.matcher(content);
        StringBuilder result = new StringBuilder();
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendOffset("+HH:MM", "+00:00").toFormatter();
        while(matcher.find()) {
            String format = matcher.group(1);
            String date = matcher.group(2);
            try {
                ZonedDateTime zone = ZonedDateTime.parse(date);
                String time;
                switch (format) {
                    case "D":
                        time = zone.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
                        break;
                    case "T12":
                        time = zone.format(DateTimeFormatter.ofPattern("hh:mma")) + " (" + zone.format(formatter) + ")";
                        break;
                    case "T24":
                        time = zone.format(DateTimeFormatter.ofPattern("HH:mm")) + " (" + zone.format(formatter) + ")";
                        break;
                    default:
                        time = matcher.group(0);
                }
                matcher.appendReplacement(result, Matcher.quoteReplacement(time));
            } catch (DateTimeParseException e) {
                matcher.appendReplacement(result, Matcher.quoteReplacement(matcher.group(0)));
            }
        }
        matcher.appendTail(result);
        return result.toString();
    }
}
