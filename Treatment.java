public class Treatment {
    public static double getCost(String treatmentType) {
        switch (treatmentType.toLowerCase()) {
            case "acne treatment":
                return 2750.00;
            case "skin whitening":
                return 7650.00;
            case "mole removal":
                return 3850.00;
            case "laser treatment":
                return 12500.00;
            default:
                return 0.00;
        }
    }
}
