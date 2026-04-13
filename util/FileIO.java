package util;

// Imports for all the needed functions and shit.
// Not sure if it's okay to import with *.
// Maybe need to change back to single imports.
// If reviewers burn my ass I will know why.
import java.io.*;
import java.nio.file.*;
import java.util.*;

// As you will see my naming schemes are HORRIBLE.
// That's why I am here writing comments at 1AM,
// so you (reviewer) can understand wtf the code I wrote does.
public class FileIO {

    // Reads txt files, duh.
    // It only reads .txt files and nothing else...and is used only once.
    // Note to self: If the input file gets too big,
    // this function may lag the shit of your computer,
    // since it has to load the whole file to a SINGLE string.
    public static String readTxt(String path) {
        Path filepath = Paths.get(path);
        try {
            return Files.readString(filepath);
        } catch (IOException e) {
            System.err.println("Input not found");
            return null;
        }
    }

    // Same as previous.
    // But it can also be used for writing a test input file.
    // Note to self: Have not tested writing huge strings,
    // may crash the shitbox laptop if not careful (kernel panic).
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

    // Oh boy, this was a fucky wucky thing to write.
    // Originally this was just few lines of code.
    // But now it's this piece of spaghetti code.
    // Since I realized I can't run non-compiled code,
    // while also using external packages.
    // So bye bye OpenCSV and welcome yandere dev level code.
    // Also do not question my questionable design choices
    // of using an Object just for returning two values at once.
    // I do not know why I did that either.
    public static AirportLookup readCsv(String path) {
        // WHY DID I MAKE THIS LIKE THAT?! (╯°□°)╯︵ ┻━┻
        String LEGAL_CHARACTERS = "QWERTYUIOPASDFGHJKLZXCVBNM" +
                                    "qwertyuiopasdfghjklzxcvbnm" +
                                    "1234567890" +
                                    "!@#$%^&*()-_=+\\|[]{};':\",.<>/?`~ ";
        try {
            // Preps for the horrible for loop.
            // csv and map variables are the outputs.
            // Note to self, if the airport-loopup.csv file is 500k lines instead of 4k,
            // prepare for kernel panic (aka the shitbox laptop is not surving).
            List<String[]> csv = new ArrayList<>();
            List<String> lines = Files.readAllLines(Paths.get(path));
            Map<String, Integer> map = getCSVColumnOrderMap(lines.getFirst().split(","));
            lines.removeFirst();
            // Iterates over EVERY LINE IN THE DAMN FILE.
            for (String line : lines) {
                // Wow, this naming scheme rocks.....
                String tempLine = line;
                boolean skipComa = false;
                String[] splitLine = new String[6];
                int wordIndex = 0;
                // Iterates over EVERY LETTER in the line...
                // Have fun debugging this....
                for (int i = 0; i < line.length(); i++) {
                    // Everything that isn't in english alphabet will get flagged as "malformed".
                    // That also means that the French habibis and Chinese comrades and can't use this tool.
                    if (LEGAL_CHARACTERS.indexOf(line.charAt(i)) == -1) {
                        System.err.println("Airport lookup malformed");
                        return null;
                    // Checks for empty cells.
                    // My braincells aren't passing this one.
                    } else if (line.charAt(i) == ',' && line.charAt(i + 1) == ',') {
                        System.err.println("Airport lookup malformed");
                        return null;
                    // Crude fix for avoiding splitting at the darn coma in the middle of coordinates value.
                    } else if (line.charAt(i) == ',' && line.charAt(i + 1) == '"'){
                        skipComa = true;
                        // You are going to see these 4 lines a lot more....
                        if (wordIndex > 5) {
                                System.err.println("Airport lookup malformed");
                                return null;
                        }
                        // Aquires the value and the rest of the string using the helper function down bellow.
                        String[] temp = cutStrHere(tempLine, i);
                        splitLine[wordIndex] = temp[0];
                        tempLine = temp[1];
                        wordIndex++;
                    // This will run most of the time.
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
                    // In the end the string will be cut the point where the last value IS the string.
                    // This took an impressive amount of time to figure out....
                    } else if (i++ == line.length()) {
                        if (wordIndex > 5) {
                            System.err.println("Airport lookup malformed");
                            return null;
                        }
                        splitLine[wordIndex] = tempLine;
                    }
                }
                // That's it huh....
                csv.add(splitLine);
            }
            // And here we create the Object to send back.
            // You can probably see how the comments degrade the lower you go.
            // That's because the Monster i drank earlier is loosing it's might over my brain stem.
            // And also probably because I've been listening to the same Cyberpunk 2077 end credits song 
            // for the past 30 minutes and Arasaka has long taken my soul...
            // If you are older folk who doesn't play video games....I am so sorry for making you read all this nonsense....
            AirportLookup out = new AirportLookup(map, csv);
            return out;
        } catch (IOException e) {
            System.err.println("Airport lookup not found");
            return null;
        }
    }

    // This is totally not stolen from StackOverflow...
    // Returns the substring before and after the split point.
    private static String[] cutStrHere(String str, int i) {
        return new String[] {str.substring(0, 1), str.substring(i + 1)};
    }

    // Returns the map for the aiport lookup file.
    // This is to handle such cases where the lookup file has different order for colomns.
    // Also doubles as a filter for lookup files written by the illiterate.
    // That including me since I can't seem to remember to capitalize my darn i's.
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
