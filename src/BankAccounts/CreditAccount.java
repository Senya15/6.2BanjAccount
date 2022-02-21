package BankAccounts;

public class CreditAccount extends BankAccount {

    public CreditAccount(int percent) {

        super(percent);
    }

    public int getMoney(int money) {

        int commission = money / 100 * percent;
        money += commission;
        if (money <= accountAmount) {
            accountAmount -= money;
            return 1;
        } else {
            return -1;
        }
    }
}
