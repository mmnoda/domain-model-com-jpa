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

import br.com.devmedia.cleancode.infraestrutura.FormatadorSingleton;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Formattable;
import java.util.Formatter;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.isNull;

public final class Quantidade implements Serializable, Comparable<Quantidade>, Formattable {

    private static final long serialVersionUID = 4868785344958171893L;

    public static final Quantidade UM = valueOf(BigInteger.ONE);

    private final BigInteger valor;

    private Quantidade(BigInteger valor) {
        checkArgument(!isNull(valor), "Valor da quantidade nulo");
        this.valor = valor;
    }

    public static Quantidade valueOf(BigInteger valor) {
        return new Quantidade(valor);
    }

    public static Quantidade valueOf(long valor) {
        return new Quantidade(BigInteger.valueOf(valor));
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Quantidade) {
            final Quantidade other = (Quantidade) obj;
            return Objects.equals(this.valor, other.valor);
        }
        return false;
    }

    @Override
    public String toString() {
        return valor.toString();
    }

    @Override
    public int compareTo(Quantidade o) {
        return valor.compareTo(o.valor);
    }

    public long longValue() {
        return valor.longValue();
    }

    public BigDecimal toBigDecimal() {
        return new BigDecimal(valor);
    }

    @Override
    public void formatTo(Formatter formatter, int flags, int width, int precision) {
        formatter.format(FormatadorSingleton.INSTANCE.formatar(valor));
    }
}
