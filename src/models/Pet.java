package models;
import interfaces.Adoptable;
public abstract class Pet implements Adoptable {

    private int petId;
    private String name;
    private int age;
    private String breed;
    private String gender;
    private boolean available;
    private boolean adopted;

    // Constructor
    public Pet(int petId, String name, int age, String breed,
               String gender, boolean available) {
        this.petId = petId;
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.gender = gender;
        this.available = available;
        this.adopted = false;
    }

    // Getters
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

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    // Abstract method
    public abstract void makeSound();

    // Common method
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
    adopted = true;
    available = false;
}

@Override
public boolean isAdopted() {
    return adopted;
}
}