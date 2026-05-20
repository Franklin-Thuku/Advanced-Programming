import Lecture1_adt.*; // Import all classes from Lecture1_adt package to be used in this client code
import Lecture4_interfaces_abstract_classes.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
/*
* Client Code for accessing the Lecture1_adt.TransactionInterface.java module
 */
public class Main {

    public static void testTransaction1() {
        Calendar d1 = new GregorianCalendar(); // d1 is an Object [Objects are Reference types]
        Lecture1_adt.Transaction1 t1 = new Lecture1_adt.Transaction1(1000, d1); // amount and d1 are arguments

        System.out.println(t1.toString());
        System.out.println("Lecture1_adt.TransactionInterface Amount: \t " + t1.amount);
        System.out.println("Lecture1_adt.TransactionInterface Date: \t " + t1.date);

        // Please note that the Client Codes can access the data in the class directly through the dot operator
        // This kind of exposure is a threat to both the Representation Independence and Preservation of Invariants
    }


    /** @return a transaction of same amount as t, one month later
     * This is a PRODUCER of the class Lecture1_adt.Transaction2
     * This code will help demostrate the Design exposures still present in transaction2 class
     * */

    public static Transaction2 makeNextPayment(Transaction2 t) {
        Calendar d =  t.getDate();
        d.add(Calendar.MONTH, 1);
        return new Transaction2(t.getAmount(), d);
    }

    /*
    Testing Transaction2 class
     */
    public static void testTransaction2() {

        Calendar d1 = new GregorianCalendar();

        Lecture1_adt.Transaction2 t = new Lecture1_adt.Transaction2(1000, d1);

        Lecture1_adt.Transaction2 modified_t = makeNextPayment(t);

        System.out.println("\n\nState of the Object T1 After Client Code Tried to Change the Amount");
        System.out.println("Lecture1_adt.TransactionInterface Amount: \t "+modified_t.getAmount());
        System.out.println("Lecture1_adt.TransactionInterface Date: \t "+modified_t.getDate().getTime());

        System.out.println("\n\nHow does T2 Look Like?????");
        System.out.println("Lecture1_adt.TransactionInterface Amount: \t "+modified_t.getAmount());
        System.out.println("Lecture1_adt.TransactionInterface Date: \t "+modified_t.getDate().getTime());

        /* Please note that Although we have solved the problem of Transaction1
        * And client code can no longer use the dot (.) operator to directly access the data
        * There is still some exposure especially if we pass an object of a previous Transaction2 to create a new Transaction2 object
         */

    }


    /** @return a list of 12 monthly payments of identical amounts
     * This code will help demostrate the Design exposures still present in transaction3 class
     * */
    public static List<Transaction3> makeYearOfPayments (int amount) throws NullPointerException {

        List<Transaction3> listOfTransaction3s = new ArrayList<Transaction3>();
        Calendar date = new GregorianCalendar(2024, Calendar.JANUARY, 3);


        for (int i = 0; i < 12; i++) {
            listOfTransaction3s.add(new Transaction3(amount, date));
            date.add(Calendar.MONTH, 1);
        }
        return listOfTransaction3s;
    }

    /*
    Testing Transaction3 class
     */
    public static void testTransaction3() {

        List<Transaction3> allPaymentsIn2024 = makeYearOfPayments(1000);

        for (Transaction3 t3 : allPaymentsIn2024) {

            // Display all the 12 Transactions
            for (Transaction3 transact : allPaymentsIn2024) {
                System.out.println("\n\n  ::::::::::::::::::::::::::::::::::::::::::::\n");
                System.out.println("Lecture1_adt.TransactionInterface Amount: \t "+transact.getAmount());
                System.out.println("Lecture1_adt.TransactionInterface Date: \t "+transact.getDate().getTime());
            }
        }

        /* Please Check all the 12 transactions displayed and hwo their dates look like
         * Note that Although Transaction3 class resolves to an extent the exposure in Transaction2 class
         * There is still some exposure especially if we pass an object of a previous Transaction3 to create a
         * new Transaction3 object
         */
    }


    /** @return a list of 12 monthly payments of identical amounts
     * This code Show that by judicious copying and defensive programming we eliminate the exposure in Transaction3
     * As defined in the constructor of Transaction4 class
     * */

    public static List<Transaction4> makeYearOfPaymentsFinal (int amount) throws NullPointerException {

        List<Transaction4> listOfTransaction4s = new ArrayList<Transaction4>();
        Calendar date = new GregorianCalendar(2024, Calendar.JANUARY, 3);


        for (int i = 0; i < 12; i++) {
            listOfTransaction4s.add(new Transaction4(amount, date));
            date.add(Calendar.MONTH, 1);
        }
        return listOfTransaction4s;
    }

    /*
    Testing Transaction3 class
     */
    public static void testTransaction4() {

        /*
         * Call the function to make all the Twelve transaction in a year of our business
         */

        List<Transaction4> transactionsIn2024 = makeYearOfPaymentsFinal(1200);

        // Display all the 12 Transactions
        for (Transaction4 transact : transactionsIn2024) {
            System.out.println("\n\n  ::::::::::::::::::::::::::::::::::::::::::::\n");
            System.out.println("Lecture1_adt.TransactionInterface Amount: \t "+transact.getAmount());
            System.out.println("Lecture1_adt.TransactionInterface Date: \t "+transact.getDate().getTime());
        }

        // Please Take a look at all the 12 transaction now and compare with the outputs of the Transaction3 class
    }

