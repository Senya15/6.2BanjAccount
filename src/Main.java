import BankAccounts.BankAccount;
import BankAccounts.CreditAccount;
import BankAccounts.DepositAccount;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BankAccount mainAccount = new BankAccount(0);
        DepositAccount depositAccount = new DepositAccount(0);
        CreditAccount creditAccount = new CreditAccount(1);

        boolean check = true;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Выберите счёт (1 -> MAIN, 2 -> DEPOSIT, 3 -> CREDIT, 0 -> завершение программы):");
            while (!scanner.hasNextInt()) {
                System.out.println("Неверно! Повторите ввод ещё раз.");
                scanner.next();
            }
            int inCommand = scanner.nextInt();
            switch (inCommand) {
                case 1:
                    mainAccount.run();
                    break;
                case 2:
                    depositAccount.run();
                    break;
                case 3:
                    creditAccount.run();
                    break;
                case 0:
                    System.out.println("Програма завершена...");
                    check = false;
                    scanner.close();
                    break;
                default:
                    System.out.println("Команда не распознана! Повторите");
            }
        } while (check);
    }
}
