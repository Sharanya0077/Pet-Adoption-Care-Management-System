
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import models.*;
import util.FileManager;
import util.AdopterFileManager;

public class GenerateSampleData {

    public static void main(String[] args) throws IOException {

        FileManager fm = new FileManager();
        AdopterFileManager afm = new AdopterFileManager();

        // 1. Sample pets
        Dog buddy = new Dog(
            101, "Buddy", 3, "Labrador", "Male", true, "Advanced"
        );
        buddy.adopt();

        Dog coco = new Dog(
            102, "Coco", 2, "Beagle", "Female", true, "Beginner"
        );

        Cat luna = new Cat(
            103, "Luna", 1, "Persian", "Female", true, true
        );

        Cat milo = new Cat(
            104, "Milo", 4, "Indie", "Male", true, false
        );

        Bird kiwi = new Bird(
            105, "Kiwi", 1, "Budgerigar", "Male", true, true
        );

        Bird pepper = new Bird(
            106, "Pepper", 2, "Cockatiel", "Female", true, true
        );

        List<Pet> pets = Arrays.asList(
            buddy, coco, luna, milo, kiwi, pepper
        );

        // 2. Sample adopter accounts
        Adopter a1 = new Adopter(
            1, "Aarav Sharma", "aarav@example.com", "9000000001"
        );

        Adopter a2 = new Adopter(
            2, "Ananya Mehta", "ananya@example.com", "9000000002"
        );

        Adopter a3 = new Adopter(
            3, "Rohan Verma", "rohan@example.com", "9000000003"
        );

        List<Adopter> adopters = Arrays.asList(a1, a2, a3);

        // 3. Sample adoption applications
        AdoptionApplication app1 = new AdoptionApplication(
            1001, a1, buddy, "01-10-2026"
        );
        app1.setStatus("Approved");

        AdoptionApplication app2 = new AdoptionApplication(
            1002, a2, coco, "05-10-2026"
        );

        AdoptionApplication app3 = new AdoptionApplication(
            1003, a3, luna, "06-10-2026"
        );
        app3.setStatus("Rejected");

        List<AdoptionApplication> applications =
            Arrays.asList(app1, app2, app3);

        // 4. Medical records
        List<MedicalRecord> records = Arrays.asList(
            new MedicalRecord(
                501, buddy, "Dental examination",
                "Teeth cleaned; routine check completed",
                "01-10-2026"
            ),
            new MedicalRecord(
                502, luna, "Mild skin irritation",
                "Skin examination and prescribed care",
                "03-10-2026"
            ),
            new MedicalRecord(
                503, coco, "Routine health check",
                "Healthy; no treatment required",
                "05-10-2026"
            )
        );

        // 5. Vaccination records
        List<Vaccination> vaccinations = Arrays.asList(
            new Vaccination(
                601, buddy, "Rabies",
                "01-10-2026", "01-10-2027"
            ),
            new Vaccination(
                602, luna, "Feline core vaccine",
                "03-10-2026", "03-10-2027"
            ),
            new Vaccination(
                603, coco, "Canine core vaccine",
                "05-10-2026", "05-10-2027"
            )
        );

        // 6. Vet appointments
        Vet vet = new Vet(
            3, "Dr Aditi Sharma",
            "vet@example.com", "9000000010",
            "General Veterinary Care"
        );

        Appointment appointment1 = new Appointment(
            701, buddy, vet, "10-10-2026", "Routine check-up"
        );

        Appointment appointment2 = new Appointment(
            702, luna, vet, "12-10-2026", "Skin follow-up"
        );
        appointment2.setStatus("Completed");

        List<Appointment> appointments =
            Arrays.asList(appointment1, appointment2);

        // Save every dataset using the existing file managers
        fm.savePets("data/pets.txt", pets);
        afm.saveAdopters("data/adopters.txt", adopters);
        fm.saveApplications("data/applications.txt", applications);
        fm.saveMedicalRecords("data/medical_records.txt", records);
        fm.saveVaccinations("data/vaccinations.txt", vaccinations);
        fm.saveAppointments("data/appointments.txt", appointments);

        System.out.println("Sample dataset created successfully!");
        System.out.println("Pets: " + pets.size());
        System.out.println("Adopters: " + adopters.size());
        System.out.println("Applications: " + applications.size());
        System.out.println("Medical records: " + records.size());
        System.out.println("Vaccinations: " + vaccinations.size());
        System.out.println("Appointments: " + appointments.size());
    }
}
