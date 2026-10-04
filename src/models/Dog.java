package models;

public class Dog extends Pet {

    private String trainingLevel;

    public Dog(int petId, String name, int age, String breed,
               String gender, boolean available, String trainingLevel) {

        super(petId, name, age, breed, gender, available);
        this.trainingLevel = trainingLevel;
    }

    public String getTrainingLevel() {
        return trainingLevel;
    }

    public void setTrainingLevel(String trainingLevel) {
        this.trainingLevel = trainingLevel;
    }

    @Override
    public void makeSound() {
        System.out.println("Woof!");
    }
}