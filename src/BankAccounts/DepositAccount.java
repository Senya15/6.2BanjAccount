package BankAccounts;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DepositAccount extends BankAccount {
    private GregorianCalendar timer;

    public void depositMoney(int money) {
        this.accountAmount += money;
        setTimer();
    }

    private void setTimer() {
        timer = new GregorianCalendar();
        int DAY = 30;
        int HOUR = 0;
        int MINUTE = 0;
        timer.add(Calendar.DATE, DAY);
        timer.add(Calendar.HOUR, HOUR);
        timer.add(Calendar.MINUTE, MINUTE);
    }

    private GregorianCalendar getTimer() {
        return timer;
    }

    public int getMoney(int money) {
        Calendar timeNow = GregorianCalendar.getInstance();
        if (getTimer().before(timeNow)) {
            if (money <= this.accountAmount) {
                this.accountAmount -= money;
                return 1;
            } else {
                return -1;
            }
        } else {
            long timeLeft = (timer.getTime().getTime() - timeNow.getTime().getTime());
            Timer timer = new Timer(timeLeft);
            System.out.println("Не возможно снять деньги со счёта! С последнего пополнения счёта не прошло "
                    + timer.getTime());
            return 0;
        }
    }

}
