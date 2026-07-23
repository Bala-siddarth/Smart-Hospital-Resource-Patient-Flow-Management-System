package service;

import entities.Appointment;
import entities.Bill;
import entities.Doctor;
import entities.Patient;
import entities.Ward;
import enums.*;
import exceptions.HospitalException;
import utils.HospitalConfig;
import utils.IdGenerator;
import utils.ValidationUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class HospitalService {

    private List<Patient> patients;
    private Map<String, Patient> patientMap;

    private List<Doctor> doctors;
    private Map<String, Doctor> doctorMap;

    private List<Ward> wards;

    private List<Appointment> appointments;

    private List<Bill> bills;

    private PriorityQueue<Patient> emergencyQueue;

    public HospitalService() {

        patients = new ArrayList<>();
        patientMap = new HashMap<>();

        doctors = new ArrayList<>();
        doctorMap = new HashMap<>();

        wards = new ArrayList<>();

        appointments = new ArrayList<>();

        bills = new ArrayList<>();

        emergencyQueue = new PriorityQueue<>(
                Comparator.comparing(Patient::getSeverityLevel).reversed()
        );

        loadDoctors();
        loadWards();
    }

    private void loadDoctors() {

        addDoctor("Dr. John", DoctorSpecialization.GENERAL_PHYSICIAN);
        addDoctor("Dr. Sarah", DoctorSpecialization.GENERAL_PHYSICIAN);
        addDoctor("Dr. David", DoctorSpecialization.CARDIOLOGIST);
        addDoctor("Dr. Kevin", DoctorSpecialization.CARDIOLOGIST);
        addDoctor("Dr. Smith", DoctorSpecialization.NEUROLOGIST);
        addDoctor("Dr. Siddi", DoctorSpecialization.NEUROLOGIST);
        addDoctor("Dr. Robert", DoctorSpecialization.ORTHOPEDIC);
        addDoctor("Dr. Emily", DoctorSpecialization.PEDIATRICIAN);

    }

    private void addDoctor(String name,
                           DoctorSpecialization specialization) {

        Doctor doctor = new Doctor(
                IdGenerator.generateDoctorId(),
                name,
                specialization,
                HospitalConfig.MAX_PATIENTS_PER_DOCTOR
        );

        doctors.add(doctor);
        doctorMap.put(doctor.getDoctorId(), doctor);

    }

    private void loadWards() {

        wards.add(new Ward(
                IdGenerator.generateWardId(),
                WardType.GENERAL,
                HospitalConfig.MAX_GENERAL_BEDS
        ));

        wards.add(new Ward(
                IdGenerator.generateWardId(),
                WardType.ICU,
                HospitalConfig.MAX_ICU_BEDS
        ));

    }

    public void registerPatient(Patient patient) throws HospitalException {

        validatePatient(patient);

        if (isDuplicatePatient(patient.getPhone())) {
            throw new HospitalException("Patient already registered with this phone number.");
        }

        patient.setPatientId(IdGenerator.generatePatientId());

        storePatient(patient);

    }
    private void validatePatient(Patient patient) throws HospitalException {

        if (!ValidationUtil.isValidName(patient.getName())) {
            throw new HospitalException("Invalid patient name.");
        }

        if (!ValidationUtil.isValidAge(patient.getAge())) {
            throw new HospitalException("Invalid patient age.");
        }

        if (!ValidationUtil.isValidPhone(patient.getPhone())) {
            throw new HospitalException("Invalid phone number.");
        }

        if (!ValidationUtil.isValidBloodGroup(patient.getBloodGroup())) {
            throw new HospitalException("Invalid blood group.");
        }

    }
    private boolean isDuplicatePatient(String phone) {

        for (Patient patient : patients) {

            if (patient.getPhone().equals(phone)) {
                return true;
            }

        }

        return false;
    }
    private void storePatient(Patient patient) {

        patients.add(patient);

        patientMap.put(patient.getPatientId(), patient);

    }
    public Patient searchPatient(String patientId) throws HospitalException {

        Patient patient = patientMap.get(patientId);

        if (patient == null) {
            throw new HospitalException("Patient not found.");
        }

        return patient;
    }
    public void updatePatient(Patient updatedPatient) throws HospitalException {

        Patient existingPatient = searchPatient(updatedPatient.getPatientId());

        validatePatient(updatedPatient);

        for (Patient patient : patients) {

            if (!patient.getPatientId().equals(updatedPatient.getPatientId())
                    && patient.getPhone().equals(updatedPatient.getPhone())) {

                throw new HospitalException("Phone number already exists.");

            }

        }

        existingPatient.setName(updatedPatient.getName());
        existingPatient.setAge(updatedPatient.getAge());
        existingPatient.setGender(updatedPatient.getGender());
        existingPatient.setPhone(updatedPatient.getPhone());
        existingPatient.setBloodGroup(updatedPatient.getBloodGroup());

    }
    public void admitPatient(String patientId) throws HospitalException {

        Patient patient = searchPatient(patientId);

        if (patient.getStatus() == PatientStatus.ADMITTED) {
            throw new HospitalException("Patient is already admitted.");
        }

        Ward ward = findAvailableWard(patient);

        Doctor doctor = findAvailableDoctor(patient);

        ward.occupyBed();

        doctor.assignPatient();

        patient.setAssignedWard(ward);
        patient.setAssignedDoctor(doctor);

        patient.setStatus(PatientStatus.ADMITTED);

    }
    private Ward findAvailableWard(Patient patient)
            throws HospitalException {

        WardType requiredWard;

        if (patient.isEmergency()
                || patient.getSeverityLevel() == SeverityLevel.CRITICAL) {

            requiredWard = WardType.ICU;

        } else {

            requiredWard = WardType.GENERAL;

        }

        for (Ward ward : wards) {

            if (ward.getWardType() == requiredWard
                    && ward.hasAvailableBed()) {

                return ward;

            }

        }

        throw new HospitalException("No beds available.");

    }
    private Doctor findAvailableDoctor(Patient patient)
            throws HospitalException {

        Doctor selectedDoctor = null;

        for (Doctor doctor : doctors) {

            if (!doctor.isAvailable()) {
                continue;
            }

            if (doctor.getSpecialization()
                    != patient.getRequiredSpecialization()) {
                continue;
            }

            if (selectedDoctor == null
                    || doctor.getCurrentPatients()
                    < selectedDoctor.getCurrentPatients()) {

                selectedDoctor = doctor;
            }
        }

        if (selectedDoctor == null) {
            throw new HospitalException(
                    "No available " + patient.getRequiredSpecialization() + " found."
            );
        }

        return selectedDoctor;
    }
    public void scheduleAppointment(String patientId,
                                    LocalDateTime appointmentTime)
            throws HospitalException {

        Patient patient = searchPatient(patientId);

        Doctor doctor = patient.getAssignedDoctor();

        if (doctor == null) {
            throw new HospitalException("Patient has no assigned doctor.");
        }

        validateAppointmentTime(appointmentTime);

        if (isPatientBusy(patient, appointmentTime)) {

            throw new HospitalException(
                    "Patient already has an appointment at this time.");

        }

        if (isDoctorBusy(doctor, appointmentTime)) {

            throw new HospitalException(
                    "Doctor already has an appointment at this time.");

        }

        Appointment appointment = new Appointment(
                IdGenerator.generateAppointmentId(),
                patient,
                doctor,
                appointmentTime,
                patient.isEmergency()
        );

        appointments.add(appointment);

    }
    private void validateAppointmentTime(LocalDateTime appointmentTime)
            throws HospitalException {

        LocalTime time = appointmentTime.toLocalTime();

        if (appointmentTime.isBefore(LocalDateTime.now())) {
            throw new HospitalException("Appointment cannot be scheduled in the past.");
        }

        if (time.isBefore(HospitalConfig.HOSPITAL_OPEN_TIME)
                || time.isAfter(HospitalConfig.HOSPITAL_CLOSE_TIME)) {

            throw new HospitalException("Appointment is outside hospital working hours.");
        }

    }
    private boolean isDoctorBusy(Doctor doctor,
                                 LocalDateTime appointmentTime) {

        for (Appointment appointment : appointments) {

            if (appointment.getDoctor().equals(doctor)
                    && appointment.getAppointmentDateTime().equals(appointmentTime)
                    && appointment.getStatus() == AppointmentStatus.SCHEDULED) {

                return true;

            }

        }

        return false;

    }
    private boolean isPatientBusy(Patient patient,
                                  LocalDateTime appointmentTime) {

        for (Appointment appointment : appointments) {

            if (appointment.getPatient().equals(patient)
                    && appointment.getAppointmentDateTime().equals(appointmentTime)
                    && appointment.getStatus() == AppointmentStatus.SCHEDULED) {

                return true;

            }

        }

        return false;

    }
    public void addEmergencyPatient(String patientId)
            throws HospitalException {

        Patient patient = searchPatient(patientId);

        if (!patient.isEmergency()) {
            throw new HospitalException("Patient is not an emergency patient.");
        }

        if (patient.getStatus() == PatientStatus.ADMITTED ||
                patient.getStatus() == PatientStatus.EMERGENCY_WAITING) {
            throw new HospitalException("Patient is already admitted or waiting as Emergency.");
        }
        emergencyQueue.offer(patient);
        patient.setStatus(PatientStatus.EMERGENCY_WAITING);

    }
    public Patient admitNextEmergencyPatient() throws HospitalException {

        if (emergencyQueue.isEmpty()) {
            throw new HospitalException("No emergency patients waiting.");
        }

        Patient patient = emergencyQueue.poll();

        Ward ward = findAvailableWard(patient);

        Doctor doctor = findAvailableDoctor(patient);

        ward.occupyBed();
        doctor.assignPatient();

        patient.setAssignedWard(ward);
        patient.setAssignedDoctor(doctor);
        patient.setStatus(PatientStatus.ADMITTED);

        return patient;
    }
    public void transferPatient(String patientId, WardType newWardType)
            throws HospitalException {

        Patient patient = searchPatient(patientId);

        if (patient.getStatus() != PatientStatus.ADMITTED) {
            throw new HospitalException("Patient is not admitted.");
        }

        Ward currentWard = patient.getAssignedWard();

        if (currentWard.getWardType() == newWardType) {
            throw new HospitalException("Patient is already in this ward.");
        }

        Ward newWard = findAvailableWard(newWardType);

        currentWard.releaseBed();

        newWard.occupyBed();

        patient.setAssignedWard(newWard);

    }
    private Ward findAvailableWard(WardType wardType)
            throws HospitalException {

        for (Ward ward : wards) {

            if (ward.getWardType() == wardType
                    && ward.hasAvailableBed()) {

                return ward;

            }

        }

        throw new HospitalException("No beds available in requested ward.");

    }
    public Bill generateBill(String patientId)
            throws HospitalException {

        Patient patient = searchPatient(patientId);

        if (billAlreadyGenerated(patient)) {
            throw new HospitalException("Bill has already been generated for this patient.");
        }
        if (patient.getStatus() != PatientStatus.ADMITTED) {
            throw new HospitalException("Patient is not admitted.");
        }

        Bill bill = new Bill();

        bill.setBillId(IdGenerator.generateBillId());

        bill.setPatient(patient);

        bill.setAdmissionFee(HospitalConfig.ADMISSION_FEE);

        bill.setConsultationFee(HospitalConfig.CONSULTATION_FEE);

        double wardCharge;

        if (patient.getAssignedWard().getWardType() == WardType.ICU) {

            wardCharge = HospitalConfig.ICU_WARD_CHARGE;

        } else {

            wardCharge = HospitalConfig.GENERAL_WARD_CHARGE;

        }

        bill.setWardCharges(wardCharge);

        bill.setTreatmentCharges(0);

        if (patient.isEmergency()) {

            bill.setEmergencyCharges(HospitalConfig.EMERGENCY_CHARGE);

        } else {

            bill.setEmergencyCharges(0);

        }

        bill.setDiscount(0);

        double total =
                bill.getAdmissionFee()
                        + bill.getConsultationFee()
                        + bill.getWardCharges()
                        + bill.getTreatmentCharges()
                        + bill.getEmergencyCharges()
                        - bill.getDiscount();

        bill.setTotalAmount(total);

        bill.setPaid(false);

        bills.add(bill);

        return bill;

    }
    private boolean billAlreadyGenerated(Patient patient) {

        for (Bill bill : bills) {

            if (bill.getPatient().getPatientId()
                    .equals(patient.getPatientId())) {

                return true;

            }

        }

        return false;

    }
    public void dischargePatient(String patientId)
            throws HospitalException {

        Patient patient = searchPatient(patientId);
        if (!billAlreadyGenerated(patient)) {
            throw new HospitalException("Generate the bill before discharging the patient.");
        }
        if (patient.getStatus() != PatientStatus.ADMITTED) {
            throw new HospitalException("Patient is not admitted.");
        }

        Ward ward = patient.getAssignedWard();
        Doctor doctor = patient.getAssignedDoctor();

        if (ward != null) {
            ward.releaseBed();
        }

        if (doctor != null) {
            doctor.releasePatient();
        }

        patient.setAssignedWard(null);
        patient.setAssignedDoctor(null);

        patient.setStatus(PatientStatus.DISCHARGED);

    }
    public void displayAllPatients() {

        if (patients.isEmpty()) {

            System.out.println("No patients found.");
            return;

        }

        for (Patient patient : patients) {

            System.out.println(patient);

        }

    }
    public void displayAdmittedPatients() {

        boolean found = false;

        for (Patient patient : patients) {

            if (patient.getStatus() == PatientStatus.ADMITTED) {

                System.out.println(patient);

                found = true;

            }

        }

        if (!found) {

            System.out.println("No admitted patients.");

        }

    }
    public void displayDoctors() {

        for (Doctor doctor : doctors) {

            System.out.println(doctor);

        }

    }
    public void displayWardStatus() {

        for (Ward ward : wards) {

            System.out.println("------------------------");

            System.out.println("Ward : " + ward.getWardType());

            System.out.println("Occupied Beds : "
                    + ward.getOccupiedBeds());

            System.out.println("Available Beds : "
                    + ward.getAvailableBeds());

        }

    }
    public void displayAppointments() {

        if (appointments.isEmpty()) {

            System.out.println("No appointments.");

            return;

        }

        for (Appointment appointment : appointments) {

            System.out.println(appointment);

        }

    }
    public void displayBills() {

        if (bills.isEmpty()) {

            System.out.println("No bills generated.");

            return;

        }

        for (Bill bill : bills) {

            System.out.println(bill);

        }

    }
}