    public static void testDeposit() {
        System.out.println("\nTEST: Deposit Transaction");

        BankAccount ba = new BankAccount(1000);
        Calendar date = new GregorianCalendar();
        DepositTrasaction deposit = new DepositTrasaction(500, date);

        System.out.println("Balance before deposit: " + ba.getBalance());
        deposit.printTransactionDetails();

        try {
            deposit.apply(ba);
        } catch (InsufficientFundsException e) {
            System.out.println("Deposits don't throw this.");
        }

        System.out.println("Balance after deposit: " + ba.getBalance());
    }

    public static void testWithdrawalSuccess() {
        System.out.println("\nTEST: Successful Withdrawal");

        BankAccount ba = new BankAccount(1000);
        Calendar date = new GregorianCalendar();
        WithdrawalTransaction withdrawal = new WithdrawalTransaction(300, date);

        System.out.println("Balance before withdrawal: " + ba.getBalance());
        withdrawal.printTransactionDetails();

        try {
            withdrawal.apply(ba);
        } catch (InsufficientFundsException e) {
            System.out.println("Should NOT happen: " + e.getMessage());
        }

        System.out.println("Balance after withdrawal: " + ba.getBalance());
    }

    public static void testWithdrawalException() {
        System.out.println("\nTEST: Insufficient Funds Exception");

        BankAccount ba = new BankAccount(100);
        Calendar date = new GregorianCalendar();
        WithdrawalTransaction withdrawal = new WithdrawalTransaction(500, date);

        System.out.println("Balance: " + ba.getBalance());
        System.out.println("Attempting to withdraw: " + withdrawal.getAmount());

        try {
            withdrawal.apply(ba);
        } catch (InsufficientFundsException e) {
            System.out.println("Exception: " + e.getMessage());
            System.out.println("Deficit amount: " + e.getDeficitAmount());
        }

        System.out.println("Balance after failed attempt: " + ba.getBalance());
    }

    public static void testWithdrawalAvailableBalance() {
        System.out.println("\nTEST: Overloaded apply() — Available Balance");

        BankAccount ba = new BankAccount(150);
        Calendar date = new GregorianCalendar();
        WithdrawalTransaction withdrawal = new WithdrawalTransaction(500, date);

        System.out.println("Balance: " + ba.getBalance());
        System.out.println("Attempting to withdraw: " + withdrawal.getAmount());

        withdrawal.apply(ba, true);

        System.out.println("Balance after: " + ba.getBalance());
        System.out.println("Amount not withdrawn: " + withdrawal.getAmountNotWithdrawn());
        withdrawal.printTransactionDetails();
    }

    public static void testReverse() {
        System.out.println("\nTEST: Reverse Withdrawal");

        BankAccount ba = new BankAccount(1000);
        Calendar date = new GregorianCalendar();
        WithdrawalTransaction withdrawal = new WithdrawalTransaction(400, date);

        System.out.println("Original balance: " + ba.getBalance());

        try {
            withdrawal.apply(ba);
        } catch (InsufficientFundsException e) {
            System.out.println("Should NOT happen: " + e.getMessage());
        }

        System.out.println("Balance after withdrawal: " + ba.getBalance());

        boolean reversed = withdrawal.reverse(ba);
        System.out.println("Reversed? " + reversed);
        System.out.println("Balance after reversal: " + ba.getBalance());

        boolean reversedAgain = withdrawal.reverse(ba);
        System.out.println("Reversed again? " + reversedAgain);
    }

    public static void testPolymorphism() {
        System.out.println("\nTEST: Polymorphism");

        BankAccount ba1 = new BankAccount(1000);
        BankAccount ba2 = new BankAccount(500);
        Calendar date = new GregorianCalendar();

        DepositTrasaction deposit = new DepositTrasaction(200, date);
        WithdrawalTransaction withdrawal = new WithdrawalTransaction(300, date);

        BaseTransaction bt1 = deposit;
        BaseTransaction bt2 = withdrawal;

        System.out.println("\nApplying via BaseTransaction reference");

        try {
            bt1.apply(ba1);
            System.out.println("After deposit (via base ref): " + ba1.getBalance());

            bt2.apply(ba2);
            System.out.println("After withdrawal (via base ref): " + ba2.getBalance());
        } catch (InsufficientFundsException e) {
            System.out.println("Exception: " + e.getMessage());
        }

        System.out.println("\nDynamic Binding Demo");
        BaseTransaction[] transactions = {deposit, withdrawal};
        for (BaseTransaction bt : transactions) {
            System.out.print("Transaction type: ");
            bt.printTransactionDetails();
        }
    }


    public static void main(String[] args) {
        // ===== OLD TESTS (from Lecture1_adt) =====
        // testTransaction1();
        // testTransaction2();
        // testTransaction3();
        // testTransaction4();

        // ===== NEW TESTS (Assignment 1) =====
        testDeposit();
        testWithdrawalSuccess();
        testWithdrawalException();
        testWithdrawalAvailableBalance();
        testReverse();
        testPolymorphism();

    }
}