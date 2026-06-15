package Lecture4_interfaces_abstract_classes;

import java.util.Calendar;

public interface TransactionInterface {

    double getAmount();

    Calendar getDate();

    String getTransactionID();

    void apply(BankAccount ba) throws InsufficientFundsException;

    void printTransactionDetails();
}