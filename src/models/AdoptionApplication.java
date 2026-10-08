package models;

public class AdoptionApplication {
    private int applicationId;
    private Adopter adopter;
    private Pet pet;
    private String applicationDate;
    private String status;

    public AdoptionApplication(int applicationId, Adopter adopter,
                               Pet pet, String applicationDate) {

        this.applicationId = applicationId;
        this.adopter = adopter;
        this.pet = pet;
        this.applicationDate = applicationDate;
        this.status = "Pending";
    }

    public int getApplicationId() {
        return applicationId;
    }

    public Adopter getAdopter() {
        return adopter;
    }

    public Pet getPet() {
        return pet;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public void approve() {
    status = "Approved";
}

public void reject() {
    status = "Rejected";
}

    public void displayApplication() {
        System.out.println("Application ID: " + applicationId);
        System.out.println("Adopter: " + adopter.getName());
        System.out.println("Pet: " + pet.getName());
        System.out.println("Application Date: " + applicationDate);
        System.out.println("Status: " + status);
    }
}
