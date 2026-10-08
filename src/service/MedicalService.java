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

    public void displayMedicalRecords(Pet pet) {
        for (MedicalRecord record : medicalRecords) {
            if (record.getPet().getPetId() == pet.getPetId()) {
                record.displayRecord();
                System.out.println("--------------------");
            }
        }
    }

    public void displayVaccinations(Pet pet) {
        for (Vaccination vaccination : vaccinations) {
            if (vaccination.getPet().getPetId() == pet.getPetId()) {
                vaccination.displayVaccination();
                System.out.println("--------------------");
            }
        }
    }

    public void displayAppointments(Vet vet) {
        for (Appointment appointment : appointments) {
            if (appointment.getVet().getUserId() == vet.getUserId()) {
                appointment.displayAppointment();
                System.out.println("--------------------");
            }
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