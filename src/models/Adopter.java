
package models;

import interfaces.Notifiable;

public class Adopter extends User implements Notifiable {

    public Adopter(int userId, String name,
                   String email, String phone) {
        super(userId, name, email, phone);
    }

    @Override
    public void displayDashboard() {
        System.out.println("Adopter Dashboard");
        System.out.println("Welcome, " + getName() + "!");
    }

    @Override
    public void sendNotification(String message) {
        System.out.println("\n========== NOTIFICATION ==========");
        System.out.println("To: " + getName());
        System.out.println("Message: " + message);
        System.out.println("==================================");
    }
}
