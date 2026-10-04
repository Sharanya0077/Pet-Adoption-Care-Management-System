package models;

public class Cat extends Pet {

    private boolean indoorOnly;

    public Cat(int petId, String name, int age, String breed,
               String gender, boolean available, boolean indoorOnly) {

        super(petId, name, age, breed, gender, available);
        this.indoorOnly = indoorOnly;
    }

    public boolean isIndoorOnly() {
        return indoorOnly;
    }

    public void setIndoorOnly(boolean indoorOnly) {
        this.indoorOnly = indoorOnly;
    }

    @Override
    public void makeSound() {
        System.out.println("Meow!");
    }
}