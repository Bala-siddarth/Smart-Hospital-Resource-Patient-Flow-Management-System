package entities;

public class Bill {

    private String billId;
    private Patient patient;

    private double admissionFee;
    private double consultationFee;
    private double wardCharges;
    private double treatmentCharges;
    private double emergencyCharges;
    private double discount;
    private double totalAmount;

    private boolean paid;

    public Bill() {
    }

    public Bill(String billId, Patient patient) {
        this.billId = billId;
        this.patient = patient;
        this.paid = false;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public double getAdmissionFee() {
        return admissionFee;
    }

    public void setAdmissionFee(double admissionFee) {
        this.admissionFee = admissionFee;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }

    public double getWardCharges() {
        return wardCharges;
    }

    public void setWardCharges(double wardCharges) {
        this.wardCharges = wardCharges;
    }

    public double getTreatmentCharges() {
        return treatmentCharges;
    }

    public void setTreatmentCharges(double treatmentCharges) {
        this.treatmentCharges = treatmentCharges;
    }

    public double getEmergencyCharges() {
        return emergencyCharges;
    }

    public void setEmergencyCharges(double emergencyCharges) {
        this.emergencyCharges = emergencyCharges;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    @Override
    public String toString() {

        return "\nBill ID             : " + billId +
                "\nPatient             : " + patient.getName() +
                "\nAdmission Fee       : " + admissionFee +
                "\nConsultation Fee    : " + consultationFee +
                "\nWard Charges        : " + wardCharges +
                "\nTreatment Charges   : " + treatmentCharges +
                "\nEmergency Charges   : " + emergencyCharges +
                "\nDiscount            : " + discount +
                "\nTotal Amount        : " + totalAmount +
                "\nPayment Status      : " + (paid ? "PAID" : "PENDING");
    }
}