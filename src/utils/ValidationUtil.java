package utils;

import enums.BloodGroup;

public class ValidationUtil {

    public static boolean isValidAge(int age) {
        return age >= HospitalConfig.MIN_AGE &&
                age <= HospitalConfig.MAX_AGE;
    }

    public static boolean isValidPhone(String phone) {

        if (phone == null)
            return false;

        return phone.matches("\\d{" + HospitalConfig.PHONE_LENGTH + "}");
    }

    public static boolean isValidName(String name) {

        return name != null &&
                !name.trim().isEmpty();
    }

    public static boolean isValidBloodGroup(BloodGroup bloodGroup) {
        return bloodGroup != null;
    }
}