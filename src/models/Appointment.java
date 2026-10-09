
package models;

public class Appointment {

    private int appointmentId;
    private Pet pet;
    private Vet vet;
    private String appointmentDate;
    private String reason;
    private String status;

    public Appointment(int appointmentId, Pet pet, Vet vet,
                       String appointmentDate, String reason) {

        if (appointmentId <= 0) {
            throw new IllegalArgumentException(
                "Appointment ID must be positive."
            );
        }
        if (pet == null || vet == null) {
            throw new IllegalArgumentException(
                "Pet and vet are required."
            );
        }

        this.appointmentId = appointmentId;
        this.pet = pet;
        this.vet = vet;
        this.appointmentDate = requireText(
            appointmentDate, "Appointment date"
        );
        this.reason = requireText(reason, "Reason");
        this.status = "Scheduled";
    }

    private String requireText(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(
                field + " cannot be empty."
            );
        }
        return value.trim();
    }

    public int getAppointmentId() { return appointmentId; }
    public Pet getPet() { return pet; }
    public Vet getVet() { return vet; }
    public String getAppointmentDate() { return appointmentDate; }
    public String getReason() { return reason; }
    public String getStatus() { return status; }

    public void setStatus(String status) {
        if (!"Scheduled".equals(status)
                && !"Completed".equals(status)
                && !"Cancelled".equals(status)) {
            throw new IllegalArgumentException(
                "Invalid appointment status."
            );
        }
        this.status = status;
    }

    public void displayAppointment() {
        System.out.println("Appointment ID: " + appointmentId);
        System.out.println("Pet: " + pet.getName());
        System.out.println("Vet: Dr. " + vet.getName());
        System.out.println("Date: " + appointmentDate);
        System.out.println("Reason: " + reason);
        System.out.println("Status: " + status);
    }
}
