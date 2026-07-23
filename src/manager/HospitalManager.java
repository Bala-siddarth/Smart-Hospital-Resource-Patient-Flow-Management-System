package manager;

import entities.Bill;
import entities.Patient;
import enums.*;
import exceptions.HospitalException;
import service.HospitalService;

import java.sql.SQLInput;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;

public class HospitalManager {

    private HospitalService hospitalService;
    private Scanner scanner;

    public HospitalManager() {
        hospitalService = new HospitalService();
        scanner = new Scanner(System.in);
    }

    public void registerPatient() {

        try {

            System.out.println("\n========== Patient Registration ==========");

            System.out.print("Enter Name : ");
            String name = scanner.nextLine();

            System.out.print("Enter Age : ");
            int age = Integer.parseInt(scanner.nextLine());

            System.out.println("Gender");
            System.out.println("1. MALE");
            System.out.println("2. FEMALE");
            System.out.println("3. OTHER");
            System.out.print("Choose : ");
            int genderChoice = Integer.parseInt(scanner.nextLine());

            Gender gender;

            switch (genderChoice) {
                case 1:
                    gender = Gender.MALE;
                    break;
                case 2:
                    gender = Gender.FEMALE;
                    break;
                case 3:
                    gender = Gender.OTHER;
                    break;
                default:
                    System.out.println("Invalid Gender.");
                    return;
            }

            System.out.print("Enter Phone Number : ");
            String phone = scanner.nextLine();

            System.out.println("\nBlood Groups");

            BloodGroup[] bloodGroups = BloodGroup.values();

            for (int i = 0; i < bloodGroups.length; i++) {
                System.out.println((i + 1) + ". " + bloodGroups[i]);
            }

            System.out.print("Choose : ");
            int bloodChoice = Integer.parseInt(scanner.nextLine());

            if (bloodChoice < 1 || bloodChoice > bloodGroups.length) {
                System.out.println("Invalid Blood Group.");
                return;
            }

            BloodGroup bloodGroup = bloodGroups[bloodChoice - 1];

            System.out.print("Emergency Patient (Y/N) : ");
            boolean emergency = scanner.nextLine().equalsIgnoreCase("Y");

            SeverityLevel severity;

            if (emergency) {

                System.out.println("\nSeverity");

                SeverityLevel[] levels = SeverityLevel.values();

                for (int i = 0; i < levels.length; i++) {
                    System.out.println((i + 1) + ". " + levels[i]);
                }

                System.out.print("Choose : ");
                int severityChoice = Integer.parseInt(scanner.nextLine());

                if (severityChoice < 1 || severityChoice > levels.length) {
                    System.out.println("Invalid Severity.");
                    return;
                }

                severity = levels[severityChoice - 1];

            } else {

                severity = SeverityLevel.LOW;

            }
            System.out.println("Select Required Doctor Specialization");
            System.out.println("1. General Physician");
            System.out.println("2. Cardiologist");
            System.out.println("3. Neurologist");
            System.out.println("4. Orthopedic");
            System.out.println("5. Pediatrician");
            int choicek = scanner.nextInt();
            scanner.nextLine();
            Patient patient = new Patient(
                    null,
                    name,
                    age,
                    gender,
                    phone,
                    bloodGroup,
                    emergency,
                    severity
            );
            patient.setRequiredSpecialization(getSpecialization(choicek));
            hospitalService.registerPatient(patient);

            System.out.println("\nPatient Registered Successfully.");
            System.out.println("Patient ID : " + patient.getPatientId());

        } catch (HospitalException e) {

            System.out.println(e.getMessage());

        } catch (Exception e) {

            System.out.println("Invalid Input.");

        }

    }

