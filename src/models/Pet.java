
package models;

import interfaces.Adoptable;
import exceptions.PetAlreadyAdoptedException;

public abstract class Pet implements Adoptable {

    private int petId;
    private String name;
    private int age;
    private String breed;
    private String gender;
    private boolean available;
    private boolean adopted;

    public Pet(int petId, String name, int age, String breed,
               String gender, boolean available) {

        if (petId <= 0) {
            throw new IllegalArgumentException("Pet ID must be positive.");
        }
        if (age < 0) {
            throw new IllegalArgumentException("Pet age cannot be negative.");
        }

        this.petId = petId;
        this.name = requireText(name, "Name");
        this.age = age;
        this.breed = requireText(breed, "Breed");
        this.gender = requireText(gender, "Gender");
        this.available = available;
        this.adopted = false;
    }

    private static String requireText(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(
                field + " cannot be empty."
            );
        }
        return value.trim();
    }

    public int getPetId() {
        return petId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getBreed() {
        return breed;
    }

    public String getGender() {
        return gender;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setName(String name) {
        this.name = requireText(name, "Name");
    }

    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException(
                "Pet age cannot be negative."
            );
        }
        this.age = age;
    }

    public void setBreed(String breed) {
        this.breed = requireText(breed, "Breed");
    }

    public void setGender(String gender) {
        this.gender = requireText(gender, "Gender");
    }

    public void setAvailable(boolean available) {
        if (adopted && available) {
            throw new IllegalArgumentException(
                "An adopted pet cannot be marked available."
            );
        }
        this.available = available;
    }

    public abstract void makeSound();

    public void displayInfo() {
        System.out.println("Pet ID: " + petId);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Breed: " + breed);
        System.out.println("Gender: " + gender);
        System.out.println("Available: " + available);
    }

    @Override
    public void adopt() {
        if (adopted) {
            throw new PetAlreadyAdoptedException(
                name + " has already been adopted."
            );
        }

        adopted = true;
        available = false;
    }

    @Override
    public boolean isAdopted() {
        return adopted;
    }
}
