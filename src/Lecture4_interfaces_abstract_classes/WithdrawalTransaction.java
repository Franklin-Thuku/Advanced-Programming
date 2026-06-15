package Lecture4_interfaces_abstract_classes;

import org.jetbrains.annotations.NotNull;
import java.util.Calendar;

public class WithdrawalTransaction extends BaseTransaction {
    private BankAccount appliedAccount = null;
    private boolean isApplied = false;
    private double shortfallRecord = 0.0;

    public WithdrawalTransaction(int amount, @NotNull Calendar date) {
        super(amount, date);
    }

    private boolean checkDepositAmount(int amt) {
        if (amt < 0) {
            return false;
        } else {
            return true;
        }
    }

    // Question 2: Reversal logic restores the account balance to its original amount
    public boolean reverse() {
        if (isApplied && appliedAccount != null) {
            double currentBalance = appliedAccount.getBalance();
            // Restore original amount (current balance + what was successfully withdrawn)
            double restoredBalance = currentBalance + (getAmount() - shortfallRecord);
            appliedAccount.setBalance(restoredBalance);
            isApplied = false; // Reset state
            System.out.println("Withdrawal transaction reversed successfully.");
            return true;
        }
        System.out.println("Reversal failed: Transaction has not been applied yet.");
        return false;
    }

    // Method to print a transaction receipt or details
    @Override
    public void printTransactionDetails() {
        System.out.println("--- Withdrawal Transaction ---");
        System.out.println("Transaction ID: " + getTransactionID());
        System.out.println("Date: " + getDate().getTime());
        System.out.println("Amount Requested: " + getAmount());
        if (shortfallRecord > 0) {
            System.out.println("Shortfall (Not Withdrawn): " + shortfallRecord);
        }
    }

    // Question 3: Standard apply using the throws keyword
    @Override
    public void apply(BankAccount ba) throws InsufficientFundsException {
        double curr_balance = ba.getBalance();
        if (curr_balance >= getAmount()) {
            double new_balance = curr_balance - getAmount();
            ba.setBalance(new_balance);
            this.appliedAccount = ba;
            this.isApplied = true;
            this.shortfallRecord = 0.0;
        } else {
            throw new InsufficientFundsException("Insufficient funds to complete the withdrawal of " + getAmount());
        }
    }

    // Question 3: Overloaded apply handling 0 < balance < withdrawal amount using try...catch...finally
    public void apply(BankAccount ba, boolean partialWithdrawalAllowed) {
        try {
            double curr_balance = ba.getBalance();
            if (curr_balance < getAmount()) {
                // Check if balance is greater than 0 for partial withdrawal
                if (curr_balance > 0) {
                    shortfallRecord = getAmount() - curr_balance;
                    ba.setBalance(0.0); // Withdraw all available balance
                    this.appliedAccount = ba;
                    this.isApplied = true;
                    System.out.println("Partial withdrawal executed. Balance set to 0. Shortfall: " + shortfallRecord);
                } else {
                    // Trigger exception block manually if balance is 0 or less
                    throw new InsufficientFundsException("Account balance is zero or negative. Cannot withdraw.");
                }
            } else {
                // If funds are completely sufficient, proceed normally via standard apply
                apply(ba);
            }
        } catch (InsufficientFundsException e) {
            System.out.println("Caught Exception inside overloaded apply: " + e.getMessage());
            this.isApplied = false;
        } finally {
            System.out.println("Transaction processing attempt finished for Transaction ID: " + getTransactionID());
        }
    }
}