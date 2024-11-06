import java.util.ArrayList;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConsultationSchedule {
    private final String[][] timeSlots = {
            { "10:00", "10:15", "10:30", "10:45", "11:00", "11:15", "11:30", "11:45", "12:00" }, // Mon
            { "14:00", "14:15", "14:30", "14:45", "15:00", "15:15", "15:30", "15:45", "16:00" }, // Wed
            { "16:00", "16:15", "16:30", "16:45", "17:00", "17:15", "17:30", "17:45", "18:00" }, // Fri
            { "09:00", "09:15", "09:30", "09:45", "10:00", "10:15", "10:30", "10:45", "11:00" } // Sat
    };

    private final DayOfWeek[] allowedDays = { DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY,
            DayOfWeek.SATURDAY };

    public ArrayList<String> getAvailableSlots(String date, ArrayList<Appointment> appointments) {
        ArrayList<String> availableTimes = new ArrayList<>();
        DayOfWeek dayOfWeek = extractDayOfWeekFromDate(date);
        int dayIndex = getDayIndex(dayOfWeek);

        if (dayIndex == -1)
            return availableTimes;

        for (String slot : timeSlots[dayIndex]) {
            availableTimes.add(slot);
        }
        return availableTimes;
    }

    //Returns doctors available at a given date and time
    public ArrayList<Dermatologist> getAvailableDermatologists(String date, String time,
            ArrayList<Appointment> appointments, ArrayList<Dermatologist> allDermatologists) {
        ArrayList<Dermatologist> availableDermatologists = new ArrayList<>(allDermatologists);

        for (Appointment appointment : appointments) {
            if (appointment.date.equals(date) && appointment.time.equals(time)) {
                //Remove booked dermatologist for the specific slot
                availableDermatologists.removeIf(doc -> doc.getId().equals(appointment.dermatologist));
            }
        }
        return availableDermatologists;
    }

    public int getDayIndex(DayOfWeek dayOfWeek) {
        for (int i = 0; i < allowedDays.length; i++) {
            if (allowedDays[i] == dayOfWeek) {
                return i;
            }
        }
        return -1; 
    }

    public DayOfWeek extractDayOfWeekFromDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate.getDayOfWeek();
    }
}
