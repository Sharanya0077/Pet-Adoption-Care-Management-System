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

    public void approveApplication(AdoptionApplication application) {

    if (!application.getStatus().equals("Approved")) {
        System.out.println(
            "Application must be approved by an admin first."
        );
        return;
    }

    Pet pet = application.getPet();
    pet.adopt();

    System.out.println(
        "Application " + application.getApplicationId()
        + " approved. " + pet.getName()
        + " has been adopted by "
        + application.getAdopter().getName() + "!"
    );
}

    public void rejectApplication(AdoptionApplication application) {
        application.reject();
        System.out.println(
            "Application " + application.getApplicationId()
            + " has been rejected."
        );
    }
}