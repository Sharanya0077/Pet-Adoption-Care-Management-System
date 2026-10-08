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
public class Main {

    public static void main(String[] args) {

        PetService petService = new PetService();

        Pet dog = new Dog(
            101,
            "Buddy",
            2,
            "Golden Retriever",
            "Male",
            true,
            "Basic"
        );

        petService.addPet(dog);

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

        } catch (PetNotFoundException e) {

            System.out.println(
                "Error: " + e.getMessage()
            );
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
    }
}