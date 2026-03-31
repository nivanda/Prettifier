package net.nivanda.prettifier;

import net.nivanda.prettifier.util.FileIO;
import net.nivanda.prettifier.util.AirportCodeManager;

import java.util.List;
import java.util.Map;

public class Prettifier {

    final boolean BonusContent = false;

    public static void main(String[] args) {
        String content;
        List<String[]> airportLookup;
        Map<String, Integer> orderMap;

        if (args.length == 0) {
            System.out.println("itinerary usage:\n$ java Prettifier.java ./input.txt ./output.txt ./airport-lookup.csv");
        } else if (args[0].equals("-h") && args.length == 1) {
            System.out.println("itinerary usage:\n$ java Prettifier.java ./input.txt ./output.txt ./airport-lookup.csv");
        } else if (args.length < 3) {
            System.out.println("itinerary usage:\n$ java Prettifier.java ./input.txt ./output.txt ./airport-lookup.csv");
        } else {
            content = FileIO.readTxt(args[0]);
            if (content == null) return;
            airportLookup = FileIO.readCsv(args[2]);
            if (airportLookup == null) return;
            if (FileIO.isCSVCorrupted(airportLookup)) return;
            orderMap = FileIO.getCSVColumnOrderMap(airportLookup.getFirst());
            airportLookup.removeFirst();
            PrettifierMain(content, airportLookup, orderMap);
        }
    }

    public static void PrettifierMain(String content, List<String[]> airportLookup, Map<String, Integer> orderMap) {

    }
}