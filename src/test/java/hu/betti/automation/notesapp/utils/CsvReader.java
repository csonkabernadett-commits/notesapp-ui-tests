package hu.betti.automation.notesapp.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import hu.betti.automation.notesapp.models.Note;

public class CsvReader {

    public static List<Note> readNotes(String filePath) {

        List<Note> notes = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Path.of(filePath));

            // Első sor a fejléc
            for (int i = 1; i < lines.size(); i++) {

                String[] data = lines.get(i).split(",");

                String title = data[0].trim();
                String description = data[1].trim();
                String category = data[2].trim();

                notes.add(new Note(title, description, category));
            }

        } catch (IOException e) {
            throw new RuntimeException("CSV file could not be read: " + filePath, e);
        }

        return notes;
    }
}
	
	

