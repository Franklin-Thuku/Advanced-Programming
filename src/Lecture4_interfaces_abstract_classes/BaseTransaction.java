package Lecture4_interfaces_abstract_classes;

import java.util.Calendar;

public abstract class BaseTransaction implements TransactionInterface {

    private final int amount;
    private final Calendar date;
    private final String transactionID;

    public BaseTransaction(int amount, Calendar date) {
        this.amount = amount;
        this.date = (Calendar) date.clone();
        int uniq = (int) (Math.random() * 10000);
        this.transactionID = date.getTimeInMillis() + "-" + uniq;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public Calendar getDate() {
        return (Calendar) date.clone();
    }

    @Override
    public String getTransactionID() {
        return transactionID;
    }

    @Override
    public void printTransactionDetails() {
        System.out.println("--- Transaction Details ---");
        System.out.println("Transaction ID: " + transactionID);
        System.out.println("Date: " + date.getTime());
        System.out.println("Amount: " + amount);
    }

    @Override
    public void apply(BankAccount ba) throws InsufficientFundsException {
        System.out.println("Base transaction executed.");
    }
}