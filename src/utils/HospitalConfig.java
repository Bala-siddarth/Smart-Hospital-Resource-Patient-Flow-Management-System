package utils;

import java.time.LocalTime;

public final class HospitalConfig {

    private HospitalConfig() {
        // Prevent object creation
    }
    // Hospital Working hours

    public static final int HOSPITAL_OPEN_HOUR = 8;
    public static final int HOSPITAL_CLOSE_HOUR = 20;

       //Billing Configuration

    public static final double ADMISSION_FEE = 500;

    public static final double CONSULTATION_FEE = 300;

    public static final double GENERAL_WARD_CHARGE = 1000;

    public static final double ICU_WARD_CHARGE = 5000;

    public static final double EMERGENCY_CHARGE = 2000;


    //Doctor Configuration

    public static final int MAX_PATIENTS_PER_DOCTOR = 20;


       //Ward Configuration

    public static final int MAX_GENERAL_BEDS = 50;
    public static final int MAX_ICU_BEDS = 10;

       //Patient Validation

    public static final int MIN_AGE = 0;
    public static final int MAX_AGE = 120;
    public static final int PHONE_LENGTH = 10;

      // Billing Discounts

    public static final double SENIOR_CITIZEN_DISCOUNT = 0.10; // 10%

    public static final LocalTime HOSPITAL_OPEN_TIME =
            LocalTime.of(9, 0);

    public static final LocalTime HOSPITAL_CLOSE_TIME =
            LocalTime.of(18, 0);
}