package models;

public class ShelterAdmin extends User {

    public ShelterAdmin(int userId, String name, String email, String phone) {
        super(userId, name, email, phone);
    }

    @Override
    public void displayDashboard() {
        System.out.println("Shelter Administrator Dashboard");
        System.out.println("Welcome, " + getName() + "!");
    }
}