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

        this.appointmentId = appointmentId;
        this.pet = pet;
        this.vet = vet;
        this.appointmentDate = appointmentDate;
        this.reason = reason;
        this.status = "Scheduled";
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public Pet getPet() {
        return pet;
    }

    public Vet getVet() {
        return vet;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public String getReason() {
        return reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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