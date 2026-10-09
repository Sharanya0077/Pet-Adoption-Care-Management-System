
package service;

import models.Appointment;
import models.MedicalRecord;
import models.Pet;
import models.Vaccination;
import models.Vet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MedicalService {

    private List<MedicalRecord> medicalRecords = new ArrayList<>();
    private List<Vaccination> vaccinations = new ArrayList<>();
    private List<Appointment> appointments = new ArrayList<>();

    public void addMedicalRecord(MedicalRecord record) {
        if (record == null) {
            throw new IllegalArgumentException(
                "Medical record cannot be null."
            );
        }

        for (MedicalRecord existing : medicalRecords) {
            if (existing.getRecordId() == record.getRecordId()) {
                throw new IllegalArgumentException(
                    "Duplicate medical record ID: " + record.getRecordId()
                );
            }
        }
        medicalRecords.add(record);
    }

    public void addVaccination(Vaccination vaccination) {
        if (vaccination == null) {
            throw new IllegalArgumentException(
                "Vaccination cannot be null."
            );
        }

        for (Vaccination existing : vaccinations) {
            if (existing.getVaccinationId()
                    == vaccination.getVaccinationId()) {
                throw new IllegalArgumentException(
                    "Duplicate vaccination ID: "
                    + vaccination.getVaccinationId()
                );
            }
        }
        vaccinations.add(vaccination);
    }

    public void scheduleAppointment(Appointment appointment) {
        if (appointment == null) {
            throw new IllegalArgumentException(
                "Appointment cannot be null."
            );
        }

        for (Appointment existing : appointments) {
            if (existing.getAppointmentId()
                    == appointment.getAppointmentId()) {
                throw new IllegalArgumentException(
                    "Duplicate appointment ID: "
                    + appointment.getAppointmentId()
                );
            }
        }
        appointments.add(appointment);
    }

    public void setMedicalRecords(List<MedicalRecord> records) {
        List<MedicalRecord> checked = new ArrayList<>();
        Set<Integer> ids = new HashSet<>();

        if (records == null) {
            throw new IllegalArgumentException("Records cannot be null.");
        }

        for (MedicalRecord record : records) {
            if (record == null || !ids.add(record.getRecordId())) {
                throw new IllegalArgumentException(
                    "Null or duplicate medical record."
                );
            }
            checked.add(record);
        }

        medicalRecords = checked;
    }

    public void setVaccinations(List<Vaccination> savedVaccinations) {
        List<Vaccination> checked = new ArrayList<>();
        Set<Integer> ids = new HashSet<>();

        if (savedVaccinations == null) {
            throw new IllegalArgumentException(
                "Vaccination list cannot be null."
            );
        }

        for (Vaccination vaccination : savedVaccinations) {
            if (vaccination == null
                    || !ids.add(vaccination.getVaccinationId())) {
                throw new IllegalArgumentException(
                    "Null or duplicate vaccination."
                );
            }
            checked.add(vaccination);
        }

        vaccinations = checked;
    }

    public void setAppointments(List<Appointment> savedAppointments) {
        List<Appointment> checked = new ArrayList<>();
        Set<Integer> ids = new HashSet<>();

        if (savedAppointments == null) {
            throw new IllegalArgumentException(
                "Appointment list cannot be null."
            );
        }

        for (Appointment appointment : savedAppointments) {
            if (appointment == null
                    || !ids.add(appointment.getAppointmentId())) {
                throw new IllegalArgumentException(
                    "Null or duplicate appointment."
                );
            }
            checked.add(appointment);
        }

        appointments = checked;
    }

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
        return Collections.unmodifiableList(medicalRecords);
    }

    public List<Vaccination> getVaccinations() {
        return Collections.unmodifiableList(vaccinations);
    }

    public List<Appointment> getAppointments() {
        return Collections.unmodifiableList(appointments);
    }
}
