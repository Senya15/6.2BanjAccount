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
                case 1 -> run(mainAccount);
                case 2 -> run(depositAccount);
                case 3 -> run(creditAccount);
                case 0 -> {
                    System.out.println("Програма завершена...");
                    check = false;
                    scInt.close();
                }
                default -> System.out.println("Команда не распознана! Повторите");
            }
        } while (check);
    }

    public static void run(BankAccount bankAccount) {
        String accountName = bankAccount.getClass().getSimpleName();
        Scanner scCommand = new Scanner(System.in);
        boolean check = true;
        int money;
        int amount;
        do {
            System.out.printf("Введите одну из команд для счёта %s (BALANCE, DEPOSIT, GET, EXIT):%n", accountName);
            inLine = scCommand.nextLine().trim();
            checkInLine();
            switch (getCommand()) {
                case BALANCE:
                    System.out.printf("Остаток на счету: %d рублей%n", bankAccount.getAccountAmount());
                    break;
                case DEPOSIT:
                        System.out.printf("Внесите сумму для пополнения счёта %s%n", accountName);
                        money = hasNextIntAmount();
                    bankAccount.depositMoney(money);
                    System.out.printf("Вы пополнили счёт %s на %d рублей%n", accountName, money);
                    break;
                case GET:
                    System.out.printf("Комиссия за снятие составит %d %c от суммы%n", bankAccount.getPercent(), '%');
                        System.out.printf("Введите сумму для снятия со счёта %s%n", accountName);
                        money = hasNextIntAmount();
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
                    System.out.println("Введите счёт, на который хотите совершить перевод" +
                            "(1 -> MainAccount, 2 -> DepositAccount, 3 -> CreditAccount, " +
                            "*любое число* -> Вернутья назад)");
                    int selectAccount = hasNextIntCommand();
                    switch (selectAccount) {
                        case 1:
                            if (bankAccount.checkSend(mainAccount)) {
                                System.out.println("Выберите другой счёт!");
                                break;
                            }
                            System.out.printf("Введите сумму для перевода на счёт %s%n",
                                    mainAccount.getClass().getSimpleName());
                            amount = hasNextIntAmount();
                            if (bankAccount.send(mainAccount, amount)) {
                                System.out.printf("%d рублей переведены на счёт %s%n", amount,
                                        mainAccount.getClass().getSimpleName());
                            } else {
                                System.out.println("Невозможно совершить перевод! Недостаточно средст.");
                            }
                            break;
                        case 2:
                            if (bankAccount.checkSend(depositAccount)) {
                                System.out.println("Выберите другой счёт!");
                                break;
                            }
                            System.out.printf("Введите сумму для перевода на счёт %s%n",
                                    depositAccount.getClass().getSimpleName());
                            amount = hasNextIntAmount();
                            if (bankAccount.send(depositAccount, amount)) {
                                System.out.printf("%d рублей переведены на счёт %s%n", amount,
                                        depositAccount.getClass().getSimpleName());
                            } else {
                                System.out.println("Невозможно совершить перевод! Недостаточно средст.");
                            }
                            break;
                        case 3:
                            if (bankAccount.checkSend(creditAccount)) {
                                System.out.println("Выберите другой счёт!");
                                break;
                            }
                            System.out.printf("Введите сумму для перевода на счёт %s%n",
                                    creditAccount.getClass().getSimpleName());
                            amount = hasNextIntAmount();
                            if (bankAccount.send(creditAccount, amount)) {
                                System.out.printf("%d рублей переведены на счёт %s%n", amount,
                                        creditAccount.getClass().getSimpleName());
                            } else {
                                System.out.println("Невозможно совершить перевод! Недостаточно средст.");
                            }
                            break;
                        default:
                            System.out.println("Команда не распознана! Возврат назад...");
                    }
                    break;
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


    public static int hasNextIntCommand() {
        while (!scInt.hasNextInt()) {
            System.out.println("Неверно! Повторите ввод ещё раз.");
            scInt.next();
        }
        return scInt.nextInt();
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

    public static Command getCommand() {
        return command;
    }
}

