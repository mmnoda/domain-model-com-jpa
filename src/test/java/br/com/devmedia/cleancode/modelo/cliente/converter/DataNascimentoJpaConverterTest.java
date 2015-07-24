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

package br.com.devmedia.cleancode.modelo.cliente.converter;

import br.com.devmedia.cleancode.modelo.cliente.DataNascimento;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class DataNascimentoJpaConverterTest {

    private final LocalDate dataEsperada = LocalDate.of(2015, 7, 10);

    private DataNascimentoJpaConverter dataNascimentoJpaConverter;

    @Before
    public void setUp() {
        dataNascimentoJpaConverter = new DataNascimentoJpaConverter();
    }

    @Test
    public void deve_converter_date_para_nascimento() {
        DataNascimento nascimento = dataNascimentoJpaConverter.convertToEntityAttribute(Date.valueOf(dataEsperada));
        assertThat(nascimento).isNotNull().isEqualTo(DataNascimento.valueOf(dataEsperada));
    }

    @Test
    public void deve_converter_nascimento_para_date() {
        Date date = dataNascimentoJpaConverter.convertToDatabaseColumn(DataNascimento.valueOf(dataEsperada));
        assertThat(date).isNotNull().isEqualTo(Date.valueOf(dataEsperada));
    }
}