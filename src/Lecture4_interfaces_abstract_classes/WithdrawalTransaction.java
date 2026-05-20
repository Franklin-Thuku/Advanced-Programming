package Lecture4_interfaces_abstract_classes;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class WithdrawalTransaction extends BaseTransaction {
     private boolean applied = false;   
    private boolean reversed = false;  
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

    // Method to reverse the transaction
    public boolean reverse(BankAccount ba) {
         if (applied && !reversed) {
            double curr_balance = ba.getBalance();
            double new_balance = curr_balance + getAmount();
            ba.setBalance(new_balance);
            reversed = true;
            System.out.println("Withdrawal reversed. Amount returned: " + getAmount());
        return true;
         }
         else if (reversed) {
            System.out.println("Transaction has already been reversed.");
            return false;
        } else {
            System.out.println("Transaction was never applied, cannot reverse.");
            return false;
        }
    } // return true if reversal was successful

    // Method to print a transaction receipt or details
    @Override
    public void printTransactionDetails() {
        System.out.println("Withdrawal Transaction: " + this.toString());
        System.out.println("  Applied: " + applied);
        System.out.println("  Reversed: " + reversed);
        if (amountNotWithdrawn > 0) {
            System.out.println("  Amount not withdrawn: " + amountNotWithdrawn);
        }
    }

    /*
    Oportunity for assignment: implementing different form of withdrawal
     */
     @Override
    public void apply(BankAccount ba) throws InsufficientFundsException {
        double curr_balance = ba.getBalance();

        if (curr_balance >= getAmount()) {
            double new_balance = curr_balance - getAmount();
            ba.setBalance(new_balance);
            applied = true;
            System.out.println("Withdrawal of " + getAmount() + " applied. New balance: " + new_balance);
        } else {
            double deficit = getAmount() - curr_balance;
            throw new InsufficientFundsException(
                "Insufficient funds! Balance: " + curr_balance + ", needed: " + getAmount(), deficit);
        }
    }

    public void apply(BankAccount ba, boolean withdrawAvailable) {
        double curr_balance = ba.getBalance();

        try {
            apply(ba);
        } catch (InsufficientFundsException e) {
            System.out.println("Exception caught: " + e.getMessage());

            if (curr_balance > 0 && withdrawAvailable) {
                amountNotWithdrawn = getAmount() - curr_balance;
                ba.setBalance(0);
                applied = true;
                System.out.println("Withdrew available balance: " + curr_balance);
                System.out.println("Amount not withdrawn (shortfall): " + amountNotWithdrawn);
            } else {
                System.out.println("No withdrawal made. Balance is 0.");
            }
        } finally {
            System.out.println("Withdrawal attempt completed");
        }
    }

    public double getAmountNotWithdrawn() {
        return amountNotWithdrawn;
    }
    /*
    Assignment 1 Q3: Write the Reverse method - a method unique to the WithdrawalTransaction Class
     */
}

