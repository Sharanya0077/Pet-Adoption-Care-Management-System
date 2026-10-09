
package models;

public class AdoptionApplication {

    private int applicationId;
    private Adopter adopter;
    private Pet pet;
    private String applicationDate;
    private String status;

    public AdoptionApplication(int applicationId, Adopter adopter,
                               Pet pet, String applicationDate) {

        if (applicationId <= 0) {
            throw new IllegalArgumentException(
                "Application ID must be positive."
            );
        }

        if (adopter == null || pet == null) {
            throw new IllegalArgumentException(
                "Adopter and pet are required."
            );
        }

        this.applicationDate = requireText(
            applicationDate, "Application date"
        );

        this.applicationId = applicationId;
        this.adopter = adopter;
        this.pet = pet;
        this.status = "Pending";
    }

    private String requireText(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(
                field + " cannot be empty."
            );
        }
        return value.trim();
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
        if (!"Pending".equals(status)
                && !"Approved".equals(status)
                && !"Rejected".equals(status)) {
            throw new IllegalArgumentException(
                "Invalid application status."
            );
        }
        this.status = status;
    }

    public void approve() {
        if (!"Pending".equals(status)) {
            throw new IllegalStateException(
                "Only pending applications can be approved."
            );
        }
        status = "Approved";
    }

    public void reject() {
        if (!"Pending".equals(status)) {
            throw new IllegalStateException(
                "Only pending applications can be rejected."
            );
        }
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