    public void searchPatient() {

        try {

            System.out.println("\n========== Search Patient ==========");

            System.out.print("Enter Patient ID : ");
            String patientId = scanner.nextLine();

            Patient patient = hospitalService.searchPatient(patientId);

            System.out.println("\nPatient Found");
            System.out.println(patient);

        } catch (HospitalException e) {

            System.out.println(e.getMessage());

        }

    }
    public void updatePatient() {

        try {

            System.out.println("\n========== Update Patient ==========");

            System.out.print("Enter Patient ID : ");
            String patientId = scanner.nextLine();

            System.out.print("Enter New Name : ");
            String name = scanner.nextLine();

            System.out.print("Enter New Age : ");
            int age = Integer.parseInt(scanner.nextLine());

            System.out.println("Gender");
            System.out.println("1. MALE");
            System.out.println("2. FEMALE");
            System.out.println("3. OTHER");
            System.out.print("Choose : ");

            int genderChoice = Integer.parseInt(scanner.nextLine());

            Gender gender;

            switch (genderChoice) {

                case 1:
                    gender = Gender.MALE;
                    break;

                case 2:
                    gender = Gender.FEMALE;
                    break;

                case 3:
                    gender = Gender.OTHER;
                    break;

                default:
                    System.out.println("Invalid Gender.");
                    return;

            }

            System.out.print("Enter New Phone : ");
            String phone = scanner.nextLine();

            System.out.println("\nBlood Groups");

            BloodGroup[] bloodGroups = BloodGroup.values();

            for (int i = 0; i < bloodGroups.length; i++) {

                System.out.println((i + 1) + ". " + bloodGroups[i]);

            }

            System.out.print("Choose : ");

            int bloodChoice = Integer.parseInt(scanner.nextLine());

            if (bloodChoice < 1 || bloodChoice > bloodGroups.length) {

                System.out.println("Invalid Blood Group.");
                return;

            }

            BloodGroup bloodGroup = bloodGroups[bloodChoice - 1];

            Patient updatedPatient = new Patient();

            updatedPatient.setPatientId(patientId);
            updatedPatient.setName(name);
            updatedPatient.setAge(age);
            updatedPatient.setGender(gender);
            updatedPatient.setPhone(phone);
            updatedPatient.setBloodGroup(bloodGroup);

            hospitalService.updatePatient(updatedPatient);

            System.out.println("\nPatient Updated Successfully.");

        } catch (HospitalException e) {

            System.out.println(e.getMessage());

        } catch (Exception e) {

            System.out.println("Invalid Input.");

        }

    }
    private DoctorSpecialization getSpecialization(int choicek)
            throws HospitalException {

        switch (choicek) {

            case 1:
                return DoctorSpecialization.GENERAL_PHYSICIAN;

            case 2:
                return DoctorSpecialization.CARDIOLOGIST;

            case 3:
                return DoctorSpecialization.NEUROLOGIST;

            case 4:
                return DoctorSpecialization.ORTHOPEDIC;

            case 5:
                return DoctorSpecialization.PEDIATRICIAN;

            default:
                throw new HospitalException("Invalid specialization.");

        }
    }
    public void admitPatient() {

        try {

            System.out.println("\n========== Admit Patient ==========");

            System.out.print("Enter Patient ID : ");
            String patientId = scanner.nextLine();

            hospitalService.admitPatient(patientId);

            System.out.println("\nPatient Admitted Successfully.");

        } catch (HospitalException e) {

            System.out.println(e.getMessage());

        }

    }
    public void scheduleAppointment() {

        try {

            System.out.println("\n========== Schedule Appointment ==========");

            System.out.print("Enter Patient ID : ");
            String patientId = scanner.nextLine();

            System.out.print("Enter Date (yyyy-MM-dd) : ");
            String date = scanner.nextLine();

            System.out.print("Enter Time (HH:mm) : ");
            String time = scanner.nextLine();

            LocalDate appointmentDate = LocalDate.parse(date);
            LocalTime appointmentTime = LocalTime.parse(time);

            LocalDateTime dateTime =
                    LocalDateTime.of(appointmentDate, appointmentTime);

            hospitalService.scheduleAppointment(patientId, dateTime);

            System.out.println("\nAppointment Scheduled Successfully.");

        } catch (HospitalException e) {

            System.out.println(e.getMessage());

        } catch (Exception e) {

            System.out.println("Invalid Date or Time.");

        }

    }

    public void addEmergencyPatient() {

        try {

            System.out.println("\n========== Add Emergency Patient ==========");

            System.out.print("Enter Patient ID : ");
            String patientId = scanner.nextLine();

            hospitalService.addEmergencyPatient(patientId);

            System.out.println("Patient added to emergency queue.");

        } catch (HospitalException e) {

            System.out.println(e.getMessage());

        }

    }
    public void admitNextEmergencyPatient() {

        try {

            Patient patient = hospitalService.admitNextEmergencyPatient();

            System.out.println("\nEmergency Patient "
                    + patient.getPatientId()
                    + " admitted successfully.");

        } catch (HospitalException e) {

            System.out.println(e.getMessage());

        }

    }
    public void transferPatient() {

        try {

            System.out.println("\n========== Transfer Patient ==========");

            System.out.print("Enter Patient ID : ");
            String patientId = scanner.nextLine();

            System.out.println("\nTransfer To");

            System.out.println("1. GENERAL");
            System.out.println("2. ICU");

            System.out.print("Choose : ");

            int choice = Integer.parseInt(scanner.nextLine());

            WardType wardType;

            switch (choice) {

                case 1:
                    wardType = WardType.GENERAL;
                    break;

                case 2:
                    wardType = WardType.ICU;
                    break;

                default:
                    System.out.println("Invalid Choice.");
                    return;

            }

            hospitalService.transferPatient(patientId, wardType);

            System.out.println("\nPatient Transferred Successfully.");

        }

        catch (HospitalException e) {

            System.out.println(e.getMessage());

        }

        catch (Exception e) {

            System.out.println("Invalid Input.");

        }

    }
    public void generateBill() {

        try {

            System.out.println("\n========== Generate Bill ==========");

            System.out.print("Enter Patient ID : ");

            String patientId = scanner.nextLine();

            hospitalService.generateBill(patientId);

        }

        catch (HospitalException e) {

            System.out.println(e.getMessage());

        }

    }
    public void dischargePatient() {

        try {

            System.out.println("\n========== Discharge Patient ==========");

            System.out.print("Enter Patient ID : ");

            String patientId = scanner.nextLine();

            hospitalService.dischargePatient(patientId);

            System.out.println("\nPatient Discharged Successfully.");

        }

        catch (HospitalException e) {

            System.out.println(e.getMessage());

        }

    }
    public void reports() {

        int choice;

        do {

            System.out.println("\n========== REPORTS ==========");

            System.out.println("1. All Patients");
            System.out.println("2. Admitted Patients");
            System.out.println("3. Doctors");
            System.out.println("4. Ward Status");
            System.out.println("5. Appointments");
            System.out.println("6. Bills");
            System.out.println("0. Back");

            System.out.print("Enter Choice : ");

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {

                case 1:
                    hospitalService.displayAllPatients();
                    break;

                case 2:
                    hospitalService.displayAdmittedPatients();
                    break;

                case 3:
                    hospitalService.displayDoctors();
                    break;

                case 4:
                    hospitalService.displayWardStatus();
                    break;

                case 5:
                    hospitalService.displayAppointments();
                    break;

                case 6:
                    hospitalService.displayBills();
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Invalid Choice.");

            }

        } while (choice != 0);

    }

}