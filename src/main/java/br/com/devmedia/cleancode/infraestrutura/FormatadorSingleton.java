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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;

public enum FormatadorSingleton {
    INSTANCE;

    public final String FORMATO_DUAS_CASAS_DECIMAIS = "###,###,##0.00";

    public final String FORMATO_INTEIRO = "###,###,##0";

    public String formatar(BigDecimal valor) {
        DecimalFormat numberFormat = new DecimalFormat(FORMATO_DUAS_CASAS_DECIMAIS);
        return numberFormat.format(valor.doubleValue());
    }

    public BigDecimal parse(String s) throws ParseException {
        DecimalFormat numberFormat = new DecimalFormat(FORMATO_DUAS_CASAS_DECIMAIS);
        return BigDecimal.valueOf(numberFormat.parse(s).doubleValue());
    }

    public String formatar(BigInteger valor) {
        DecimalFormat numberFormat = new DecimalFormat(FORMATO_INTEIRO);
        return numberFormat.format(valor.doubleValue());
    }
}
