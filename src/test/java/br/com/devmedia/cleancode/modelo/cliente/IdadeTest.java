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

public class IdadeTest {

    private Idade idade;

    @Test
    public void deve_ser_igual_ao_proprio() {
        idade = Idade.valueOf(10);
        assertIdadeIgualA(idade);
    }

    @Test
    public void deve_implementar_equals_consistente() {
        idade = Idade.valueOf(21);
        assertIdadeIgualA(Idade.valueOf(21));
        assertIdadeDiferenteDe(Idade.valueOf(55));
    }

    @Test
    public void deve_verificar_se_MENOR_de_idade() {
        idade = Idade.valueOf(17);
        assertThat(idade.isMaiorIdade()).isFalse();
    }

    @Test
    public void deve_verificar_se_MAIOR_de_idade() {
        idade = Idade.valueOf(18);
        assertThat(idade.isMaiorIdade()).isTrue();
    }

    @Test
    public void deve_imprimir_to_string_corretamente() {
        idade = Idade.valueOf(84);
        assertThat(idade.toString()).isNotNull().isEqualTo("84 ano(s)") ;

    }

    private void assertIdadeIgualA(Idade idadeIgual) {
        assertThat(idade).isEqualTo(idadeIgual);
        assertThat(idade.hashCode()).isEqualTo(idadeIgual.hashCode());
    }

    private void assertIdadeDiferenteDe(Idade idadeDiferente) {
        assertThat(idade).isNotEqualTo(idadeDiferente);
        assertThat(idade.hashCode()).isNotEqualTo(idadeDiferente.hashCode());
    }
}