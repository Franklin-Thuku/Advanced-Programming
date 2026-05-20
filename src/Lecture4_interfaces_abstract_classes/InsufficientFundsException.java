package Lecture4_interfaces_abstract_classes;
public class InsufficientFundsException extends Exception {

    private double deficitAmount;

    /**
     * Constructor
     * @param message Description of the error
     * @param deficit The amount by which the balance falls short
     */
    public InsufficientFundsException(String message, double deficit) {
        super(message);
        this.deficitAmount = deficit;
    }

    /**
     * @return The amount still needed to cover the withdrawal
     */
    public double getDeficitAmount() {
        return deficitAmount;
    }
}