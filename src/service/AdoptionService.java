package service;
import exceptions.PetNotFoundException;
import models.Adopter;
import models.Pet;

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
}