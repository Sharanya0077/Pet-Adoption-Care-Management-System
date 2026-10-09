
import models.*;
import service.*;
import exceptions.*;

public class ProjectTests {

    public static void main(String[] args) throws Exception {

        System.out.println("===== PROJECT EDGE-CASE TESTS =====");

        PetService petService = new PetService();
        AdoptionService adoptionService =
                new AdoptionService(petService);

        Dog dog = new Dog(
                1, "Buddy", 3, "Labrador",
                "Male", true, "Basic"
        );

        Adopter adopter = new Adopter(
                101, "Sharanya",
                "sharanya@example.com", "9876543210"
        );

        // Test 1: Add a pet successfully
        petService.addPet(dog);
        System.out.println("PASS: Pet added successfully.");

        // Test 2: Reject a duplicate pet ID
        try {
            petService.addPet(new Dog(
                    1, "Rocky", 2, "Beagle",
                    "Male", true, "Basic"
            ));
            System.out.println("FAIL: Duplicate pet ID accepted.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Duplicate pet ID rejected.");
        }

        // Test 3: Create an adoption application
        adoptionService.createApplication(
                1001, adopter, dog, "09-10-2026"
        );
        System.out.println("PASS: Application created.");

        // Test 4: Reject a duplicate application ID
        try {
            adoptionService.createApplication(
                    1001, adopter, dog, "10-10-2026"
            );
            System.out.println(
                    "FAIL: Duplicate application ID accepted."
            );
        } catch (InvalidAdoptionException e) {
            System.out.println(
                    "PASS: Duplicate application ID rejected."
            );
        }

        // Test 5: Reject a second pending application for the same pet
        try {
            adoptionService.createApplication(
                    1002, adopter, dog, "11-10-2026"
            );

            System.out.println(
                    "FAIL: Duplicate pending application accepted."
            );
        } catch (InvalidAdoptionException e) {
            System.out.println(
                    "PASS: Duplicate pending application rejected."
            );
        }

        // Test 6: Reject an application for an adopted pet
        dog.adopt();

        try {
            adoptionService.createApplication(
                    1002, adopter, dog, "12-10-2026"
            );

            System.out.println(
                    "FAIL: Application accepted for adopted pet."
            );
        } catch (PetAlreadyAdoptedException e) {
            System.out.println(
                    "PASS: Adopted pet cannot receive a new application."
            );
        } catch (InvalidAdoptionException e) {
            System.out.println(
                    "FAIL: Expected adopted-pet exception, got: "
                    + e.getMessage()
            );
        }

        System.out.println("===== TESTS COMPLETED =====");

    }
}
