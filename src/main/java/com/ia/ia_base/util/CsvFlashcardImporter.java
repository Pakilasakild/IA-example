package com.ia.ia_base.util;

import com.ia.ia_base.models.Flashcard;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class CsvFlashcardImporter {

    private CsvFlashcardImporter() {}

    public static List<Flashcard> loadFlashcards(Path csvPath) throws IOException {
        List<Flashcard> out = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(csvPath, StandardCharsets.UTF_8)) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;

                // skip header: question,answer
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                List<String> cols = parseCsvLine(line);
                if (cols.size() < 2) continue; // or throw

                String question = cols.get(0).trim();
                String answer   = cols.get(1).trim();

                if (question.isEmpty() || answer.isEmpty()) continue;

                out.add(new Flashcard(question, answer));
            }
        }

        return out;
    }

    // Handles commas + quotes (e.g. "a,b", "he said ""hi""")
    private static List<String> parseCsvLine(String line) {
        List<String> cols = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                // escaped quote inside quotes
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    sb.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                cols.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }

        cols.add(sb.toString());
        return cols;
    }
}
