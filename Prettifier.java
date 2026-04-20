import util.*;

import java.util.*;
import java.util.regex.*;

public class Prettifier {

    // Change this to true to enable bonus features.
    final public static boolean BonusContent = false;
    public static String content;
    public static AirportLookup airportLookup;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("itinerary usage:\n$ java Prettifier.java ./input.txt ./output.txt ./airport-lookup.csv");
        } else if (args[0].equals("-h") && args.length == 1) {
            System.out.println("itinerary usage:\n$ java Prettifier.java ./input.txt ./output.txt ./airport-lookup.csv");
        } else if (args.length == 2 && args[0].equals("-linebreakGen") && BonusContent) {
            content = "Line 1: Vertical Tab coming now...\u000B\u000B\u000BLine 2: Form Feed coming now...\fLine 3: Carriage Return coming now...\rLine 4: Done.";
            FileIO.writeTxt(args[1], content);
        } else if (args.length < 3) {
            System.out.println("itinerary usage:\n$ java Prettifier.java ./input.txt ./output.txt ./airport-lookup.csv");
        } else {
            content = FileIO.readTxt(args[0]);
            if (content == null) return;
            airportLookup = FileIO.readCsv(args[2]);
            if (airportLookup == null) return;
            PrettifierMain();
            FileIO.writeTxt(args[1], content);
        }
    }

    public static void PrettifierMain() {
        content = content.replace("\f", "\n");
        content = content.replace("\r", "\n");
        content = content.replace("\u000B", "\n");
        Pattern pattern = Pattern.compile("\n{3,}");
        Matcher matcher = pattern.matcher(content);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(result, Matcher.quoteReplacement("\n\n"));
        }
        matcher.appendTail(result);
        content = result.toString();
        content = DateTimeSolver.solveDateTime(content);
        content = AirportCodeManager.detectAndReplaceCodes(content, airportLookup.getLookup(), airportLookup.getMap(), BonusContent);
    }
}