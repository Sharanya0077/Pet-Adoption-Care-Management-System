package models;

public class Bird extends Pet {

    private boolean canFly;

    public Bird(int petId, String name, int age, String breed,
                String gender, boolean available, boolean canFly) {

        super(petId, name, age, breed, gender, available);
        this.canFly = canFly;
    }

    public boolean canFly() {
        return canFly;
    }

    public void setCanFly(boolean canFly) {
        this.canFly = canFly;
    }

    @Override
    public void makeSound() {
        System.out.println("Chirp!");
    }
}