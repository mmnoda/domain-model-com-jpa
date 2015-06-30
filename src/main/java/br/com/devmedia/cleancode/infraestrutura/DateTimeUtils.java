package br.com.devmedia.cleancode.infraestrutura;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 *
 */
public class DateTimeUtils {

    private static DateTimeUtils INSTANCE;

    private ZoneId zone = ZoneId.systemDefault();
    private Clock clock;

    private DateTimeUtils() {
        zone = ZoneId.systemDefault();
        setPadrao();
    }

    public static DateTimeUtils getInstance() {
        if (INSTANCE == null) {
            synchronized (DateTimeUtils.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DateTimeUtils();
                }
            }
        }
        return INSTANCE;
    }

    public void fixar(LocalDateTime dataHora) {
        clock = Clock.fixed(dataHora.atZone(zone).toInstant(), zone);
    }

    public LocalDate newLocalDate() {
        return LocalDate.now(getClock());
    }

    public LocalDateTime newLocalDateTime() {
        return LocalDateTime.now(getClock());
    }

    public Clock getClock() {
        return clock;
    }

    public void setPadrao() {
        clock = Clock.systemDefaultZone();
    }
}
