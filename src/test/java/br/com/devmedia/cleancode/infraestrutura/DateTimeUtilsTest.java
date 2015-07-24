/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Márcio M. Noda
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package br.com.devmedia.cleancode.infraestrutura;

import org.junit.After;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class DateTimeUtilsTest {

    private final DateTimeUtils dateTimeUtils = DateTimeUtils.INSTANCE;

    @After
    public void tearDown() {
        dateTimeUtils.setClockPadrao();
    }

    @Test
    public void deve_fixar_data() {
        LocalDateTime dataFixada = getData30Junho2015();
        dateTimeUtils.fixar(dataFixada);

        assertNowComBaseNoClockIgualDataFixada(dataFixada);
        assertNovaLocalDateIgualDataFixada(dataFixada);
        assertNovaLocalDateTimeIgualDataFixada(dataFixada);
    }

    private void assertNovaLocalDateTimeIgualDataFixada(LocalDateTime dataFixada) {
        assertThat(dateTimeUtils.newLocalDateTime()).isEqualTo(dataFixada);
    }

    private void assertNovaLocalDateIgualDataFixada(LocalDateTime dataFixada) {
        assertThat(dateTimeUtils.newLocalDate()).isEqualTo(dataFixada.toLocalDate());
    }

    private void assertNowComBaseNoClockIgualDataFixada(LocalDateTime dataFixada) {
        assertThat(LocalDateTime.now(dateTimeUtils.getClock())).isEqualTo(dataFixada);
    }

    private LocalDateTime getData30Junho2015() {
        return LocalDateTime.of(2015, 6, 30, 0, 0);
    }

}