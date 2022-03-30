package BankAccounts;

import lombok.Getter;

@Getter
public class CreditAccount extends BankAccount {
    public static final float PERCENT = 1.5f; // комиссии за снятие денег (в %)
    private int commission; // комиссии за снятие денег (в рублях)

    public int getMoney(int money) {
        this.commission = (int) (money / 100 * PERCENT);
        money += this.commission;
        if (money <= this.accountAmount) {
            this.accountAmount -= money;
            return 1;
        } else {
            return -1;
        }
    }
}
