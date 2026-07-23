package entities;

import enums.DoctorSpecialization;

public class Doctor {

    private String doctorId;
    private String name;
    private DoctorSpecialization specialization;
    private int maxPatientsPerDay;
    private int currentPatients;

    public Doctor() {
    }

    public Doctor(String doctorId, String name,
                  DoctorSpecialization specialization,
                  int maxPatientsPerDay) {

        this.doctorId = doctorId;
        this.name = name;
        this.specialization = specialization;
        this.maxPatientsPerDay = maxPatientsPerDay;
        this.currentPatients = 0;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DoctorSpecialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(DoctorSpecialization specialization) {
        this.specialization = specialization;
    }

    public int getMaxPatientsPerDay() {
        return maxPatientsPerDay;
    }

    public void setMaxPatientsPerDay(int maxPatientsPerDay) {
        this.maxPatientsPerDay = maxPatientsPerDay;
    }

    public int getCurrentPatients() {
        return currentPatients;
    }

    public void setCurrentPatients(int currentPatients) {
        this.currentPatients = currentPatients;
    }

    public boolean isAvailable() {
        return currentPatients < maxPatientsPerDay;
    }

    public void assignPatient() {
        currentPatients++;
    }

    public void releasePatient() {
        if (currentPatients > 0) {
            currentPatients--;
        }
    }

    @Override
    public String toString() {
        return "\nDoctor ID        : " + doctorId +
                "\nName             : " + name +
                "\nSpecialization   : " + specialization +
                "\nCurrent Patients : " + currentPatients +
                "\nDaily Limit      : " + maxPatientsPerDay;
    }
}