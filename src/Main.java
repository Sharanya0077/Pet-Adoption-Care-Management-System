
import exceptions.PetNotFoundException;
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

        // 1. Load saved pets, or create demo data if none exist
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
            System.out.println("Could not load pets: " + e.getMessage());
            return;
        }

        Pet dog = petService.getPets().get(0);

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

        MedicalService medicalService = new MedicalService();

        // 3. Load saved applications and care records
        List<AdoptionApplication> applications;
        try {
            applications = fileManager.loadApplications(
                "data/applications.txt", petService.getPets()
            );

            medicalService.setMedicalRecords(
                fileManager.loadMedicalRecords(
                    "data/medical_records.txt", petService.getPets()
                )
            );

            medicalService.setVaccinations(
                fileManager.loadVaccinations(
                    "data/vaccinations.txt", petService.getPets()
                )
            );

            medicalService.setAppointments(
                fileManager.loadAppointments(
                    "data/appointments.txt", petService.getPets()
                )
            );

            System.out.println("Saved applications and care records loaded.");

        } catch (IOException e) {
            System.out.println(
                "Could not load saved records: " + e.getMessage()
            );
            return;
        }

        // 4. Run the adoption demo only when appropriate
        System.out.println("\n========== ADOPTION ==========");

        boolean existingApplication = false;

        for (AdoptionApplication application : applications) {
            if (application.getPet().getPetId() == dog.getPetId()
                    && !application.getStatus().equals("Rejected")) {
                existingApplication = true;
                break;
            }
        }

        if (!dog.isAvailable() || dog.isAdopted()) {
            System.out.println(
                dog.getName() + " is already adopted or unavailable."
            );

        } else if (existingApplication) {
            System.out.println(
                "An application already exists for " + dog.getName() + "."
            );

        } else {
            int nextApplicationId = 1001;

            for (AdoptionApplication application : applications) {
                if (application.getApplicationId() >= nextApplicationId) {
                    nextApplicationId =
                        application.getApplicationId() + 1;
                }
            }

            try {
                AdoptionApplication application =
                    adoptionService.createApplication(
                        nextApplicationId,
                        adopter,
                        dog,
                        "09-10-2026"
                    );

                if (application != null) {
                    applications.add(application);
                    application.displayApplication();

                    System.out.println("\n--- Admin Review ---");
                    admin.approveApplication(application);
                    adoptionService.approveApplication(application);
                }

            } catch (PetNotFoundException e) {
                System.out.println("Adoption error: " + e.getMessage());
            }
        }

        System.out.println("\nPet available: " + dog.isAvailable());
        System.out.println("Pet adopted: " + dog.isAdopted());

        // 5. Add sample medical data only if it doesn't already exist
        boolean hasMedicalRecord = false;

        for (MedicalRecord record : medicalService.getMedicalRecords()) {
            if (record.getPet().getPetId() == dog.getPetId()) {
                hasMedicalRecord = true;
                break;
            }
        }

        if (!hasMedicalRecord) {
            medicalService.addMedicalRecord(new MedicalRecord(
                501, dog, "Minor skin infection",
                "Topical medication for 7 days", "09-10-2026"
            ));
        }

        boolean hasVaccination = false;

        for (Vaccination vaccination : medicalService.getVaccinations()) {
            if (vaccination.getPet().getPetId() == dog.getPetId()) {
                hasVaccination = true;
                break;
            }
        }

        if (!hasVaccination) {
            medicalService.addVaccination(new Vaccination(
                601, dog, "Rabies",
                "09-10-2026", "09-10-2027"
            ));
        }

        boolean hasAppointment = false;

        for (Appointment appointment : medicalService.getAppointments()) {
            if (appointment.getPet().getPetId() == dog.getPetId()
                    && appointment.getVet().getUserId() == vet.getUserId()) {
                hasAppointment = true;
                break;
            }
        }

        if (!hasAppointment) {
            medicalService.scheduleAppointment(new Appointment(
                701, dog, vet,
                "10-10-2026", "Routine check-up"
            ));
        }

        // 6. Display medical and care information
        System.out.println("\n========== MEDICAL & CARE ==========");

        System.out.println("\n--- Medical Records ---");
        medicalService.displayMedicalRecords(dog);

        System.out.println("\n--- Vaccinations ---");
        medicalService.displayVaccinations(dog);

        System.out.println("\n--- Vet Appointments ---");
        medicalService.displayAppointments(vet);

        // 7. Save all five types of data
        System.out.println("\n========== SAVING DATA ==========");

        try {
            fileManager.savePets(
                "data/pets.txt", petService.getPets()
            );

            fileManager.saveApplications(
                "data/applications.txt", applications
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
            System.out.println("File storage error: " + e.getMessage());
        }
    }
}
