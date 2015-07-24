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

package br.com.devmedia.cleancode.modelo.cliente;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CpfTest {

    private Cpf cpf;

    @Test
    public void deve_ser_igual_ao_proprio() {
        cpf = Cpf.valueOf("56470416470");
        assertCpfIgualA(cpf);
    }

    @Test
    public void deve_implementar_equals_consistente() {
        cpf = Cpf.valueOf("31628763973");
        assertCpfIgualA(Cpf.valueOf("31628763973"));
        assertCpfDiferenteDe(Cpf.valueOf("42263873090"));
    }

    @Test
    public void deve_formatar_corretamente() {
        cpf = Cpf.valueOf("31628763973");
        assertThat(String.format("%s", cpf)).isEqualTo("316.287.639-73");
    }

    private void assertCpfIgualA(Cpf cpfIgual) {
        assertThat(cpf).isEqualTo(cpfIgual);
        assertThat(cpf.hashCode()).isEqualTo(cpfIgual.hashCode());
    }

    private void assertCpfDiferenteDe(Cpf cpfDiferente) {
        assertThat(cpf).isNotEqualTo(cpfDiferente);
        assertThat(cpf.hashCode()).isNotEqualTo(cpfDiferente.hashCode());
    }

}