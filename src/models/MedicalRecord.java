
package models;

public class MedicalRecord {

    private int recordId;
    private Pet pet;
    private String diagnosis;
    private String treatment;
    private String recordDate;

    public MedicalRecord(int recordId, Pet pet, String diagnosis,
                         String treatment, String recordDate) {

        if (recordId <= 0) {
            throw new IllegalArgumentException(
                "Medical record ID must be positive."
            );
        }
        if (pet == null) {
            throw new IllegalArgumentException("Pet is required.");
        }

        this.recordId = recordId;
        this.pet = pet;
        this.diagnosis = requireText(diagnosis, "Diagnosis");
        this.treatment = requireText(treatment, "Treatment");
        this.recordDate = requireText(recordDate, "Record date");
    }

    private String requireText(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(
                field + " cannot be empty."
            );
        }
        return value.trim();
    }

    public int getRecordId() { return recordId; }
    public Pet getPet() { return pet; }
    public String getDiagnosis() { return diagnosis; }
    public String getTreatment() { return treatment; }
    public String getRecordDate() { return recordDate; }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = requireText(diagnosis, "Diagnosis");
    }

    public void setTreatment(String treatment) {
        this.treatment = requireText(treatment, "Treatment");
    }

    public void displayRecord() {
        System.out.println("Record ID: " + recordId);
        System.out.println("Pet: " + pet.getName());
        System.out.println("Diagnosis: " + diagnosis);
        System.out.println("Treatment: " + treatment);
        System.out.println("Date: " + recordDate);
    }
}
