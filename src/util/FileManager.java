
package util;

import models.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.Base64;

public class FileManager {

    private void ensureDirectoryExists(String fileName)
            throws IOException {
        Path parent = Paths.get(fileName).getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
    }

    private String encode(String value) {
        return Base64.getEncoder().encodeToString(
            value.getBytes(StandardCharsets.UTF_8)
        );
    }

    private String decode(String value) throws IOException {
        try {
            return new String(
                Base64.getDecoder().decode(value),
                StandardCharsets.UTF_8
            );
        } catch (IllegalArgumentException e) {
            throw new IOException("Invalid text in saved data.", e);
        }
    }

    private List<String> readLines(String fileName)
            throws IOException {
        Path path = Paths.get(fileName);
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }
        return Files.readAllLines(path, StandardCharsets.UTF_8);
    }

    private void writeLines(String fileName, List<String> lines)
            throws IOException {
        ensureDirectoryExists(fileName);
        Files.write(
            Paths.get(fileName),
            lines,
            StandardCharsets.UTF_8,
            StandardOpenOption.CREATE,
            StandardOpenOption.TRUNCATE_EXISTING
        );
    }

    private String[] fields(String line, int expected)
            throws IOException {
        String[] result = line.split("\\|", -1);
        if (result.length != expected) {
            throw new IOException("Invalid saved record: " + line);
        }
        return result;
    }

    private int number(String value) throws IOException {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IOException("Invalid number in saved data.", e);
        }
    }

    private Pet findPet(List<Pet> pets, int id) throws IOException {
        for (Pet pet : pets) {
            if (pet.getPetId() == id) {
                return pet;
            }
        }
        throw new IOException("Saved record refers to missing pet ID " + id);
    }

    // Generic text storage
    public void saveData(String fileName, String data)
            throws IOException {
        ensureDirectoryExists(fileName);
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(data);
        }
    }

    public String loadData(String fileName) throws IOException {
        StringBuilder data = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line).append(System.lineSeparator());
            }
        }
        return data.toString();
    }

    // PETS
    public void savePets(String fileName, List<Pet> pets)
            throws IOException {
        List<String> lines = new ArrayList<>();

        for (Pet pet : pets) {
            String type;
            String extra;

            if (pet instanceof Dog) {
                type = "Dog";
                extra = ((Dog) pet).getTrainingLevel();
            } else if (pet instanceof Cat) {
                type = "Cat";
                extra = String.valueOf(((Cat) pet).isIndoorOnly());
            } else if (pet instanceof Bird) {
                type = "Bird";
                extra = String.valueOf(((Bird) pet).canFly());
            } else {
                throw new IOException("Unknown pet subtype.");
            }

            lines.add(type + "|" + pet.getPetId() + "|" +
                encode(pet.getName()) + "|" + pet.getAge() + "|" +
                encode(pet.getBreed()) + "|" + encode(pet.getGender()) +
                "|" + pet.isAvailable() + "|" + pet.isAdopted() +
                "|" + encode(extra));
        }

        writeLines(fileName, lines);
    }

    public List<Pet> loadPets(String fileName) throws IOException {
        List<Pet> pets = new ArrayList<>();

        for (String line : readLines(fileName)) {
            String[] f = fields(line, 9);
            int id = number(f[1]);
            String name = decode(f[2]);
            int age = number(f[3]);
            String breed = decode(f[4]);
            String gender = decode(f[5]);
            boolean available = Boolean.parseBoolean(f[6]);
            boolean adopted = Boolean.parseBoolean(f[7]);
            String extra = decode(f[8]);

            Pet pet;
            switch (f[0]) {
                case "Dog":
                    pet = new Dog(id, name, age, breed, gender,
                                  available, extra);
                    break;
                case "Cat":
                    pet = new Cat(id, name, age, breed, gender,
                                  available, Boolean.parseBoolean(extra));
                    break;
                case "Bird":
                    pet = new Bird(id, name, age, breed, gender,
                                   available, Boolean.parseBoolean(extra));
                    break;
                default:
                    throw new IOException("Unknown pet type: " + f[0]);
            }

            if (adopted) {
                pet.adopt();
            }
            pets.add(pet);
        }
        return pets;
    }

    // ADOPTION APPLICATIONS
    public void saveApplications(String fileName,
            List<AdoptionApplication> applications) throws IOException {
        List<String> lines = new ArrayList<>();

        for (AdoptionApplication a : applications) {
            Adopter u = a.getAdopter();
            lines.add(a.getApplicationId() + "|" + u.getUserId() +
                "|" + encode(u.getName()) + "|" + encode(u.getEmail()) +
                "|" + encode(u.getPhone()) + "|" +
                a.getPet().getPetId() + "|" +
                encode(a.getApplicationDate()) + "|" +
                encode(a.getStatus()));
        }
        writeLines(fileName, lines);
    }

    public List<AdoptionApplication> loadApplications(
            String fileName, List<Pet> pets) throws IOException {
        List<AdoptionApplication> result = new ArrayList<>();

        for (String line : readLines(fileName)) {
            String[] f = fields(line, 8);
            Adopter adopter = new Adopter(
                number(f[1]), decode(f[2]), decode(f[3]), decode(f[4])
            );
            AdoptionApplication a = new AdoptionApplication(
                number(f[0]), adopter, findPet(pets, number(f[5])),
                decode(f[6])
            );
            a.setStatus(decode(f[7]));
            result.add(a);
        }
        return result;
    }

    // MEDICAL RECORDS
    public void saveMedicalRecords(String fileName,
            List<MedicalRecord> records) throws IOException {
        List<String> lines = new ArrayList<>();

        for (MedicalRecord r : records) {
            lines.add(r.getRecordId() + "|" + r.getPet().getPetId() +
                "|" + encode(r.getDiagnosis()) + "|" +
                encode(r.getTreatment()) + "|" + encode(r.getRecordDate()));
        }
        writeLines(fileName, lines);
    }

    public List<MedicalRecord> loadMedicalRecords(
            String fileName, List<Pet> pets) throws IOException {
        List<MedicalRecord> result = new ArrayList<>();

        for (String line : readLines(fileName)) {
            String[] f = fields(line, 5);
            result.add(new MedicalRecord(
                number(f[0]), findPet(pets, number(f[1])),
                decode(f[2]), decode(f[3]), decode(f[4])
            ));
        }
        return result;
    }

    // VACCINATIONS
    public void saveVaccinations(String fileName,
            List<Vaccination> vaccinations) throws IOException {
        List<String> lines = new ArrayList<>();

        for (Vaccination v : vaccinations) {
            lines.add(v.getVaccinationId() + "|" +
                v.getPet().getPetId() + "|" + encode(v.getVaccineName()) +
                "|" + encode(v.getVaccinationDate()) + "|" +
                encode(v.getNextDueDate()));
        }
        writeLines(fileName, lines);
    }

    public List<Vaccination> loadVaccinations(
            String fileName, List<Pet> pets) throws IOException {
        List<Vaccination> result = new ArrayList<>();

        for (String line : readLines(fileName)) {
            String[] f = fields(line, 5);
            result.add(new Vaccination(
                number(f[0]), findPet(pets, number(f[1])),
                decode(f[2]), decode(f[3]), decode(f[4])
            ));
        }
        return result;
    }

    // APPOINTMENTS
    public void saveAppointments(String fileName,
            List<Appointment> appointments) throws IOException {
        List<String> lines = new ArrayList<>();

        for (Appointment a : appointments) {
            Vet v = a.getVet();
            lines.add(a.getAppointmentId() + "|" +
                a.getPet().getPetId() + "|" + v.getUserId() +
                "|" + encode(v.getName()) + "|" + encode(v.getEmail()) +
                "|" + encode(v.getPhone()) + "|" +
                encode(v.getSpecialization()) + "|" +
                encode(a.getAppointmentDate()) + "|" +
                encode(a.getReason()) + "|" + encode(a.getStatus()));
        }
        writeLines(fileName, lines);
    }

    public List<Appointment> loadAppointments(
            String fileName, List<Pet> pets) throws IOException {
        List<Appointment> result = new ArrayList<>();

        for (String line : readLines(fileName)) {
            String[] f = fields(line, 10);
            Vet vet = new Vet(
                number(f[2]), decode(f[3]), decode(f[4]),
                decode(f[5]), decode(f[6])
            );
            Appointment a = new Appointment(
                number(f[0]), findPet(pets, number(f[1])), vet,
                decode(f[7]), decode(f[8])
            );
            a.setStatus(decode(f[9]));
            result.add(a);
        }
        return result;
    }
}
