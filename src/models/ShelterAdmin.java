
package models;

public class ShelterAdmin extends User {

    public ShelterAdmin(int userId, String name,
                        String email, String phone) {
        super(userId, name, email, phone);
    }

    @Override
    public void displayDashboard() {
        System.out.println("Shelter Administrator Dashboard");
        System.out.println("Welcome, " + getName() + "!");
    }

    public void approveApplication(AdoptionApplication application) {
        if (application == null) {
            System.out.println("Invalid application.");
            return;
        }

        if (!"Pending".equals(application.getStatus())) {
            System.out.println("This application has already been processed.");
            return;
        }

        application.approve();

        System.out.println(
            "Application " + application.getApplicationId()
            + " approved by the shelter admin."
        );

        application.getAdopter().sendNotification(
            "Your application for "
            + application.getPet().getName()
            + " has been approved!"
        );
    }

    public void rejectApplication(AdoptionApplication application) {
        if (application == null) {
            System.out.println("Invalid application.");
            return;
        }

        if (!"Pending".equals(application.getStatus())) {
            System.out.println("This application has already been processed.");
            return;
        }

        application.reject();

        System.out.println(
            "Application " + application.getApplicationId()
            + " has been rejected."
        );

        application.getAdopter().sendNotification(
            "Unfortunately, your application for "
            + application.getPet().getName()
            + " has been rejected."
        );
    }
}
