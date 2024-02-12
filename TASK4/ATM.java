import java.util.*;

class BankAcc {
    double balance = 100000.00;

    boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        } else {
            return false;
        }
    }

    boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            return true;
        } else {
            return false;
        }
    }

    double checkBalance() {
        return balance;
    }

}

class ATM {
    public static void main(String[] args) {
        BankAcc acc = new BankAcc();
        Scanner sc = new Scanner(System.in);
        int ch;
        do {
            System.out.println("\n MY BANK");
            System.out
                    .println("\n MENU: \n 1. Withdraw Money \n 2. Deposit Money \n 3.Available Balance \n 4. Exit \n");
            System.out.println("Enter your choice:");
            ch = sc.nextInt();

            switch (ch) {
                case 1:
                    System.out.print("Enter the amount to be withdrawn:");
                    double withdrawAmt = sc.nextDouble();
                    if (acc.withdraw(withdrawAmt)) {
                        System.out.println("Amount Withdrawn Successfully!");
                    } else {
                        System.out.println("Insufficient Balance! Try Again.");
                    }

                    break;

                case 2:
                    System.out.print("Enter the amount to be deposited:");
                    double depositAmt = sc.nextDouble();
                    if (acc.deposit(depositAmt)) {
                        System.out.println("Deposit Successful!");
                    } else {
                        System.out.println("Invalid Amount!");
                    }
                    break;

                case 3:
                    double balanceAmt = acc.checkBalance();
                    System.out.print("Balance  is : Rs." + balanceAmt);
                    break;

                case 4:
                    System.exit(0);

                default:
                    break;
            }

        } while (ch != 4);

        sc.close();
    }
}