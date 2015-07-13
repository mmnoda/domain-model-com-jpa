package br.com.devmedia.cleancode.infraestrutura;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 *
 */
public enum DateTimeUtils {
    INSTANCE;

    private final ZoneId zone;
    private Clock clock;

    DateTimeUtils() {
        zone = ZoneId.systemDefault();
        setClockPadrao();
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

    public void setClockPadrao() {
        clock = Clock.systemDefaultZone();
    }
}
