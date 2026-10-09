
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
    private final Vet vet;
    private final AdopterService adopterService;

    private Adopter activeAdopter;
    private final Scanner scanner = new Scanner(System.in);

    public Menu(
            PetService petService,
            AdoptionService adoptionService,
            MedicalService medicalService,
            List<AdoptionApplication> applications,
            ShelterAdmin admin,
            Vet vet,
            AdopterService adopterService) {

        this.petService = petService;
        this.adoptionService = adoptionService;
        this.medicalService = medicalService;
        this.applications = applications;
        this.admin = admin;
        this.vet = vet;
        this.adopterService = adopterService;
        this.activeAdopter = null;
    }

    // Main role-selection menu
    public void start() {
        int choice;

        do {
            System.out.println("\n===== PET ADOPTION & CARE SYSTEM =====");
            System.out.println("1. Adopter");
            System.out.println("2. Shelter Admin");
            System.out.println("3. Vet");
            System.out.println("0. Exit");
            System.out.print("Choose your role: ");

            choice = readInt();

            try {
                switch (choice) {
                    case 1:
                        adopterMenu();
                        break;
                    case 2:
                        adminMenu();
                        break;
                    case 3:
                        vetMenu();
                        break;
                    case 0:
                        System.out.println("Thank you for using the system!");
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

    // Adopter menu
    private void adopterMenu() throws PetNotFoundException {
        int choice;

        do {
            System.out.println("\n===== ADOPTER MENU =====");

            if (activeAdopter != null) {
                System.out.println("Welcome, " + activeAdopter.getName());
            } else {
                System.out.println("No adopter account selected.");
            }

            System.out.println("1. View all pets");
            System.out.println("2. Search pets by name");
            System.out.println("3. Search pets by breed");
            System.out.println("4. Submit adoption application");
            System.out.println("5. View adoption applications");
            System.out.println("6. Register / switch adopter account");
            System.out.println("0. Back to role selection");
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
                        manageAdopterAccount();
                        break;
                    case 0:
                        System.out.println("Returning to role selection.");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (PetNotFoundException | IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (RuntimeException e) {
                System.out.println("Operation failed: " + e.getMessage());
            }

        } while (choice != 0);
    }

    // Shelter admin menu
    private void adminMenu() {
        int choice;

        do {
            System.out.println("\n===== SHELTER ADMIN MENU =====");
            System.out.println("Welcome, " + admin.getName());
            System.out.println("1. View all pets");
            System.out.println("2. View adoption applications");
            System.out.println("3. Process an application");
            System.out.println("0. Back to role selection");
            System.out.print("Enter your choice: ");

            choice = readInt();

            try {
                switch (choice) {
                    case 1:
                        viewPets();
                        break;
                    case 2:
                        viewApplications();
                        break;
                    case 3:
                        processApplication();
                        break;
                    case 0:
                        System.out.println("Returning to role selection.");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (RuntimeException e) {
                System.out.println("Operation failed: " + e.getMessage());
            }

        } while (choice != 0);
    }

    // Vet menu
    private void vetMenu() {
        int choice;

        do {
            System.out.println("\n===== VET MENU =====");
            System.out.println("Welcome, " + vet.getName());
            System.out.println("1. View all pets");
            System.out.println("2. View medical records and vaccinations");
            System.out.println("3. View vet appointments");
            System.out.println("0. Back to role selection");
            System.out.print("Enter your choice: ");

            choice = readInt();

            try {
                switch (choice) {
                    case 1:
                        viewPets();
                        break;
                    case 2:
                        viewCareRecords();
                        break;
                    case 3:
                        medicalService.displayAppointments(vet);
                        break;
                    case 0:
                        System.out.println("Returning to role selection.");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (RuntimeException e) {
                System.out.println("Operation failed: " + e.getMessage());
            }

        } while (choice != 0);
    }

    // Read an integer safely
    private int readInt() {
        while (!scanner.hasNextInt()) {
            System.out.print("Enter a valid number: ");
            scanner.next();
        }

        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    // Display all pets
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

    // Search pets by name or breed
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

    // Submit an adoption application
    private void submitApplication() throws PetNotFoundException {
        if (activeAdopter == null) {
            System.out.println(
                "Please register or select an adopter account first."
            );
            return;
        }

        viewPets();

        if (petService.getPets().isEmpty()) {
            return;
        }

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
                nextId,
                application.getApplicationId() + 1
            );
        }

        System.out.print("Enter application date (DD-MM-YYYY): ");
        String date = scanner.nextLine().trim();

        AdoptionApplication application =
            adoptionService.createApplication(
                nextId, activeAdopter, selectedPet, date
            );

        if (application != null) {
            applications.add(application);
            System.out.println("Application submitted successfully!");
            application.displayApplication();
        }
    }

    // Display adoption applications
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

    // Admin approves or rejects applications
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

        if (!"Pending".equals(selected.getStatus())) {
            System.out.println("This application has already been processed.");
            return;
        }

        System.out.println("1. Approve");
        System.out.println("2. Reject");
        System.out.print("Choose an action: ");

        int action = readInt();

        if (action == 1) {
            Pet pet = selected.getPet();

            if (pet.isAdopted() || !pet.isAvailable()) {
                System.out.println(
                    "This pet is already adopted or unavailable."
                );
                return;
            }

            admin.approveApplication(selected);

            if ("Approved".equals(selected.getStatus())) {
                pet.adopt();
            }

        } else if (action == 2) {
            admin.rejectApplication(selected);
        } else {
            System.out.println("Invalid action.");
        }
    }

    // Display medical records and vaccinations
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
    }

    // Register or select an adopter account
    private void manageAdopterAccount() {
        System.out.println("\n===== ADOPTER ACCOUNTS =====");
        System.out.println("1. Register new adopter");
        System.out.println("2. Choose existing adopter");
        System.out.print("Enter choice: ");

        int choice = readInt();

        if (choice == 1) {
            System.out.print("Enter full name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Enter phone number: ");
            String phone = scanner.nextLine().trim();

            if (name.isEmpty() || email.isEmpty()
                    || phone.isEmpty() || !email.contains("@")) {
                System.out.println(
                    "Enter a valid name, email and phone number."
                );
                return;
            }

            Adopter adopter = new Adopter(
                adopterService.getNextUserId(),
                name,
                email,
                phone
            );

            try {
                adopterService.addAdopter(adopter);
                activeAdopter = adopter;

                System.out.println("Account registered successfully!");
                System.out.println("Welcome, " + activeAdopter.getName());

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

        } else if (choice == 2) {
            if (adopterService.getAdopters().isEmpty()) {
                System.out.println(
                    "No adopter accounts exist yet. Register first."
                );
                return;
            }

            for (Adopter adopter : adopterService.getAdopters()) {
                System.out.println(
                    "ID: " + adopter.getUserId()
                    + " | Name: " + adopter.getName()
                    + " | Email: " + adopter.getEmail()
                );
            }

            System.out.print("Enter adopter ID: ");
            int id = readInt();

            Adopter selected = adopterService.findById(id);

            if (selected == null) {
                System.out.println("Adopter account not found.");
            } else {
                activeAdopter = selected;
                System.out.println(
                    "Active account: " + activeAdopter.getName()
                );
            }

        } else {
            System.out.println("Invalid choice.");
        }
    }
}
