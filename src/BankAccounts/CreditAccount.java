package BankAccounts;

public class CreditAccount extends BankAccount {

    public CreditAccount(int percent) {

        super(percent);
    }

    public int getMoney(int money) {

        this.commission = money / 100 * percent;
        money += this.commission;
        if (money <= this.accountAmount) {
            this.accountAmount -= money;
            return 1;
        } else {
            return -1;
        }
    }
}
