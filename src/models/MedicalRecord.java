package models;

public class MedicalRecord {

    private int recordId;
    private Pet pet;
    private String diagnosis;
    private String treatment;
    private String recordDate;

    public MedicalRecord(int recordId, Pet pet, String diagnosis,
                         String treatment, String recordDate) {

        this.recordId = recordId;
        this.pet = pet;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.recordDate = recordDate;
    }

    public int getRecordId() {
        return recordId;
    }

    public Pet getPet() {
        return pet;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public void displayRecord() {
        System.out.println("Record ID: " + recordId);
        System.out.println("Pet: " + pet.getName());
        System.out.println("Diagnosis: " + diagnosis);
        System.out.println("Treatment: " + treatment);
        System.out.println("Date: " + recordDate);
    }
}