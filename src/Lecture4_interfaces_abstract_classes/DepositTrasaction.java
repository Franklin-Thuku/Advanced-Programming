package Lecture4_interfaces_abstract_classes;

import org.jetbrains.annotations.NotNull;
import java.util.Calendar;

public class DepositTrasaction extends BaseTransaction {

    public DepositTrasaction(int amount, @NotNull Calendar date){
        super(amount, date);
    }

    private boolean checkDepositAmount(int amt){
        if (amt < 0){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public void printTransactionDetails(){
        System.out.println("--- Deposit Transaction ---");
        System.out.println("Transaction ID: " + getTransactionID());
        System.out.println("Date: " + getDate().getTime());
        System.out.println("Amount Deposited: " + getAmount());
    }

    // Added 'throws InsufficientFundsException' to maintain signature compatibility
    @Override
    public void apply(BankAccount ba) throws InsufficientFundsException {
        double curr_balance = ba.getBalance();
        double new_balance = curr_balance + getAmount();
        ba.setBalance(new_balance);
    }
}