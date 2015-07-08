package br.com.devmedia.cleancode.infraestrutura;

import org.junit.After;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class DateTimeUtilsTest {

    private final DateTimeUtils dateTimeUtils = DateTimeUtils.INSTANCE;

    @After
    public void tearDown() {
        dateTimeUtils.setPadrao();
    }

    @Test
    public void deve_fixar_data() {
        LocalDateTime dataFixada = getData30Junho2015();
        dateTimeUtils.fixar(dataFixada);

        assertNowComBaseNoClockIgualDataFixada(dataFixada);
        assertNewLocalDateIgualDataFixada(dataFixada);
        assertNewLocalDateTimeIgualDataFixada(dataFixada);
    }

    private void assertNewLocalDateTimeIgualDataFixada(LocalDateTime dataFixada) {
        assertThat(dateTimeUtils.newLocalDateTime()).isEqualTo(dataFixada);
    }

    private void assertNewLocalDateIgualDataFixada(LocalDateTime dataFixada) {
        assertThat(dateTimeUtils.newLocalDate()).isEqualTo(dataFixada.toLocalDate());
    }

    private void assertNowComBaseNoClockIgualDataFixada(LocalDateTime dataFixada) {
        assertThat(LocalDateTime.now(dateTimeUtils.getClock())).isEqualTo(dataFixada);
    }

    private LocalDateTime getData30Junho2015() {
        return LocalDateTime.of(2015, 6, 30, 0, 0);
    }

}