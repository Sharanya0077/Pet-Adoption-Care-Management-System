package service;
import exceptions.PetNotFoundException;
import models.Adopter;
import models.Pet;
import models.AdoptionApplication;

public class AdoptionService {

    private PetService petService;

    public AdoptionService(PetService petService) {
        this.petService = petService;
    }

   public void adoptPet(int petId, Adopter adopter) throws PetNotFoundException{

        for (Pet pet : petService.getPets()) {

            if (pet.getPetId() == petId) {

                if (!pet.isAvailable()) {
                    System.out.println("Sorry, this pet is not available for adoption.");
                    return;
                }

                pet.adopt();

                System.out.println(
                    adopter.getName() + " successfully adopted " + pet.getName() + "!"
                );

                return;
            }
        }

       throw new PetNotFoundException(
    "Pet with ID " + petId + " was not found."
);
    }
    public AdoptionApplication createApplication(
        int applicationId,
        Adopter adopter,
        Pet pet,
        String applicationDate) throws PetNotFoundException {

    boolean petExists = false;

    for (Pet existingPet : petService.getPets()) {
        if (existingPet.getPetId() == pet.getPetId()) {
            petExists = true;
            break;
        }
    }

    if (!petExists) {
        throw new PetNotFoundException(
            "Pet with ID " + pet.getPetId() + " was not found."
        );
    }

    if (!pet.isAvailable()) {
        System.out.println(
            "This pet is currently not available for adoption."
        );
        return null;
    }

    AdoptionApplication application =
        new AdoptionApplication(
            applicationId,
            adopter,
            pet,
            applicationDate
        );

    System.out.println("Adoption application created successfully!");

    return application;
}
}