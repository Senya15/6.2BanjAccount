package BankAccounts;

import lombok.Getter;

@Getter
public class BankAccount {
    public int accountAmount;
    public int percent; // комиссии за снятие денег (в %)
    public int commission; // комиссии за снятие денег (в рублях)
    public BankAccount(int percent) {
        this.percent = percent;
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
    }

    public boolean send(BankAccount receiver, int amount) {
        if (getMoney(amount) == 1) {
            receiver.depositMoney(amount);
            return true;
        } else return false;
    }
}

