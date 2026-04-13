import util.*;

import java.util.List;
import java.util.Map;

public class Prettifier {

    final public static boolean BonusContent = false;
    public static String content;
    public static AirportLookup airportLookup;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("itinerary usage:\n$ java Prettifier.java ./input.txt ./output.txt ./airport-lookup.csv");
        } else if (args[0].equals("-h") && args.length == 1) {
            System.out.println("itinerary usage:\n$ java Prettifier.java ./input.txt ./output.txt ./airport-lookup.csv");
        } else if (args.length == 2 && args[0].equals("-linebreakGen") && BonusContent) {
            content = "Line 1: Vertical Tab coming now...\u000BLine 2: Form Feed coming now...\fLine 3: Carriage Return coming now...\rLine 4: Done.";
            FileIO.writeTxt(content, args[1]);
        } else if (args.length < 3) {
            System.out.println("itinerary usage:\n$ java Prettifier.java ./input.txt ./output.txt ./airport-lookup.csv");
        } else {
            content = FileIO.readTxt(args[0]);
            if (content == null) return;
            airportLookup = FileIO.readCsv(args[2]);
            if (airportLookup == null) return;
            for (String[] line : airportLookup.getLookup()) {
                for (String value : line) {
                    System.out.print(value + "|"); 
                }
                System.out.println();
            }
            PrettifierMain();
            FileIO.writeTxt(args[1], content);
        }
    }

    public static void PrettifierMain() {
        content = content.replace("\f", "\n");
        content = content.replace("\r", "\n");
        content = content.replace("\u000B", "\n");
        List<String> codes = AirportCodeManager.detectCodes(content, BonusContent);
        for (String code : codes) {
            String replacementText = AirportCodeManager.getAirportName(code, airportLookup.getLookup(), airportLookup.getMap(), BonusContent);
            if (replacementText != null) content = content.replace(code, replacementText);
        }
        content = DateTimeSolver.solveDateTime(content);
    }
}