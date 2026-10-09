
import java.util.List;
import java.util.Scanner;

import models.*;
import service.*;
import exceptions.PetNotFoundException;

public class Menu {

    private final PetService petService;
    private final AdoptionService adoptionService;
    private final MedicalService medicalService;
    private final List<AdoptionApplication> applications;
    private final ShelterAdmin admin;
    private final Adopter adopter;
    private final Vet vet;
    private final Scanner scanner = new Scanner(System.in);

    public Menu(
            PetService petService,
            AdoptionService adoptionService,
            MedicalService medicalService,
            List<AdoptionApplication> applications,
            ShelterAdmin admin,Vet vet,
            Adopter adopter) {

        this.petService = petService;
        this.adoptionService = adoptionService;
        this.medicalService = medicalService;
        this.applications = applications;
        this.admin = admin;
        this.vet=vet;
        this.adopter = adopter;
    }

    public void start() {

        int choice;

        do {
            System.out.println("\n===== PET ADOPTION & CARE SYSTEM =====");
            System.out.println("1. View all pets");
            System.out.println("2. Search pets by name");
            System.out.println("3. Search pets by breed");
            System.out.println("4. Submit adoption application");
            System.out.println("5. View adoption applications");
            System.out.println("6. Process an application");
            System.out.println("7. View medical and care records");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            choice = readInt();

            try {
                switch (choice) {
                    case 1:
                        viewPets();
                        break;

                    case 2:
                        searchPets(true);
                        break;

                    case 3:
                        searchPets(false);
                        break;

                    case 4:
                        submitApplication();
                        break;

                    case 5:
                        viewApplications();
                        break;

                    case 6:
                        processApplication();
                        break;

                    case 7:
                        viewCareRecords();
                        break;

                    case 0:
                        System.out.println("Exiting the system. Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (PetNotFoundException | IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (RuntimeException e) {
                System.out.println("Operation failed: " + e.getMessage());
            }

        } while (choice != 0);
    }

    private int readInt() {
        while (!scanner.hasNextInt()) {
            System.out.print("Enter a valid number: ");
            scanner.next();
        }

        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    private void viewPets() {
        System.out.println("\n===== ALL PETS =====");

        if (petService.getPets().isEmpty()) {
            System.out.println("No pets registered.");
            return;
        }

        for (Pet pet : petService.getPets()) {
            pet.displayInfo();
            System.out.println("--------------------");
        }
    }

    
private void searchPets(boolean byName) {
    System.out.print(
        byName ? "Enter pet name: " : "Enter pet breed: "
    );

    String query = scanner.nextLine().trim();

    if (query.isEmpty()) {
        System.out.println("Search query cannot be empty.");
        return;
    }

    if (byName) {
        petService.searchByName(query);
    } else {
        petService.searchByBreed(query);
    }
}


    private void submitApplication() throws PetNotFoundException {
        viewPets();

        System.out.print("Enter the pet ID you want to adopt: ");
        int petId = readInt();

        Pet selectedPet = null;

        for (Pet pet : petService.getPets()) {
            if (pet.getPetId() == petId) {
                selectedPet = pet;
                break;
            }
        }

        if (selectedPet == null) {
            throw new PetNotFoundException(
                "Pet with ID " + petId + " was not found."
            );
        }

        int nextId = 1;

        for (AdoptionApplication application : applications) {
            nextId = Math.max(
                nextId, application.getApplicationId() + 1
            );
        }

        System.out.print("Enter application date (DD-MM-YYYY): ");
        String date = scanner.nextLine().trim();

        AdoptionApplication application =
                adoptionService.createApplication(
                    nextId, adopter, selectedPet, date
                );

        if (application != null) {
            applications.add(application);
            System.out.println(
                "Your application ID is " + nextId
            );
            System.out.println(
                "Status: " + application.getStatus()
            );
        }
    }

    private void viewApplications() {
        System.out.println("\n===== ADOPTION APPLICATIONS =====");

        if (applications.isEmpty()) {
            System.out.println("No applications found.");
            return;
        }

        for (AdoptionApplication application : applications) {
            application.displayApplication();
            System.out.println("--------------------");
        }
    }

    private void processApplication() {
        viewApplications();

        if (applications.isEmpty()) {
            return;
        }

        System.out.print("Enter application ID: ");
        int id = readInt();

        AdoptionApplication selected = null;

        for (AdoptionApplication application : applications) {
            if (application.getApplicationId() == id) {
                selected = application;
                break;
            }
        }

        if (selected == null) {
            System.out.println("Application not found.");
            return;
        }

        System.out.println("1. Approve");
        System.out.println("2. Reject");
        System.out.print("Choose an action: ");

        int action = readInt();

        if (action == 1) {
            admin.approveApplication(selected);

            if ("Approved".equals(selected.getStatus())) {
                adoptionService.approveApplication(selected);
            }
        } else if (action == 2) {
            admin.rejectApplication(selected);
        } else {
            System.out.println("Invalid action.");
        }
    }

    
private void viewCareRecords() {
    if (petService.getPets().isEmpty()) {
        System.out.println("No pets registered.");
        return;
    }

    for (Pet pet : petService.getPets()) {
        System.out.println(
            "\n===== MEDICAL RECORDS: " + pet.getName() + " ====="
        );
        medicalService.displayMedicalRecords(pet);

        System.out.println(
            "\n===== VACCINATIONS: " + pet.getName() + " ====="
        );
        medicalService.displayVaccinations(pet);
    }

    System.out.println("\n===== VET APPOINTMENTS =====");
    medicalService.displayAppointments(vet);
}

}
