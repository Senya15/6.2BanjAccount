import BankAccounts.BankAccount;
import BankAccounts.Command;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] args) {
        BankAccount mainAccount = new BankAccount();
        mainAccount.run();
    }
}
