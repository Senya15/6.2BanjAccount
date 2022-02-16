package BankAccounts;

import lombok.Getter;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

@Getter
public class BankAccount {
    private int accountAmount;
    private Command command;
    private String inLine;

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean check = true;
        do {
            System.out.println("Введите одну из команд (DEPOSIT, GET, BALANCE):");
            inLine = scanner.nextLine().trim();
            checkInLine();
            int money = 0;
            switch (getCommand()) {
                case BALANCE:
                    System.out.println("Остаток на счету: " + getAccountAmount() + " рублей");
                    break;
                case DEPOSIT:
                    do {
                        System.out.println("Внесите сумму для пополнения счёта:");
                        while (!scanner.hasNextInt()) {
                            System.out.println("Неверно! Повторите ввод ещё раз.");
                            money = scanner.nextInt();
                        }
                        if (money < 0) {
                            System.out.println("Сумма не может быть отрицательной!");
                        }
                    } while (money < 0);
                    depositMoney(money);
                    break;
                case GET:
                    do {
                        System.out.println("Введите сумму для снятия со счёта:");
                        while (!scanner.hasNextInt()) {
                            System.out.println("Неверно! Повторите ввод ещё раз.");
                            scanner.next();
                        }
                        money = scanner.nextInt();
                        if (money < 0) {
                            System.out.println("Сумма не может быть отрицательной!");
                        }
                    } while (money < 0);
                    if (getMoney(money)) {
                        System.out.println("Вы сняли " + money + " рублей");
                    } else {
                        System.out.println("Недостаточно средст на счёте!");
                    }
                    break;
                case EXIT:
                    System.out.println("Програма завершена...");
                    check = false;
                    break;
                default:
                    System.out.println("Команда не распознана! Повторите");
            }
        } while (check);
    }

    private void checkInLine() {
        if (inLine.matches("^BALANCE")) {
            command = Command.BALANCE;
        } else if (inLine.matches("^GET")) {
            command = Command.GET;
        } else if (inLine.matches("^DEPOSIT")) {
            command = Command.DEPOSIT;
        } else if (inLine.matches("^EXIT")) {
            command = Command.EXIT;
        } else {
            command = Command.NONE;
        }
    }

    public boolean getMoney(int money) {
        if (money <= accountAmount) {
            accountAmount -= money;
            return true;
        } else {
            return false;
        }
    }

    public void depositMoney(int money) {
        accountAmount += money;
        System.out.println("Вы пополнили счёт на " + money + " рублей");
    }


}

