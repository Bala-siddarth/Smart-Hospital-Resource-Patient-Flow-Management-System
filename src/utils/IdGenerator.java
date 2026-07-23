package utils;

public class IdGenerator {

    private static int patientCounter = 1001;
    private static int doctorCounter = 201;
    private static int wardCounter = 11;
    private static int appointmentCounter = 5001;
    private static int billCounter = 9001;

    public static String generatePatientId() {
        return "PAT" + patientCounter++;
    }

    public static String generateDoctorId() {
        return "DOC" + doctorCounter++;
    }

    public static String generateWardId() {
        return "WRD" + wardCounter++;
    }

    public static String generateAppointmentId() {
        return "APP" + appointmentCounter++;
    }

    public static String generateBillId() {
        return "BILL" + billCounter++;
    }
}