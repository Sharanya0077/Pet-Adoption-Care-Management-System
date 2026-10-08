import exceptions.PetNotFoundException;
import models.Adopter;
import models.Dog;
import models.Pet;
import service.AdoptionService;
import service.PetService;

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

        AdoptionService adoptionService =
                new AdoptionService(petService);

        try {
            adoptionService.adoptPet(101, adopter);
            System.out.println("Pet available: " + dog.isAvailable());
            System.out.println("Pet adopted: " + dog.isAdopted());
        } catch (PetNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}