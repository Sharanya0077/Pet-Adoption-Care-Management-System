
package service;

import models.Appointment;
import models.MedicalRecord;
import models.Pet;
import models.Vaccination;
import models.Vet;

import java.util.ArrayList;
import java.util.List;

public class MedicalService {

    private List<MedicalRecord> medicalRecords;
    private List<Vaccination> vaccinations;
    private List<Appointment> appointments;

    public MedicalService() {
        medicalRecords = new ArrayList<>();
        vaccinations = new ArrayList<>();
        appointments = new ArrayList<>();
    }

    public void addMedicalRecord(MedicalRecord record) {
        medicalRecords.add(record);
    }

    public void addVaccination(Vaccination vaccination) {
        vaccinations.add(vaccination);
    }

    public void scheduleAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    // Restore records loaded from files
    public void setMedicalRecords(List<MedicalRecord> records) {
        medicalRecords.clear();
        medicalRecords.addAll(records);
    }

    public void setVaccinations(List<Vaccination> savedVaccinations) {
        vaccinations.clear();
        vaccinations.addAll(savedVaccinations);
    }

    public void setAppointments(List<Appointment> savedAppointments) {
        appointments.clear();
        appointments.addAll(savedAppointments);
    }

    // Display medical records for a particular pet
    public void displayMedicalRecords(Pet pet) {
        boolean found = false;

        for (MedicalRecord record : medicalRecords) {
            if (record.getPet().getPetId() == pet.getPetId()) {
                record.displayRecord();
                System.out.println("--------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No medical records found for " + pet.getName());
        }
    }

    // Display vaccinations for a particular pet
    public void displayVaccinations(Pet pet) {
        boolean found = false;

        for (Vaccination vaccination : vaccinations) {
            if (vaccination.getPet().getPetId() == pet.getPetId()) {
                vaccination.displayVaccination();
                System.out.println("--------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No vaccinations found for " + pet.getName());
        }
    }

    // Display appointments assigned to a particular vet
    public void displayAppointments(Vet vet) {
        boolean found = false;

        for (Appointment appointment : appointments) {
            if (appointment.getVet().getUserId() == vet.getUserId()) {
                appointment.displayAppointment();
                System.out.println("--------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No appointments found for Dr. " + vet.getName());
        }
    }

    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }

    public List<Vaccination> getVaccinations() {
        return vaccinations;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }
}
