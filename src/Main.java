import exceptions.PetNotFoundException;
import models.Adopter;
import models.AdoptionApplication;
import models.Dog;
import models.Pet;
import models.ShelterAdmin;
import service.AdoptionService;
import service.PetService;
import models.Appointment;
import models.MedicalRecord;
import models.Vaccination;
import models.Vet;
import service.MedicalService;
import util.FileManager;
import java.io.IOException;
import java.util.List;
public class Main {

    public static void main(String[] args) {

PetService petService = new PetService();
FileManager fileManager = new FileManager();

Pet dog;

try {
    List<Pet> savedPets = fileManager.loadPets("data/pets.txt");

    if (!savedPets.isEmpty()) {
        petService.setPets(savedPets);
        dog = savedPets.get(0);

        System.out.println("Saved pets loaded successfully.");
    } else {
        dog = new Dog(
            101,
            "Buddy",
            2,
            "Golden Retriever",
            "Male",
            true,
            "Basic"
        );

        petService.addPet(dog);
        System.out.println("Demo pet created.");
    }

} catch (IOException e) {
    dog = new Dog(
        101,
        "Buddy",
        2,
        "Golden Retriever",
        "Male",
        true,
        "Basic"
    );

    petService.addPet(dog);
    System.out.println("No saved pet data found. Demo pet created.");
}

        Adopter adopter = new Adopter(
            1,
            "Sharanya",
            "sharanya@example.com",
            "9876543210"
        );

        ShelterAdmin admin = new ShelterAdmin(
            2,
            "Shelter Admin",
            "admin@example.com",
            "9999999999"
        );

        AdoptionService adoptionService =
                new AdoptionService(petService);

try {

    if (!dog.isAvailable() || dog.isAdopted()) {

        System.out.println("\n--- Adoption Unavailable ---");
        System.out.println(
            dog.getName() +
            " has already been adopted or is unavailable."
        );

    } else {

        AdoptionApplication application =
            adoptionService.createApplication(
                1001,
                adopter,
                dog,
                "09-10-2026"
            );

        if (application != null) {

            application.displayApplication();

            System.out.println("\n--- Admin Review ---");

            admin.approveApplication(application);
            adoptionService.approveApplication(application);

            System.out.println("\n--- Final Pet Status ---");

            System.out.println(
                "Pet available: " + dog.isAvailable()
            );

            System.out.println(
                "Pet adopted: " + dog.isAdopted()
            );
        }
    }

} catch (PetNotFoundException e) {

    System.out.println("Error: " + e.getMessage());
}

        System.out.println("\n========== MEDICAL & CARE ==========");

MedicalService medicalService = new MedicalService();

Vet vet = new Vet(
    3,
    "Mehta",
    "vet@example.com",
    "8888888888",
    "Veterinary Medicine"
);

MedicalRecord record = new MedicalRecord(
    501,
    dog,
    "Minor skin infection",
    "Topical medication for 7 days",
    "09-10-2026"
);

Vaccination vaccination = new Vaccination(
    601,
    dog,
    "Rabies",
    "09-10-2026",
    "09-10-2027"
);

Appointment appointment = new Appointment(
    701,
    dog,
    vet,
    "10-10-2026",
    "Routine check-up"
);

medicalService.addMedicalRecord(record);
medicalService.addVaccination(vaccination);
medicalService.scheduleAppointment(appointment);

System.out.println("\n--- Medical Record ---");
medicalService.displayMedicalRecords(dog);

System.out.println("\n--- Vaccination ---");
medicalService.displayVaccinations(dog);

System.out.println("\n--- Vet Appointment ---");
medicalService.displayAppointments(vet);
System.out.println("\n========== PET FILE STORAGE ==========");



try {
    // Save all pets to the file
    fileManager.savePets("data/pets.txt", petService.getPets());

    System.out.println("Pets saved successfully!");

    // Load pets back from the file
    List<Pet> loadedPets = fileManager.loadPets("data/pets.txt");

    System.out.println("\nPets loaded from file:");

    for (Pet loadedPet : loadedPets) {
        loadedPet.displayInfo();
        System.out.println("Adopted: " + loadedPet.isAdopted());
        System.out.println("--------------------");
    }

} catch (IOException e) {
    System.out.println("File error: " + e.getMessage());
}
    }
}