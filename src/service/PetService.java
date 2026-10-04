package service;

import interfaces.Searchable;
import models.Pet;

import java.util.ArrayList;
import java.util.List;

public class PetService implements Searchable {

    private List<Pet> pets;

    public PetService() {
        pets = new ArrayList<>();
    }

    // Add a pet to the system
    public void addPet(Pet pet) {
        pets.add(pet);
    }

    // Display all pets
    public void displayAllPets() {
        for (Pet pet : pets) {
            pet.displayInfo();
            System.out.println("--------------------");
        }
    }

    // Search pet by name
    @Override
    public void searchByName(String name) {
        boolean found = false;

        for (Pet pet : pets) {
            if (pet.getName().equalsIgnoreCase(name)) {
                pet.displayInfo();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No pet found with name: " + name);
        }
    }

    // Search pet by breed
    @Override
    public void searchByBreed(String breed) {
        boolean found = false;

        for (Pet pet : pets) {
            if (pet.getBreed().equalsIgnoreCase(breed)) {
                pet.displayInfo();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No pet found with breed: " + breed);
        }
    }

    // Get all pets
    public List<Pet> getPets() {
        return pets;
    }
}