import java.util.ArrayList;

public class Appointment {
    String appointmentID;
    String patientID;
    String date;
    String time;
    String dermatologist;
    String treatmentType;
    double treatmentCost;

    public Appointment(String appointmentID, String patientID, String date, String time, String dermatologist,
            String treatmentType, double treatmentCost) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.date = date;
        this.time = time;
        this.dermatologist = dermatologist;
        this.treatmentType = treatmentType;
        this.treatmentCost = treatmentCost;
    }

    public void updateDetails(String newDate, String newTime) {
        this.date = newDate;
        this.time = newTime;
    }

    public static Appointment searchAppointment(ArrayList<Appointment> appointments, String searchTerm) {
        for (Appointment appointment : appointments) {
            if (appointment.appointmentID.equals(searchTerm) || appointment.patientID.equals(searchTerm)) {
                return appointment;
            }
        }
        return null;
    }

    public static ArrayList<Appointment> filterAppointmentsByDate(ArrayList<Appointment> appointments, String date) {
        ArrayList<Appointment> filteredAppointments = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.date.equals(date)) {
                filteredAppointments.add(appointment);
            }
        }
        return filteredAppointments;
    }
}
