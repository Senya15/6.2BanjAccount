package BankAccounts;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DepositAccount extends BankAccount {
    private GregorianCalendar setTime;

    public DepositAccount(int percent) {
        super(percent);
    }

    public void depositMoney(int money) {
        accountAmount += money;
        setTime = new GregorianCalendar();
        setTime.add(Calendar.MINUTE, 3);
    }

    public int getMoney(int money) {
        Calendar timeNow = GregorianCalendar.getInstance();
        if (setTime.before(timeNow)) {
            if (money <= accountAmount) {
                accountAmount -= money;
                return 1;
            } else {
                long timeLeft = timeNow.getTime().getTime() - setTime.getTime().getTime();
                System.out.println("Не возможно снять деньги со счёта. Не прошло " + timeLeft);
                return -1;
            }
        } else return 0;
    }

}
