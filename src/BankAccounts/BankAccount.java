package BankAccounts;

import lombok.Getter;

@Getter
public class BankAccount {
    int accountAmount;

    public int getMoney(int money) {
        if (money <= this.accountAmount) {
            this.accountAmount -= money;
            return 1;
        } else {
            return -1;
        }
    }

    public void depositMoney(int money) {
        this.accountAmount += money;
    }

    public boolean send(BankAccount receiver, int amount) {
        if (getMoney(amount) == 1) {
            receiver.depositMoney(amount);
            return true;
        } else return false;
    }
}

