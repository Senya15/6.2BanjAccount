package BankAccounts;

import lombok.Getter;

import java.util.Calendar;
import java.util.GregorianCalendar;

@Getter
public class DepositAccount extends BankAccount {
    private GregorianCalendar setTime;
    private final int DAY = 30;
    private final int HOUR = 0;
    private final int MINUTE = 0;

    public void depositMoney(int money) {
        this.accountAmount += money;
        setTimer();
    }

    public void setTimer() {
        setTime = new GregorianCalendar();
        setTime.add(Calendar.DATE, DAY);
        setTime.add(Calendar.HOUR, HOUR);
        setTime.add(Calendar.MINUTE, MINUTE);
    }

    public int getMoney(int money) {
        Calendar timeNow = GregorianCalendar.getInstance();
        if (setTime.before(timeNow)) {
            if (money <= this.accountAmount) {
                this.accountAmount -= money;
                return 1;
            } else {
                return -1;
            }
        } else {
            long timeLeft = (setTime.getTime().getTime() - timeNow.getTime().getTime());
            Timer timer = new Timer(timeLeft);
            System.out.println("Не возможно снять деньги со счёта! С последнего пополнения счёта не прошло "
                    + timer.getTime());
            return 0;
        }
    }

}
