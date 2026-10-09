
package models;

public class Vaccination {

    private int vaccinationId;
    private Pet pet;
    private String vaccineName;
    private String vaccinationDate;
    private String nextDueDate;

    public Vaccination(int vaccinationId, Pet pet, String vaccineName,
                       String vaccinationDate, String nextDueDate) {

        if (vaccinationId <= 0) {
            throw new IllegalArgumentException(
                "Vaccination ID must be positive."
            );
        }
        if (pet == null) {
            throw new IllegalArgumentException("Pet is required.");
        }

        this.vaccinationId = vaccinationId;
        this.pet = pet;
        this.vaccineName = requireText(vaccineName, "Vaccine name");
        this.vaccinationDate = requireText(
            vaccinationDate, "Vaccination date"
        );
        this.nextDueDate = requireText(nextDueDate, "Next due date");
    }

    private String requireText(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(
                field + " cannot be empty."
            );
        }
        return value.trim();
    }

    public int getVaccinationId() { return vaccinationId; }
    public Pet getPet() { return pet; }
    public String getVaccineName() { return vaccineName; }
    public String getVaccinationDate() { return vaccinationDate; }
    public String getNextDueDate() { return nextDueDate; }

    public void displayVaccination() {
        System.out.println("Vaccination ID: " + vaccinationId);
        System.out.println("Pet: " + pet.getName());
        System.out.println("Vaccine: " + vaccineName);
        System.out.println("Date: " + vaccinationDate);
        System.out.println("Next Due: " + nextDueDate);
    }
}

