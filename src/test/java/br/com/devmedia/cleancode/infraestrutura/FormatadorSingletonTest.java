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

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class FormatadorSingletonTest {

    private final FormatadorSingleton formatadorSingleton = FormatadorSingleton.INSTANCE;

    @Test
    public void deve_formatar_big_decimal() throws Exception {
        assertThat(formatadorSingleton.formatar(BigDecimal.valueOf(1999.50))).isNotNull().isEqualTo("1.999,50");
    }

    @Test
    public void deve_formatar_big_integer() throws Exception {
        assertThat(formatadorSingleton.formatar(BigInteger.valueOf(123456))).isNotNull().isEqualTo("123.456");
    }

    @Test
    public void deve_efetuar_parse_com_sucesso() throws ParseException {
        assertThat(formatadorSingleton.parse("1.234.567,89")).isNotNull().
                isEqualTo(BigDecimal.valueOf(1234567.89));

    }

    @Test(expected = ParseException.class)
    public void deve_validar_parse_de_string_invalido() throws ParseException {
        formatadorSingleton.parse(" 1234567,89 ");
        fail("Parse incorreto");
    }
}