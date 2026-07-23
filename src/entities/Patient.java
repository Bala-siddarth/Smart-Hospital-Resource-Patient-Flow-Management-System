package entities;

import enums.*;

public class Patient {

    private String patientId;
    private String name;
    private int age;
    private Gender gender;
    private String phone;
    private BloodGroup bloodGroup;
    private DoctorSpecialization requiredSpecialization;

    private PatientStatus status;

    private boolean emergency;
    private SeverityLevel severityLevel;

    private Doctor assignedDoctor;
    private Ward assignedWard;

    public Patient() {
    }

    public Patient(String patientId, String name, int age, Gender gender,
                   String phone, BloodGroup bloodGroup,
                   boolean emergency, SeverityLevel severityLevel) {

        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.bloodGroup = bloodGroup;
        this.emergency = emergency;
        this.severityLevel = severityLevel;
        this.status = PatientStatus.REGISTERED;
    }
    public DoctorSpecialization getRequiredSpecialization() {
        return requiredSpecialization;
    }

    public void setRequiredSpecialization(DoctorSpecialization requiredSpecialization) {
        this.requiredSpecialization = requiredSpecialization;
    }
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BloodGroup getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public PatientStatus getStatus() {
        return status;
    }

    public void setStatus(PatientStatus status) {
        this.status = status;
    }

    public boolean isEmergency() {
        return emergency;
    }

    public void setEmergency(boolean emergency) {
        this.emergency = emergency;
    }

    public SeverityLevel getSeverityLevel() {
        return severityLevel;
    }

    public void setSeverityLevel(SeverityLevel severityLevel) {
        this.severityLevel = severityLevel;
    }

    public Doctor getAssignedDoctor() {
        return assignedDoctor;
    }

    public void setAssignedDoctor(Doctor assignedDoctor) {
        this.assignedDoctor = assignedDoctor;
    }

    public Ward getAssignedWard() {
        return assignedWard;
    }

    public void setAssignedWard(Ward assignedWard) {
        this.assignedWard = assignedWard;
    }

    @Override
    public String toString() {

        return "\nPatient ID      : " + patientId +
                "\nName            : " + name +
                "\nAge             : " + age +
                "\nGender          : " + gender +
                "\nPhone           : " + phone +
                "\nBlood Group     : " + bloodGroup +
                "\nStatus          : " + status +
                "\nEmergency       : " + emergency +
                "\nSeverity Level  : " + severityLevel +
                "\nAssigned Doctor : " + (assignedDoctor != null ? assignedDoctor.getName() : "Not Assigned") +
                "\nAssigned Ward   : " + (assignedWard != null ? assignedWard.getWardId() : "Not Assigned");
    }

}