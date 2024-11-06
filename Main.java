import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Patient> patients = new ArrayList<>();
        ArrayList<Appointment> appointments = new ArrayList<>();
        ArrayList<Dermatologist> dermatologists = new ArrayList<>();
        ConsultationSchedule consultationSchedule = new ConsultationSchedule();
        double REGISTRATION_FEE = 500.00;
        Scanner scanner = new Scanner(System.in);

        //Sample Dermatologist Data
        dermatologists.add(new Dermatologist("D01", "Dr. Smith", "Acne Specialist"));
        dermatologists.add(new Dermatologist("D02", "Dr. John", "Laser Specialist"));

        //Sample Patient Data
        patients.add(new Patient("P01", "John", "123456789V", "john@e.com", "0771234567"));
        patients.add(new Patient("P02", "Smith", "987654321V", "smith@e.com", "0712345678"));

        while (true) {
            System.out.println("\n--- Aurora Skin Care Management System ---");
            System.out.println("1. Create New Patient");
            System.out.println("2. Make Appointment");
            System.out.println("3. Update Appointment Details");
            System.out.println("4. View Appointments by Date");
            System.out.println("5. Search for an Appointment");
            System.out.println("6. Generate Invoice");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Create New Patient
                    System.out.print("Enter Patient ID: ");
                    String newPatientID = scanner.nextLine();
                    System.out.print("Enter Patient Name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter NIC: ");
                    String newNIC = scanner.nextLine();
                    System.out.print("Enter Email: ");
                    String newEmail = scanner.nextLine();
                    System.out.print("Enter Phone Number: ");
                    String newPhone = scanner.nextLine();

                    Patient newPatient = new Patient(newPatientID, newName, newNIC, newEmail, newPhone);
                    patients.add(newPatient);
                    System.out.println("New patient created successfully with ID: " + newPatientID);
                    break;

                case 2:
                    //Make Appointment
                    System.out.print("Enter Patient ID: ");
                    String patientID = scanner.nextLine();
                    Patient patient = Patient.searchPatient(patients, patientID);
                    if (patient != null) {
                        System.out.println("Available Treatments:");
                        System.out.println(" Acne Treatment - LKR 2750.00");
                        System.out.println(" Skin Whitening - LKR 7650.00");
                        System.out.println(" Mole Removal - LKR 3850.00");
                        System.out.println(" Laser Treatment - LKR 12500.00");
                        System.out.print("Enter Treatment Type: ");
                        String treatmentType = scanner.nextLine();
                        double treatmentCost = Treatment.getCost(treatmentType);
                        if (treatmentCost == 0) {
                            System.out.println("Invalid treatment type. Please try again.");
                            break;
                        }

                        System.out.print("Enter Appointment Date (yyyy-MM-dd): ");
                        String date = scanner.nextLine();
                        if (consultationSchedule
                                .getDayIndex(consultationSchedule.extractDayOfWeekFromDate(date)) == -1) {
                            System.out.println(
                                    "Appointments can only be booked on Monday, Wednesday, Friday, or Saturday.");
                            break;
                        }

                        System.out.println("Available Times on " + date + ":");
                        ArrayList<String> availableTimes = consultationSchedule.getAvailableSlots(date, appointments);
                        if (availableTimes.isEmpty()) {
                            System.out.println("No available slots for this date.");
                            break;
                        }
                        for (int i = 0; i < availableTimes.size(); i++) {
                            System.out.println((i + 1) + ". " + availableTimes.get(i));
                        }
                        System.out.print("Choose Time: ");
                        int timeChoice = scanner.nextInt();
                        scanner.nextLine();
                        if (timeChoice < 1 || timeChoice > availableTimes.size()) {
                            System.out.println("Invalid time selection.");
                            break;
                        }
                        String selectedTime = availableTimes.get(timeChoice - 1);

                        System.out.println("Available Dermatologists at " + selectedTime + ":");
                        ArrayList<Dermatologist> availableDermatologists = getAvailableDermatologists(date,
                                selectedTime, appointments, dermatologists);
                        if (availableDermatologists.isEmpty()) {
                            System.out.println("No dermatologists available for the selected time.");
                            break;
                        }

                        for (int i = 0; i < availableDermatologists.size(); i++) {
                            System.out.println((i + 1) + ". " + availableDermatologists.get(i));
                        }
                        System.out.print("Choose Dermatologist: ");
                        int dermatologistChoice = scanner.nextInt();
                        scanner.nextLine();
                        if (dermatologistChoice < 1 || dermatologistChoice > availableDermatologists.size()) {
                            System.out.println("Invalid dermatologist selection.");
                            break;
                        }
                        Dermatologist selectedDermatologist = availableDermatologists.get(dermatologistChoice - 1);

                        Appointment newAppointment = new Appointment(
                                "APT" + (appointments.size() + 1),
                                patientID, date, selectedTime,
                                selectedDermatologist.getName(), treatmentType,
                                treatmentCost);
                        appointments.add(newAppointment);
                        
                        Payment payment = new Payment(REGISTRATION_FEE, treatmentCost);
                        System.out.println("Appointment created with ID: " + newAppointment.appointmentID);
                    } else {
                        System.out.println("Patient not found.");
                    }
                    break;

                case 3:
                    //Update Appointment Details
                    System.out.print("Enter Appointment ID to update: ");
                    String appointmentID = scanner.nextLine();
                    Appointment appointmentToUpdate = Appointment.searchAppointment(appointments, appointmentID);
                    if (appointmentToUpdate != null) {
                        System.out.print("Enter New Date (yyyy-MM-dd): ");
                        String newDate = scanner.nextLine();
                        System.out.print("Enter New Time: ");
                        String newTime = scanner.nextLine();

                        boolean isAvailable = checkDermatologistAvailability(appointmentToUpdate.dermatologist, newDate,
                                newTime, appointments);
                        if (!isAvailable) {
                            System.out
                                    .println("The selected dermatologist is not available at this new date and time.");
                        } else {
                            appointmentToUpdate.updateDetails(newDate, newTime);
                            System.out.println("Appointment updated successfully.");
                        }
                    } else {
                        System.out.println("Appointment not found.");
                    }
                    break;

                case 4:
                    //View Appointments by Date
                    System.out.print("Enter Date (yyyy-MM-dd): ");
                    String viewDate = scanner.nextLine();
                    ArrayList<Appointment> filteredAppointments = Appointment.filterAppointmentsByDate(appointments,
                            viewDate);
                    if (filteredAppointments.isEmpty()) {
                        System.out.println("No appointments found for this date.");
                    } else {
                        for (Appointment appt : filteredAppointments) {
                            System.out.println("Appointment ID: " + appt.appointmentID + ", Patient Name: "
                                    + patients.stream()
                                            .filter(p -> p.patientID.equals(appt.patientID))
                                            .findFirst().orElse(null).name
                                    +
                                    ", Dermatologist: " + appt.dermatologist +
                                    ", Treatment: " + appt.treatmentType +
                                    ", Date: " + appt.date +
                                    ", Time: " + appt.time +
                                    ", Treatment Cost: LKR " + appt.treatmentCost);
                        }
                    }
                    break;

                case 5:
                    //Search for an Appointment
                    System.out.print("Enter Patient ID or Appointment ID to search: ");
                    String searchTerm = scanner.nextLine();
                    Appointment searchedAppointment = Appointment.searchAppointment(appointments, searchTerm);
                    if (searchedAppointment != null) {
                        System.out.println("Appointment found: " + searchedAppointment.appointmentID +
                                ", Patient: " + patients.stream()
                                        .filter(p -> p.patientID.equals(searchedAppointment.patientID))
                                        .findFirst().orElse(null).name
                                +
                                ", Dermatologist: " + searchedAppointment.dermatologist);
                    } else {
                        System.out.println("Appointment not found.");
                    }
                    break;

                case 6:
                    //Generate Invoice
                    System.out.print("Enter Appointment ID for Invoice: ");
                    String invoiceAppointmentID = scanner.nextLine();
                    Appointment invoiceAppointment = Appointment.searchAppointment(appointments, invoiceAppointmentID);
                    if (invoiceAppointment != null) {
                        Payment invoicePayment = new Payment(REGISTRATION_FEE, invoiceAppointment.treatmentCost);
                        String invoice = Invoice.generateInvoice(invoicePayment, invoiceAppointment, patients);
                        System.out.println(invoice);
                    } else {
                        System.out.println("Appointment not found.");
                    }
                    break;
                case 7:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static ArrayList<Dermatologist> getAvailableDermatologists(String date, String time,
            ArrayList<Appointment> appointments, ArrayList<Dermatologist> dermatologists) {
        ArrayList<Dermatologist> availableDermatologists = new ArrayList<>();
        for (Dermatologist doc : dermatologists) {
            boolean isAvailable = true;
            for (Appointment appointment : appointments) {
                if (appointment.date.equals(date) && appointment.time.equals(time)
                        && appointment.dermatologist.equals(doc.getName())) {
                    isAvailable = false;
                    break;
                }
            }
            if (isAvailable) {
                availableDermatologists.add(doc);
            }
        }
        return availableDermatologists;
    }

    private static boolean checkDermatologistAvailability(String dermatologistName, String date, String time,
            ArrayList<Appointment> appointments) {
        for (Appointment appointment : appointments) {
            if (appointment.date.equals(date) && appointment.time.equals(time)
                    && appointment.dermatologist.equals(dermatologistName)) {
                return false;
            }
        }
        return true;
    }
}
