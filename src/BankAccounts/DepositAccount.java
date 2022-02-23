package BankAccounts;

import lombok.Getter;

import java.util.Calendar;
import java.util.GregorianCalendar;

@Getter
public class DepositAccount extends BankAccount {
    private GregorianCalendar setTime;

    public void depositMoney(int money) {
        this.accountAmount += money;
        setTime = new GregorianCalendar();
        setTime.add(Calendar.MINUTE, 3);
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
            int timeLeft = (int) (setTime.getTime().getTime() - timeNow.getTime().getTime());
            int timeLeftHr = timeLeft / 3_600_000;
            int timeLeftMinutes = timeLeft / 3_600_000 / 6_0000;
            int timeLeftSeconds = timeLeft % 60_000 / 1000;
            System.out.printf("Не возможно снять деньги со счёта. С последнего пополнения счёта не прошло " +
                            "%d час(а/ов) %d минут(ы) %d секунд(ы)%n",
                    timeLeftHr, timeLeftMinutes, timeLeftSeconds);
            return 0;
        }
    }

}
