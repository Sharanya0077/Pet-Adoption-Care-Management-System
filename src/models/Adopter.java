package models;

public class Adopter extends User {

    public Adopter(int userId, String name, String email, String phone) {
        super(userId, name, email, phone);
    }

    @Override
    public void displayDashboard() {
        System.out.println("Adopter Dashboard");
        System.out.println("Welcome, " + getName() + "!");
    }
}