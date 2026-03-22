package net.nivanda.prettifier.util;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileIO {
    public String readTxt(String path) {
        Path filepath = Paths.get(path);
        try {
            return Files.readString(filepath);
        } catch (IOException e) {
            System.err.println("Input not found");
            return null;
        }
    }

    public void writeTxt(String path, String content) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
            System.err.println("An error occurred while writing output to a file");
            e.printStackTrace();
        }
    }

    public List<String[]> readCsv(String path) {
        try {
            FileReader fileReader = new FileReader(path);
            CSVReader csvReader = new CSVReaderBuilder(fileReader).withSkipLines(1).build();
            List<String[]> data = csvReader.readAll();
            return data;
        } catch (IOException e) {
            System.err.println("Airport lookup not found");
            return null;
        } catch (CsvException e) {
            System.err.println("Something went wrong when reading the CSV file");
            e.printStackTrace();
            return null;
        }
    }
}
