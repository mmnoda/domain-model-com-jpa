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

import br.com.devmedia.cleancode.infraestrutura.DateTimeUtils;
import org.junit.After;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class DataNascimentoTest {

    private DataNascimento nascimento;

    private final DateTimeUtils dateTimeUtils = DateTimeUtils.INSTANCE;

    @After
    public void tearDown() {
        dateTimeUtils.setClockPadrao();
    }

    @Test
    public void deve_ser_igual_ao_proprio() {
        nascimento = newDataNascimento10Dezembro1995();
        assertNascimentoIgualA(nascimento);
    }

    @Test
    public void deve_implementar_equals_consistente() {
        nascimento = newDataNascimento25Junho1980();
        assertNascimentoIgualA(newDataNascimento25Junho1980());
        assertNascimentoDiferenteDe(DataNascimento.of(2000, 1, 1));
    }

    @Test
    public void deve_calcular_idade_de_19_anos() {
        fixarData30Junho2015();
        nascimento = newDataNascimento10Dezembro1995();
        assertIdadeCalculadaIgualA(Idade.valueOf(19));
    }

    @Test
    public void deve_calcular_idade_de_35_anos() {
        fixarData30Junho2015();
        nascimento = newDataNascimento25Junho1980();
        assertIdadeCalculadaIgualA(Idade.valueOf(35));
    }

    @Test
    public void deve_formatar_data_corretamente() {
        nascimento = newDataNascimento25Junho1980();
        assertThat(nascimento.toString()).isNotNull().isEqualTo("25/06/1980");
    }

    private void fixarData30Junho2015() {
        dateTimeUtils.fixar(getData30Junho2015());
    }

    private DataNascimento newDataNascimento10Dezembro1995() {
        return DataNascimento.of(1995, 12, 10);
    }

    private DataNascimento newDataNascimento25Junho1980() {
        return DataNascimento.of(1980, 6, 25);
    }

    private void assertIdadeCalculadaIgualA(Idade idadeEsperada) {
        assertThat(nascimento.getIdade()).isNotNull().isEqualTo(idadeEsperada);
    }

    private void assertNascimentoDiferenteDe(DataNascimento outroDiferente) {
        assertThat(nascimento).isNotEqualTo(outroDiferente);
        assertThat(nascimento.hashCode()).isNotEqualTo(outroDiferente.hashCode());
    }

    private void assertNascimentoIgualA(DataNascimento outroIgual) {
        assertThat(nascimento).isEqualTo(outroIgual);
        assertThat(nascimento.hashCode()).isEqualTo(outroIgual.hashCode());
    }

    private LocalDateTime getData30Junho2015() {
        return LocalDateTime.of(2015, 6, 30, 0, 0);
    }
}