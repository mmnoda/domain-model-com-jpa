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

import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;

public class DinheiroTest {

    private Dinheiro dinheiro;

    private String formatado;

    @Test
    public void deve_ser_igual_ao_proprio() {
        dinheiro = Dinheiro.valueOf(50);
        assertDinheiroIgualA(dinheiro);
    }

    @Test
    public void deve_implementar_equals_consistente() {
        dinheiro = Dinheiro.valueOf(200);
        assertDinheiroIgualA(Dinheiro.valueOf(200));
        assertDinheiroDiferenteDe(Dinheiro.valueOf(100));
    }

    @Test
    public void deve_formatar_corretamente() {
        dinheiro = Dinheiro.valueOf(123456.89);
        formatado = String.format("%s", dinheiro);
        assertThat(formatado).isNotNull().isEqualTo("123.456,89");
    }

    @Test
    public void deve_efetuar_parse() throws ParseException {
        dinheiro = Dinheiro.parse("987.654,32");
        assertDinheiroIgualA(Dinheiro.valueOf(987654.32));
    }

    @Test
    public void deve_somar() {
        dinheiro = Dinheiro.somar(Dinheiro.valueOf(10), Dinheiro.valueOf(20));
        assertDinheiroIgualA(Dinheiro.valueOf(30));
    }

    @Test
    public void deve_comparar_dinheiro_igual() {
        dinheiro = Dinheiro.valueOf(10.00);
        int compareTo = dinheiro.compareTo(Dinheiro.valueOf(10));
        assertThat(compareTo).isEqualTo(0);
    }

    @Test
    public void deve_comparar_dinheiro_maior() {
        dinheiro = Dinheiro.valueOf(123);
        int compareTo = dinheiro.compareTo(Dinheiro.valueOf(122.99));
        assertThat(compareTo).isEqualTo(1);
    }

    @Test
    public void deve_comparar_dinheiro_menor() {
        dinheiro = Dinheiro.valueOf(200);
        int compareTo = dinheiro.compareTo(Dinheiro.valueOf(200.01));
        assertThat(compareTo).isEqualTo(-1);
    }

    private void assertDinheiroIgualA(Dinheiro dinheiroIgual) {
        assertThat(dinheiro).isEqualTo(dinheiroIgual);
        assertThat(dinheiro.hashCode()).isEqualTo(dinheiroIgual.hashCode());
    }

    private void assertDinheiroDiferenteDe(Dinheiro dinheiroDiferente) {
        assertThat(dinheiro).isNotEqualTo(dinheiroDiferente);
        assertThat(dinheiro.hashCode()).isNotEqualTo(dinheiroDiferente.hashCode());
    }

}