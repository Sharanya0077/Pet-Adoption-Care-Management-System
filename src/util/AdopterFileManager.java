
package util;

import models.Adopter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class AdopterFileManager {

    private String encode(String value) {
        return Base64.getEncoder().encodeToString(
            value.getBytes(StandardCharsets.UTF_8)
        );
    }

    private String decode(String value) {
        return new String(
            Base64.getDecoder().decode(value),
            StandardCharsets.UTF_8
        );
    }

    public void saveAdopters(
            String filename, List<Adopter> adopters)
            throws IOException {

        File file = new File(filename);
        File parent = file.getParentFile();

        if (parent != null && !parent.exists()
                && !parent.mkdirs()) {
            throw new IOException(
                "Could not create directory: " + parent
            );
        }

        try (BufferedWriter writer =
                new BufferedWriter(new FileWriter(file))) {

            for (Adopter adopter : adopters) {
                writer.write(
                    adopter.getUserId() + "|"
                    + encode(adopter.getName()) + "|"
                    + encode(adopter.getEmail()) + "|"
                    + encode(adopter.getPhone())
                );

                writer.newLine();
            }
        }
    }

    public List<Adopter> loadAdopters(String filename)
            throws IOException {

        List<Adopter> adopters = new ArrayList<>();
        File file = new File(filename);

        if (!file.exists()) {
            return adopters;
        }

        try (BufferedReader reader =
                new BufferedReader(new FileReader(file))) {

            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                try {
                    String[] parts = line.split("\\|", -1);

                    if (parts.length != 4) {
                        throw new IllegalArgumentException(
                            "Expected four fields."
                        );
                    }

                    int id = Integer.parseInt(parts[0]);

                    adopters.add(new Adopter(
                        id,
                        decode(parts[1]),
                        decode(parts[2]),
                        decode(parts[3])
                    ));

                } catch (RuntimeException e) {
                    throw new IOException(
                        "Invalid adopter data at line "
                        + lineNumber, e
                    );
                }
            }
        }

        return adopters;
    }
}
