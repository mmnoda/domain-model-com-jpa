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
import java.math.BigDecimal;
import java.util.Objects;

import static br.com.devmedia.cleancode.infraestrutura.ArredondamentoConstants.ARREDONDAMENTO_PADRAO;
import static br.com.devmedia.cleancode.infraestrutura.ArredondamentoConstants.CASAS_DECIMAIS;
import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.isNull;

public final class Percentual implements Serializable, Comparable<Percentual> {

    private static final long serialVersionUID = 1394408518476318914L;

    public static final Percentual ZERO = valueOf(BigDecimal.ZERO);

    private static final BigDecimal _100 = new BigDecimal(100).setScale(CASAS_DECIMAIS, ARREDONDAMENTO_PADRAO);

    private final BigDecimal valor;

    private Percentual(BigDecimal valor) {
        checkArgument(!isNull(valor), "Valor nulo");
        this.valor = valor.setScale(CASAS_DECIMAIS, ARREDONDAMENTO_PADRAO);
    }

    public static Percentual valueOf(BigDecimal valor) {
        return new Percentual(valor);
    }

    public static Percentual valueOf(double valor) {
        return new Percentual(BigDecimal.valueOf(valor));
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Percentual) {
            final Percentual other = (Percentual) obj;
            return Objects.equals(this.valor, other.valor);
        }
        return false;
    }

    @Override
    public String toString() {
        return valor.toString();
    }

    public Percentual add(Percentual outro) {
        return valueOf(valor.add(outro.valor));
    }

    public Dinheiro calcular(Dinheiro dinheiro) {
        return Dinheiro.valueOf(dinheiro.toBigDecimal().multiply(valor).
                divide(_100, ARREDONDAMENTO_PADRAO));
    }

    public BigDecimal toBigDecimal() {
        return valor;
    }

    @Override
    public int compareTo(Percentual o) {
        return valor.compareTo(o.valor);
    }
}
