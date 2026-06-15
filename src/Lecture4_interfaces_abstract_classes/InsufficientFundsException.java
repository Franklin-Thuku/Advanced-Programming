package Lecture4_interfaces_abstract_classes;

public class InsufficientFundsException extends Exception {

    private double deficitAmount;

    public InsufficientFundsException(String message, double deficit) {
        super(message);
        this.deficitAmount = deficit;
    }

    public InsufficientFundsException(String message) {
        super(message);
        this.deficitAmount = 0.0;
    }

    public double getDeficitAmount() {
        return deficitAmount;
    }
}