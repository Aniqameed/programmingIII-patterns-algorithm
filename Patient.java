import java.util.ArrayList;

public class Patient {
    String patientID;
    String name;
    String NIC;
    String email;
    String phone;

    public Patient(String patientID, String name, String NIC, String email, String phone) {
        this.patientID = patientID;
        this.name = name;
        this.NIC = NIC;
        this.email = email;
        this.phone = phone;
    }

    public static Patient searchPatient(ArrayList<Patient> patients, String searchTerm) {
        for (Patient patient : patients) {
            if (patient.patientID.equals(searchTerm) || patient.name.equalsIgnoreCase(searchTerm)) {
                return patient;
            }
        }
        return null;
    }
}
