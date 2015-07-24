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

package br.com.devmedia.cleancode.modelo.comum;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PercentualTest {

    private Percentual percentual;

    @Test
    public void deve_ser_igual_ao_proprio() {
        percentual = Percentual.valueOf(100);
        assertPercentualIgualA(percentual);
    }

    @Test
    public void deve_implementar_equals_consistente() {
        percentual = Percentual.valueOf(75);
        assertPercentualIgualA(Percentual.valueOf(75));
        assertPercentualDiferenteDe(Percentual.valueOf(63));
    }

    @Test
    public void deve_calcular_percentual() {
        percentual = Percentual.valueOf(50);
        assertThat(percentual.calcular(Dinheiro.valueOf(1000))).isNotNull().
                isEqualTo(Dinheiro.valueOf(500));
    }

    @Test
    public void deve_somar_com_outro_percentual() {
        percentual = Percentual.valueOf(10);
        Percentual resultado = percentual.add(Percentual.valueOf(30));
        assertThat(resultado).isNotNull().isEqualTo(Percentual.valueOf(40));
    }

    private void assertPercentualIgualA(Percentual percentualIgual) {
        assertThat(percentual).isEqualTo(percentualIgual);
        assertThat(percentual.hashCode()).isEqualTo(percentualIgual.hashCode());
    }

    private void assertPercentualDiferenteDe(Percentual percentualDiferente) {
        assertThat(percentual).isNotEqualTo(percentualDiferente);
        assertThat(percentual.hashCode()).isNotEqualTo(percentualDiferente.hashCode());
    }

}