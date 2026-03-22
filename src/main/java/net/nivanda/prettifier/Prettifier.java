package net.nivanda.prettifier;

import net.nivanda.prettifier.util.FileIO;

import java.util.List;

public class Prettifier {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("itinerary usage:\n$ java Prettifier.java ./input.txt ./output.txt ./airport-lookup.csv");
        } else if (args[0].equals("-h") && args.length == 1) {
            System.out.println("itinerary usage:\n$ java Prettifier.java ./input.txt ./output.txt ./airport-lookup.csv");
        } else if (args.length < 3) {
            System.out.println("itinerary usage:\n$ java Prettifier.java ./input.txt ./output.txt ./airport-lookup.csv");
        } else {
            List<String[]> testCSV = FileIO.readCsv(args[2]);
            for (String[] line : testCSV) {
                System.out.println(line[5]);
            }
        }
    }
}