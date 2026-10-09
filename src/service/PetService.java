
package service;

import interfaces.Searchable;
import models.Pet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Collections;

public class PetService implements Searchable {

    private List<Pet> pets;

    public PetService() {
        pets = new ArrayList<>();
    }

    public void addPet(Pet pet) {
        if (pet == null) {
            throw new IllegalArgumentException(
                "Pet cannot be null."
            );
        }

        for (Pet existing : pets) {
            if (existing.getPetId() == pet.getPetId()) {
                throw new IllegalArgumentException(
                    "Duplicate pet ID: " + pet.getPetId()
                );
            }
        }

        pets.add(pet);
    }

    public void displayAllPets() {
        if (pets.isEmpty()) {
            System.out.println("No pets registered.");
            return;
        }

        for (Pet pet : pets) {
            pet.displayInfo();
            System.out.println("--------------------");
        }
    }

    @Override
    public void searchByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Please enter a pet name.");
            return;
        }

        boolean found = false;

        for (Pet pet : pets) {
            if (pet.getName().equalsIgnoreCase(name.trim())) {
                pet.displayInfo();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No pet found with name: " + name);
        }
    }

    @Override
    public void searchByBreed(String breed) {
        if (breed == null || breed.trim().isEmpty()) {
            System.out.println("Please enter a breed.");
            return;
        }

        boolean found = false;

        for (Pet pet : pets) {
            if (pet.getBreed().equalsIgnoreCase(breed.trim())) {
                pet.displayInfo();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No pet found with breed: " + breed);
        }
    }

    public List<Pet> getPets() {
        return Collections.unmodifiableList(pets);
    }

    public void setPets(List<Pet> savedPets) {
        if (savedPets == null) {
            throw new IllegalArgumentException(
                "Pet list cannot be null."
            );
        }

        Set<Integer> ids = new HashSet<>();

        for (Pet pet : savedPets) {
            if (pet == null) {
                throw new IllegalArgumentException(
                    "Pet list cannot contain null."
                );
            }

            if (!ids.add(pet.getPetId())) {
                throw new IllegalArgumentException(
                    "Duplicate pet ID in loaded data: "
                    + pet.getPetId()
                );
            }
        }

        pets.clear();
        pets.addAll(savedPets);
    }
}
