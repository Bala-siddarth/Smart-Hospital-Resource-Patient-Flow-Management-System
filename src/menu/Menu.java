package menu;

import manager.HospitalManager;

import java.util.Scanner;

public class Menu {

    private Scanner scanner;
    private HospitalManager hospitalManager;

    public Menu() {
        scanner = new Scanner(System.in);
        hospitalManager = new HospitalManager();
    }

    public void start() {

        int choice;

        do {
            System.out.println(" SMART HOSPITAL MANAGEMENT SYSTEM");
            System.out.println("1. Register Patient");
            System.out.println("2. Search Patient");
            System.out.println("3. Update Patient");
            System.out.println("4. Admit Patient");
            System.out.println("5. Schedule Appointment");
            System.out.println("6. Emergency Admission");
            System.out.println("7. Transfer Patient");
            System.out.println("8. Discharge Patient");
            System.out.println("9. Billing");
            System.out.println("10. Reports");
            System.out.println("0. Exit");

            System.out.print("\nEnter Choice : ");

            try {

                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1:
                        hospitalManager.registerPatient();
                        break;

                    case 2:
                        hospitalManager.searchPatient();
                        break;

                    case 3:
                        hospitalManager.updatePatient();
                        break;

                    case 4:
                        hospitalManager.admitPatient();
                        break;

                    case 5:
                        hospitalManager.scheduleAppointment();
                        break;

                    case 6:

                        int emergencyChoice;

                        do {

                            System.out.println("\n===== Emergency Menu =====");

                            System.out.println("1. Add Emergency Patient");
                            System.out.println("2. Admit Next Emergency Patient");
                            System.out.println("0. Back");

                            System.out.print("Enter Choice : ");

                            emergencyChoice = Integer.parseInt(scanner.nextLine());

                            switch (emergencyChoice) {

                                case 1:
                                    hospitalManager.addEmergencyPatient();
                                    break;

                                case 2:
                                    hospitalManager.admitNextEmergencyPatient();
                                    break;

                                case 0:
                                    break;

                                default:
                                    System.out.println("Invalid Choice.");

                            }

                        } while (emergencyChoice != 0);

                        break;

                    case 7:
                        hospitalManager.transferPatient();
                        break;

                    case 8:
                        hospitalManager.dischargePatient();
                        break;

                    case 9:
                        hospitalManager.generateBill();
                        break;

                    case 10:
                        hospitalManager.reports();
                        break;

                    case 0:
                        System.out.println("Thank You!");
                        break;

                    default:
                        System.out.println("Invalid Choice.");

                }

            } catch (Exception e) {

                System.out.println("Invalid Input.");
                choice = -1;

            }

        } while (choice != 0);

    }

}