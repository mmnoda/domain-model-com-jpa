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

import java.io.Serializable;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

public final class Descricao implements Serializable, Comparable<Descricao> {

    private static final long serialVersionUID = 5026614055708173550L;

    public static final Descricao VAZIA = new Descricao("");

    private final String valor;

    private Descricao(String valor) {
        this.valor = valor;
    }

    public static Descricao valueOf(String valor) {
        checkArgument(!isNullOrEmpty(valor), "Descrição nulo ou vazio");
        return new Descricao(valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Descricao) {
            final Descricao other = (Descricao) obj;
            return Objects.equals(this.valor, other.valor);
        }
        return false;
    }

    @Override
    public String toString() {
        return valor;
    }

    @Override
    public int compareTo(Descricao o) {
        return valor.compareTo(o.valor);
    }
}
