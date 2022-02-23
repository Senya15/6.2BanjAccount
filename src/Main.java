import BankAccounts.BankAccount;
import BankAccounts.Command;
import BankAccounts.CreditAccount;
import BankAccounts.DepositAccount;

import java.util.Scanner;

public class Main {
    static BankAccount mainAccount = new BankAccount();
    static DepositAccount depositAccount = new DepositAccount();
    static CreditAccount creditAccount = new CreditAccount();

    static String mainAccountName = mainAccount.getClass().getSimpleName();
    static String depositAccountName = depositAccount.getClass().getSimpleName();
    static String creditAccountName = creditAccount.getClass().getSimpleName();
    static Command command;
    static String inLine;
    static Scanner scInt = new Scanner(System.in);
    static Scanner scCommand = new Scanner(System.in);

    public static void main(String[] args) {
        long timeMileSeconds = 259200000L; // 20,76145 минут
        int timeDays = (int) (timeMileSeconds / 86_400_000); // дней
        int timeHr = (int) (timeMileSeconds % 86_400_000 / 3_600_000); // 0,34602416666667 часа
        int timeMinutes = (int) (timeMileSeconds % 3_600_000 / 60000); // 20 минут
        int ostatokSeconds = (int) (timeMileSeconds % 60000 / 1000); // секунд

        String days;
        String hours;
        String minutes;
        String seconds;
     /*   if (timeDays == 1) days = "день";
        if (timeDays < 5) days = "дня";
        if (timeDays >= 5 || timeDays == 0) days = "дней";*/
        switch (timeDays) {
            case 1:
                days = "день";
                break;
            case 2:
            case 3:
            case 4:
                days = "дня";
                break;
            default:
                days = "дней";
        }

        System.out.printf("%d %s %d час(а/ов) %d минут(ы) %d секунд(ы)%n",
                timeDays, days, timeHr, timeMinutes, ostatokSeconds);

        boolean check = true;
        do {
            System.out.printf("Выберите счёт (1 -> %s, 2 -> %s, 3 -> %s, 9 -> проверка баланса всех счетов, " +
                            "0 -> завершение программы):%n", mainAccountName, depositAccountName, creditAccountName);
            int inCommand = nextIntCommand();
            switch (inCommand) {
                case 1:
                    selectOperation(mainAccount);
                    break;
                case 2:
                    selectOperation(depositAccount);
                    break;
                case 3:
                    selectOperation(creditAccount);
                    break;
                case 9:
                    System.out.printf("Остаток на счету %s: %d рублей%n",
                            mainAccountName, mainAccount.getAccountAmount());
                    System.out.printf("Остаток на счету %s: %d рублей%n",
                            mainAccountName, depositAccount.getAccountAmount());
                    System.out.printf("Остаток на счету %s: %d рублей%n%n",
                            creditAccountName, creditAccount.getAccountAmount());
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

    public static void selectOperation(BankAccount bankAccount) {
        String bankAccountName = bankAccount.getClass().getSimpleName();
        boolean check = true;
        int money;
        do {
            System.out.printf("Введите одну из команд для счёта %s (BALANCE, DEPOSIT, GET, SEND, EXIT):%n",
                    bankAccountName);
            inLine = scCommand.nextLine().trim();
            checkInLine();
            switch (getCommand()) {
                case BALANCE:
                    System.out.printf("Остаток на счету: %d рублей%n", bankAccount.getAccountAmount());
                    break;
                case DEPOSIT:
                    System.out.printf("Внесите сумму для пополнения счёта %s%n", bankAccountName);
                    money = nextIntAmount();
                    if (money == 0) break;
                    bankAccount.depositMoney(money);
                    System.out.printf("Вы пополнили счёт %s на %d рублей%n", bankAccountName, money);
                    break;
                case GET:
                    if (bankAccount.equals(creditAccount)) {
                        System.out.printf("Комиссия за снятие составит %.1f %c от суммы%n", CreditAccount.PERCENT, '%');
                    }
                    System.out.printf("Введите сумму для снятия со счёта %s%n", bankAccountName);
                    money = nextIntAmount();
                    if (money == 0) break;
                    int checkMoney = bankAccount.getMoney(money);
                    if (checkMoney == 1) {
                        System.out.printf("Вы сняли %d рублей%n", money);
                        if (bankAccount.equals(creditAccount)) {
                            System.out.printf("Комиссия составила %d рублей%n", creditAccount.getCommission());
                        }
                    } else if (checkMoney == -1) {
                        System.out.println("Недостаточно средст на счёте!");
                    }
                    break;
                case SEND:
                    System.out.printf("Введите счёт, на который хотите совершить перевод (1 -> %s, 2 -> %s," +
                                    " 3 -> %s, *любое другое число* -> Вернутья назад)%n",
                            mainAccountName, depositAccountName, creditAccountName);
                    int selectAccount = nextIntCommand();
                    switch (selectAccount) {
                        case 1:
                            send(bankAccount, mainAccount, mainAccountName);
                            break;
                        case 2:
                            send(bankAccount, depositAccount, depositAccountName);
                            break;
                        case 3:
                            send(bankAccount, creditAccount, creditAccountName);
                            break;
                        default:
                            System.out.println("Команда не распознана! Возврат назад...");
                    }
                    break;
                case EXIT:
                    System.out.printf("Работа со счётом %s заершена...%n", bankAccountName);
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

    public static void send(BankAccount senderAccount, BankAccount receiver, String receiverName) {
        if (senderAccount.checkSend(receiver)) {
            System.out.println("Вы пытаетесь перевести денги на этот же счёт.\nВыберите другой счёт.");
            return;
        }
        if (senderAccount.equals(creditAccount)) {
            System.out.printf("Комиссия за снятие денег со счёта составит %.1f %c от суммы%n",
                    CreditAccount.PERCENT, '%');
        }
        System.out.printf("Введите сумму для перевода на счёт %s%n", receiverName);
        int amount = nextIntAmount();
        if (amount == 0) return;
        if (senderAccount.send(receiver, amount)) {
            System.out.printf("%d рублей переведены на счёт %s%n", amount, receiverName);
            if (senderAccount.equals(creditAccount)) {
                System.out.printf("Комиссия составила %d рублей%n", creditAccount.getCommission());
            }
        } else {
            System.out.println("Невозможно совершить перевод!");
        }
    }

    public static int nextIntCommand() {
        while (!scInt.hasNextInt()) {
            System.out.println("Неверно! Повторите ввод ещё раз.");
            scInt.next();
        }
        return scInt.nextInt();
    }

    public static int nextIntAmount() {
        int number = 1;
        do {
            if (number < 0) {
                System.out.println("Сумма должна быть больше нуля!");
            }
            while (!scInt.hasNextInt()) {
                System.out.println("Неверно! Повторите ввод ещё раз.");
                scInt.next();
            }
            number = scInt.nextInt();
        } while (number < 0);
        return number;
    }

    public static Command getCommand() {
        return command;
    }
}

