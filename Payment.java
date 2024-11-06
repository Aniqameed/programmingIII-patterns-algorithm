public class Payment {
    double registrationFee;
    double treatmentCost;

    public Payment(double registrationFee, double treatmentCost) {
        this.registrationFee = registrationFee;
        this.treatmentCost = treatmentCost;
    }

    public double getTotalAmount() {
        return registrationFee + treatmentCost + (treatmentCost * 0.025);
    }
}
