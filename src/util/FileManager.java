
package util;

import models.Dog;
import models.Cat;
import models.Bird;
import models.Pet;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {

    public void saveData(String fileName, String data)
            throws IOException {

                ensureDirectoryExists(fileName);

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(data);
        }
    }

    public String loadData(String fileName)
            throws IOException {

        StringBuilder data = new StringBuilder();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(fileName))) {

            String line;

            while ((line = reader.readLine()) != null) {
                data.append(line)
                    .append(System.lineSeparator());
            }
        }

        return data.toString();
    }

    public void savePets(String fileName, List<Pet> pets)
            throws IOException {
                ensureDirectoryExists(fileName);

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(fileName))) {

            for (Pet pet : pets) {

                String type = pet instanceof Dog ? "Dog"
                            : pet instanceof Cat ? "Cat"
                            : pet instanceof Bird ? "Bird"
                            : "Pet";

                String extra = "";

                if (pet instanceof Dog) {
                    extra = ((Dog) pet).getTrainingLevel();
                } else if (pet instanceof Cat) {
                    extra = String.valueOf(
                        ((Cat) pet).isIndoorOnly()
                    );
                } else if (pet instanceof Bird) {
                    extra = String.valueOf(
                        ((Bird) pet).canFly()
                    );
                }

                writer.write(
                    type + "|" +
                    pet.getPetId() + "|" +
                    pet.getName() + "|" +
                    pet.getAge() + "|" +
                    pet.getBreed() + "|" +
                    pet.getGender() + "|" +
                    pet.isAvailable() + "|" +
                    pet.isAdopted() + "|" +
                    extra
                );

                writer.newLine();
            }
        }
    }

    public List<Pet> loadPets(String fileName)
            throws IOException {

        List<Pet> pets = new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(fileName))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] fields = line.split("\\|", -1);

                if (fields.length != 9) {
                    throw new IOException(
                        "Invalid pet record: " + line
                    );
                }

                String type = fields[0];
                int id = Integer.parseInt(fields[1]);
                String name = fields[2];
                int age = Integer.parseInt(fields[3]);
                String breed = fields[4];
                String gender = fields[5];
                boolean available =
                    Boolean.parseBoolean(fields[6]);
                boolean adopted =
                    Boolean.parseBoolean(fields[7]);
                String extra = fields[8];

                Pet pet;

                switch (type) {
                    case "Dog":
                        pet = new Dog(
                            id, name, age, breed, gender,
                            available, extra
                        );
                        break;

                    case "Cat":
                        pet = new Cat(
                            id, name, age, breed, gender,
                            available, Boolean.parseBoolean(extra)
                        );
                        break;

                    case "Bird":
                        pet = new Bird(
                            id, name, age, breed, gender,
                            available, Boolean.parseBoolean(extra)
                        );
                        break;

                    case "Pet":
                        throw new IOException(
                            "Cannot load an abstract Pet directly."
                        );

                    default:
                        throw new IOException(
                            "Unknown pet type: " + type
                        );
                }

                if (adopted) {
                    pet.adopt();
                }

                pets.add(pet);
            }
        } catch (NumberFormatException e) {
            throw new IOException(
                "Invalid number in pet data.", e
            );
        }

        return pets;
    }
    private void ensureDirectoryExists(String fileName)
        throws IOException {

    Path path = Paths.get(fileName);
    Path parent = path.getParent();

    if (parent != null) {
        Files.createDirectories(parent);
    }
}
}
