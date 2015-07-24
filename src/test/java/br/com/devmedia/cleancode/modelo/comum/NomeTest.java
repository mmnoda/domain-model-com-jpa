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

public class NomeTest {

    private Nome nome;

    @Test
    public void deve_ser_igual_ao_proprio() {
        nome = Nome.valueOf("João da Silva");
        assertNomeIgualA(nome);
    }

    @Test
    public void deve_implementar_equals_consistente() {
        nome = Nome.valueOf("José");
        assertNomeIgualA(Nome.valueOf("José"));
        assertNomeDiferenteDe(Nome.valueOf("Maria"));
    }

    @Test
    public void deve_converter_para_maiusculo() {
        nome = Nome.valueOf("teste minúsculo");
        assertThat(nome.toString()).isEqualTo("TESTE MINÚSCULO");
    }

    private void assertNomeIgualA(Nome nomeIgual) {
        assertThat(nome).isEqualTo(nomeIgual);
        assertThat(nome.hashCode()).isEqualTo(nomeIgual.hashCode());
    }

    private void assertNomeDiferenteDe(Nome nomeDiferente) {
        assertThat(nome).isNotEqualTo(nomeDiferente);
        assertThat(nome.hashCode()).isNotEqualTo(nomeDiferente.hashCode());
    }

}