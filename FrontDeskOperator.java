import java.util.ArrayList;
import java.util.Scanner;

public class FrontDeskOperator {
    private ConsultationSchedule schedule;
    private ArrayList<Appointment> appointments;
    private ArrayList<Dermatologist> dermatologists;

    public FrontDeskOperator(ConsultationSchedule schedule, ArrayList<Appointment> appointments,
            ArrayList<Dermatologist> dermatologists) {
        this.schedule = schedule;
        this.appointments = appointments;
        this.dermatologists = dermatologists;
    }

    public void makeAppointment(Patient patient, String date) {
        // Scanner scanner = new Scanner(System.in);
        try (Scanner scanner = new Scanner(System.in)) {

        //Show available time slots
        ArrayList<String> availableTimes = schedule.getAvailableSlots(date, appointments);
        System.out.println("Available Time Slots:");
        for (String time : availableTimes) {
            System.out.println("- " + time);
        }

        System.out.print("Choose Time: ");
        String time = scanner.nextLine();

        ArrayList<Dermatologist> availableDermatologists = schedule.getAvailableDermatologists(date, time, appointments,
                dermatologists);

        if (availableDermatologists.isEmpty()) {
            System.out.println("No available dermatologists for the selected date and time.");
            return;
        }

        System.out.println("Available Dermatologists:");
        for (Dermatologist dermatologist : availableDermatologists) {
            System.out.println("- " + dermatologist.getName() + " (" + dermatologist.getId() + ")");
        }

        System.out.print("Choose Dermatologist ID: ");
        String dermatologistID = scanner.nextLine();

        boolean isAvailable = availableDermatologists.stream()
                .anyMatch(doc -> doc.getId().equals(dermatologistID));

        if (!isAvailable) {
            System.out.println("Selected dermatologist is not available for the chosen time slot.");
            return;
        }

        System.out.print("Enter Treatment Type (e.g., Acne Treatment, Skin Whitening, etc.): ");
        String treatmentType = scanner.nextLine();
        double treatmentCost = Treatment.getCost(treatmentType);

        if (treatmentCost == 0) {
            System.out.println("Invalid treatment type entered.");
            return;
        }

        String appointmentID = "APPT" + (appointments.size() + 1);

        Appointment appointment = new Appointment(appointmentID, patient.patientID, date, time, dermatologistID,
                treatmentType, treatmentCost);
        appointments.add(appointment);
        System.out.println("Appointment successfully created for " + patient.name + " with " + dermatologistID + " on "
                + date + " at " + time);
    }
}
}
