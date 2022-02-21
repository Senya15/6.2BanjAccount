package BankAccounts;

import lombok.Getter;
import java.util.Scanner;
@Getter

public class BankAccount {
    public int accountAmount;
    public Command command;
    public String inLine;
    public int percent; // установка комиссии за снятие денег (в %)

    public BankAccount(int percent) {
        this.percent = percent;
    }

    public void run() {
        Scanner scCommand = new Scanner(System.in);
        Scanner scInt = new Scanner(System.in);
        boolean check = true;
        do {
            System.out.println("Введите одну из команд (DEPOSIT, GET, BALANCE):");
            inLine = scCommand.nextLine().trim();
            checkInLine();
            int money;
            switch (getCommand()) {
                case BALANCE:
                    System.out.println("Остаток на счету: " + getAccountAmount() + " рублей");
                    break;
                case DEPOSIT:
                    do {
                        System.out.println("Внесите сумму для пополнения счёта:");
                        while (!scInt.hasNextInt()) {
                            System.out.println("Неверно! Повторите ввод ещё раз.");
                            scInt.next();
                        }
                        money = scInt.nextInt();
                        if (money <= 0) {
                            System.out.println("Сумма должна быть больше нуля!");
                        }
                    } while (money < 0);
                    depositMoney(money);
                    break;
                case GET:
                    do {
                        System.out.println("Комиссия за снятие составит " + percent + "% от суммы" +
                                "\nВведите сумму для снятия со счёта:");
                        while (!scInt.hasNextInt()) {
                            System.out.println("Неверно! Повторите ввод ещё раз.");
                            scInt.next();
                        }
                        money = scInt.nextInt();
                        if (money < 0) {
                            System.out.println("Сумма не может быть отрицательной!");
                        }
                    } while (money < 0);
                    int checkMoney = getMoney(money);
                    if (checkMoney == 1) {
                        System.out.println("Вы сняли " + money + " рублей");
                        System.out.println("Комиссия составила " + money / 100 * percent + " рублей");
                    } else if (checkMoney == -1) {
                        System.out.println("Недостаточно средст на счёте!");
                    } else if (checkMoney == 0) {
                        System.out.println("Вы не можете снять деньги! Не истекло установленное время, после последнего пополнения счёта.");
                    }
                    break;
                case EXIT:
                    System.out.println("Работа со счётом заершена...");
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

    public int getMoney(int money) {
        if (money <= accountAmount) {
            accountAmount -= money;
            return 1;
        } else {
            return -1;
        }
    }

    public void depositMoney(int money) {
        accountAmount += money;
        System.out.println("Вы пополнили счёт на " + money + " рублей");
    }


}

