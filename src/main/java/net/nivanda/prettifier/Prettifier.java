package net.nivanda.prettifier;

public class Prettifier {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("itinerary usage:\n$ java Prettifier.java ./input.txt ./output.txt ./airport-lookup.csv");
            return;
        } else if (args[0].equals("-h") && args.length == 1) {
            System.out.println("itinerary usage:\n$ java Prettifier.java ./input.txt ./output.txt ./airport-lookup.csv");
            return;
        } else if (args.length < 3) {
            System.out.println("itinerary usage:\n$ java Prettifier.java ./input.txt ./output.txt ./airport-lookup.csv");
            return;
        }
    }
}