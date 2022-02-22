import BankAccounts.BankAccount;
import BankAccounts.Command;
import BankAccounts.CreditAccount;
import BankAccounts.DepositAccount;

import java.util.Scanner;

public class Main {
    static BankAccount mainAccount = new BankAccount(0);
    static BankAccount depositAccount = new DepositAccount(0);
    static BankAccount creditAccount = new CreditAccount(1);

    static Command command;
    static String inLine;
    static Scanner scInt = new Scanner(System.in);

    public static void main(String[] args) {

        boolean check = true;

        do {
            System.out.printf("Выберите счёт (1 -> %s, 2 -> %s, 3 -> %s, 0 -> завершение программы):%n",
                    mainAccount.getClass().getSimpleName(), depositAccount.getClass().getSimpleName(),
                    creditAccount.getClass().getSimpleName());
            int inCommand = hasNextIntCommand();
            switch (inCommand) {
                case 1:
                    run(mainAccount);
                    break;
                case 2:
                    run(depositAccount);
                    break;
                case 3:
                    run(creditAccount);
                    break;
                case 0:
                    System.out.println("Програма завершена...");
                    check = false;
                    scInt.close();
                    break;
                default:
                    System.out.println("Команда не распознана! Повторите");
            }
        } while (check);
    }

    public static void run(BankAccount bankAccount) {
        String accountName = bankAccount.getClass().getSimpleName();
        Scanner scCommand = new Scanner(System.in);
//        Scanner scInt = new Scanner(System.in);
        boolean check = true;
        int money;
        do {
            System.out.printf("Введите одну из команд для счёта %s (BALANCE, DEPOSIT, GET, EXIT):%n", accountName);
            inLine = scCommand.nextLine().trim();
            checkInLine();
            switch (getCommand()) {
                case BALANCE:
                    System.out.printf("Остаток на счету: %d рублей%n", bankAccount.getAccountAmount());
                    break;
                case DEPOSIT:
                    do {
                        System.out.printf("Внесите сумму для пополнения счёта %s%n", accountName);
                        money = hasNextIntAmount();
                        if (money <= 0) {
                            System.out.println("Сумма должна  быть больше нуля!");
                        }
                    } while (money <= 0);
                    bankAccount.depositMoney(money);
                    System.out.printf("Вы пополнили счёт %s на %d рублей%n", accountName, money);
                    break;
                case GET:
                    System.out.printf("Комиссия за снятие составит %d %c от суммы%n", bankAccount.getPercent(), '%');
                    do {
                        System.out.printf("Введите сумму для снятия со счёта %s%n", accountName);
                        money = hasNextIntAmount();
                        if (money < 0) {
                            System.out.println("Сумма должна быть больше нуля!");
                        }
                    } while (money < 0);
                    int checkMoney = bankAccount.getMoney(money);
                    if (checkMoney == 1) {
                        System.out.printf("Вы сняли %d рублей%n", money);
                        System.out.printf("Комиссия составила %d рублей%n", bankAccount.getCommission());
                    } else if (checkMoney == -1) {
                        System.out.println("Недостаточно средст на счёте!");
                    } else if (checkMoney == 0) {
                        System.out.printf("Вы не можете снять деньги! " +
                                "Не истекло установленное время, после последнего пополнения счёта %s%n", accountName);
                    }
                    break;
                case SEND:
                    do {
                        System.out.println("Введите счёт, на который хотите совершить перевод" +
                                "(1 -> MainAccount, 2 -> DepositAccount, 3 -> CreditAccount)");
                        int selectAccount = hasNextIntCommand();
                        switch (selectAccount) {
                            case 1:
                                if (!bankAccount.equals(mainAccount)) {
                                    System.out.println("Введите сумму для перевода на счёт " +
                                            mainAccount.getClass().getSimpleName());
                                    int amount = hasNextIntAmount();
                                    if (bankAccount.send(mainAccount, amount)) {
                                        System.out.println(amount + " рублей переведены на счёт " + mainAccount);
                                    } else {
                                        System.out.println("Невозможно совершить перевод!");
                                    }
                                } else {
                                    System.out.println("Выберите другой счёт!");
                                }
                                break;
                            case 2:
                                if (!bankAccount.equals(depositAccount)) {
                                    System.out.println("Введите сумму для перевода на счёт " +
                                            depositAccount.getClass().getSimpleName());
                                    int amount = hasNextIntAmount();
                                    if (bankAccount.send(depositAccount, amount)) {
                                        System.out.println(amount + " рублей переведены на счёт " + depositAccount);
                                    } else {
                                        System.out.println("Невозможно совершить перевод!");
                                    }
                                } else {
                                    System.out.println("Выберите другой счёт!");
                                }
                                break;
                            case 3:
                                if (!bankAccount.equals(creditAccount)) {
                                    System.out.println("Введите сумму для перевода на счёт " +
                                            creditAccount.getClass().getSimpleName());
                                    int amount = hasNextIntAmount();
                                    if (bankAccount.send(creditAccount, amount)) {
                                        System.out.println(amount + " рублей переведены на счёт " + creditAccount);
                                    } else {
                                        System.out.println("Невозможно совершить перевод!");
                                    }
                                } else {
                                    System.out.println("Выберите другой счёт!");
                                }
                                break;
                            case 0:

                            default:
                                System.out.println("Команда не распознана! Повторите");
                        }
                    } while (true);
                case EXIT:
                    System.out.printf("Работа со счётом %s заершена...%n", accountName);
                    check = false;
                    break;
                default:
                    System.out.println("Команда не распознана! Повторите");
            }
        } while (check);
    }

    private static void checkInLine() {
        if (inLine.matches("^BALANCE")) {
            command = Command.BALANCE;
        } else if (inLine.matches("^GET")) {
            command = Command.GET;
        } else if (inLine.matches("^DEPOSIT")) {
            command = Command.DEPOSIT;
        } else if (inLine.matches("^EXIT")) {
            command = Command.EXIT;
        } else if (inLine.matches("^SEND")) {
            command = Command.SEND;
        } else {
            command = Command.NONE;
        }
    }

    public static Command getCommand() {
        return command;
    }

    public static int hasNextIntCommand() {
        int number;
            while (!scInt.hasNextInt()) {
                System.out.println("Неверно! Повторите ввод ещё раз.");
                scInt.next();
            }
            number = scInt.nextInt();
        return number;
    }

    public static int hasNextIntAmount() {
        int number = 1;
        do {
            if (number <= 0) {
                System.out.println("Сумма должна быть больше нуля!");
            }
            while (!scInt.hasNextInt()) {
                System.out.println("Неверно! Повторите ввод ещё раз.");
                scInt.next();
            }
            number = scInt.nextInt();
        } while (number <= 0);
        return number;
    }
}

