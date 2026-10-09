
package service;

import exceptions.PetNotFoundException;
import exceptions.PetAlreadyAdoptedException;
import exceptions.InvalidAdoptionException;
import models.Adopter;
import models.Pet;
import models.AdoptionApplication;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
public class AdoptionService {

    private PetService petService;
    private Set<Integer> applicationIds = new HashSet<>();
private List<AdoptionApplication> registeredApplications =
        new ArrayList<>();
    public AdoptionService(PetService petService) {
        this.petService = petService;
    }

    public void adoptPet(int petId, Adopter adopter)
            throws PetNotFoundException {

        Pet pet = findPet(petId);

        if (pet.isAdopted()) {
            throw new PetAlreadyAdoptedException(
                pet.getName() + " has already been adopted."
            );
        }

        if (!pet.isAvailable()) {
            throw new InvalidAdoptionException(
                pet.getName() + " is unavailable for adoption."
            );
        }

        pet.adopt();

        System.out.println(
            adopter.getName() + " successfully adopted "
            + pet.getName() + "!"
        );
    }

    public AdoptionApplication createApplication(
            int applicationId,
            Adopter adopter,
            Pet pet,
            String applicationDate) throws PetNotFoundException {
                if (applicationIds.contains(applicationId)) {
    throw new InvalidAdoptionException(
        "Duplicate application ID: " + applicationId
    );
}

        if (adopter == null || pet == null
                || applicationDate == null
                || applicationDate.trim().isEmpty()) {
            throw new InvalidAdoptionException(
                "Adopter, pet, and application date are required."
            );
        }

        if (applicationId <= 0) {
            throw new InvalidAdoptionException(
                "Application ID must be positive."
            );
        }

        
Pet registeredPet = findPet(pet.getPetId());

// Check pet status first
if (registeredPet.isAdopted()) {
    throw new PetAlreadyAdoptedException(
        registeredPet.getName() + " has already been adopted."
    );
}

if (!registeredPet.isAvailable()) {
    throw new InvalidAdoptionException(
        registeredPet.getName() + " is unavailable for adoption."
    );
}

// Then check for another pending application
for (AdoptionApplication existing : registeredApplications) {
    if (existing.getPet().getPetId() == registeredPet.getPetId()
            && "Pending".equals(existing.getStatus())) {
        throw new InvalidAdoptionException(
            "A pending application already exists for "
            + registeredPet.getName() + "."
        );
    }
}


AdoptionApplication application =
    new AdoptionApplication(
        applicationId,
        adopter,
        registeredPet,
        applicationDate.trim()
    );

applicationIds.add(applicationId);
registeredApplications.add(application);
System.out.println(
    "Adoption application created successfully!"
);

return application;

    }

    public void approveApplication(AdoptionApplication application) {
        if (application == null
                || !"Approved".equals(application.getStatus())) {
            System.out.println(
                "The application has not been approved by the admin."
            );
            return;
        }

        Pet pet = application.getPet();

        if (pet.isAdopted()) {
            throw new PetAlreadyAdoptedException(
                pet.getName() + " has already been adopted."
            );
        }

        if (!pet.isAvailable()) {
            throw new InvalidAdoptionException(
                pet.getName() + " is unavailable for adoption."
            );
        }

        pet.adopt();

        System.out.println(
            pet.getName() + " has been adopted by "
            + application.getAdopter().getName() + "!"
        );
    }

    private Pet findPet(int petId) throws PetNotFoundException {
        for (Pet pet : petService.getPets()) {
            if (pet.getPetId() == petId) {
                return pet;
            }
        }

        throw new PetNotFoundException(
            "Pet with ID " + petId + " was not found."
        );
    }
    
public void registerApplications(
        List<AdoptionApplication> applications) {

    if (applications == null) {
        throw new IllegalArgumentException(
            "Application list cannot be null."
        );
    }

    Set<Integer> newIds = new HashSet<>();

    for (AdoptionApplication application : applications) {
        if (application == null) {
            throw new IllegalArgumentException(
                "Application cannot be null."
            );
        }

        if (application.getApplicationId() <= 0
                || !newIds.add(application.getApplicationId())) {
            throw new IllegalArgumentException(
                "Invalid or duplicate application ID."
            );
        }
    }

    applicationIds.clear();
    applicationIds.addAll(newIds);

    registeredApplications = new ArrayList<>(applications);
}

}
