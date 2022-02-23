package BankAccounts;

public class Timer {
    long timeMileSeconds;

    public Timer(long timeMileSeconds) {
        this.timeMileSeconds = timeMileSeconds;
    }

    public String getTime() {
        int timeDays = (int) (timeMileSeconds / 86_400_000); // дней
        int timeHr = (int) (timeMileSeconds % 86_400_000 / 3_600_000); // часов
        int timeMinutes = (int) (timeMileSeconds % 3_600_000 / 60000); // минут
        int timeSeconds = (int) (timeMileSeconds % 60000 / 1000); // секунд

        String d = getDay(timeDays);
        String hr = getHour(timeHr);
        String min = getEnding(timeMinutes);
        String sec = getEnding(timeSeconds);

        return  String.format("%d %s %d %s %d минут%s %d секунд%s%n",
                timeDays, d, timeHr, hr, timeMinutes, min, timeSeconds, sec);
    }

    public String getDay(int timeDays) {
        return switch (timeDays % 10) {
            case 1 -> "день";
            case 2, 3, 4 -> "дня";
            default -> "дней";
        };
    }

    public String getHour(int timeHr) {
        return switch (timeHr % 10) {
            case 1 -> "час";
            case 2, 3, 4 -> "часа";
            default -> "часов";
        };
    }

    public String getEnding(int timeMinOrSec) {
        return switch (timeMinOrSec % 10) {
            case 1 -> "а";
            case 2, 3, 4 -> "ы";
            default -> "";
        };
    }
}
