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

package br.com.devmedia.cleancode.modelo.produto.converter;

import br.com.devmedia.cleancode.modelo.produto.Codigo;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CodigoJpaConverterTest {

    public final String VALOR_ESPERADO = "654321";

    private CodigoJpaConverter codigoJpaConverter;

    @Before
    public void setUp() {
        codigoJpaConverter = new CodigoJpaConverter();
    }

    @Test
    public void deve_converter_codigo_para_string() {
        String s = codigoJpaConverter.convertToDatabaseColumn(Codigo.valueOf(VALOR_ESPERADO));
        assertThat(s).isNotNull().isEqualTo(VALOR_ESPERADO);
    }

    @Test
    public void deve_converter_string_para_codigo() {
        Codigo codigo = codigoJpaConverter.convertToEntityAttribute(VALOR_ESPERADO);
        assertThat(codigo).isNotNull().isEqualTo(Codigo.valueOf(VALOR_ESPERADO));
    }
}