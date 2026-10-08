package models;

public class Vaccination {

    private int vaccinationId;
    private Pet pet;
    private String vaccineName;
    private String vaccinationDate;
    private String nextDueDate;

    public Vaccination(int vaccinationId, Pet pet, String vaccineName,
                       String vaccinationDate, String nextDueDate) {

        this.vaccinationId = vaccinationId;
        this.pet = pet;
        this.vaccineName = vaccineName;
        this.vaccinationDate = vaccinationDate;
        this.nextDueDate = nextDueDate;
    }

    public int getVaccinationId() {
        return vaccinationId;
    }

    public Pet getPet() {
        return pet;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public String getVaccinationDate() {
        return vaccinationDate;
    }

    public String getNextDueDate() {
        return nextDueDate;
    }

    public void displayVaccination() {
        System.out.println("Vaccination ID: " + vaccinationId);
        System.out.println("Pet: " + pet.getName());
        System.out.println("Vaccine: " + vaccineName);
        System.out.println("Date: " + vaccinationDate);
        System.out.println("Next Due: " + nextDueDate);
    }
}