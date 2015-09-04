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

import java.io.Serializable;
import java.util.Formattable;
import java.util.Formatter;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

public final class Cpf implements Serializable, Comparable<Cpf>, Formattable {

    private static final long serialVersionUID = -614188801416251816L;

    private static final Pattern PATTERN = Pattern.compile("(\\d{3})(\\d{3})(\\d{3})(\\d{2})");

    private static final String FORMATO_CPF = "$1.$2.$3-$4";

    private static final int[] PESOS_DIGITO_1 = {10, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] PESOS_DIGITO_2 = {11, 10, 9, 8, 7, 6, 5, 4, 3};

    private final String valor;

    private Cpf(String valor) {
        checkArgument(valor.matches("\\d{11}"), "Formato CPF inválido");
        this.valor = valor;
    }

    public static Cpf valueOf(String valor) {
        checkArgument(!isNullOrEmpty(valor), "CPF nulo ou vazio");
        return new Cpf(valor.replaceAll("[\\D]+", ""));
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(valor);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Cpf) {
            final Cpf other = (Cpf) obj;
            return Objects.equals(this.valor, other.valor);
        }
        return false;
    }

    @Override
    public String toString() {
        return valor;
    }

    @Override
    public int compareTo(Cpf o) {
        return valor.compareTo(o.valor);
    }

    @Override
    public void formatTo(Formatter formatter, int flags, int width, int precision) {
        Matcher matcher = PATTERN.matcher(valor);
        if (matcher.matches()) {
            formatter.format(matcher.replaceAll(FORMATO_CPF));
        }
    }
}
