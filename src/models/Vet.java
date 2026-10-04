package models;

public class Vet extends User {

    private String specialization;

    public Vet(int userId, String name, String email, String phone,
               String specialization) {

        super(userId, name, email, phone);
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    @Override
    public void displayDashboard() {
        System.out.println("Veterinarian Dashboard");
        System.out.println("Welcome, Dr. " + getName() + "!");
        System.out.println("Specialization: " + specialization);
    }
}