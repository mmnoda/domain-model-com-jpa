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

package br.com.devmedia.cleancode.modelo.comum.converter;

import br.com.devmedia.cleancode.modelo.comum.Quantidade;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuantidadeJpaConverterTest {

    private QuantidadeJpaConverter quantidadeJpaConverter;

    @Before
    public void setUp() {
        quantidadeJpaConverter = new QuantidadeJpaConverter();
    }

    @Test
    public void deve_converter_quantidade_para_long() {
        Long valor = quantidadeJpaConverter.convertToDatabaseColumn(Quantidade.valueOf(50));
        assertThat(valor).isNotNull().isEqualTo(50);
    }

    @Test
    public void deve_converter_long_para_quantidade() {
        Quantidade quantidade = quantidadeJpaConverter.convertToEntityAttribute(330L);
        assertThat(quantidade).isNotNull().isEqualTo(Quantidade.valueOf(330));
    }
}