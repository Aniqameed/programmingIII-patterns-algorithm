public class TreatmentRecord {
    String treatmentID;
    String patientID;
    String treatmentType;
    String date;
    String dermatologist;

    public TreatmentRecord(String treatmentID, String patientID, String treatmentType, String date,
            String dermatologist) {
        this.treatmentID = treatmentID;
        this.patientID = patientID;
        this.treatmentType = treatmentType;
        this.date = date;
        this.dermatologist = dermatologist;
    }
}
