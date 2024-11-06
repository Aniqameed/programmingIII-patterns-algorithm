import java.util.ArrayList;

public class Invoice {
    public static String generateInvoice(Payment payment, Appointment appointment, ArrayList<Patient> patients) {
        double totalAmount = payment.getTotalAmount();
        Patient patient = Patient.searchPatient(patients, appointment.patientID);
        return "Invoice for Appointment ID: " + appointment.appointmentID + "\n" +
                "Patient Name: " + patient.name + "\n" +
                "Dermatologist: " + appointment.dermatologist + "\n" +
                "Treatment: " + appointment.treatmentType + "\n" +
                "Treatment Cost: LKR " + appointment.treatmentCost + "\n" +
                "Registration Fee: LKR " + payment.registrationFee + "\n" +
                "Tax (2.5%): LKR " + (appointment.treatmentCost * 0.025) + "\n" +
                "Total Amount: LKR " + Math.ceil(totalAmount);
    }
}
