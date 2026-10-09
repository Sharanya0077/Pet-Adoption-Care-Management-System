
import models.*;
import service.AdoptionService;
import service.PetService;
import service.MedicalService;
import util.FileManager;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        FileManager fileManager = new FileManager();
        PetService petService = new PetService();
        MedicalService medicalService = new MedicalService();

        // 1. Load pets or create a demo pet
        try {
            List<Pet> savedPets =
                    fileManager.loadPets("data/pets.txt");

            if (savedPets.isEmpty()) {
                petService.addPet(new Dog(
                    101, "Buddy", 2, "Golden Retriever",
                    "Male", true, "Basic"
                ));

                System.out.println("Demo pet created.");
            } else {
                petService.setPets(savedPets);
                System.out.println("Saved pets loaded.");
            }

        } catch (IOException e) {
            System.out.println(
                "Could not load pets: " + e.getMessage()
            );
            return;
        }

        // 2. Create users and services
        Adopter adopter = new Adopter(
            1, "Sharanya",
            "sharanya@example.com", "9876543210"
        );

        ShelterAdmin admin = new ShelterAdmin(
            2, "Shelter Admin",
            "admin@example.com", "9999999999"
        );

        Vet vet = new Vet(
            3, "Mehta", "vet@example.com",
            "8888888888", "Veterinary Medicine"
        );

        AdoptionService adoptionService =
                new AdoptionService(petService);

        // 3. Load saved applications and care records
        List<AdoptionApplication> applications;

        try {
            applications = fileManager.loadApplications(
                "data/applications.txt",
                petService.getPets()
            );

            adoptionService.registerApplications(applications);

            medicalService.setMedicalRecords(
                fileManager.loadMedicalRecords(
                    "data/medical_records.txt",
                    petService.getPets()
                )
            );

            medicalService.setVaccinations(
                fileManager.loadVaccinations(
                    "data/vaccinations.txt",
                    petService.getPets()
                )
            );

            medicalService.setAppointments(
                fileManager.loadAppointments(
                    "data/appointments.txt",
                    petService.getPets()
                )
            );

            System.out.println(
                "Saved applications and care records loaded."
            );

        } catch (IOException e) {
            System.out.println(
                "Could not load saved records: " + e.getMessage()
            );
            return;
        }

        // 4. Add demo care data only when a category is empty
        Pet demoPet = petService.getPets().get(0);

        if (medicalService.getMedicalRecords().isEmpty()) {
            medicalService.addMedicalRecord(new MedicalRecord(
                501, demoPet,
                "Routine health check",
                "General health assessment",
                "09-10-2026"
            ));
        }

        if (medicalService.getVaccinations().isEmpty()) {
            medicalService.addVaccination(new Vaccination(
                601, demoPet,
                "Rabies",
                "09-10-2026",
                "09-10-2027"
            ));
        }

        if (medicalService.getAppointments().isEmpty()) {
            medicalService.scheduleAppointment(new Appointment(
                701, demoPet, vet,
                "10-10-2026",
                "Routine check-up"
            ));
        }

        // 5. Start the interactive menu
        Menu menu = new Menu(
    petService,
    adoptionService,
    medicalService,
    applications,
    admin,
    vet,
    adopter
);

        menu.start();

        // 6. Save all data after the user exits
        System.out.println("\n========== SAVING DATA ==========");

        try {
            fileManager.savePets(
                "data/pets.txt",
                petService.getPets()
            );

            fileManager.saveApplications(
                "data/applications.txt",
                applications
            );

            fileManager.saveMedicalRecords(
                "data/medical_records.txt",
                medicalService.getMedicalRecords()
            );

            fileManager.saveVaccinations(
                "data/vaccinations.txt",
                medicalService.getVaccinations()
            );

            fileManager.saveAppointments(
                "data/appointments.txt",
                medicalService.getAppointments()
            );

            System.out.println("All records saved successfully!");

        } catch (IOException e) {
            System.out.println(
                "File storage error: " + e.getMessage()
            );
        }
    }
}
