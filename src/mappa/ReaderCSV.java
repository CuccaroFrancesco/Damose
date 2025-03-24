package mappa;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.util.*;

public class ReaderCSV {
    public static void main(String[] args) {
        String csvFile = "data.csv";
        List<String[]> data = new ArrayList<>();
        
        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                data.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
			e.printStackTrace();
		}
        
        // Converti la lista in un array 2D
        String[][] array = new String[data.size()][];
        data.toArray(array);
        
        // Stampa per verificare
        for (String[] row : array) {
            System.out.println(Arrays.toString(row));
        }
    }
}
