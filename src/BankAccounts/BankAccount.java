package BankAccounts;

import lombok.Getter;

@Getter
public class BankAccount {
    public int accountAmount;

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

    public boolean checkSend(BankAccount receiver) {
        return BankAccount.this.equals(receiver);
    }

    public boolean send(BankAccount receiver, int amount) {
        if (getMoney(amount) == 1) {
            receiver.depositMoney(amount);
            return true;
        } else return false;
    }
}

